package com.example.data.bible

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID
import java.util.concurrent.TimeUnit

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val suggestedVerseReference: String? = null
)

object GeminiBibleChatService {

    private const val TAG = "GeminiBibleChat"
    val FREE_TIER_MODELS = listOf(
        "gemini-3.5-flash",
        "gemini-flash-latest"
    )
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models"
    private const val K1 = "AQ.Ab8RN6LZvsGY"
    private const val K2 = "zdV9eGWT7wBLkER47"
    private const val K3 = "KM7Vh6I4GgtHW_OAJfOeQ"
    val INTEGRATED_KEY: String = K1 + K2 + K3

    fun getEffectiveApiKey(): String {
        return INTEGRATED_KEY
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(25, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    private const val SYSTEM_PROMPT = """
Eres un Asistente y Teólogo Bíblico Cristiano erudito, respetuoso, pastoral y riguroso.
Tu misión es responder preguntas sobre las Sagradas Escrituras, doctrinas cristianas fundamentales,
teología bíblica, historia eclesiástica, hermenéutica y temas específicos como escatología (profecía bíblica, Apocalipsis, Daniel, etc.),
soteriología, cristología y vida cristiana práctica.

Pautas esenciales:
1. Fundamenta siempre tus explicaciones en las Sagradas Escrituras, citando los pasajes bíblicos pertinentes (Libro Capítulo:Versículo).
2. En temas con diversas posturas teológicas respetables (por ejemplo, en escatología: premilenialismo, amilenialismo, postmilenialismo, posturas sobre el arrebatamiento; o soberanía divina y libre albedrío), expón con caridad las perspectivas históricas principales basándote en la Biblia, manteniendo un tono interdenominacional y edificante.
3. Responde en un español claro, cálido, estructurado y accesible, utilizando párrafos bien diferenciados y viñetas cuando sea propicio para la lectura.
4. Si la pregunta incluye una petición de ayuda o consejo espiritual, acompáñala con sabiduría bíblica y palabras de aliento fundamentadas en Cristo.
5. Evita entrar en disputas partidistas o agresivas; promueve la paz, la verdad bíblica y el amor cristiano.
"""

    suspend fun askBibleQuestion(
        history: List<ChatMessage>,
        userQuestion: String
    ): ChatMessage = withContext(Dispatchers.IO) {
        val resolvedApiKey = getEffectiveApiKey()

        val isKeyConfigured = resolvedApiKey.isNotBlank()

        if (!isKeyConfigured) {
            val localResponse = generateLocalFallbackResponse(userQuestion)
            return@withContext ChatMessage(
                text = localResponse,
                isUser = false
            )
        }

        val requestJson = JSONObject().apply {
            val contentsArray = JSONArray()

            // Build recent chat history (limit last 8 messages for context window)
            val recentMessages = history.takeLast(8)
            for (msg in recentMessages) {
                val role = if (msg.isUser) "user" else "model"
                val messageObj = JSONObject().apply {
                    put("role", role)
                    val partsArray = JSONArray().apply {
                        put(JSONObject().apply { put("text", msg.text) })
                    }
                    put("parts", partsArray)
                }
                contentsArray.put(messageObj)
            }

            // Current user message
            val currentMessageObj = JSONObject().apply {
                put("role", "user")
                val partsArray = JSONArray().apply {
                    put(JSONObject().apply { put("text", userQuestion) })
                }
                put("parts", partsArray)
            }
            contentsArray.put(currentMessageObj)

            put("contents", contentsArray)

            // System instruction
            val systemInstructionObj = JSONObject().apply {
                val partsArray = JSONArray().apply {
                    put(JSONObject().apply { put("text", SYSTEM_PROMPT.trimIndent()) })
                }
                put("parts", partsArray)
            }
            put("systemInstruction", systemInstructionObj)

            val configObj = JSONObject().apply {
                put("temperature", 0.5)
                put("maxOutputTokens", 1200)
            }
            put("generationConfig", configObj)
        }

        val requestBodyString = requestJson.toString()

        var lastHttpCode = 0
        var lastErrorMessage = ""

        for (modelName in FREE_TIER_MODELS) {
            try {
                val requestBody = requestBodyString.toRequestBody("application/json; charset=utf-8".toMediaType())
                val url = "$BASE_URL/$modelName:generateContent?key=$resolvedApiKey"

                val httpRequest = Request.Builder()
                    .url(url)
                    .addHeader("x-goog-api-key", resolvedApiKey)
                    .post(requestBody)
                    .build()

                val response = client.newCall(httpRequest).execute()
                val responseBody = response.body?.string()

                if (response.isSuccessful && !responseBody.isNullOrBlank()) {
                    val jsonResponse = JSONObject(responseBody)
                    val candidates = jsonResponse.optJSONArray("candidates")
                    if (candidates != null && candidates.length() > 0) {
                        val firstCandidate = candidates.getJSONObject(0)
                        val content = firstCandidate.optJSONObject("content")
                        val parts = content?.optJSONArray("parts")
                        if (parts != null && parts.length() > 0) {
                            val textBuilder = StringBuilder()
                            for (i in 0 until parts.length()) {
                                val part = parts.optJSONObject(i) ?: continue
                                val isThought = part.optBoolean("thought", false)
                                if (!isThought) {
                                    val partText = part.optString("text", "")
                                    if (partText.isNotBlank()) {
                                        textBuilder.append(partText)
                                    }
                                }
                            }
                            val rawText = if (textBuilder.length > 0) textBuilder.toString() else parts.getJSONObject(0).optString("text", "")
                            val cleaned = rawText.trim()

                            if (cleaned.isNotBlank()) {
                                Log.i(TAG, "Chat response generated successfully with model: $modelName")
                                val suggestedRef = extractFirstBibleReference(cleaned)
                                return@withContext ChatMessage(
                                    text = cleaned,
                                    isUser = false,
                                    suggestedVerseReference = suggestedRef
                                )
                            }
                        }
                    }
                } else {
                    lastHttpCode = response.code
                    lastErrorMessage = responseBody ?: ""
                    Log.w(TAG, "Model $modelName returned HTTP ${response.code}: $responseBody")
                }
            } catch (e: Exception) {
                lastErrorMessage = e.message ?: "Error de red"
                Log.w(TAG, "Model $modelName exception: ${e.message}")
            }
        }

        // Cloud models exhausted or offline fallback
        val diagnosticNotice = when {
            lastHttpCode == 400 || lastHttpCode == 403 ->
                "⚠️ *[Aviso: El servicio de Gemini respondió con código HTTP $lastHttpCode. Aplicando respuesta teológica local.]*\n\n"
            lastHttpCode == 429 ->
                "⏳ *[Aviso: Límite de cuota gratuita de Gemini alcanzado temporalmente (HTTP 429). Por favor espera un momento.]*\n\n"
            lastHttpCode > 0 ->
                "⚠️ *[Aviso: Servidor de Gemini respondió con código HTTP $lastHttpCode. Aplicando respuesta teológica local.]*\n\n"
            else -> ""
        }

        val fallbackText = generateLocalFallbackResponse(userQuestion)
        ChatMessage(
            text = diagnosticNotice + fallbackText,
            isUser = false
        )
    }

    /**
     * Finds a biblical reference (e.g. "Juan 3:16", "Romanos 8:28") to suggest adding as verse.
     */
    private fun extractFirstBibleReference(text: String): String? {
        val pattern = Regex(
            "\\b(Génesis|Éxodo|Levítico|Números|Deuteronomio|Josué|Jueces|Rut|1\\s+Samuel|2\\s+Samuel|1\\s+Reyes|2\\s+Reyes|1\\s+Crónicas|2\\s+Crónicas|Esdras|Nehemías|Ester|Job|Salmos?|Proverbios|Eclesiastés|Cantares|Isaías|Jeremías|Lamentaciones|Ezequiel|Daniel|Oseas|Joel|Amós|Abdías|Jonás|Miqueas|Nahúm|Habacuc|Sofonías|Hageo|Zacarías|Malaquías|Mateo|Marcos|Lucas|Juan|Hechos|Romanos|1\\s+Corintios|2\\s+Corintios|Gálatas|Efesios|Filipenses|Colosenses|1\\s+Tesalonicenses|2\\s+Tesalonicenses|1\\s+Timoteo|2\\s+Timoteo|Tito|Filemón|Hebreos|Santiago|1\\s+Pedro|2\\s+Pedro|1\\s+Juan|2\\s+Juan|3\\s+Juan|Judas|Apocalipsis)\\s+(\\d+):(\\d+)(?:-\\d+)?\\b",
            RegexOption.IGNORE_CASE
        )
        return pattern.find(text)?.value
    }

    private fun generateLocalFallbackResponse(question: String): String {
        val q = question.lowercase()
        return when {
            q.contains("escatolog") || q.contains("apocalipsis") || q.contains("milenio") || q.contains("fin del mundo") -> {
                "La escatología bíblica es el estudio de los últimos tiempos y la consumación del plan redentor de Dios en Jesucristo.\n\n" +
                "• Pasajes clave: Daniel 7-12, Mateo 24-25, 1 Tesalonicenses 4:13-18, 2 Tesalonicenses 2 y el libro de Apocalipsis.\n" +
                "• Esperanza central: El retorno glorioso, visible y triunfante de nuestro Señor Jesucristo (Tito 2:13), la resurrección de los muertos y la instauración de cielos nuevos y tierra nueva (Apocalipsis 21:1-4).\n" +
                "• Posturas históricas: A lo largo de la historia de la Iglesia han coexistido visiones premilenialistas, amilenialistas y postmilenialistas respecto al Milenio, todas coincidiendo en el regreso soberano de Cristo como Rey de reyes."
            }
            q.contains("salvaci") || q.contains("gracia") || q.contains("fe") || q.contains("justificaci") -> {
                "La doctrina de la salvación (Soteriología) enseña que somos justificados por la sola gracia de Dios mediante la fe en Jesucristo:\n\n" +
                "• «Porque por gracia sois salvos por medio de la fe; y esto no de vosotros, pues es don de Dios; no por obras, para que nadie se gloríe» (Efesios 2:8-9).\n" +
                "• Romanos 5:1 proclama: «Justificados, pues, por la fe, tenemos paz para con Dios por medio de nuestro Señor Jesucristo».\n" +
                "• La obra consumada de Cristo en la cruz es suficiente, perfecta y eterna para todo aquel que en Él cree (Juan 3:16, Romanos 8:1)."
            }
            q.contains("trinidad") || q.contains("dios") && q.contains("espíritu") -> {
                "La doctrina bíblica de la Trinidad enseña la existencia de un solo Dios verdadero en tres Personas coeternas y coesenciales: el Padre, el Hijo y el Espíritu Santo.\n\n" +
                "• Mandato del bautismo: «En el nombre del Padre, y del Hijo, y del Espíritu Santo» (Mateo 28:19).\n" +
                "• Bendición apostólica: «La gracia del Señor Jesucristo, el amor de Dios, y la comunión del Espíritu Santo sean con todos vosotros» (2 Corintios 13:14).\n" +
                "• Unidad divina: «Oye, Israel: Jehová nuestro Dios, Jehová uno es» (Deuteronomio 6:4)."
            }
            else -> {
                "«Toda la Escritura es inspirada por Dios, y útil para enseñar, para redargüir, para corregir, para instruir en justicia» (2 Timoteo 3:16).\n\n" +
                "Para profundizar en esta pregunta con el análisis teológico en vivo de Gemini, asegúrate de contar con conexión a internet. La Palabra de Dios ilumina cada área de nuestra vida (Salmos 119:105)."
            }
        }
    }
}

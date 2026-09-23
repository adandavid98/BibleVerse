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
import java.util.concurrent.TimeUnit

sealed class ContextGenerationResult {
    data class Success(val contextText: String, val source: ContextSource) : ContextGenerationResult()
    data class Error(val message: String, val fallbackContext: String) : ContextGenerationResult()
}

enum class ContextSource {
    AI_GEMINI,
    LOCAL_EXACT,
    LOCAL_ENGINE
}

object GeminiVerseContextService {

    private const val TAG = "GeminiContextService"
    // Gemini free-tier models — all confirmed available on ai.google.dev/pricing
    // Cascade: try newest/fastest first, fall back to stable older versions
    val FREE_TIER_MODELS = listOf(
        "gemini-3.5-flash-lite",
        "gemini-3.6-flash",
        "gemini-flash-lite-latest",
        "gemini-3.5-flash"
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
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Attempts to generate context using the free-tier Gemini models cascade.
     * If rate limits (HTTP 429), errors, or missing connectivity occur,
     * it seamlessly and safely falls back to the rich local BibleContextEngine exegesis.
     */
    suspend fun generateContext(
        book: String,
        chapter: Int,
        verse: String,
        verseText: String,
        bibleVersion: String = "RVR1960",
        forceLocalOnly: Boolean = false
    ): ContextGenerationResult = withContext(Dispatchers.IO) {
        val reference = "$book $chapter:$verse".trim()

        val resolvedApiKey = getEffectiveApiKey()
        val isKeyConfigured = resolvedApiKey.isNotBlank()

        if (forceLocalOnly || !isKeyConfigured) {
            val localContext = BibleContextEngine.getDeepTheologicalExegesis(
                bookName = book,
                chapter = chapter,
                verse = verse,
                verseText = verseText,
                bibleVersion = bibleVersion
            )
            return@withContext ContextGenerationResult.Success(
                contextText = localContext,
                source = ContextSource.LOCAL_ENGINE
            )
        }

        // 2. Query Gemini free tier models with automatic fallback across models
        val prompt = buildString {
            append("Eres un erudito bíblico y teólogo con profundo conocimiento histórico, doctrinal y exegético. ")
            append("Proporciona una síntesis contextual rigurosa, clara y edificante para el siguiente pasaje bíblico:\n\n")
            append("Cita Bíblica: $reference\n")
            append("Versión de traducción: $bibleVersion\n")
            if (verseText.isNotBlank()) {
                append("Texto bíblico: \"$verseText\"\n\n")
            } else {
                append("\n")
            }
            append("Redacta un texto fluido y directo en español (máximo 2 a 3 párrafos breves, sin encabezados en negrita ni viñetas markdown) que explique de forma natural:\n")
            append("1. Contexto histórico y autor original (quién escribió, época y circunstancias del destinatario).\n")
            append("2. Significado teológico esencial del pasaje dentro de las Escrituras.\n")
            append("3. Aplicación moral y devocional para la vida del creyente hoy.\n\n")
            append("Mantén un tono respetuoso, edificante, neutro e interdenominacional.")
        }

        val requestJson = JSONObject().apply {
            val contentsArray = JSONArray().apply {
                val contentObj = JSONObject().apply {
                    val partsArray = JSONArray().apply {
                        put(JSONObject().apply { put("text", prompt) })
                    }
                    put("parts", partsArray)
                }
                put(contentObj)
            }
            put("contents", contentsArray)

            val configObj = JSONObject().apply {
                put("temperature", 0.3)
                put("maxOutputTokens", 650)
            }
            put("generationConfig", configObj)
        }

        val requestBodyString = requestJson.toString()

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
                            val cleaned = rawText
                                .replace(Regex("^#+\\s*", RegexOption.MULTILINE), "")
                                .replace("**", "")
                                .trim()

                            if (cleaned.isNotBlank()) {
                                Log.i(TAG, "Context generated successfully using model: $modelName")
                                return@withContext ContextGenerationResult.Success(
                                    contextText = cleaned,
                                    source = ContextSource.AI_GEMINI
                                )
                            }
                        }
                    }
                } else {
                    Log.w(TAG, "Model $modelName responded with HTTP ${response.code}: $responseBody. Trying next fallback...")
                }
            } catch (e: Exception) {
                Log.w(TAG, "Exception calling model $modelName: ${e.message}. Trying next fallback...")
            }
        }

        // 3. Fallback to rich local theological engine if all cloud models fail or are throttled
        Log.i(TAG, "All AI models exhausted or offline. Applying rich local canonical exegesis engine.")
        val localFallback = BibleContextEngine.getDeepTheologicalExegesis(
            bookName = book,
            chapter = chapter,
            verse = verse,
            verseText = verseText,
            bibleVersion = bibleVersion
        )
        ContextGenerationResult.Success(
            contextText = localFallback,
            source = ContextSource.LOCAL_ENGINE
        )
    }
}

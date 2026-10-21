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
import java.io.BufferedReader
import java.io.InputStreamReader
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
    // Gemini free-tier models — prioritizing fast low-latency models for instant response
    val FREE_TIER_MODELS = listOf(
        "gemini-3.1-flash-lite",
        "gemini-flash-lite-latest",
        "gemini-3.5-flash-lite",
        "gemini-3.8-flash",
        "gemini-3.7-flash",
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
                put("maxOutputTokens", 1500)
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

    /**
     * Explains selected verses in exactly two concise paragraphs.
     * Uses SSE streaming (streamGenerateContent) to stream tokens in real-time,
     * providing instantaneous feedback identical to the chatbot experience.
     */
    suspend fun explainVersesStream(
        book: String,
        chapter: Int,
        verseRange: String,
        verseText: String,
        bibleVersion: String = "RVR1960",
        forceLocalOnly: Boolean = false,
        onChunk: suspend (String) -> Unit
    ): ContextGenerationResult = withContext(Dispatchers.IO) {
        val reference = if (verseRange.isBlank()) "$book $chapter".trim() else "$book $chapter:$verseRange".trim()

        val resolvedApiKey = getEffectiveApiKey()
        val isKeyConfigured = resolvedApiKey.isNotBlank()

        if (forceLocalOnly || !isKeyConfigured) {
            val localContext = BibleContextEngine.getDeepTheologicalExegesis(
                bookName = book,
                chapter = chapter,
                verse = verseRange,
                verseText = verseText,
                bibleVersion = bibleVersion
            )
            onChunk(localContext)
            return@withContext ContextGenerationResult.Success(
                contextText = localContext,
                source = ContextSource.LOCAL_ENGINE
            )
        }

        val prompt = buildString {
            append("Eres un pedagogo y erudito bíblico. Explica de forma clara, directa, accesible y edificante el siguiente pasaje bíblico para arrojar luz y facilitar su comprensión:\n\n")
            append("Cita Bíblica: $reference\n")
            append("Versión de traducción: $bibleVersion\n")
            if (verseText.isNotBlank()) {
                append("Texto bíblico:\n\"$verseText\"\n\n")
            } else {
                append("\n")
            }
            append("Instrucciones obligatorias de formato:\n")
            append("- Escribe exactamente DOS párrafos breves en español.\n")
            append("- NO uses encabezados, NO uses títulos en negrita, NO uses listas ni viñetas markdown. Solo texto fluido.\n")
            append("- Primer párrafo (Contexto y situación): Explica de forma concisa quién habla o escribe, a quién se dirige, en qué momento histórico/cultural y qué está ocurriendo en el pasaje.\n")
            append("- Segundo párrafo (Significado y aplicación): Explica el significado esencial del pasaje, aclarando posibles dudas o términos difíciles, y qué principio espiritual enseña para la vida de hoy.\n")
            append("- Mantén un tono respetuoso, neutro, edificante e interdenominacional.")
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
                put("maxOutputTokens", 1200)
            }
            put("generationConfig", configObj)
        }

        val requestBodyString = requestJson.toString()
        val fullAccumulatedText = StringBuilder()

        for (modelName in FREE_TIER_MODELS) {
            var modelEmittedChunk = false
            try {
                val requestBody = requestBodyString.toRequestBody("application/json; charset=utf-8".toMediaType())
                val url = "$BASE_URL/$modelName:streamGenerateContent?alt=sse&key=$resolvedApiKey"

                val httpRequest = Request.Builder()
                    .url(url)
                    .addHeader("x-goog-api-key", resolvedApiKey)
                    .addHeader("Accept", "text/event-stream")
                    .post(requestBody)
                    .build()

                val response = client.newCall(httpRequest).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body
                    if (responseBody != null) {
                        val reader = BufferedReader(InputStreamReader(responseBody.byteStream(), Charsets.UTF_8))
                        var line: String?
                        while (reader.readLine().also { line = it } != null) {
                            val currentLine = line?.trim() ?: continue
                            if (currentLine.startsWith("data:")) {
                                val jsonStr = currentLine.removePrefix("data:").trim()
                                if (jsonStr.isNotEmpty() && jsonStr != "[DONE]") {
                                    try {
                                        val jsonObj = JSONObject(jsonStr)
                                        val candidates = jsonObj.optJSONArray("candidates")
                                        if (candidates != null && candidates.length() > 0) {
                                            val firstCandidate = candidates.getJSONObject(0)
                                            val content = firstCandidate.optJSONObject("content")
                                            val parts = content?.optJSONArray("parts")
                                            if (parts != null && parts.length() > 0) {
                                                for (i in 0 until parts.length()) {
                                                    val part = parts.optJSONObject(i) ?: continue
                                                    val isThought = part.optBoolean("thought", false)
                                                    if (!isThought) {
                                                        val chunkText = part.optString("text", "")
                                                        if (chunkText.isNotEmpty()) {
                                                            modelEmittedChunk = true
                                                            val cleanChunk = chunkText.replace("**", "")
                                                            fullAccumulatedText.append(cleanChunk)
                                                            onChunk(cleanChunk)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (e: Exception) {
                                        Log.w(TAG, "Error parsing SSE chunk: ${e.message}")
                                    }
                                }
                            }
                        }
                    }

                    if (modelEmittedChunk && fullAccumulatedText.isNotBlank()) {
                        val cleaned = fullAccumulatedText.toString()
                            .replace(Regex("^#+\\s*", RegexOption.MULTILINE), "")
                            .replace("**", "")
                            .trim()
                        Log.i(TAG, "Explanation streamed successfully using model: $modelName")
                        return@withContext ContextGenerationResult.Success(
                            contextText = cleaned,
                            source = ContextSource.AI_GEMINI
                        )
                    }
                } else {
                    Log.w(TAG, "Model $modelName stream responded with HTTP ${response.code}. Trying next fallback...")
                }
            } catch (e: Exception) {
                Log.w(TAG, "Exception streaming with model $modelName: ${e.message}. Trying next fallback...")
                if (modelEmittedChunk && fullAccumulatedText.isNotBlank()) {
                    val cleaned = fullAccumulatedText.toString()
                        .replace(Regex("^#+\\s*", RegexOption.MULTILINE), "")
                        .replace("**", "")
                        .trim()
                    return@withContext ContextGenerationResult.Success(
                        contextText = cleaned,
                        source = ContextSource.AI_GEMINI
                    )
                }
            }
        }

        Log.i(TAG, "All AI models exhausted or offline. Applying rich local canonical exegesis engine.")
        val localFallback = BibleContextEngine.getDeepTheologicalExegesis(
            bookName = book,
            chapter = chapter,
            verse = verseRange,
            verseText = verseText,
            bibleVersion = bibleVersion
        )
        if (fullAccumulatedText.isEmpty()) {
            onChunk(localFallback)
        }
        ContextGenerationResult.Success(
            contextText = localFallback,
            source = ContextSource.LOCAL_ENGINE
        )
    }

    /**
     * Non-streaming wrapper for explainVerses.
     */
    suspend fun explainVerses(
        book: String,
        chapter: Int,
        verseRange: String,
        verseText: String,
        bibleVersion: String = "RVR1960",
        forceLocalOnly: Boolean = false
    ): ContextGenerationResult {
        return explainVersesStream(
            book = book,
            chapter = chapter,
            verseRange = verseRange,
            verseText = verseText,
            bibleVersion = bibleVersion,
            forceLocalOnly = forceLocalOnly,
            onChunk = { /* no-op */ }
        )
    }
}

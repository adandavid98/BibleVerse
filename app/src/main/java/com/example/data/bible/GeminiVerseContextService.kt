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
    // Free tier friendly cascade: primary fast model -> lightweight flash lite -> flash latest -> local engine
    private val FREE_TIER_MODELS = listOf(
        "gemini-3.5-flash",
        "gemini-3.1-flash-lite-preview",
        "gemini-flash-latest"
    )
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models"

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Attempts to generate context using the free-tier Gemini models cascade
     * (gemini-3.5-flash -> gemini-3.1-flash-lite-preview -> gemini-flash-latest).
     * If rate limits (HTTP 429), errors, or missing connectivity occur,
     * it seamlessly and safely falls back to the rich local BibleContextEngine.
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

        // 1. If forced local or no API key configured, use local engine immediately
        val apiKey = BuildConfig.GEMINI_API_KEY.trim()
        val isKeyConfigured = apiKey.isNotBlank() &&
                apiKey != "MY_GEMINI_API_KEY" &&
                apiKey != "MY_NEW_API_KEY_DEFAULT_VALUE"

        if (forceLocalOnly || !isKeyConfigured) {
            val localContext = BibleContextEngine.getLocalContext(book, chapter, verse, verseText)
            val source = if (BibleContextEngine.findCuratedContext(book, chapter, verse) != null) {
                ContextSource.LOCAL_EXACT
            } else {
                ContextSource.LOCAL_ENGINE
            }
            return@withContext ContextGenerationResult.Success(
                contextText = localContext,
                source = source
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
                val url = "$BASE_URL/$modelName:generateContent?key=$apiKey"

                val httpRequest = Request.Builder()
                    .url(url)
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
                            val rawText = parts.getJSONObject(0).optString("text", "")
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

        // 3. Fallback to local theological engine if all cloud models fail or are throttled
        Log.i(TAG, "All AI models exhausted or offline. Applying rich local canonical engine.")
        val localFallback = BibleContextEngine.getLocalContext(book, chapter, verse, verseText)
        ContextGenerationResult.Success(
            contextText = localFallback,
            source = ContextSource.LOCAL_ENGINE
        )
    }
}

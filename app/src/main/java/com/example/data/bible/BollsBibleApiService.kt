package com.example.data.bible

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.util.concurrent.TimeUnit

data class BollsVerseDto(
    val verseNumber: Int,
    val text: String,
    val comment: String? = null
)

object BollsBibleApiService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(12, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    suspend fun fetchChapter(version: String, bookId: Int, chapter: Int): List<BollsVerseDto>? = withContext(Dispatchers.IO) {
        val slug = mapVersionToSlug(version)
        val url = "https://bolls.life/get-chapter/$slug/$bookId/$chapter/"

        try {
            val request = Request.Builder()
                .url(url)
                .header("User-Agent", "BibleVerseApp/1.2 (Android)")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext null
                val bodyString = response.body?.string() ?: return@withContext null
                val jsonArray = JSONArray(bodyString)

                val result = mutableListOf<BollsVerseDto>()
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val verseNum = obj.optInt("verse", i + 1)
                    val rawText = obj.optString("text", "")
                    val comment = obj.optString("comment", null)

                    // Clean raw HTML entities if any
                    val cleanText = sanitizeVerseText(rawText)

                    result.add(
                        BollsVerseDto(
                            verseNumber = verseNum,
                            text = cleanText,
                            comment = comment
                        )
                    )
                }
                result
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun sanitizeVerseText(text: String): String {
        return text
            .replace(Regex("<[^>]*>"), "") // Remove HTML tags
            .replace("&nbsp;", " ")
            .replace("&quot;", "\"")
            .replace("&amp;", "&")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&#39;", "'")
            .trim()
    }

    fun mapVersionToSlug(version: String): String {
        return when (version.uppercase().trim()) {
            "RVR1960", "RV1960", "REINA-VALERA 1960" -> "RV1960"
            "NVI", "NUEVA VERSIÓN INTERNACIONAL" -> "NVI"
            "NTV", "NUEVA TRADUCCIÓN VIVIENTE" -> "NTV"
            "NBLA", "NUEVA BIBLIA DE LAS AMÉRICAS" -> "LBLA"
            "TLA", "TRADUCCIÓN EN LENGUAJE ACTUAL" -> "PDT"
            "LBLA", "LA BIBLIA DE LAS AMÉRICAS" -> "LBLA"
            "PDT", "PALABRA DE DIOS PARA TODOS" -> "PDT"
            "BTX3", "BIBLIA TEXTUAL" -> "BTX3"
            "RV2004" -> "RV2004"
            else -> "RV1960"
        }
    }
}

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
        if (slug.isBlank()) return@withContext null
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

    private val titleBlockRegex = Regex(
        "(?is)<p[^>]*align\\s*=\\s*['\"]?center['\"]?[^>]*>.*?</p>|<h[1-6][^>]*>.*?</h[1-6]>"
    )

    /**
     * Cleans a raw Bolls verse. Some translations (e.g. PDT) embed the section title inside the
     * first verse of a section as `<p align='center'><b><i>Title</i></b></p>`; stripping only the tags
     * would glue the title to the verse text, so the whole title block is removed first.
     */
    fun sanitizeVerseText(text: String): String {
        return BibleTextSanitizer.sanitize(text)
    }

    fun mapVersionToSlug(version: String): String {
        return when (version.uppercase().trim()) {
            "RVR1960", "RV1960", "REINA-VALERA 1960" -> "RV1960"
            "NVI", "NUEVA VERSIÓN INTERNACIONAL" -> "NVI"
            "NTV", "NUEVA TRADUCCIÓN VIVIENTE" -> "NTV"
            "NBLA", "NUEVA BIBLIA DE LAS AMÉRICAS" -> "LBLA"
            "LBLA", "LA BIBLIA DE LAS AMÉRICAS" -> "LBLA"
            "PDT", "PALABRA DE DIOS PARA TODOS" -> "PDT"
            "BTX3", "BIBLIA TEXTUAL" -> "BTX3"
            "RV2004" -> "RV2004"
            else -> ""
        }
    }
}

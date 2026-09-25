package com.example.data.bible

import com.example.data.local.BibleReaderDao
import com.example.data.model.BibleReaderVerseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.util.concurrent.TimeUnit

sealed interface VersionDownloadState {
    object Idle : VersionDownloadState
    data class Downloading(val progressPercent: Int, val message: String) : VersionDownloadState
    object Downloaded : VersionDownloadState
    data class Error(val error: String) : VersionDownloadState
}

object OfflineBibleDownloadManager {

    private val client = OkHttpClient.Builder()
        .connectTimeout(25, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    private val _downloadStates = MutableStateFlow<Map<String, VersionDownloadState>>(
        mapOf("RVR1960" to VersionDownloadState.Downloaded)
    )
    val downloadStates: StateFlow<Map<String, VersionDownloadState>> = _downloadStates.asStateFlow()

    suspend fun isVersionOfflineReady(dao: BibleReaderDao, versionCode: String): Boolean = withContext(Dispatchers.IO) {
        if (versionCode.equals("RVR1960", ignoreCase = true) || versionCode.equals("RV1960", ignoreCase = true)) {
            return@withContext true
        }
        val count = dao.getVerseCountForVersion(versionCode)
        count > 5000
    }

    suspend fun refreshStatuses(dao: BibleReaderDao) = withContext(Dispatchers.IO) {
        val updated = mutableMapOf<String, VersionDownloadState>()
        updated["RVR1960"] = VersionDownloadState.Downloaded

        for (version in BibleCatalog.versions) {
            val code = version.code
            if (code.equals("RVR1960", ignoreCase = true)) continue

            val count = dao.getVerseCountForVersion(code)
            if (count > 5000) {
                updated[code] = VersionDownloadState.Downloaded
            } else if (_downloadStates.value[code] !is VersionDownloadState.Downloading) {
                updated[code] = VersionDownloadState.Idle
            }
        }
        _downloadStates.update { current ->
            current + updated
        }
    }

    suspend fun downloadVersion(dao: BibleReaderDao, versionCode: String) = withContext(Dispatchers.IO) {
        val codeUpper = versionCode.uppercase().trim()
        if (codeUpper == "RVR1960" || codeUpper == "RV1960") {
            _downloadStates.update { it + (codeUpper to VersionDownloadState.Downloaded) }
            return@withContext
        }

        val slug = BollsBibleApiService.mapVersionToSlug(codeUpper)
        val url = "https://bolls.life/static/translations/" + slug + ".json"

        _downloadStates.update {
            it + (codeUpper to VersionDownloadState.Downloading(5, "Conectando al servidor..."))
        }

        try {
            val request = Request.Builder()
                .url(url)
                .header("User-Agent", "BibleVerse/1.7 (Android)")
                .build()

            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                _downloadStates.update {
                    it + (codeUpper to VersionDownloadState.Error("Error HTTP " + response.code))
                }
                return@withContext
            }

            _downloadStates.update {
                it + (codeUpper to VersionDownloadState.Downloading(25, "Descargando texto completo..."))
            }

            val bodyString = response.body?.string() ?: throw IllegalStateException("Respuesta vacía del servidor")
            val jsonArray = JSONArray(bodyString)
            val totalItems = jsonArray.length()

            if (totalItems == 0) {
                _downloadStates.update {
                    it + (codeUpper to VersionDownloadState.Error("No se encontraron versículos"))
                }
                return@withContext
            }

            _downloadStates.update {
                it + (codeUpper to VersionDownloadState.Downloading(45, "Indexando " + totalItems + " versículos..."))
            }

            dao.deleteVersesForVersion(codeUpper)

            val batchSize = 1000
            val batch = mutableListOf<BibleReaderVerseEntity>()

            for (i in 0 until totalItems) {
                val obj = jsonArray.getJSONObject(i)
                val bookId = obj.optInt("book", 1)
                val chapter = obj.optInt("chapter", 1)
                val verseNum = obj.optInt("verse", 1)
                val rawText = obj.optString("text", "")

                val cleanText = sanitizeVerseText(rawText)
                val isJesus = WordsOfJesusCatalog.isWordsOfJesus(bookId, chapter, verseNum)
                val heading = BiblePericopesCatalog.getHeading(
                    bookId = bookId,
                    chapter = chapter,
                    verse = verseNum,
                    version = codeUpper
                )

                batch.add(
                    BibleReaderVerseEntity(
                        bookId = bookId,
                        chapter = chapter,
                        verseNumber = verseNum,
                        text = cleanText,
                        bibleVersion = codeUpper,
                        sectionHeading = heading,
                        isRedLetter = isJesus
                    )
                )

                if (batch.size >= batchSize || i == totalItems - 1) {
                    dao.insertVerses(batch)
                    batch.clear()

                    val pct = 50 + ((i.toFloat() / totalItems.toFloat()) * 48).toInt()
                    _downloadStates.update {
                        it + (codeUpper to VersionDownloadState.Downloading(pct, "Guardando versículos (" + pct + "%)..."))
                    }
                }
            }

            _downloadStates.update {
                it + (codeUpper to VersionDownloadState.Downloaded)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _downloadStates.update {
                it + (codeUpper to VersionDownloadState.Error("Fallo: " + (e.localizedMessage ?: "Error de red")))
            }
        }
    }

    private fun sanitizeVerseText(text: String): String {
        return text
            .replace(Regex("<[^>]*>"), "")
            .replace("&nbsp;", " ")
            .replace("&quot;", "\"")
            .replace("&amp;", "&")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&#39;", "'")
            .trim()
    }
}

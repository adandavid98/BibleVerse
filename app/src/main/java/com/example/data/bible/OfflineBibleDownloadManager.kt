package com.example.data.bible

import android.content.Context
import android.util.Log
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
        mapOf(
            "RVR1960" to VersionDownloadState.Downloaded,
            "NBLA" to VersionDownloadState.Downloaded,
            "TLA" to VersionDownloadState.Downloaded,
            "DHH" to VersionDownloadState.Downloaded,
            "DHH94PC" to VersionDownloadState.Downloaded,
            "NVI" to VersionDownloadState.Downloaded
        )
    )
    val downloadStates: StateFlow<Map<String, VersionDownloadState>> = _downloadStates.asStateFlow()

    suspend fun isVersionOfflineReady(dao: BibleReaderDao, versionCode: String): Boolean = withContext(Dispatchers.IO) {
        val norm = OfflineBibleManager.normalizeVersion(versionCode)
        if (OfflineBibleManager.isAssetVersion(norm)) {
            return@withContext true
        }
        val count = dao.getVerseCountForVersion(norm)
        count > 5000
    }

    suspend fun refreshStatuses(dao: BibleReaderDao) = withContext(Dispatchers.IO) {
        val updated = mutableMapOf<String, VersionDownloadState>()

        for (version in BibleCatalog.versions) {
            val code = OfflineBibleManager.normalizeVersion(version.code)
            if (OfflineBibleManager.isAssetVersion(code)) {
                updated[version.code] = VersionDownloadState.Downloaded
            } else {
                val count = dao.getVerseCountForVersion(code)
                if (count > 5000) {
                    updated[version.code] = VersionDownloadState.Downloaded
                } else if (_downloadStates.value[version.code] !is VersionDownloadState.Downloading) {
                    updated[version.code] = VersionDownloadState.Idle
                }
            }
        }
        _downloadStates.update { current ->
            current + updated
        }
    }

    fun isAssetVersion(versionCode: String): Boolean =
        OfflineBibleManager.isAssetVersion(versionCode)

    suspend fun downloadVersion(dao: BibleReaderDao, versionCode: String) = withContext(Dispatchers.IO) {
        val codeUpper = OfflineBibleManager.normalizeVersion(versionCode)

        // If this version is bundled in APK assets (RVR1960, TLA, DHH, NBLA), it's immediately ready!
        if (OfflineBibleManager.isAssetVersion(codeUpper)) {
            val appCtx = com.example.BibleApplication.instance
            OfflineBibleManager.ensureReady(appCtx, codeUpper)
            _downloadStates.update { it + (versionCode to VersionDownloadState.Downloaded) }
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

                val cleanText = BibleTextSanitizer.sanitize(rawText)
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
            Log.e("OfflineDownloadMgr", "Error descargando versión $codeUpper: ${e.message}", e)
            _downloadStates.update {
                it + (codeUpper to VersionDownloadState.Error("Error: ${e.message}"))
            }
        }
    }
}

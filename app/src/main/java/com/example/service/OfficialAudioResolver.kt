package com.example.service

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

/**
 * Resolves and caches official pre-recorded human audio tracks by chapter (exact YouVersion CDN).
 * Enables seamless, continuous "song-like" playback with 0 rate-limits and instant seek/pause.
 */
object OfficialAudioResolver {

    private const val TAG = "OfficialAudioResolver"

    private val USFM_CODES = listOf(
        "GEN", "EXO", "LEV", "NUM", "DEU", "JOS", "JDG", "RUT", "1SA", "2SA",
        "1KI", "2KI", "1CH", "2CH", "EZR", "NEH", "EST", "JOB", "PSA", "PRO",
        "ECC", "SNG", "ISA", "JER", "LAM", "EZK", "DAN", "HOS", "JOL", "AMO",
        "OBA", "JON", "MIC", "NAM", "HAB", "ZEP", "HAG", "ZEC", "MAL",
        "MAT", "MRK", "LUK", "JHN", "ACT", "ROM", "1CO", "2CO", "GAL", "EPH",
        "PHP", "COL", "1TH", "2TH", "1TI", "2TI", "TIT", "PHM", "HEB", "JAS",
        "1PE", "2PE", "1JN", "2JN", "3JN", "JUD", "REV"
    )

    private val VERSION_IDS = mapOf(
        "RVR1960" to "149",
        "NVI" to "128",
        "DHH" to "414",
        "NTV" to "127"
    )

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .followRedirects(true)
            .retryOnConnectionFailure(true)
            .build()
    }

    private fun getCacheDirectory(context: Context): File {
        val dir = File(context.cacheDir, "bible_audio_official")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    fun getLocalCacheFile(context: Context, version: String, bookOrder: Int, chapter: Int): File {
        val safeVersion = version.replace("[^a-zA-Z0-9_]".toRegex(), "")
        return File(getCacheDirectory(context), "${safeVersion}_b${bookOrder}_c${chapter}.mp3")
    }

    fun getUsfmCode(bookOrder: Int): String {
        return if (bookOrder in 1..USFM_CODES.size) {
            USFM_CODES[bookOrder - 1]
        } else {
            "GEN"
        }
    }

    /**
     * Resolves the official chapter audio URL or returns local cache.
     * Starts background download without blocking playback.
     */
    suspend fun resolveChapterAudio(
        context: Context,
        version: String,
        bookOrder: Int,
        chapter: Int
    ): String? = withContext(Dispatchers.IO) {
        val cacheFile = getLocalCacheFile(context, version, bookOrder, chapter)
        if (cacheFile.exists() && cacheFile.length() > 50000L) {
            return@withContext cacheFile.absolutePath
        }

        val versionId = VERSION_IDS[version.uppercase()] ?: VERSION_IDS["RVR1960"]!!
        val usfm = getUsfmCode(bookOrder)
        val safeVerName = if (VERSION_IDS.containsKey(version.uppercase())) version.uppercase() else "RVR1960"
        val pageUrl = "https://www.bible.com/bible/$versionId/$usfm.$chapter.$safeVerName"

        try {
            val pageRequest = Request.Builder()
                .url(pageUrl)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
                .header("Accept-Language", "es-ES,es;q=0.9,en;q=0.8")
                .header("Sec-Ch-Ua", "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"")
                .header("Sec-Ch-Ua-Mobile", "?0")
                .header("Sec-Ch-Ua-Platform", "\"Windows\"")
                .header("Sec-Fetch-Dest", "document")
                .header("Sec-Fetch-Mode", "navigate")
                .header("Sec-Fetch-Site", "none")
                .header("Sec-Fetch-User", "?1")
                .header("Upgrade-Insecure-Requests", "1")
                .build()

            val pageResponse = httpClient.newCall(pageRequest).execute()
            if (!pageResponse.isSuccessful) {
                Log.w(TAG, "Failed to load chapter page: HTTP ${pageResponse.code}")
                return@withContext null
            }

            val html = pageResponse.body?.string() ?: return@withContext null

            // Regex extraction of the MP3 stream URL (handles both // and \/\/)
            val pattern = Pattern.compile("format_mp3_32k[\":\\s\\\\]+((?:https?:)?(?://|\\\\/\\\\/)[^\"'\\s]+?\\.mp3[^\"'\\s]*?)[\"']")
            val matcher = pattern.matcher(html)
            val audioUrl = if (matcher.find()) {
                val rawMatch = matcher.group(1) ?: return@withContext null
                val cleanUrl = rawMatch.replace("\\/", "/").replace("\\", "")
                if (cleanUrl.startsWith("//")) {
                    "https:$cleanUrl"
                } else if (!cleanUrl.startsWith("http")) {
                    "https://$cleanUrl"
                } else {
                    cleanUrl
                }
            } else {
                Log.w(TAG, "No audio URL found for $usfm.$chapter ($version)")
                return@withContext null
            }

            // Trigger non-blocking background download to cache for offline use
            CoroutineScope(Dispatchers.IO).launch {
                downloadAndCacheAudio(context, audioUrl, cacheFile)
            }

            // Return live streaming CDN URL immediately so playback starts instantly
            return@withContext audioUrl
        } catch (e: Exception) {
            Log.w(TAG, "Error resolving official audio for $bookOrder:$chapter: ${e.message}")
            return@withContext null
        }
    }

    private fun downloadAndCacheAudio(context: Context, audioUrl: String, destinationFile: File) {
        if (destinationFile.exists() && destinationFile.length() > 50000L) return
        try {
            val tempFile = File(destinationFile.parentFile, "${destinationFile.name}.tmp")
            val downloadRequest = Request.Builder()
                .url(audioUrl)
                .header("User-Agent", "Mozilla/5.0")
                .build()

            val downloadResponse = httpClient.newCall(downloadRequest).execute()
            if (downloadResponse.isSuccessful) {
                downloadResponse.body?.byteStream()?.use { input ->
                    FileOutputStream(tempFile).use { output ->
                        input.copyTo(output)
                        output.flush()
                    }
                }
                if (tempFile.exists() && tempFile.length() > 50000L) {
                    tempFile.renameTo(destinationFile)
                    cleanOldCacheFilesIfNeeded(context)
                } else {
                    tempFile.delete()
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "Could not cache audio file in background: ${e.message}")
        }
    }

    private fun cleanOldCacheFilesIfNeeded(context: Context) {
        try {
            val cacheDir = getCacheDirectory(context)
            val files = cacheDir.listFiles() ?: return
            val maxSizeBytes = 300L * 1024L * 1024L // 300 MB

            var totalSize = files.sumOf { it.length() }
            if (totalSize > maxSizeBytes) {
                val sortedFiles = files.sortedBy { it.lastModified() }
                for (file in sortedFiles) {
                    val size = file.length()
                    if (file.delete()) {
                        totalSize -= size
                        if (totalSize < maxSizeBytes * 0.8) break
                    }
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error cleaning official audio cache", e)
        }
    }
}

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
 * Resolves and caches official pre-recorded human audio tracks by chapter (Faith Comes By Hearing / YouVersion CDN).
 * Ensures smooth streaming, instant offline playback when cached, and automatic cleaning of corrupted cache files.
 */
object OfficialAudioResolver {

    private const val TAG = "OfficialAudioResolver"
    private const val MIN_VALID_AUDIO_BYTES = 50000L // 50 KB minimum for a real MP3 file

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
        "NTV" to "127",
        "NBLA" to "103"
    )

    private val liveUrlCache = java.util.concurrent.ConcurrentHashMap<String, String>()

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .followRedirects(true)
            .retryOnConnectionFailure(true)
            .build()
    }

    fun getCacheDirectory(context: Context): File {
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
     * Cleans up incomplete or corrupted cache files (less than 50KB or temporary files).
     */
    fun cleanCorruptedCache(context: Context) {
        try {
            val cacheDir = getCacheDirectory(context)
            val files = cacheDir.listFiles() ?: return
            val now = System.currentTimeMillis()
            for (file in files) {
                val isStaleTmp = file.name.endsWith(".tmp") && (now - file.lastModified() > 60000L)
                val isCorruptMp3 = file.name.endsWith(".mp3") && file.length() < MIN_VALID_AUDIO_BYTES
                if (isStaleTmp || isCorruptMp3) {
                    file.delete()
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error cleaning corrupted cache", e)
        }
    }

    /**
     * Checks if the chapter audio is already downloaded and valid for offline listening.
     */
    fun isChapterCached(context: Context, version: String, bookOrder: Int, chapter: Int): Boolean {
        val cacheFile = getLocalCacheFile(context, version, bookOrder, chapter)
        return cacheFile.exists() && cacheFile.length() >= MIN_VALID_AUDIO_BYTES
    }

    /**
     * Resolves the official human chapter audio URL or returns the local cache if already present.
     * When streaming from online, initiates background download to save for offline use.
     */
    suspend fun resolveChapterAudio(
        context: Context,
        version: String,
        bookOrder: Int,
        chapter: Int
    ): String? = withContext(Dispatchers.IO) {
        // 1. Clean any corrupted remnants
        cleanCorruptedCache(context)

        // 2. Check local offline cache first
        val cacheFile = getLocalCacheFile(context, version, bookOrder, chapter)
        if (cacheFile.exists() && cacheFile.length() >= MIN_VALID_AUDIO_BYTES) {
            Log.i(TAG, "Playing from local offline cache: ${cacheFile.name} (${cacheFile.length()} bytes)")
            return@withContext cacheFile.absolutePath
        }

        val cacheKey = "${version.uppercase()}:$bookOrder:$chapter"
        val cachedLiveUrl = liveUrlCache[cacheKey]
        if (cachedLiveUrl != null) {
            Log.i(TAG, "Using in-memory cached audio URL: $cachedLiveUrl")
            CoroutineScope(Dispatchers.IO).launch {
                downloadAndCacheAudio(context, cachedLiveUrl, cacheFile)
            }
            return@withContext cachedLiveUrl
        }

        // 3. Resolve live audio stream URL dynamically at play time
        val versionId = VERSION_IDS[version.uppercase()] ?: VERSION_IDS["RVR1960"]!!
        val usfm = getUsfmCode(bookOrder)
        val safeVerName = if (VERSION_IDS.containsKey(version.uppercase())) version.uppercase() else "RVR1960"

        // Candidate URLs: /audio-bible/ is the official player endpoint, fallback to /bible/
        val candidateUrls = listOf(
            "https://www.bible.com/audio-bible/$versionId/$usfm.$chapter.$safeVerName",
            "https://www.bible.com/bible/$versionId/$usfm.$chapter.$safeVerName"
        )

        for (pageUrl in candidateUrls) {
            try {
                // Clean standard browser headers that pass through CDN without bot challenges
                val pageRequest = Request.Builder()
                    .url(pageUrl)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
                    .header("Accept-Language", "es-ES,es;q=0.9,en;q=0.8")
                    .header("Upgrade-Insecure-Requests", "1")
                    .build()

                val pageResponse = httpClient.newCall(pageRequest).execute()
                if (!pageResponse.isSuccessful) {
                    Log.w(TAG, "HTTP ${pageResponse.code} for $pageUrl")
                    continue
                }

                val html = pageResponse.body?.string() ?: continue

                var rawUrl: String? = null

                // Strategy 1: Direct CDN URL matching (YouVersion audio CDN)
                val cdnPattern = Pattern.compile("https?://audio-bible-cdn\\.youversionapi\\.com/[^\"'\\s<>]+\\.mp3[^\"'\\s<>]*")
                val cdnMatcher = cdnPattern.matcher(html)
                if (cdnMatcher.find()) {
                    rawUrl = cdnMatcher.group(0)
                }

                // Strategy 2: format_mp3_32k key in embedded script/JSON
                if (rawUrl == null) {
                    val keyPattern = Pattern.compile("format_mp3_32k[\":\\s\\\\]+((?:https?:)?(?://|\\\\/\\\\/)[^\"'\\s]+?\\.mp3[^\"'\\s]*?)[\"']")
                    val keyMatcher = keyPattern.matcher(html)
                    if (keyMatcher.find()) {
                        rawUrl = keyMatcher.group(1)
                    }
                }

                // Strategy 3: Any MP3 URL in response
                if (rawUrl == null) {
                    val anyMp3Pattern = Pattern.compile("((?:https?:)?(?://|\\\\/\\\\/)[^\"'\\s<>]+\\.mp3(?:\\?[^\"'\\s<>]*)?)")
                    val anyMp3Matcher = anyMp3Pattern.matcher(html)
                    if (anyMp3Matcher.find()) {
                        rawUrl = anyMp3Matcher.group(1)
                    }
                }

                if (!rawUrl.isNullOrBlank()) {
                    var cleanUrl = rawUrl
                        .replace("\\/", "/")
                        .replace("\\", "")
                        .replace("\"", "")
                        .replace("'", "")
                        .trim()

                    if (cleanUrl.startsWith("//")) {
                        cleanUrl = "https:$cleanUrl"
                    } else if (!cleanUrl.startsWith("http")) {
                        cleanUrl = "https://$cleanUrl"
                    }

                    Log.i(TAG, "Resolved live official human audio URL from $pageUrl: $cleanUrl")
                    liveUrlCache[cacheKey] = cleanUrl

                    // 4. Trigger non-blocking background download so it's stored permanently for offline playback
                    CoroutineScope(Dispatchers.IO).launch {
                        downloadAndCacheAudio(context, cleanUrl, cacheFile)
                    }

                    // Return live streaming CDN URL immediately so playback starts instantly
                    return@withContext cleanUrl
                }
            } catch (e: Exception) {
                Log.w(TAG, "Error resolving official audio from $pageUrl: ${e.message}")
            }
        }

        Log.w(TAG, "No audio URL found in response for $usfm.$chapter ($version)")
        return@withContext null
    }

    private fun downloadAndCacheAudio(context: Context, audioUrl: String, destinationFile: File) {
        if (destinationFile.exists() && destinationFile.length() >= MIN_VALID_AUDIO_BYTES) return
        try {
            val tempFile = File(destinationFile.parentFile, "${destinationFile.name}.tmp")
            val downloadRequest = Request.Builder()
                .url(audioUrl)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                .header("Accept", "*/*")
                .build()

            val downloadResponse = httpClient.newCall(downloadRequest).execute()
            if (downloadResponse.isSuccessful) {
                downloadResponse.body?.byteStream()?.use { input ->
                    FileOutputStream(tempFile).use { output ->
                        input.copyTo(output)
                        output.flush()
                    }
                }
                if (tempFile.exists() && tempFile.length() >= MIN_VALID_AUDIO_BYTES) {
                    if (destinationFile.exists()) destinationFile.delete()
                    val renamed = tempFile.renameTo(destinationFile)
                    if (renamed) {
                        Log.i(TAG, "Successfully cached audio for offline: ${destinationFile.name} (${destinationFile.length()} bytes)")
                        cleanOldCacheFilesIfNeeded(context)
                    } else {
                        tempFile.delete()
                    }
                } else {
                    tempFile.delete()
                }
            } else {
                tempFile.delete()
            }
        } catch (e: Exception) {
            Log.w(TAG, "Could not cache audio file in background: ${e.message}")
        }
    }

    private fun cleanOldCacheFilesIfNeeded(context: Context) {
        try {
            val cacheDir = getCacheDirectory(context)
            val files = cacheDir.listFiles() ?: return
            val maxSizeBytes = 500L * 1024L * 1024L // 500 MB limit

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

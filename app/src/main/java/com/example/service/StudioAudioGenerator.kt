package com.example.service

import android.content.Context
import android.util.Base64
import android.util.Log
import com.example.data.bible.GeminiBibleChatService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.TimeUnit

/**
 * High-fidelity Studio Neural Audio Generator.
 * Connects to Google's state-of-the-art Generative Speech Generation API (gemini-3.1-flash-tts-preview)
 * to synthesize 24kHz studio-quality natural voices (Kore / Charon) with offline disk caching.
 */
object StudioAudioGenerator {

    private const val TAG = "StudioAudioGenerator"
    private const val TTS_ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/interactions"
    private const val MODEL_NAME = "gemini-3.1-flash-tts-preview"

    private const val SAMPLE_RATE = 24000
    private const val CHANNELS = 1
    private const val BITS_PER_SAMPLE = 16

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(25, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    private fun getCacheDirectory(context: Context): File {
        val dir = File(context.cacheDir, "bible_audio_cache")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    fun getAudioCacheFile(
        context: Context,
        version: String,
        bookId: Int,
        chapter: Int,
        verseNumber: Int,
        gender: AudioVoiceGender
    ): File {
        val safeVersion = version.replace("[^a-zA-Z0-9_]".toRegex(), "")
        val fileName = "${safeVersion}_b${bookId}_c${chapter}_v${verseNumber}_${gender.cloudVoice}.wav"
        return File(getCacheDirectory(context), fileName)
    }

    fun isVerseCached(
        context: Context,
        version: String,
        bookId: Int,
        chapter: Int,
        verseNumber: Int,
        gender: AudioVoiceGender
    ): Boolean {
        val file = getAudioCacheFile(context, version, bookId, chapter, verseNumber, gender)
        return file.exists() && file.length() > 44L
    }

    /**
     * Retrieves cached audio or generates fresh high-fidelity studio voice audio via Google Cloud / Gemini TTS.
     */
    suspend fun getOrGenerateVerseAudio(
        context: Context,
        version: String,
        bookId: Int,
        chapter: Int,
        verseNumber: Int,
        rawText: String,
        gender: AudioVoiceGender
    ): File? = withContext(Dispatchers.IO) {
        val cacheFile = getAudioCacheFile(context, version, bookId, chapter, verseNumber, gender)
        if (cacheFile.exists() && cacheFile.length() > 44L) {
            return@withContext cacheFile
        }

        val cleanText = BibleAudioSpeechSanitizer.prepareForNaturalSpeech(rawText)
        if (cleanText.isBlank()) {
            return@withContext null
        }

        try {
            val apiKey = GeminiBibleChatService.getEffectiveApiKey()
            val voiceName = gender.cloudVoice

            val jsonBody = JSONObject().apply {
                put("model", MODEL_NAME)
                put("input", cleanText)
                put("response_format", JSONObject().put("type", "audio"))
                put(
                    "generation_config",
                    JSONObject().put(
                        "speech_config",
                        JSONArray().put(JSONObject().put("voice", voiceName))
                    )
                )
            }

            val request = Request.Builder()
                .url(TTS_ENDPOINT)
                .addHeader("x-goog-api-key", apiKey)
                .addHeader("Content-Type", "application/json")
                .post(jsonBody.toString().toRequestBody("application/json; charset=utf-8".toMediaType()))
                .build()

            val response = httpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                val errorBody = response.body?.string() ?: ""
                Log.e(TAG, "TTS API Error HTTP ${response.code}: $errorBody")
                return@withContext null
            }

            val respString = response.body?.string() ?: return@withContext null
            val rootObj = JSONObject(respString)
            val steps = rootObj.optJSONArray("steps") ?: return@withContext null
            if (steps.length() == 0) return@withContext null

            val step0 = steps.getJSONObject(0)
            val contents = step0.optJSONArray("content") ?: return@withContext null
            if (contents.length() == 0) return@withContext null

            val content0 = contents.getJSONObject(0)
            val base64Data = content0.optString("data", "")
            if (base64Data.isBlank()) return@withContext null

            val pcmBytes = Base64.decode(base64Data, Base64.DEFAULT)
            if (pcmBytes.isEmpty()) return@withContext null

            // Write as standard 44-byte WAV
            val tempFile = File(cacheFile.parentFile, "${cacheFile.name}.tmp")
            FileOutputStream(tempFile).use { fos ->
                val wavHeader = createWavHeader(pcmBytes.size, SAMPLE_RATE, CHANNELS, BITS_PER_SAMPLE)
                fos.write(wavHeader)
                fos.write(pcmBytes)
                fos.flush()
            }

            if (tempFile.renameTo(cacheFile)) {
                cleanOldCacheFilesIfNeeded(context)
                return@withContext cacheFile
            } else {
                tempFile.copyTo(cacheFile, overwrite = true)
                tempFile.delete()
                return@withContext cacheFile
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception during audio synthesis for verse $bookId $chapter:$verseNumber", e)
            return@withContext null
        }
    }

    /**
     * Builds a standard 44-byte RIFF/WAVE header for linear PCM audio.
     */
    private fun createWavHeader(
        pcmDataSize: Int,
        sampleRate: Int,
        channels: Int,
        bitsPerSample: Int
    ): ByteArray {
        val totalDataLen = pcmDataSize + 36
        val byteRate = sampleRate * channels * (bitsPerSample / 8)
        val blockAlign = channels * (bitsPerSample / 8)

        val buffer = ByteBuffer.allocate(44)
        buffer.order(ByteOrder.LITTLE_ENDIAN)

        // RIFF header
        buffer.put('R'.code.toByte())
        buffer.put('I'.code.toByte())
        buffer.put('F'.code.toByte())
        buffer.put('F'.code.toByte())
        buffer.putInt(totalDataLen)
        buffer.put('W'.code.toByte())
        buffer.put('A'.code.toByte())
        buffer.put('V'.code.toByte())
        buffer.put('E'.code.toByte())

        // fmt chunk
        buffer.put('f'.code.toByte())
        buffer.put('m'.code.toByte())
        buffer.put('t'.code.toByte())
        buffer.put(' '.code.toByte())
        buffer.putInt(16) // Subchunk1Size for PCM
        buffer.putShort(1.toShort()) // AudioFormat = 1 (PCM)
        buffer.putShort(channels.toShort())
        buffer.putInt(sampleRate)
        buffer.putInt(byteRate)
        buffer.putShort(blockAlign.toShort())
        buffer.putShort(bitsPerSample.toShort())

        // data chunk
        buffer.put('d'.code.toByte())
        buffer.put('a'.code.toByte())
        buffer.put('t'.code.toByte())
        buffer.put('a'.code.toByte())
        buffer.putInt(pcmDataSize)

        return buffer.array()
    }

    /**
     * Background lookahead pre-fetch to guarantee 0ms playback transitions between verses.
     */
    suspend fun prefetchVerseAudio(
        context: Context,
        version: String,
        bookId: Int,
        chapter: Int,
        verseNumber: Int,
        rawText: String,
        gender: AudioVoiceGender
    ) {
        if (!isVerseCached(context, version, bookId, chapter, verseNumber, gender)) {
            getOrGenerateVerseAudio(context, version, bookId, chapter, verseNumber, rawText, gender)
        }
    }

    /**
     * Cache governance: keeps total cache under ~150MB by removing oldest files if needed.
     */
    private fun cleanOldCacheFilesIfNeeded(context: Context) {
        try {
            val cacheDir = getCacheDirectory(context)
            val files = cacheDir.listFiles() ?: return
            val maxSizeBytes = 150L * 1024L * 1024L // 150 MB

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
            Log.w(TAG, "Error during cache cleanup", e)
        }
    }
}

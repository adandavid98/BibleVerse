package com.example.service

import android.content.Context
import android.util.Base64
import android.util.Log
import com.example.data.bible.GeminiVerseContextService
import com.example.data.bible.OfflineBibleManager
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
 * Ultra-realistic Neural Chapter Audio Synthesizer (Gemini AI TTS - NotebookLM style).
 * Generates continuous, high-definition human narration with natural breathing and emotional pacing,
 * saves to local disk for 100% offline playback, and supports both Male and Female voices.
 */
object GeminiAudioSynthesizer {

    private const val TAG = "GeminiAudioSynthesizer"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models"

    // Gemini TTS models cascade
    private val TTS_MODELS = listOf(
        "gemini-3.8-flash-tts",
        "gemini-3.8-flash-lite-tts",
        "gemini-2.5-flash-preview-tts",
        "gemini-3.1-flash-tts-preview"
    )

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(25, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    fun getCacheDirectory(context: Context): File {
        val dir = File(context.cacheDir, "bible_audio_ai")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    fun getLocalCacheFile(
        context: Context,
        version: String,
        bookId: Int,
        chapter: Int,
        gender: AudioVoiceGender
    ): File {
        val safeVersion = version.replace("[^a-zA-Z0-9_]".toRegex(), "")
        return File(getCacheDirectory(context), "chapter_${safeVersion}_b${bookId}_c${chapter}_${gender.name}.wav")
    }

    fun isChapterCached(
        context: Context,
        version: String,
        bookId: Int,
        chapter: Int,
        gender: AudioVoiceGender
    ): Boolean {
        val cacheFile = getLocalCacheFile(context, version, bookId, chapter, gender)
        return cacheFile.exists() && cacheFile.length() > 5000L
    }

    /**
     * Resolves the chapter audio file from local offline cache or synthesizes it live
     * with Google Gemini Neural Voice (NotebookLM style) and caches it permanently.
     */
    suspend fun resolveOrSynthesizeChapterAudio(
        context: Context,
        version: String,
        bookId: Int,
        bookName: String,
        chapter: Int,
        gender: AudioVoiceGender
    ): String? = withContext(Dispatchers.IO) {
        cleanCorruptedCache(context)

        // 1. Instant offline playback if already cached
        val cacheFile = getLocalCacheFile(context, version, bookId, chapter, gender)
        if (cacheFile.exists() && cacheFile.length() > 5000L) {
            Log.i(TAG, "Playing from local offline AI cache: ${cacheFile.name} (${cacheFile.length()} bytes)")
            return@withContext cacheFile.absolutePath
        }

        // 2. Fetch verses from local database
        val verses = OfflineBibleManager.getVerses(context, bookId, chapter)
        val textToNarrate = if (verses.isNotEmpty()) {
            buildNarrationText(bookName, chapter, verses.map { it.text })
        } else {
            val playlist = BibleAudioController.currentPlaylist
            if (playlist.isNotEmpty()) {
                buildNarrationText(bookName, chapter, playlist.map { it.text })
            } else {
                Log.w(TAG, "No verses available to synthesize for $bookName $chapter")
                return@withContext null
            }
        }

        if (textToNarrate.isBlank()) return@withContext null

        val apiKey = GeminiVerseContextService.getEffectiveApiKey()
        if (apiKey.isBlank()) {
            Log.e(TAG, "Gemini API key is not configured")
            return@withContext null
        }

        // 3. Segment text if long (>2500 chars) for smooth, reliable synthesis
        val chunks = splitTextIntoChunks(textToNarrate, 2200)
        Log.i(TAG, "Synthesizing $bookName $chapter (${gender.displayName}) with Gemini TTS in ${chunks.size} chunk(s)...")

        val pcmPayloads = mutableListOf<ByteArray>()
        var sampleRate = 24000

        for ((index, chunk) in chunks.withIndex()) {
            val wavResult = synthesizeChunkWithModels(chunk, gender, apiKey)
            if (wavResult == null || wavResult.isEmpty()) {
                Log.e(TAG, "Failed to synthesize chunk ${index + 1}/${chunks.size} for $bookName $chapter")
                return@withContext null
            }

            val (extractedPcm, sr) = extractPcmFromWavOrL16(wavResult)
            if (extractedPcm.isEmpty()) {
                Log.e(TAG, "Failed to extract valid audio data from chunk ${index + 1}")
                return@withContext null
            }
            sampleRate = sr
            pcmPayloads.add(extractedPcm)
        }

        // 4. Concatenate into single WAV file and save to cache
        val tempFile = File(cacheFile.parentFile, "${cacheFile.name}.tmp")
        try {
            val totalPcmSize = pcmPayloads.sumOf { it.size }
            val wavHeader = createWavHeader(totalPcmSize, sampleRate, 1, 16)

            FileOutputStream(tempFile).use { fos ->
                fos.write(wavHeader)
                for (pcm in pcmPayloads) {
                    fos.write(pcm)
                }
                fos.flush()
            }

            if (tempFile.exists() && tempFile.length() > 5000L) {
                if (cacheFile.exists()) cacheFile.delete()
                val renamed = tempFile.renameTo(cacheFile)
                if (renamed) {
                    Log.i(TAG, "Successfully synthesized and cached AI audio: ${cacheFile.name} (${cacheFile.length()} bytes)")
                    cleanOldCacheIfNeeded(context)
                    return@withContext cacheFile.absolutePath
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error writing synthesized audio file", e)
        } finally {
            if (tempFile.exists()) tempFile.delete()
        }

        return@withContext null
    }

    private fun synthesizeChunkWithModels(
        text: String,
        gender: AudioVoiceGender,
        apiKey: String
    ): ByteArray? {
        val voiceName = if (gender == AudioVoiceGender.MALE) "Puck" else "Kore"
        val prompt = "Lee con voz serena, elocuente y cálida de narrador de audiolibros el siguiente texto bíblico, de forma fluida y natural, sin añadir comentarios ni introducciones:\n\n$text"

        val jsonPayload = JSONObject().apply {
            put("contents", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "user")
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply { put("text", prompt) })
                    })
                })
            })
            put("generationConfig", JSONObject().apply {
                put("responseModalities", JSONArray().apply { put("AUDIO") })
                put("speechConfig", JSONObject().apply {
                    put("voiceConfig", JSONObject().apply {
                        put("prebuiltVoiceConfig", JSONObject().apply {
                            put("voiceName", voiceName)
                        })
                    })
                })
            })
        }

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonPayload.toString().toRequestBody(mediaType)

        for (model in TTS_MODELS) {
            try {
                val url = "$BASE_URL/$model:generateContent?key=$apiKey"
                val request = Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build()

                httpClient.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        Log.w(TAG, "Model $model returned HTTP ${response.code}")
                        return@use
                    }

                    val bodyStr = response.body?.string() ?: return@use
                    val root = JSONObject(bodyStr)
                    val candidates = root.optJSONArray("candidates") ?: return@use
                    if (candidates.length() == 0) return@use

                    val content = candidates.getJSONObject(0).optJSONObject("content") ?: return@use
                    val parts = content.optJSONArray("parts") ?: return@use

                    for (i in 0 until parts.length()) {
                        val part = parts.getJSONObject(i)
                        val inlineData = part.optJSONObject("inlineData")
                        if (inlineData != null) {
                            val base64Data = inlineData.optString("data", "")
                            if (base64Data.isNotBlank()) {
                                return Base64.decode(base64Data, Base64.DEFAULT)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.w(TAG, "Error generating TTS on model $model: ${e.message}")
            }
        }

        return null
    }

    private fun extractPcmFromWavOrL16(rawBytes: ByteArray): Pair<ByteArray, Int> {
        if (rawBytes.size < 4) return Pair(ByteArray(0), 24000)

        // Check if starts with "RIFF" header
        if (rawBytes[0] == 'R'.code.toByte() &&
            rawBytes[1] == 'I'.code.toByte() &&
            rawBytes[2] == 'F'.code.toByte() &&
            rawBytes[3] == 'F'.code.toByte() &&
            rawBytes.size > 44
        ) {
            val sampleRate = ByteBuffer.wrap(rawBytes, 24, 4).order(ByteOrder.LITTLE_ENDIAN).int
            val pcmData = rawBytes.copyOfRange(44, rawBytes.size)
            return Pair(pcmData, if (sampleRate > 0) sampleRate else 24000)
        }

        // Raw L16 Linear PCM @ 24kHz
        return Pair(rawBytes, 24000)
    }

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

        buffer.put('R'.code.toByte())
        buffer.put('I'.code.toByte())
        buffer.put('F'.code.toByte())
        buffer.put('F'.code.toByte())
        buffer.putInt(totalDataLen)
        buffer.put('W'.code.toByte())
        buffer.put('A'.code.toByte())
        buffer.put('V'.code.toByte())
        buffer.put('E'.code.toByte())

        buffer.put('f'.code.toByte())
        buffer.put('m'.code.toByte())
        buffer.put('t'.code.toByte())
        buffer.put(' '.code.toByte())
        buffer.putInt(16) // Subchunk1Size (16 for PCM)
        buffer.putShort(1.toShort()) // AudioFormat (1 for PCM)
        buffer.putShort(channels.toShort())
        buffer.putInt(sampleRate)
        buffer.putInt(byteRate)
        buffer.putShort(blockAlign.toShort())
        buffer.putShort(bitsPerSample.toShort())

        buffer.put('d'.code.toByte())
        buffer.put('a'.code.toByte())
        buffer.put('t'.code.toByte())
        buffer.put('a'.code.toByte())
        buffer.putInt(pcmDataSize)

        return buffer.array()
    }

    private fun buildNarrationText(
        bookName: String,
        chapter: Int,
        verseTexts: List<String>
    ): String {
        val sb = StringBuilder()
        sb.append("$bookName, capítulo $chapter.\n\n")

        for (text in verseTexts) {
            val clean = BibleAudioSpeechSanitizer.prepareForNaturalSpeech(text)
            if (clean.isNotBlank()) {
                sb.append(clean)
                if (!clean.endsWith(".") && !clean.endsWith(";") && !clean.endsWith("!") && !clean.endsWith("?")) {
                    sb.append(". ")
                } else {
                    sb.append(" ")
                }
            }
        }
        return sb.toString().trim()
    }

    private fun splitTextIntoChunks(text: String, maxLen: Int): List<String> {
        val chunks = mutableListOf<String>()
        val sentences = text.split(Regex("(?<=[.!?\\n])\\s+"))
        var currentChunk = StringBuilder()

        for (sentence in sentences) {
            if (currentChunk.length + sentence.length + 1 > maxLen) {
                if (currentChunk.isNotEmpty()) {
                    chunks.add(currentChunk.toString().trim())
                    currentChunk = StringBuilder()
                }
                if (sentence.length > maxLen) {
                    var remaining = sentence
                    while (remaining.length > maxLen) {
                        chunks.add(remaining.take(maxLen))
                        remaining = remaining.drop(maxLen)
                    }
                    currentChunk.append(remaining)
                } else {
                    currentChunk.append(sentence)
                }
            } else {
                if (currentChunk.isNotEmpty()) currentChunk.append(" ")
                currentChunk.append(sentence)
            }
        }

        if (currentChunk.isNotEmpty()) {
            chunks.add(currentChunk.toString().trim())
        }

        return chunks
    }

    fun cleanCorruptedCache(context: Context) {
        try {
            val cacheDir = getCacheDirectory(context)
            val files = cacheDir.listFiles() ?: return
            val now = System.currentTimeMillis()
            for (file in files) {
                val isStaleTmp = file.name.endsWith(".tmp") && (now - file.lastModified() > 60000L)
                val isCorruptWav = file.name.endsWith(".wav") && file.length() < 5000L
                if (isStaleTmp || isCorruptWav) {
                    file.delete()
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error cleaning corrupted cache", e)
        }
    }

    private fun cleanOldCacheIfNeeded(context: Context) {
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
            Log.w(TAG, "Error managing cache limit", e)
        }
    }
}

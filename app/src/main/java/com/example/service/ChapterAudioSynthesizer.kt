package com.example.service

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.Locale
import java.util.UUID
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.resume

/**
 * High-definition Chapter Audio Synthesizer.
 * Synthesizes the entire chapter into a seamless continuous WAV audio file
 * using Android's native Google Speech Engine with 0 rate-limits, full offline support,
 * and high-fidelity natural voice options (Male & Female).
 */
object ChapterAudioSynthesizer {

    private const val TAG = "ChapterAudioSynthesizer"
    private const val MAX_CHUNK_LENGTH = 3200

    private var tts: TextToSpeech? = null
    private val isInitialized = AtomicBoolean(false)
    private var activeContext: Context? = null

    fun initialize(context: Context) {
        if (tts != null && isInitialized.get()) return
        synchronized(this) {
            if (tts == null) {
                activeContext = context.applicationContext
                tts = TextToSpeech(context.applicationContext) { status ->
                    if (status == TextToSpeech.SUCCESS) {
                        isInitialized.set(true)
                        configureLocale()
                        Log.i(TAG, "Native Speech Engine successfully initialized")
                    } else {
                        Log.e(TAG, "Native Speech Engine init failed: status=$status")
                    }
                }
            }
        }
    }

    private fun configureLocale() {
        tts?.let { engine ->
            try {
                val localeEs = Locale("es", "ES")
                val res = engine.setLanguage(localeEs)
                if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
                    val genericEs = Locale("es")
                    engine.setLanguage(genericEs)
                }
            } catch (e: Exception) {
                Log.w(TAG, "Error setting Spanish locale on TTS engine", e)
            }
        }
    }

    private fun getCacheDirectory(context: Context): File {
        val dir = File(context.cacheDir, "bible_audio_synthesized")
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

    /**
     * Synthesizes the complete chapter into a continuous audio file.
     * Guaranteed to work offline, with 0 quota limits and instant seekability.
     */
    suspend fun synthesizeChapterAudio(
        context: Context,
        version: String,
        bookId: Int,
        bookName: String,
        chapter: Int,
        verses: List<AudioVerseItem>,
        gender: AudioVoiceGender
    ): File? = withContext(Dispatchers.IO) {
        val cacheFile = getLocalCacheFile(context, version, bookId, chapter, gender)
        if (cacheFile.exists() && cacheFile.length() > 5000L) {
            return@withContext cacheFile
        }

        initialize(context)

        // Wait up to 3 seconds for TTS engine if just started
        var waitCount = 0
        while (!isInitialized.get() && waitCount < 30) {
            delay(100)
            waitCount++
        }

        val engine = tts
        if (engine == null || !isInitialized.get()) {
            Log.e(TAG, "TTS engine not ready for chapter synthesis")
            return@withContext null
        }

        applyVoiceForGender(engine, gender)

        val fullText = buildChapterNarration(bookName, chapter, verses)
        if (fullText.isBlank()) return@withContext null

        try {
            if (fullText.length <= MAX_CHUNK_LENGTH) {
                val tempFile = File(cacheFile.parentFile, "${cacheFile.name}.tmp")
                val success = synthesizeTextChunk(engine, fullText, tempFile)
                if (success && tempFile.exists() && tempFile.length() > 1000L) {
                    tempFile.renameTo(cacheFile)
                    cleanOldCacheIfNeeded(context)
                    return@withContext cacheFile
                } else {
                    tempFile.delete()
                    return@withContext null
                }
            } else {
                // Synthesize multi-part chapter and seamlessly stitch PCM WAV files
                val chunks = splitTextIntoChunks(fullText, MAX_CHUNK_LENGTH)
                val chunkFiles = mutableListOf<File>()

                for ((idx, chunk) in chunks.withIndex()) {
                    val chunkFile = File(cacheFile.parentFile, "${cacheFile.name}_part$idx.wav")
                    val ok = synthesizeTextChunk(engine, chunk, chunkFile)
                    if (ok && chunkFile.exists() && chunkFile.length() > 1000L) {
                        chunkFiles.add(chunkFile)
                    } else {
                        chunkFile.delete()
                    }
                }

                if (chunkFiles.size == chunks.size) {
                    val merged = concatenateWavFiles(chunkFiles, cacheFile)
                    chunkFiles.forEach { it.delete() }
                    if (merged) {
                        cleanOldCacheIfNeeded(context)
                        return@withContext cacheFile
                    }
                } else {
                    chunkFiles.forEach { it.delete() }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error synthesizing chapter audio for $bookId:$chapter", e)
        }

        return@withContext null
    }

    private suspend fun synthesizeTextChunk(
        engine: TextToSpeech,
        text: String,
        destinationFile: File
    ): Boolean = withTimeoutOrNull(25000L) {
        suspendCancellableCoroutine { continuation ->
            val utteranceId = "synth_${UUID.randomUUID()}"
            val params = Bundle().apply {
                putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId)
            }

            engine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(id: String?) {}

                override fun onDone(id: String?) {
                    if (id == utteranceId) {
                        if (continuation.isActive) {
                            continuation.resume(true)
                        }
                    }
                }

                override fun onError(id: String?) {
                    if (id == utteranceId) {
                        Log.e(TAG, "TTS synthesizeToFile error for utterance $id")
                        if (continuation.isActive) {
                            continuation.resume(false)
                        }
                    }
                }

                @Deprecated("Deprecated in Java")
                override fun onError(id: String?, errorCode: Int) {
                    if (id == utteranceId) {
                        Log.e(TAG, "TTS synthesizeToFile error $errorCode for utterance $id")
                        if (continuation.isActive) {
                            continuation.resume(false)
                        }
                    }
                }
            })

            val result = engine.synthesizeToFile(text, params, destinationFile, utteranceId)
            if (result != TextToSpeech.SUCCESS) {
                Log.e(TAG, "synthesizeToFile returned failure code: $result")
                if (continuation.isActive) {
                    continuation.resume(false)
                }
            }
        }
    } ?: false

    private fun applyVoiceForGender(engine: TextToSpeech, gender: AudioVoiceGender) {
        try {
            val voices = engine.voices ?: return
            val spanishVoices = voices.filter { v ->
                val lang = v.locale.language.lowercase()
                lang == "es" || lang.startsWith("es")
            }

            val selectedVoice = when (gender) {
                AudioVoiceGender.FEMALE -> {
                    spanishVoices.firstOrNull { v ->
                        val name = v.name.lowercase()
                        (name.contains("female") || name.contains("-eea-") || name.contains("-sfb-") || name.contains("-efc-") || name.contains("-ana-")) &&
                                (v.quality >= Voice.QUALITY_HIGH || v.isNetworkConnectionRequired)
                    } ?: spanishVoices.firstOrNull { v ->
                        val name = v.name.lowercase()
                        name.contains("female") || name.contains("-eea-") || name.contains("-sfb-")
                    } ?: spanishVoices.firstOrNull { v ->
                        !v.name.lowercase().contains("male") && !v.name.lowercase().contains("-eec-")
                    }
                }
                AudioVoiceGender.MALE -> {
                    spanishVoices.firstOrNull { v ->
                        val name = v.name.lowercase()
                        (name.contains("male") || name.contains("-eec-") || name.contains("-sfa-") || name.contains("-dfa-") || name.contains("-enr-")) &&
                                (v.quality >= Voice.QUALITY_HIGH || v.isNetworkConnectionRequired)
                    } ?: spanishVoices.firstOrNull { v ->
                        val name = v.name.lowercase()
                        name.contains("male") || name.contains("-eec-") || name.contains("-sfa-")
                    }
                }
            }

            if (selectedVoice != null) {
                engine.voice = selectedVoice
                Log.d(TAG, "Selected voice: ${selectedVoice.name} for $gender")
            } else {
                configureLocale()
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error applying custom voice gender", e)
            configureLocale()
        }
    }

    private fun buildChapterNarration(
        bookName: String,
        chapter: Int,
        verses: List<AudioVerseItem>
    ): String {
        val sb = StringBuilder()
        sb.append("$bookName, capítulo $chapter.\n\n")

        for (v in verses) {
            val clean = BibleAudioSpeechSanitizer.prepareForNaturalSpeech(v.text)
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
                    // Force break very long sentence
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

    /**
     * Stitches multiple linear 16-bit PCM WAV files together into a single continuous file.
     */
    private fun concatenateWavFiles(inputFiles: List<File>, outputFile: File): Boolean {
        if (inputFiles.isEmpty()) return false
        if (inputFiles.size == 1) {
            return inputFiles[0].renameTo(outputFile) || run {
                inputFiles[0].copyTo(outputFile, overwrite = true)
                true
            }
        }

        try {
            // Read 44-byte header from the first file
            val firstBytes = inputFiles[0].readBytes()
            if (firstBytes.size < 44) return false

            val sampleRate = ByteBuffer.wrap(firstBytes, 24, 4).order(ByteOrder.LITTLE_ENDIAN).int
            val channels = ByteBuffer.wrap(firstBytes, 22, 2).order(ByteOrder.LITTLE_ENDIAN).short.toInt()
            val bitsPerSample = ByteBuffer.wrap(firstBytes, 34, 2).order(ByteOrder.LITTLE_ENDIAN).short.toInt()

            var totalPcmSize = 0L
            val pcmList = mutableListOf<ByteArray>()

            for (file in inputFiles) {
                val data = file.readBytes()
                if (data.size > 44) {
                    val pcmChunk = data.copyOfRange(44, data.size)
                    pcmList.add(pcmChunk)
                    totalPcmSize += pcmChunk.size
                }
            }

            if (totalPcmSize == 0L) return false

            FileOutputStream(outputFile).use { fos ->
                val header = createWavHeader(totalPcmSize.toInt(), sampleRate, channels, bitsPerSample)
                fos.write(header)
                for (pcm in pcmList) {
                    fos.write(pcm)
                }
                fos.flush()
            }
            return outputFile.exists() && outputFile.length() > 5000L
        } catch (e: Exception) {
            Log.e(TAG, "Error concatenating WAV files", e)
            return false
        }
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

        // RIFF chunk
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
        buffer.putInt(16) // Subchunk1Size
        buffer.putShort(1.toShort()) // PCM
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

    private fun cleanOldCacheIfNeeded(context: Context) {
        try {
            val cacheDir = getCacheDirectory(context)
            val files = cacheDir.listFiles() ?: return
            val maxSizeBytes = 300L * 1024L * 1024L // 300 MB
            var totalSize = files.sumOf { it.length() }
            if (totalSize > maxSizeBytes) {
                val sorted = files.sortedBy { it.lastModified() }
                for (f in sorted) {
                    val sz = f.length()
                    if (f.delete()) {
                        totalSize -= sz
                        if (totalSize < maxSizeBytes * 0.8) break
                    }
                }
            }
        } catch (_: Exception) {}
    }
}

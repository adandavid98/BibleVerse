package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

class VoiceInputState(
    private val context: Context
) {
    var isRecording by mutableStateOf(false)
        private set

    var rmsLevel by mutableFloatStateOf(0f)
        private set

    var currentTranscribedText by mutableStateOf("")
        private set

    private var accumulatedText = ""
    private var partialText = ""

    private var speechRecognizer: SpeechRecognizer? = null
    private val mainHandler = Handler(Looper.getMainLooper())
    private var restartRunnable: Runnable? = null
    private var consecutiveClientErrors = 0

    private fun getPreferredSpanishTag(): String {
        val sysLocale = Locale.getDefault()
        return if (sysLocale.language.equals("es", ignoreCase = true)) {
            sysLocale.toLanguageTag()
        } else {
            "es-419" // Español Latinoamericano
        }
    }

    private fun getSpeechIntent(): Intent {
        val langTag = getPreferredSpanishTag()
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, langTag)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, langTag)
            putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, langTag)
            putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", arrayOf(langTag, "es-419", "es-ES", "es-US"))
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
            // Tiempos extendidos para permitir hablar pausadamente
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 600000L)
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 4000L)
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 4000L)
        }
    }

    private fun initRecognizerIfNeeded(): Boolean {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            Toast.makeText(context, "El servicio de voz no está habilitado en este dispositivo", Toast.LENGTH_SHORT).show()
            return false
        }
        if (speechRecognizer == null) {
            try {
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
                    setRecognitionListener(object : RecognitionListener {
                        override fun onReadyForSpeech(params: Bundle?) {
                            Log.d("VoiceInputManager", "onReadyForSpeech")
                            consecutiveClientErrors = 0
                        }

                        override fun onBeginningOfSpeech() {
                            Log.d("VoiceInputManager", "onBeginningOfSpeech")
                        }

                        override fun onRmsChanged(rmsdB: Float) {
                            if (isRecording) {
                                val norm = ((rmsdB + 2f) / 12f).coerceIn(0f, 1f)
                                rmsLevel = norm
                            }
                        }

                        override fun onBufferReceived(buffer: ByteArray?) {}

                        override fun onEndOfSpeech() {
                            Log.d("VoiceInputManager", "onEndOfSpeech")
                            if (isRecording) {
                                scheduleRestart(200)
                            }
                        }

                        override fun onError(error: Int) {
                            Log.e("VoiceInputManager", "SpeechRecognizer error: $error")
                            if (!isRecording) return

                            when (error) {
                                SpeechRecognizer.ERROR_NO_MATCH,
                                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> {
                                    if (partialText.isNotBlank()) {
                                        accumulatedText = if (accumulatedText.isBlank()) partialText else "$accumulatedText $partialText"
                                        partialText = ""
                                        updateDisplayedText()
                                    }
                                    scheduleRestart(250)
                                }
                                SpeechRecognizer.ERROR_CLIENT,
                                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> {
                                    consecutiveClientErrors++
                                    destroyRecognizer()
                                    if (consecutiveClientErrors < 3) {
                                        scheduleRestart(350)
                                    } else {
                                        isRecording = false
                                        Toast.makeText(context, "Por favor verifica que la app de Google o voz esté activa", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> {
                                    isRecording = false
                                    Toast.makeText(context, "Se requiere permiso de micrófono para consultar por voz", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    scheduleRestart(350)
                                }
                            }
                        }

                        override fun onResults(results: Bundle?) {
                            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            val text = matches?.firstOrNull()?.trim() ?: ""
                            Log.d("VoiceInputManager", "onResults: $text")
                            if (text.isNotBlank()) {
                                accumulatedText = if (accumulatedText.isBlank()) text else "$accumulatedText $text"
                                partialText = ""
                                updateDisplayedText()
                            }
                            if (isRecording) {
                                scheduleRestart(150)
                            }
                        }

                        override fun onPartialResults(partialResults: Bundle?) {
                            if (!isRecording) return
                            val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            val current = matches?.firstOrNull()?.trim() ?: ""
                            if (current.isNotBlank()) {
                                partialText = current
                                updateDisplayedText()
                            }
                        }

                        override fun onEvent(eventType: Int, params: Bundle?) {}
                    })
                }
            } catch (e: Exception) {
                Log.e("VoiceInputManager", "Error creating SpeechRecognizer", e)
                return false
            }
        }
        return true
    }

    private fun scheduleRestart(delayMs: Long) {
        restartRunnable?.let { mainHandler.removeCallbacks(it) }
        restartRunnable = Runnable {
            if (isRecording) {
                try {
                    initRecognizerIfNeeded()
                    speechRecognizer?.startListening(getSpeechIntent())
                } catch (e: Exception) {
                    Log.e("VoiceInputManager", "Error starting listening on restart", e)
                    destroyRecognizer()
                    initRecognizerIfNeeded()
                    speechRecognizer?.startListening(getSpeechIntent())
                }
            }
        }
        mainHandler.postDelayed(restartRunnable!!, delayMs)
    }

    private fun updateDisplayedText() {
        val combined = listOf(accumulatedText, partialText)
            .filter { it.isNotBlank() }
            .joinToString(" ")
            .trim()
            .replace("\\s+".toRegex(), " ")
        currentTranscribedText = combined
    }

    fun startListening() {
        accumulatedText = ""
        partialText = ""
        currentTranscribedText = ""
        rmsLevel = 0f
        isRecording = true
        consecutiveClientErrors = 0

        mainHandler.post {
            if (!initRecognizerIfNeeded()) {
                isRecording = false
                return@post
            }
            try {
                speechRecognizer?.startListening(getSpeechIntent())
            } catch (e: Exception) {
                Log.e("VoiceInputManager", "Error starting listening", e)
                destroyRecognizer()
                if (initRecognizerIfNeeded()) {
                    speechRecognizer?.startListening(getSpeechIntent())
                }
            }
        }
    }

    fun stopListening(): String {
        isRecording = false
        rmsLevel = 0f
        restartRunnable?.let { mainHandler.removeCallbacks(it) }

        try {
            speechRecognizer?.stopListening()
        } catch (_: Exception) {}

        updateDisplayedText()
        val raw = currentTranscribedText
        val result = if (raw.isNotBlank()) {
            raw.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        } else {
            ""
        }

        accumulatedText = ""
        partialText = ""
        currentTranscribedText = ""
        return result
    }

    fun cancelListening() {
        isRecording = false
        rmsLevel = 0f
        restartRunnable?.let { mainHandler.removeCallbacks(it) }
        try {
            speechRecognizer?.cancel()
        } catch (_: Exception) {}
        accumulatedText = ""
        partialText = ""
        currentTranscribedText = ""
    }

    fun destroyRecognizer() {
        restartRunnable?.let { mainHandler.removeCallbacks(it) }
        try {
            speechRecognizer?.destroy()
        } catch (_: Exception) {}
        speechRecognizer = null
    }
}

@Composable
fun rememberVoiceInputState(): VoiceInputState {
    val context = LocalContext.current.applicationContext
    val state = remember { VoiceInputState(context) }

    DisposableEffect(Unit) {
        onDispose {
            state.cancelListening()
            state.destroyRecognizer()
        }
    }

    return state
}

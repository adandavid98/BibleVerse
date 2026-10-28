package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
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

    private fun getSpeechIntent(): Intent {
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
            putExtra("android.speech.extra.DICTATION_MODE", true)
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 300000L)
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 10000L)
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 10000L)
        }
    }

    private fun initRecognizerIfNeeded(): Boolean {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            Toast.makeText(context, "El reconocimiento de voz no está disponible en este dispositivo", Toast.LENGTH_SHORT).show()
            return false
        }
        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
                setRecognitionListener(object : RecognitionListener {
                    override fun onReadyForSpeech(params: Bundle?) {}

                    override fun onBeginningOfSpeech() {}

                    override fun onRmsChanged(rmsdB: Float) {
                        if (isRecording) {
                            val norm = ((rmsdB + 2f) / 12f).coerceIn(0f, 1f)
                            rmsLevel = norm
                        }
                    }

                    override fun onBufferReceived(buffer: ByteArray?) {}

                    override fun onEndOfSpeech() {
                        // Si el usuario aún no presionó stop, nos preparamos para continuar
                        if (isRecording) {
                            scheduleRestart(100)
                        }
                    }

                    override fun onError(error: Int) {
                        if (!isRecording) return

                        when (error) {
                            SpeechRecognizer.ERROR_NO_MATCH,
                            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> {
                                // Pausa en el habla, reiniciamos escucha para permitir hablar cuanto quiera
                                scheduleRestart(150)
                            }
                            SpeechRecognizer.ERROR_CLIENT,
                            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> {
                                destroyRecognizer()
                                scheduleRestart(200)
                            }
                            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> {
                                isRecording = false
                                Toast.makeText(context, "Se requiere permiso de micrófono para consultar por voz", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                scheduleRestart(300)
                            }
                        }
                    }

                    override fun onResults(results: Bundle?) {
                        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        val text = matches?.firstOrNull()?.trim() ?: ""
                        if (text.isNotBlank()) {
                            accumulatedText = if (accumulatedText.isBlank()) text else "$accumulatedText $text"
                            partialText = ""
                            updateDisplayedText()
                        }
                        if (isRecording) {
                            scheduleRestart(100)
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
                    destroyRecognizer()
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
        currentTranscribedText = combined
    }

    fun startListening() {
        if (!initRecognizerIfNeeded()) return
        accumulatedText = ""
        partialText = ""
        currentTranscribedText = ""
        rmsLevel = 0f
        isRecording = true

        try {
            speechRecognizer?.startListening(getSpeechIntent())
        } catch (e: Exception) {
            destroyRecognizer()
            initRecognizerIfNeeded()
            speechRecognizer?.startListening(getSpeechIntent())
        }
    }

    fun stopListening(): String {
        isRecording = false
        rmsLevel = 0f
        restartRunnable?.let { mainHandler.removeCallbacks(it) }

        try {
            speechRecognizer?.stopListening()
        } catch (_: Exception) {}

        val result = currentTranscribedText
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

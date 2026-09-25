package com.example.service

import android.content.Context
import android.content.Intent
import android.os.Build
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object BibleAudioController {

    private val _audioState = MutableStateFlow(BibleAudioState())
    val audioState: StateFlow<BibleAudioState> = _audioState.asStateFlow()

    // In-memory playlist to prevent Binder TransactionTooLargeException
    @Volatile
    var currentPlaylist: List<AudioVerseItem> = emptyList()
        private set

    // Available speeds
    val availableSpeeds = listOf(1.0f, 1.25f, 1.5f)

    fun startChapter(
        context: Context,
        bookId: Int,
        bookName: String,
        chapter: Int,
        version: String,
        verses: List<AudioVerseItem>,
        startVerseNumber: Int = 1,
        speed: Float = _audioState.value.speechRate
    ) {
        if (verses.isEmpty()) return
        currentPlaylist = verses
        val startIndex = verses.indexOfFirst { it.verseNumber == startVerseNumber }.coerceAtLeast(0)
        val initialVerse = verses[startIndex]

        _audioState.update {
            it.copy(
                isActive = true,
                isPlaying = true,
                currentBookId = bookId,
                currentBookName = bookName,
                currentChapter = chapter,
                currentVerseNumber = initialVerse.verseNumber,
                currentVerseText = initialVerse.text,
                totalVerses = verses.size,
                currentIndex = startIndex,
                speechRate = speed,
                bibleVersion = version,
                errorMessage = null
            )
        }

        val intent = Intent(context, BibleAudioService::class.java).apply {
            action = BibleAudioService.ACTION_START
            putExtra(BibleAudioService.EXTRA_BOOK_ID, bookId)
            putExtra(BibleAudioService.EXTRA_BOOK_NAME, bookName)
            putExtra(BibleAudioService.EXTRA_CHAPTER, chapter)
            putExtra(BibleAudioService.EXTRA_VERSION, version)
            putExtra(BibleAudioService.EXTRA_START_INDEX, startIndex)
            putExtra(BibleAudioService.EXTRA_SPEED, speed)
        }
        startService(context, intent)
    }

    fun play(context: Context) {
        sendCommand(context, BibleAudioService.ACTION_PLAY)
    }

    fun pause(context: Context) {
        sendCommand(context, BibleAudioService.ACTION_PAUSE)
    }

    fun togglePlayPause(context: Context) {
        sendCommand(context, BibleAudioService.ACTION_TOGGLE)
    }

    fun next(context: Context) {
        sendCommand(context, BibleAudioService.ACTION_NEXT)
    }

    fun previous(context: Context) {
        sendCommand(context, BibleAudioService.ACTION_PREVIOUS)
    }

    fun seekToVerse(context: Context, verseNumber: Int) {
        val index = currentPlaylist.indexOfFirst { it.verseNumber == verseNumber }
        if (index >= 0) {
            val intent = Intent(context, BibleAudioService::class.java).apply {
                action = BibleAudioService.ACTION_SEEK_INDEX
                putExtra(BibleAudioService.EXTRA_START_INDEX, index)
            }
            startService(context, intent)
        }
    }

    fun cycleSpeed(context: Context) {
        val currentSpeed = _audioState.value.speechRate
        val nextSpeed = when {
            currentSpeed < 1.1f -> 1.25f
            currentSpeed < 1.35f -> 1.5f
            else -> 1.0f
        }
        setSpeed(context, nextSpeed)
    }

    fun setSpeed(context: Context, speed: Float) {
        _audioState.update { it.copy(speechRate = speed) }
        val intent = Intent(context, BibleAudioService::class.java).apply {
            action = BibleAudioService.ACTION_SET_SPEED
            putExtra(BibleAudioService.EXTRA_SPEED, speed)
        }
        startService(context, intent)
    }

    fun stop(context: Context) {
        sendCommand(context, BibleAudioService.ACTION_STOP)
        _audioState.update { it.copy(isActive = false, isPlaying = false) }
    }

    internal fun updatePlaylist(verses: List<AudioVerseItem>) {
        currentPlaylist = verses
    }

    internal fun updateState(transform: (BibleAudioState) -> BibleAudioState) {
        _audioState.update(transform)
    }

    private fun sendCommand(context: Context, action: String) {
        val intent = Intent(context, BibleAudioService::class.java).apply {
            this.action = action
        }
        startService(context, intent)
    }

    private fun startService(context: Context, intent: Intent) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

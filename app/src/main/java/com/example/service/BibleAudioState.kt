package com.example.service

import java.util.Locale

/**
 * Available voice genders for natural neural narration.
 */
enum class AudioVoiceGender(val displayName: String, val shortLabel: String, val cloudVoice: String) {
    FEMALE("Voz Femenina", "Femenina", "Kore"),
    MALE("Voz Masculina", "Masculina", "Charon")
}

/**
 * Item representing a verse in the audio playback queue.
 */
data class AudioVerseItem(
    val bookId: Int,
    val bookName: String,
    val chapter: Int,
    val verseNumber: Int,
    val text: String
)

/**
 * Observable UI and Service state for Bible Audio playback.
 */
data class BibleAudioState(
    val isPlaying: Boolean = false,
    val isActive: Boolean = false,
    val currentBookId: Int = 1,
    val currentBookName: String = "",
    val currentChapter: Int = 1,
    val currentVerseNumber: Int = 1,
    val currentVerseText: String = "",
    val totalVerses: Int = 0,
    val currentIndex: Int = 0,
    val speechRate: Float = 1.0f,
    val voiceGender: AudioVoiceGender = AudioVoiceGender.FEMALE,
    val bibleVersion: String = "RVR1960",
    val isBuffering: Boolean = false,
    val errorMessage: String? = null,
    val currentPositionMs: Long = 0L,
    val totalDurationMs: Long = 0L
) {
    val trackProgressFraction: Float
        get() = if (totalDurationMs > 0) {
            (currentPositionMs.toFloat() / totalDurationMs.toFloat()).coerceIn(0f, 1f)
        } else {
            0f
        }

    val currentPositionFormatted: String
        get() = formatTime(currentPositionMs)

    val totalDurationFormatted: String
        get() = formatTime(totalDurationMs)

    val progressFraction: Float
        get() = if (totalDurationMs > 0) trackProgressFraction else (if (totalVerses > 0) (currentIndex + 1).toFloat() / totalVerses.toFloat() else 0f)

    companion object {
        fun formatTime(millis: Long): String {
            if (millis <= 0) return "00:00"
            val totalSeconds = millis / 1000
            val minutes = totalSeconds / 60
            val seconds = totalSeconds % 60
            return String.format(Locale.US, "%02d:%02d", minutes, seconds)
        }
    }
}

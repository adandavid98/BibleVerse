package com.example.service

/**
 * Available voice genders for natural neural narration.
 */
enum class AudioVoiceGender(val displayName: String, val shortLabel: String) {
    FEMALE("Voz Femenina", "Femenina"),
    MALE("Voz Masculina", "Masculina")
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
    val errorMessage: String? = null
) {
    val progressFraction: Float
        get() = if (totalVerses > 0) (currentIndex + 1).toFloat() / totalVerses.toFloat() else 0f
}

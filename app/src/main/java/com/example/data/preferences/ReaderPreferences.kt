package com.example.data.preferences

enum class ReaderThemeMode {
    LIGHT,
    DARK,
    SEPIA,
    NIGHT
}

enum class ReaderFontFamily {
    SERIF,
    SANS_SERIF,
    CONDENSED,
    CASUAL,
    CURSIVE,
    MONOSPACE
}

data class ReaderPreferences(
    val fontFamily: ReaderFontFamily = ReaderFontFamily.SERIF,
    val fontSizeSp: Float = 18f,
    val lineSpacingMultiplier: Float = 1.4f,
    val themeMode: ReaderThemeMode = ReaderThemeMode.SEPIA,
    val lastBookId: Int = 1, // Genesis
    val lastChapter: Int = 1,
    val lastVerse: Int = 1,
    val bibleVersion: String = "RVR1960",
    val redLettersEnabled: Boolean = true,
    val continuousScrollEnabled: Boolean = false,
    val showSectionHeadings: Boolean = true
)

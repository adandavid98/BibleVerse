package com.example.service

import com.example.data.bible.OfflineBibleManager

/**
 * Prepares and sanitizes Bible text for ultra-natural, fluent TextToSpeech narration.
 * Eliminates unnatural pauses, footnoted symbols, and punctuation jerks.
 */
object BibleAudioSpeechSanitizer {

    fun prepareForNaturalSpeech(rawText: String?): String {
        if (rawText.isNullOrBlank()) return ""

        // 1. Base HTML tag & entity stripping
        var text = OfflineBibleManager.cleanVerseText(rawText)

        // 2. Remove leading verse numbers (e.g., "1 ", "12 ") so speech doesn't stutter on numbers
        text = text.replace(Regex("^\\d+\\s*"), "")

        // 3. Remove footnote references in brackets or parentheses: [a], [1], (1), (A), *
        text = text.replace(Regex("\\[[^\\]]*\\]"), "")
        text = text.replace(Regex("\\([0-9a-zA-Z]{1,3}\\)"), "")
        text = text.replace("*", "")

        // 4. Smooth out harsh pauses caused by em-dashes and long hyphens
        text = text.replace("—", ", ")
        text = text.replace("--", ", ")

        // 5. Convert colons and semicolons inside sentences into gentle pauses (commas)
        text = text.replace(Regex("([a-záéíóúñA-ZÁÉÍÓÚÑ]):(\\s+[a-záéíóúñ])"), "$1,$2")
        text = text.replace(";", ",")

        // 6. Clean quotes and parenthesis artifacts
        text = text.replace("«", "\"").replace("»", "\"")
        text = text.replace("(", "").replace(")", "")

        // 7. Normalize consecutive commas, periods, or whitespaces
        text = text.replace(Regex(",\\s*,"), ",")
        text = text.replace(Regex("\\.\\s*\\."), ".")
        text = text.replace(Regex("\\s+"), " ")

        return text.trim()
    }
}

package com.example.domain.usecase

import com.example.domain.model.ReaderVerseUiModel

class FormatVerseQuotationUseCase {

    operator fun invoke(verses: List<ReaderVerseUiModel>, bibleVersion: String = "RVR1960"): String {
        if (verses.isEmpty()) return ""

        val sorted = verses.sortedBy { it.verseNumber }
        val first = sorted.first()
        val bookName = first.bookName
        val chapter = first.chapter

        val verseRef = formatVerseRange(sorted.map { it.verseNumber })
        val combinedText = if (sorted.size == 1) {
            sorted.first().text
        } else {
            sorted.joinToString(" ") { "(${it.verseNumber}) ${it.text}" }
        }

        return "«$combinedText»\n\n— $bookName $chapter:$verseRef ($bibleVersion)"
    }

    fun formatCitationOnly(verses: List<ReaderVerseUiModel>, bibleVersion: String = "RVR1960"): String {
        if (verses.isEmpty()) return ""
        val sorted = verses.sortedBy { it.verseNumber }
        val first = sorted.first()
        val verseRef = formatVerseRange(sorted.map { it.verseNumber })
        return "${first.bookName} ${first.chapter}:$verseRef ($bibleVersion)"
    }

    private fun formatVerseRange(numbers: List<Int>): String {
        if (numbers.isEmpty()) return ""
        if (numbers.size == 1) return numbers.first().toString()

        val ranges = mutableListOf<String>()
        var start = numbers[0]
        var prev = numbers[0]

        for (i in 1 until numbers.size) {
            val curr = numbers[i]
            if (curr == prev + 1) {
                prev = curr
            } else {
                if (start == prev) {
                    ranges.add("$start")
                } else {
                    ranges.add("$start-$prev")
                }
                start = curr
                prev = curr
            }
        }
        if (start == prev) {
            ranges.add("$start")
        } else {
            ranges.add("$start-$prev")
        }

        return ranges.joinToString(", ")
    }
}

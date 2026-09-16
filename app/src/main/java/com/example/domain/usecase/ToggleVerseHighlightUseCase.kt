package com.example.domain.usecase

import com.example.data.repository.BibleReaderRepository

class ToggleVerseHighlightUseCase(
    private val repository: BibleReaderRepository
) {

    suspend fun applyHighlight(bookId: Int, chapter: Int, verseNumbers: List<Int>, colorHex: String) {
        verseNumbers.forEach { verseNum ->
            repository.saveHighlight(bookId, chapter, verseNum, colorHex)
        }
    }

    suspend fun removeHighlight(bookId: Int, chapter: Int, verseNumbers: List<Int>) {
        repository.removeHighlights(bookId, chapter, verseNumbers)
    }
}

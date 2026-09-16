package com.example.domain.usecase

import com.example.data.bible.BibleCatalog
import com.example.data.bible.WordsOfJesusCatalog
import com.example.data.repository.BibleReaderRepository
import com.example.domain.model.ReaderVerseUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetChapterVersesWithHighlightsUseCase(
    private val repository: BibleReaderRepository
) {

    suspend fun ensureLoaded(bookId: Int, chapter: Int, version: String) {
        repository.ensureChapterVerses(bookId, chapter, version)
    }

    operator fun invoke(bookId: Int, chapter: Int, version: String): Flow<List<ReaderVerseUiModel>> {
        val bookName = BibleCatalog.books.firstOrNull { it.order == bookId }?.name ?: "Libro"
        val versesFlow = repository.getVerses(bookId, chapter, version)
        val highlightsFlow = repository.getHighlights(bookId, chapter)

        return combine(versesFlow, highlightsFlow) { verses, highlights ->
            val highlightMap = highlights.associateBy { it.verseNumber }
            verses.map { verse ->
                val isJesusSpoken = verse.isRedLetter || WordsOfJesusCatalog.isWordsOfJesus(bookId, chapter, verse.verseNumber)
                ReaderVerseUiModel(
                    bookId = bookId,
                    bookName = bookName,
                    chapter = chapter,
                    verseNumber = verse.verseNumber,
                    text = verse.text,
                    highlightColorHex = highlightMap[verse.verseNumber]?.colorHex,
                    isSelected = false,
                    sectionHeading = verse.sectionHeading,
                    isRedLetter = isJesusSpoken
                )
            }
        }
    }
}

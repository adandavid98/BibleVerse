package com.example.domain.model

data class ReaderVerseUiModel(
    val bookId: Int,
    val bookName: String,
    val chapter: Int,
    val verseNumber: Int,
    val text: String,
    val highlightColorHex: String? = null,
    val isSelected: Boolean = false,
    val sectionHeading: String? = null,
    val isRedLetter: Boolean = false
)

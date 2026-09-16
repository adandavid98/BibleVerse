package com.example.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "bible_reader_verses",
    indices = [
        Index(value = ["bookId", "chapter", "verseNumber", "bibleVersion"], unique = true),
        Index(value = ["bookId", "chapter", "bibleVersion"])
    ]
)
data class BibleReaderVerseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bookId: Int,
    val chapter: Int,
    val verseNumber: Int,
    val text: String,
    val bibleVersion: String = "RVR1960",
    val sectionHeading: String? = null,
    val isRedLetter: Boolean = false
)

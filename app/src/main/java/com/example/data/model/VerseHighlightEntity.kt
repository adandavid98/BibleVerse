package com.example.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "verse_highlights",
    indices = [
        Index(value = ["bookId", "chapter", "verseNumber"], unique = true)
    ]
)
data class VerseHighlightEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bookId: Int,
    val chapter: Int,
    val verseNumber: Int,
    val colorHex: String,
    val createdAt: Long = System.currentTimeMillis()
)

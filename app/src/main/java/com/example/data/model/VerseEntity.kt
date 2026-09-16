package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "verses")
data class VerseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val book: String,
    val chapterVerse: String,
    val reference: String,
    val testament: String, // "Antiguo Testamento" or "Nuevo Testamento"
    val text: String,
    val context: String,
    val topic: String = "General",
    val isFavorite: Boolean = false,
    val notes: String = "",
    val highlightColor: String = "", // e.g. "#FFF59D", "#A7F3D0", "#BAE6FD", "#FBCFE8", "#FED7AA" or ""
    val highlightedPhrases: String = "", // custom highlighted terms or excerpts
    val isCustom: Boolean = false,
    val orderIndex: Int = 0,
    val bibleVersion: String = "RVR1960",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

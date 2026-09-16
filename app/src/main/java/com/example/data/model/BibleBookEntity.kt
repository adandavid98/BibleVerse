package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bible_books")
data class BibleBookEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val testament: String,
    val chaptersCount: Int,
    val category: String,
    val abbreviation: String,
    val orderIndex: Int
)

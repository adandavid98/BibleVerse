package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.VerseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VerseDao {

    @Query("SELECT * FROM verses ORDER BY orderIndex ASC, id ASC")
    fun getAllVerses(): Flow<List<VerseEntity>>

    @Query("SELECT * FROM verses WHERE isFavorite = 1 ORDER BY updatedAt DESC")
    fun getFavoriteVerses(): Flow<List<VerseEntity>>

    @Query("SELECT * FROM verses WHERE highlightColor != '' ORDER BY updatedAt DESC")
    fun getHighlightedVerses(): Flow<List<VerseEntity>>

    @Query("SELECT * FROM verses WHERE notes != '' ORDER BY updatedAt DESC")
    fun getVersesWithNotes(): Flow<List<VerseEntity>>

    @Query("SELECT * FROM verses WHERE testament = :testament ORDER BY orderIndex ASC, id ASC")
    fun getVersesByTestament(testament: String): Flow<List<VerseEntity>>

    @Query("""
        SELECT * FROM verses 
        WHERE text LIKE '%' || :query || '%' 
           OR reference LIKE '%' || :query || '%' 
           OR context LIKE '%' || :query || '%' 
           OR topic LIKE '%' || :query || '%' 
           OR notes LIKE '%' || :query || '%'
        ORDER BY orderIndex ASC, id ASC
    """)
    fun searchVerses(query: String): Flow<List<VerseEntity>>

    @Query("SELECT * FROM verses WHERE id = :id")
    fun getVerseById(id: Long): Flow<VerseEntity?>

    @Query("SELECT * FROM verses WHERE id = :id")
    suspend fun getVerseByIdDirect(id: Long): VerseEntity?

    @Query("SELECT COUNT(*) FROM verses")
    suspend fun getCount(): Int

    @Query("SELECT * FROM verses ORDER BY id ASC")
    suspend fun getAllVersesDirect(): List<VerseEntity>

    @Query("DELETE FROM verses WHERE id IN (:ids)")
    suspend fun deleteVersesByIds(ids: List<Long>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerse(verse: VerseEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVerses(verses: List<VerseEntity>)

    @Update
    suspend fun updateVerse(verse: VerseEntity)

    @Delete
    suspend fun deleteVerse(verse: VerseEntity)

    @Query("UPDATE verses SET isFavorite = :isFavorite, updatedAt = :timestamp WHERE id = :id")
    suspend fun updateFavorite(id: Long, isFavorite: Boolean, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE verses SET notes = :notes, updatedAt = :timestamp WHERE id = :id")
    suspend fun updateNotes(id: Long, notes: String, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE verses SET highlightColor = :color, highlightedPhrases = :phrases, updatedAt = :timestamp WHERE id = :id")
    suspend fun updateHighlight(id: Long, color: String, phrases: String, timestamp: Long = System.currentTimeMillis())
}

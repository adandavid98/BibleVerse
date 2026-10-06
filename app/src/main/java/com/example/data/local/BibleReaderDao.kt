package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.BibleBookEntity
import com.example.data.model.BibleReaderVerseEntity
import com.example.data.model.VerseHighlightEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BibleReaderDao {

    // === Books ===
    @Query("SELECT * FROM bible_books ORDER BY orderIndex ASC")
    fun getAllBooks(): Flow<List<BibleBookEntity>>

    @Query("SELECT * FROM bible_books WHERE id = :id LIMIT 1")
    fun getBookById(id: Int): Flow<BibleBookEntity?>

    @Query("SELECT COUNT(*) FROM bible_books")
    suspend fun getBookCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<BibleBookEntity>)

    // === Verses ===
    @Query("""
        SELECT * FROM bible_reader_verses 
        WHERE bookId = :bookId AND chapter = :chapter AND bibleVersion = :version 
        ORDER BY verseNumber ASC
    """)
    fun getVerses(bookId: Int, chapter: Int, version: String): Flow<List<BibleReaderVerseEntity>>

    @Query("""
        SELECT * FROM bible_reader_verses 
        WHERE bookId = :bookId AND chapter = :chapter AND bibleVersion = :version 
        ORDER BY verseNumber ASC
    """)
    suspend fun getVersesSync(bookId: Int, chapter: Int, version: String): List<BibleReaderVerseEntity>

    @Query("SELECT COUNT(*) FROM bible_reader_verses WHERE bookId = :bookId AND chapter = :chapter AND bibleVersion = :version")
    suspend fun getVerseCountForChapter(bookId: Int, chapter: Int, version: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerses(verses: List<BibleReaderVerseEntity>)

    @Query("DELETE FROM bible_reader_verses WHERE text LIKE :pattern")
    suspend fun deleteVersesLike(pattern: String): Int

    @Query("DELETE FROM bible_reader_verses WHERE bookId = :bookId AND chapter = :chapter AND bibleVersion = :version")
    suspend fun deleteVersesForChapter(bookId: Int, chapter: Int, version: String)

    @Query("SELECT COUNT(*) FROM bible_reader_verses WHERE bibleVersion = :version")
    suspend fun getVerseCountForVersion(version: String): Int

    @Query("DELETE FROM bible_reader_verses WHERE bibleVersion = :version")
    suspend fun deleteVersesForVersion(version: String)

    @Query("""
        SELECT * FROM bible_reader_verses 
        WHERE bibleVersion = :version 
        AND replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(lower(text), 'á','a'), 'é','e'), 'í','i'), 'ó','o'), 'ú','u'), 'ü','u'), 'Á','a'), 'É','e'), 'Í','i'), 'Ó','o'), 'Ú','u'), 'Ü','u') LIKE :pattern 
        AND (:bookId IS NULL OR bookId = :bookId) 
        AND (:testament IS NULL OR (:testament = 'OT' AND bookId <= 39) OR (:testament = 'NT' AND bookId >= 40)) 
        ORDER BY bookId ASC, chapter ASC, verseNumber ASC 
        LIMIT :limit
    """)
    suspend fun searchVersesInVersion(
        pattern: String,
        version: String,
        testament: String?,
        bookId: Int?,
        limit: Int = 100
    ): List<BibleReaderVerseEntity>

    // === Highlights ===
    @Query("SELECT * FROM verse_highlights WHERE bookId = :bookId AND chapter = :chapter")
    fun getHighlights(bookId: Int, chapter: Int): Flow<List<VerseHighlightEntity>>

    @Query("SELECT * FROM verse_highlights WHERE bookId = :bookId AND chapter = :chapter")
    suspend fun getHighlightsSync(bookId: Int, chapter: Int): List<VerseHighlightEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHighlight(highlight: VerseHighlightEntity)

    @Query("DELETE FROM verse_highlights WHERE bookId = :bookId AND chapter = :chapter AND verseNumber = :verseNumber")
    suspend fun deleteHighlight(bookId: Int, chapter: Int, verseNumber: Int)

    @Query("DELETE FROM verse_highlights WHERE bookId = :bookId AND chapter = :chapter AND verseNumber IN (:verseNumbers)")
    suspend fun deleteHighlights(bookId: Int, chapter: Int, verseNumbers: List<Int>)

    @Query("SELECT * FROM verse_highlights ORDER BY createdAt DESC")
    fun getAllHighlights(): Flow<List<VerseHighlightEntity>>
}

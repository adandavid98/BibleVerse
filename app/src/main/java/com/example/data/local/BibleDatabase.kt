package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.initial.InitialVersesData
import com.example.data.model.BibleBookEntity
import com.example.data.model.BibleReaderVerseEntity
import com.example.data.model.VerseEntity
import com.example.data.model.VerseHighlightEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        VerseEntity::class,
        BibleBookEntity::class,
        BibleReaderVerseEntity::class,
        VerseHighlightEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class BibleDatabase : RoomDatabase() {

    abstract fun verseDao(): VerseDao
    abstract fun bibleReaderDao(): BibleReaderDao

    companion object {
        @Volatile
        private var INSTANCE: BibleDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE verses ADD COLUMN bibleVersion TEXT NOT NULL DEFAULT 'RVR1960'")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS bible_books (
                        id INTEGER PRIMARY KEY NOT NULL,
                        name TEXT NOT NULL,
                        testament TEXT NOT NULL,
                        chaptersCount INTEGER NOT NULL,
                        category TEXT NOT NULL,
                        abbreviation TEXT NOT NULL,
                        orderIndex INTEGER NOT NULL
                    )
                """.trimIndent())

                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS bible_reader_verses (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        bookId INTEGER NOT NULL,
                        chapter INTEGER NOT NULL,
                        verseNumber INTEGER NOT NULL,
                        text TEXT NOT NULL,
                        bibleVersion TEXT NOT NULL DEFAULT 'RVR1960'
                    )
                """.trimIndent())
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_bible_reader_verses_bookId_chapter_verseNumber ON bible_reader_verses(bookId, chapter, verseNumber)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_bible_reader_verses_bookId_chapter ON bible_reader_verses(bookId, chapter)")

                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS verse_highlights (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        bookId INTEGER NOT NULL,
                        chapter INTEGER NOT NULL,
                        verseNumber INTEGER NOT NULL,
                        colorHex TEXT NOT NULL,
                        createdAt INTEGER NOT NULL
                    )
                """.trimIndent())
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_verse_highlights_bookId_chapter_verseNumber ON verse_highlights(bookId, chapter, verseNumber)")
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("DROP TABLE IF EXISTS bible_reader_verses")
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS bible_reader_verses (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        bookId INTEGER NOT NULL,
                        chapter INTEGER NOT NULL,
                        verseNumber INTEGER NOT NULL,
                        text TEXT NOT NULL,
                        bibleVersion TEXT NOT NULL DEFAULT 'RVR1960',
                        sectionHeading TEXT,
                        isRedLetter INTEGER NOT NULL DEFAULT 0
                    )
                """.trimIndent())
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_bible_reader_verses_bookId_chapter_verseNumber_bibleVersion ON bible_reader_verses(bookId, chapter, verseNumber, bibleVersion)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_bible_reader_verses_bookId_chapter_bibleVersion ON bible_reader_verses(bookId, chapter, bibleVersion)")
            }
        }

        fun getDatabase(context: Context, scope: CoroutineScope? = null): BibleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BibleDatabase::class.java,
                    "bible_verses_database"
                )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                .fallbackToDestructiveMigration(false)
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

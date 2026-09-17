package com.example.data.bible

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.zip.GZIPInputStream

data class OfflineVerseDto(
    val bookId: Int,
    val chapter: Int,
    val verseNumber: Int,
    val text: String
)

object OfflineBibleManager {

    private const val ASSET_NAME = "bible/bible_rvr1960.db.gz"
    private const val DB_FILE_NAME = "bible_rvr1960.db"

    @Volatile
    private var database: SQLiteDatabase? = null

    /**
     * Ensures the local uncompressed SQLite database is extracted to internal storage.
     * Extraction only happens once and takes ~150-200ms.
     */
    suspend fun ensureReady(context: Context) = withContext(Dispatchers.IO) {
        if (database != null && database?.isOpen == true) return@withContext

        synchronized(this) {
            if (database != null && database?.isOpen == true) return@synchronized

            val dbFile = File(context.filesDir, DB_FILE_NAME)
            // If file does not exist or is corrupted (less than 1MB), re-extract
            if (!dbFile.exists() || dbFile.length() < 1_000_000) {
                try {
                    context.assets.open(ASSET_NAME).use { rawIn ->
                        GZIPInputStream(rawIn).use { gzIn ->
                            FileOutputStream(dbFile).use { fileOut ->
                                val buffer = ByteArray(32 * 1024)
                                var bytesRead: Int
                                while (gzIn.read(buffer).also { bytesRead = it } != -1) {
                                    fileOut.write(buffer, 0, bytesRead)
                                }
                                fileOut.flush()
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    return@synchronized
                }
            }

            try {
                database = SQLiteDatabase.openDatabase(
                    dbFile.absolutePath,
                    null,
                    SQLiteDatabase.OPEN_READONLY or SQLiteDatabase.NO_LOCALIZED_COLLATORS
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Reads all verses for a given book and chapter offline directly from SQLite in ~1ms.
     */
    suspend fun getVerses(context: Context, bookId: Int, chapter: Int): List<OfflineVerseDto> = withContext(Dispatchers.IO) {
        ensureReady(context)
        val db = database ?: return@withContext emptyList()

        val results = mutableListOf<OfflineVerseDto>()
        try {
            val cursor = db.rawQuery(
                "SELECT verse, text FROM bible_verses WHERE book = ? AND chapter = ? ORDER BY verse ASC",
                arrayOf(bookId.toString(), chapter.toString())
            )
            cursor.use { c ->
                val colVerse = c.getColumnIndexOrThrow("verse")
                val colText = c.getColumnIndexOrThrow("text")
                while (c.moveToNext()) {
                    val verseNum = c.getInt(colVerse)
                    val text = c.getString(colText)
                    results.add(
                        OfflineVerseDto(
                            bookId = bookId,
                            chapter = chapter,
                            verseNumber = verseNum,
                            text = text
                        )
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        results
    }
}

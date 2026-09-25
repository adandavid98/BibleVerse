package com.example.data.bible

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
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

    private const val TAG = "OfflineBibleManager"
    private const val ASSET_NAME = "bible/bible_rvr1960.db.gz"
    private const val DB_FILE_NAME = "bible_rvr1960.db"
    private const val TOTAL_CANONICAL_VERSES = 31102
    private const val MIN_VALID_SIZE_BYTES = 4_000_000L

    @Volatile
    private var database: SQLiteDatabase? = null

    suspend fun ensureDatabase(context: Context): Boolean = ensureReady(context)

    /**
     * Ensures the local SQLite database is extracted, verified for full canonical integrity
     * (31,102 verses) and ready for immediate sub-millisecond offline reading.
     */
    suspend fun ensureReady(context: Context): Boolean = withContext(Dispatchers.IO) {
        if (isDatabaseHealthy()) return@withContext true

        synchronized(this) {
            if (isDatabaseHealthy()) return@synchronized true

            val dbFile = File(context.filesDir, DB_FILE_NAME)

            // 1. If existing file exists but is unhealthy or truncated, remove it
            if (dbFile.exists() && (!isValidDatabaseFile(dbFile))) {
                Log.w(TAG, "Existing database file is corrupted or incomplete (${dbFile.length()} bytes). Removing.")
                closeCurrentDatabase()
                dbFile.delete()
            }

            // 2. Extract freshly from assets if needed
            if (!dbFile.exists()) {
                val extracted = extractFromAssetsAtomically(context, dbFile)
                if (!extracted) {
                    Log.e(TAG, "Failed to extract offline database from assets.")
                    return@synchronized false
                }
            }

            // 3. Open database with read-write flags to avoid readonly locking issues
            try {
                closeCurrentDatabase()
                database = SQLiteDatabase.openDatabase(
                    dbFile.absolutePath,
                    null,
                    SQLiteDatabase.OPEN_READWRITE or SQLiteDatabase.NO_LOCALIZED_COLLATORS
                )
                if (isDatabaseHealthy()) {
                    Log.d(TAG, "Offline SQLite Bible successfully opened and verified.")
                    return@synchronized true
                } else {
                    Log.e(TAG, "Database opened but failed health check. Re-extracting.")
                    closeCurrentDatabase()
                    dbFile.delete()
                    val reExtracted = extractFromAssetsAtomically(context, dbFile)
                    if (reExtracted) {
                        database = SQLiteDatabase.openDatabase(
                            dbFile.absolutePath,
                            null,
                            SQLiteDatabase.OPEN_READWRITE or SQLiteDatabase.NO_LOCALIZED_COLLATORS
                        )
                    }
                    return@synchronized isDatabaseHealthy()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception opening offline database", e)
                closeCurrentDatabase()
                dbFile.delete()
                return@synchronized false
            }
        }
    }

    private fun isDatabaseHealthy(): Boolean {
        val db = database ?: return false
        if (!db.isOpen) return false
        return try {
            val cursor = db.rawQuery("SELECT COUNT(*) FROM bible_verses", null)
            val count = cursor.use { c ->
                if (c.moveToFirst()) c.getInt(0) else 0
            }
            count >= TOTAL_CANONICAL_VERSES
        } catch (e: Exception) {
            Log.w(TAG, "Health check failed on open database", e)
            false
        }
    }

    private fun isValidDatabaseFile(file: File): Boolean {
        if (!file.exists() || file.length() < MIN_VALID_SIZE_BYTES) return false
        var testDb: SQLiteDatabase? = null
        return try {
            testDb = SQLiteDatabase.openDatabase(
                file.absolutePath,
                null,
                SQLiteDatabase.OPEN_READWRITE or SQLiteDatabase.NO_LOCALIZED_COLLATORS
            )
            val cursor = testDb.rawQuery("SELECT COUNT(*) FROM bible_verses", null)
            val count = cursor.use { c ->
                if (c.moveToFirst()) c.getInt(0) else 0
            }
            count >= TOTAL_CANONICAL_VERSES
        } catch (e: Exception) {
            Log.w(TAG, "isValidDatabaseFile check failed for ${file.name}", e)
            false
        } finally {
            try { testDb?.close() } catch (_: Exception) {}
        }
    }

    private fun extractFromAssetsAtomically(context: Context, targetFile: File): Boolean {
        val tempFile = File(targetFile.parentFile, "${targetFile.name}.tmp")
        if (tempFile.exists()) tempFile.delete()

        val candidates = listOf("bible/bible_rvr1960.db", "bible/bible_rvr1960.db.gz")
        for (assetPath in candidates) {
            try {
                context.assets.open(assetPath).use { rawIn ->
                    val bis = java.io.BufferedInputStream(rawIn)
                    bis.mark(4)
                    val b1 = bis.read()
                    val b2 = bis.read()
                    bis.reset()

                    val isGzip = (b1 == 0x1f && b2 == 0x8b)
                    val inputStream = if (isGzip) GZIPInputStream(bis) else bis

                    FileOutputStream(tempFile).use { fileOut ->
                        val buffer = ByteArray(64 * 1024)
                        var bytesRead: Int
                        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                            fileOut.write(buffer, 0, bytesRead)
                        }
                        fileOut.flush()
                    }
                }

                if (isValidDatabaseFile(tempFile)) {
                    if (targetFile.exists()) targetFile.delete()
                    val renamed = tempFile.renameTo(targetFile)
                    if (!renamed) {
                        tempFile.copyTo(targetFile, overwrite = true)
                        tempFile.delete()
                    }
                    Log.d(TAG, "Base de datos RVR1960 extraída exitosamente desde $assetPath")
                    return true
                } else {
                    Log.e(TAG, "Extracted temp file from $assetPath failed integrity check (${tempFile.length()} bytes)")
                    if (tempFile.exists()) tempFile.delete()
                }
            } catch (e: Exception) {
                Log.w(TAG, "No se pudo extraer desde $assetPath: ${e.message}")
                if (tempFile.exists()) tempFile.delete()
            }
        }
        return false
    }

    private fun closeCurrentDatabase() {
        try {
            database?.close()
        } catch (_: Exception) {}
        database = null
    }

    /**
     * Reads all verses for a given book and chapter offline directly from SQLite in ~1ms.
     * Includes self-healing: if an unexpected corruption happens, it auto-repairs and retries.
     */
    suspend fun getVerses(context: Context, bookId: Int, chapter: Int): List<OfflineVerseDto> = withContext(Dispatchers.IO) {
        val isReady = ensureReady(context)
        if (!isReady) return@withContext emptyList()

        val results = readVersesQuery(bookId, chapter)
        if (results.isNotEmpty()) {
            return@withContext results
        }

        // Check if database health is degraded before deleting
        if (!isDatabaseHealthy()) {
            Log.w(TAG, "Database health degraded for book $bookId, chapter $chapter. Re-extracting.")
            synchronized(this@OfflineBibleManager) {
                val dbFile = File(context.filesDir, DB_FILE_NAME)
                closeCurrentDatabase()
                dbFile.delete()
            }
            val recovered = ensureReady(context)
            if (recovered) {
                return@withContext readVersesQuery(bookId, chapter)
            }
        }
        emptyList()
    }

    private fun readVersesQuery(bookId: Int, chapter: Int): List<OfflineVerseDto> {
        val db = database ?: return emptyList()
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
            Log.e(TAG, "Query error for book $bookId, chapter $chapter", e)
        }
        return results
    }

    /**
     * Returns only the count of verses for a given book/chapter without loading text.
     */
    suspend fun getVerseCount(context: Context, bookId: Int, chapter: Int): Int = withContext(Dispatchers.IO) {
        ensureReady(context)
        val db = database ?: return@withContext 0
        try {
            val cursor = db.rawQuery(
                "SELECT COUNT(*) FROM bible_verses WHERE book = ? AND chapter = ?",
                arrayOf(bookId.toString(), chapter.toString())
            )
            cursor.use { c ->
                if (c.moveToFirst()) c.getInt(0) else 0
            }
        } catch (e: Exception) {
            Log.e(TAG, "Count error for book $bookId, chapter $chapter", e)
            0
        }
    }
}

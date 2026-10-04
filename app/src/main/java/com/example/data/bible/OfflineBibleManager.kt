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
    // Minimum verse rows a bundled database must have to be considered complete. Translations such as
    // TLA/DHH merge verse ranges, so their row count is lower than the 31,102 canonical verses.
    private val minVerses = mapOf("RVR1960" to 31100, "TLA" to 26000, "DHH" to 30000, "NBLA" to 31000)
    private fun minFor(code: String) = minVerses[code.uppercase()] ?: 31000
    private const val MIN_VALID_SIZE_BYTES = 1_000_000L

    // In-memory caches for open databases
    private val databases = mutableMapOf<String, SQLiteDatabase>()

    // Mappings for known offline assets
    private val offlineAssets = mapOf(
        "RVR1960" to "bible/bible_rvr1960.db.gz",
        "TLA" to "bible/bible_tla.db.gz",
        "DHH" to "bible/bible_dhh94pc.db.gz",
        "NBLA" to "bible/bible_nbla.db.gz"
    )

    fun isBundled(code: String): Boolean = offlineAssets.containsKey(code.uppercase().trim())
    fun bundledCodes(): Set<String> = offlineAssets.keys

    suspend fun ensureDatabase(context: Context, versionCode: String = "RVR1960"): Boolean = ensureReady(context, versionCode)

    suspend fun ensureReady(context: Context, versionCode: String = "RVR1960"): Boolean = withContext(Dispatchers.IO) {
        val code = versionCode.uppercase()
        if (!offlineAssets.containsKey(code)) {
            // Not a bundled offline DB version
            return@withContext false
        }
        
        if (isDatabaseHealthy(code)) return@withContext true

        synchronized(this@OfflineBibleManager) {
            if (isDatabaseHealthy(code)) return@synchronized true

            val dbFileName = "bible_${code.lowercase()}.db"
            val dbFile = File(context.filesDir, dbFileName)

            if (dbFile.exists() && (!isValidDatabaseFile(dbFile, code))) {
                closeCurrentDatabase(code)
                dbFile.delete()
            }

            if (!dbFile.exists()) {
                val extracted = extractFromAssetsAtomically(context, dbFile, code)
                if (!extracted) {
                    return@synchronized false
                }
            }

            try {
                closeCurrentDatabase(code)
                val db = SQLiteDatabase.openDatabase(
                    dbFile.absolutePath,
                    null,
                    SQLiteDatabase.OPEN_READWRITE or SQLiteDatabase.NO_LOCALIZED_COLLATORS
                )
                databases[code] = db
                
                if (isDatabaseHealthy(code)) {
                    return@synchronized true
                } else {
                    closeCurrentDatabase(code)
                    dbFile.delete()
                    val reExtracted = extractFromAssetsAtomically(context, dbFile, code)
                    if (reExtracted) {
                        databases[code] = SQLiteDatabase.openDatabase(
                            dbFile.absolutePath,
                            null,
                            SQLiteDatabase.OPEN_READWRITE or SQLiteDatabase.NO_LOCALIZED_COLLATORS
                        )
                    }
                    return@synchronized isDatabaseHealthy(code)
                }
            } catch (e: Exception) {
                closeCurrentDatabase(code)
                dbFile.delete()
                return@synchronized false
            }
        }
    }

    private fun isDatabaseHealthy(versionCode: String): Boolean {
        val db = databases[versionCode.uppercase()] ?: return false
        if (!db.isOpen) return false
        return try {
            val cursor = db.rawQuery("SELECT COUNT(*) FROM bible_verses", null)
            val count = cursor.use { c ->
                if (c.moveToFirst()) c.getInt(0) else 0
            }
            count >= minFor(versionCode)
        } catch (e: Exception) {
            false
        }
    }

    private fun isValidDatabaseFile(file: File, versionCode: String): Boolean {
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
            count >= minFor(versionCode)
        } catch (e: Exception) {
            false
        } finally {
            try { testDb?.close() } catch (_: Exception) {}
        }
    }

    private fun extractFromAssetsAtomically(context: Context, targetFile: File, versionCode: String): Boolean {
        val tempFile = File(targetFile.parentFile, "${targetFile.name}.tmp")
        if (tempFile.exists()) tempFile.delete()

        val assetPath = offlineAssets[versionCode.uppercase()] ?: return false
        
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

                if (isValidDatabaseFile(tempFile, versionCode)) {
                    if (targetFile.exists()) targetFile.delete()
                    val renamed = tempFile.renameTo(targetFile)
                    if (!renamed) {
                        tempFile.copyTo(targetFile, overwrite = true)
                        tempFile.delete()
                    }
                    return true
                } else {
                    if (tempFile.exists()) tempFile.delete()
                }
        } catch (e: Exception) {
            if (tempFile.exists()) tempFile.delete()
        }
        return false
    }

    private fun closeCurrentDatabase(versionCode: String) {
        val code = versionCode.uppercase()
        try {
            databases[code]?.close()
        } catch (_: Exception) {}
        databases.remove(code)
    }

    suspend fun getVerses(context: Context, bookId: Int, chapter: Int, versionCode: String = "RVR1960"): List<OfflineVerseDto> = withContext(Dispatchers.IO) {
        val code = versionCode.uppercase()
        val isReady = ensureReady(context, code)
        if (!isReady) return@withContext emptyList()

        val results = readVersesQuery(bookId, chapter, code)
        if (results.isNotEmpty()) {
            return@withContext results
        }

        if (!isDatabaseHealthy(code)) {
            synchronized(this@OfflineBibleManager) {
                val dbFile = File(context.filesDir, "bible_${code.lowercase()}.db")
                closeCurrentDatabase(code)
                dbFile.delete()
            }
            val recovered = ensureReady(context, code)
            if (recovered) {
                return@withContext readVersesQuery(bookId, chapter, code)
            }
        }
        emptyList()
    }

    private fun readVersesQuery(bookId: Int, chapter: Int, versionCode: String): List<OfflineVerseDto> {
        val db = databases[versionCode.uppercase()] ?: return emptyList()
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
                    val rawText = c.getString(colText)
                    results.add(
                        OfflineVerseDto(
                            bookId = bookId,
                            chapter = chapter,
                            verseNumber = verseNum,
                            text = cleanVerseText(rawText)
                        )
                    )
                }
            }
        } catch (e: Exception) {}
        return results
    }

    suspend fun getVerseCount(context: Context, bookId: Int, chapter: Int, versionCode: String = "RVR1960"): Int = withContext(Dispatchers.IO) {
        val code = versionCode.uppercase()
        ensureReady(context, code)
        val db = databases[code] ?: return@withContext 0
        try {
            val cursor = db.rawQuery(
                "SELECT COUNT(*) FROM bible_verses WHERE book = ? AND chapter = ?",
                arrayOf(bookId.toString(), chapter.toString())
            )
            cursor.use { c ->
                if (c.moveToFirst()) c.getInt(0) else 0
            }
        } catch (e: Exception) {
            0
        }
    }

    suspend fun searchVerses(
        context: Context,
        query: String,
        testament: String? = null,
        bookId: Int? = null,
        limit: Int = 100,
        versionCode: String = "RVR1960"
    ): List<OfflineVerseDto> = withContext(Dispatchers.IO) {
        val trimmed = query.trim()
        val code = versionCode.uppercase()
        if (trimmed.isEmpty()) return@withContext emptyList()
        ensureReady(context, code)
        val db = databases[code] ?: return@withContext emptyList()
        val results = mutableListOf<OfflineVerseDto>()
        try {
            val conditions = mutableListOf<String>()
            val args = mutableListOf<String>()

            conditions.add("text LIKE ?")
            args.add("%$trimmed%")

            if (bookId != null && bookId in 1..66) {
                conditions.add("book = ?")
                args.add(bookId.toString())
            } else if (testament == "OT") {
                conditions.add("book <= 39")
            } else if (testament == "NT") {
                conditions.add("book >= 40")
            }

            val whereClause = if (conditions.isNotEmpty()) "WHERE " + conditions.joinToString(" AND ") else ""
            val sql = "SELECT book, chapter, verse, text FROM bible_verses $whereClause ORDER BY book ASC, chapter ASC, verse ASC LIMIT ?"
            args.add(limit.toString())

            val cursor = db.rawQuery(sql, args.toTypedArray())
            cursor.use { c ->
                val colBook = c.getColumnIndexOrThrow("book")
                val colChap = c.getColumnIndexOrThrow("chapter")
                val colVerse = c.getColumnIndexOrThrow("verse")
                val colText = c.getColumnIndexOrThrow("text")
                while (c.moveToNext()) {
                    val rawText = c.getString(colText)
                    results.add(
                        OfflineVerseDto(
                            bookId = c.getInt(colBook),
                            chapter = c.getInt(colChap),
                            verseNumber = c.getInt(colVerse),
                            text = cleanVerseText(rawText)
                        )
                    )
                }
            }
        } catch (e: Exception) {}
        results
    }

    fun cleanVerseText(raw: String?): String {
        if (raw.isNullOrBlank()) return ""
        return raw
            .replace(Regex("(?i)<br\\s*/?>"), " ")
            .replace(Regex("<[^>]*>"), "")
            .replace("&nbsp;", " ")
            .replace("&quot;", "\"")
            .replace("&amp;", "&")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&#39;", "'")
            .replace("\\s+".toRegex(), " ")
            .trim()
    }
}

package com.example.data.bible

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.concurrent.ConcurrentHashMap
import java.util.zip.GZIPInputStream

data class OfflineVerseDto(
    val bookId: Int,
    val chapter: Int,
    val verseNumber: Int,
    val text: String
)

object OfflineBibleManager {

    private const val TAG = "OfflineBibleManager"
    private const val ASSET_VERSION_STAMP = "v_clean_2026_10_03_v4"

    // Minimum verse rows required for each bundled version to be considered complete
    private val minVerses = mapOf(
        "RVR1960" to 31100,
        "TLA" to 25000,
        "DHH" to 29000,
        "DHH94PC" to 29000,
        "NBLA" to 30000
    )
    private fun minFor(code: String) = minVerses[code.uppercase().trim()] ?: 25000
    private const val MIN_VALID_SIZE_BYTES = 500_000L

    // In-memory caches for open databases (thread-safe)
    private val databases = ConcurrentHashMap<String, SQLiteDatabase>()

    // Mappings for known offline assets
    private val offlineAssets = mapOf(
        "RVR1960" to "bible/bible_rvr1960.db.gz",
        "TLA" to "bible/bible_tla.db.gz",
        "DHH" to "bible/bible_dhh94pc.db.gz",
        "DHH94PC" to "bible/bible_dhh94pc.db.gz",
        "NBLA" to "bible/bible_nbla.db.gz"
    )

    fun isBundled(code: String): Boolean = offlineAssets.containsKey(code.uppercase().trim())
    fun bundledCodes(): Set<String> = setOf("RVR1960", "TLA", "DHH", "NBLA")

    suspend fun ensureDatabase(context: Context, versionCode: String = "RVR1960"): Boolean =
        ensureReady(context, versionCode)

    suspend fun ensureReady(context: Context, versionCode: String = "RVR1960"): Boolean = withContext(Dispatchers.IO) {
        val code = versionCode.uppercase().trim()
        if (!offlineAssets.containsKey(code)) {
            Log.w(TAG, "[$code] No asset registered for version")
            return@withContext false
        }

        if (isDatabaseHealthy(code)) return@withContext true

        synchronized(this@OfflineBibleManager) {
            if (isDatabaseHealthy(code)) return@synchronized true

            val prefs = context.getSharedPreferences("offline_bible_manager_prefs", Context.MODE_PRIVATE)
            val versionKey = "extracted_asset_version_${code.lowercase()}"
            val currentStamp = prefs.getString(versionKey, null)

            val dbFileName = "bible_${code.lowercase()}.db"
            val dbFile = File(context.filesDir, dbFileName)

            // If the database version stamp changed or the file is missing/invalid, force re-extraction from assets
            val needsExtraction = !dbFile.exists() ||
                    currentStamp != ASSET_VERSION_STAMP ||
                    !isValidDatabaseFile(dbFile, code)

            if (needsExtraction) {
                Log.i(TAG, "[$code] Re-extracting database: fileExists=${dbFile.exists()}, stamp=$currentStamp, expected=$ASSET_VERSION_STAMP")
                closeCurrentDatabase(code)
                try { SQLiteDatabase.deleteDatabase(dbFile) } catch (_: Exception) { dbFile.delete() }

                val extracted = extractFromAssetsAtomically(context, dbFile, code)
                if (!extracted) {
                    Log.e(TAG, "[$code] Extraction failed from assets")
                    return@synchronized false
                }
                prefs.edit().putString(versionKey, ASSET_VERSION_STAMP).apply()
            }

            try {
                closeCurrentDatabase(code)
                val db = SQLiteDatabase.openDatabase(
                    dbFile.absolutePath,
                    null,
                    SQLiteDatabase.OPEN_READONLY or SQLiteDatabase.NO_LOCALIZED_COLLATORS
                )
                databases[code] = db

                val healthy = isDatabaseHealthy(code)
                if (healthy) {
                    Log.i(TAG, "[$code] Database opened successfully and verified healthy")
                    return@synchronized true
                } else {
                    Log.e(TAG, "[$code] Database unhealthy after opening. Forcing clean re-extraction...")
                    closeCurrentDatabase(code)
                    try { SQLiteDatabase.deleteDatabase(dbFile) } catch (_: Exception) { dbFile.delete() }
                    val reExtracted = extractFromAssetsAtomically(context, dbFile, code)
                    if (reExtracted) {
                        val newDb = SQLiteDatabase.openDatabase(
                            dbFile.absolutePath,
                            null,
                            SQLiteDatabase.OPEN_READONLY or SQLiteDatabase.NO_LOCALIZED_COLLATORS
                        )
                        databases[code] = newDb
                        prefs.edit().putString(versionKey, ASSET_VERSION_STAMP).apply()
                    }
                    return@synchronized isDatabaseHealthy(code)
                }
            } catch (e: Exception) {
                Log.e(TAG, "[$code] Exception opening database: ${e.message}. Forcing re-extraction.", e)
                closeCurrentDatabase(code)
                try { SQLiteDatabase.deleteDatabase(dbFile) } catch (_: Exception) { dbFile.delete() }
                val reExtracted = extractFromAssetsAtomically(context, dbFile, code)
                if (reExtracted) {
                    try {
                        val newDb = SQLiteDatabase.openDatabase(
                            dbFile.absolutePath,
                            null,
                            SQLiteDatabase.OPEN_READONLY or SQLiteDatabase.NO_LOCALIZED_COLLATORS
                        )
                        databases[code] = newDb
                        prefs.edit().putString(versionKey, ASSET_VERSION_STAMP).apply()
                    } catch (e2: Exception) {
                        Log.e(TAG, "[$code] Fatal: Exception after re-extraction: ${e2.message}", e2)
                        return@synchronized false
                    }
                }
                return@synchronized isDatabaseHealthy(code)
            }
        }
    }

    private fun isDatabaseHealthy(versionCode: String): Boolean {
        val code = versionCode.uppercase().trim()
        val db = databases[code] ?: return false
        if (!db.isOpen) return false
        return try {
            val cursor = db.rawQuery("SELECT COUNT(*) FROM bible_verses", null)
            val count = cursor.use { c ->
                if (c.moveToFirst()) c.getInt(0) else 0
            }
            val min = minFor(code)
            val ok = count >= min
            if (!ok) {
                Log.w(TAG, "[$code] Health check failed: rows=$count < min=$min")
            }
            ok
        } catch (e: Exception) {
            Log.e(TAG, "[$code] Error checking health: ${e.message}", e)
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
                SQLiteDatabase.OPEN_READONLY or SQLiteDatabase.NO_LOCALIZED_COLLATORS
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
        val code = versionCode.uppercase().trim()
        val assetPath = offlineAssets[code] ?: run {
            Log.e(TAG, "[$code] No assetPath found")
            return false
        }

        val tempFile = File(targetFile.parentFile, "${targetFile.name}.tmp")
        if (tempFile.exists()) tempFile.delete()

        try {
            Log.i(TAG, "[$code] Opening asset: $assetPath")
            context.assets.open(assetPath).use { rawIn ->
                val bis = java.io.BufferedInputStream(rawIn)
                bis.mark(4)
                val b1 = bis.read()
                val b2 = bis.read()
                bis.reset()

                val isGzip = (b1 == 0x1f && b2 == 0x8b)
                val inputStream: InputStream = if (isGzip) GZIPInputStream(bis) else bis

                FileOutputStream(tempFile).use { fileOut ->
                    val buffer = ByteArray(64 * 1024)
                    var bytesRead: Int
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        fileOut.write(buffer, 0, bytesRead)
                    }
                    fileOut.flush()
                }
            }

            Log.i(TAG, "[$code] Temp file extracted, size=${tempFile.length()} bytes")
            if (tempFile.length() < MIN_VALID_SIZE_BYTES) {
                Log.e(TAG, "[$code] Extracted temp file too small: ${tempFile.length()}")
                tempFile.delete()
                return false
            }

            // Quick validation check before replacing target
            if (isValidDatabaseFile(tempFile, code)) {
                try { SQLiteDatabase.deleteDatabase(targetFile) } catch (_: Exception) { targetFile.delete() }
                val renamed = tempFile.renameTo(targetFile)
                if (!renamed) {
                    tempFile.copyTo(targetFile, overwrite = true)
                    tempFile.delete()
                }
                Log.i(TAG, "[$code] Database atomically deployed to ${targetFile.absolutePath}")
                return true
            } else {
                Log.e(TAG, "[$code] Extracted temp file failed validity test")
                tempFile.delete()
                return false
            }
        } catch (e: Exception) {
            Log.e(TAG, "[$code] Error during extraction: ${e.message}", e)
            if (tempFile.exists()) tempFile.delete()
            return false
        }
    }

    private fun closeCurrentDatabase(versionCode: String) {
        val code = versionCode.uppercase().trim()
        try {
            databases[code]?.close()
        } catch (_: Exception) {}
        databases.remove(code)
    }

    suspend fun getVerses(context: Context, bookId: Int, chapter: Int, versionCode: String = "RVR1960"): List<OfflineVerseDto> = withContext(Dispatchers.IO) {
        val code = versionCode.uppercase().trim()
        val isReady = ensureReady(context, code)
        if (!isReady) {
            Log.e(TAG, "[$code] getVerses: ensureReady returned false for $code ($bookId:$chapter)")
            return@withContext emptyList()
        }

        val results = readVersesQuery(bookId, chapter, code)
        if (results.isNotEmpty()) {
            return@withContext results
        }

        Log.w(TAG, "[$code] Query returned 0 verses for $bookId:$chapter. Checking health...")
        if (!isDatabaseHealthy(code)) {
            synchronized(this@OfflineBibleManager) {
                val dbFile = File(context.filesDir, "bible_${code.lowercase()}.db")
                closeCurrentDatabase(code)
                try { SQLiteDatabase.deleteDatabase(dbFile) } catch (_: Exception) { dbFile.delete() }
            }
            val recovered = ensureReady(context, code)
            if (recovered) {
                return@withContext readVersesQuery(bookId, chapter, code)
            }
        }
        emptyList()
    }

    private fun readVersesQuery(bookId: Int, chapter: Int, versionCode: String): List<OfflineVerseDto> {
        val code = versionCode.uppercase().trim()
        val db = databases[code] ?: run {
            Log.e(TAG, "[$code] readVersesQuery: No open DB for $code")
            return emptyList()
        }
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
        } catch (e: Exception) {
            Log.e(TAG, "[$code] readVersesQuery failed for $bookId:$chapter: ${e.message}", e)
        }
        return results
    }

    suspend fun getVerseCount(context: Context, bookId: Int, chapter: Int, versionCode: String = "RVR1960"): Int = withContext(Dispatchers.IO) {
        val code = versionCode.uppercase().trim()
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
            Log.e(TAG, "[$code] getVerseCount failed for $bookId:$chapter: ${e.message}", e)
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
        val code = versionCode.uppercase().trim()
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
        } catch (e: Exception) {
            Log.e(TAG, "[$code] searchVerses failed: ${e.message}", e)
        }
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

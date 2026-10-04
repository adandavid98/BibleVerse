package com.example.data.bible

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
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

    data class VersionAssetConfig(
        val assetPath: String,
        val dbFileName: String,
        val minVerses: Int
    )

    private val ASSET_VERSIONS = mapOf(
        "RVR1960" to VersionAssetConfig("bible/bible_rvr1960.db.gz", "bible_rvr1960.db", 20000),
        "NBLA" to VersionAssetConfig("bible/bible_nbla.db.gz", "bible_nbla.db", 20000),
        "TLA" to VersionAssetConfig("bible/bible_tla.db.gz", "bible_tla.db", 20000),
        "DHH" to VersionAssetConfig("bible/bible_dhh94pc.db.gz", "bible_dhh94pc.db", 20000),
        "DHH94PC" to VersionAssetConfig("bible/bible_dhh94pc.db.gz", "bible_dhh94pc.db", 20000)
    )

    private val databases = ConcurrentHashMap<String, SQLiteDatabase>()
    private val locks = ConcurrentHashMap<String, Any>()

    fun normalizeVersion(version: String): String {
        return when (val upper = version.uppercase().trim()) {
            "RV1960", "REINA-VALERA 1960" -> "RVR1960"
            "DHH94PC" -> "DHH"
            else -> upper
        }
    }

    fun isAssetVersion(version: String): Boolean {
        val norm = normalizeVersion(version)
        return ASSET_VERSIONS.containsKey(norm)
    }

    private fun getLockFor(version: String): Any {
        return locks.computeIfAbsent(version) { Any() }
    }

    private fun getDbFile(context: Context, fileName: String): File {
        val standardDb = context.getDatabasePath(fileName)
        val filesDb = File(context.filesDir, fileName)
        if (filesDb.exists() && filesDb.length() > 1_000_000L) {
            return filesDb
        }
        if (standardDb.exists() && standardDb.length() > 1_000_000L) {
            return standardDb
        }
        val parent = standardDb.parentFile ?: context.filesDir
        if (!parent.exists()) parent.mkdirs()
        return standardDb
    }

    suspend fun ensureDatabase(context: Context): Boolean = ensureReady(context, "RVR1960")

    suspend fun ensureAllAssetDatabases(context: Context) = withContext(Dispatchers.IO) {
        for (version in ASSET_VERSIONS.keys) {
            try {
                ensureReady(context, version)
            } catch (e: Exception) {
                Log.w(TAG, "Error pre-calentando versión offline $version: ${e.message}")
            }
        }
    }

    /**
     * Ensures the local SQLite database for the requested version is extracted, verified,
     * and ready for immediate sub-millisecond offline reading.
     */
    suspend fun ensureReady(context: Context, version: String = "RVR1960"): Boolean = withContext(Dispatchers.IO) {
        val normVersion = normalizeVersion(version)
        val config = ASSET_VERSIONS[normVersion] ?: return@withContext false

        val existing = databases[normVersion]
        if (existing != null && existing.isOpen && isDatabaseHealthy(existing, config.minVerses)) {
            return@withContext true
        }

        synchronized(getLockFor(normVersion)) {
            val current = databases[normVersion]
            if (current != null && current.isOpen && isDatabaseHealthy(current, config.minVerses)) {
                return@synchronized true
            }

            val dbFile = getDbFile(context, config.dbFileName)

            // 1. If existing file is corrupted or incomplete, remove it
            if (dbFile.exists() && (!isValidDatabaseFile(dbFile, config.minVerses))) {
                Log.w(TAG, "Archivo $normVersion corrupto o incompleto (${dbFile.length()} bytes). Eliminando.")
                closeDatabase(normVersion)
                try { SQLiteDatabase.deleteDatabase(dbFile) } catch (_: Exception) { dbFile.delete() }
            }

            // 2. Extract freshly from assets if needed
            if (!dbFile.exists()) {
                val extracted = extractFromAssetsAtomically(context, config.assetPath, dbFile, config.minVerses)
                if (!extracted) {
                    Log.e(TAG, "Fallo al extraer base de datos $normVersion desde assets.")
                    return@synchronized false
                }
            }

            // 3. Open database with read-write flags to avoid readonly locking issues
            try {
                closeDatabase(normVersion)
                val db = SQLiteDatabase.openDatabase(
                    dbFile.absolutePath,
                    null,
                    SQLiteDatabase.OPEN_READWRITE or SQLiteDatabase.NO_LOCALIZED_COLLATORS
                )
                if (isDatabaseHealthy(db, config.minVerses)) {
                    databases[normVersion] = db
                    Log.d(TAG, "Base SQLite $normVersion abierta y verificada con éxito.")
                    return@synchronized true
                } else {
                    Log.e(TAG, "Base $normVersion falló verificación de integridad. Re-extrayendo.")
                    try { db.close() } catch (_: Exception) {}
                    try { SQLiteDatabase.deleteDatabase(dbFile) } catch (_: Exception) { dbFile.delete() }

                    val reExtracted = extractFromAssetsAtomically(context, config.assetPath, dbFile, config.minVerses)
                    if (reExtracted) {
                        val reDb = SQLiteDatabase.openDatabase(
                            dbFile.absolutePath,
                            null,
                            SQLiteDatabase.OPEN_READWRITE or SQLiteDatabase.NO_LOCALIZED_COLLATORS
                        )
                        databases[normVersion] = reDb
                        return@synchronized isDatabaseHealthy(reDb, config.minVerses)
                    }
                    return@synchronized false
                }
            } catch (e: Exception) {
                Log.e(TAG, "Excepción abriendo base $normVersion", e)
                closeDatabase(normVersion)
                try { SQLiteDatabase.deleteDatabase(dbFile) } catch (_: Exception) { dbFile.delete() }
                return@synchronized false
            }
        }
    }

    private fun isDatabaseHealthy(db: SQLiteDatabase?, minVerses: Int): Boolean {
        if (db == null || !db.isOpen) return false
        return try {
            val cursor = db.rawQuery("SELECT COUNT(*) FROM bible_verses", null)
            val count = cursor.use { c ->
                if (c.moveToFirst()) c.getInt(0) else 0
            }
            count >= minVerses
        } catch (e: Exception) {
            Log.w(TAG, "Fallo en health check", e)
            false
        }
    }

    private fun isValidDatabaseFile(file: File, minVerses: Int): Boolean {
        if (!file.exists() || file.length() < 1_000_000L) return false
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
            count >= minVerses
        } catch (e: Exception) {
            Log.w(TAG, "Verificación isValidDatabaseFile falló para ${file.name}: ${e.message}")
            false
        } finally {
            try { testDb?.close() } catch (_: Exception) {}
        }
    }

    private fun extractFromAssetsAtomically(
        context: Context,
        assetPath: String,
        targetFile: File,
        minVerses: Int
    ): Boolean {
        val parent = targetFile.parentFile ?: context.filesDir
        if (!parent.exists()) parent.mkdirs()
        val tempFile = File(parent, "${targetFile.name}.tmp")
        if (tempFile.exists()) tempFile.delete()

        try {
            context.assets.open(assetPath).use { rawIn ->
                val bis = BufferedInputStream(rawIn, 64 * 1024)
                val isGzip = assetPath.endsWith(".gz", ignoreCase = true)
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

            if (isValidDatabaseFile(tempFile, minVerses)) {
                try { SQLiteDatabase.deleteDatabase(targetFile) } catch (_: Exception) { targetFile.delete() }
                val renamed = tempFile.renameTo(targetFile)
                if (!renamed) {
                    tempFile.copyTo(targetFile, overwrite = true)
                    tempFile.delete()
                }
                Log.d(TAG, "Base de datos extraída exitosamente desde $assetPath a ${targetFile.name}")
                return true
            } else {
                Log.e(TAG, "Archivo extraído desde $assetPath falló integridad (${tempFile.length()} bytes)")
                if (tempFile.exists()) tempFile.delete()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error extrayendo $assetPath a ${targetFile.name}", e)
            if (tempFile.exists()) tempFile.delete()
        }
        return false
    }

    private fun closeDatabase(version: String) {
        val db = databases.remove(version)
        try { db?.close() } catch (_: Exception) {}
    }

    /**
     * Reads all verses for a given book, chapter, and version offline directly from SQLite in ~1ms.
     * All verse text is sanitized through BibleTextSanitizer.
     */
    suspend fun getVerses(
        context: Context,
        bookId: Int,
        chapter: Int,
        version: String = "RVR1960"
    ): List<OfflineVerseDto> = withContext(Dispatchers.IO) {
        val normVersion = normalizeVersion(version)
        val config = ASSET_VERSIONS[normVersion] ?: return@withContext emptyList()

        val isReady = ensureReady(context, normVersion)
        if (!isReady) return@withContext emptyList()

        val results = readVersesQuery(normVersion, bookId, chapter)
        if (results.isNotEmpty()) {
            return@withContext results
        }

        val db = databases[normVersion]
        if (!isDatabaseHealthy(db, config.minVerses)) {
            Log.w(TAG, "Salud degradada en $normVersion para libro $bookId, cap $chapter. Re-extrayendo.")
            synchronized(getLockFor(normVersion)) {
                val dbFile = File(context.filesDir, config.dbFileName)
                closeDatabase(normVersion)
                try { SQLiteDatabase.deleteDatabase(dbFile) } catch (_: Exception) { dbFile.delete() }
            }
            val recovered = ensureReady(context, normVersion)
            if (recovered) {
                return@withContext readVersesQuery(normVersion, bookId, chapter)
            }
        }
        emptyList()
    }

    private fun readVersesQuery(version: String, bookId: Int, chapter: Int): List<OfflineVerseDto> {
        val db = databases[version] ?: return emptyList()
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
            Log.e(TAG, "Error de consulta para $version libro $bookId, cap $chapter", e)
        }
        return results
    }

    /**
     * Returns only the count of verses for a given book/chapter without loading text.
     */
    suspend fun getVerseCount(
        context: Context,
        bookId: Int,
        chapter: Int,
        version: String = "RVR1960"
    ): Int = withContext(Dispatchers.IO) {
        val norm = normalizeVersion(version)
        ensureReady(context, norm)
        val db = databases[norm] ?: return@withContext 0
        try {
            val cursor = db.rawQuery(
                "SELECT COUNT(*) FROM bible_verses WHERE book = ? AND chapter = ?",
                arrayOf(bookId.toString(), chapter.toString())
            )
            cursor.use { c ->
                if (c.moveToFirst()) c.getInt(0) else 0
            }
        } catch (e: Exception) {
            Log.e(TAG, "Count error para $norm libro $bookId, cap $chapter", e)
            0
        }
    }

    /**
     * Searches verses locally and offline in the requested SQLite database.
     */
    suspend fun searchVerses(
        context: Context,
        query: String,
        testament: String? = null,
        bookId: Int? = null,
        limit: Int = 100,
        version: String = "RVR1960"
    ): List<OfflineVerseDto> = withContext(Dispatchers.IO) {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) return@withContext emptyList()
        val norm = normalizeVersion(version)
        ensureReady(context, norm)
        val db = databases[norm] ?: return@withContext emptyList()
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
            Log.e(TAG, "Search error en $norm para query: $query", e)
        }
        results
    }

    /**
     * Cleans and normalizes biblical verse text using BibleTextSanitizer.
     */
    fun cleanVerseText(raw: String?): String {
        return BibleTextSanitizer.sanitize(raw)
    }
}

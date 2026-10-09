package com.example.data.bible

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

data class OfflineVerseDto(
    val bookId: Int,
    val chapter: Int,
    val verseNumber: Int,
    val text: String
)

object OfflineBibleManager {

    private const val TAG = "OfflineBibleManager"
    private const val ASSET_PATH = "bible/bible_offline.db"
    private const val DB_FILE_NAME = "bible_offline.db"

    @Volatile
    private var database: SQLiteDatabase? = null
    private val lock = Any()

    fun normalizeVersion(version: String): String {
        return when (val upper = version.uppercase().trim()) {
            "RV1960", "REINA-VALERA 1960" -> "RVR1960"
            "DHH94PC" -> "DHH"
            "NUEVA VERSIÓN INTERNACIONAL" -> "NVI"
            else -> upper
        }
    }

    private val ASSET_VERSIONS = setOf("RVR1960", "NBLA", "TLA", "DHH", "NVI")

    fun isAssetVersion(version: String): Boolean {
        val norm = normalizeVersion(version)
        return ASSET_VERSIONS.contains(norm)
    }

    private fun getDbFile(context: Context): File {
        val standardDb = context.getDatabasePath(DB_FILE_NAME)
        val filesDb = File(context.filesDir, DB_FILE_NAME)
        if (filesDb.exists() && filesDb.length() > 26_000_000L) {
            return filesDb
        }
        if (standardDb.exists() && standardDb.length() > 26_000_000L) {
            return standardDb
        }
        val parent = standardDb.parentFile ?: context.filesDir
        if (!parent.exists()) parent.mkdirs()
        return standardDb
    }

    suspend fun ensureDatabase(context: Context): Boolean = ensureReady(context)

    suspend fun ensureAllAssetDatabases(context: Context) = withContext(Dispatchers.IO) {
        ensureReady(context)
    }

    suspend fun ensureReady(context: Context, version: String = "RVR1960"): Boolean = withContext(Dispatchers.IO) {
        val current = database
        if (current != null && current.isOpen) return@withContext true

        synchronized(lock) {
            val existing = database
            if (existing != null && existing.isOpen) return@synchronized true

            val targetFile = getDbFile(context)

            // If file missing or incomplete, copy directly from assets
            if (!targetFile.exists() || targetFile.length() < 26_000_000L) {
                try {
                    val parent = targetFile.parentFile ?: context.filesDir
                    if (!parent.exists()) parent.mkdirs()
                    val tempFile = File(parent, "${DB_FILE_NAME}.tmp")
                    if (tempFile.exists()) tempFile.delete()

                    Log.d(TAG, "Copiando $ASSET_PATH a ${tempFile.absolutePath}...")
                    context.assets.open(ASSET_PATH).use { inStream ->
                        FileOutputStream(tempFile).use { outStream ->
                            inStream.copyTo(outStream, bufferSize = 64 * 1024)
                            outStream.flush()
                        }
                    }

                    if (tempFile.length() > 26_000_000L) {
                        try { SQLiteDatabase.deleteDatabase(targetFile) } catch (_: Exception) { targetFile.delete() }
                        val renamed = tempFile.renameTo(targetFile)
                        if (!renamed) {
                            tempFile.copyTo(targetFile, overwrite = true)
                            tempFile.delete()
                        }
                        Log.d(TAG, "Base de datos offline copiada con éxito (${targetFile.length()} bytes)")
                    } else {
                        Log.e(TAG, "Archivo copiado incompleto (${tempFile.length()} bytes)")
                        tempFile.delete()
                        return@synchronized false
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error copiando $ASSET_PATH", e)
                    return@synchronized false
                }
            }

            try {
                val db = SQLiteDatabase.openDatabase(
                    targetFile.absolutePath,
                    null,
                    SQLiteDatabase.OPEN_READWRITE or SQLiteDatabase.NO_LOCALIZED_COLLATORS
                )
                database = db
                Log.d(TAG, "Base SQLite offline abierta y lista.")
                return@synchronized true
            } catch (e: Exception) {
                Log.e(TAG, "Error abriendo base offline en ${targetFile.absolutePath}", e)
                return@synchronized false
            }
        }
    }

    suspend fun getVerses(
        context: Context,
        bookId: Int,
        chapter: Int,
        version: String = "RVR1960"
    ): List<OfflineVerseDto> = withContext(Dispatchers.IO) {
        val normVersion = normalizeVersion(version)
        if (!isAssetVersion(normVersion)) return@withContext emptyList()

        if (!ensureReady(context, normVersion)) return@withContext emptyList()

        val db = database ?: return@withContext emptyList()
        val results = mutableListOf<OfflineVerseDto>()
        try {
            val cursor = db.rawQuery(
                "SELECT verse, text FROM bible_verses WHERE version = ? AND book = ? AND chapter = ? ORDER BY verse ASC",
                arrayOf(normVersion, bookId.toString(), chapter.toString())
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
            Log.e(TAG, "Error consultando $normVersion $bookId:$chapter", e)
        }
        results
    }

    suspend fun getVerseCount(
        context: Context,
        bookId: Int,
        chapter: Int,
        version: String = "RVR1960"
    ): Int = withContext(Dispatchers.IO) {
        val norm = normalizeVersion(version)
        if (!isAssetVersion(norm)) return@withContext 0
        ensureReady(context, norm)
        val db = database ?: return@withContext 0
        try {
            val cursor = db.rawQuery(
                "SELECT COUNT(*) FROM bible_verses WHERE version = ? AND book = ? AND chapter = ?",
                arrayOf(norm, bookId.toString(), chapter.toString())
            )
            cursor.use { c ->
                if (c.moveToFirst()) c.getInt(0) else 0
            }
        } catch (e: Exception) {
            Log.e(TAG, "Count error para $norm $bookId:$chapter", e)
            0
        }
    }

    const val SQL_ACCENT_STRIP = "replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(lower(text), 'á','a'), 'é','e'), 'í','i'), 'ó','o'), 'ú','u'), 'ü','u'), 'Á','a'), 'É','e'), 'Í','i'), 'Ó','o'), 'Ú','u'), 'Ü','u')"

    suspend fun searchVerses(
        context: Context,
        query: String,
        testament: String? = null,
        bookId: Int? = null,
        limit: Int = 100,
        version: String = "RVR1960",
        dao: com.example.data.local.BibleReaderDao? = null
    ): List<OfflineVerseDto> = withContext(Dispatchers.IO) {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) return@withContext emptyList()
        val norm = normalizeVersion(version)
        val cleanQuery = BibleTextSanitizer.removeAccents(trimmed).lowercase()

        // 1. Pre-packaged asset versions (RVR1960, NBLA, TLA, DHH)
        if (isAssetVersion(norm)) {
            ensureReady(context, norm)
            val db = database ?: return@withContext emptyList()
            val results = mutableListOf<OfflineVerseDto>()
            try {
                val conditions = mutableListOf<String>()
                val args = mutableListOf<String>()

                conditions.add("version = ?")
                args.add(norm)

                conditions.add("$SQL_ACCENT_STRIP LIKE ?")
                args.add("%$cleanQuery%")

                if (bookId != null && bookId in 1..66) {
                    conditions.add("book = ?")
                    args.add(bookId.toString())
                } else if (testament == "OT") {
                    conditions.add("book <= 39")
                } else if (testament == "NT") {
                    conditions.add("book >= 40")
                }

                val whereClause = "WHERE " + conditions.joinToString(" AND ")
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
                Log.e(TAG, "Search error en asset $norm para query: $query", e)
            }
            return@withContext results
        }

        // 2. Downloaded versions in Room (NVI, NTV, LBLA)
        if (dao != null) {
            try {
                val pattern = "%$cleanQuery%"
                val entities = dao.searchVersesInVersion(
                    pattern = pattern,
                    version = norm,
                    testament = testament,
                    bookId = bookId,
                    limit = limit
                )
                return@withContext entities.map {
                    OfflineVerseDto(
                        bookId = it.bookId,
                        chapter = it.chapter,
                        verseNumber = it.verseNumber,
                        text = cleanVerseText(it.text)
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Search error en Room $norm para query: $query", e)
            }
        }

        emptyList()
    }

    fun cleanVerseText(raw: String?): String {
        return BibleTextSanitizer.sanitize(raw)
    }
}

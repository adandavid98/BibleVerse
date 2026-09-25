package com.example.data.repository

import com.example.data.bible.BibleCatalog
import com.example.data.bible.BollsBibleApiService
import com.example.data.bible.WordsOfJesusCatalog
import com.example.data.local.BibleReaderDao
import com.example.data.model.BibleBookEntity
import com.example.data.model.BibleReaderVerseEntity
import com.example.data.model.VerseHighlightEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

import android.content.Context
import com.example.data.bible.BiblePericopesCatalog
import com.example.data.bible.OfflineBibleManager

class BibleReaderRepository(
    private val dao: BibleReaderDao,
    private val context: Context
) {

    suspend fun initializeCatalogIfNeeded() = withContext(Dispatchers.IO) {
        if (dao.getBookCount() < 66) {
            val bookEntities = BibleCatalog.books.map { book ->
                BibleBookEntity(
                    id = book.order,
                    name = book.name,
                    testament = book.testament,
                    chaptersCount = book.chaptersCount,
                    category = book.category,
                    abbreviation = book.abbreviation,
                    orderIndex = book.order
                )
            }
            dao.insertBooks(bookEntities)
        }

        // Purge any synthetic placeholder verses from earlier versions
        try {
            dao.deleteVersesLike("%Palabra de Dios para edificación%")
        } catch (_: Exception) {}

        // Pre-warm the offline RVR1960 database in background to avoid any delay
        try {
            OfflineBibleManager.ensureDatabase(context)
        } catch (_: Exception) {}
    }

    private fun normalizeVersion(version: String): String {
        val upper = version.uppercase().trim()
        return when (upper) {
            "RV1960", "REINA-VALERA 1960" -> "RVR1960"
            else -> upper
        }
    }

    fun getAllBooks(): Flow<List<BibleBookEntity>> {
        return dao.getAllBooks()
    }

    fun getBookById(bookId: Int): Flow<BibleBookEntity?> {
        return dao.getBookById(bookId)
    }

    fun getVerses(bookId: Int, chapter: Int, version: String): Flow<List<BibleReaderVerseEntity>> {
        val norm = normalizeVersion(version)
        return dao.getVerses(bookId, chapter, norm)
    }

    suspend fun ensureChapterVerses(bookId: Int, chapter: Int, version: String = "RVR1960") = withContext(Dispatchers.IO) {
        val normVersion = normalizeVersion(version)
        val isRvr1960 = normVersion == "RVR1960"

        // 1. For RVR1960 (primary translation), ALWAYS use the complete pre-packaged offline SQLite (31,102 verses).
        //    OfflineBibleManager guarantees all 31,102 verses are available offline, self-healing from asset if necessary.
        if (isRvr1960) {
            val offlineVerses = OfflineBibleManager.getVerses(context, bookId, chapter)
            if (offlineVerses.isNotEmpty()) {
                val existing = dao.getVersesSync(bookId, chapter, normVersion)
                val hasSynthetic = existing.any { it.text.contains("Palabra de Dios para edificación") }
                val hasHeadingsInCatalog = BiblePericopesCatalog.hasHeadingsForChapter(bookId, chapter)
                val missingHeadings = hasHeadingsInCatalog && existing.isNotEmpty() && existing.all { it.sectionHeading.isNullOrBlank() }

                // If not cached in Room yet, or incomplete, or contains synthetic placeholder, or missing headings, reload completely
                if (existing.size != offlineVerses.size || hasSynthetic || missingHeadings) {
                    dao.deleteVersesForChapter(bookId, chapter, normVersion)
                    val entities = offlineVerses.map { dto ->
                        val isJesus = WordsOfJesusCatalog.isWordsOfJesus(bookId, chapter, dto.verseNumber)
                            || isWordsOfJesus(bookId, chapter, dto.verseNumber, dto.text)
                        val heading = BiblePericopesCatalog.getHeading(context, bookId, chapter, dto.verseNumber, normVersion)
                        BibleReaderVerseEntity(
                            bookId = bookId,
                            chapter = chapter,
                            verseNumber = dto.verseNumber,
                            text = dto.text,
                            bibleVersion = normVersion,
                            sectionHeading = heading,
                            isRedLetter = isJesus
                        )
                    }
                    dao.insertVerses(entities)
                }
                return@withContext
            }
        }

        // 2. Check if we already have valid verses in Room for this chapter and version
        val existing = dao.getVersesSync(bookId, chapter, normVersion)
        val hasSynthetic = existing.any { it.text.contains("Palabra de Dios para edificación") }
        val hasHeadingsInCatalog = BiblePericopesCatalog.hasHeadingsForChapter(bookId, chapter)
        val missingHeadings = hasHeadingsInCatalog && existing.isNotEmpty() && existing.all { it.sectionHeading.isNullOrBlank() }

        if (existing.isNotEmpty() && !hasSynthetic && !missingHeadings) {
            return@withContext
        }

        if (missingHeadings && !hasSynthetic) {
            val updated = existing.map { v ->
                val heading = BiblePericopesCatalog.getHeading(context, bookId, chapter, v.verseNumber, normVersion)
                if (heading != v.sectionHeading) v.copy(sectionHeading = heading) else v
            }
            dao.insertVerses(updated)
            return@withContext
        }

        if (hasSynthetic) {
            dao.deleteVersesForChapter(bookId, chapter, normVersion)
        }

        // 3. For other versions: check if the version was fully downloaded offline in Room
        if (!isRvr1960) {
            val cachedCount = dao.getVerseCountForVersion(normVersion)
            if (cachedCount > 5000) {
                // Version is fully downloaded in Room, check if chapter is now present
                val fresh = dao.getVersesSync(bookId, chapter, normVersion)
                if (fresh.isNotEmpty()) return@withContext
            }
        }

        // 4. For other versions not yet downloaded: attempt to fetch chapter via Bolls API
        val networkVerses = BollsBibleApiService.fetchChapter(normVersion, bookId, chapter)
        if (!networkVerses.isNullOrEmpty()) {
            val entities = networkVerses.map { dto ->
                val isJesus = WordsOfJesusCatalog.isWordsOfJesus(bookId, chapter, dto.verseNumber)
                    || isWordsOfJesus(bookId, chapter, dto.verseNumber, dto.text)
                val heading = BiblePericopesCatalog.getHeading(context, bookId, chapter, dto.verseNumber, normVersion)
                BibleReaderVerseEntity(
                    bookId = bookId,
                    chapter = chapter,
                    verseNumber = dto.verseNumber,
                    text = dto.text,
                    bibleVersion = normVersion,
                    sectionHeading = heading,
                    isRedLetter = isJesus
                )
            }
            dao.insertVerses(entities)
            return@withContext
        }

        // 5. Offline fallback: Network unavailable or failed. Fall back to pre-packaged RVR1960 Scripture.
        //    User ALWAYS reads genuine, sacred biblical text, NEVER synthetic or empty verses.
        val fallbackOffline = OfflineBibleManager.getVerses(context, bookId, chapter)
        if (fallbackOffline.isNotEmpty()) {
            val entities = fallbackOffline.map { dto ->
                val isJesus = WordsOfJesusCatalog.isWordsOfJesus(bookId, chapter, dto.verseNumber)
                    || isWordsOfJesus(bookId, chapter, dto.verseNumber, dto.text)
                val heading = BiblePericopesCatalog.getHeading(context, bookId, chapter, dto.verseNumber, normVersion)
                BibleReaderVerseEntity(
                    bookId = bookId,
                    chapter = chapter,
                    verseNumber = dto.verseNumber,
                    text = dto.text,
                    bibleVersion = normVersion,
                    sectionHeading = heading,
                    isRedLetter = isJesus
                )
            }
            dao.insertVerses(entities)
        }
    }

    fun getHighlights(bookId: Int, chapter: Int): Flow<List<VerseHighlightEntity>> {
        return dao.getHighlights(bookId, chapter)
    }

    suspend fun saveHighlight(bookId: Int, chapter: Int, verseNumber: Int, colorHex: String) = withContext(Dispatchers.IO) {
        dao.insertHighlight(
            VerseHighlightEntity(
                bookId = bookId,
                chapter = chapter,
                verseNumber = verseNumber,
                colorHex = colorHex
            )
        )
    }

    suspend fun removeHighlight(bookId: Int, chapter: Int, verseNumber: Int) = withContext(Dispatchers.IO) {
        dao.deleteHighlight(bookId, chapter, verseNumber)
    }

    suspend fun removeHighlights(bookId: Int, chapter: Int, verseNumbers: List<Int>) = withContext(Dispatchers.IO) {
        dao.deleteHighlights(bookId, chapter, verseNumbers)
    }

    private fun detectSectionHeading(bookId: Int, chapter: Int, verseNumber: Int): String? {
        return BiblePericopesCatalog.getHeading(context, bookId, chapter, verseNumber, "RVR1960")
    }

    private fun isWordsOfJesus(bookId: Int, chapter: Int, verseNumber: Int, text: String): Boolean {
        // Gospels: Matthew (40), Mark (41), Luke (42), John (43), Revelation (66)
        if (bookId !in 40..43 && bookId != 66) return false

        // Matthew 5, 6, 7 (Sermon on the Mount) are almost entirely Jesus speaking
        if (bookId == 40 && chapter in 5..7 && (chapter != 5 || verseNumber >= 3)) return true

        // John 14, 15, 16, 17 (Farewell Discourse)
        if (bookId == 43 && chapter in 14..17) return true

        // Specific John 3 verses where Jesus speaks to Nicodemus
        if (bookId == 43 && chapter == 3 && verseNumber in listOf(3, 5, 7, 8, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21)) return true

        // Keyword indicators for words of Jesus across the Gospels
        val lower = text.lowercase()
        return lower.contains("de cierto, de cierto") ||
                lower.contains("de cierto os digo") ||
                lower.contains("yo soy el camino") ||
                lower.contains("yo soy el pan") ||
                lower.contains("yo soy la luz") ||
                lower.contains("yo soy la resurrección") ||
                lower.contains("yo soy el buen pastor") ||
                lower.contains("la paz os dejo, mi paz os doy") ||
                (lower.contains("respondió jesús") && lower.contains(":")) ||
                (lower.contains("jesús les dijo") && lower.contains(":"))
    }
}


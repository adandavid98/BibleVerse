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
import com.example.data.bible.BibleTextSanitizer
import com.example.data.bible.OfflineBibleDownloadManager
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

        // One-time purge of earlier contaminated TLA/DHH/NBLA cache so they are freshly
        // populated with genuine authentic text from their respective asset packages.
        try {
            val prefs = context.getSharedPreferences("bible_cache_maintenance", Context.MODE_PRIVATE)
            if (!prefs.getBoolean("clean_asset_versions_v6", false)) {
                dao.deleteVersesForVersion("TLA")
                dao.deleteVersesForVersion("DHH")
                dao.deleteVersesForVersion("DHH94PC")
                dao.deleteVersesForVersion("NBLA")
                prefs.edit().putBoolean("clean_asset_versions_v6", true).apply()
            }
        } catch (_: Exception) {}

        // Pre-warm the offline databases in background
        try {
            OfflineBibleManager.ensureAllAssetDatabases(context)
        } catch (_: Exception) {}
    }

    private fun normalizeVersion(version: String): String {
        return OfflineBibleManager.normalizeVersion(version)
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
        val isAsset = OfflineBibleManager.isAssetVersion(normVersion)

        // 1. For pre-packaged offline asset versions (RVR1960, NBLA, TLA, DHH, DHH94PC):
        //    ALWAYS read directly from their respective genuine offline SQLite file.
        //    Instant sub-millisecond, 100% offline, zero network required!
        if (isAsset) {
            var offlineVerses = OfflineBibleManager.getVerses(context, bookId, chapter, normVersion)
            if (offlineVerses.isEmpty()) {
                OfflineBibleManager.ensureReady(context, normVersion)
                offlineVerses = OfflineBibleManager.getVerses(context, bookId, chapter, normVersion)
            }
            if (offlineVerses.isNotEmpty()) {
                val existing = dao.getVersesSync(bookId, chapter, normVersion)
                val firstTextDiffers = existing.isNotEmpty() &&
                    BibleTextSanitizer.sanitize(existing.first().text) != BibleTextSanitizer.sanitize(offlineVerses.first().text)
                val sizeDiffers = existing.size != offlineVerses.size
                val hasSynthetic = existing.any { it.text.contains("Palabra de Dios para edificación") }
                val hasHeadingsInCatalog = BiblePericopesCatalog.hasHeadingsForChapter(bookId, chapter, normVersion)
                val needsHeadingRefresh = hasHeadingsInCatalog && existing.isNotEmpty() && existing.any { v ->
                    val expected = BiblePericopesCatalog.getHeading(context, bookId, chapter, v.verseNumber, normVersion)
                    v.sectionHeading != expected
                }
                val hasHtmlTags = existing.any { it.text.contains("<br", ignoreCase = true) || it.text.contains("<") }

                if (existing.isEmpty() || sizeDiffers || firstTextDiffers || hasSynthetic || needsHeadingRefresh || hasHtmlTags) {
                    dao.deleteVersesForChapter(bookId, chapter, normVersion)
                    val entities = offlineVerses.map { dto ->
                        val cleanText = BibleTextSanitizer.sanitize(dto.text)
                        val isJesus = WordsOfJesusCatalog.isWordsOfJesus(bookId, chapter, dto.verseNumber)
                            || isWordsOfJesus(bookId, chapter, dto.verseNumber, cleanText)
                        val heading = BiblePericopesCatalog.getHeading(context, bookId, chapter, dto.verseNumber, normVersion)
                        BibleReaderVerseEntity(
                            bookId = bookId,
                            chapter = chapter,
                            verseNumber = dto.verseNumber,
                            text = cleanText,
                            bibleVersion = normVersion,
                            sectionHeading = heading,
                            isRedLetter = isJesus
                        )
                    }
                    dao.insertVerses(entities)
                }
                return@withContext
            }
            // Never fall through to network or RVR fallback for asset translations!
            return@withContext
        }

        // 2. Check if we already have valid verses in Room for this chapter and version (for downloaded versions)
        val existing = dao.getVersesSync(bookId, chapter, normVersion)
        if (existing.isNotEmpty()) {
            val hasSynthetic = existing.any { it.text.contains("Palabra de Dios para edificación") }
            if (hasSynthetic) {
                dao.deleteVersesForChapter(bookId, chapter, normVersion)
            } else {
                val hasHeadingsInCatalog = BiblePericopesCatalog.hasHeadingsForChapter(bookId, chapter, normVersion)
                val needsHeadingRefresh = hasHeadingsInCatalog && existing.any { v ->
                    val expected = BiblePericopesCatalog.getHeading(context, bookId, chapter, v.verseNumber, normVersion)
                    v.sectionHeading != expected
                }
                val hasRawTags = existing.any { it.text.contains("<") }

                if (needsHeadingRefresh || hasRawTags) {
                    val updated = existing.map { v ->
                        val cleanText = BibleTextSanitizer.sanitize(v.text)
                        val expectedHeading = BiblePericopesCatalog.getHeading(context, bookId, chapter, v.verseNumber, normVersion)
                        v.copy(
                            text = cleanText,
                            sectionHeading = expectedHeading
                        )
                    }
                    dao.insertVerses(updated)
                }
                return@withContext
            }
        }

        // 3. For online-only versions: check if the version was fully downloaded in Room
        val cachedCount = dao.getVerseCountForVersion(normVersion)
        if (cachedCount > 5000) {
            val fresh = dao.getVersesSync(bookId, chapter, normVersion)
            if (fresh.isNotEmpty()) return@withContext
        }

        // 4. Attempt to fetch chapter via Bolls API
        val networkVerses = BollsBibleApiService.fetchChapter(normVersion, bookId, chapter)
        if (!networkVerses.isNullOrEmpty()) {
            val entities = networkVerses.map { dto ->
                val cleanText = BibleTextSanitizer.sanitize(dto.text)
                val isJesus = WordsOfJesusCatalog.isWordsOfJesus(bookId, chapter, dto.verseNumber)
                    || isWordsOfJesus(bookId, chapter, dto.verseNumber, cleanText)
                val heading = BiblePericopesCatalog.getHeading(context, bookId, chapter, dto.verseNumber, normVersion)
                BibleReaderVerseEntity(
                    bookId = bookId,
                    chapter = chapter,
                    verseNumber = dto.verseNumber,
                    text = cleanText,
                    bibleVersion = normVersion,
                    sectionHeading = heading,
                    isRedLetter = isJesus
                )
            }
            dao.insertVerses(entities)
            return@withContext
        }

        // 5. Emergency offline fallback:
        //    ONLY if RVR1960 itself failed, never corrupt another version with RVR1960 text!
        if (normVersion == "RVR1960") {
            val fallbackOffline = OfflineBibleManager.getVerses(context, bookId, chapter, "RVR1960")
            if (fallbackOffline.isNotEmpty()) {
                val entities = fallbackOffline.map { dto ->
                    val cleanText = BibleTextSanitizer.sanitize(dto.text)
                    val isJesus = WordsOfJesusCatalog.isWordsOfJesus(bookId, chapter, dto.verseNumber)
                        || isWordsOfJesus(bookId, chapter, dto.verseNumber, cleanText)
                    val heading = BiblePericopesCatalog.getHeading(context, bookId, chapter, dto.verseNumber, "RVR1960")
                    BibleReaderVerseEntity(
                        bookId = bookId,
                        chapter = chapter,
                        verseNumber = dto.verseNumber,
                        text = cleanText,
                        bibleVersion = "RVR1960",
                        sectionHeading = heading,
                        isRedLetter = isJesus
                    )
                }
                dao.insertVerses(entities)
            }
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


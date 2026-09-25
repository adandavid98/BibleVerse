package com.example.ui.reader.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.local.BibleDatabase
import com.example.data.local.VerseDao
import com.example.data.model.BibleBookEntity
import com.example.data.model.VerseEntity
import com.example.data.preferences.ReaderFontFamily
import com.example.data.preferences.ReaderPreferences
import com.example.data.preferences.ReaderPreferencesRepository
import com.example.data.preferences.ReaderThemeMode
import com.example.data.repository.BibleReaderRepository
import com.example.domain.model.ReaderVerseUiModel
import com.example.domain.usecase.FormatVerseQuotationUseCase
import com.example.domain.usecase.GetBibleBooksUseCase
import com.example.domain.usecase.GetChapterVersesWithHighlightsUseCase
import com.example.domain.usecase.ToggleVerseHighlightUseCase
import com.example.ui.reader.model.BibleReaderUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BibleReaderViewModel(
    private val getBibleBooksUseCase: GetBibleBooksUseCase,
    private val getChapterVersesUseCase: GetChapterVersesWithHighlightsUseCase,
    private val toggleHighlightUseCase: ToggleVerseHighlightUseCase,
    private val formatVerseQuotationUseCase: FormatVerseQuotationUseCase,
    private val preferencesRepository: ReaderPreferencesRepository,
    private val verseDao: VerseDao? = null,
    val readerDao: com.example.data.local.BibleReaderDao? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(BibleReaderUiState())
    val uiState: StateFlow<BibleReaderUiState> = _uiState.asStateFlow()

    private var currentChapterVersesJob: Job? = null

    init {
        viewModelScope.launch {
            getBibleBooksUseCase.initialize()

            // Cleanly coordinate books and preferences without race conditions
            combine(getBibleBooksUseCase(), preferencesRepository.readerPreferences) { books, prefs ->
                Pair(books, prefs)
            }.collectLatest { (books, prefs) ->
                if (books.isEmpty()) return@collectLatest

                val targetBook = books.firstOrNull { it.id == prefs.lastBookId } ?: books.first()
                val targetChapter = prefs.lastChapter.coerceIn(1, targetBook.chaptersCount)
                val targetVersion = prefs.bibleVersion

                val current = _uiState.value
                val needsReload = current.verses.isEmpty() ||
                        current.currentBook?.id != targetBook.id ||
                        current.currentChapter != targetChapter ||
                        current.preferences.bibleVersion != targetVersion

                _uiState.update {
                    it.copy(
                        books = books,
                        preferences = prefs,
                        currentBook = targetBook,
                        currentChapter = targetChapter
                    )
                }

                if (needsReload) {
                    loadChapter(targetBook, targetChapter, targetVersion)
                }
            }
        }
    }

    fun reloadCurrentChapter() {
        val currentBook = _uiState.value.currentBook ?: return
        val currentChapter = _uiState.value.currentChapter
        val version = _uiState.value.preferences.bibleVersion
        loadChapter(currentBook, currentChapter, version)
    }

    fun selectBookAndChapter(book: BibleBookEntity, chapter: Int) {
        selectBookChapterVerse(book, chapter, 1)
    }

    fun selectBookChapterVerse(book: BibleBookEntity, chapter: Int, verse: Int = 1) {
        val validChapter = chapter.coerceIn(1, book.chaptersCount)
        _uiState.update {
            it.copy(
                currentBook = book,
                currentChapter = validChapter,
                selectedVerseNumbers = emptySet(),
                targetScrollVerse = verse,
                isBookChapterSelectorOpen = false
            )
        }
        viewModelScope.launch {
            preferencesRepository.updateLastPosition(book.id, validChapter, verse)
        }
        loadChapter(book, validChapter, _uiState.value.preferences.bibleVersion)
    }

    fun navigateToVerse(bookId: Int, chapter: Int, verse: Int = 1) {
        val book = _uiState.value.books.firstOrNull { it.id == bookId }
            ?: com.example.data.model.BibleBookEntity(
                id = bookId,
                name = com.example.data.bible.BibleCatalog.books.getOrNull(bookId - 1)?.name ?: "Libro $bookId",
                testament = if (bookId <= 39) "Antiguo Testamento" else "Nuevo Testamento",
                chaptersCount = com.example.data.bible.BibleCatalog.books.getOrNull(bookId - 1)?.chaptersCount ?: 1,
                abbreviation = com.example.data.bible.BibleCatalog.books.getOrNull(bookId - 1)?.abbreviation ?: "",
                orderIndex = bookId
            )
        selectBookChapterVerse(book, chapter, verse)
    }

    fun clearTargetScrollVerse() {
        _uiState.update { it.copy(targetScrollVerse = null) }
    }

    fun setReaderBarsVisible(visible: Boolean) {
        if (_uiState.value.isReaderBarsVisible != visible) {
            _uiState.update { it.copy(isReaderBarsVisible = visible) }
        }
    }

    fun toggleReaderBars() {
        _uiState.update { it.copy(isReaderBarsVisible = !it.isReaderBarsVisible) }
    }

    fun nextChapter() {
        val currentBook = _uiState.value.currentBook ?: return
        val currentChapter = _uiState.value.currentChapter
        if (currentChapter < currentBook.chaptersCount) {
            selectBookAndChapter(currentBook, currentChapter + 1)
        } else {
            // Move to next book if available
            val nextBook = _uiState.value.books.firstOrNull { it.orderIndex == currentBook.orderIndex + 1 }
            if (nextBook != null) {
                selectBookAndChapter(nextBook, 1)
            }
        }
    }

    fun previousChapter() {
        val currentBook = _uiState.value.currentBook ?: return
        val currentChapter = _uiState.value.currentChapter
        if (currentChapter > 1) {
            selectBookAndChapter(currentBook, currentChapter - 1)
        } else {
            // Move to previous book last chapter
            val prevBook = _uiState.value.books.firstOrNull { it.orderIndex == currentBook.orderIndex - 1 }
            if (prevBook != null) {
                selectBookAndChapter(prevBook, prevBook.chaptersCount)
            }
        }
    }

    private fun loadChapter(book: BibleBookEntity, chapter: Int, version: String) {
        currentChapterVersesJob?.cancel()
        currentChapterVersesJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    isLoadingMore = false,
                    currentBook = book,
                    currentChapter = chapter,
                    selectedVerseNumbers = emptySet()
                )
            }
            getChapterVersesUseCase.ensureLoaded(book.id, chapter, version)
            getChapterVersesUseCase(book.id, chapter, version).collectLatest { versesList ->
                val isContinuous = _uiState.value.preferences.continuousScrollEnabled
                val currentVerses = _uiState.value.verses

                val updatedVerses = if (isContinuous && currentVerses.any { it.bookId != book.id || it.chapter != chapter }) {
                    val otherVerses = currentVerses.filterNot { it.bookId == book.id && it.chapter == chapter }
                    (otherVerses + versesList).sortedWith(compareBy({ it.bookId }, { it.chapter }, { it.verseNumber }))
                } else {
                    versesList
                }

                _uiState.update {
                    it.copy(
                        verses = updatedVerses,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun loadNextChapterContinuous() {
        val currentVerses = _uiState.value.verses
        if (currentVerses.isEmpty() || _uiState.value.isLoadingMore) return

        val lastVerse = currentVerses.last()
        val lastBookId = lastVerse.bookId
        val lastChapter = lastVerse.chapter
        val currentBook = _uiState.value.books.firstOrNull { it.id == lastBookId } ?: return

        val nextBook: BibleBookEntity
        val nextChapter: Int

        if (lastChapter < currentBook.chaptersCount) {
            nextBook = currentBook
            nextChapter = lastChapter + 1
        } else {
            val candidateBook = _uiState.value.books.firstOrNull { it.orderIndex == currentBook.orderIndex + 1 } ?: return
            nextBook = candidateBook
            nextChapter = 1
        }

        // Avoid duplicate load if already in list
        if (currentVerses.any { it.bookId == nextBook.id && it.chapter == nextChapter }) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true) }
            val version = _uiState.value.preferences.bibleVersion
            getChapterVersesUseCase.ensureLoaded(nextBook.id, nextChapter, version)
            val newVerses = getChapterVersesUseCase(nextBook.id, nextChapter, version).first()

            _uiState.update { state ->
                val combined = (state.verses + newVerses)
                    .distinctBy { "${it.bookId}_${it.chapter}_${it.verseNumber}" }
                    .sortedWith(compareBy({ it.bookId }, { it.chapter }, { it.verseNumber }))
                state.copy(
                    verses = combined,
                    isLoadingMore = false
                )
            }
        }
    }

    fun updateVisibleBookAndChapter(bookId: Int, chapter: Int) {
        val book = _uiState.value.books.firstOrNull { it.id == bookId } ?: return
        if (book.id != _uiState.value.currentBook?.id || chapter != _uiState.value.currentChapter) {
            _uiState.update { it.copy(currentBook = book, currentChapter = chapter) }
            viewModelScope.launch {
                preferencesRepository.updateLastPosition(book.id, chapter, 1)
            }
        }
    }

    fun toggleVerseSelection(verseNumber: Int) {
        _uiState.update { current ->
            val updatedSelection = current.selectedVerseNumbers.toMutableSet()
            if (updatedSelection.contains(verseNumber)) {
                updatedSelection.remove(verseNumber)
            } else {
                updatedSelection.add(verseNumber)
            }

            val updatedVerses = current.verses.map { verse ->
                verse.copy(isSelected = updatedSelection.contains(verse.verseNumber))
            }

            current.copy(
                selectedVerseNumbers = updatedSelection,
                verses = updatedVerses
            )
        }
    }

    fun clearSelection() {
        _uiState.update { current ->
            current.copy(
                selectedVerseNumbers = emptySet(),
                verses = current.verses.map { it.copy(isSelected = false) }
            )
        }
    }

    fun applyHighlightToSelection(colorHex: String) {
        val currentBook = _uiState.value.currentBook ?: return
        val chapter = _uiState.value.currentChapter
        val selected = getSelectedVerses()
        if (selected.isEmpty()) return

        val dao = verseDao
        viewModelScope.launch {
            if (dao != null) {
                val version = _uiState.value.preferences.bibleVersion
                val testament = if (currentBook.orderIndex <= 39) "Antiguo Testamento" else "Nuevo Testamento"
                val allVerses = dao.getAllVersesDirect()
                for (v in selected) {
                    val ref = "${currentBook.name} $chapter:${v.verseNumber}"
                    val existing = allVerses.find {
                        it.reference.trim().equals(ref.trim(), ignoreCase = true)
                    }
                    if (existing != null) {
                        dao.updateVerse(
                            existing.copy(
                                highlightColor = colorHex,
                                updatedAt = System.currentTimeMillis()
                            )
                        )
                    } else {
                        val entity = VerseEntity(
                            book = currentBook.name,
                            chapterVerse = "$chapter:${v.verseNumber}",
                            reference = ref,
                            testament = testament,
                            text = v.text,
                            context = "Versículo resaltado desde el Lector de la Biblia.",
                            topic = "Biblia",
                            isFavorite = false,
                            highlightColor = colorHex,
                            isCustom = true,
                            bibleVersion = version
                        )
                        dao.insertVerse(entity)
                    }
                }
            }
            toggleHighlightUseCase.applyHighlight(currentBook.id, chapter, selected.map { it.verseNumber }, colorHex)
            clearSelection()
        }
    }

    fun removeHighlightFromSelection() {
        val currentBook = _uiState.value.currentBook ?: return
        val chapter = _uiState.value.currentChapter
        val selected = getSelectedVerses()
        if (selected.isEmpty()) return

        val dao = verseDao
        viewModelScope.launch {
            if (dao != null) {
                val allVerses = dao.getAllVersesDirect()
                for (v in selected) {
                    val ref = "${currentBook.name} $chapter:${v.verseNumber}"
                    val existing = allVerses.find {
                        it.reference.trim().equals(ref.trim(), ignoreCase = true)
                    }
                    if (existing != null) {
                        dao.updateVerse(
                            existing.copy(
                                highlightColor = "",
                                updatedAt = System.currentTimeMillis()
                            )
                        )
                    }
                }
            }
            toggleHighlightUseCase.removeHighlight(currentBook.id, chapter, selected.map { it.verseNumber })
            clearSelection()
        }
    }

    fun getSelectedVerses(): List<ReaderVerseUiModel> {
        val selectedSet = _uiState.value.selectedVerseNumbers
        return _uiState.value.verses
            .filter { selectedSet.contains(it.verseNumber) }
            .sortedBy { it.verseNumber }
    }

    fun getFormattedQuotation(): String {
        return formatVerseQuotationUseCase(getSelectedVerses(), _uiState.value.preferences.bibleVersion)
    }

    fun getCitationOnly(): String {
        return formatVerseQuotationUseCase.formatCitationOnly(getSelectedVerses(), _uiState.value.preferences.bibleVersion)
    }

    fun getSelectedRawText(): String {
        val selected = getSelectedVerses()
        return if (selected.size == 1) {
            selected.first().text
        } else {
            selected.joinToString(" ") { it.text }
        }
    }

    // Sheet / Dialog controls
    fun openBookChapterSelector() {
        _uiState.update { it.copy(isBookChapterSelectorOpen = true) }
    }

    fun closeBookChapterSelector() {
        _uiState.update { it.copy(isBookChapterSelectorOpen = false) }
    }

    fun openSettingsSheet() {
        _uiState.update { it.copy(isSettingsSheetOpen = true) }
    }

    fun closeSettingsSheet() {
        _uiState.update { it.copy(isSettingsSheetOpen = false) }
    }

    fun openShareDialog() {
        _uiState.update { it.copy(isShareDialogOpen = true) }
    }

    fun closeShareDialog() {
        _uiState.update { it.copy(isShareDialogOpen = false) }
    }

    fun openCompareModal() {
        _uiState.update { it.copy(isCompareModalOpen = true) }
    }

    fun closeCompareModal() {
        _uiState.update { it.copy(isCompareModalOpen = false) }
    }

    fun openCrossReferences(verse: ReaderVerseUiModel) {
        _uiState.update {
            it.copy(
                isCrossReferencesOpen = true,
                selectedCrossReferenceVerse = verse
            )
        }
    }

    fun closeCrossReferences() {
        _uiState.update {
            it.copy(
                isCrossReferencesOpen = false,
                selectedCrossReferenceVerse = null
            )
        }
    }

    // Preference Updates
    fun updateFontFamily(fontFamily: ReaderFontFamily) {
        viewModelScope.launch { preferencesRepository.updateFontFamily(fontFamily) }
    }

    fun updateFontSize(sizeSp: Float) {
        viewModelScope.launch { preferencesRepository.updateFontSize(sizeSp) }
    }

    fun updateLineSpacing(spacing: Float) {
        viewModelScope.launch { preferencesRepository.updateLineSpacing(spacing) }
    }

    fun updateThemeMode(themeMode: ReaderThemeMode) {
        viewModelScope.launch { preferencesRepository.updateThemeMode(themeMode) }
    }

    fun updateBibleVersion(version: String) {
        viewModelScope.launch {
            preferencesRepository.updateBibleVersion(version)
            val currentBook = _uiState.value.currentBook
            if (currentBook != null) {
                loadChapter(currentBook, _uiState.value.currentChapter, version)
            }
        }
    }

    fun updateRedLettersEnabled(enabled: Boolean) {
        viewModelScope.launch { preferencesRepository.updateRedLettersEnabled(enabled) }
    }

    fun updateContinuousScrollEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.updateContinuousScrollEnabled(enabled)
            if (!enabled) {
                val currentBook = _uiState.value.currentBook
                val currentChapter = _uiState.value.currentChapter
                if (currentBook != null) {
                    loadChapter(currentBook, currentChapter, _uiState.value.preferences.bibleVersion)
                }
            }
        }
    }

    fun updateShowSectionHeadings(enabled: Boolean) {
        viewModelScope.launch { preferencesRepository.updateShowSectionHeadings(enabled) }
    }

    fun saveSelectedVersesToMainModule(colorHex: String = "") {
        val currentBook = _uiState.value.currentBook ?: return
        val chapter = _uiState.value.currentChapter
        val selected = getSelectedVerses()
        if (selected.isEmpty()) return

        val dao = verseDao
        viewModelScope.launch {
            if (dao != null) {
                val version = _uiState.value.preferences.bibleVersion
                val testament = if (currentBook.orderIndex <= 39) "Antiguo Testamento" else "Nuevo Testamento"
                val allVerses = dao.getAllVersesDirect()
                for (v in selected) {
                    val ref = "${currentBook.name} $chapter:${v.verseNumber}"
                    val existing = allVerses.find {
                        it.reference.trim().equals(ref.trim(), ignoreCase = true)
                    }
                    if (existing != null) {
                        dao.updateVerse(
                            existing.copy(
                                isFavorite = true,
                                highlightColor = if (colorHex.isNotBlank()) colorHex else existing.highlightColor,
                                updatedAt = System.currentTimeMillis()
                            )
                        )
                    } else {
                        val entity = VerseEntity(
                            book = currentBook.name,
                            chapterVerse = "$chapter:${v.verseNumber}",
                            reference = ref,
                            testament = testament,
                            text = v.text,
                            context = "Versículo guardado desde el Lector de la Biblia.",
                            topic = "Biblia",
                            isFavorite = true,
                            highlightColor = colorHex,
                            isCustom = true,
                            bibleVersion = version
                        )
                        dao.insertVerse(entity)
                    }
                }
            }
            if (colorHex.isNotBlank()) {
                toggleHighlightUseCase.applyHighlight(currentBook.id, chapter, selected.map { it.verseNumber }, colorHex)
            }
            clearSelection()
        }
    }

    fun downloadBibleVersion(versionCode: String) {
        val dao = readerDao ?: return
        viewModelScope.launch {
            com.example.data.bible.OfflineBibleDownloadManager.downloadVersion(dao, versionCode)
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val db = BibleDatabase.getDatabase(context)
            val dao = db.bibleReaderDao()
            val repo = BibleReaderRepository(dao, context.applicationContext)
            val getBooks = GetBibleBooksUseCase(repo)
            val getChapterVerses = GetChapterVersesWithHighlightsUseCase(repo)
            val toggleHighlight = ToggleVerseHighlightUseCase(repo)
            val formatQuote = FormatVerseQuotationUseCase()
            val prefsRepo = ReaderPreferencesRepository(context)

            return BibleReaderViewModel(
                getBibleBooksUseCase = getBooks,
                getChapterVersesUseCase = getChapterVerses,
                toggleHighlightUseCase = toggleHighlight,
                formatVerseQuotationUseCase = formatQuote,
                preferencesRepository = prefsRepo,
                verseDao = db.verseDao(),
                readerDao = dao
            ) as T
        }
    }
}

package com.example.ui.reader.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.local.BibleDatabase
import com.example.data.model.BibleBookEntity
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BibleReaderViewModel(
    private val getBibleBooksUseCase: GetBibleBooksUseCase,
    private val getChapterVersesUseCase: GetChapterVersesWithHighlightsUseCase,
    private val toggleHighlightUseCase: ToggleVerseHighlightUseCase,
    private val formatVerseQuotationUseCase: FormatVerseQuotationUseCase,
    private val preferencesRepository: ReaderPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BibleReaderUiState())
    val uiState: StateFlow<BibleReaderUiState> = _uiState.asStateFlow()

    private var currentChapterVersesJob: Job? = null

    init {
        viewModelScope.launch {
            getBibleBooksUseCase.initialize()
        }

        // Collect Books
        viewModelScope.launch {
            getBibleBooksUseCase().collectLatest { books ->
                _uiState.update { current ->
                    val selectedBook = current.currentBook ?: books.firstOrNull { it.id == current.preferences.lastBookId }
                        ?: books.firstOrNull()
                    current.copy(
                        books = books,
                        currentBook = selectedBook,
                        isLoading = books.isEmpty()
                    )
                }
            }
        }

        // Collect Preferences
        viewModelScope.launch {
            preferencesRepository.readerPreferences.collectLatest { prefs ->
                val prevPrefs = _uiState.value.preferences
                _uiState.update { it.copy(preferences = prefs) }

                // If book or chapter or version changed in preferences or on first run, reload chapter
                val bookId = prefs.lastBookId
                val chapter = prefs.lastChapter
                val version = prefs.bibleVersion
                if (bookId != _uiState.value.currentBook?.id ||
                    chapter != _uiState.value.currentChapter ||
                    version != prevPrefs.bibleVersion ||
                    _uiState.value.verses.isEmpty()
                ) {
                    val targetBook = _uiState.value.books.firstOrNull { it.id == bookId }
                    if (targetBook != null) {
                        loadChapter(targetBook, chapter, version)
                    }
                }
            }
        }
    }

    fun selectBookAndChapter(book: BibleBookEntity, chapter: Int) {
        val validChapter = chapter.coerceIn(1, book.chaptersCount)
        _uiState.update {
            it.copy(
                currentBook = book,
                currentChapter = validChapter,
                selectedVerseNumbers = emptySet(),
                isBookChapterSelectorOpen = false
            )
        }
        viewModelScope.launch {
            preferencesRepository.updateLastPosition(book.id, validChapter, 1)
        }
        loadChapter(book, validChapter, _uiState.value.preferences.bibleVersion)
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
            _uiState.update { it.copy(isLoading = true, currentBook = book, currentChapter = chapter) }
            getChapterVersesUseCase.ensureLoaded(book.id, chapter, version)
            getChapterVersesUseCase(book.id, chapter, version).collectLatest { versesList ->
                val selectedSet = _uiState.value.selectedVerseNumbers
                val updatedVerses = versesList.map { verse ->
                    verse.copy(isSelected = selectedSet.contains(verse.verseNumber))
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
        val selected = _uiState.value.selectedVerseNumbers.toList()
        if (selected.isEmpty()) return

        viewModelScope.launch {
            toggleHighlightUseCase.applyHighlight(currentBook.id, chapter, selected, colorHex)
            clearSelection()
        }
    }

    fun removeHighlightFromSelection() {
        val currentBook = _uiState.value.currentBook ?: return
        val chapter = _uiState.value.currentChapter
        val selected = _uiState.value.selectedVerseNumbers.toList()
        if (selected.isEmpty()) return

        viewModelScope.launch {
            toggleHighlightUseCase.removeHighlight(currentBook.id, chapter, selected)
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

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val db = BibleDatabase.getDatabase(context)
            val repo = BibleReaderRepository(db.bibleReaderDao())
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
                preferencesRepository = prefsRepo
            ) as T
        }
    }
}

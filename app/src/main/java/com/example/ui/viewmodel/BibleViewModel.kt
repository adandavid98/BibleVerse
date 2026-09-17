package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.bible.ChatMessage
import com.example.data.bible.GeminiBibleChatService
import com.example.data.bible.BibleContextEngine
import com.example.data.bible.ContextGenerationResult
import com.example.data.bible.ContextSource
import com.example.data.bible.GeminiVerseContextService
import com.example.data.local.BibleDatabase
import com.example.data.model.VerseEntity
import com.example.data.repository.VerseRepository
import com.example.BuildConfig
import com.example.export.ExportManager
import com.example.service.NotificationHelper
import com.example.sync.CloudSyncManager
import com.example.sync.CloudSyncState
import com.example.sync.FirebaseSyncManager
import com.example.sync.FirebaseUserState
import com.example.ui.theme.AppReadingTheme
import com.example.update.AppUpdateInfo
import com.example.update.AppUpdateManager
import com.example.update.UpdateDownloadStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.util.Calendar

enum class VerseFilter(val label: String) {
    TODOS("Todos"),
    ANTIGUO("Antiguo Testamento"),
    NUEVO("Nuevo Testamento"),
    FAVORITOS("Favoritos"),
    CON_NOTAS("Con Notas"),
    RESALTADOS("Resaltados")
}

data class BibleUiState(
    val verses: List<VerseEntity> = emptyList(),
    val filteredVerses: List<VerseEntity> = emptyList(),
    val searchQuery: String = "",
    val selectedFilter: VerseFilter = VerseFilter.TODOS,
    val selectedTopic: String? = null,
    val verseOfTheDay: VerseEntity? = null,
    val readingTheme: AppReadingTheme = AppReadingTheme.LIGHT,
    val fontSizeScale: Float = 1.0f,
    val reminderHour: Int = 8,
    val reminderMinute: Int = 0,
    val reminderEnabled: Boolean = true,
    val cloudSyncId: String = "",
    val lastSyncTime: String = "",
    val syncStatusMessage: String? = null,
    val selectedVerseForDetail: VerseEntity? = null,
    val showAddDialog: Boolean = false,
    val showExportDialog: Boolean = false,
    val showSyncDialog: Boolean = false,
    val availableTopics: List<String> = emptyList()
)

class BibleViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: VerseRepository
    private val prefs = application.getSharedPreferences("bible_app_settings", Context.MODE_PRIVATE)

    private val _searchQuery = MutableStateFlow("")
    private val _selectedFilter = MutableStateFlow(VerseFilter.TODOS)
    private val _selectedTopic = MutableStateFlow<String?>(null)
    private val _readingTheme = MutableStateFlow(
        AppReadingTheme.valueOf(prefs.getString("theme_mode", AppReadingTheme.LIGHT.name) ?: AppReadingTheme.LIGHT.name)
    )
    private val _fontSizeScale = MutableStateFlow(prefs.getFloat("font_size_scale", 1.0f))
    private val _reminderHour = MutableStateFlow(prefs.getInt(NotificationHelper.KEY_REMINDER_HOUR, 8))
    private val _reminderMinute = MutableStateFlow(prefs.getInt(NotificationHelper.KEY_REMINDER_MINUTE, 0))
    private val _reminderEnabled = MutableStateFlow(prefs.getBoolean(NotificationHelper.KEY_REMINDER_ENABLED, true))
    private val _cloudSyncId = MutableStateFlow(CloudSyncManager.getOrGenerateSyncId(application))
    private val _lastSyncTime = MutableStateFlow(CloudSyncManager.getLastSyncTime(application))
    private val _syncStatusMessage = MutableStateFlow<String?>(null)
    private val _selectedVerseForDetail = MutableStateFlow<VerseEntity?>(null)
    private val _showAddDialog = MutableStateFlow(false)
    private val _showExportDialog = MutableStateFlow(false)
    private val _showSyncDialog = MutableStateFlow(false)

    private val _updateInfo = MutableStateFlow<AppUpdateInfo?>(null)
    val updateInfo: StateFlow<AppUpdateInfo?> = _updateInfo.asStateFlow()

    private val _updateDownloadStatus = MutableStateFlow<UpdateDownloadStatus>(UpdateDownloadStatus.Idle)
    val updateDownloadStatus: StateFlow<UpdateDownloadStatus> = _updateDownloadStatus.asStateFlow()

    private val _showUpdateDialog = MutableStateFlow(false)
    val showUpdateDialog: StateFlow<Boolean> = _showUpdateDialog.asStateFlow()

    private val _githubRepo = MutableStateFlow(AppUpdateManager.getGitHubRepo(application))
    val githubRepo: StateFlow<String> = _githubRepo.asStateFlow()

    private val _autoCheckUpdates = MutableStateFlow(AppUpdateManager.isAutoCheckEnabled(application))
    val autoCheckUpdates: StateFlow<Boolean> = _autoCheckUpdates.asStateFlow()

    // Firebase Auth & Firestore State
    val firebaseUserState: StateFlow<FirebaseUserState?> = FirebaseSyncManager.currentUserState
    val firebaseSyncOperation: StateFlow<CloudSyncState> = FirebaseSyncManager.syncOperationState

    // Context Generation State (Hybrid: Local Engine + Gemini AI)
    private val _isGeneratingContext = MutableStateFlow(false)
    val isGeneratingContext: StateFlow<Boolean> = _isGeneratingContext.asStateFlow()

    private val _contextGenerationMessage = MutableStateFlow<String?>(null)
    val contextGenerationMessage: StateFlow<String?> = _contextGenerationMessage.asStateFlow()

    // Bible Theological Chatbot State
    private val _showBibleChatDialog = MutableStateFlow(false)
    val showBibleChatDialog: StateFlow<Boolean> = _showBibleChatDialog.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading: StateFlow<Boolean> = _isChatLoading.asStateFlow()

    private lateinit var bibleReaderDao: com.example.data.local.BibleReaderDao
    val offlineDownloadStates = com.example.data.bible.OfflineBibleDownloadManager.downloadStates

    init {
        val db = BibleDatabase.getDatabase(application, viewModelScope)
        repository = VerseRepository(db.verseDao())
        bibleReaderDao = db.bibleReaderDao()

        // Refresh offline versions statuses
        viewModelScope.launch {
            com.example.data.bible.OfflineBibleDownloadManager.refreshStatuses(bibleReaderDao)
        }

        // Ensure pre-population on first run
        viewModelScope.launch {
            repository.checkAndSeedInitialData()
        }

        // Setup notification channel and alarm schedule
        NotificationHelper.createNotificationChannel(application)
        if (_reminderEnabled.value) {
            NotificationHelper.scheduleDailyReminder(application, _reminderHour.value, _reminderMinute.value)
        }

        // Check for updates in background shortly after launch (only if enabled)
        viewModelScope.launch {
            delay(3500)
            if (AppUpdateManager.isAutoCheckEnabled(application)) {
                checkForUpdates(silent = true)
            }
        }
    }

    private val _filterState = combine(
        _searchQuery,
        _selectedFilter,
        _selectedTopic
    ) { query, filter, topic ->
        Triple(query, filter, topic)
    }

    private val _displaySettings = combine(
        _readingTheme,
        _fontSizeScale
    ) { theme, fontScale ->
        Pair(theme, fontScale)
    }

    private val _dialogState = combine(
        _showAddDialog,
        _showExportDialog,
        _showSyncDialog
    ) { showAdd, showExport, showSync ->
        Triple(showAdd, showExport, showSync)
    }

    private data class DetailAndSyncState(
        val selectedVerse: VerseEntity?,
        val syncMessage: String?,
        val reminderEnabled: Boolean,
        val cloudSyncId: String
    )

    private val _detailAndSync = combine(
        _selectedVerseForDetail,
        _syncStatusMessage,
        _reminderEnabled,
        _cloudSyncId
    ) { detail, syncMsg, reminderEn, syncId ->
        DetailAndSyncState(detail, syncMsg, reminderEn, syncId)
    }

    val uiState: StateFlow<BibleUiState> = combine(
        repository.allVerses,
        _filterState,
        _displaySettings,
        _dialogState,
        _detailAndSync
    ) { allVersesRaw, filterParams, displayParams, dialogParams, detailSync ->
        // In-memory deduplication safeguarding immediate UI presentation:
        // preserve favorite, notes, highlight, and retain original canon ordering
        val allVerses = allVersesRaw
            .sortedWith(
                compareByDescending<VerseEntity> { it.isFavorite }
                    .thenByDescending { it.notes.isNotBlank() }
                    .thenByDescending { it.highlightColor.isNotBlank() }
                    .thenBy { it.id }
            )
            .distinctBy {
                it.reference.trim().lowercase().replace("\\s+".toRegex(), " ")
            }
            .sortedWith(compareBy({ it.orderIndex }, { it.id }))

        val (query, filter, topic) = filterParams
        val (theme, fontScale) = displayParams
        val (showAdd, showExport, showSync) = dialogParams
        val currentDetail = detailSync.selectedVerse
        val syncMsg = detailSync.syncMessage

        val filtered = allVerses.filter { verse ->
            // Filter by search query
            val matchesQuery = query.isBlank() || (
                verse.reference.contains(query, ignoreCase = true) ||
                verse.text.contains(query, ignoreCase = true) ||
                verse.context.contains(query, ignoreCase = true) ||
                verse.topic.contains(query, ignoreCase = true) ||
                verse.notes.contains(query, ignoreCase = true)
            )

            // Filter by category / pill
            val matchesFilter = when (filter) {
                VerseFilter.TODOS -> true
                VerseFilter.ANTIGUO -> verse.testament.contains("Antiguo", ignoreCase = true)
                VerseFilter.NUEVO -> verse.testament.contains("Nuevo", ignoreCase = true)
                VerseFilter.FAVORITOS -> verse.isFavorite
                VerseFilter.CON_NOTAS -> verse.notes.isNotBlank()
                VerseFilter.RESALTADOS -> verse.highlightColor.isNotBlank()
            }

            // Filter by topic chip
            val matchesTopic = topic == null || verse.topic.equals(topic, ignoreCase = true)

            matchesQuery && matchesFilter && matchesTopic
        }

        val topics = allVerses.map { it.topic }.filter { it.isNotBlank() }.distinct().sorted()
        val vod = NotificationHelper.getVerseOfTheDay()
        val realVod = allVerses.find { it.reference == vod.reference } ?: vod

        // Update selected verse if currently open in detail dialog
        val updatedDetail = if (currentDetail != null) {
            allVerses.find { it.id == currentDetail.id } ?: currentDetail
        } else null

        BibleUiState(
            verses = allVerses,
            filteredVerses = filtered,
            searchQuery = query,
            selectedFilter = filter,
            selectedTopic = topic,
            verseOfTheDay = realVod,
            readingTheme = theme,
            fontSizeScale = fontScale,
            reminderHour = _reminderHour.value,
            reminderMinute = _reminderMinute.value,
            reminderEnabled = detailSync.reminderEnabled,
            cloudSyncId = detailSync.cloudSyncId,
            lastSyncTime = _lastSyncTime.value,
            syncStatusMessage = syncMsg,
            selectedVerseForDetail = updatedDetail,
            showAddDialog = showAdd,
            showExportDialog = showExport,
            showSyncDialog = showSync,
            availableTopics = topics
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BibleUiState()
    )

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onFilterSelected(filter: VerseFilter) {
        _selectedFilter.value = filter
    }

    fun onTopicSelected(topic: String?) {
        _selectedTopic.value = if (_selectedTopic.value == topic) null else topic
    }

    fun toggleFavorite(verse: VerseEntity) {
        viewModelScope.launch {
            repository.toggleFavorite(verse.id, verse.isFavorite)
        }
    }

    fun updateNotes(verseId: Long, notes: String) {
        viewModelScope.launch {
            repository.updateNotes(verseId, notes)
        }
    }

    fun updateHighlight(verseId: Long, colorHex: String, highlightedPhrase: String = "") {
        viewModelScope.launch {
            repository.updateHighlight(verseId, colorHex, highlightedPhrase)
        }
    }

    fun addCustomVerse(
        book: String,
        chapterVerse: String,
        testament: String,
        text: String,
        context: String,
        topic: String,
        notes: String,
        bibleVersion: String = "RVR1960"
    ) {
        viewModelScope.launch {
            val reference = "$book $chapterVerse".trim()
            val formattedText = if (!text.startsWith("«") && !text.endsWith("»")) "«$text»" else text
            val newVerse = VerseEntity(
                book = book,
                chapterVerse = chapterVerse,
                reference = reference,
                testament = testament,
                text = formattedText,
                context = context,
                topic = topic.ifBlank { "Personal" },
                notes = notes,
                isCustom = true,
                orderIndex = 999,
                bibleVersion = bibleVersion.ifBlank { "RVR1960" },
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            val newId = repository.insertVerse(newVerse)
            _showAddDialog.value = false
            _syncStatusMessage.value = "Versículo añadido con éxito"
        }
    }

    fun updateVerseDetails(
        verseId: Long,
        reference: String,
        text: String,
        context: String,
        topic: String,
        bibleVersion: String = "RVR1960"
    ) {
        viewModelScope.launch {
            val current = repository.getVerseByIdDirect(verseId)
            if (current != null) {
                val formattedText = if (!text.startsWith("«") && !text.endsWith("»")) "«$text»" else text
                val updated = current.copy(
                    reference = reference.trim(),
                    text = formattedText.trim(),
                    context = context.trim(),
                    topic = topic.trim().ifBlank { current.topic },
                    bibleVersion = bibleVersion.ifBlank { current.bibleVersion },
                    updatedAt = System.currentTimeMillis()
                )
                repository.updateVerse(updated)
                _selectedVerseForDetail.value = updated
                _syncStatusMessage.value = "Versículo y contexto actualizados con éxito"
            }
        }
    }

    fun updateVerseContext(verseId: Long, newContext: String) {
        viewModelScope.launch {
            val current = repository.getVerseByIdDirect(verseId)
            if (current != null) {
                val updated = current.copy(
                    context = newContext.trim(),
                    updatedAt = System.currentTimeMillis()
                )
                repository.updateVerse(updated)
                _selectedVerseForDetail.value = updated
                _syncStatusMessage.value = "Contexto teológico actualizado con éxito"
            }
        }
    }

    /**
     * Generates biblical context using the Hybrid approach:
     * First checks / connects with Gemini 3.5 Flash AI,
     * seamlessly falling back to the rich local theological engine if offline or key is missing.
     */
    fun generateIntelligentContext(
        book: String,
        chapter: Int,
        verse: String,
        verseText: String,
        bibleVersion: String = "RVR1960",
        forceLocal: Boolean = false,
        onResult: (context: String, isAi: Boolean) -> Unit
    ) {
        viewModelScope.launch {
            _isGeneratingContext.value = true
            _contextGenerationMessage.value = if (forceLocal) "Obteniendo contexto local..." else "Generando contexto inteligente con IA..."
            try {
                val result = GeminiVerseContextService.generateContext(
                    book = book,
                    chapter = chapter,
                    verse = verse,
                    verseText = verseText,
                    bibleVersion = bibleVersion,
                    forceLocalOnly = forceLocal
                )
                when (result) {
                    is ContextGenerationResult.Success -> {
                        val isAi = result.source == ContextSource.AI_GEMINI
                        _contextGenerationMessage.value = when (result.source) {
                            ContextSource.AI_GEMINI -> "Contexto generado con Inteligencia Artificial"
                            ContextSource.LOCAL_EXACT -> "Contexto exacto precargado"
                            ContextSource.LOCAL_ENGINE -> "Contexto del catálogo teológico local"
                        }
                        onResult(result.contextText, isAi)
                    }
                    is ContextGenerationResult.Error -> {
                        _contextGenerationMessage.value = "Contexto teológico local aplicado"
                        onResult(result.fallbackContext, false)
                    }
                }
            } catch (e: Exception) {
                val fallback = BibleContextEngine.getLocalContext(book, chapter, verse, verseText)
                _contextGenerationMessage.value = "Contexto teológico local aplicado"
                onResult(fallback, false)
            } finally {
                _isGeneratingContext.value = false
            }
        }
    }

    /**
     * Re-analyzes or enriches the context of an existing verse using Gemini AI.
     */
    fun enrichVerseContextWithAi(verseId: Long) {
        viewModelScope.launch {
            val current = repository.getVerseByIdDirect(verseId) ?: return@launch
            _isGeneratingContext.value = true
            _syncStatusMessage.value = "Generando exégesis con IA..."
            try {
                val parts = current.reference.split(" ")
                val book = current.book.ifBlank { parts.dropLast(1).joinToString(" ").ifBlank { "Salmos" } }
                val chapterVerse = current.chapterVerse.ifBlank { parts.lastOrNull() ?: "1:1" }
                val cvParts = chapterVerse.split(":")
                val chapter = cvParts.getOrNull(0)?.filter { it.isDigit() }?.toIntOrNull() ?: 1
                val verse = cvParts.getOrNull(1) ?: "1"

                val result = GeminiVerseContextService.generateContext(
                    book = book,
                    chapter = chapter,
                    verse = verse,
                    verseText = current.text.removeSurrounding("«", "»"),
                    bibleVersion = current.bibleVersion
                )
                val newContext = when (result) {
                    is ContextGenerationResult.Success -> result.contextText
                    is ContextGenerationResult.Error -> result.fallbackContext
                }
                if (newContext.isNotBlank()) {
                    val updated = current.copy(
                        context = newContext,
                        updatedAt = System.currentTimeMillis()
                    )
                    repository.updateVerse(updated)
                    _selectedVerseForDetail.value = updated
                    _syncStatusMessage.value = "¡Contexto enriquecido con IA con éxito!"
                }
            } catch (e: Exception) {
                _syncStatusMessage.value = "No se pudo conectar con la IA. Se mantuvo el contexto actual."
            } finally {
                _isGeneratingContext.value = false
            }
        }
    }

    fun deleteVerse(verse: VerseEntity) {
        viewModelScope.launch {
            repository.deleteVerse(verse)
            if (_selectedVerseForDetail.value?.id == verse.id) {
                _selectedVerseForDetail.value = null
            }
        }
    }

    fun setReadingTheme(theme: AppReadingTheme) {
        _readingTheme.value = theme
        prefs.edit().putString("theme_mode", theme.name).apply()
    }

    fun setFontSizeScale(scale: Float) {
        _fontSizeScale.value = scale
        prefs.edit().putFloat("font_size_scale", scale).apply()
    }

    fun setDailyReminder(hour: Int, minute: Int, enabled: Boolean) {
        _reminderHour.value = hour
        _reminderMinute.value = minute
        _reminderEnabled.value = enabled

        prefs.edit()
            .putInt(NotificationHelper.KEY_REMINDER_HOUR, hour)
            .putInt(NotificationHelper.KEY_REMINDER_MINUTE, minute)
            .putBoolean(NotificationHelper.KEY_REMINDER_ENABLED, enabled)
            .apply()

        val app = getApplication<Application>()
        if (enabled) {
            NotificationHelper.scheduleDailyReminder(app, hour, minute)
        } else {
            NotificationHelper.cancelDailyReminder(app)
        }
    }

    fun testDailyNotification() {
        val app = getApplication<Application>()
        val vod = uiState.value.verseOfTheDay ?: NotificationHelper.getVerseOfTheDay()
        NotificationHelper.showVerseNotification(
            context = app,
            reference = vod.reference,
            text = vod.text,
            reflection = vod.context
        )
    }

    fun openVerseDetail(verse: VerseEntity?) {
        _selectedVerseForDetail.value = verse
    }

    fun showAddVerseDialog(show: Boolean) {
        _showAddDialog.value = show
    }

    fun showExportDialog(show: Boolean) {
        _showExportDialog.value = show
    }

    fun showSyncDialog(show: Boolean) {
        _showSyncDialog.value = show
    }

    fun exportContent(format: String, exportAll: Boolean = true) {
        val app = getApplication<Application>()
        val versesToExport = if (exportAll) {
            uiState.value.verses
        } else {
            uiState.value.verses.filter { it.isFavorite || it.notes.isNotBlank() || it.highlightColor.isNotBlank() }
        }

        if (versesToExport.isEmpty()) {
            _syncStatusMessage.value = "No hay versículos para exportar con este criterio."
            return
        }

        try {
            val title = if (exportAll) "Compilación de Versículos Bíblicos" else "Mis Versículos Favoritos y Notas"
            if (format.equals("PDF", ignoreCase = true)) {
                val file = ExportManager.exportToPdf(app, title, versesToExport)
                ExportManager.shareExportedFile(app, file, "application/pdf", title)
            } else {
                val file = ExportManager.exportToPlainText(app, title, versesToExport)
                ExportManager.shareExportedFile(app, file, "text/plain", title)
            }
            _showExportDialog.value = false
            _syncStatusMessage.value = "Archivo $format exportado correctamente"
        } catch (e: Exception) {
            _syncStatusMessage.value = "Error al exportar: ${e.localizedMessage}"
        }
    }

    fun shareVerse(verse: VerseEntity) {
        val app = getApplication<Application>()
        ExportManager.shareSingleVerse(app, verse)
    }

    fun shareVerse(context: Context, verse: VerseEntity) {
        ExportManager.shareSingleVerse(context, verse)
    }

    fun performCloudBackup(): String {
        val app = getApplication<Application>()
        val syncId = _cloudSyncId.value
        val payload = CloudSyncManager.generateCloudPayload(syncId, uiState.value.verses)
        CloudSyncManager.recordSyncSuccess(app)
        _lastSyncTime.value = CloudSyncManager.getLastSyncTime(app)
        _syncStatusMessage.value = "¡Copia de respaldo sincronizada con éxito en la nube!"
        return payload
    }

    fun performCloudRestore(payloadJson: String) {
        val app = getApplication<Application>()
        viewModelScope.launch {
            try {
                val merged = CloudSyncManager.applyCloudPayload(payloadJson, repository, uiState.value.verses)
                CloudSyncManager.recordSyncSuccess(app)
                _lastSyncTime.value = CloudSyncManager.getLastSyncTime(app)
                _syncStatusMessage.value = "Sincronización completada: $merged versículo(s) actualizados."
            } catch (e: Exception) {
                _syncStatusMessage.value = "Error en formato de sincronización: ${e.message}"
            }
        }
    }

    val permanentSha1: String = FirebaseSyncManager.PERMANENT_KEYSTORE_SHA1
    val permanentSha256: String = FirebaseSyncManager.PERMANENT_KEYSTORE_SHA256

    fun getSavedFirebaseWebClientId(context: Context): String? {
        return FirebaseSyncManager.getSavedWebClientId(context)
    }

    fun saveFirebaseWebClientId(context: Context, clientId: String) {
        FirebaseSyncManager.saveWebClientId(context, clientId)
    }

    fun getResolvedFirebaseWebClientId(context: Context): String? {
        return FirebaseSyncManager.resolveServerClientId(context)
    }

    fun signInWithGoogle(context: Context, serverClientId: String? = null) {
        viewModelScope.launch {
            _syncStatusMessage.value = "Conectando con cuenta de Google..."
            val result = FirebaseSyncManager.signInWithGoogle(context, serverClientId)
            result.onSuccess { user ->
                _syncStatusMessage.value = "Sesión iniciada como ${user.displayName ?: user.email}"
                // Auto-sync after successful sign-in
                syncWithFirestore()
            }.onFailure { err ->
                _syncStatusMessage.value = "${err.message}"
            }
        }
    }

    fun signInAnonymously() {
        val app = getApplication<Application>()
        viewModelScope.launch {
            _syncStatusMessage.value = "Conectando con nube de Firebase..."
            val result = FirebaseSyncManager.signInAnonymously(app)
            result.onSuccess {
                _syncStatusMessage.value = "Conectado a la nube. Sincronizando..."
                syncWithFirestore()
            }.onFailure { err ->
                _syncStatusMessage.value = "Error conectando a la nube: ${err.message}"
            }
        }
    }

    fun signInWithEmailPassword(email: String, pass: String) {
        val app = getApplication<Application>()
        viewModelScope.launch {
            _syncStatusMessage.value = "Iniciando sesión con correo..."
            val result = FirebaseSyncManager.signInWithEmailPassword(app, email, pass)
            result.onSuccess { user ->
                _syncStatusMessage.value = "Sesión iniciada como ${user.email}"
                syncWithFirestore()
            }.onFailure { err ->
                _syncStatusMessage.value = "Error: ${err.message}"
            }
        }
    }

    fun signOutFirebase(context: Context) {
        viewModelScope.launch {
            FirebaseSyncManager.signOut(context)
            _syncStatusMessage.value = "Sesión cerrada en la nube"
        }
    }

    fun uploadToFirestore() {
        val app = getApplication<Application>()
        viewModelScope.launch {
            val result = FirebaseSyncManager.uploadToFirestore(uiState.value.verses, app)
            result.onSuccess { count ->
                CloudSyncManager.recordSyncSuccess(app)
                _lastSyncTime.value = CloudSyncManager.getLastSyncTime(app)
                _syncStatusMessage.value = "¡$count elementos respaldados con éxito en tu cuenta!"
            }.onFailure { err ->
                _syncStatusMessage.value = "Error al respaldar en la nube: ${err.message}"
            }
        }
    }

    fun downloadFromFirestore() {
        val app = getApplication<Application>()
        viewModelScope.launch {
            val result = FirebaseSyncManager.downloadFromFirestore(repository, uiState.value.verses, app)
            result.onSuccess { count ->
                CloudSyncManager.recordSyncSuccess(app)
                _lastSyncTime.value = CloudSyncManager.getLastSyncTime(app)
                _syncStatusMessage.value = "¡$count versículos y notas restaurados desde la nube!"
            }.onFailure { err ->
                _syncStatusMessage.value = "Error al restaurar desde la nube: ${err.message}"
            }
        }
    }

    fun syncWithFirestore() {
        val app = getApplication<Application>()
        viewModelScope.launch {
            val downloadRes = FirebaseSyncManager.downloadFromFirestore(repository, uiState.value.verses, app)
            val uploadRes = FirebaseSyncManager.uploadToFirestore(uiState.value.verses, app)
            if (downloadRes.isSuccess || uploadRes.isSuccess) {
                CloudSyncManager.recordSyncSuccess(app)
                _lastSyncTime.value = CloudSyncManager.getLastSyncTime(app)
                _syncStatusMessage.value = "Sincronización en la nube completada."
            }
        }
    }

    fun updateCloudSyncId(newId: String) {
        val app = getApplication<Application>()
        CloudSyncManager.setSyncId(app, newId)
        _cloudSyncId.value = newId
    }

    fun clearSyncMessage() {
        _syncStatusMessage.value = null
    }

    fun checkForUpdates(silent: Boolean = false) {
        val app = getApplication<Application>()
        _updateDownloadStatus.value = UpdateDownloadStatus.Checking
        viewModelScope.launch {
            val result = AppUpdateManager.checkForUpdate(app)
            result.onSuccess { info ->
                _updateInfo.value = info
                if (info.isUpdateAvailable) {
                    _updateDownloadStatus.value = UpdateDownloadStatus.Available(info)
                    // If checked automatically in background (silent), respect postpone/ignore preferences
                    if (!silent || AppUpdateManager.shouldShowAutomaticPrompt(app, info.tagName)) {
                        _showUpdateDialog.value = true
                    }
                } else {
                    _updateDownloadStatus.value = UpdateDownloadStatus.UpToDate
                    if (!silent) {
                        _syncStatusMessage.value = "Tienes la última versión instalada (v${BuildConfig.VERSION_NAME})"
                    }
                }
            }.onFailure { err ->
                _updateDownloadStatus.value = UpdateDownloadStatus.Error(err.localizedMessage ?: "Error al verificar actualizaciones")
                if (!silent) {
                    _syncStatusMessage.value = "Error al verificar actualización: ${err.localizedMessage}"
                }
            }
        }
    }

    fun startUpdateDownload() {
        val app = getApplication<Application>()
        val info = _updateInfo.value ?: return
        val downloadUrl = info.apkDownloadUrl ?: return

        _updateDownloadStatus.value = UpdateDownloadStatus.Downloading(0f, 0L, info.apkSizeBytes)
        viewModelScope.launch {
            val result = AppUpdateManager.downloadApk(app, downloadUrl) { progress, downloaded, total ->
                _updateDownloadStatus.value = UpdateDownloadStatus.Downloading(progress, downloaded, total)
            }
            result.onSuccess { file ->
                _updateDownloadStatus.value = UpdateDownloadStatus.ReadyToInstall(file.absolutePath, info)
                AppUpdateManager.installApk(app, file)
            }.onFailure { err ->
                _updateDownloadStatus.value = UpdateDownloadStatus.Error(err.localizedMessage ?: "Error al descargar el APK")
            }
        }
    }

    fun installDownloadedApk(context: Context) {
        val status = _updateDownloadStatus.value
        if (status is UpdateDownloadStatus.ReadyToInstall) {
            val file = File(status.apkPath)
            AppUpdateManager.installApk(context, file)
        }
    }

    fun openGitHubReleaseInBrowser(context: Context) {
        val info = _updateInfo.value
        val url = info?.apkDownloadUrl ?: info?.htmlUrl ?: "https://github.com/${_githubRepo.value}/releases"
        AppUpdateManager.openBrowser(context, url)
    }

    fun setGitHubRepo(newRepo: String) {
        val app = getApplication<Application>()
        AppUpdateManager.setGitHubRepo(app, newRepo)
        _githubRepo.value = AppUpdateManager.getGitHubRepo(app)
        _syncStatusMessage.value = "Repositorio configurado: ${_githubRepo.value}"
    }

    fun setShowUpdateDialog(show: Boolean) {
        _showUpdateDialog.value = show
    }

    fun postponeUpdate(hours: Int = 24) {
        val app = getApplication<Application>()
        AppUpdateManager.postponeUpdate(app, hours)
        _showUpdateDialog.value = false
        _syncStatusMessage.value = "Te recordaremos sobre esta actualización más adelante."
    }

    fun ignoreCurrentVersion() {
        val app = getApplication<Application>()
        val tag = _updateInfo.value?.tagName ?: return
        AppUpdateManager.ignoreVersion(app, tag)
        _showUpdateDialog.value = false
        _syncStatusMessage.value = "Se omitieron los avisos automáticos para la versión $tag."
    }

    fun setAutoCheckUpdates(enabled: Boolean) {
        val app = getApplication<Application>()
        AppUpdateManager.setAutoCheckEnabled(app, enabled)
        _autoCheckUpdates.value = enabled
        _syncStatusMessage.value = if (enabled) {
            "Búsqueda automática de actualizaciones activada."
        } else {
            "Búsqueda automática desactivada. Puedes buscar manualmente en Ajustes."
        }
    }

    // Bible Chatbot Actions
    fun showBibleChat(show: Boolean) {
        _showBibleChatDialog.value = show
    }

    fun sendBibleChatMessage(question: String) {
        val text = question.trim()
        if (text.isBlank() || _isChatLoading.value) return

        val userMessage = ChatMessage(text = text, isUser = true)
        val currentHistory = _chatMessages.value
        _chatMessages.value = currentHistory + userMessage
        _isChatLoading.value = true

        viewModelScope.launch {
            try {
                val assistantReply = GeminiBibleChatService.askBibleQuestion(
                    history = currentHistory,
                    userQuestion = text
                )
                _chatMessages.value = _chatMessages.value + assistantReply
            } catch (e: Exception) {
                val errorMessage = ChatMessage(
                    text = "Ocurrió un error al procesar la respuesta. Por favor intenta de nuevo.",
                    isUser = false
                )
                _chatMessages.value = _chatMessages.value + errorMessage
            } finally {
                _isChatLoading.value = false
            }
        }
    }

    fun clearBibleChat() {
        _chatMessages.value = emptyList()
    }

    fun openAddVerseForReference(reference: String) {
        _showBibleChatDialog.value = false
        _showAddDialog.value = true
    }

    fun downloadOfflineVersion(versionCode: String) {
        viewModelScope.launch {
            com.example.data.bible.OfflineBibleDownloadManager.downloadVersion(bibleReaderDao, versionCode)
        }
    }

    fun refreshOfflineVersions() {
        viewModelScope.launch {
            com.example.data.bible.OfflineBibleDownloadManager.refreshStatuses(bibleReaderDao)
        }
    }
}

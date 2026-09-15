package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.BibleDatabase
import com.example.data.model.VerseEntity
import com.example.data.repository.VerseRepository
import com.example.export.ExportManager
import com.example.service.NotificationHelper
import com.example.sync.CloudSyncManager
import com.example.ui.theme.AppReadingTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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

    init {
        val db = BibleDatabase.getDatabase(application, viewModelScope)
        repository = VerseRepository(db.verseDao())

        // Ensure pre-population on first run
        viewModelScope.launch {
            repository.checkAndSeedInitialData()
        }

        // Setup notification channel and alarm schedule
        NotificationHelper.createNotificationChannel(application)
        if (_reminderEnabled.value) {
            NotificationHelper.scheduleDailyReminder(application, _reminderHour.value, _reminderMinute.value)
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
    ) { allVerses, filterParams, displayParams, dialogParams, detailSync ->
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
        notes: String
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
        topic: String
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

    fun updateCloudSyncId(newId: String) {
        val app = getApplication<Application>()
        CloudSyncManager.setSyncId(app, newId)
        _cloudSyncId.value = newId
    }

    fun clearSyncMessage() {
        _syncStatusMessage.value = null
    }
}

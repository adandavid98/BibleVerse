package com.example.ui.reader.model

import com.example.data.model.BibleBookEntity
import com.example.data.preferences.ReaderPreferences
import com.example.domain.model.ReaderVerseUiModel

enum class ShareCardTemplate(
    val title: String,
    val subtitle: String,
    val previewColorHex: String
) {
    MINIMALIST(
        title = "Minimalista Editorial",
        subtitle = "Diseño limpio, tipografía solemne y fondo claro",
        previewColorHex = "#FFFFFF"
    ),
    SACRED_GRADIENT(
        title = "Gradiente Celestial",
        subtitle = "Azul noche profundo con contrastes luminosos",
        previewColorHex = "#1E1B4B"
    ),
    PARCHMENT(
        title = "Pergamino Clásico",
        subtitle = "Estilo clásico cálido con detalles históricos",
        previewColorHex = "#FBF0D9"
    ),
    DEEP_NIGHT(
        title = "Noche y Oro",
        subtitle = "Elegancia oscura solemne con acentos dorados",
        previewColorHex = "#0B0F19"
    )
}

data class HighlightColorItem(
    val hex: String,
    val name: String
)

object HighlightPalette {
    val colors = listOf(
        // Tonos oscuros y profundos
        HighlightColorItem("#1E40AF", "Azul Zafiro Oscuro"),
        HighlightColorItem("#166534", "Verde Bosque Oscuro"),
        HighlightColorItem("#7E22CE", "Púrpura Imperial"),
        HighlightColorItem("#991B1B", "Rojo Carmesí"),
        HighlightColorItem("#B45309", "Ámbar Intenso"),
        // Tonos clásicos
        HighlightColorItem("#FEF08A", "Amarillo Sol"),
        HighlightColorItem("#A7F3D0", "Verde Menta"),
        HighlightColorItem("#BAE6FD", "Azul Cielo"),
        HighlightColorItem("#E9D5FF", "Lavanda"),
        HighlightColorItem("#FBCFE8", "Rosa Pastel"),
        HighlightColorItem("#FED7AA", "Melocotón")
    )
}

data class ReadingPlanSession(
    val planId: String,
    val planTitle: String,
    val dayNumber: Int,
    val dayTitle: String,
    val segments: List<com.example.data.bible.PlanPassageSegment>,
    val currentSegmentIndex: Int = 0,
    val readSegmentIndices: Set<Int> = emptySet(),
    val isCustomPlan: Boolean = false,
    val storyNarrative: String? = null
) {
    val totalSegments: Int get() = segments.size
    val planKey: String get() = if (isCustomPlan && !planId.startsWith("CUSTOM_")) "CUSTOM_$planId" else planId
    val currentSegment: com.example.data.bible.PlanPassageSegment? get() = segments.getOrNull(currentSegmentIndex)
    val isAllSegmentsRead: Boolean get() = totalSegments > 0 && readSegmentIndices.size >= totalSegments
    val progressPercentage: Float get() = if (totalSegments > 0) (readSegmentIndices.size.toFloat() / totalSegments.toFloat()).coerceIn(0f, 1f) else 0f
}

data class BibleReaderUiState(
    val isLoading: Boolean = true,
    val isLoadingMore: Boolean = false,
    val books: List<BibleBookEntity> = emptyList(),
    val currentBook: BibleBookEntity? = null,
    val currentChapter: Int = 1,
    val verses: List<ReaderVerseUiModel> = emptyList(),
    val selectedVerseNumbers: Set<Int> = emptySet(),
    val preferences: ReaderPreferences = ReaderPreferences(),
    val isBookChapterSelectorOpen: Boolean = false,
    val isSettingsSheetOpen: Boolean = false,
    val isShareDialogOpen: Boolean = false,
    val isCompareModalOpen: Boolean = false,
    val isCrossReferencesOpen: Boolean = false,
    val selectedCrossReferenceVerse: ReaderVerseUiModel? = null,
    val chapterParallelTitle: String? = null,
    val chapterParallelReferences: List<com.example.data.bible.CrossReferenceItem>? = null,
    val targetScrollVerse: Int? = null,
    val transientHighlightedVerses: Set<Int> = emptySet(),
    val userMessage: String? = null,
    val isReaderBarsVisible: Boolean = true,
    val activePlanSession: ReadingPlanSession? = null
)

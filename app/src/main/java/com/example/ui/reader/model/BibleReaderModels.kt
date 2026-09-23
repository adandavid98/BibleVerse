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
    )
}

data class HighlightColorItem(
    val hex: String,
    val name: String
)

object HighlightPalette {
    val colors = listOf(
        HighlightColorItem("#FEF08A", "Amarillo Sol"),
        HighlightColorItem("#A7F3D0", "Verde Menta"),
        HighlightColorItem("#BAE6FD", "Azul Cielo"),
        HighlightColorItem("#E9D5FF", "Lavanda"),
        HighlightColorItem("#FBCFE8", "Rosa Pastel"),
        HighlightColorItem("#FED7AA", "Melocotón"),
        HighlightColorItem("#99F6E4", "Esmeralda"),
        HighlightColorItem("#FDE68A", "Oro Cálido")
    )
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
    val targetScrollVerse: Int? = null,
    val userMessage: String? = null,
    val isReaderBarsVisible: Boolean = true
)

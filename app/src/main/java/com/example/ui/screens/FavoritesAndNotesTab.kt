package com.example.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.components.VerseCard
import com.example.ui.viewmodel.BibleUiState
import com.example.ui.viewmodel.BibleViewModel
import com.example.ui.viewmodel.VerseFilter

@Composable
fun FavoritesAndNotesTab(
    viewModel: BibleViewModel,
    uiState: BibleUiState
) {
    val context = LocalContext.current
    val activeFilter = when (uiState.selectedFilter) {
        VerseFilter.RESALTADOS -> VerseFilter.RESALTADOS
        VerseFilter.CON_NOTAS -> VerseFilter.CON_NOTAS
        else -> VerseFilter.FAVORITOS
    }

    val displayedVerses = when (activeFilter) {
        VerseFilter.FAVORITOS -> uiState.verses.filter { it.isFavorite }
        VerseFilter.RESALTADOS -> uiState.verses.filter { it.highlightColor.isNotBlank() }
        VerseFilter.CON_NOTAS -> uiState.verses.filter { it.notes.isNotBlank() }
        else -> uiState.verses.filter { it.isFavorite }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = activeFilter == VerseFilter.FAVORITOS,
                onClick = { viewModel.onFilterSelected(VerseFilter.FAVORITOS) },
                label = {
                    Text(
                        text = "Favoritos (${uiState.verses.count { it.isFavorite }})",
                        maxLines = 1,
                        softWrap = false
                    )
                },
                leadingIcon = { Icon(Icons.Filled.Favorite, contentDescription = null, modifier = Modifier.size(16.dp)) },
                modifier = Modifier.testTag("chip_sub_favorites")
            )
            FilterChip(
                selected = activeFilter == VerseFilter.RESALTADOS,
                onClick = { viewModel.onFilterSelected(VerseFilter.RESALTADOS) },
                label = {
                    Text(
                        text = "Resaltados (${uiState.verses.count { it.highlightColor.isNotBlank() }})",
                        maxLines = 1,
                        softWrap = false
                    )
                },
                leadingIcon = { Icon(Icons.Filled.Bookmark, contentDescription = null, modifier = Modifier.size(16.dp)) },
                modifier = Modifier.testTag("chip_sub_highlights")
            )
            FilterChip(
                selected = activeFilter == VerseFilter.CON_NOTAS,
                onClick = { viewModel.onFilterSelected(VerseFilter.CON_NOTAS) },
                label = {
                    Text(
                        text = "Con Notas (${uiState.verses.count { it.notes.isNotBlank() }})",
                        maxLines = 1,
                        softWrap = false
                    )
                },
                leadingIcon = { Icon(Icons.Filled.Edit, contentDescription = null, modifier = Modifier.size(16.dp)) },
                modifier = Modifier.testTag("chip_sub_notes")
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (displayedVerses.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Icon(
                        imageVector = when (activeFilter) {
                            VerseFilter.FAVORITOS -> Icons.Outlined.FavoriteBorder
                            VerseFilter.RESALTADOS -> Icons.Outlined.BookmarkBorder
                            else -> Icons.Filled.Edit
                        },
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = when (activeFilter) {
                            VerseFilter.FAVORITOS -> "Aún no tienes versículos favoritos marcados."
                            VerseFilter.RESALTADOS -> "No has resaltado ningún versículo todavía."
                            else -> "No has escrito notas personales en ningún versículo."
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Toca el corazón o el resaltador en cualquier versículo para guardarlo aquí.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(displayedVerses, key = { it.id }) { verse ->
                    VerseCard(
                        verse = verse,
                        fontScale = uiState.fontSizeScale,
                        onClick = { viewModel.openVerseDetail(verse) },
                        onFavoriteToggle = { viewModel.toggleFavorite(verse) },
                        onShare = { viewModel.shareVerse(context, verse) },
                        onHighlightClick = { viewModel.openVerseDetail(verse) }
                    )
                }
            }
        }
    }
}

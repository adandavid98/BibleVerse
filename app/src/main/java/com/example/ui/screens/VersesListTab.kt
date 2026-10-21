package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.bible.BibleBook
import com.example.data.bible.BibleCatalog
import com.example.data.bible.BibleTextSanitizer
import com.example.ui.components.VerseCard
import com.example.ui.viewmodel.BibleUiState
import com.example.ui.viewmodel.BibleViewModel
import com.example.ui.viewmodel.VerseFilter

@Composable
fun VersesListTab(
    viewModel: BibleViewModel,
    uiState: BibleUiState,
    lastReadBookName: String = "Génesis",
    lastReadBookId: Int = 1,
    lastReadChapter: Int = 1,
    lastReadVersion: String = "RVR1960",
    listState: LazyListState = rememberLazyListState(),
    onOpenReadingPlans: () -> Unit = {},
    onNavigateToReader: (bookId: Int, chapter: Int, verse: Int) -> Unit = { _, _, _ -> }
) {
    val context = LocalContext.current
    var selectedBook by remember { mutableStateOf<BibleBook?>(null) }
    var isBookPickerOpen by remember { mutableStateOf(false) }

    val otCount = remember(uiState.verses) { uiState.verses.count { it.testament.contains("Antiguo", ignoreCase = true) } }
    val ntCount = remember(uiState.verses) { uiState.verses.count { it.testament.contains("Nuevo", ignoreCase = true) } }

    val bookVerseCounts = remember(uiState.verses) {
        val map = mutableMapOf<String, Int>()
        uiState.verses.forEach { verse ->
            val clean = BibleTextSanitizer.removeAccents(verse.book).lowercase()
            map[clean] = (map[clean] ?: 0) + 1
        }
        map
    }

    val displayedVerses = remember(uiState.verses, uiState.selectedFilter, selectedBook) {
        if (selectedBook != null) {
            val targetClean = BibleTextSanitizer.removeAccents(selectedBook!!.name).lowercase()
            uiState.verses.filter {
                BibleTextSanitizer.removeAccents(it.book).lowercase() == targetClean
            }
        } else {
            when (uiState.selectedFilter) {
                VerseFilter.TODOS -> uiState.verses
                VerseFilter.ANTIGUO -> uiState.verses.filter { it.testament.contains("Antiguo", ignoreCase = true) }
                VerseFilter.NUEVO -> uiState.verses.filter { it.testament.contains("Nuevo", ignoreCase = true) }
                VerseFilter.FAVORITOS -> uiState.verses.filter { it.isFavorite }
                VerseFilter.CON_NOTAS -> uiState.verses.filter { it.notes.isNotBlank() }
                VerseFilter.RESALTADOS -> uiState.verses.filter { it.highlightColor.isNotBlank() }
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
    ) {
        // Continuar Lectura Card (Resume Reading Shortcut)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onNavigateToReader(lastReadBookId, lastReadChapter, 1) }
                    .testTag("card_resume_reading"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.AutoStories,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "CONTINUAR LECTURA",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "$lastReadBookName $lastReadChapter",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Traducción activa: $lastReadVersion",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    FilledTonalButton(
                        onClick = { onNavigateToReader(lastReadBookId, lastReadChapter, 1) },
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text("Reanudar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
                    }
                }
            }
        }

        // Verse of the Day Card (Destacado del Día)
        uiState.verseOfTheDay?.let { vod ->
            item {
                VerseOfTheDayCard(
                    verse = vod,
                    fontScale = uiState.fontSizeScale,
                    onClick = { viewModel.openVerseDetail(vod) },
                    onFavoriteToggle = { viewModel.toggleFavorite(vod) },
                    onShare = { viewModel.shareVerse(context, vod) }
                )
            }
        }

        // Reading Plan Quick Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onOpenReadingPlans() },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "Plan de Lectura Anual",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "365 días • Clásico y Cronológico",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Button(
                        onClick = onOpenReadingPlans,
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("Ver Plan", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Filter Pills: Todos, Antiguo Testamento, Nuevo Testamento, Por Libro...
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedBook == null && uiState.selectedFilter == VerseFilter.TODOS,
                    onClick = { 
                        selectedBook = null
                        viewModel.onFilterSelected(VerseFilter.TODOS) 
                    },
                    label = { Text("Todos (${uiState.verses.size})") },
                    modifier = Modifier.testTag("filter_all")
                )
                FilterChip(
                    selected = selectedBook == null && uiState.selectedFilter == VerseFilter.ANTIGUO,
                    onClick = { 
                        selectedBook = null
                        viewModel.onFilterSelected(VerseFilter.ANTIGUO) 
                    },
                    label = { Text("Antiguo Testamento ($otCount)") },
                    modifier = Modifier.testTag("filter_ot")
                )
                FilterChip(
                    selected = selectedBook == null && uiState.selectedFilter == VerseFilter.NUEVO,
                    onClick = { 
                        selectedBook = null
                        viewModel.onFilterSelected(VerseFilter.NUEVO) 
                    },
                    label = { Text("Nuevo Testamento ($ntCount)") },
                    modifier = Modifier.testTag("filter_nt")
                )
                val selectedBookCount = remember(selectedBook, bookVerseCounts) {
                    if (selectedBook != null) {
                        bookVerseCounts[BibleTextSanitizer.removeAccents(selectedBook!!.name).lowercase()] ?: 0
                    } else 0
                }
                FilterChip(
                    selected = selectedBook != null,
                    onClick = { isBookPickerOpen = true },
                    label = { 
                        Text(if (selectedBook != null) "Libro: ${selectedBook!!.name} ($selectedBookCount)" else "Por Libro...") 
                    },
                    trailingIcon = if (selectedBook != null) {
                        {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Limpiar filtro de libro",
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable { selectedBook = null }
                            )
                        }
                    } else null,
                    modifier = Modifier.testTag("filter_book")
                )
            }
        }

        // Verses Cards List
        items(
            items = displayedVerses,
            key = { it.id }
        ) { verse ->
            VerseCard(
                verse = verse,
                fontScale = uiState.fontSizeScale,
                onClick = { viewModel.openVerseDetail(verse) },
                onFavoriteToggle = { viewModel.toggleFavorite(verse) },
                onShare = { viewModel.shareVerse(context, verse) },
                onHighlightClick = { viewModel.openVerseDetail(verse) }
            )
        }

        if (displayedVerses.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                        )
                        Text(
                            text = if (selectedBook != null) "No hay versículos guardados en ${selectedBook!!.name}" else "No hay versículos disponibles",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (selectedBook != null) {
                            TextButton(onClick = { selectedBook = null }) {
                                Text("Ver todos los versículos")
                            }
                        }
                    }
                }
            }
        }
    }

    // Book Picker Dialog for filtering verses by book
    if (isBookPickerOpen) {
        AlertDialog(
            onDismissRequest = { isBookPickerOpen = false },
            title = { Text("Seleccionar Libro para Filtrar", fontWeight = FontWeight.Bold) },
            text = {
                var bookSearch by remember { mutableStateOf("") }
                val filteredBooks = remember(bookSearch) {
                    if (bookSearch.isBlank()) BibleCatalog.books
                    else {
                        val searchClean = BibleTextSanitizer.removeAccents(bookSearch.trim()).lowercase()
                        BibleCatalog.books.filter { 
                            BibleTextSanitizer.removeAccents(it.name).lowercase().contains(searchClean) 
                        }
                    }
                }

                Column(modifier = Modifier.fillMaxWidth().height(350.dp)) {
                    OutlinedTextField(
                        value = bookSearch,
                        onValueChange = { bookSearch = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Buscar libro...") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        item {
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedBook = null
                                        isBookPickerOpen = false
                                    }
                                    .padding(vertical = 10.dp, horizontal = 8.dp)
                            ) {
                                Text(
                                    "Todos los libros (Sin filtro)",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Divider()
                        }
                        itemsIndexed(filteredBooks) { index, book ->
                            val isFirstNt = index > 0 && filteredBooks[index - 1].order <= 39 && book.order > 39
                            if (isFirstNt) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Divider(
                                        modifier = Modifier.weight(1f),
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.7f)
                                    )
                                    Text(
                                        text = "Nuevo Testamento",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                    Divider(
                                        modifier = Modifier.weight(1f),
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.7f)
                                    )
                                }
                            }
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedBook = book
                                        isBookPickerOpen = false
                                    }
                                    .padding(vertical = 8.dp, horizontal = 8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(book.name, fontWeight = FontWeight.Medium)
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val count = bookVerseCounts[BibleTextSanitizer.removeAccents(book.name).lowercase()] ?: 0
                                        if (count > 0) {
                                            Surface(
                                                shape = RoundedCornerShape(8.dp),
                                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                                            ) {
                                                Text(
                                                    text = "$count",
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            if (book.order <= 39) "AT" else "NT",
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { isBookPickerOpen = false }) {
                    Text("Cerrar")
                }
            }
        )
    }
}

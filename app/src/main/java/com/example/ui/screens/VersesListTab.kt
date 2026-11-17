package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
                    onShareWithBackground = { bgRes ->
                        com.example.ui.reader.export.CardBitmapRenderer.shareVerseOfTheDayImage(context, vod, bgRes)
                    }
                )
            }
        }

        // Reading Plan Quick Banner (Estilo YouVersion con Portada y Gradiente)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(84.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .clickable { onOpenReadingPlans() },
                shape = RoundedCornerShape(18.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.plan_cover_chronological),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.90f),
                                        Color.Black.copy(alpha = 0.70f),
                                        Color.Black.copy(alpha = 0.35f)
                                    )
                                )
                            )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 12.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Planes y Devocionales",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Clásico, Cronológico e Historias",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.85f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        FilledTonalButton(
                            onClick = onOpenReadingPlans,
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            colors = androidx.compose.material3.ButtonDefaults.filledTonalButtonColors(
                                containerColor = Color.White.copy(alpha = 0.22f),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Explorar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
                        }
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

    // Book Picker BottomSheet for filtering verses by book
    if (isBookPickerOpen) {
        var bookSearch by remember { mutableStateOf("") }
        var testamentTabIndex by remember { mutableIntStateOf(0) } // 0: Todo, 1: A.T., 2: N.T.

        val filteredBooks = remember(bookSearch, testamentTabIndex) {
            val byTestament = when (testamentTabIndex) {
                1 -> BibleCatalog.books.filter { it.order <= 39 }
                2 -> BibleCatalog.books.filter { it.order > 39 }
                else -> BibleCatalog.books
            }
            if (bookSearch.isBlank()) byTestament
            else {
                val searchClean = BibleTextSanitizer.removeAccents(bookSearch.trim()).lowercase()
                byTestament.filter {
                    BibleTextSanitizer.removeAccents(it.name).lowercase().contains(searchClean)
                }
            }
        }

        Dialog(
            onDismissRequest = { isBookPickerOpen = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                decorFitsSystemWindows = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.55f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { isBookPickerOpen = false }
                    ),
                contentAlignment = Alignment.BottomCenter
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.84f)
                        .imePadding()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {}
                        ),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding()
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        // Drag handle
                        Box(
                            modifier = Modifier
                                .size(width = 36.dp, height = 4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f))
                                .align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Filtrar por Libro",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${BibleCatalog.books.size} libros • toca uno para filtrar",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                            ) {
                                IconButton(
                                    onClick = { isBookPickerOpen = false },
                                    modifier = Modifier.size(34.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Cerrar",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Search bar with icon & clear button
                        OutlinedTextField(
                            value = bookSearch,
                            onValueChange = { bookSearch = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Buscar libro (ej. Salmos, Juan)...") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            trailingIcon = {
                                if (bookSearch.isNotEmpty()) {
                                    IconButton(onClick = { bookSearch = "" }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Limpiar búsqueda",
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Testament Segmented Tabs: Todo (66) | A.T. (39) | N.T. (27)
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                listOf(
                                    0 to "Todo (66)",
                                    1 to "A.T. (39)",
                                    2 to "N.T. (27)"
                                ).forEach { (idx, label) ->
                                    val isTabSelected = testamentTabIndex == idx
                                    Surface(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(10.dp))
                                            .clickable { testamentTabIndex = idx },
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (isTabSelected) MaterialTheme.colorScheme.primary else Color.Transparent
                                    ) {
                                        Box(
                                            modifier = Modifier.padding(vertical = 8.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = label,
                                                fontSize = 12.sp,
                                                fontWeight = if (isTabSelected) FontWeight.Bold else FontWeight.Medium,
                                                color = if (isTabSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Book List
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            // "Todos los libros (Sin filtro)" card
                            item(key = "all_books_filter") {
                                val isAllSelected = selectedBook == null
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable {
                                            selectedBook = null
                                            isBookPickerOpen = false
                                        },
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (isAllSelected) {
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                                    } else {
                                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                    },
                                    border = if (isAllSelected) {
                                        BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                                    } else null
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 14.dp, vertical = 12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Todos los libros (Sin filtro)",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        if (isAllSelected) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Seleccionado",
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            if (filteredBooks.isEmpty()) {
                                item(key = "no_books_found") {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 32.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "No se encontró ningún libro",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            } else {
                                itemsIndexed(filteredBooks, key = { _, book -> book.order }) { index, book ->
                                    val isFirstNt = testamentTabIndex == 0 &&
                                        index > 0 &&
                                        filteredBooks[index - 1].order <= 39 &&
                                        book.order > 39
                                    if (isFirstNt) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 6.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Divider(
                                                modifier = Modifier.weight(1f),
                                                thickness = 1.dp,
                                                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.7f)
                                            )
                                            Surface(
                                                shape = RoundedCornerShape(10.dp),
                                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                                                modifier = Modifier.padding(horizontal = 8.dp)
                                            ) {
                                                Text(
                                                    text = "Nuevo Testamento",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                                                )
                                            }
                                            Divider(
                                                modifier = Modifier.weight(1f),
                                                thickness = 1.dp,
                                                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.7f)
                                            )
                                        }
                                    }

                                    val count = bookVerseCounts[BibleTextSanitizer.removeAccents(book.name).lowercase()] ?: 0
                                    val isBookSelected = selectedBook?.order == book.order
                                    val cardColor = if (isBookSelected) {
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                                            .compositeOver(MaterialTheme.colorScheme.surface)
                                    } else {
                                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.42f)
                                            .compositeOver(MaterialTheme.colorScheme.surface)
                                    }

                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(12.dp))
                                            .clickable {
                                                selectedBook = book
                                                isBookPickerOpen = false
                                            }
                                            .alpha(if (count == 0 && !isBookSelected) 0.58f else 1f),
                                        shape = RoundedCornerShape(12.dp),
                                        color = cardColor,
                                        border = if (isBookSelected) {
                                            BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                                        } else null
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 14.dp, vertical = 12.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Text(
                                                    text = book.name,
                                                    fontWeight = if (isBookSelected) FontWeight.Bold else FontWeight.SemiBold,
                                                    fontSize = 14.5.sp,
                                                    color = if (isBookSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                                )
                                                if (isBookSelected) {
                                                    Icon(
                                                        imageVector = Icons.Default.Check,
                                                        contentDescription = "Seleccionado",
                                                        tint = MaterialTheme.colorScheme.primary,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }

                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                if (count > 0) {
                                                    Surface(
                                                        shape = RoundedCornerShape(999.dp),
                                                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                                                    ) {
                                                        Text(
                                                            text = "$count vers.",
                                                            fontSize = 11.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                                        )
                                                    }
                                                }
                                                Surface(
                                                    shape = RoundedCornerShape(6.dp),
                                                    color = MaterialTheme.colorScheme.surface
                                                ) {
                                                    Text(
                                                        text = if (book.order <= 39) "AT" else "NT",
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                        fontSize = 11.sp,
                                                        fontWeight = FontWeight.SemiBold,
                                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

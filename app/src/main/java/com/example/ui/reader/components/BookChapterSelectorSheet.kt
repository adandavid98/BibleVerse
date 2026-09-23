package com.example.ui.reader.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.BibleBookEntity

enum class SelectorStep {
    BOOK,
    CHAPTER,
    VERSE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookChapterSelectorSheet(
    books: List<BibleBookEntity>,
    currentBook: BibleBookEntity?,
    currentChapter: Int,
    currentVerse: Int = 1,
    onSelectBookChapterVerse: (BibleBookEntity, Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    // 0: Todo, 1: Antiguo Testamento, 2: Nuevo Testamento
    var selectedFilterIndex by remember { mutableIntStateOf(0) }
    var currentStep by remember { mutableStateOf(SelectorStep.BOOK) }
    var chosenBook by remember { mutableStateOf(currentBook ?: books.firstOrNull()) }
    var chosenChapter by remember { mutableIntStateOf(currentChapter) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredBooks = remember(books, selectedFilterIndex, searchQuery) {
        val byTestament = when (selectedFilterIndex) {
            1 -> books.filter { it.orderIndex <= 39 }
            2 -> books.filter { it.orderIndex > 39 }
            else -> books // Todo (1 to 66 in order)
        }
        if (searchQuery.isBlank()) byTestament
        else byTestament.filter {
            it.name.contains(searchQuery, ignoreCase = true) || it.abbreviation.contains(searchQuery, ignoreCase = true)
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        BackHandler {
            when (currentStep) {
                SelectorStep.VERSE -> currentStep = SelectorStep.CHAPTER
                SelectorStep.CHAPTER -> currentStep = SelectorStep.BOOK
                SelectorStep.BOOK -> onDismiss()
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                contentWindowInsets = WindowInsets.safeDrawing,
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            if (currentStep != SelectorStep.BOOK) {
                                IconButton(
                                    onClick = {
                                        currentStep = when (currentStep) {
                                            SelectorStep.VERSE -> SelectorStep.CHAPTER
                                            SelectorStep.CHAPTER -> SelectorStep.BOOK
                                            else -> SelectorStep.BOOK
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                                }
                            }
                        },
                        title = {
                            Text(
                                text = when (currentStep) {
                                    SelectorStep.BOOK -> "Seleccionar Libro"
                                    SelectorStep.CHAPTER -> "${chosenBook?.name ?: ""} — Capítulos"
                                    SelectorStep.VERSE -> "${chosenBook?.name ?: ""} $chosenChapter — Versículos"
                                },
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        actions = {
                            IconButton(onClick = onDismiss) {
                                Icon(Icons.Default.Close, contentDescription = "Cerrar")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {

            AnimatedContent(
                targetState = currentStep,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "SelectorStepAnim"
            ) { step ->
                when (step) {
                    SelectorStep.BOOK -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            // 3 Filter Tabs: Todo, Antiguo Testamento, Nuevo Testamento
                            TabRow(
                                selectedTabIndex = selectedFilterIndex,
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.primary
                            ) {
                                Tab(
                                    selected = selectedFilterIndex == 0,
                                    onClick = { selectedFilterIndex = 0 },
                                    text = { Text("Todo (66)") }
                                )
                                Tab(
                                    selected = selectedFilterIndex == 1,
                                    onClick = { selectedFilterIndex = 1 },
                                    text = { Text("A.T. (39)") }
                                )
                                Tab(
                                    selected = selectedFilterIndex == 2,
                                    onClick = { selectedFilterIndex = 2 },
                                    text = { Text("N.T. (27)") }
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Search bar
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Buscar libro...") },
                                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Book list in order Genesis to Revelation with scroll memory to current book
                            val targetBookId = chosenBook?.id ?: currentBook?.id ?: 1
                            val initialBookIndex = remember(filteredBooks) {
                                val idx = filteredBooks.indexOfFirst { it.id == targetBookId }
                                (idx - 1).coerceAtLeast(0)
                            }
                            val bookListState = androidx.compose.foundation.lazy.rememberLazyListState(initialFirstVisibleItemIndex = initialBookIndex)

                            LaunchedEffect(currentStep, targetBookId) {
                                if (currentStep == SelectorStep.BOOK) {
                                    val idx = filteredBooks.indexOfFirst { it.id == targetBookId }
                                    if (idx >= 0) {
                                        bookListState.scrollToItem((idx - 1).coerceAtLeast(0))
                                    }
                                }
                            }

                            LazyColumn(
                                state = bookListState,
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(filteredBooks, key = { it.id }) { book ->
                                    val isCurrent = book.id == targetBookId
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(12.dp))
                                            .clickable {
                                                chosenBook = book
                                                currentStep = SelectorStep.CHAPTER
                                            }
                                            .then(
                                                if (isCurrent) Modifier.border(1.5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                                                else Modifier
                                            ),
                                        color = if (isCurrent) MaterialTheme.colorScheme.primaryContainer
                                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(36.dp)
                                                        .background(
                                                            if (isCurrent) MaterialTheme.colorScheme.primary
                                                            else MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                                                            CircleShape
                                                        ),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = "${book.orderIndex}",
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 12.sp,
                                                        color = if (isCurrent) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(12.dp))
                                                Column {
                                                    Text(
                                                        text = book.name,
                                                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color = if (isCurrent) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                                    )
                                                    Text(
                                                        text = "${book.category} • ${book.chaptersCount} cap.",
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                                    )
                                                }
                                            }
                                            Text(
                                                text = "›",
                                                fontSize = 22.sp,
                                                color = MaterialTheme.colorScheme.outline
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    SelectorStep.CHAPTER -> {
                        val book = chosenBook ?: return@AnimatedContent
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = "Elige un capítulo de ${book.name} (1 al ${book.chaptersCount}):",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(minSize = 56.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items((1..book.chaptersCount).toList()) { ch ->
                                    val isCurrentChapter = (book.id == currentBook?.id) && (ch == currentChapter)
                                    Box(
                                        modifier = Modifier
                                            .aspectRatio(1f)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(
                                                if (isCurrentChapter) MaterialTheme.colorScheme.primary
                                                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                                            )
                                            .border(
                                                width = 1.dp,
                                                color = if (isCurrentChapter) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .clickable {
                                                chosenChapter = ch
                                                currentStep = SelectorStep.VERSE
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = ch.toString(),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Center,
                                            color = if (isCurrentChapter) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                    }

                    SelectorStep.VERSE -> {
                        val book = chosenBook ?: return@AnimatedContent
                        val verseCount = com.example.data.bible.BibleVerseCounts.getVerseCount(
                            bookName = book.name,
                            bookOrder = book.orderIndex,
                            chapter = chosenChapter
                        )

                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = "Elige un versículo para comenzar la lectura:",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(minSize = 52.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items((1..verseCount).toList()) { vNum ->
                                    val isCurrentVerse = (book.id == currentBook?.id) && (chosenChapter == currentChapter) && (vNum == currentVerse)
                                    Box(
                                        modifier = Modifier
                                            .aspectRatio(1f)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(
                                                if (isCurrentVerse) MaterialTheme.colorScheme.primary
                                                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                            )
                                            .border(
                                                width = 1.dp,
                                                color = if (isCurrentVerse) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .clickable {
                                                onSelectBookChapterVerse(book, chosenChapter, vNum)
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = vNum.toString(),
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 15.sp,
                                            textAlign = TextAlign.Center,
                                            color = if (isCurrentVerse) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
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

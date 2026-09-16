package com.example.ui.reader.components

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
import com.example.data.model.BibleBookEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookChapterSelectorSheet(
    books: List<BibleBookEntity>,
    currentBook: BibleBookEntity?,
    currentChapter: Int,
    onSelectBookAndChapter: (BibleBookEntity, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedTestamentIndex by remember { mutableIntStateOf(if ((currentBook?.orderIndex ?: 1) > 39) 1 else 0) }
    var selectedBookForChapters by remember { mutableStateOf(currentBook ?: books.firstOrNull()) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredBooks = remember(books, selectedTestamentIndex, searchQuery) {
        val testament = if (selectedTestamentIndex == 0) "Antiguo Testamento" else "Nuevo Testamento"
        books.filter { it.testament == testament }.filter {
            if (searchQuery.isBlank()) true
            else it.name.contains(searchQuery, ignoreCase = true) || it.abbreviation.contains(searchQuery, ignoreCase = true)
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.88f)
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (selectedBookForChapters != null && selectedBookForChapters != currentBook) {
                    IconButton(onClick = { selectedBookForChapters = null }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver a libros")
                    }
                } else {
                    Spacer(modifier = Modifier.width(48.dp))
                }

                Text(
                    text = if (selectedBookForChapters != null) "${selectedBookForChapters?.name} — Capítulos" else "Seleccionar Libro",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (selectedBookForChapters == null) {
                // Testament Tabs
                TabRow(
                    selectedTabIndex = selectedTestamentIndex,
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    Tab(
                        selected = selectedTestamentIndex == 0,
                        onClick = { selectedTestamentIndex = 0 },
                        text = { Text("Antiguo Testamento (39)") }
                    )
                    Tab(
                        selected = selectedTestamentIndex == 1,
                        onClick = { selectedTestamentIndex = 1 },
                        text = { Text("Nuevo Testamento (27)") }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Buscar libro...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Books List
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredBooks, key = { it.id }) { book ->
                        val isCurrent = book.id == currentBook?.id
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { selectedBookForChapters = book },
                            color = if (isCurrent) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .background(
                                                if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                                                CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = book.abbreviation.take(3),
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
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }
            } else {
                // Chapters Grid
                val targetBook = selectedBookForChapters!!
                Text(
                    text = "Selecciona un capítulo de ${targetBook.name} (1 al ${targetBook.chaptersCount})",
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
                    items((1..targetBook.chaptersCount).toList()) { chapter ->
                        val isCurrentChapter = (targetBook.id == currentBook?.id) && (chapter == currentChapter)
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
                                    onSelectBookAndChapter(targetBook, chapter)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = chapter.toString(),
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
    }
}

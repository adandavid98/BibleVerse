package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.bible.BibleCatalog
import com.example.data.bible.BibleBook
import com.example.data.bible.BibleTextSanitizer
import com.example.data.bible.OfflineBibleDownloadManager
import com.example.data.bible.OfflineVerseDto
import com.example.data.local.BibleDatabase
import com.example.data.preferences.ReaderPreferencesRepository
import com.example.ui.reader.components.BibleVersionSelectorDialog
import com.example.ui.viewmodel.BibleUiState
import com.example.ui.viewmodel.BibleViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTab(
    viewModel: BibleViewModel,
    uiState: BibleUiState,
    onNavigateToReader: (bookId: Int, chapter: Int, verse: Int) -> Unit = { _, _, _ -> }
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val prefsRepo = remember { ReaderPreferencesRepository(context.applicationContext) }
    val readerPrefs by prefsRepo.readerPreferences.collectAsState(initial = null)
    val defaultVersion = readerPrefs?.bibleVersion ?: "RVR1960"

    var selectedVersionCode by remember { mutableStateOf<String?>(null) }
    val activeVersion = selectedVersionCode ?: defaultVersion
    val downloadStates by OfflineBibleDownloadManager.downloadStates.collectAsState()
    var isVersionPickerOpen by remember { mutableStateOf(false) }

    var searchScope by remember { mutableStateOf("ALL") } // "ALL", "OT", "NT", "BOOK"
    var selectedBook by remember { mutableStateOf<BibleBook?>(null) }
    var isBookPickerOpen by remember { mutableStateOf(false) }

    var localQuery by remember { mutableStateOf("") }
    var offlineResults by remember { mutableStateOf<List<OfflineVerseDto>>(emptyList()) }
    var isSearchingOffline by remember { mutableStateOf(false) }

    // Execute instant search via ViewModel
    LaunchedEffect(localQuery, searchScope, selectedBook, activeVersion) {
        val trimmed = localQuery.trim()
        if (trimmed.length < 2) {
            offlineResults = emptyList()
            isSearchingOffline = false
            return@LaunchedEffect
        }

        // Debounce 200ms while user is typing
        delay(200)

        isSearchingOffline = true
        val testamentParam = when (searchScope) {
            "OT" -> "OT"
            "NT" -> "NT"
            else -> null
        }
        val bookParam = if (searchScope == "BOOK") selectedBook?.order else null

        val results = viewModel.searchBibleOffline(
            query = trimmed,
            testament = testamentParam,
            bookId = bookParam,
            version = activeVersion
        )
        offlineResults = results
        isSearchingOffline = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Search bar
        OutlinedTextField(
            value = localQuery,
            onValueChange = { 
                localQuery = it
                viewModel.onSearchQueryChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("search_input_field"),
            placeholder = { Text("Buscar en toda la Biblia (gracia, fe, justicia...)") },
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = "Buscar")
            },
            trailingIcon = {
                if (localQuery.isNotEmpty()) {
                    IconButton(onClick = { 
                        localQuery = ""
                        viewModel.onSearchQueryChange("")
                    }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Limpiar")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Search Scope Filter Chips
        Text(
            text = "Ámbito de búsqueda bíblica (Offline):",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = true,
                onClick = { isVersionPickerOpen = true },
                label = { Text("Versión: $activeVersion") },
                leadingIcon = {
                    Icon(
                        Icons.Default.MenuBook,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )

            FilterChip(
                selected = searchScope == "ALL",
                onClick = { 
                    searchScope = "ALL"
                    selectedBook = null
                },
                label = { Text("Toda la Biblia") }
            )
            FilterChip(
                selected = searchScope == "OT",
                onClick = { 
                    searchScope = "OT"
                    selectedBook = null
                },
                label = { Text("Antiguo Testamento") }
            )
            FilterChip(
                selected = searchScope == "NT",
                onClick = { 
                    searchScope = "NT"
                    selectedBook = null
                },
                label = { Text("Nuevo Testamento") }
            )
            FilterChip(
                selected = searchScope == "BOOK",
                onClick = { 
                    searchScope = "BOOK"
                    isBookPickerOpen = true
                },
                label = { 
                    Text(if (selectedBook != null) "Libro: ${selectedBook!!.name}" else "Por Libro...") 
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isSearchingOffline) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(32.dp))
            }
        } else if (localQuery.trim().length < 2) {
            // Initial state with suggested keywords
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    modifier = Modifier.size(64.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Búsqueda Bíblica Offline",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Busca al instante en los 31,102 versículos de la Biblia sin conexión a internet.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Palabras clave sugeridas:",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))

                val quickKeywords = listOf("Gracia", "Justicia", "Fe", "Amor", "Paz", "Salvación", "Esperanza", "Perdón", "Sabiduría", "Verdad")
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(quickKeywords) { keyword ->
                        SuggestionChip(
                            onClick = { localQuery = keyword },
                            label = { Text(keyword) }
                        )
                    }
                }
            }
        } else if (offlineResults.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "No se encontraron versículos con «$localQuery» en el ámbito seleccionado",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
            }
        } else {
            // Results list
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${offlineResults.size} versículo(s) encontrados ($activeVersion)",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(offlineResults, key = { "${it.bookId}_${it.chapter}_${it.verseNumber}" }) { item ->
                    val bookName = BibleCatalog.books.getOrNull(item.bookId - 1)?.name ?: "Libro ${item.bookId}"
                    val testament = if (item.bookId <= 39) "Antiguo Testamento" else "Nuevo Testamento"

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "$bookName ${item.chapter}:${item.verseNumber}",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = testament,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            // Highlighted query in verse text
                            Text(
                                text = buildHighlightedString(item.text, localQuery, MaterialTheme.colorScheme.primary),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    IconButton(
                                        onClick = {
                                            val quote = "«${item.text}» - $bookName ${item.chapter}:${item.verseNumber} ($activeVersion)"
                                            val clip = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                            clip.setPrimaryClip(ClipData.newPlainText("Versículo", quote))
                                            Toast.makeText(context, "Copiado al portapapeles", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.ContentCopy,
                                            contentDescription = "Copiar",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }

                                    IconButton(
                                        onClick = {
                                            viewModel.addCustomVerse(
                                                book = bookName,
                                                chapterVerse = "${item.chapter}:${item.verseNumber}",
                                                testament = testament,
                                                text = item.text,
                                                context = "Versículo guardado desde Búsqueda Bíblica Offline.",
                                                topic = "Búsqueda Bíblica",
                                                notes = "",
                                                bibleVersion = activeVersion
                                            )
                                            Toast.makeText(context, "Guardado en Favoritos", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Favorite,
                                            contentDescription = "Guardar",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                Button(
                                    onClick = {
                                        onNavigateToReader(item.bookId, item.chapter, item.verseNumber)
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text("Leer en contexto", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Book Picker Dialog
    if (isBookPickerOpen) {
        AlertDialog(
            onDismissRequest = { isBookPickerOpen = false },
            title = { Text("Seleccionar Libro para Filtrar", fontWeight = FontWeight.Bold) },
            text = {
                var bookSearch by remember { mutableStateOf("") }
                val filteredBooks = remember(bookSearch) {
                    if (bookSearch.isBlank()) BibleCatalog.books
                    else BibleCatalog.books.filter { it.name.contains(bookSearch, ignoreCase = true) }
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
                                        searchScope = "ALL"
                                        isBookPickerOpen = false
                                    }
                                    .padding(vertical = 10.dp, horizontal = 8.dp)
                            ) {
                                Text("Todos los libros (Sin filtro)", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
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
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(book.name, fontWeight = FontWeight.Medium)
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
            },
            confirmButton = {
                TextButton(onClick = { isBookPickerOpen = false }) {
                    Text("Cerrar")
                }
            }
        )
    }

    if (isVersionPickerOpen) {
        BibleVersionSelectorDialog(
            currentVersion = activeVersion,
            downloadStates = downloadStates,
            onSelectVersion = { chosen ->
                selectedVersionCode = chosen
                isVersionPickerOpen = false
            },
            onDismiss = { isVersionPickerOpen = false },
            onDownloadVersion = { vCode ->
                scope.launch {
                    val dao = BibleDatabase.getDatabase(context.applicationContext).bibleReaderDao()
                    OfflineBibleDownloadManager.downloadVersion(dao, vCode)
                }
            }
        )
    }
}

private fun buildHighlightedString(
    fullText: String,
    query: String,
    highlightColor: Color
): androidx.compose.ui.text.AnnotatedString {
    return buildAnnotatedString {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) {
            append(fullText)
            return@buildAnnotatedString
        }

        var currentIndex = 0
        val cleanFull = BibleTextSanitizer.removeAccents(fullText).lowercase()
        val cleanQuery = BibleTextSanitizer.removeAccents(trimmed).lowercase()

        while (currentIndex < fullText.length) {
            val matchIndex = cleanFull.indexOf(cleanQuery, currentIndex)
            if (matchIndex == -1) {
                append(fullText.substring(currentIndex))
                break
            }

            if (matchIndex > currentIndex) {
                append(fullText.substring(currentIndex, matchIndex))
            }

            val endIndex = (matchIndex + cleanQuery.length).coerceAtMost(fullText.length)
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = highlightColor
                )
            ) {
                append(fullText.substring(matchIndex, endIndex))
            }

            currentIndex = endIndex
        }
    }
}

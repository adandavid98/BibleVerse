package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.bible.BibleBook
import com.example.data.bible.BibleCatalog
import com.example.data.bible.BibleContextEngine
import com.example.data.bible.BibleVersion
import com.example.data.bible.ContextGenerationResult
import com.example.data.bible.ContextSource
import com.example.data.bible.GeminiVerseContextService
import kotlinx.coroutines.launch
import com.example.data.initial.InitialVersesData

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddVerseDialog(
    onDismiss: () -> Unit,
    onAddVerse: (
        book: String,
        chapterVerse: String,
        testament: String,
        text: String,
        context: String,
        topic: String,
        notes: String,
        bibleVersion: String
    ) -> Unit,
    onGenerateContext: ((
        book: String,
        chapter: Int,
        verse: String,
        text: String,
        version: String,
        forceLocal: Boolean,
        callback: (String, Boolean) -> Unit
    ) -> Unit)? = null
) {
    val coroutineScope = rememberCoroutineScope()

    // Mode selection: 0 = Selector de Libros y Versiones, 1 = Entrada Manual
    var selectedTab by remember { mutableIntStateOf(0) }

    // Selected Bible Translation Version
    var selectedVersion by remember { mutableStateOf(BibleCatalog.versions.first()) }
    var showVersionModal by remember { mutableStateOf(false) }

    // === STATE FOR OPTION 1: BIBLE BOOK SELECTOR ===
    var selectedTestamentFilter by remember { mutableStateOf("Todos") } // "Todos", "Antiguo Testamento", "Nuevo Testamento"
    var bookSearchQuery by remember { mutableStateOf("") }
    var selectedBook by remember { mutableStateOf(BibleCatalog.books.first()) }
    var selectedChapter by remember { mutableIntStateOf(1) }
    var verseInput by remember { mutableStateOf("1") }
    var bibleText by remember { mutableStateOf("") }
    var bibleContext by remember { mutableStateOf("") }
    var bibleTopic by remember { mutableStateOf("General") }
    var bibleNotes by remember { mutableStateOf("") }
    var isGeneratingAiTab0 by remember { mutableStateOf(false) }
    var contextSourceBadgeTab0 by remember { mutableStateOf("Catálogo Local") }

    // === STATE FOR OPTION 2: MANUAL ENTRY ===
    var manualBook by remember { mutableStateOf("") }
    var manualChapterVerse by remember { mutableStateOf("") }
    var manualTestament by remember { mutableStateOf("Nuevo Testamento") }
    var manualTopic by remember { mutableStateOf("") }
    var manualText by remember { mutableStateOf("") }
    var manualContext by remember { mutableStateOf("") }
    var manualNotes by remember { mutableStateOf("") }
    var isGeneratingAiTab1 by remember { mutableStateOf(false) }
    var contextSourceBadgeTab1 by remember { mutableStateOf<String?>(null) }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Quick common topics
    val suggestedTopics = listOf("Fe", "Amor", "Esperanza", "Fortaleza", "Paz", "Salvación", "Sabiduría", "Oración", "Promesa", "Gracia")

    // Intelligent hybrid filling for Book & Chapter selection:
    // 1. Checks if exact match exists in InitialVersesData
    // 2. Otherwise computes grounded canonical context from BibleContextEngine
    LaunchedEffect(selectedBook, selectedChapter, verseInput, selectedVersion) {
        val refQuery1 = "${selectedBook.name} $selectedChapter:$verseInput".trim()
        val refQuery2 = "${selectedBook.name} $selectedChapter : $verseInput".trim()
        val match = InitialVersesData.verses.firstOrNull {
            it.reference.equals(refQuery1, ignoreCase = true) ||
            it.reference.replace(" ", "").equals(refQuery2.replace(" ", ""), ignoreCase = true)
        }
        if (match != null) {
            if (bibleText.isBlank() || bibleText.startsWith("«")) {
                bibleText = match.text.removeSurrounding("«", "»")
            }
            bibleContext = match.context
            contextSourceBadgeTab0 = "Catálogo Exacto"
            if (bibleTopic == "General" || bibleTopic.isBlank()) {
                bibleTopic = match.topic
            }
        } else {
            // Intelligent canonical theological context for all 66 books and chapters
            if (bibleContext.isBlank() || contextSourceBadgeTab0 != "✨ IA (Gemini)") {
                bibleContext = BibleContextEngine.getLocalContext(selectedBook.name, selectedChapter, verseInput, bibleText)
                contextSourceBadgeTab0 = "Catálogo Local"
            }
        }
    }

    if (showVersionModal) {
        BibleVersionSelectionDialog(
            currentVersionCode = selectedVersion.code,
            onVersionSelected = { newVersion ->
                selectedVersion = newVersion
            },
            onDismiss = { showVersionModal = false }
        )
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.94f)
                .imePadding()
                .clip(RoundedCornerShape(24.dp))
                .testTag("dialog_add_verse"),
            color = MaterialTheme.colorScheme.background,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Filled.MenuBook,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "Añadir Versículo",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "Explora libros o escribe manualmente",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("btn_close_add_verse")
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = "Cerrar")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // TABS: 2 OPCIONES (Libros de la Biblia vs Entrada Manual)
                TabRow(
                    selectedTabIndex = selectedTab,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0; errorMessage = null },
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Filled.AutoStories, contentDescription = null, modifier = Modifier.size(16.dp))
                                Text(
                                    text = "Libros de la Biblia",
                                    fontSize = 13.sp,
                                    fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        },
                        modifier = Modifier.testTag("tab_bible_selector")
                    )

                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1; errorMessage = null },
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Filled.EditNote, contentDescription = null, modifier = Modifier.size(16.dp))
                                Text(
                                    text = "Agregar Manual",
                                    fontSize = 13.sp,
                                    fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        },
                        modifier = Modifier.testTag("tab_manual_entry")
                    )
                }

                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )

                // BODY CONTENT
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // SELECCIONADOR DE VERSIÓN (Aplica a ambas opciones)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { showVersionModal = true }
                            .testTag("card_select_bible_version"),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Versión de Traducción:",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = MaterialTheme.colorScheme.primary
                                    ) {
                                        Text(
                                            text = selectedVersion.code,
                                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Text(
                                        text = selectedVersion.name,
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }

                            OutlinedButton(
                                onClick = { showVersionModal = true },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("btn_change_version")
                            ) {
                                Text("Cambiar", fontSize = 12.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(Icons.Filled.ArrowDropDown, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    }

                    if (selectedTab == 0) {
                        // ==========================================
                        // OPCIÓN 1: SELECTOR DE LIBROS DE LA BIBLIA
                        // ==========================================

                        // Filtro de Testamento (Todos - Antiguo - Nuevo)
                        Text(
                            text = "Filtrar por Testamento:",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                selected = selectedTestamentFilter == "Todos",
                                onClick = { selectedTestamentFilter = "Todos" },
                                label = { Text("Todos (66)") },
                                modifier = Modifier.testTag("chip_filter_all")
                            )
                            FilterChip(
                                selected = selectedTestamentFilter == "Antiguo Testamento",
                                onClick = { selectedTestamentFilter = "Antiguo Testamento" },
                                label = { Text("Antiguo Testamento (39)") },
                                modifier = Modifier.testTag("chip_filter_ot")
                            )
                            FilterChip(
                                selected = selectedTestamentFilter == "Nuevo Testamento",
                                onClick = { selectedTestamentFilter = "Nuevo Testamento" },
                                label = { Text("Nuevo Testamento (27)") },
                                modifier = Modifier.testTag("chip_filter_nt")
                            )
                        }

                        // Buscador de libros
                        OutlinedTextField(
                            value = bookSearchQuery,
                            onValueChange = { bookSearchQuery = it },
                            placeholder = { Text("Buscar libro (ej. Génesis, Salmos, Mateo, Romanos...)") },
                            leadingIcon = {
                                Icon(Icons.Filled.Search, contentDescription = null, modifier = Modifier.size(18.dp))
                            },
                            trailingIcon = {
                                if (bookSearchQuery.isNotBlank()) {
                                    IconButton(onClick = { bookSearchQuery = "" }) {
                                        Icon(Icons.Filled.Close, contentDescription = "Limpiar", modifier = Modifier.size(16.dp))
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_search_books")
                        )

                        // Lista filtrada de libros de la Biblia
                        val filteredBooks = remember(selectedTestamentFilter, bookSearchQuery) {
                            BibleCatalog.books.filter { book ->
                                val matchesTestament = when (selectedTestamentFilter) {
                                    "Antiguo Testamento" -> book.testament == "Antiguo Testamento"
                                    "Nuevo Testamento" -> book.testament == "Nuevo Testamento"
                                    else -> true
                                }
                                val matchesQuery = bookSearchQuery.isBlank() ||
                                        book.name.contains(bookSearchQuery, ignoreCase = true) ||
                                        book.abbreviation.contains(bookSearchQuery, ignoreCase = true)
                                matchesTestament && matchesQuery
                            }
                        }

                        Text(
                            text = "Selecciona un libro (${filteredBooks.size} disponibles):",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // Grid de chips de libros
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 160.dp)
                                .verticalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            filteredBooks.forEach { book ->
                                val isSelected = book.name == selectedBook.name
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        selectedBook = book
                                        if (selectedChapter > book.chaptersCount) {
                                            selectedChapter = 1
                                        }
                                    },
                                    label = {
                                        Text("${book.name} (${book.chaptersCount})", fontSize = 12.sp)
                                    },
                                    leadingIcon = if (isSelected) {
                                        { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                                    } else null,
                                    modifier = Modifier.testTag("book_chip_${book.name}")
                                )
                            }
                        }

                        // Selector de Capítulo y Versículo
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(
                                    text = "Libro: ${selectedBook.name} (${selectedBook.testament})",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Selector de Capítulo
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Capítulo (1..${selectedBook.chaptersCount}):",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        OutlinedTextField(
                                            value = selectedChapter.toString(),
                                            onValueChange = { input ->
                                                val num = input.filter { it.isDigit() }.toIntOrNull()
                                                if (num != null && num in 1..selectedBook.chaptersCount) {
                                                    selectedChapter = num
                                                } else if (input.isEmpty()) {
                                                    selectedChapter = 1
                                                }
                                            },
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                            singleLine = true,
                                            shape = RoundedCornerShape(10.dp),
                                            modifier = Modifier.fillMaxWidth().testTag("input_chapter_number")
                                        )
                                    }

                                    // Selector de Versículo
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Versículo(s):",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        OutlinedTextField(
                                            value = verseInput,
                                            onValueChange = { verseInput = it },
                                            placeholder = { Text("ej. 1 ó 16-17") },
                                            singleLine = true,
                                            shape = RoundedCornerShape(10.dp),
                                            modifier = Modifier.fillMaxWidth().testTag("input_verse_number")
                                        )
                                    }
                                }

                                // Quick chapter buttons row (first 10 or current)
                                Text(
                                    text = "Capítulos rápidos:",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Row(
                                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    for (c in 1..minOf(selectedBook.chaptersCount, 25)) {
                                        FilterChip(
                                            selected = selectedChapter == c,
                                            onClick = { selectedChapter = c },
                                            label = { Text("Cap. $c", fontSize = 11.sp) }
                                        )
                                    }
                                }
                            }
                        }

                        // Vista previa de la referencia
                        val generatedRef = "${selectedBook.name} $selectedChapter:$verseInput".trim()
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = "Referencia a registrar:",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                    Text(
                                        text = "$generatedRef (${selectedVersion.code})",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }

                        // Texto Bíblico
                        OutlinedTextField(
                            value = bibleText,
                            onValueChange = { bibleText = it },
                            label = { Text("Texto del Versículo *") },
                            placeholder = { Text("Escribe o pega el texto bíblico según la versión ${selectedVersion.shortName}...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_bible_text"),
                            minLines = 3,
                            maxLines = 6,
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Panel de Contexto Inteligente (Híbrido: Local + IA)
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.AutoAwesome,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = "Contexto Inteligente",
                                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (contextSourceBadgeTab0.contains("IA")) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.secondaryContainer
                                        }
                                    ) {
                                        Text(
                                            text = contextSourceBadgeTab0,
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = if (contextSourceBadgeTab0.contains("IA")) {
                                                MaterialTheme.colorScheme.onPrimary
                                            } else {
                                                MaterialTheme.colorScheme.onSecondaryContainer
                                            },
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = {
                                            isGeneratingAiTab0 = true
                                            if (onGenerateContext != null) {
                                                onGenerateContext(
                                                    selectedBook.name,
                                                    selectedChapter,
                                                    verseInput,
                                                    bibleText,
                                                    selectedVersion.code,
                                                    false
                                                ) { res, isAi ->
                                                    bibleContext = res
                                                    contextSourceBadgeTab0 = if (isAi) "✨ IA (Gemini)" else "Catálogo Local"
                                                    isGeneratingAiTab0 = false
                                                }
                                            } else {
                                                coroutineScope.launch {
                                                    val res = GeminiVerseContextService.generateContext(
                                                        book = selectedBook.name,
                                                        chapter = selectedChapter,
                                                        verse = verseInput,
                                                        verseText = bibleText,
                                                        bibleVersion = selectedVersion.code
                                                    )
                                                    when (res) {
                                                        is ContextGenerationResult.Success -> {
                                                            bibleContext = res.contextText
                                                            contextSourceBadgeTab0 = if (res.source == ContextSource.AI_GEMINI) "✨ IA (Gemini)" else "Catálogo Local"
                                                        }
                                                        is ContextGenerationResult.Error -> {
                                                            bibleContext = res.fallbackContext
                                                            contextSourceBadgeTab0 = "Catálogo Local"
                                                        }
                                                    }
                                                    isGeneratingAiTab0 = false
                                                }
                                            }
                                        },
                                        enabled = !isGeneratingAiTab0,
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier
                                            .weight(1.3f)
                                            .testTag("btn_enrich_ai_tab0")
                                    ) {
                                        if (isGeneratingAiTab0) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(14.dp),
                                                strokeWidth = 2.dp,
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("Generando...", fontSize = 11.sp)
                                        } else {
                                            Icon(Icons.Filled.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Llenar con IA", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            bibleContext = BibleContextEngine.getLocalContext(
                                                selectedBook.name,
                                                selectedChapter,
                                                verseInput,
                                                bibleText
                                            )
                                            contextSourceBadgeTab0 = "Catálogo Local"
                                        },
                                        enabled = !isGeneratingAiTab0,
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier
                                            .weight(1f)
                                            .testTag("btn_restore_local_tab0")
                                    ) {
                                        Icon(Icons.Filled.MenuBook, contentDescription = null, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Local", fontSize = 11.sp)
                                    }
                                }
                            }
                        }

                        // Contexto Teológico y Moral
                        OutlinedTextField(
                            value = bibleContext,
                            onValueChange = { 
                                bibleContext = it
                                contextSourceBadgeTab0 = "Personalizado"
                            },
                            label = { Text("Contexto Teológico y Moral *") },
                            placeholder = { Text("Explica el significado teológico, propósito histórico o aplicación moral...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_bible_context"),
                            minLines = 3,
                            maxLines = 6,
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Categoría / Tema
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Tema / Categoría:",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Row(
                                modifier = Modifier.horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                suggestedTopics.forEach { t ->
                                    FilterChip(
                                        selected = bibleTopic.equals(t, ignoreCase = true),
                                        onClick = { bibleTopic = t },
                                        label = { Text(t, fontSize = 11.sp) }
                                    )
                                }
                            }
                            OutlinedTextField(
                                value = bibleTopic,
                                onValueChange = { bibleTopic = it },
                                placeholder = { Text("Otro tema personalizado...") },
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth().testTag("input_bible_topic")
                            )
                        }

                        // Notas Personales
                        OutlinedTextField(
                            value = bibleNotes,
                            onValueChange = { bibleNotes = it },
                            label = { Text("Notas Personales (Opcional)") },
                            placeholder = { Text("Tus notas o reflexiones devocionales...") },
                            modifier = Modifier.fillMaxWidth().testTag("input_bible_notes"),
                            minLines = 2,
                            maxLines = 4,
                            shape = RoundedCornerShape(12.dp)
                        )

                    } else {
                        // ==========================================
                        // OPCIÓN 2: AGREGAR MANUAL (COMO YA ESTÁ)
                        // ==========================================

                        // Testamento Chips
                        Text(
                            text = "Testamento:",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FilterChip(
                                selected = manualTestament == "Antiguo Testamento",
                                onClick = { manualTestament = "Antiguo Testamento" },
                                label = { Text("Antiguo Testamento") },
                                modifier = Modifier.testTag("chip_antiguo")
                            )
                            FilterChip(
                                selected = manualTestament == "Nuevo Testamento",
                                onClick = { manualTestament = "Nuevo Testamento" },
                                label = { Text("Nuevo Testamento") },
                                modifier = Modifier.testTag("chip_nuevo")
                            )
                        }

                        // Libro y Capítulo:Versículo
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedTextField(
                                value = manualBook,
                                onValueChange = { manualBook = it },
                                label = { Text("Libro (ej. Salmos)") },
                                modifier = Modifier
                                    .weight(1.3f)
                                    .testTag("input_book"),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp)
                            )

                            OutlinedTextField(
                                value = manualChapterVerse,
                                onValueChange = { manualChapterVerse = it },
                                label = { Text("Capítulo:Versículo") },
                                placeholder = { Text("ej. 23:1") },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("input_chapter_verse"),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp)
                            )
                        }

                        // Tema o Categoría
                        OutlinedTextField(
                            value = manualTopic,
                            onValueChange = { manualTopic = it },
                            label = { Text("Tema o Categoría (ej. Confianza, Promesa)") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_topic"),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Texto Bíblico
                        OutlinedTextField(
                            value = manualText,
                            onValueChange = { manualText = it },
                            label = { Text("Texto Bíblico *") },
                            placeholder = { Text("Escribe el texto del versículo...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_text"),
                            minLines = 3,
                            maxLines = 6,
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Panel de Contexto Inteligente (Manual)
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.AutoAwesome,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = "Llenado Inteligente de Contexto",
                                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }

                                    if (contextSourceBadgeTab1 != null) {
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = if (contextSourceBadgeTab1!!.contains("IA")) {
                                                MaterialTheme.colorScheme.primary
                                            } else {
                                                MaterialTheme.colorScheme.secondaryContainer
                                            }
                                        ) {
                                            Text(
                                                text = contextSourceBadgeTab1!!,
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                color = if (contextSourceBadgeTab1!!.contains("IA")) {
                                                    MaterialTheme.colorScheme.onPrimary
                                                } else {
                                                    MaterialTheme.colorScheme.onSecondaryContainer
                                                },
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = {
                                            if (manualBook.isBlank()) {
                                                errorMessage = "Indica al menos el nombre del libro (ej. Salmos)"
                                                return@Button
                                            }
                                            isGeneratingAiTab1 = true
                                            val cvParts = manualChapterVerse.split(":")
                                            val ch = cvParts.getOrNull(0)?.filter { it.isDigit() }?.toIntOrNull() ?: 1
                                            val vs = cvParts.getOrNull(1)?.ifBlank { "1" } ?: "1"

                                            if (onGenerateContext != null) {
                                                onGenerateContext(
                                                    manualBook,
                                                    ch,
                                                    vs,
                                                    manualText,
                                                    selectedVersion.code,
                                                    false
                                                ) { res, isAi ->
                                                    manualContext = res
                                                    contextSourceBadgeTab1 = if (isAi) "✨ IA (Gemini)" else "Catálogo Local"
                                                    isGeneratingAiTab1 = false
                                                }
                                            } else {
                                                coroutineScope.launch {
                                                    val res = GeminiVerseContextService.generateContext(
                                                        book = manualBook,
                                                        chapter = ch,
                                                        verse = vs,
                                                        verseText = manualText,
                                                        bibleVersion = selectedVersion.code
                                                    )
                                                    when (res) {
                                                        is ContextGenerationResult.Success -> {
                                                            manualContext = res.contextText
                                                            contextSourceBadgeTab1 = if (res.source == ContextSource.AI_GEMINI) "✨ IA (Gemini)" else "Catálogo Local"
                                                        }
                                                        is ContextGenerationResult.Error -> {
                                                            manualContext = res.fallbackContext
                                                            contextSourceBadgeTab1 = "Catálogo Local"
                                                        }
                                                    }
                                                    isGeneratingAiTab1 = false
                                                }
                                            }
                                        },
                                        enabled = !isGeneratingAiTab1,
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier
                                            .weight(1.3f)
                                            .testTag("btn_enrich_ai_tab1")
                                    ) {
                                        if (isGeneratingAiTab1) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(14.dp),
                                                strokeWidth = 2.dp,
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("Generando...", fontSize = 11.sp)
                                        } else {
                                            Icon(Icons.Filled.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Llenar con IA", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            if (manualBook.isBlank()) {
                                                errorMessage = "Indica al menos el nombre del libro (ej. Salmos)"
                                                return@OutlinedButton
                                            }
                                            val cvParts = manualChapterVerse.split(":")
                                            val ch = cvParts.getOrNull(0)?.filter { it.isDigit() }?.toIntOrNull() ?: 1
                                            val vs = cvParts.getOrNull(1)?.ifBlank { "1" } ?: "1"
                                            manualContext = BibleContextEngine.getLocalContext(manualBook, ch, vs, manualText)
                                            contextSourceBadgeTab1 = "Catálogo Local"
                                        },
                                        enabled = !isGeneratingAiTab1,
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier
                                            .weight(1f)
                                            .testTag("btn_restore_local_tab1")
                                    ) {
                                        Icon(Icons.Filled.MenuBook, contentDescription = null, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Local", fontSize = 11.sp)
                                    }
                                }
                            }
                        }

                        // Contexto Teológico y Moral
                        OutlinedTextField(
                            value = manualContext,
                            onValueChange = { 
                                manualContext = it
                                contextSourceBadgeTab1 = "Personalizado"
                            },
                            label = { Text("Contexto Teológico y Moral *") },
                            placeholder = { Text("Explica el significado histórico, moral y teológico del pasaje...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_context"),
                            minLines = 4,
                            maxLines = 8,
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Notas Personales
                        OutlinedTextField(
                            value = manualNotes,
                            onValueChange = { manualNotes = it },
                            label = { Text("Notas Personales (Opcional)") },
                            placeholder = { Text("Tus reflexiones personales para este versículo...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_initial_notes"),
                            minLines = 2,
                            maxLines = 4,
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    if (errorMessage != null) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.errorContainer,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = errorMessage ?: "",
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }

                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )

                // Actions Bottom Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = {
                            if (selectedTab == 0) {
                                // Validate Option 1: Bible Selector
                                if (selectedBook.name.isBlank() || verseInput.isBlank() || bibleText.isBlank() || bibleContext.isBlank()) {
                                    errorMessage = "Por favor completa el texto bíblico y su contexto teológico."
                                } else {
                                    val chv = "$selectedChapter:$verseInput".trim()
                                    onAddVerse(
                                        selectedBook.name.trim(),
                                        chv,
                                        selectedBook.testament,
                                        bibleText.trim(),
                                        bibleContext.trim(),
                                        bibleTopic.trim().ifBlank { "General" },
                                        bibleNotes.trim(),
                                        selectedVersion.code
                                    )
                                }
                            } else {
                                // Validate Option 2: Manual
                                if (manualBook.isBlank() || manualChapterVerse.isBlank() || manualText.isBlank() || manualContext.isBlank()) {
                                    errorMessage = "Por favor completa el libro, versículo, texto y contexto."
                                } else {
                                    onAddVerse(
                                        manualBook.trim(),
                                        manualChapterVerse.trim(),
                                        manualTestament,
                                        manualText.trim(),
                                        manualContext.trim(),
                                        manualTopic.trim(),
                                        manualNotes.trim(),
                                        selectedVersion.code
                                    )
                                }
                            }
                        },
                        modifier = Modifier.testTag("btn_confirm_add_verse"),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Filled.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Guardar Versículo")
                    }
                }
            }
        }
    }
}

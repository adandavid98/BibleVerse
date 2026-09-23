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
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.data.bible.OfflineBibleManager
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import com.example.data.bible.ContextGenerationResult
import com.example.data.bible.ContextSource
import com.example.data.bible.GeminiVerseContextService
import android.content.Context
import com.example.data.bible.BollsBibleApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    val context = androidx.compose.ui.platform.LocalContext.current
    var showReferencePickerWindow by remember { mutableStateOf(false) }

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
    var isFetchingVerseText by remember { mutableStateOf(false) }

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
    // Resolves genuine biblical text across Offline SQLite, Room Cache, Network (Bolls API) & Canonical fallbacks
    LaunchedEffect(selectedBook, selectedChapter, verseInput, selectedVersion) {
        val vNum = verseInput.toIntOrNull() ?: 1
        isFetchingVerseText = true
        val resolvedText = resolveVerseText(
            context = context,
            bookOrder = selectedBook.order,
            chapter = selectedChapter,
            verse = vNum,
            versionCode = selectedVersion.code,
            bookName = selectedBook.name
        )
        if (resolvedText.isNotBlank()) {
            bibleText = resolvedText
        }
        isFetchingVerseText = false

        if (bibleContext.isBlank() || contextSourceBadgeTab0 != "✨ IA (Gemini)") {
            bibleContext = BibleContextEngine.getLocalContext(selectedBook.name, selectedChapter, verseInput, bibleText)
            contextSourceBadgeTab0 = "Catálogo Local"
        }
    }

    if (showReferencePickerWindow) {
        BibleReferenceWindowPicker(
            initialBook = selectedBook,
            initialChapter = selectedChapter,
            initialVerse = verseInput.toIntOrNull() ?: 1,
            onReferenceSelected = { book, chapter, verse ->
                selectedBook = book
                selectedChapter = chapter
                verseInput = verse.toString()
                bibleText = "" // Clear immediately so outdated verse is not shown
                showReferencePickerWindow = false
                coroutineScope.launch {
                    isFetchingVerseText = true
                    val resolved = resolveVerseText(
                        context = context,
                        bookOrder = book.order,
                        chapter = chapter,
                        verse = verse,
                        versionCode = selectedVersion.code,
                        bookName = book.name
                    )
                    if (resolved.isNotBlank()) {
                        bibleText = resolved
                    }
                    isFetchingVerseText = false
                    val ctx = BibleContextEngine.getLocalContext(book.name, chapter, verse.toString(), bibleText)
                    if (ctx.isNotBlank()) {
                        bibleContext = ctx
                    }
                }
            },
            onDismiss = { showReferencePickerWindow = false }
        )
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
                        // OPCIÓN 1: SELECTOR PRECISO (VENTANA 3 PASOS)
                        // ==========================================

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                            ),
                            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f))
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "PASO 1: SELECCIONAR REFERENCIA",
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = "${selectedBook.name} $selectedChapter:$verseInput",
                                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "${selectedBook.testament} • ${selectedBook.category} • ${selectedVersion.code}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(44.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                Icons.Filled.MenuBook,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onPrimary,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                }

                                Button(
                                    onClick = { showReferencePickerWindow = true },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("btn_open_reference_picker"),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Filled.AutoStories, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Elegir Libro, Capítulo y Versículo")
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
                            trailingIcon = {
                                if (isFetchingVerseText) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(18.dp),
                                        strokeWidth = 2.dp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            },
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


private enum class PickerStep {
    BOOK,
    CHAPTER,
    VERSE
}

@Composable
fun BibleReferenceWindowPicker(
    initialBook: BibleBook,
    initialChapter: Int,
    initialVerse: Int,
    onReferenceSelected: (BibleBook, Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var step by remember { mutableStateOf(PickerStep.BOOK) }
    var pickedBook by remember { mutableStateOf(initialBook) }
    var pickedChapter by remember { mutableIntStateOf(initialChapter) }
    var testamentFilter by remember { mutableIntStateOf(if (initialBook.order > 39) 2 else 0) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredBooks = remember(testamentFilter, searchQuery) {
        val list = when (testamentFilter) {
            1 -> BibleCatalog.books.filter { it.order <= 39 }
            2 -> BibleCatalog.books.filter { it.order > 39 }
            else -> BibleCatalog.books
        }
        if (searchQuery.isBlank()) list
        else list.filter { it.name.contains(searchQuery, ignoreCase = true) || it.abbreviation.contains(searchQuery, ignoreCase = true) }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.92f)
                .clip(RoundedCornerShape(24.dp)),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp)
            ) {
                // Header with navigation and title
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (step != PickerStep.BOOK) {
                        IconButton(onClick = {
                            step = if (step == PickerStep.VERSE) PickerStep.CHAPTER else PickerStep.BOOK
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    } else {
                        Spacer(modifier = Modifier.width(48.dp))
                    }

                    Text(
                        text = when (step) {
                            PickerStep.BOOK -> "1. Selecciona Libro"
                            PickerStep.CHAPTER -> "${pickedBook.name} — 2. Capítulo"
                            PickerStep.VERSE -> "${pickedBook.name} $pickedChapter — 3. Versículo"
                        },
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Filled.Close, contentDescription = "Cerrar")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(10.dp))

                when (step) {
                    PickerStep.BOOK -> {
                        // 3 Filter Tabs
                        TabRow(
                            selectedTabIndex = testamentFilter,
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.primary
                        ) {
                            Tab(
                                selected = testamentFilter == 0,
                                onClick = { testamentFilter = 0 },
                                text = { Text("Todo (66)") }
                            )
                            Tab(
                                selected = testamentFilter == 1,
                                onClick = { testamentFilter = 1 },
                                text = { Text("A.T. (39)") }
                            )
                            Tab(
                                selected = testamentFilter == 2,
                                onClick = { testamentFilter = 2 },
                                text = { Text("N.T. (27)") }
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Buscar libro...") },
                            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        val initialIdx = remember(filteredBooks) {
                            val idx = filteredBooks.indexOfFirst { it.name == initialBook.name }
                            (idx - 1).coerceAtLeast(0)
                        }
                        val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIdx)

                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(filteredBooks, key = { it.order }) { book ->
                                val isSelected = book.name == pickedBook.name
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable {
                                            pickedBook = book
                                            step = PickerStep.CHAPTER
                                        }
                                        .then(
                                            if (isSelected) Modifier.border(1.5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                                            else Modifier
                                        ),
                                    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Surface(
                                                shape = CircleShape,
                                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                                                modifier = Modifier.size(32.dp)
                                            ) {
                                                Box(contentAlignment = Alignment.Center) {
                                                    Text(
                                                        text = "${book.order}",
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 12.sp,
                                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Column {
                                                Text(
                                                    text = book.name,
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                    style = MaterialTheme.typography.bodyLarge,
                                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                                )
                                                Text(
                                                    text = "${book.category} • ${book.chaptersCount} capítulos",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        }
                                        Text("›", fontSize = 22.sp, color = MaterialTheme.colorScheme.outline)
                                    }
                                }
                            }
                        }
                    }

                    PickerStep.CHAPTER -> {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 64.dp),
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            items((1..pickedBook.chaptersCount).toList()) { ch ->
                                val isSelected = ch == pickedChapter
                                Surface(
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable {
                                            pickedChapter = ch
                                            step = PickerStep.VERSE
                                        },
                                    color = if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "$ch",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                    }

                    PickerStep.VERSE -> {
                        val maxVerses = com.example.data.bible.BibleVerseCounts.getVerseCount(
                            bookName = pickedBook.name,
                            bookOrder = pickedBook.order,
                            chapter = pickedChapter
                        )
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 60.dp),
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            items((1..maxVerses).toList()) { v ->
                                val isSelected = v == initialVerse
                                Surface(
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable {
                                            onReferenceSelected(pickedBook, pickedChapter, v)
                                        },
                                    color = if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "$v",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
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

/**
 * Resolves biblical text across offline SQLite, Room cache, network (Bolls API) and canonical data.
 */
private suspend fun resolveVerseText(
    context: Context,
    bookOrder: Int,
    chapter: Int,
    verse: Int,
    versionCode: String,
    bookName: String
): String = withContext(Dispatchers.IO) {
    val normVersion = when (versionCode.uppercase().trim()) {
        "RV1960", "REINA-VALERA 1960" -> "RVR1960"
        else -> versionCode.uppercase().trim()
    }

    // 1. Try OfflineBibleManager for RVR1960 (local SQLite database - instant sub-millisecond)
    if (normVersion == "RVR1960") {
        try {
            val offlineVerses = OfflineBibleManager.getVerses(context, bookOrder, chapter)
            val match = offlineVerses.firstOrNull { it.verseNumber == verse }
            if (match != null && match.text.isNotBlank()) {
                return@withContext match.text.trim()
            }
        } catch (_: Exception) {}
    }

    // 2. Try fetching from network (Bolls API) if online
    try {
        val netVerses = BollsBibleApiService.fetchChapter(normVersion, bookOrder, chapter)
        if (!netVerses.isNullOrEmpty()) {
            val netMatch = netVerses.firstOrNull { it.verseNumber == verse }
            if (netMatch != null && netMatch.text.isNotBlank()) {
                return@withContext netMatch.text.trim()
            }
        }
    } catch (_: Exception) {}

    // 3. Fallback to OfflineBibleManager regardless of version
    try {
        val fallbackOffline = OfflineBibleManager.getVerses(context, bookOrder, chapter)
        val fallbackMatch = fallbackOffline.firstOrNull { it.verseNumber == verse }
        if (fallbackMatch != null && fallbackMatch.text.isNotBlank()) {
            return@withContext fallbackMatch.text.trim()
        }
    } catch (_: Exception) {}

    // 4. InitialVersesData fallback if available
    val refQuery = "$bookName $chapter:$verse".trim()
    val initMatch = InitialVersesData.verses.firstOrNull { it.reference.equals(refQuery, ignoreCase = true) }
    if (initMatch != null && initMatch.text.isNotBlank()) {
        return@withContext initMatch.text.removeSurrounding("«", "»").trim()
    }

    ""
}

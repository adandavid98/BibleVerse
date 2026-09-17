package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Cloud
import com.example.ui.reader.BibleReaderScreen
import com.example.ui.reader.viewmodel.BibleReaderViewModel
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.BuildConfig
import com.example.data.model.VerseEntity
import com.example.ui.components.AddVerseDialog
import com.example.ui.components.CloudSyncDialog
import com.example.ui.components.ExportDialog
import com.example.ui.components.UpdateDialog
import com.example.ui.components.VerseCard
import com.example.ui.components.VerseDetailDialog
import com.example.ui.theme.AppReadingTheme
import com.example.ui.theme.BibleHighlightColors
import com.example.ui.components.BibleChatDialog
import androidx.compose.material.icons.filled.AutoAwesome
import com.example.ui.viewmodel.BibleUiState
import com.example.ui.viewmodel.BibleViewModel
import com.example.ui.viewmodel.VerseFilter
import com.example.update.UpdateDownloadStatus
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Edit
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: BibleViewModel,
    uiState: BibleUiState
) {
    val context = LocalContext.current
    val readerViewModel: BibleReaderViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = BibleReaderViewModel.Factory(context)
    )
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedTab by remember { mutableIntStateOf(0) }
    val versesListState = rememberLazyListState()

    val showUpdateDialog by viewModel.showUpdateDialog.collectAsStateWithLifecycle()
    val updateInfo by viewModel.updateInfo.collectAsStateWithLifecycle()
    val updateDownloadStatus by viewModel.updateDownloadStatus.collectAsStateWithLifecycle()
    val firebaseUserState by viewModel.firebaseUserState.collectAsStateWithLifecycle()
    val firebaseSyncOperation by viewModel.firebaseSyncOperation.collectAsStateWithLifecycle()
    val showBibleChatDialog by viewModel.showBibleChatDialog.collectAsStateWithLifecycle()
    val chatMessages by viewModel.chatMessages.collectAsStateWithLifecycle()
    val isChatLoading by viewModel.isChatLoading.collectAsStateWithLifecycle()

    // Notify user of sync messages
    LaunchedEffect(uiState.syncStatusMessage) {
        uiState.syncStatusMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSyncMessage()
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (selectedTab != 1) {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    if (selectedTab != 0) {
                                        selectedTab = 0
                                        viewModel.onFilterSelected(VerseFilter.TODOS)
                                    }
                                    scope.launch {
                                        versesListState.animateScrollToItem(0)
                                    }
                                }
                                .padding(horizontal = 6.dp, vertical = 4.dp)
                                .testTag("top_bar_title_scroll_to_top")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MenuBook,
                                contentDescription = "Subir al inicio",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "BibleVerse",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "Compilación Canónica Fundamental",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { viewModel.showBibleChat(true) },
                            modifier = Modifier.testTag("btn_top_chat_ai")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AutoAwesome,
                                contentDescription = "Asistente Bíblico IA",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        IconButton(
                            onClick = { viewModel.showAddVerseDialog(true) },
                            modifier = Modifier.testTag("btn_top_add")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Añadir Versículo Manual",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        IconButton(
                            onClick = { selectedTab = 4 },
                            modifier = Modifier.testTag("btn_top_settings")
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = "Ajustes de la Aplicación",
                                tint = if (selectedTab == 4) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = {
                        if (selectedTab == 0) {
                            scope.launch {
                                versesListState.animateScrollToItem(0)
                            }
                        } else {
                            selectedTab = 0
                            viewModel.onFilterSelected(VerseFilter.TODOS)
                        }
                    },
                    icon = { Icon(Icons.Filled.MenuBook, contentDescription = "Versículos") },
                    label = { Text("Versículos") },
                    modifier = Modifier.testTag("nav_verses")
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                    },
                    icon = { Icon(Icons.Filled.AutoStories, contentDescription = "Lector") },
                    label = { Text("Lector") },
                    modifier = Modifier.testTag("nav_reader")
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                    },
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Buscador") },
                    label = { Text("Buscador") },
                    modifier = Modifier.testTag("nav_search")
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = {
                        selectedTab = 3
                        viewModel.onFilterSelected(VerseFilter.FAVORITOS)
                    },
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favoritos") },
                    label = { Text("Favoritos") },
                    modifier = Modifier.testTag("nav_favorites")
                )
            }
        },
        floatingActionButton = {
            if (selectedTab == 0 || selectedTab == 3) {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SmallFloatingActionButton(
                        onClick = { viewModel.showBibleChat(true) },
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.testTag("fab_bible_chat")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AutoAwesome,
                            contentDescription = "Consultar Asistente Bíblico IA"
                        )
                    }

                    FloatingActionButton(
                        onClick = { viewModel.showAddVerseDialog(true) },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.testTag("fab_add_verse")
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Añadir Versículo")
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (selectedTab) {
                0 -> VersesListTab(viewModel, uiState, versesListState)
                1 -> BibleReaderScreen(viewModel = readerViewModel)
                2 -> SearchTab(viewModel, uiState)
                3 -> FavoritesAndNotesTab(viewModel, uiState)
                4 -> SettingsTab(viewModel, uiState, onBack = { selectedTab = 0 })
            }
        }
    }

    if (uiState.showAddDialog) {
        AddVerseDialog(
            onDismiss = { viewModel.showAddVerseDialog(false) },
            onAddVerse = { book, chv, testm, txt, ctx, top, nts, bibleVer ->
                viewModel.addCustomVerse(book, chv, testm, txt, ctx, top, nts, bibleVer)
            },
            onGenerateContext = { b, c, v, txt, ver, force, cb ->
                viewModel.generateIntelligentContext(b, c, v, txt, ver, force, cb)
            }
        )
    }

    if (uiState.showSyncDialog) {
        CloudSyncDialog(
            syncId = uiState.cloudSyncId,
            lastSyncTime = uiState.lastSyncTime,
            userState = firebaseUserState,
            syncOperation = firebaseSyncOperation,
            onDismiss = { viewModel.showSyncDialog(false) },
            onSignInGoogle = { viewModel.signInWithGoogle(context) },
            onSignInAnonymous = { viewModel.signInAnonymously() },
            onSignInEmailPassword = { email, pass -> viewModel.signInWithEmailPassword(email, pass) },
            onSignOut = { viewModel.signOutFirebase(context) },
            onUploadFirestore = { viewModel.uploadToFirestore() },
            onDownloadFirestore = { viewModel.downloadFromFirestore() },
            onPerformBackup = { viewModel.performCloudBackup() },
            onPerformRestore = { viewModel.performCloudRestore(it) },
            permanentSha1 = viewModel.permanentSha1,
            currentWebClientId = viewModel.getResolvedFirebaseWebClientId(context),
            onSaveWebClientId = { id -> viewModel.saveFirebaseWebClientId(context, id) }
        )
    }

    if (showUpdateDialog && updateInfo != null) {
        UpdateDialog(
            updateInfo = updateInfo!!,
            downloadStatus = updateDownloadStatus,
            onStartDownload = { viewModel.startUpdateDownload() },
            onInstallApk = { viewModel.installDownloadedApk(context) },
            onOpenInBrowser = { viewModel.openGitHubReleaseInBrowser(context) },
            onPostpone = { viewModel.postponeUpdate(24) },
            onIgnoreVersion = { viewModel.ignoreCurrentVersion() },
            onDismiss = { viewModel.setShowUpdateDialog(false) }
        )
    }

    uiState.selectedVerseForDetail?.let { verse ->
        VerseDetailDialog(
            verse = verse,
            fontScale = uiState.fontSizeScale,
            onDismiss = { viewModel.openVerseDetail(null) },
            onFavoriteToggle = { viewModel.toggleFavorite(verse) },
            onHighlightSelected = { colorHex ->
                viewModel.updateHighlight(verse.id, colorHex)
            },
            onSaveNotes = { notes ->
                viewModel.updateNotes(verse.id, notes)
            },
            onSaveVerseEdit = { ref, txt, ctx, top, bibleVer ->
                viewModel.updateVerseDetails(verse.id, ref, txt, ctx, top, bibleVer)
            },
            onShare = { viewModel.shareVerse(context, verse) },
            onExportPdf = {
                viewModel.exportContent("PDF", exportAll = false)
            },
            onDelete = if (verse.isCustom) {
                { viewModel.deleteVerse(verse) }
            } else null,
            onEnrichContextAi = {
                viewModel.enrichVerseContextWithAi(verse.id)
            }
        )
    }

    if (showBibleChatDialog) {
        BibleChatDialog(
            chatMessages = chatMessages,
            isLoading = isChatLoading,
            onSendMessage = { question ->
                viewModel.sendBibleChatMessage(question)
            },
            onClearChat = { viewModel.clearBibleChat() },
            onDismiss = { viewModel.showBibleChat(false) },
            onAddSuggestedVerse = { reference ->
                viewModel.openAddVerseForReference(reference)
            }
        )
    }
}

@Composable
fun VersesListTab(
    viewModel: BibleViewModel,
    uiState: BibleUiState,
    listState: LazyListState = rememberLazyListState()
) {
    val context = LocalContext.current
    val otCount = remember(uiState.verses) { uiState.verses.count { it.testament.contains("Antiguo", ignoreCase = true) } }
    val ntCount = remember(uiState.verses) { uiState.verses.count { it.testament.contains("Nuevo", ignoreCase = true) } }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
    ) {
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

        // Filter Pills: Todos, Antiguo Testamento, Nuevo Testamento
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = uiState.selectedFilter == VerseFilter.TODOS,
                    onClick = { viewModel.onFilterSelected(VerseFilter.TODOS) },
                    label = { Text("Todos (${uiState.verses.size})") },
                    modifier = Modifier.testTag("filter_all")
                )
                FilterChip(
                    selected = uiState.selectedFilter == VerseFilter.ANTIGUO,
                    onClick = { viewModel.onFilterSelected(VerseFilter.ANTIGUO) },
                    label = { Text("Antiguo Testamento ($otCount)") },
                    modifier = Modifier.testTag("filter_ot")
                )
                FilterChip(
                    selected = uiState.selectedFilter == VerseFilter.NUEVO,
                    onClick = { viewModel.onFilterSelected(VerseFilter.NUEVO) },
                    label = { Text("Nuevo Testamento ($ntCount)") },
                    modifier = Modifier.testTag("filter_nt")
                )
            }
        }

        // Verses Cards List
        items(
            items = uiState.filteredVerses,
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
    }
}

@Composable
fun SearchTab(
    viewModel: BibleViewModel,
    uiState: BibleUiState
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Search bar
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("search_input_field"),
            placeholder = { Text("Buscar por palabra clave, libro, pasaje o tema...") },
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = "Buscar")
            },
            trailingIcon = {
                if (uiState.searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Limpiar")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Topic chips row
        Text(
            text = "Temas bíblicos destacados:",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            item {
                FilterChip(
                    selected = uiState.selectedTopic == null,
                    onClick = { viewModel.onTopicSelected(null) },
                    label = { Text("Cualquiera") }
                )
            }
            items(uiState.availableTopics) { topic ->
                FilterChip(
                    selected = uiState.selectedTopic == topic,
                    onClick = { viewModel.onTopicSelected(topic) },
                    label = { Text(topic) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Results count
        Text(
            text = "${uiState.filteredVerses.size} versículo(s) encontrados",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Results list
        if (uiState.filteredVerses.isEmpty()) {
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
                        text = "No se encontraron versículos con «${uiState.searchQuery}»",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                items(uiState.filteredVerses, key = { it.id }) { verse ->
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

@Composable
fun FavoritesAndNotesTab(
    viewModel: BibleViewModel,
    uiState: BibleUiState
) {
    val context = LocalContext.current
    var subFilter by remember { mutableStateOf("FAVORITOS") } // "FAVORITOS", "RESALTADOS", "NOTAS"

    val displayedVerses = when (subFilter) {
        "FAVORITOS" -> uiState.verses.filter { it.isFavorite }
        "RESALTADOS" -> uiState.verses.filter { it.highlightColor.isNotBlank() }
        "NOTAS" -> uiState.verses.filter { it.notes.isNotBlank() }
        else -> uiState.verses.filter { it.isFavorite }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = subFilter == "FAVORITOS",
                onClick = { subFilter = "FAVORITOS" },
                label = { Text("Favoritos (${uiState.verses.count { it.isFavorite }})") },
                leadingIcon = { Icon(Icons.Filled.Favorite, contentDescription = null, modifier = Modifier.size(16.dp)) },
                modifier = Modifier.testTag("chip_sub_favorites")
            )
            FilterChip(
                selected = subFilter == "RESALTADOS",
                onClick = { subFilter = "RESALTADOS" },
                label = { Text("Resaltados (${uiState.verses.count { it.highlightColor.isNotBlank() }})") },
                modifier = Modifier.testTag("chip_sub_highlights")
            )
            FilterChip(
                selected = subFilter == "NOTAS",
                onClick = { subFilter = "NOTAS" },
                label = { Text("Con Notas (${uiState.verses.count { it.notes.isNotBlank() }})") },
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
                        imageVector = if (subFilter == "FAVORITOS") Icons.Outlined.FavoriteBorder else Icons.Outlined.BookmarkBorder,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = when (subFilter) {
                            "FAVORITOS" -> "Aún no tienes versículos favoritos marcados."
                            "RESALTADOS" -> "No has resaltado ningún versículo todavía."
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

@Composable
fun SettingsTab(
    viewModel: BibleViewModel,
    uiState: BibleUiState,
    onBack: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var reminderHour by remember(uiState.reminderHour) { mutableIntStateOf(uiState.reminderHour) }
    var reminderMinute by remember(uiState.reminderMinute) { mutableIntStateOf(uiState.reminderMinute) }
    var reminderEnabled by remember(uiState.reminderEnabled) { mutableStateOf(uiState.reminderEnabled) }
    var showTimePickerDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        if (onBack != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Regresar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "Configuración",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Ajustes, Copias y Nube",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
        }
        // Section: Visual Themes (Modo Lectura Nocturna Anti-Fatiga)
        Text(
            text = "MODO DE LECTURA & VISUALIZACIÓN",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = MaterialTheme.colorScheme.secondary
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Tema de la aplicación:",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Modo Claro
                    ThemeOptionCard(
                        title = "Claro",
                        subtitle = "Día",
                        icon = Icons.Filled.LightMode,
                        isSelected = uiState.readingTheme == AppReadingTheme.LIGHT,
                        onClick = { viewModel.setReadingTheme(AppReadingTheme.LIGHT) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("theme_light")
                    )

                    // Modo Lectura Nocturna Sepia (Anti-Fatiga)
                    ThemeOptionCard(
                        title = "Nocturno",
                        subtitle = "Anti-fatiga",
                        icon = Icons.Filled.Nightlight,
                        isSelected = uiState.readingTheme == AppReadingTheme.SEPIA_NIGHT,
                        onClick = { viewModel.setReadingTheme(AppReadingTheme.SEPIA_NIGHT) },
                        modifier = Modifier
                            .weight(1.15f)
                            .testTag("theme_sepia")
                    )

                    // Modo Oscuro Profundo
                    ThemeOptionCard(
                        title = "Oscuro",
                        subtitle = "OLED",
                        icon = Icons.Filled.DarkMode,
                        isSelected = uiState.readingTheme == AppReadingTheme.DARK,
                        onClick = { viewModel.setReadingTheme(AppReadingTheme.DARK) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("theme_dark")
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Font size scale
                Text(
                    text = "Tamaño del texto bíblico:",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        "Normal" to 1.0f,
                        "Grande" to 1.18f,
                        "Muy Grande" to 1.35f
                    ).forEach { (label, scale) ->
                        val isSelected = kotlin.math.abs(uiState.fontSizeScale - scale) < 0.05f
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.setFontSizeScale(scale) },
                            label = { Text(label) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("font_scale_$label")
                        )
                    }
                }
            }
        }

        // Section: Daily Reminders / Notifications
        Text(
            text = "RECORDATORIOS DIARIOS DE VERSÍCULOS",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = MaterialTheme.colorScheme.secondary
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Notificación diaria",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Recibe el versículo del día y su reflexión cada mañana",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = reminderEnabled,
                        onCheckedChange = { enabled ->
                            reminderEnabled = enabled
                            viewModel.setDailyReminder(reminderHour, reminderMinute, enabled)
                        },
                        modifier = Modifier.testTag("switch_reminder")
                    )
                }

                if (reminderEnabled) {
                    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Hora preferida:",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            val timeStr = String.format("%02d:%02d", reminderHour, reminderMinute)
                            Text(
                                text = "$timeStr hrs",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            // Quick buttons to adjust hour
                            OutlinedButton(
                                onClick = {
                                    reminderHour = (reminderHour + 1) % 24
                                    viewModel.setDailyReminder(reminderHour, reminderMinute, true)
                                },
                                modifier = Modifier.testTag("btn_change_hour")
                            ) {
                                Text("+1 Hora")
                            }

                            Button(
                                onClick = {
                                    reminderHour = (reminderHour + 23) % 24
                                    viewModel.setDailyReminder(reminderHour, reminderMinute, true)
                                }
                            ) {
                                Text("-1 Hora")
                            }
                        }
                    }

                    // Test notification button
                    OutlinedButton(
                        onClick = {
                            viewModel.testDailyNotification()
                            Toast.makeText(context, "Notificación enviada", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("btn_test_notification")
                    ) {
                        Icon(Icons.Filled.NotificationsActive, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Probar Notificación Ahora")
                    }
                }
            }
        }

        // Section: Nube y Copia de Seguridad
        Text(
            text = "NUBE Y COPIA DE SEGURIDAD",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = MaterialTheme.colorScheme.secondary
        )

        val firebaseUserState by viewModel.firebaseUserState.collectAsStateWithLifecycle()
        val firebaseSyncOperation by viewModel.firebaseSyncOperation.collectAsStateWithLifecycle()

        com.example.ui.components.CloudSyncCardContent(
            syncId = uiState.cloudSyncId,
            lastSyncTime = uiState.lastSyncTime,
            userState = firebaseUserState,
            syncOperation = firebaseSyncOperation,
            onDismiss = null,
            onSignInGoogle = { viewModel.signInWithGoogle(context) },
            onSignInAnonymous = { viewModel.signInAnonymously() },
            onSignInEmailPassword = { email, pass -> viewModel.signInWithEmailPassword(email, pass) },
            onSignOut = { viewModel.signOutFirebase(context) },
            onUploadFirestore = { viewModel.uploadToFirestore() },
            onDownloadFirestore = { viewModel.downloadFromFirestore() },
            onPerformBackup = { viewModel.performCloudBackup() },
            onPerformRestore = { viewModel.performCloudRestore(it) },
            permanentSha1 = viewModel.permanentSha1,
            currentWebClientId = viewModel.getResolvedFirebaseWebClientId(context),
            onSaveWebClientId = { id -> viewModel.saveFirebaseWebClientId(context, id) },
            isDialog = false
        )

        // Section: Updates and GitHub Releases
        Text(
            text = "ACTUALIZACIONES DE LA APLICACIÓN",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = MaterialTheme.colorScheme.secondary
        )

        val updateDownloadStatus by viewModel.updateDownloadStatus.collectAsStateWithLifecycle()
        val updateInfo by viewModel.updateInfo.collectAsStateWithLifecycle()
        val autoCheckUpdates by viewModel.autoCheckUpdates.collectAsStateWithLifecycle()

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Versión instalada",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "v${BuildConfig.VERSION_NAME} (Compilación ${BuildConfig.VERSION_CODE})",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "Oficial",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                // Switch for automatic update checking on app start
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Buscar actualizaciones al abrir",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (autoCheckUpdates) "Avisa cuando haya una versión nueva" else "Solo buscar manualmente",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = autoCheckUpdates,
                        onCheckedChange = { viewModel.setAutoCheckUpdates(it) },
                        modifier = Modifier.testTag("switch_auto_check_updates")
                    )
                }

                // If update is available or status
                val currentStatus = updateDownloadStatus
                when (currentStatus) {
                    is UpdateDownloadStatus.Available -> {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { viewModel.setShowUpdateDialog(true) },
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Filled.SystemUpdate,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "¡Nueva versión disponible: ${currentStatus.info.tagName}!",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "Toca para ver novedades e instalar actualización",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    is UpdateDownloadStatus.UpToDate -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Tienes la versión más reciente.",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    else -> {}
                }

                // Button: Check for updates
                Button(
                    onClick = { viewModel.checkForUpdates(silent = false) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("btn_check_updates")
                ) {
                    if (updateDownloadStatus is UpdateDownloadStatus.Checking) {
                        Icon(Icons.Filled.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Buscando actualizaciones...")
                    } else {
                        Icon(Icons.Filled.SystemUpdate, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Buscar Actualizaciones Ahora")
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeOptionCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            ),
        color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun VerseOfTheDayCard(
    verse: VerseEntity,
    fontScale: Float = 1.0f,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onShare: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .testTag("card_verse_of_the_day"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.AutoAwesome,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "VERSÍCULO DEL DÍA",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Row {
                    IconButton(
                        onClick = onFavoriteToggle,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (verse.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (verse.isFavorite) Color(0xFFE53935) else MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    IconButton(
                        onClick = onShare,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Compartir",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = verse.text,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic,
                    fontSize = (18 * fontScale).sp,
                    lineHeight = (26 * fontScale).sp,
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "— ${verse.reference}",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = verse.topic,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

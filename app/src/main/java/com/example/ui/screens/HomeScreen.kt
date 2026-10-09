package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.bible.BibleCatalog
import com.example.ui.components.AddVerseDialog
import com.example.ui.components.BibleChatDialog
import com.example.ui.components.CloudSyncDialog
import com.example.ui.components.UpdateDialog
import com.example.ui.components.VerseDetailDialog
import com.example.ui.reader.BibleReaderScreen
import com.example.ui.reader.viewmodel.BibleReaderViewModel
import com.example.ui.screens.components.ReadingPlansBottomSheet
import com.example.ui.viewmodel.BibleUiState
import com.example.ui.viewmodel.BibleViewModel
import com.example.ui.viewmodel.VerseFilter
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
    var showReadingPlans by remember { mutableStateOf(false) }
    var readingPlansInitialTab by remember { mutableIntStateOf(0) }

    val showUpdateDialog by viewModel.showUpdateDialog.collectAsStateWithLifecycle()
    val updateInfo by viewModel.updateInfo.collectAsStateWithLifecycle()
    val updateDownloadStatus by viewModel.updateDownloadStatus.collectAsStateWithLifecycle()
    val firebaseUserState by viewModel.firebaseUserState.collectAsStateWithLifecycle()
    val firebaseSyncOperation by viewModel.firebaseSyncOperation.collectAsStateWithLifecycle()
    val showBibleChatDialog by viewModel.showBibleChatDialog.collectAsStateWithLifecycle()
    val chatMessages by viewModel.chatMessages.collectAsStateWithLifecycle()
    val isChatLoading by viewModel.isChatLoading.collectAsStateWithLifecycle()
    val isGeneratingContext by viewModel.isGeneratingContext.collectAsStateWithLifecycle()
    val readerUiState by readerViewModel.uiState.collectAsStateWithLifecycle()

    // Smooth Android back button handling: Return from Settings to home
    BackHandler(enabled = selectedTab == 4) {
        selectedTab = 0
    }

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
            // Main TopBar displayed on main tabs (0, 2, 3). Hidden on Reader (1) and Settings (4)
            if (selectedTab in listOf(0, 2, 3)) {
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
                        // AI Assistant action button
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

                        // Reading Plans action button
                        IconButton(
                            onClick = { showReadingPlans = true },
                            modifier = Modifier.testTag("btn_top_reading_plans")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CalendarMonth,
                                contentDescription = "Planes de Lectura Bíblica",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        // Settings action button
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
            // Bottom navigation bar only on tabs 0..3 (hidden in Settings to avoid phantom unselected tabs)
            val showBottomBar = (selectedTab in 0..3) && (selectedTab != 1 || readerUiState.isReaderBarsVisible)

            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
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
            }
        },
        floatingActionButton = {
            // Single, focused FAB: Dedicated Add Verse action without confusing duplication
            if (selectedTab == 0 || selectedTab == 3) {
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
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    if ((selectedTab == 1 && !readerUiState.isReaderBarsVisible) || selectedTab == 4) {
                        PaddingValues(0.dp)
                    } else {
                        paddingValues
                    }
                )
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (selectedTab) {
                0 -> VersesListTab(
                    viewModel = viewModel,
                    uiState = uiState,
                    lastReadBookName = readerUiState.currentBook?.name ?: "Génesis",
                    lastReadBookId = readerUiState.currentBook?.id ?: 1,
                    lastReadChapter = readerUiState.currentChapter,
                    lastReadVersion = readerUiState.preferences.bibleVersion,
                    listState = versesListState,
                    onOpenReadingPlans = { showReadingPlans = true },
                    onNavigateToReader = { bookId, chapter, verse ->
                        readerViewModel.navigateToVerse(bookId, chapter, verse)
                        selectedTab = 1
                    }
                )
                1 -> BibleReaderScreen(viewModel = readerViewModel)
                2 -> SearchTab(
                    viewModel = viewModel,
                    uiState = uiState,
                    onNavigateToReader = { bookId, chapter, verse ->
                        readerViewModel.navigateToVerse(bookId, chapter, verse)
                        selectedTab = 1
                    }
                )
                3 -> FavoritesAndNotesTab(viewModel, uiState)
                4 -> SettingsTab(
                    viewModel = viewModel,
                    uiState = uiState,
                    onBack = { selectedTab = 0 },
                    onOpenReadingPlans = { tab ->
                        readingPlansInitialTab = tab
                        showReadingPlans = true
                    }
                )
            }
        }
    }

    if (uiState.showAddDialog) {
        AddVerseDialog(
            initialReference = uiState.initialReferenceForAdd,
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
            },
            isEnrichingAi = isGeneratingContext
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
            onNavigateToVerse = { reference ->
                val target = BibleCatalog.parseReference(reference)
                if (target != null) {
                    viewModel.showBibleChat(false)
                    readerViewModel.navigateToVerse(
                        bookId = target.bookId,
                        chapter = target.chapter,
                        startVerse = target.startVerse,
                        endVerse = target.endVerse
                    )
                    selectedTab = 1
                } else {
                    viewModel.openAddVerseForReference(reference)
                }
            }
        )
    }

    if (showReadingPlans) {
        ReadingPlansBottomSheet(
            viewModel = viewModel,
            initialTab = readingPlansInitialTab,
            onNavigateToReader = { bookId, chapter, verse ->
                readerViewModel.navigateToVerse(bookId, chapter, verse)
                selectedTab = 1
            },
            onDismiss = { showReadingPlans = false }
        )
    }
}

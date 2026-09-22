package com.example.ui.reader

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.preferences.ReaderFontFamily
import com.example.data.preferences.ReaderThemeMode
import com.example.domain.model.ReaderVerseUiModel
import com.example.ui.reader.components.BibleVersionSelectorDialog
import com.example.ui.reader.components.BookChapterSelectorSheet
import com.example.ui.reader.components.ReaderSettingsBottomSheet
import com.example.ui.reader.components.ShareTemplateDialog
import com.example.ui.reader.components.VerseActionBar
import com.example.ui.reader.viewmodel.BibleReaderViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibleReaderScreen(
    viewModel: BibleReaderViewModel,
    onBack: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    var showVersionSelectorModal by remember { mutableStateOf(false) }

    // Ensure active chapter is loaded if screen appears empty
    LaunchedEffect(uiState.currentBook, uiState.currentChapter, uiState.verses.size) {
        if (uiState.verses.isEmpty() && !uiState.isLoading && uiState.currentBook != null) {
            viewModel.reloadCurrentChapter()
        }
    }

    // Scroll to specific target verse when selected from modal or navigation
    LaunchedEffect(uiState.targetScrollVerse, uiState.verses.size) {
        val target = uiState.targetScrollVerse
        if (target != null && uiState.verses.isNotEmpty()) {
            val index = uiState.verses.indexOfFirst {
                it.bookId == uiState.currentBook?.id && it.chapter == uiState.currentChapter && it.verseNumber == target
            }
            val scrollIndex = if (index >= 0) index else uiState.verses.indexOfFirst { it.verseNumber == target }
            if (scrollIndex >= 0) {
                listState.animateScrollToItem(scrollIndex)
                viewModel.clearTargetScrollVerse()
            }
        }
    }

    // Trigger continuous scroll load when user approaches the end of loaded chapters
    LaunchedEffect(listState, uiState.preferences.continuousScrollEnabled, uiState.verses.size) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val total = layoutInfo.totalItemsCount
            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            total > 0 && lastVisible >= total - 3
        }.distinctUntilChanged().collect { shouldLoadMore ->
            if (shouldLoadMore && uiState.preferences.continuousScrollEnabled && !uiState.isLoadingMore && !uiState.isLoading) {
                viewModel.loadNextChapterContinuous()
            }
        }
    }

    // Dynamically update current visible book & chapter in top bar and selector as user scrolls
    LaunchedEffect(listState, uiState.preferences.continuousScrollEnabled) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo.firstOrNull()?.index
        }.distinctUntilChanged().collect { firstIndex ->
            if (firstIndex != null && uiState.preferences.continuousScrollEnabled && firstIndex in uiState.verses.indices) {
                val visibleVerse = uiState.verses[firstIndex]
                viewModel.updateVisibleBookAndChapter(visibleVerse.bookId, visibleVerse.chapter)
            }
        }
    }

    // Colors matching theme
    val (themeBg, themeText, themeSecondary, themeAccent) = when (uiState.preferences.themeMode) {
        ReaderThemeMode.LIGHT -> Quad(
            Color(0xFFFFFFFF),
            Color(0xFF1F2937),
            Color(0xFF6B7280),
            Color(0xFF2563EB)
        )
        ReaderThemeMode.SEPIA -> Quad(
            Color(0xFFFBF0D9),
            Color(0xFF382813),
            Color(0xFF8D6E48),
            Color(0xFF9A3412)
        )
        ReaderThemeMode.DARK -> Quad(
            Color(0xFF121417),
            Color(0xFFF1F5F9),
            Color(0xFF94A3B8),
            Color(0xFF38BDF8)
        )
        ReaderThemeMode.NIGHT -> Quad(
            Color(0xFF090D16),
            Color(0xFFE2E8F0),
            Color(0xFF64748B),
            Color(0xFF818CF8)
        )
    }

    val selectedFontFamily = when (uiState.preferences.fontFamily) {
        ReaderFontFamily.SERIF -> FontFamily.Serif
        ReaderFontFamily.SANS_SERIF -> FontFamily.SansSerif
        ReaderFontFamily.CONDENSED -> FontFamily(android.graphics.Typeface.create("sans-serif-condensed", android.graphics.Typeface.NORMAL))
        ReaderFontFamily.CASUAL -> FontFamily(android.graphics.Typeface.create("casual", android.graphics.Typeface.NORMAL))
        ReaderFontFamily.CURSIVE -> FontFamily.Cursive
        ReaderFontFamily.MONOSPACE -> FontFamily.Monospace
    }

    val fontSize = uiState.preferences.fontSizeSp.sp
    val lineHeight = (uiState.preferences.fontSizeSp * uiState.preferences.lineSpacingMultiplier).sp

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { viewModel.openBookChapterSelector() },
                        color = themeAccent.copy(alpha = 0.12f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${uiState.currentBook?.name ?: "Biblia"} ${uiState.currentChapter}",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium,
                                color = themeText
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = themeText,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                },
                navigationIcon = {
                    if (onBack != null) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = themeText)
                        }
                    }
                },
                actions = {
                    // Direct Bible Version Selector Modal Pill in top-right
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { showVersionSelectorModal = true }
                            .border(1.dp, themeSecondary.copy(alpha = 0.35f), RoundedCornerShape(16.dp)),
                        color = themeAccent.copy(alpha = 0.12f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = uiState.preferences.bibleVersion,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelLarge,
                                color = themeText
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "Cambiar versión de la Biblia",
                                tint = themeText,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Reader Settings Button
                    IconButton(onClick = { viewModel.openSettingsSheet() }) {
                        Icon(Icons.Default.FormatSize, contentDescription = "Ajustes de lectura", tint = themeText)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = themeBg,
                    titleContentColor = themeText,
                    actionIconContentColor = themeText
                )
            )
        },
        containerColor = themeBg
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.verses.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = themeAccent)
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Cargando lectura...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = themeSecondary
                            )
                            Button(
                                onClick = { viewModel.reloadCurrentChapter() },
                                colors = ButtonDefaults.buttonColors(containerColor = themeAccent)
                            ) {
                                Text("Recargar capítulo")
                            }
                        }
                    }
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 22.dp),
                    contentPadding = PaddingValues(top = 12.dp, bottom = 110.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    itemsIndexed(
                        items = uiState.verses,
                        key = { _, v -> "${v.bookId}_${v.chapter}_${v.verseNumber}" }
                    ) { index, verse ->
                        val prevVerse = if (index > 0) uiState.verses[index - 1] else null
                        val isNewChapter = prevVerse != null && (prevVerse.chapter != verse.chapter || prevVerse.bookId != verse.bookId)

                        Column(modifier = Modifier.fillMaxWidth()) {
                            if (isNewChapter) {
                                ChapterBreakHeader(
                                    bookName = verse.bookName,
                                    chapter = verse.chapter,
                                    fontFamily = selectedFontFamily,
                                    themeText = themeText,
                                    themeSecondary = themeSecondary
                                )
                            }

                            // Section Heading (Perícopa) in bold italic if present
                            if (!verse.sectionHeading.isNullOrBlank()) {
                                Spacer(modifier = Modifier.height(14.dp))
                                Text(
                                    text = verse.sectionHeading,
                                    fontFamily = selectedFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = (fontSize.value * 1.08f).sp,
                                    color = themeText,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }

                            // Compact verse text with superscript number & red letters
                            CompactVerseRow(
                                verse = verse,
                                fontFamily = selectedFontFamily,
                                fontSize = fontSize,
                                lineHeight = lineHeight,
                                textColor = themeText,
                                secondaryColor = themeSecondary,
                                isDarkTheme = uiState.preferences.themeMode == ReaderThemeMode.DARK || uiState.preferences.themeMode == ReaderThemeMode.NIGHT,
                                redLettersEnabled = uiState.preferences.redLettersEnabled,
                                onClick = { viewModel.toggleVerseSelection(verse.verseNumber) }
                            )
                        }
                    }

                    if (uiState.isLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 18.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(26.dp),
                                    color = themeAccent,
                                    strokeWidth = 2.5.dp
                                )
                            }
                        }
                    }

                    // Bottom spacer before bottom controls
                    item {
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }
            }

            // Navigation buttons at the extremes & center pill (Matching screenshot layout)
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left extreme: Previous chapter button (pointing left)
                Surface(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .shadow(6.dp, CircleShape)
                        .clickable { viewModel.previousChapter() },
                    shape = CircleShape,
                    color = themeBg,
                    border = BorderStroke(1.dp, themeSecondary.copy(alpha = 0.3f))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Capítulo anterior",
                            tint = themeText,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Center: Current Book & Chapter pill (Click opens selector modal)
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .shadow(8.dp, RoundedCornerShape(24.dp))
                        .clickable { viewModel.openBookChapterSelector() },
                    shape = RoundedCornerShape(24.dp),
                    color = themeBg,
                    border = BorderStroke(1.dp, themeSecondary.copy(alpha = 0.35f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(themeAccent.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = themeAccent,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = "${(uiState.currentBook?.name ?: "LIBRO").uppercase()} ${uiState.currentChapter}",
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.5.sp,
                            fontSize = 13.sp,
                            color = themeText
                        )
                    }
                }

                // Right extreme: Next chapter button (pointing right)
                Surface(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .shadow(6.dp, CircleShape)
                        .clickable { viewModel.nextChapter() },
                    shape = CircleShape,
                    color = themeBg,
                    border = BorderStroke(1.dp, themeSecondary.copy(alpha = 0.3f))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.ArrowForward,
                            contentDescription = "Capítulo siguiente",
                            tint = themeText,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Contextual Floating Action Bar when verses are selected
            VerseActionBar(
                selectedCount = uiState.selectedVerseNumbers.size,
                onCopy = {
                    val formatted = viewModel.getFormattedQuotation()
                    copyToClipboard(context, formatted)
                    Toast.makeText(context, "Versículo copiado", Toast.LENGTH_SHORT).show()
                    viewModel.clearSelection()
                },
                onHighlight = { hex ->
                    viewModel.applyHighlightToSelection(hex)
                },
                onRemoveHighlight = {
                    viewModel.removeHighlightFromSelection()
                },
                onSaveToVerses = {
                    viewModel.saveSelectedVersesToMainModule()
                    Toast.makeText(context, "Versículo guardado en Versículos y Favoritos", Toast.LENGTH_SHORT).show()
                },
                onShareText = {
                    val formatted = viewModel.getFormattedQuotation()
                    sharePlainText(context, formatted)
                },
                onShareCard = {
                    viewModel.openShareDialog()
                },
                onClearSelection = {
                    viewModel.clearSelection()
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 76.dp)
            )
        }
    }

    // Modal Sheet: 3-step Book -> Chapter -> Verse Selector with Todo / AT / NT tabs
    if (uiState.isBookChapterSelectorOpen) {
        BookChapterSelectorSheet(
            books = uiState.books,
            currentBook = uiState.currentBook,
            currentChapter = uiState.currentChapter,
            currentVerse = uiState.targetScrollVerse ?: 1,
            onSelectBookChapterVerse = { book, chapter, verse ->
                viewModel.selectBookChapterVerse(book, chapter, verse)
            },
            onDismiss = { viewModel.closeBookChapterSelector() }
        )
    }

    // Bottom Sheet: Reader Settings (with Red letters & continuous scroll)
    if (uiState.isSettingsSheetOpen) {
        ReaderSettingsBottomSheet(
            preferences = uiState.preferences,
            onFontFamilyChange = { viewModel.updateFontFamily(it) },
            onFontSizeChange = { viewModel.updateFontSize(it) },
            onLineSpacingChange = { viewModel.updateLineSpacing(it) },
            onThemeModeChange = { viewModel.updateThemeMode(it) },
            onVersionChange = { viewModel.updateBibleVersion(it) },
            onRedLettersChange = { viewModel.updateRedLettersEnabled(it) },
            onContinuousScrollChange = { viewModel.updateContinuousScrollEnabled(it) },
            onDismiss = { viewModel.closeSettingsSheet() }
        )
    }

    // Dialog: Share Image Template
    if (uiState.isShareDialogOpen) {
        ShareTemplateDialog(
            verseText = viewModel.getSelectedRawText(),
            citationText = viewModel.getCitationOnly(),
            onCopyCitation = {
                val formatted = viewModel.getFormattedQuotation()
                copyToClipboard(context, formatted)
                Toast.makeText(context, "Cita copiada al portapapeles", Toast.LENGTH_SHORT).show()
                viewModel.clearSelection()
            },
            onDismiss = { viewModel.closeShareDialog() }
        )
    }
    val offlineStates by com.example.data.bible.OfflineBibleDownloadManager.downloadStates.collectAsState()

    // Dialog: Direct Bible Version Selector Modal
    if (showVersionSelectorModal) {
        BibleVersionSelectorDialog(
            currentVersion = uiState.preferences.bibleVersion,
            onSelectVersion = { version ->
                viewModel.updateBibleVersion(version)
            },
            downloadStates = offlineStates,
            onDownloadVersion = { version ->
                viewModel.downloadBibleVersion(version)
            },
            onDismiss = { showVersionSelectorModal = false }
        )
    }
}

@Composable
private fun CompactVerseRow(
    verse: ReaderVerseUiModel,
    fontFamily: FontFamily,
    fontSize: androidx.compose.ui.unit.TextUnit,
    lineHeight: androidx.compose.ui.unit.TextUnit,
    textColor: Color,
    secondaryColor: Color,
    isDarkTheme: Boolean,
    redLettersEnabled: Boolean,
    onClick: () -> Unit
) {
    val highlightColor = remember(verse.highlightColorHex) {
        if (!verse.highlightColorHex.isNullOrBlank()) {
            try {
                Color(android.graphics.Color.parseColor(verse.highlightColorHex)).copy(alpha = 0.28f)
            } catch (e: Exception) {
                null
            }
        } else null
    }

    val selectionBorder = if (verse.isSelected) {
        BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
    } else if (highlightColor != null) {
        BorderStroke(0.8.dp, highlightColor.copy(alpha = 0.5f))
    } else null

    val rowBg = when {
        verse.isSelected -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
        highlightColor != null -> highlightColor
        else -> Color.Transparent
    }

    // Words of Jesus red color (Light mode: #DC2626, Dark mode: #F87171)
    val jesusRedColor = if (isDarkTheme) Color(0xFFF87171) else Color(0xFFDC2626)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(rowBg)
            .then(if (selectionBorder != null) Modifier.border(selectionBorder, RoundedCornerShape(8.dp)) else Modifier)
            .clickable { onClick() }
            .padding(horizontal = 6.dp, vertical = 3.dp)
    ) {
        val annotatedText = buildAnnotatedString {
            // Elegant superscript verse number
            withStyle(
                style = SpanStyle(
                    color = secondaryColor.copy(alpha = 0.75f),
                    fontWeight = FontWeight.Bold,
                    fontSize = (fontSize.value * 0.7f).sp,
                    baselineShift = BaselineShift.Superscript
                )
            ) {
                append("${verse.verseNumber} ")
            }

            // Append verse content with accurate red letters
            appendVerseContent(
                builder = this,
                text = verse.text,
                isRedLetter = verse.isRedLetter,
                redLettersEnabled = redLettersEnabled,
                textColor = textColor,
                jesusRedColor = jesusRedColor,
                fontFamily = fontFamily,
                fontSize = fontSize
            )
        }

        Text(
            text = annotatedText,
            lineHeight = lineHeight,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ChapterBreakHeader(
    bookName: String,
    chapter: Int,
    fontFamily: FontFamily,
    themeText: Color,
    themeSecondary: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 28.dp, bottom = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            color = themeSecondary.copy(alpha = 0.25f),
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "$bookName $chapter",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily
            ),
            color = themeText
        )
        Spacer(modifier = Modifier.height(14.dp))
        Divider(
            color = themeSecondary.copy(alpha = 0.25f),
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth(0.9f)
        )
    }
}

private fun appendVerseContent(
    builder: androidx.compose.ui.text.AnnotatedString.Builder,
    text: String,
    isRedLetter: Boolean,
    redLettersEnabled: Boolean,
    textColor: Color,
    jesusRedColor: Color,
    fontFamily: FontFamily,
    fontSize: androidx.compose.ui.unit.TextUnit
) {
    if (!isRedLetter || !redLettersEnabled) {
        builder.withStyle(SpanStyle(color = textColor, fontSize = fontSize, fontFamily = fontFamily)) {
            append(text)
        }
        return
    }

    // Check for quote marks: «...» or "..."
    val hasGuillemets = text.contains("«") && text.contains("»")
    if (hasGuillemets) {
        val qStart = text.indexOf("«")
        val qEnd = text.indexOf("»", qStart)
        if (qStart >= 0 && qEnd > qStart) {
            val before = text.substring(0, qStart)
            val spoken = text.substring(qStart, qEnd + 1)
            val after = text.substring(qEnd + 1)

            if (before.isNotEmpty()) {
                builder.withStyle(SpanStyle(color = textColor, fontSize = fontSize, fontFamily = fontFamily)) {
                    append(before)
                }
            }
            builder.withStyle(SpanStyle(color = jesusRedColor, fontSize = fontSize, fontFamily = fontFamily)) {
                append(spoken)
            }
            if (after.isNotEmpty()) {
                builder.withStyle(SpanStyle(color = textColor, fontSize = fontSize, fontFamily = fontFamily)) {
                    append(after)
                }
            }
            return
        }
    }

    // Check for colon dialogue indicator (e.g. "diciendo: ", "les dijo: ", "respondió: ", etc.)
    val colonIdx = text.indexOf(":")
    if (colonIdx in 3..(text.length - 4)) {
        val prefix = text.substring(0, colonIdx + 1)
        val spoken = text.substring(colonIdx + 1)

        val prefixLower = prefix.lowercase()
        val isSpeechIntro = prefixLower.contains("dijo") ||
                prefixLower.contains("diciendo") ||
                prefixLower.contains("respondió") ||
                prefixLower.contains("habló") ||
                prefixLower.contains("preguntó") ||
                prefixLower.contains("clamó")

        if (isSpeechIntro) {
            builder.withStyle(SpanStyle(color = textColor, fontSize = fontSize, fontFamily = fontFamily)) {
                append(prefix)
            }
            builder.withStyle(SpanStyle(color = jesusRedColor, fontSize = fontSize, fontFamily = fontFamily)) {
                append(spoken)
            }
            return
        }
    }

    // Otherwise, the entire verse is Jesus speaking
    builder.withStyle(SpanStyle(color = jesusRedColor, fontSize = fontSize, fontFamily = fontFamily)) {
        append(text)
    }
}

private data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Versículo Bíblico", text)
    clipboard.setPrimaryClip(clip)
}

private fun sharePlainText(context: Context, text: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    context.startActivity(Intent.createChooser(intent, "Compartir versículo"))
}

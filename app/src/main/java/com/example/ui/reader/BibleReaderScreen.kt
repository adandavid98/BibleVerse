package com.example.ui.reader

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.preferences.ReaderFontFamily
import com.example.data.preferences.ReaderThemeMode
import com.example.domain.model.ReaderVerseUiModel
import com.example.ui.reader.components.BookChapterSelectorSheet
import com.example.ui.reader.components.ReaderSettingsBottomSheet
import com.example.ui.reader.components.ShareTemplateDialog
import com.example.ui.reader.components.VerseActionBar
import com.example.ui.reader.viewmodel.BibleReaderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibleReaderScreen(
    viewModel: BibleReaderViewModel,
    onBack: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    // Scroll to top when book or chapter changes
    LaunchedEffect(uiState.currentBook?.id, uiState.currentChapter) {
        listState.scrollToItem(0)
    }

    // Color theme resolution
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
            Color(0xFF1E293B),
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
                    // Previous chapter
                    IconButton(onClick = { viewModel.previousChapter() }) {
                        Icon(Icons.Default.ChevronLeft, contentDescription = "Capítulo anterior", tint = themeText)
                    }

                    // Next chapter
                    IconButton(onClick = { viewModel.nextChapter() }) {
                        Icon(Icons.Default.ChevronRight, contentDescription = "Capítulo siguiente", tint = themeText)
                    }

                    // Reader Settings
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
            if (uiState.isLoading && uiState.verses.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = themeAccent)
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Chapter Heading
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = (uiState.currentBook?.name ?: "").uppercase(),
                                fontFamily = selectedFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                letterSpacing = 2.sp,
                                color = themeSecondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "CAPÍTULO ${uiState.currentChapter}",
                                fontFamily = selectedFontFamily,
                                fontWeight = FontWeight.Black,
                                fontSize = 26.sp,
                                color = themeText
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = uiState.preferences.bibleVersion,
                                style = MaterialTheme.typography.labelMedium,
                                color = themeSecondary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Divider(
                                color = themeSecondary.copy(alpha = 0.3f),
                                thickness = 1.dp,
                                modifier = Modifier.width(60.dp)
                            )
                        }
                    }

                    // Verses List
                    items(uiState.verses, key = { it.verseNumber }) { verse ->
                        VerseRow(
                            verse = verse,
                            fontFamily = selectedFontFamily,
                            fontSize = fontSize,
                            lineHeight = lineHeight,
                            textColor = themeText,
                            numberColor = themeAccent,
                            onClick = { viewModel.toggleVerseSelection(verse.verseNumber) }
                        )
                    }

                    // Chapter End Navigation Footer
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = { viewModel.previousChapter() },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.ChevronLeft, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Capítulo Anterior", fontSize = 13.sp)
                            }

                            Button(
                                onClick = { viewModel.nextChapter() },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Siguiente Capítulo", fontSize = 13.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(Icons.Default.ChevronRight, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }

            // Contextual Floating Action Bar
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
                    .padding(bottom = 12.dp)
            )
        }
    }

    // Modal Sheet: Book & Chapter Selector
    if (uiState.isBookChapterSelectorOpen) {
        BookChapterSelectorSheet(
            books = uiState.books,
            currentBook = uiState.currentBook,
            currentChapter = uiState.currentChapter,
            onSelectBookAndChapter = { book, chapter ->
                viewModel.selectBookAndChapter(book, chapter)
            },
            onDismiss = { viewModel.closeBookChapterSelector() }
        )
    }

    // Bottom Sheet: Reader Settings
    if (uiState.isSettingsSheetOpen) {
        ReaderSettingsBottomSheet(
            preferences = uiState.preferences,
            onFontFamilyChange = { viewModel.updateFontFamily(it) },
            onFontSizeChange = { viewModel.updateFontSize(it) },
            onLineSpacingChange = { viewModel.updateLineSpacing(it) },
            onThemeModeChange = { viewModel.updateThemeMode(it) },
            onVersionChange = { viewModel.updateBibleVersion(it) },
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
}

@Composable
private fun VerseRow(
    verse: ReaderVerseUiModel,
    fontFamily: FontFamily,
    fontSize: androidx.compose.ui.unit.TextUnit,
    lineHeight: androidx.compose.ui.unit.TextUnit,
    textColor: Color,
    numberColor: Color,
    onClick: () -> Unit
) {
    val highlightColor = remember(verse.highlightColorHex) {
        if (!verse.highlightColorHex.isNullOrBlank()) {
            try {
                Color(android.graphics.Color.parseColor(verse.highlightColorHex)).copy(alpha = 0.45f)
            } catch (e: Exception) {
                null
            }
        } else null
    }

    val selectionBorder = if (verse.isSelected) {
        BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
    } else null

    val rowBg = when {
        verse.isSelected -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
        highlightColor != null -> highlightColor
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(rowBg)
            .then(if (selectionBorder != null) Modifier.border(selectionBorder, RoundedCornerShape(8.dp)) else Modifier)
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        val annotatedText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = numberColor,
                    fontWeight = FontWeight.Black,
                    fontSize = (fontSize.value * 0.8f).sp
                )
            ) {
                append("${verse.verseNumber}  ")
            }
            withStyle(
                style = SpanStyle(
                    color = textColor,
                    fontSize = fontSize,
                    fontFamily = fontFamily
                )
            ) {
                append(verse.text)
            }
        }

        Text(
            text = annotatedText,
            lineHeight = lineHeight,
            modifier = Modifier.fillMaxWidth()
        )
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

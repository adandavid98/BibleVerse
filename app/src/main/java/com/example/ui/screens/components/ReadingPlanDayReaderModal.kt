package com.example.ui.screens.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.bible.BibleCatalog
import com.example.data.bible.OfflineBibleManager
import com.example.data.bible.OfflineVerseDto
import com.example.data.bible.PlanPassageSegment
import com.example.data.bible.ReadingPlanDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

data class DaySegmentWithVerses(
    val segment: PlanPassageSegment,
    val verses: List<OfflineVerseDto>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingPlanDayReaderModal(
    day: ReadingPlanDay,
    isCompleted: Boolean,
    onToggleCompleted: (Int) -> Unit,
    onNextDay: (() -> Unit)? = null,
    onOpenInFullBible: (bookId: Int, chapter: Int, verse: Int) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var fontSizeSp by remember { mutableStateOf(17f) }
    var isLoading by remember { mutableStateOf(true) }
    var segmentsData by remember { mutableStateOf<List<DaySegmentWithVerses>>(emptyMap<Int, String>().let { emptyList() }) }
    var isVersesExpanded by remember(day) { mutableStateOf(day.storyNarrative.isBlank()) }

    val scope = rememberCoroutineScope()
    val prefsRepo = remember { com.example.data.preferences.ReaderPreferencesRepository(context.applicationContext) }
    val readerDao = remember { com.example.data.local.BibleDatabase.getDatabase(context.applicationContext).bibleReaderDao() }
    val readerRepo = remember { com.example.data.repository.BibleReaderRepository(readerDao, context.applicationContext) }
    val offlineStates by com.example.data.bible.OfflineBibleDownloadManager.downloadStates.collectAsState()

    // Same version the main reader uses (shared preference), changeable right here.
    var selectedVersion by remember { mutableStateOf("RVR1960") }
    var showVersionSelector by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        selectedVersion = try {
            prefsRepo.readerPreferences.first().bibleVersion
        } catch (_: Exception) {
            "RVR1960"
        }
    }

    LaunchedEffect(day, selectedVersion) {
        isLoading = true
        val list = withContext(Dispatchers.IO) {
            val acc = mutableListOf<DaySegmentWithVerses>()
            val targetSegments = if (day.passages.isNotEmpty()) {
                day.passages
            } else {
                val bName = BibleCatalog.books.firstOrNull { it.order == day.primaryBookId }?.name ?: "Libro ${day.primaryBookId}"
                listOf(PlanPassageSegment(day.primaryBookId, bName, day.primaryChapter))
            }

            for (seg in targetSegments) {
                val verses = try {
                    if (selectedVersion.uppercase() == "RVR1960") {
                        OfflineBibleManager.getVerses(context, seg.bookId, seg.chapter)
                    } else {
                        readerRepo.ensureChapterVerses(seg.bookId, seg.chapter, selectedVersion)
                        readerRepo.getVerses(seg.bookId, seg.chapter, selectedVersion).first().map {
                            OfflineVerseDto(it.bookId, it.chapter, it.verseNumber, it.text)
                        }
                    }
                } catch (_: Exception) {
                    emptyList()
                }
                acc.add(DaySegmentWithVerses(seg, verses))
            }
            acc
        }
        segmentsData = list
        isLoading = false
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
            // Header bar
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Regresar a planes",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = day.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1
                            )
                            Text(
                                text = day.passagesSummary,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    // Font size pills & Close
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Surface(
                            onClick = { if (fontSizeSp > 13f) fontSizeSp -= 1.5f },
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("A-", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Surface(
                            onClick = { if (fontSizeSp < 25f) fontSizeSp += 1.5f },
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("A+", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 20.dp),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // 1. Historia Narrada (si está disponible)
                    if (day.storyNarrative.isNotBlank()) {
                        item(key = "story_narrative_${day.dayNumber}") {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 6.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.AutoStories,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                            text = "El Relato Bíblico",
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    Text(
                                        text = day.storyNarrative,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontSize = fontSizeSp.sp,
                                        lineHeight = (fontSizeSp * 1.55f).sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }

                    // 2. Contexto Histórico-Cultural y Enseñanza
                    if (day.historicalContext.isNotBlank() || day.spiritualLesson.isNotBlank()) {
                        item(key = "story_insights_${day.dayNumber}") {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 6.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    if (day.historicalContext.isNotBlank()) {
                                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                                            ) {
                                                Icon(
                                                    Icons.Default.MenuBook,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                                Text(
                                                    text = "Contexto Histórico y Cultural",
                                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                            Text(
                                                text = day.historicalContext,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                lineHeight = 20.sp
                                            )
                                        }
                                    }

                                    if (day.spiritualLesson.isNotBlank()) {
                                        if (day.historicalContext.isNotBlank()) {
                                            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                                        }
                                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                                            ) {
                                                Icon(
                                                    Icons.Default.Lightbulb,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.secondary,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                                Text(
                                                    text = "Enseñanza para Memorizar",
                                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                                    color = MaterialTheme.colorScheme.secondary
                                                )
                                            }
                                            Text(
                                                text = day.spiritualLesson,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                lineHeight = 20.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // 3. Encabezado de Texto Bíblico de Referencia
                    item(key = "reference_header_${day.dayNumber}") {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { isVersesExpanded = !isVersesExpanded }
                                    .padding(horizontal = 14.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        Icons.Default.MenuBook,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Column {
                                        Text(
                                            text = if (day.storyNarrative.isNotBlank()) "Texto Bíblico de Referencia" else "Pasajes Bíblicos",
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "${day.passagesSummary} ($selectedVersion)",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        onClick = { showVersionSelector = true },
                                        shape = RoundedCornerShape(16.dp),
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                        modifier = Modifier.padding(end = 2.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                                        ) {
                                            Text(
                                                text = selectedVersion,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Icon(
                                                Icons.Default.ArrowDropDown,
                                                contentDescription = "Seleccionar versión",
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                    IconButton(
                                        onClick = {
                                            onOpenInFullBible(day.primaryBookId, day.primaryChapter, day.primaryVerse)
                                            onDismiss()
                                        }
                                    ) {
                                        Icon(
                                            Icons.Default.OpenInNew,
                                            contentDescription = "Abrir en la Biblia",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                    IconButton(onClick = { isVersesExpanded = !isVersesExpanded }) {
                                        Icon(
                                            if (isVersesExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = if (isVersesExpanded) "Colapsar" else "Expandir",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // 4. Versículos Bíblicos (desplegables si es plan de historias, o abiertos si está expandido)
                    if (isVersesExpanded) {
                        segmentsData.forEach { segData ->
                            item(key = "header_${segData.segment.bookId}_${segData.segment.chapter}") {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        Icons.Default.MenuBook,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = "${segData.segment.bookName} ${segData.segment.chapter}",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }

                        items(segData.verses, key = { "v_${it.bookId}_${it.chapter}_${it.verseNumber}" }) { verse ->
                            val annotated = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = (fontSizeSp * 0.75f).sp,
                                        baselineShift = BaselineShift.Superscript
                                    )
                                ) {
                                    append("${verse.verseNumber} ")
                                }
                                append(OfflineBibleManager.cleanVerseText(verse.text))
                            }

                            Text(
                                text = annotated,
                                fontSize = fontSizeSp.sp,
                                lineHeight = (fontSizeSp * 1.55f).sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp)
                            )
                        }

                        item(key = "divider_${segData.segment.bookId}_${segData.segment.chapter}") {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                    }

                    // YouVersion Style Completion Card at bottom of reading
                    item(key = "completion_card") {
                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isCompleted) {
                                    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                                } else {
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                                }
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = if (isCompleted) Color(0xFF10B981) else MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(52.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = if (isCompleted) "¡Día ${day.dayNumber} Completado!" else "¡Has llegado al final de la lectura!",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center
                                )

                                Text(
                                    text = if (isCompleted) {
                                        "Tu progreso ha sido guardado. Puedes continuar con el siguiente día."
                                    } else {
                                        "Presiona el botón a continuación para registrar tu avance de hoy."
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )

                                // Main Mark-As-Completed Button
                                Button(
                                    onClick = { onToggleCompleted(day.dayNumber) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isCompleted) Color(0xFF10B981) else MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(20.dp))
                                        Text(
                                            text = if (isCompleted) "✔ Día ${day.dayNumber} Completado" else "Completar Día ${day.dayNumber}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                    }
                                }

                                // Next day button (if available)
                                if (onNextDay != null && day.dayNumber < 365) {
                                    OutlinedButton(
                                        onClick = onNextDay,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(46.dp),
                                        shape = RoundedCornerShape(14.dp)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Text(
                                                "Continuar al Día ${day.dayNumber + 1}",
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 14.sp
                                            )
                                            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                                        }
                                    }
                                }

                                // Open in full reader link
                                TextButton(
                                    onClick = {
                                        onDismiss()
                                        onOpenInFullBible(day.primaryBookId, day.primaryChapter, day.primaryVerse)
                                    }
                                ) {
                                    Text(
                                        "Abrir en la Biblia completa",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

    if (showVersionSelector) {
        com.example.ui.reader.components.BibleVersionSelectorDialog(
            currentVersion = selectedVersion,
            onSelectVersion = { version ->
                selectedVersion = version
                scope.launch { prefsRepo.updateBibleVersion(version) }
            },
            downloadStates = offlineStates,
            onDownloadVersion = { version ->
                scope.launch {
                    com.example.data.bible.OfflineBibleDownloadManager.downloadVersion(readerDao, version)
                }
            },
            onDismiss = { showVersionSelector = false }
        )
    }
}

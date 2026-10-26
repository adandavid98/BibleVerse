package com.example.ui.screens.components

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.bible.BibleReadingPlanCatalog
import com.example.data.bible.BibleTextSanitizer
import com.example.data.bible.ReadingPlanDay
import com.example.data.bible.ReadingPlanType
import com.example.data.model.CustomPlanDayEntity
import com.example.data.model.CustomPlanWithDays
import com.example.data.model.CustomReadingPlanEntity
import com.example.data.bible.CustomPlanGenerator
import com.example.data.preferences.ReadingPlanPreferences
import com.example.ui.viewmodel.BibleViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingPlansBottomSheet(
    viewModel: BibleViewModel? = null,
    initialTab: Int = 0,
    onNavigateToReader: (bookId: Int, chapter: Int, verse: Int) -> Unit,
    onStartPlanSession: (com.example.ui.reader.model.ReadingPlanSession) -> Unit = {},
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current
    val preferences = remember { ReadingPlanPreferences(context) }
    val progress by preferences.progressFlow.collectAsState()

    val launchCatalogDay = { day: ReadingPlanDay, planType: ReadingPlanType ->
        val readSegments = preferences.getDayReadSegments(planType.name, day.dayNumber)
        val lastSeg = preferences.getLastReadSegment(planType.name, day.dayNumber)
        val segments = day.passages.ifEmpty {
            listOf(
                com.example.data.bible.PlanPassageSegment(
                    day.primaryBookId,
                    com.example.data.bible.BibleCatalog.books.getOrNull(day.primaryBookId - 1)?.name ?: "Libro",
                    day.primaryChapter
                )
            )
        }
        val startIdx = if (lastSeg in segments.indices) lastSeg else (segments.indices.firstOrNull { it !in readSegments } ?: 0)
        val session = com.example.ui.reader.model.ReadingPlanSession(
            planId = planType.name,
            planTitle = planType.title,
            dayNumber = day.dayNumber,
            dayTitle = day.title,
            segments = segments,
            currentSegmentIndex = startIdx,
            readSegmentIndices = readSegments,
            isCustomPlan = false,
            storyNarrative = null
        )
        preferences.setLastReadDay(planType, day.dayNumber)
        onDismiss()
        onStartPlanSession(session)
    }

    val launchCustomDay = { planWithDays: CustomPlanWithDays, dayEntity: CustomPlanDayEntity ->
        val passages = CustomPlanGenerator.deserializeSegmentsFromJson(dayEntity.passagesJson).ifEmpty {
            listOf(
                com.example.data.bible.PlanPassageSegment(
                    dayEntity.primaryBookId,
                    com.example.data.bible.BibleCatalog.books.getOrNull(dayEntity.primaryBookId - 1)?.name ?: "Libro",
                    dayEntity.primaryChapter
                )
            )
        }
        val planKey = "CUSTOM_${dayEntity.planId}"
        val readSegments = preferences.getDayReadSegments(planKey, dayEntity.dayNumber)
        val lastSeg = preferences.getLastReadSegment(planKey, dayEntity.dayNumber)
        val startIdx = if (lastSeg in passages.indices) lastSeg else (passages.indices.firstOrNull { it !in readSegments } ?: 0)
        val session = com.example.ui.reader.model.ReadingPlanSession(
            planId = dayEntity.planId,
            planTitle = planWithDays.plan.title,
            dayNumber = dayEntity.dayNumber,
            dayTitle = "Día ${dayEntity.dayNumber}: ${dayEntity.passageSummary}",
            segments = passages,
            currentSegmentIndex = startIdx,
            readSegmentIndices = readSegments,
            isCustomPlan = true
        )
        onDismiss()
        onStartPlanSession(session)
    }

    // Custom Plans state from Room Database
    val activeCustomPlans by (viewModel?.activeCustomPlans ?: remember { MutableStateFlow(emptyList()) }).collectAsState()
    val completedCustomPlans by (viewModel?.completedCustomPlans ?: remember { MutableStateFlow(emptyList()) }).collectAsState()

    // Navigation state: null = Lobby de planes (YouVersion style); Non-null = Ventana completa de detalle de plan
    var detailedCatalogPlanType by remember { mutableStateOf<ReadingPlanType?>(null) }
    var showHistoryView by remember { mutableStateOf(false) }

    // Filter pill for the Lobby: 0: Todos, 1: Mis Planes, 2: Catálogo, 3: Historial
    var selectedLobbyFilter by remember(initialTab) { mutableIntStateOf(if (initialTab == 2) 3 else initialTab) }

    // Dialogs state
    var showCreatePlanDialog by remember { mutableStateOf(false) }
    var selectedPlanIdForDetail by remember { mutableStateOf<String?>(null) }
    val selectedPlanForDetail = remember(selectedPlanIdForDetail, activeCustomPlans, completedCustomPlans) {
        selectedPlanIdForDetail?.let { id ->
            activeCustomPlans.find { it.plan.id == id } ?: completedCustomPlans.find { it.plan.id == id }
        }
    }
    var celebrationPlan by remember { mutableStateOf<CustomReadingPlanEntity?>(null) }
    var activeReadingDay by remember { mutableStateOf<ReadingPlanDay?>(null) }
    var showResetCatalogConfirmDialog by remember { mutableStateOf<ReadingPlanType?>(null) }

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
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // =========================================================================
                // CASO 1: VENTANA COMPLETA DE DESGLOSE DE DÍAS (CATÁLOGO CANÓNICO)
                // =========================================================================
                if (detailedCatalogPlanType != null) {
                    val viewingPlanType = detailedCatalogPlanType!!
                    val planDays = remember(viewingPlanType) { BibleReadingPlanCatalog.getPlan(viewingPlanType) }
                    val isStoriesPlan = viewingPlanType == ReadingPlanType.BIBLE_STORIES
                    var searchQuery by remember { mutableStateOf("") }

                    val filteredCatalogDays = remember(planDays, isStoriesPlan, searchQuery) {
                        if (!isStoriesPlan || searchQuery.isBlank()) {
                            planDays
                        } else {
                            val queryClean = BibleTextSanitizer.removeAccents(searchQuery.trim()).lowercase()
                            planDays.filter { day ->
                                val titleClean = BibleTextSanitizer.removeAccents(day.title).lowercase()
                                val summaryClean = BibleTextSanitizer.removeAccents(day.passagesSummary).lowercase()
                                titleClean.contains(queryClean) || summaryClean.contains(queryClean)
                            }
                        }
                    }

                    val listState = rememberLazyListState(
                        initialFirstVisibleItemIndex = (preferences.getLastReadDay(viewingPlanType) - 1).coerceAtLeast(0)
                    )

                    val isPlanActive = progress.activePlanType == viewingPlanType
                    val completedCount = if (isPlanActive) progress.totalCompleted else 0
                    val totalDays = planDays.size.coerceAtLeast(1)
                    val percent = (completedCount.toFloat() / totalDays).coerceIn(0f, 1f)

                    // Header de la Ventana Completa de Detalle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f, fill = false),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            IconButton(
                                onClick = { detailedCatalogPlanType = null },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Regresar al catálogo")
                            }
                            Column {
                                Text(
                                    text = viewingPlanType.title,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = if (isPlanActive) "Plan activo • Día ${preferences.getLastReadDay(viewingPlanType)} de $totalDays" else "${viewingPlanType.description} • $totalDays días",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        if (!isPlanActive) {
                            FilledTonalButton(
                                onClick = {
                                    preferences.setPlanType(viewingPlanType)
                                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                },
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Activar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        } else {
                            IconButton(
                                onClick = { showResetCatalogConfirmDialog = viewingPlanType },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = "Reiniciar plan", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Barra de Progreso del Plan
                    LinearProgressIndicator(
                        progress = { percent },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = Color(0xFFFF5252),
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    // Buscador de Historias (si es Historias de la Biblia)
                    if (isStoriesPlan) {
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            placeholder = {
                                Text("Buscar en 365 historias por título o palabra...", fontSize = 13.sp)
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(Icons.Default.Clear, contentDescription = "Limpiar búsqueda")
                                    }
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                            ),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Lista de Días Desglosados con tarjetas compactas garantizadas
                    if (isStoriesPlan && filteredCatalogDays.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No se encontraron historias para \"$searchQuery\"",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(top = 4.dp, bottom = 80.dp)
                        ) {
                            items(filteredCatalogDays, key = { "${viewingPlanType.name}_${it.dayNumber}" }) { day ->
                                val isCompleted = if (isPlanActive) progress.completedDays.contains(day.dayNumber) else false
                                val isCurrentDay = isPlanActive && (day.dayNumber == preferences.getLastReadDay(viewingPlanType))
                                val readSegments = preferences.getDayReadSegments(viewingPlanType.name, day.dayNumber)
                                val totalSegments = day.passages.size.coerceAtLeast(1)
                                val isPartial = !isCompleted && readSegments.isNotEmpty()

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .heightIn(min = 56.dp, max = 80.dp)
                                        .clickable {
                                            if (isStoriesPlan) {
                                                preferences.setLastReadDay(ReadingPlanType.BIBLE_STORIES, day.dayNumber)
                                                activeReadingDay = day
                                            } else {
                                                launchCatalogDay(day, viewingPlanType)
                                            }
                                        },
                                    shape = RoundedCornerShape(14.dp),
                                    border = if (isCurrentDay && !isCompleted) {
                                        BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
                                    } else null,
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isCompleted) {
                                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
                                        } else if (isCurrentDay) {
                                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
                                        } else {
                                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                                        }
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .padding(horizontal = 12.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Checkbox(
                                            checked = isCompleted,
                                            onCheckedChange = {
                                                haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                                if (!isPlanActive) {
                                                    preferences.setPlanType(viewingPlanType)
                                                }
                                                preferences.toggleDayCompleted(day.dayNumber)
                                            },
                                            colors = CheckboxDefaults.colors(
                                                checkedColor = MaterialTheme.colorScheme.primary
                                            )
                                        )

                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .wrapContentHeight()
                                                .padding(horizontal = 8.dp),
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = day.title,
                                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                                    color = if (isCompleted) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    modifier = Modifier.weight(1f, fill = false)
                                                )
                                                if (isCurrentDay && !isCompleted) {
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                    Surface(
                                                        shape = RoundedCornerShape(6.dp),
                                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                                    ) {
                                                        Text(
                                                            text = if (isStoriesPlan) "Siguiente" else "Actual",
                                                            fontSize = 10.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = MaterialTheme.colorScheme.primary,
                                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                        )
                                                    }
                                                }
                                                if (isPartial) {
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                    Surface(
                                                        shape = RoundedCornerShape(6.dp),
                                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                                    ) {
                                                        Text(
                                                            text = "${readSegments.size}/$totalSegments caps",
                                                            fontSize = 10.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = MaterialTheme.colorScheme.primary,
                                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                        )
                                                    }
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = day.passagesSummary,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = if (isCompleted) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f) else MaterialTheme.colorScheme.primary,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }

                                        IconButton(
                                            onClick = {
                                                if (isStoriesPlan) {
                                                    preferences.setLastReadDay(ReadingPlanType.BIBLE_STORIES, day.dayNumber)
                                                    activeReadingDay = day
                                                } else {
                                                    launchCatalogDay(day, viewingPlanType)
                                                }
                                            },
                                            modifier = Modifier.size(36.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.ArrowForward,
                                                contentDescription = "Leer pasaje",
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }
                            item {
                                Spacer(modifier = Modifier.height(32.dp))
                            }
                        }
                    }

                // =========================================================================
                // CASO 2: VENTANA COMPLETA DE HISTORIAL DE PLANES COMPLETADOS
                // =========================================================================
                } else if (showHistoryView) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            IconButton(onClick = { showHistoryView = false }, modifier = Modifier.size(36.dp)) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                            }
                            Column {
                                Text(
                                    text = "Historial de Planes",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${completedCustomPlans.size} planes completados",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (completedCustomPlans.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Aún no has completado planes de lectura.\n¡Empieza uno hoy y persevera hasta la meta!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(top = 4.dp, bottom = 80.dp)
                        ) {
                            items(completedCustomPlans, key = { it.plan.id }) { planWithDays ->
                                CompletedPlanCard(
                                    planWithDays = planWithDays,
                                    onRestart = {
                                        viewModel?.restartCustomPlan(planWithDays.plan.id)
                                    },
                                    onDelete = {
                                        viewModel?.deleteCustomPlan(planWithDays.plan.id)
                                    }
                                )
                            }
                        }
                    }

                // =========================================================================
                // CASO 3: LOBBY PRINCIPAL DE PLANES (ESTILO YOUVERSION: BANNERS HORIZONTALES)
                // =========================================================================
                } else {
                    // Header Principal
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f, fill = false),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            IconButton(onClick = onDismiss, modifier = Modifier.size(36.dp)) {
                                Icon(Icons.Default.Close, contentDescription = "Cerrar planes")
                            }
                            Column {
                                Text(
                                    text = "Planes de Lectura",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Explora y continúa tus planes",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        FilledTonalButton(
                            onClick = { showCreatePlanDialog = true },
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Crear", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Barra Header de Filtro / Categorías (Pills YouVersion)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val filterLabels = listOf(
                            0 to "Todos",
                            1 to "Mis Planes ${if (activeCustomPlans.isNotEmpty()) "(${activeCustomPlans.size})" else ""}",
                            2 to "Catálogo",
                            3 to "Historial ${if (completedCustomPlans.isNotEmpty()) "(${completedCustomPlans.size})" else ""}"
                        )
                        filterLabels.forEach { (id, label) ->
                            FilterChip(
                                selected = selectedLobbyFilter == id,
                                onClick = { selectedLobbyFilter = id },
                                label = { Text(label, fontSize = 12.sp, fontWeight = if (selectedLobbyFilter == id) FontWeight.Bold else FontWeight.Normal) },
                                shape = RoundedCornerShape(20.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Lista de Banners Horizontales a lo Largo (uno debajo del otro)
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        contentPadding = PaddingValues(top = 4.dp, bottom = 80.dp)
                    ) {
                        // -------------------------------------------------------------
                        // SECCIÓN 1: MIS PLANES ACTIVOS (Si el filtro lo incluye)
                        // -------------------------------------------------------------
                        if (selectedLobbyFilter == 0 || selectedLobbyFilter == 1) {
                            item {
                                Text(
                                    text = "MI PLAN ACTUAL",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 1.2.sp
                                    ),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            // Banner del Plan Activo Canónico
                            val activeCoverRes = when (progress.activePlanType) {
                                ReadingPlanType.TRADITIONAL -> R.drawable.plan_cover_classic
                                ReadingPlanType.CHRONOLOGICAL -> R.drawable.plan_cover_chronological
                                ReadingPlanType.BIBLE_STORIES -> R.drawable.plan_cover_stories
                            }

                            item {
                                ReadingPlanBannerCard(
                                    title = progress.activePlanType.title,
                                    subtitle = "Día ${preferences.getLastReadDay(progress.activePlanType)} de ${progress.totalDays} • ${progress.activePlanType.description}",
                                    coverDrawableRes = activeCoverRes,
                                    badgeText = "ACTIVO",
                                    progressPercentage = progress.progressPercentage,
                                    progressText = "${(progress.progressPercentage * 100).toInt()}%",
                                    onClick = {
                                        detailedCatalogPlanType = progress.activePlanType
                                    }
                                )
                            }

                            // Banners de Planes Personalizados Activos
                            if (activeCustomPlans.isNotEmpty()) {
                                items(activeCustomPlans, key = { it.plan.id }) { planWithDays ->
                                    ReadingPlanBannerCard(
                                        title = planWithDays.plan.title,
                                        subtitle = "${planWithDays.totalDays} días • ${planWithDays.completedDaysCount} completados",
                                        coverDrawableRes = R.drawable.plan_cover_custom,
                                        badgeText = "PERSONAL",
                                        progressPercentage = planWithDays.progressPercentage,
                                        progressText = "${(planWithDays.progressPercentage * 100).toInt()}%",
                                        onClick = {
                                            selectedPlanIdForDetail = planWithDays.plan.id
                                        }
                                    )
                                }
                            }
                        }

                        // -------------------------------------------------------------
                        // SECCIÓN 2: CATÁLOGO DE PLANES DISPONIBLES
                        // -------------------------------------------------------------
                        if (selectedLobbyFilter == 0 || selectedLobbyFilter == 2) {
                            item {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "CATÁLOGO DE PLANES",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 1.2.sp
                                    ),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            // 1. Plan Clásico
                            item {
                                ReadingPlanBannerCard(
                                    title = "Plan Clásico",
                                    subtitle = "Génesis a Apocalipsis en 365 días",
                                    coverDrawableRes = R.drawable.plan_cover_classic,
                                    badgeText = if (progress.activePlanType == ReadingPlanType.TRADITIONAL) "EN CURSO" else null,
                                    onClick = {
                                        detailedCatalogPlanType = ReadingPlanType.TRADITIONAL
                                    }
                                )
                            }

                            // 2. Plan Cronológico
                            item {
                                ReadingPlanBannerCard(
                                    title = "Plan Cronológico",
                                    subtitle = "Los eventos en su orden histórico • 365 días",
                                    coverDrawableRes = R.drawable.plan_cover_chronological,
                                    badgeText = if (progress.activePlanType == ReadingPlanType.CHRONOLOGICAL) "EN CURSO" else null,
                                    onClick = {
                                        detailedCatalogPlanType = ReadingPlanType.CHRONOLOGICAL
                                    }
                                )
                            }

                            // 3. Historias de la Biblia
                            item {
                                ReadingPlanBannerCard(
                                    title = "Historias de la Biblia",
                                    subtitle = "365 relatos canónicos únicos y destacados",
                                    coverDrawableRes = R.drawable.plan_cover_stories,
                                    badgeText = if (progress.activePlanType == ReadingPlanType.BIBLE_STORIES) "EN CURSO" else null,
                                    onClick = {
                                        detailedCatalogPlanType = ReadingPlanType.BIBLE_STORIES
                                    }
                                )
                            }

                            // 4. Crear Plan Personalizado
                            item {
                                ReadingPlanBannerCard(
                                    title = "Crear Plan Personalizado",
                                    subtitle = "Diseña tu plan eligiendo libros y capítulos a tu propio ritmo",
                                    coverDrawableRes = R.drawable.plan_cover_custom,
                                    badgeText = "NUEVO",
                                    onClick = {
                                        showCreatePlanDialog = true
                                    }
                                )
                            }
                        }

                        // -------------------------------------------------------------
                        // SECCIÓN 3: HISTORIAL Y LOGROS
                        // -------------------------------------------------------------
                        if (selectedLobbyFilter == 0 || selectedLobbyFilter == 3) {
                            item {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "HISTORIAL Y LOGROS",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 1.2.sp
                                    ),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            item {
                                ReadingPlanBannerCard(
                                    title = "Historial de Planes Completados",
                                    subtitle = if (completedCustomPlans.isEmpty()) "Comienza un plan y alcanza la meta" else "${completedCustomPlans.size} planes finalizados con éxito",
                                    coverDrawableRes = R.drawable.plan_cover_completed,
                                    badgeText = "🏆 100%",
                                    onClick = {
                                        showHistoryView = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal para historias con texto narrativo
    activeReadingDay?.let { day ->
        ReadingPlanDayReaderModal(
            day = day,
            isCompleted = progress.completedDays.contains(day.dayNumber),
            planType = detailedCatalogPlanType ?: progress.activePlanType,
            onToggleCompleted = { dayNum ->
                haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                preferences.toggleDayCompleted(dayNum)
            },
            onNextDay = {
                val targetType = detailedCatalogPlanType ?: progress.activePlanType
                val daysList = BibleReadingPlanCatalog.getPlan(targetType)
                val nextDayNum = day.dayNumber + 1
                val nextDay = daysList.find { it.dayNumber == nextDayNum }
                if (nextDay != null) {
                    activeReadingDay = nextDay
                    preferences.setLastReadDay(targetType, nextDayNum)
                }
            },
            onOpenInFullBible = { _, _, _ ->
                activeReadingDay = null
                launchCatalogDay(day, detailedCatalogPlanType ?: progress.activePlanType)
            },
            onDismiss = { activeReadingDay = null }
        )
    }

    // Detalle de Plan Personalizado
    selectedPlanForDetail?.let { planWithDays ->
        CustomPlanDaysDetailDialog(
            planWithDays = planWithDays,
            onDismiss = { selectedPlanIdForDetail = null },
            onToggleDay = { dayNumber, completed ->
                haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                viewModel?.togglePlanDay(
                    planId = planWithDays.plan.id,
                    dayNumber = dayNumber,
                    completed = completed,
                    onPlanCompleted = {
                        selectedPlanIdForDetail = null
                        celebrationPlan = planWithDays.plan
                    }
                )
            },
            onNavigateToReader = { bId, ch, v ->
                selectedPlanIdForDetail = null
                onDismiss()
                onNavigateToReader(bId, ch, v)
            },
            onStartPlanSession = { session ->
                selectedPlanIdForDetail = null
                onDismiss()
                onStartPlanSession(session)
            },
            onDeletePlan = {
                viewModel?.deleteCustomPlan(planWithDays.plan.id)
                selectedPlanIdForDetail = null
            },
            onRestartPlan = {
                viewModel?.restartCustomPlan(planWithDays.plan.id)
            }
        )
    }

    // Modal de Creación de Plan
    if (showCreatePlanDialog) {
        CreateCustomPlanDialog(
            onDismiss = { showCreatePlanDialog = false },
            onCreatePlan = { title, description, bookIds, mode, targetDays, chaptersPerDay ->
                viewModel?.createCustomPlan(
                    title = title,
                    description = description,
                    selectedBookIds = bookIds,
                    mode = mode,
                    targetDays = targetDays,
                    chaptersPerDay = chaptersPerDay,
                    onSuccess = {
                        showCreatePlanDialog = false
                    }
                )
            }
        )
    }

    // Celebración de Plan Completado
    celebrationPlan?.let { plan ->
        PlanCompletedCelebrationDialog(
            planTitle = plan.title,
            totalDays = plan.totalDays,
            onDismiss = { celebrationPlan = null }
        )
    }

    // Diálogo de Confirmación para Reiniciar Plan Canónico
    showResetCatalogConfirmDialog?.let { planType ->
        AlertDialog(
            onDismissRequest = { showResetCatalogConfirmDialog = null },
            title = { Text("¿Reiniciar ${planType.title}?") },
            text = { Text("Esta acción desmarcará todos los días completados y volverá al Día 1. ¿Deseas continuar?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        preferences.resetPlanProgress(planType)
                        showResetCatalogConfirmDialog = null
                    }
                ) {
                    Text("Reiniciar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetCatalogConfirmDialog = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

/**
 * Banner Card Horizontal con Fondo de Imagen Fotográfico estilo YouVersion.
 * Ocupa todo el ancho (a lo largo), apilado uno debajo del otro con gradiente lateral.
 */
@Composable
fun ReadingPlanBannerCard(
    title: String,
    subtitle: String,
    coverDrawableRes: Int,
    badgeText: String? = null,
    progressPercentage: Float? = null,
    progressText: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(112.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 1. Imagen de Portada Fotográfica
            Image(
                painter = painterResource(id = coverDrawableRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            // 2. Scrim Gradiente Horizontal de alto contraste (oscuro a la izquierda para el texto)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.90f),
                                Color.Black.copy(alpha = 0.72f),
                                Color.Black.copy(alpha = 0.40f)
                            )
                        )
                    )
            )

            // 3. Contenido
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    if (badgeText != null) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFFF5252) // Acento coral vibrante estilo YouVersion
                        ) {
                            Text(
                                text = badgeText.uppercase(),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.85f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (progressPercentage != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth(0.88f)
                        ) {
                            LinearProgressIndicator(
                                progress = { progressPercentage },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(5.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = Color(0xFFFF5252),
                                trackColor = Color.White.copy(alpha = 0.25f)
                            )
                            if (progressText != null) {
                                Text(
                                    text = progressText,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.85f),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun CompletedPlanCard(
    planWithDays: CustomPlanWithDays,
    onRestart: () -> Unit,
    onDelete: (() -> Unit)? = null
) {
    val completedDateStr = remember(planWithDays.plan.completedAt) {
        val date = Date(planWithDays.plan.completedAt ?: System.currentTimeMillis())
        val format = SimpleDateFormat("d 'de' MMMM, yyyy", Locale("es", "ES"))
        format.format(date)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFEF08A)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.EmojiEvents,
                            contentDescription = null,
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column {
                        Text(
                            text = planWithDays.plan.title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Completado el $completedDateStr",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFFD1FAE5)
                ) {
                    Text(
                        text = "100%",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF047857),
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                    )
                }
            }

            Text(
                text = "Cumpliste con éxito todos los ${planWithDays.totalDays} días de lectura.",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onRestart,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Volver a leer", fontSize = 12.sp)
                }
            }
        }
    }
}

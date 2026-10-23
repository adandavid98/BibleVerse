package com.example.ui.screens.components

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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

    val launchCatalogDay = { day: ReadingPlanDay ->
        val readSegments = preferences.getDayReadSegments(progress.activePlanType.name, day.dayNumber)
        val lastSeg = preferences.getLastReadSegment(progress.activePlanType.name, day.dayNumber)
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
        val isStories = progress.activePlanType == ReadingPlanType.BIBLE_STORIES
        val session = com.example.ui.reader.model.ReadingPlanSession(
            planId = progress.activePlanType.name,
            planTitle = progress.activePlanType.title,
            dayNumber = day.dayNumber,
            dayTitle = day.title,
            segments = segments,
            currentSegmentIndex = startIdx,
            readSegmentIndices = readSegments,
            isCustomPlan = false,
            storyNarrative = null
        )
        preferences.setLastReadDay(progress.activePlanType, day.dayNumber)
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

    // Tabs: 0: Mis Planes, 1: Catálogo, 2: Historial
    var selectedMainTab by remember(initialTab) { mutableIntStateOf(initialTab) }

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

    // Pre-defined catalog state
    val currentDays = remember(progress.activePlanType) {
        BibleReadingPlanCatalog.getPlan(progress.activePlanType)
    }

    val traditionalListState = rememberLazyListState(initialFirstVisibleItemIndex = (preferences.getLastReadDay(ReadingPlanType.TRADITIONAL) - 1).coerceAtLeast(0))
    val chronologicalListState = rememberLazyListState(initialFirstVisibleItemIndex = (preferences.getLastReadDay(ReadingPlanType.CHRONOLOGICAL) - 1).coerceAtLeast(0))
    val storiesListState = rememberLazyListState(initialFirstVisibleItemIndex = (preferences.getLastReadDay(ReadingPlanType.BIBLE_STORIES) - 1).coerceAtLeast(0))

    val currentListState = when (progress.activePlanType) {
        ReadingPlanType.TRADITIONAL -> traditionalListState
        ReadingPlanType.CHRONOLOGICAL -> chronologicalListState
        ReadingPlanType.BIBLE_STORIES -> storiesListState
    }

    var searchQuery by remember { mutableStateOf("") }
    val isStoriesPlan = progress.activePlanType == ReadingPlanType.BIBLE_STORIES

    val filteredCatalogDays = remember(currentDays, isStoriesPlan, searchQuery) {
        if (!isStoriesPlan || searchQuery.isBlank()) {
            currentDays
        } else {
            val queryClean = BibleTextSanitizer.removeAccents(searchQuery.trim()).lowercase()
            currentDays.filter { day ->
                val titleClean = BibleTextSanitizer.removeAccents(day.title).lowercase()
                val summaryClean = BibleTextSanitizer.removeAccents(day.passagesSummary).lowercase()
                titleClean.contains(queryClean) || summaryClean.contains(queryClean)
            }
        }
    }

    LaunchedEffect(progress.activePlanType) {
        val targetDay = preferences.getLastReadDay(progress.activePlanType)
        if (searchQuery.isBlank()) {
            val targetIndex = (targetDay - 1).coerceIn(0, (currentDays.size - 1).coerceAtLeast(0))
            if (currentListState.firstVisibleItemIndex != targetIndex) {
                currentListState.scrollToItem(targetIndex)
            }
        } else {
            val indexInFiltered = filteredCatalogDays.indexOfFirst { it.dayNumber == targetDay }
            if (indexInFiltered >= 0 && currentListState.firstVisibleItemIndex != indexInFiltered) {
                currentListState.scrollToItem(indexInFiltered)
            }
        }
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
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Header Row
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
                            Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                        }
                        Column {
                            Text(
                                text = "Planes de Lectura",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Devoción diaria en las Escrituras",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
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

                // Primary Tabs: Mis Planes | Catálogo | Historial
                TabRow(
                    selectedTabIndex = selectedMainTab,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                ) {
                    Tab(
                        selected = selectedMainTab == 0,
                        onClick = { selectedMainTab = 0 },
                        text = {
                            Text(
                                text = "Mis Planes ${if (activeCustomPlans.isNotEmpty()) "(${activeCustomPlans.size})" else ""}",
                                fontWeight = if (selectedMainTab == 0) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    )
                    Tab(
                        selected = selectedMainTab == 1,
                        onClick = { selectedMainTab = 1 },
                        text = {
                            Text(
                                text = "Catálogo",
                                fontWeight = if (selectedMainTab == 1) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    )
                    Tab(
                        selected = selectedMainTab == 2,
                        onClick = { selectedMainTab = 2 },
                        text = {
                            Text(
                                text = "Historial ${if (completedCustomPlans.isNotEmpty()) "(${completedCustomPlans.size})" else ""}",
                                fontWeight = if (selectedMainTab == 2) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Tab Content
                when (selectedMainTab) {
                    // TAB 0: MIS PLANES ACTIVOS
                    0 -> {
                        if (activeCustomPlans.isEmpty()) {
                            // Empty State
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(20.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(24.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(14.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(60.dp)
                                                .clip(CircleShape)
                                                .background(MaterialTheme.colorScheme.primaryContainer),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                Icons.Default.CalendarMonth,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(32.dp)
                                            )
                                        }

                                        Text(
                                            text = "No tienes planes personalizados activos",
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )

                                        Text(
                                            text = "Puedes crear tu propio plan eligiendo cualquier libro de la Biblia y definiendo los días o capítulos a leer, o bien comenzar uno del Catálogo.",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                        )

                                        Button(
                                            onClick = { showCreatePlanDialog = true },
                                            shape = RoundedCornerShape(14.dp)
                                        ) {
                                            Icon(Icons.Default.Add, contentDescription = null)
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("Crear mi propio plan")
                                        }
                                    }
                                }
                            }
                        } else {
                            // Active Plans List
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(14.dp),
                                contentPadding = PaddingValues(top = 4.dp, bottom = 80.dp)
                            ) {
                                items(activeCustomPlans, key = { it.plan.id }) { planWithDays ->
                                    ActivePlanCard(
                                        planWithDays = planWithDays,
                                        onViewDetails = { selectedPlanIdForDetail = planWithDays.plan.id },
                                        onReadDay = { dayEntity ->
                                            launchCustomDay(planWithDays, dayEntity)
                                        },
                                        onToggleDay = { dayNumber, completed ->
                                            haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                            viewModel?.togglePlanDay(
                                                planId = planWithDays.plan.id,
                                                dayNumber = dayNumber,
                                                completed = completed,
                                                onPlanCompleted = {
                                                    celebrationPlan = planWithDays.plan
                                                }
                                            )
                                        },
                                        onDeletePlan = {
                                            viewModel?.deleteCustomPlan(planWithDays.plan.id)
                                        },
                                        onRestartPlan = {
                                            viewModel?.restartCustomPlan(planWithDays.plan.id)
                                        }
                                    )
                                }
                                item {
                                    Spacer(modifier = Modifier.height(32.dp))
                                }
                            }
                        }
                    }

                    // TAB 1: CATÁLOGO DE PLANES PREDETERMINADOS (IDÉNTICO A PRODUCCIÓN)
                    1 -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            // Selector visual de planes del catálogo (Propuesta 3: 3 tarjetas)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                val catalogOptions = listOf(
                                    Triple(ReadingPlanType.TRADITIONAL, "Clásico", "365 días"),
                                    Triple(ReadingPlanType.CHRONOLOGICAL, "Cronológico", "365 días"),
                                    Triple(ReadingPlanType.BIBLE_STORIES, "Historias", "365 relatos")
                                )

                                catalogOptions.forEach { (type, title, subtitle) ->
                                    val isSelected = progress.activePlanType == type
                                    val planProg = progress.plans[type] ?: com.example.data.preferences.SinglePlanProgress(type)
                                    val icon = when (type) {
                                        ReadingPlanType.TRADITIONAL -> Icons.Filled.MenuBook
                                        ReadingPlanType.CHRONOLOGICAL -> Icons.Filled.History
                                        ReadingPlanType.BIBLE_STORIES -> Icons.Filled.AutoStories
                                    }

                                    Card(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(14.dp))
                                            .clickable { preferences.setPlanType(type) },
                                        shape = RoundedCornerShape(14.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = if (isSelected)
                                                MaterialTheme.colorScheme.primaryContainer
                                            else
                                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                        ),
                                        border = if (isSelected)
                                            BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
                                        else
                                            BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f))
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 8.dp, vertical = 10.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Surface(
                                                    shape = CircleShape,
                                                    color = if (isSelected)
                                                        MaterialTheme.colorScheme.primary
                                                    else
                                                        MaterialTheme.colorScheme.surfaceVariant,
                                                    modifier = Modifier.size(28.dp)
                                                ) {
                                                    Box(contentAlignment = Alignment.Center) {
                                                        Icon(
                                                            imageVector = icon,
                                                            contentDescription = null,
                                                            tint = if (isSelected)
                                                                MaterialTheme.colorScheme.onPrimary
                                                            else
                                                                MaterialTheme.colorScheme.onSurfaceVariant,
                                                            modifier = Modifier.size(16.dp)
                                                        )
                                                    }
                                                }

                                                if (isSelected) {
                                                    Icon(
                                                        imageVector = Icons.Filled.CheckCircle,
                                                        contentDescription = "Seleccionado",
                                                        tint = MaterialTheme.colorScheme.primary,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }

                                            Spacer(modifier = Modifier.height(8.dp))

                                            Text(
                                                text = title,
                                                style = MaterialTheme.typography.labelLarge.copy(
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold
                                                ),
                                                color = if (isSelected)
                                                    MaterialTheme.colorScheme.onPrimaryContainer
                                                else
                                                    MaterialTheme.colorScheme.onSurface,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )

                                            Text(
                                                text = subtitle,
                                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                maxLines = 1
                                            )

                                            Spacer(modifier = Modifier.height(6.dp))

                                            LinearProgressIndicator(
                                                progress = { planProg.progressPercentage },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(4.dp)
                                                    .clip(RoundedCornerShape(2.dp)),
                                                color = if (isSelected)
                                                    MaterialTheme.colorScheme.primary
                                                else
                                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                                            )

                                            Spacer(modifier = Modifier.height(4.dp))

                                            Text(
                                                text = "${(planProg.progressPercentage * 100).toInt()}%",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 10.sp
                                                ),
                                                color = if (isSelected)
                                                    MaterialTheme.colorScheme.primary
                                                else
                                                    MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            val isCatalogPlanCompleted = progress.totalCompleted >= progress.totalDays && progress.totalDays > 0

                            if (isCatalogPlanCompleted) {
                                // 100% Completion Celebration Banner
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFFFEF3C7)
                                    ),
                                    border = BorderStroke(1.5.dp, Color(0xFFF59E0B))
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
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(38.dp)
                                                        .clip(CircleShape)
                                                        .background(Color(0xFFFDE68A)),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(
                                                        Icons.Default.EmojiEvents,
                                                        contentDescription = null,
                                                        tint = Color(0xFFD97706),
                                                        modifier = Modifier.size(22.dp)
                                                    )
                                                }
                                                Column {
                                                    Text(
                                                        text = "¡Plan Completado al 100%! 🏆",
                                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                                        color = Color(0xFF92400E)
                                                    )
                                                    Text(
                                                        text = "${progress.totalDays} de ${progress.totalDays} ${if (isStoriesPlan) "historias leídas" else "días leídos"}",
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = Color(0xFFB45309)
                                                    )
                                                }
                                            }

                                            Surface(
                                                shape = RoundedCornerShape(8.dp),
                                                color = Color(0xFFD1FAE5)
                                            ) {
                                                Text(
                                                    text = "100%",
                                                    fontWeight = FontWeight.Black,
                                                    style = MaterialTheme.typography.labelMedium,
                                                    color = Color(0xFF047857),
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                                )
                                            }
                                        }

                                        LinearProgressIndicator(
                                            progress = { 1.0f },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(8.dp)
                                                .clip(RoundedCornerShape(4.dp)),
                                            color = Color(0xFF10B981),
                                            trackColor = Color(0xFFE5E7EB)
                                        )

                                        Text(
                                            text = "¡Gloria a Dios! Has culminado con fidelidad todo el plan. Puedes reiniciar el plan para volver a leerlo desde el Día 1 cuando lo desees.",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF78350F)
                                        )

                                        Button(
                                            onClick = { showResetCatalogConfirmDialog = progress.activePlanType },
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD97706))
                                        ) {
                                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.White)
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text("Reiniciar plan desde el Día 1", fontWeight = FontWeight.Bold, color = Color.White)
                                        }
                                    }
                                }
                            } else {
                                // Regular Progress Banner
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
                                    )
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = if (isStoriesPlan) "${progress.totalCompleted} de ${progress.totalDays} historias leídas" else "${progress.totalCompleted} de ${progress.totalDays} días leídos",
                                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                                            ) {
                                                Text(
                                                    text = "${(progress.progressPercentage * 100).toInt()}%",
                                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black),
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                                if (progress.totalCompleted > 0) {
                                                    IconButton(
                                                        onClick = { showResetCatalogConfirmDialog = progress.activePlanType },
                                                        modifier = Modifier.size(24.dp)
                                                    ) {
                                                        Icon(
                                                            Icons.Default.Refresh,
                                                            contentDescription = "Reiniciar plan",
                                                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                                            modifier = Modifier.size(16.dp)
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(8.dp))

                                        LinearProgressIndicator(
                                            progress = { progress.progressPercentage },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(8.dp)
                                                .clip(RoundedCornerShape(4.dp)),
                                            color = MaterialTheme.colorScheme.primary,
                                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                                        )
                                    }
                                }
                            }

                            // Search Bar para Historias (365 historias)
                            if (isStoriesPlan) {
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = searchQuery,
                                    onValueChange = { searchQuery = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    placeholder = {
                                        Text(
                                            "Buscar en 365 historias por título o palabra...",
                                            fontSize = 13.sp
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Search,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    },
                                    trailingIcon = {
                                        if (searchQuery.isNotEmpty()) {
                                            IconButton(onClick = { searchQuery = "" }) {
                                                Icon(
                                                    Icons.Default.Clear,
                                                    contentDescription = "Limpiar búsqueda"
                                                )
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

                            // Lista de días o estado de búsqueda vacía
                            if (isStoriesPlan && filteredCatalogDays.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.padding(24.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Search,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                            modifier = Modifier.size(40.dp)
                                        )
                                        Text(
                                            text = "No se encontraron historias para \"$searchQuery\"",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                        )
                                    }
                                }
                            } else {
                                LazyColumn(
                                    state = currentListState,
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    contentPadding = PaddingValues(top = 4.dp, bottom = 80.dp)
                                ) {
                                    items(filteredCatalogDays, key = { "${progress.activePlanType.name}_${it.dayNumber}" }) { day ->
                                        val isCompleted = progress.completedDays.contains(day.dayNumber)
                                        val isCurrentDay = day.dayNumber == preferences.getLastReadDay(progress.activePlanType)
                                        val readSegments = preferences.getDayReadSegments(progress.activePlanType.name, day.dayNumber)
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
                                                        launchCatalogDay(day)
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
                                                // Checkbox
                                                Checkbox(
                                                    checked = isCompleted,
                                                    onCheckedChange = {
                                                        haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                                        preferences.toggleDayCompleted(day.dayNumber)
                                                    },
                                                    colors = CheckboxDefaults.colors(
                                                        checkedColor = MaterialTheme.colorScheme.primary
                                                    )
                                                )

                                                // Title & Passage Info
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

                                                // Read arrow button
                                                IconButton(
                                                    onClick = {
                                                        if (isStoriesPlan) {
                                                            preferences.setLastReadDay(ReadingPlanType.BIBLE_STORIES, day.dayNumber)
                                                            activeReadingDay = day
                                                        } else {
                                                            launchCatalogDay(day)
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
                        }
                    }

                    // TAB 2: HISTORIAL DE PLANES COMPLETADOS
                    2 -> {
                        if (completedCustomPlans.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(20.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(24.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(14.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(60.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFFFEF08A)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                Icons.Default.EmojiEvents,
                                                contentDescription = null,
                                                tint = Color(0xFFD97706),
                                                modifier = Modifier.size(32.dp)
                                            )
                                        }

                                        Text(
                                            text = "Aún no has completado planes",
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )

                                        Text(
                                            text = "Cuando termines al 100% las lecturas de un plan, se guardará aquí con honor como testimonio de tu constancia devocional.",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                        )
                                    }
                                }
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
                                            selectedMainTab = 0
                                        },
                                        onDelete = {
                                            viewModel?.deleteCustomPlan(planWithDays.plan.id)
                                        }
                                    )
                                }
                                item {
                                    Spacer(modifier = Modifier.height(32.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal: Crear Plan Personalizado
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
                        selectedMainTab = 0 // Go to "Mis Planes"
                    }
                )
            }
        )
    }

    // Modal: Ver detalle de todos los días de un plan activo
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

    // Modal: Celebración de Plan Completado (100%)
    celebrationPlan?.let { plan ->
        PlanCompletedCelebrationDialog(
            planTitle = plan.title,
            totalDays = plan.totalDays,
            onDismiss = { celebrationPlan = null }
        )
    }

    // Modal: Visor devocional para Historias Bíblicas (relato, contexto histórico y enseñanza)
    activeReadingDay?.let { day ->
        ReadingPlanDayReaderModal(
            day = day,
            isCompleted = progress.completedDays.contains(day.dayNumber),
            planType = progress.activePlanType,
            onToggleCompleted = { dayNum ->
                haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                preferences.toggleDayCompleted(dayNum)
            },
            onNextDay = {
                val nextDayNum = day.dayNumber + 1
                val nextDay = currentDays.find { it.dayNumber == nextDayNum }
                if (nextDay != null) {
                    activeReadingDay = nextDay
                    preferences.setLastReadDay(progress.activePlanType, nextDayNum)
                }
            },
            onOpenInFullBible = { _, _, _ ->
                activeReadingDay = null
                launchCatalogDay(day)
            },
            onDismiss = { activeReadingDay = null }
        )
    }

    // Diálogo de confirmación para reiniciar plan de catálogo
    showResetCatalogConfirmDialog?.let { planToReset ->
        AlertDialog(
            onDismissRequest = { showResetCatalogConfirmDialog = null },
            icon = { Icon(Icons.Default.Refresh, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
            title = { Text("Reiniciar plan de lectura", fontWeight = FontWeight.Bold) },
            text = {
                Text("¿Estás seguro de que deseas reiniciar tu progreso en el plan «${planToReset.title}»? Se borrarán los días completados y comenzarás nuevamente desde el Día 1.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        preferences.resetPlanProgress(planToReset)
                        showResetCatalogConfirmDialog = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Sí, reiniciar")
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

@Composable
private fun ActivePlanCard(
    planWithDays: CustomPlanWithDays,
    onViewDetails: () -> Unit,
    onReadDay: (CustomPlanDayEntity) -> Unit,
    onToggleDay: (dayNumber: Int, completed: Boolean) -> Unit,
    onDeletePlan: () -> Unit,
    onRestartPlan: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    val nextDay = planWithDays.nextPendingDay ?: planWithDays.days.firstOrNull()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header Row: Title, Percent & Menu
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = planWithDays.plan.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${planWithDays.completedDaysCount} de ${planWithDays.totalDays} días • ${planWithDays.plan.description}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "${(planWithDays.progressPercentage * 100).toInt()}%",
                            fontWeight = FontWeight.Black,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Box {
                        IconButton(onClick = { showMenu = true }, modifier = Modifier.size(32.dp)) {
                            Icon(Icons.Default.MoreVert, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                            DropdownMenuItem(
                                text = { Text("Ver todos los días") },
                                onClick = {
                                    showMenu = false
                                    onViewDetails()
                                },
                                leadingIcon = { Icon(Icons.Default.List, contentDescription = null) }
                            )
                            DropdownMenuItem(
                                text = { Text("Reiniciar plan") },
                                onClick = {
                                    showMenu = false
                                    onRestartPlan()
                                },
                                leadingIcon = { Icon(Icons.Default.Refresh, contentDescription = null) }
                            )
                            DropdownMenuItem(
                                text = { Text("Eliminar plan", color = MaterialTheme.colorScheme.error) },
                                onClick = {
                                    showMenu = false
                                    onDeletePlan()
                                },
                                leadingIcon = { Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error) }
                            )
                        }
                    }
                }
            }

            // Progress Bar
            LinearProgressIndicator(
                progress = { planWithDays.progressPercentage },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )

            // Today's reading card (Tarjeta tipo botón)
            if (nextDay != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .heightIn(min = 56.dp, max = 80.dp)
                        .clickable { onReadDay(nextDay) },
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = nextDay.isCompleted,
                            onCheckedChange = { checked ->
                                onToggleDay(nextDay.dayNumber, checked)
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
                                    text = "Lectura de Hoy: Día ${nextDay.dayNumber}",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f, fill = false)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = "Actual",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = nextDay.passageSummary,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        IconButton(
                            onClick = { onReadDay(nextDay) },
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

            // Bottom Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onViewDetails) {
                    Text("Ver desglose completo de días (${planWithDays.totalDays})", style = MaterialTheme.typography.labelMedium)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.ChevronRight, contentDescription = null, modifier = Modifier.size(16.dp))
                }
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

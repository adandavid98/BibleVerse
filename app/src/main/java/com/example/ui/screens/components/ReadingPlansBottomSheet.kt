package com.example.ui.screens.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.bible.BibleReadingPlanCatalog
import com.example.data.bible.ReadingPlanDay
import com.example.data.bible.ReadingPlanType
import com.example.data.preferences.ReadingPlanPreferences
import kotlinx.coroutines.launch

import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingPlansBottomSheet(
    onNavigateToReader: (bookId: Int, chapter: Int, verse: Int) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val preferences = remember { ReadingPlanPreferences(context) }
    val progress by preferences.progressFlow.collectAsState()

    val currentDays = remember(progress.activePlanType) {
        BibleReadingPlanCatalog.getPlan(progress.activePlanType)
    }

    val listState = rememberLazyListState()
    var activeReadingDay by remember { mutableStateOf<ReadingPlanDay?>(null) }

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
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Column {
                        Text(
                            text = "Planes de Lectura Bíblica",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${progress.totalDays} Días • ${progress.activePlanType.title}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(onClick = onDismiss) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Plan Selector Tabs: Clásico vs Cronológico vs Historias
            val selectedTab = when (progress.activePlanType) {
                ReadingPlanType.TRADITIONAL -> 0
                ReadingPlanType.CHRONOLOGICAL -> 1
                ReadingPlanType.BIBLE_STORIES -> 2
            }
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
            ) {
                Tab(
                    selected = progress.activePlanType == ReadingPlanType.TRADITIONAL,
                    onClick = { preferences.setPlanType(ReadingPlanType.TRADITIONAL) },
                    text = {
                        Text(
                            "Clásico",
                            fontSize = 13.sp,
                            fontWeight = if (progress.activePlanType == ReadingPlanType.TRADITIONAL) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
                Tab(
                    selected = progress.activePlanType == ReadingPlanType.CHRONOLOGICAL,
                    onClick = { preferences.setPlanType(ReadingPlanType.CHRONOLOGICAL) },
                    text = {
                        Text(
                            "Cronológico",
                            fontSize = 13.sp,
                            fontWeight = if (progress.activePlanType == ReadingPlanType.CHRONOLOGICAL) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
                Tab(
                    selected = progress.activePlanType == ReadingPlanType.BIBLE_STORIES,
                    onClick = { preferences.setPlanType(ReadingPlanType.BIBLE_STORIES) },
                    text = {
                        Text(
                            "Historias",
                            fontSize = 13.sp,
                            fontWeight = if (progress.activePlanType == ReadingPlanType.BIBLE_STORIES) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Progress Banner
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
                            text = "${progress.totalCompleted} de ${progress.totalDays} días leídos",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${(progress.progressPercentage * 100).toInt()}%",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { progress.progressPercentage },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // "Continuar lectura de hoy" Quick Button
                    val nextDay = currentDays.firstOrNull { it.dayNumber == progress.nextPendingDay } ?: currentDays.first()
                    Button(
                        onClick = {
                            activeReadingDay = nextDay
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(18.dp))
                            Text(
                                "Leer hoy: Día ${nextDay.dayNumber} (${nextDay.passagesSummary})",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Month Jump Quick Pills (Mes 1 .. Mes 12)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(12) { monthIdx ->
                    val targetDay = monthIdx * 30 + 1
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                scope.launch {
                                    val index = (targetDay - 1).coerceIn(0, currentDays.size - 1)
                                    listState.animateScrollToItem(index)
                                }
                            },
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    ) {
                        Text(
                            text = "Mes ${monthIdx + 1}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 365 Days List
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                items(currentDays, key = { "${progress.activePlanType.name}_${it.dayNumber}" }) { day ->
                    val isCompleted = progress.completedDays.contains(day.dayNumber)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { activeReadingDay = day },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isCompleted) {
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                            }
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Checkbox
                            Checkbox(
                                checked = isCompleted,
                                onCheckedChange = { preferences.toggleDayCompleted(day.dayNumber) },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = MaterialTheme.colorScheme.primary
                                )
                            )

                            // Title & Passage Info
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 8.dp)
                            ) {
                                Text(
                                    text = day.title,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = if (isCompleted) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = day.passagesSummary,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (isCompleted) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f) else MaterialTheme.colorScheme.primary
                                )
                            }

                            // Read button
                            IconButton(
                                onClick = {
                                    activeReadingDay = day
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
            }
        }
    }
}

    // Isolated Daily Reader Sheet
    activeReadingDay?.let { dayToRead ->
        val isDayCompleted = progress.completedDays.contains(dayToRead.dayNumber)
        val nextDayNumber = dayToRead.dayNumber + 1
        val nextDayObj = currentDays.firstOrNull { it.dayNumber == nextDayNumber }

        ReadingPlanDayReaderModal(
            day = dayToRead,
            isCompleted = isDayCompleted,
            onToggleCompleted = { dayNum ->
                preferences.toggleDayCompleted(dayNum)
            },
            onNextDay = if (nextDayObj != null) {
                { activeReadingDay = nextDayObj }
            } else null,
            onOpenInFullBible = { bookId, chapter, verse ->
                activeReadingDay = null
                onDismiss()
                onNavigateToReader(bookId, chapter, verse)
            },
            onDismiss = { activeReadingDay = null }
        )
    }
}

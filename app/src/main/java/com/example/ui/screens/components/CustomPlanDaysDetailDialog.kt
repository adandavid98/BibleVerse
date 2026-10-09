package com.example.ui.screens.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.bible.CustomPlanGenerator
import com.example.data.bible.ReadingPlanDay
import com.example.data.bible.ReadingPlanType
import com.example.data.model.CustomPlanDayEntity
import com.example.data.model.CustomPlanWithDays

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPlanDaysDetailDialog(
    planWithDays: CustomPlanWithDays,
    onDismiss: () -> Unit,
    onToggleDay: (dayNumber: Int, completed: Boolean) -> Unit,
    onNavigateToReader: (bookId: Int, chapter: Int, verse: Int) -> Unit,
    onDeletePlan: () -> Unit,
    onRestartPlan: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    var showMenu by remember { mutableStateOf(false) }
    var activeReadingDayNumber by remember { mutableStateOf<Int?>(null) }
    val liveDayEntity = remember(activeReadingDayNumber, planWithDays) {
        activeReadingDayNumber?.let { dayNum ->
            planWithDays.days.find { it.dayNumber == dayNum }
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
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(onClick = onDismiss, modifier = Modifier.size(36.dp)) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                        }
                        Column {
                            Text(
                                text = planWithDays.plan.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${planWithDays.completedDaysCount} de ${planWithDays.totalDays} días completados • ${(planWithDays.progressPercentage * 100).toInt()}%",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Reiniciar plan desde el día 1") },
                                onClick = {
                                    showMenu = false
                                    onRestartPlan()
                                },
                                leadingIcon = { Icon(Icons.Default.Refresh, contentDescription = null) }
                            )
                            DropdownMenuItem(
                                text = { Text("Eliminar este plan") },
                                onClick = {
                                    showMenu = false
                                    onDeletePlan()
                                    onDismiss()
                                },
                                leadingIcon = { Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error) }
                            )
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
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Days List
                val nextDayNumber = planWithDays.nextPendingDay?.dayNumber ?: 1
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 32.dp)
                ) {
                    items(planWithDays.days.sortedBy { it.dayNumber }, key = { it.id }) { day ->
                        val isCurrentDay = day.dayNumber == nextDayNumber

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    activeReadingDayNumber = day.dayNumber
                                },
                            shape = RoundedCornerShape(14.dp),
                            border = if (isCurrentDay && !day.isCompleted) {
                                BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
                            } else null,
                            colors = CardDefaults.cardColors(
                                containerColor = if (day.isCompleted) {
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
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Checkbox
                                Checkbox(
                                    checked = day.isCompleted,
                                    onCheckedChange = { checked ->
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        onToggleDay(day.dayNumber, checked)
                                    },
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
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "Día ${day.dayNumber}",
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                            color = if (day.isCompleted) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface
                                        )
                                        if (isCurrentDay && !day.isCompleted) {
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
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = day.passageSummary,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (day.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f) else MaterialTheme.colorScheme.primary
                                    )
                                }

                                // Arrow button
                                IconButton(
                                    onClick = {
                                        activeReadingDayNumber = day.dayNumber
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

    // Modal de lectura del día para planes personalizados (con opción opcional de abrir en la Biblia completa)
    liveDayEntity?.let { dayEntity ->
        val passages = remember(dayEntity.passagesJson) {
            CustomPlanGenerator.deserializeSegmentsFromJson(dayEntity.passagesJson)
        }
        val readingPlanDay = remember(dayEntity.dayNumber, dayEntity.passageSummary, passages) {
            ReadingPlanDay(
                dayNumber = dayEntity.dayNumber,
                title = "Día ${dayEntity.dayNumber}: ${dayEntity.passageSummary}",
                passagesSummary = dayEntity.passageSummary,
                primaryBookId = dayEntity.primaryBookId,
                primaryChapter = dayEntity.primaryChapter,
                primaryVerse = 1,
                passages = passages
            )
        }
        val nextDayEntity = planWithDays.days.firstOrNull { it.dayNumber == dayEntity.dayNumber + 1 }

        ReadingPlanDayReaderModal(
            day = readingPlanDay,
            isCompleted = dayEntity.isCompleted,
            planType = ReadingPlanType.TRADITIONAL,
            onToggleCompleted = {
                onToggleDay(dayEntity.dayNumber, !dayEntity.isCompleted)
            },
            onNextDay = if (nextDayEntity != null) {
                {
                    activeReadingDayNumber = nextDayEntity.dayNumber
                }
            } else null,
            onOpenInFullBible = { bookId, chapter, verse ->
                activeReadingDayNumber = null
                onDismiss()
                onNavigateToReader(bookId, chapter, verse)
            },
            onDismiss = { activeReadingDayNumber = null }
        )
    }
}

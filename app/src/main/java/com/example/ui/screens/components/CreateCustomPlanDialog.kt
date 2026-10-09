package com.example.ui.screens.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.bible.BibleCatalog
import com.example.data.bible.CustomPlanGenerator
import com.example.data.bible.CustomPlanPresets
import com.example.data.model.PlanDistributionMode
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCustomPlanDialog(
    onDismiss: () -> Unit,
    onCreatePlan: (title: String, description: String, selectedBookIds: List<Int>, mode: PlanDistributionMode, targetDays: Int, chaptersPerDay: Int) -> Unit
) {
    // Selected Preset & Identity (starts clean with no preselection)
    var selectedPresetId by remember { mutableStateOf<String?>(null) }
    var planTitle by remember { mutableStateOf("") }
    var selectedBookIds by remember { mutableStateOf(emptySet<Int>()) }

    // Distribution Mode
    var distributionMode by remember { mutableStateOf(PlanDistributionMode.BY_TARGET_DAYS) }
    var targetDays by remember { mutableIntStateOf(30) }
    var chaptersPerDay by remember { mutableIntStateOf(2) }

    // Book Picker Expanded State
    var showBookPickerSheet by remember { mutableStateOf(false) }

    // Calculated total chapters
    val totalChapters = remember(selectedBookIds) {
        selectedBookIds.sumOf { id ->
            BibleCatalog.books.find { it.bookId == id }?.chaptersCount ?: 0
        }
    }

    // Dynamic duration preview
    val calculatedDays = remember(totalChapters, distributionMode, targetDays, chaptersPerDay) {
        if (totalChapters == 0) {
            0
        } else if (distributionMode == PlanDistributionMode.BY_TARGET_DAYS) {
            targetDays.coerceIn(1, totalChapters.coerceAtLeast(1))
        } else {
            val perDay = chaptersPerDay.coerceAtLeast(1)
            (totalChapters + perDay - 1) / perDay
        }
    }

    val calculatedAvgPerDay = remember(totalChapters, distributionMode, targetDays, chaptersPerDay) {
        if (totalChapters == 0) {
            "0"
        } else if (distributionMode == PlanDistributionMode.BY_TARGET_DAYS) {
            val days = targetDays.coerceIn(1, totalChapters.coerceAtLeast(1))
            val avg = totalChapters.toFloat() / days.toFloat()
            String.format(Locale.getDefault(), "%.1f", avg)
        } else {
            chaptersPerDay.toString()
        }
    }

    val estimatedEndDateString = remember(calculatedDays) {
        if (calculatedDays == 0) {
            "Sin libros seleccionados"
        } else {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_YEAR, calculatedDays)
            val format = SimpleDateFormat("d 'de' MMMM, yyyy", Locale("es", "ES"))
            format.format(cal.time)
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
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        IconButton(onClick = onDismiss, modifier = Modifier.size(36.dp)) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                        }
                        Column {
                            Text(
                                text = "Crear Plan de Lectura",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "A tu propio ritmo y libros seleccionados",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))

                // Scrollable Body
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Section 1: Presets Rápidos
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "PRESETS RÁPIDOS SUGERIDOS",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                            color = MaterialTheme.colorScheme.primary
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CustomPlanPresets.presets.forEach { preset ->
                                val isSelected = selectedPresetId == preset.id && selectedBookIds == preset.bookIds.toSet()
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable {
                                            if (isSelected) {
                                                // Toggling off the preset to return to clean state
                                                selectedPresetId = null
                                                planTitle = ""
                                                selectedBookIds = emptySet()
                                            } else {
                                                selectedPresetId = preset.id
                                                planTitle = preset.title
                                                selectedBookIds = preset.bookIds.toSet()
                                            }
                                        },
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary) else null
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(text = preset.emoji, fontSize = 16.sp)
                                        Text(
                                            text = preset.title,
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                            ),
                                            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Section 2: Título del Plan
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "NOMBRE DEL PLAN",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        OutlinedTextField(
                            value = planTitle,
                            onValueChange = {
                                planTitle = it
                                if (selectedPresetId != null) {
                                    val currentPreset = CustomPlanPresets.presets.find { p -> p.id == selectedPresetId }
                                    if (currentPreset?.title != it) {
                                        selectedPresetId = null
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Ej. Mi Plan Personalizado, Evangelios, etc.") },
                            singleLine = true,
                            leadingIcon = {
                                Icon(Icons.Default.Book, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            },
                            shape = RoundedCornerShape(14.dp)
                        )
                    }

                    // Section 3: Libros Seleccionados
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "LIBROS INCLUIDOS",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                                color = MaterialTheme.colorScheme.primary
                            )
                            TextButton(onClick = { showBookPickerSheet = true }) {
                                Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Personalizar libros", style = MaterialTheme.typography.labelMedium)
                            }
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${selectedBookIds.size} libros seleccionados",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = "$totalChapters capítulos",
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }

                                if (selectedBookIds.isEmpty()) {
                                    Text(
                                        text = "Ningún libro seleccionado aún. Toca 'Personalizar libros' para elegir o selecciona un preset sugerido arriba.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                } else {
                                    val bookNames = remember(selectedBookIds) {
                                        selectedBookIds.mapNotNull { id -> BibleCatalog.books.find { it.bookId == id }?.name }.take(6)
                                    }
                                    val hasMore = selectedBookIds.size > 6
                                    Text(
                                        text = bookNames.joinToString(", ") + if (hasMore) " y ${selectedBookIds.size - 6} más..." else "",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }

                    // Section 4: Ritmo y Distribución
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "RITMO Y DISTRIBUCIÓN",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                            color = MaterialTheme.colorScheme.primary
                        )

                        TabRow(
                            selectedTabIndex = if (distributionMode == PlanDistributionMode.BY_TARGET_DAYS) 0 else 1,
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            modifier = Modifier.clip(RoundedCornerShape(12.dp))
                        ) {
                            Tab(
                                selected = distributionMode == PlanDistributionMode.BY_TARGET_DAYS,
                                onClick = { distributionMode = PlanDistributionMode.BY_TARGET_DAYS },
                                text = { Text("Por días límite", fontWeight = FontWeight.Bold) }
                            )
                            Tab(
                                selected = distributionMode == PlanDistributionMode.BY_CHAPTERS_PER_DAY,
                                onClick = { distributionMode = PlanDistributionMode.BY_CHAPTERS_PER_DAY },
                                text = { Text("Capítulos por día", fontWeight = FontWeight.Bold) }
                            )
                        }

                        if (distributionMode == PlanDistributionMode.BY_TARGET_DAYS) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Duración deseada:", style = MaterialTheme.typography.bodyMedium)
                                    Text(
                                        text = "$targetDays días",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    listOf(15, 30, 60, 90, 180, 365).forEach { daysOption ->
                                        val isSel = targetDays == daysOption
                                        FilterChip(
                                            selected = isSel,
                                            onClick = { targetDays = daysOption },
                                            label = { Text("$daysOption d", fontSize = 12.sp) },
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                            }
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Capítulos cada día:", style = MaterialTheme.typography.bodyMedium)
                                    Text(
                                        text = "$chaptersPerDay caps / día",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    listOf(1, 2, 3, 4, 5).forEach { chOption ->
                                        val isSel = chaptersPerDay == chOption
                                        FilterChip(
                                            selected = isSel,
                                            onClick = { chaptersPerDay = chOption },
                                            label = { Text("$chOption cap${if (chOption > 1) "s" else ""}", fontSize = 12.sp) },
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                            }
                        }

                        // Summary calculation card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.Schedule,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Column {
                                    if (selectedBookIds.isEmpty()) {
                                        Text(
                                            text = "Selecciona libros para calcular el plan",
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "La duración se ajustará según los libros elegidos",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    } else {
                                        Text(
                                            text = "$calculatedDays días en total • ~$calculatedAvgPerDay caps/día",
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "Fecha estimada de finalización: $estimatedEndDateString",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Bottom Action Button with safe navigation bars padding
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 4.dp)
                        .padding(top = 8.dp, bottom = 36.dp),
                    color = Color.Transparent
                ) {
                    Button(
                        onClick = {
                            if (selectedBookIds.isNotEmpty() && planTitle.isNotBlank()) {
                                onCreatePlan(
                                    planTitle.trim(),
                                    "${selectedBookIds.size} libros • $totalChapters capítulos",
                                    selectedBookIds.toList(),
                                    distributionMode,
                                    targetDays,
                                    chaptersPerDay
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        enabled = selectedBookIds.isNotEmpty() && planTitle.isNotBlank(),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (selectedBookIds.isEmpty()) "Selecciona libros primero"
                                   else if (planTitle.isBlank()) "Ingresa un nombre para el plan"
                                   else "Crear y Comenzar Plan",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }

    // Modal Sheet: Custom Book Picker
    if (showBookPickerSheet) {
        CustomBookPickerModal(
            currentSelected = selectedBookIds,
            onDismiss = { showBookPickerSheet = false },
            onApply = { newSelection ->
                selectedBookIds = newSelection
                selectedPresetId = null
                if (planTitle.isBlank() && newSelection.isNotEmpty()) {
                    if (newSelection.size == 1) {
                        val singleName = BibleCatalog.books.find { it.bookId == newSelection.first() }?.name ?: ""
                        planTitle = "Lectura de $singleName"
                    } else {
                        planTitle = "Plan Personalizado (${newSelection.size} libros)"
                    }
                }
                showBookPickerSheet = false
            }
        )
    }
}

@Composable
private fun CustomBookPickerModal(
    currentSelected: Set<Int>,
    onDismiss: () -> Unit,
    onApply: (Set<Int>) -> Unit
) {
    var tempSelected by remember { mutableStateOf(currentSelected) }
    var selectedTestamentTab by remember { mutableIntStateOf(0) } // 0: Todos, 1: AT, 2: NT

    val displayedBooks = remember(selectedTestamentTab) {
        when (selectedTestamentTab) {
            1 -> BibleCatalog.books.filter { it.bookId <= 39 }
            2 -> BibleCatalog.books.filter { it.bookId > 39 }
            else -> BibleCatalog.books
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
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                        }
                        Text(
                            text = "Seleccionar Libros",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "${tempSelected.size} / 66",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Quick Select Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    OutlinedButton(
                        onClick = { tempSelected = (1..66).toSet() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 6.dp)
                    ) {
                        Text("Todos (66)", fontSize = 11.sp)
                    }
                    OutlinedButton(
                        onClick = { tempSelected = (1..39).toSet() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 6.dp)
                    ) {
                        Text("Solo AT (39)", fontSize = 11.sp)
                    }
                    OutlinedButton(
                        onClick = { tempSelected = (40..66).toSet() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 6.dp)
                    ) {
                        Text("Solo NT (27)", fontSize = 11.sp)
                    }
                    OutlinedButton(
                        onClick = { tempSelected = emptySet() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 6.dp)
                    ) {
                        Text("Limpiar", fontSize = 11.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Tabs: Todos, AT, NT
                TabRow(selectedTabIndex = selectedTestamentTab) {
                    Tab(selected = selectedTestamentTab == 0, onClick = { selectedTestamentTab = 0 }, text = { Text("Todos") })
                    Tab(selected = selectedTestamentTab == 1, onClick = { selectedTestamentTab = 1 }, text = { Text("Antiguo") })
                    Tab(selected = selectedTestamentTab == 2, onClick = { selectedTestamentTab = 2 }, text = { Text("Nuevo") })
                }

                // Books List
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp)
                ) {
                    items(displayedBooks, key = { it.bookId }) { book ->
                        val isChecked = tempSelected.contains(book.bookId)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    tempSelected = if (isChecked) {
                                        tempSelected - book.bookId
                                    } else {
                                        tempSelected + book.bookId
                                    }
                                }
                                .padding(vertical = 10.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = { checked ->
                                        tempSelected = if (checked) tempSelected + book.bookId else tempSelected - book.bookId
                                    }
                                )
                                Column {
                                    Text(
                                        text = book.name,
                                        fontWeight = if (isChecked) FontWeight.Bold else FontWeight.Normal,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = if (isChecked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "${book.chaptersCount} capítulos • ${book.category}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                    }
                }

                // Apply button with safe bottom padding
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 4.dp)
                        .padding(top = 8.dp, bottom = 36.dp),
                    color = Color.Transparent
                ) {
                    Button(
                        onClick = { onApply(tempSelected) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        contentPadding = PaddingValues(vertical = 12.dp),
                        enabled = tempSelected.isNotEmpty()
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Aplicar selección (${tempSelected.size} libros)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

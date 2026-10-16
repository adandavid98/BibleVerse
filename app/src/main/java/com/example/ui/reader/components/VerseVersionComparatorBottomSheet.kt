package com.example.ui.reader.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.bible.BibleCatalog
import com.example.data.bible.BibleVersion
import com.example.data.bible.BollsBibleApiService
import com.example.data.bible.OfflineBibleManager
import com.example.data.bible.OfflineBibleDownloadManager
import com.example.data.bible.VersionDownloadState
import com.example.data.local.BibleReaderDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed interface VersionTextState {
    object Loading : VersionTextState
    data class Success(val text: String) : VersionTextState
    data class Error(val message: String) : VersionTextState
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerseVersionComparatorBottomSheet(
    citation: String,
    bookId: Int,
    chapter: Int,
    verseNumbers: List<Int>,
    readerDao: BibleReaderDao?,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val downloadStates by OfflineBibleDownloadManager.downloadStates.collectAsState()

    // All available canonical versions in the app
    val allVersions = remember { BibleCatalog.versions }

    val prefs = remember(context) {
        context.getSharedPreferences("bible_app_settings", Context.MODE_PRIVATE)
    }

    // Initial list of compared versions, restoring saved preferences if available
    val initialComparedVersions = remember {
        val savedString = prefs.getString("comparator_active_versions", null)
        if (savedString != null) {
            val savedCodes = savedString.split(",").map { it.trim() }.filter { it.isNotEmpty() }
            val restored = savedCodes.mapNotNull { code ->
                allVersions.firstOrNull { it.code.equals(code, ignoreCase = true) }
            }
            restored
        } else {
            val downloaded = allVersions.filter { ver ->
                ver.code == "RVR1960" ||
                downloadStates[ver.code] is VersionDownloadState.Downloaded ||
                OfflineBibleDownloadManager.isAssetVersion(ver.code)
            }
            if (downloaded.isNotEmpty()) downloaded else listOfNotNull(allVersions.firstOrNull())
        }
    }

    // Active versions being compared (persisted across app restarts)
    var comparedVersions by remember {
        mutableStateOf(initialComparedVersions)
    }

    fun persistVersions(versions: List<BibleVersion>) {
        comparedVersions = versions
        prefs.edit().putString("comparator_active_versions", versions.joinToString(",") { it.code }).apply()
    }

    var showAddVersionDialog by remember { mutableStateOf(false) }

    // Track text state for each version code
    var versionsMap by remember {
        mutableStateOf<Map<String, VersionTextState>>(
            allVersions.associate { it.code to VersionTextState.Loading }
        )
    }

    LaunchedEffect(bookId, chapter, verseNumbers) {
        val sortedVerses = verseNumbers.sorted()
        if (sortedVerses.isEmpty()) return@LaunchedEffect

        allVersions.forEach { ver ->
            val code = ver.code
            scope.launch(Dispatchers.IO) {
                try {
                    val text = loadVerseTextForVersion(context, readerDao, bookId, chapter, sortedVerses, code)
                    withContext(Dispatchers.Main) {
                        versionsMap = versionsMap + (code to VersionTextState.Success(text))
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        versionsMap = versionsMap + (code to VersionTextState.Error("No se pudo cargar texto"))
                    }
                }
            }
        }
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { newState ->
            // Prevent downward swipe / fling from accidentally closing the bottom sheet
            newState != SheetValue.Hidden
        }
    )

    // Intercept downward drag/fling over content to prevent bottom sheet from moving down
    val noDragNestedScroll = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                // Consume unconsumed downward scroll so ModalBottomSheet never moves down
                return if (available.y > 0f) Offset(0f, available.y) else Offset.Zero
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                return if (available.y > 0f) Velocity(0f, available.y) else Velocity.Zero
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .nestedScroll(noDragNestedScroll)
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.CompareArrows,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Column {
                        Text(
                            text = "Comparador de Versiones",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = citation,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Copy all comparisons
                    IconButton(
                        onClick = {
                            val sb = StringBuilder()
                            sb.appendLine("=== $citation ===")
                            comparedVersions.forEach { ver ->
                                val state = versionsMap[ver.code]
                                if (state is VersionTextState.Success) {
                                    sb.appendLine("[${ver.shortName}] ${ver.name}")
                                    sb.appendLine(state.text)
                                    sb.appendLine()
                                }
                            }
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipboard.setPrimaryClip(ClipData.newPlainText("Comparación de Versiones", sb.toString()))
                            Toast.makeText(context, "Comparación completa copiada", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            Icons.Default.ContentCopy,
                            contentDescription = "Copiar todas",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f))
            Spacer(modifier = Modifier.height(12.dp))

            // Version Cards List
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                if (comparedVersions.isEmpty()) {
                    item(key = "empty_state") {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay versiones seleccionadas",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    items(comparedVersions, key = { it.code }) { ver ->
                        val isOffline = ver.code == "RVR1960" ||
                            downloadStates[ver.code] is VersionDownloadState.Downloaded ||
                            OfflineBibleDownloadManager.isAssetVersion(ver.code)
                        val state = versionsMap[ver.code] ?: VersionTextState.Loading

                        DismissibleVersionCard(
                            ver = ver,
                            citation = citation,
                            isOffline = isOffline,
                            state = state,
                            onRemove = {
                                persistVersions(comparedVersions.filter { it.code != ver.code })
                            },
                            onRetry = {
                                versionsMap = versionsMap + (ver.code to VersionTextState.Loading)
                                scope.launch(Dispatchers.IO) {
                                    try {
                                        val text = loadVerseTextForVersion(
                                            context,
                                            readerDao,
                                            bookId,
                                            chapter,
                                            verseNumbers.sorted(),
                                            ver.code
                                        )
                                        withContext(Dispatchers.Main) {
                                            versionsMap = versionsMap + (ver.code to VersionTextState.Success(text))
                                        }
                                    } catch (_: Exception) {
                                        withContext(Dispatchers.Main) {
                                            versionsMap = versionsMap + (ver.code to VersionTextState.Error("Reintento fallido"))
                                        }
                                    }
                                }
                            },
                            onCopy = {
                                if (state is VersionTextState.Success) {
                                    val textToCopy = "«${state.text}» - $citation (${ver.shortName})"
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    clipboard.setPrimaryClip(ClipData.newPlainText("Versículo ${ver.shortName}", textToCopy))
                                    Toast.makeText(context, "${ver.shortName} copiada", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.animateItem()
                        )
                    }
                }

                // "+ Agregar versión" button at the bottom of the list
                item(key = "add_version_button") {
                    val remainingVersions = allVersions.filter { ver ->
                        comparedVersions.none { it.code == ver.code }
                    }
                    if (remainingVersions.isNotEmpty()) {
                        OutlinedButton(
                            onClick = { showAddVersionDialog = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp, bottom = 12.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Agregar versión",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }

    // Modal to add versions back to the comparator (YouVersion style)
    if (showAddVersionDialog) {
        val remainingVersions = allVersions.filter { ver ->
            comparedVersions.none { it.code == ver.code }
        }

        AlertDialog(
            onDismissRequest = { showAddVersionDialog = false },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Agregar versión",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            text = {
                if (remainingVersions.isEmpty()) {
                    Text(
                        text = "Ya has agregado todas las versiones disponibles.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 380.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(remainingVersions, key = { it.code }) { ver ->
                            val isOffline = ver.code == "RVR1960" ||
                                downloadStates[ver.code] is VersionDownloadState.Downloaded ||
                                OfflineBibleDownloadManager.isAssetVersion(ver.code)

                            Surface(
                                onClick = {
                                    showAddVersionDialog = false
                                    if (comparedVersions.none { it.code == ver.code }) {
                                        persistVersions(comparedVersions + ver)
                                        if (versionsMap[ver.code] !is VersionTextState.Success) {
                                            versionsMap = versionsMap + (ver.code to VersionTextState.Loading)
                                            scope.launch(Dispatchers.IO) {
                                                try {
                                                    val text = loadVerseTextForVersion(
                                                        context,
                                                        readerDao,
                                                        bookId,
                                                        chapter,
                                                        verseNumbers.sorted(),
                                                        ver.code
                                                    )
                                                    withContext(Dispatchers.Main) {
                                                        versionsMap = versionsMap + (ver.code to VersionTextState.Success(text))
                                                    }
                                                } catch (_: Exception) {
                                                    withContext(Dispatchers.Main) {
                                                        versionsMap = versionsMap + (ver.code to VersionTextState.Error("No se pudo cargar texto"))
                                                    }
                                                }
                                            }
                                        }
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 14.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                        ) {
                                            Text(
                                                text = ver.shortName,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.sp,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                        Text(
                                            text = ver.name,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                    if (isOffline) {
                                        Surface(
                                            shape = RoundedCornerShape(10.dp),
                                            color = Color(0xFF10B981).copy(alpha = 0.15f)
                                        ) {
                                            Text(
                                                text = "Offline",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF059669),
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    } else {
                                        Surface(
                                            shape = RoundedCornerShape(10.dp),
                                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f)
                                        ) {
                                            Text(
                                                text = "En línea",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showAddVersionDialog = false }) {
                    Text("Cerrar")
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(20.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DismissibleVersionCard(
    ver: BibleVersion,
    citation: String,
    isOffline: Boolean,
    state: VersionTextState,
    onRemove: () -> Unit,
    onRetry: () -> Unit,
    onCopy: () -> Unit,
    modifier: Modifier = Modifier
) {
    key(ver.code) {
        val dismissState = rememberSwipeToDismissBoxState()
        var isReadyForDismiss by remember(ver.code) { mutableStateOf(false) }

        LaunchedEffect(ver.code) {
            if (dismissState.currentValue != SwipeToDismissBoxValue.Settled) {
                dismissState.snapTo(SwipeToDismissBoxValue.Settled)
            }
            isReadyForDismiss = true
        }

        LaunchedEffect(dismissState.currentValue) {
            if (isReadyForDismiss && dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                onRemove()
                dismissState.snapTo(SwipeToDismissBoxValue.Settled)
            }
        }

        SwipeToDismissBox(
            state = dismissState,
            enableDismissFromStartToEnd = false,
            enableDismissFromEndToStart = true,
            backgroundContent = {
                val color by animateColorAsState(
                    targetValue = if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                        MaterialTheme.colorScheme.errorContainer
                    } else {
                        MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.6f)
                    },
                    label = "swipeDismissColor"
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                        .background(color)
                        .padding(end = 20.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Eliminar",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar versión",
                            tint = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            },
            modifier = modifier
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                        .compositeOver(MaterialTheme.colorScheme.surface)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Version Header with Badges
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = ver.shortName,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }

                            Text(
                                text = ver.name,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        Row(
                            modifier = Modifier.wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            if (isOffline) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color(0xFF10B981).copy(alpha = 0.15f)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = Color(0xFF059669),
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Text(
                                            text = "Offline",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF059669)
                                        )
                                    }
                                }
                            } else {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Cloud,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Text(
                                            text = "En línea",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }

                            // Single copy button
                            if (state is VersionTextState.Success) {
                                IconButton(
                                    onClick = onCopy,
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        Icons.Default.ContentCopy,
                                        contentDescription = "Copiar versión",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Verse Text Content
                    when (state) {
                        is VersionTextState.Loading -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    strokeWidth = 2.dp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Cargando traducción...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        is VersionTextState.Success -> {
                            Text(
                                text = state.text,
                                style = MaterialTheme.typography.bodyMedium,
                                lineHeight = 22.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        is VersionTextState.Error -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = state.message,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.error
                                )
                                TextButton(onClick = onRetry) {
                                    Text("Reintentar", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private suspend fun loadVerseTextForVersion(
    context: Context,
    readerDao: BibleReaderDao?,
    bookId: Int,
    chapter: Int,
    verseNumbers: List<Int>,
    version: String
): String {
    val normVersion = when (version.uppercase().trim()) {
        "RV1960", "REINA-VALERA 1960" -> "RVR1960"
        else -> version.uppercase().trim()
    }

    // 1. If asset version (RVR1960, NBLA, TLA, DHH, DHH94PC), use local offline SQLite directly
    if (OfflineBibleManager.isAssetVersion(normVersion)) {
        val offlineVerses = OfflineBibleManager.getVerses(context, bookId, chapter, normVersion)
        if (offlineVerses.isNotEmpty()) {
            val matching = offlineVerses.filter { it.verseNumber in verseNumbers }
            if (matching.isNotEmpty()) {
                return matching.joinToString(" ") { v ->
                    val clean = OfflineBibleManager.cleanVerseText(v.text)
                    if (verseNumbers.size > 1) "${v.verseNumber} $clean" else clean
                }
            }
        }
    }

    // 2. Check if already cached or downloaded in Room
    if (readerDao != null) {
        val cached = readerDao.getVersesSync(bookId, chapter, normVersion)
        if (cached.isNotEmpty()) {
            val matching = cached.filter { it.verseNumber in verseNumbers }
            if (matching.isNotEmpty()) {
                return matching.joinToString(" ") { v ->
                    val clean = OfflineBibleManager.cleanVerseText(v.text)
                    if (verseNumbers.size > 1) "${v.verseNumber} $clean" else clean
                }
            }
        }
    }

    // 3. Fetch from Bolls API
    val networkVerses = BollsBibleApiService.fetchChapter(normVersion, bookId, chapter)
    if (!networkVerses.isNullOrEmpty()) {
        val matching = networkVerses.filter { it.verseNumber in verseNumbers }
        if (matching.isNotEmpty()) {
            return matching.joinToString(" ") { v ->
                val clean = OfflineBibleManager.cleanVerseText(v.text)
                if (verseNumbers.size > 1) "${v.verseNumber} $clean" else clean
            }
        }
    }

    // Do NOT return RVR1960 text under another version
    return "Versículo no disponible en esta versión."
}

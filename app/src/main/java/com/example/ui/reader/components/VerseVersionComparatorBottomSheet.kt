package com.example.ui.reader.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cloud
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
    var filterOnlyDownloaded by remember { mutableStateOf(false) }

    // All available canonical versions in the app
    val allVersions = remember { BibleCatalog.versions }

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

    val visibleVersions = remember(allVersions, filterOnlyDownloaded, downloadStates) {
        if (filterOnlyDownloaded) {
            allVersions.filter { ver ->
                ver.code == "RVR1960" || downloadStates[ver.code] is VersionDownloadState.Downloaded
            }
        } else {
            allVersions
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
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
                            visibleVersions.forEach { ver ->
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

            Spacer(modifier = Modifier.height(10.dp))

            // Filter Tabs: Todas las Versiones vs Solo Descargadas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = !filterOnlyDownloaded,
                    onClick = { filterOnlyDownloaded = false },
                    label = { Text("Todas las versiones (${allVersions.size})") }
                )

                val downloadedCount = remember(downloadStates) {
                    1 + downloadStates.values.count { it is VersionDownloadState.Downloaded }
                }
                FilterChip(
                    selected = filterOnlyDownloaded,
                    onClick = { filterOnlyDownloaded = true },
                    label = { Text("Solo Descargadas ($downloadedCount)") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(10.dp))

            // Version Cards List
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                items(visibleVersions, key = { it.code }) { ver ->
                    val isOffline = ver.code == "RVR1960" || downloadStates[ver.code] is VersionDownloadState.Downloaded
                    val state = versionsMap[ver.code] ?: VersionTextState.Loading

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
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
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                Row(
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
                                            onClick = {
                                                val textToCopy = "«${state.text}» - $citation (${ver.shortName})"
                                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                                clipboard.setPrimaryClip(ClipData.newPlainText("Versículo ${ver.shortName}", textToCopy))
                                                Toast.makeText(context, "${ver.shortName} copiada", Toast.LENGTH_SHORT).show()
                                            },
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
                                        TextButton(
                                            onClick = {
                                                versionsMap = versionsMap + (ver.code to VersionTextState.Loading)
                                                scope.launch(Dispatchers.IO) {
                                                    try {
                                                        val text = loadVerseTextForVersion(context, readerDao, bookId, chapter, verseNumbers.sorted(), ver.code)
                                                        withContext(Dispatchers.Main) {
                                                            versionsMap = versionsMap + (ver.code to VersionTextState.Success(text))
                                                        }
                                                    } catch (_: Exception) {
                                                        withContext(Dispatchers.Main) {
                                                            versionsMap = versionsMap + (ver.code to VersionTextState.Error("Reintento fallido"))
                                                        }
                                                    }
                                                }
                                            }
                                        ) {
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

    // 1. If RVR1960, use local offline SQLite directly
    if (normVersion == "RVR1960") {
        val offlineVerses = OfflineBibleManager.getVerses(context, bookId, chapter)
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

    // Fallback: RVR1960 offline text
    val fallback = OfflineBibleManager.getVerses(context, bookId, chapter)
        .filter { it.verseNumber in verseNumbers }
    return if (fallback.isNotEmpty()) {
        fallback.joinToString(" ") { v ->
            val clean = OfflineBibleManager.cleanVerseText(v.text)
            if (verseNumbers.size > 1) "${v.verseNumber} $clean" else clean
        }
    } else {
        "Versículo no disponible en esta versión."
    }
}

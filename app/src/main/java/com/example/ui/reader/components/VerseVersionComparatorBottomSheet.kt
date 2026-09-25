package com.example.ui.reader.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CompareArrows
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
import com.example.data.bible.BollsBibleApiService
import com.example.data.bible.OfflineBibleManager
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
    val scrollState = rememberScrollState()

    val targetVersions = listOf(
        "RVR1960" to "Reina-Valera 1960",
        "NTV" to "Nueva Traducción Viviente",
        "NVI" to "Nueva Versión Internacional",
        "NBLA" to "Nueva Biblia de las Américas"
    )

    var versionsMap by remember {
        mutableStateOf<Map<String, VersionTextState>>(
            targetVersions.associate { it.first to VersionTextState.Loading }
        )
    }

    LaunchedEffect(bookId, chapter, verseNumbers) {
        val sortedVerses = verseNumbers.sorted()
        if (sortedVerses.isEmpty()) return@LaunchedEffect

        targetVersions.forEach { (code, _) ->
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
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CompareArrows,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
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
                            targetVersions.forEach { (code, name) ->
                                val state = versionsMap[code]
                                if (state is VersionTextState.Success) {
                                    sb.appendLine("[$code - $name]")
                                    sb.appendLine(state.text)
                                    sb.appendLine()
                                }
                            }
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipboard.setPrimaryClip(ClipData.newPlainText("Comparación Bíblica", sb.toString().trim()))
                            Toast.makeText(context, "Comparación copiada al portapapeles", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copiar todo",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(14.dp))

            // Scrollable list of versions
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                targetVersions.forEach { (code, fullName) ->
                    val state = versionsMap[code] ?: VersionTextState.Loading
                    VersionCard(
                        code = code,
                        fullName = fullName,
                        state = state,
                        onCopy = { text ->
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipboard.setPrimaryClip(ClipData.newPlainText(citation, "\"$text\" - $citation ($code)"))
                            Toast.makeText(context, "Copiado ($code)", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun VersionCard(
    code: String,
    fullName: String,
    state: VersionTextState,
    onCopy: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
        tonalElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = code,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = fullName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                }

                if (state is VersionTextState.Success) {
                    IconButton(
                        onClick = { onCopy(state.text) },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copiar",
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            when (state) {
                is VersionTextState.Loading -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Consultando versión...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                is VersionTextState.Success -> {
                    Text(
                        text = state.text,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 22.sp,
                            fontSize = 15.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                is VersionTextState.Error -> {
                    Text(
                        text = state.message,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
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
                    if (verseNumbers.size > 1) "${v.verseNumber} ${v.text}" else v.text
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
                    if (verseNumbers.size > 1) "${v.verseNumber} ${v.text}" else v.text
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
                val clean = v.text.replace(Regex("<[^>]*>"), "").trim()
                if (verseNumbers.size > 1) "${v.verseNumber} $clean" else clean
            }
        }
    }

    // Fallback: RVR1960 offline text
    val fallback = OfflineBibleManager.getVerses(context, bookId, chapter)
        .filter { it.verseNumber in verseNumbers }
    return if (fallback.isNotEmpty()) {
        fallback.joinToString(" ") { v ->
            if (verseNumbers.size > 1) "${v.verseNumber} ${v.text}" else v.text
        }
    } else {
        "Versículo no disponible en esta versión."
    }
}

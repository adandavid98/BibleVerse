package com.example.ui.reader.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.bible.BibleCrossReferencesCatalog
import com.example.data.bible.CrossReferenceItem
import com.example.data.bible.OfflineBibleManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrossReferencesBottomSheet(
    sourceCitation: String,
    bookId: Int,
    chapter: Int,
    verse: Int,
    customReferences: List<CrossReferenceItem>? = null,
    onNavigateToVerse: (bookId: Int, chapter: Int, startVerse: Int, endVerse: Int) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val references = remember(bookId, chapter, verse, customReferences) {
        customReferences ?: BibleCrossReferencesCatalog.getReferences(context, bookId, chapter, verse)
    }

    var verseTexts by remember { mutableStateOf<Map<String, String>>(emptyMap()) }

    LaunchedEffect(references) {
        references.forEach { ref ->
            launch(Dispatchers.IO) {
                try {
                    val verses = OfflineBibleManager.getVerses(context, ref.targetBookId, ref.targetChapter)
                    val matching = verses.filter { it.verseNumber in ref.targetVerse..ref.targetEndVerse }
                    val text = if (matching.size > 1) {
                        matching.joinToString("\n\n") { v ->
                            "${v.verseNumber}. ${OfflineBibleManager.cleanVerseText(v.text)}"
                        }
                    } else {
                        OfflineBibleManager.cleanVerseText(matching.firstOrNull()?.text ?: "")
                    }
                    if (text.isNotBlank()) {
                        withContext(Dispatchers.Main) {
                            verseTexts = verseTexts + (ref.targetCitation to text)
                        }
                    }
                } catch (_: Exception) {}
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
                .fillMaxHeight(0.82f)
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.MenuBook,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Text(
                            text = "Referencias Bíblicas",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Pasajes correlacionados para $sourceCitation",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
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

            Spacer(modifier = Modifier.height(14.dp))
            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(14.dp))

            if (references.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay referencias registradas para este versículo.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(references) { ref ->
                        val fullVerseText = verseTexts[ref.targetCitation] ?: ref.note

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.40f)
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = ref.targetCitation,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.primary
                                    )

                                    IconButton(
                                        onClick = {
                                            val textToCopy = "${ref.targetCitation}\n$fullVerseText"
                                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                            clipboard.setPrimaryClip(ClipData.newPlainText("Referencia Bíblica", textToCopy))
                                            Toast.makeText(context, "Pasaje copiado", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.ContentCopy,
                                            contentDescription = "Copiar",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                if (fullVerseText.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = fullVerseText,
                                        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                Button(
                                    onClick = {
                                        onDismiss()
                                        onNavigateToVerse(ref.targetBookId, ref.targetChapter, ref.targetVerse, ref.targetEndVerse)
                                    },
                                    modifier = Modifier.align(Alignment.End),
                                    shape = RoundedCornerShape(12.dp),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text("Ir al pasaje", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
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

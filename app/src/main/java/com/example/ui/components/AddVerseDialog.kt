package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AddVerseDialog(
    onDismiss: () -> Unit,
    onAddVerse: (
        book: String,
        chapterVerse: String,
        testament: String,
        text: String,
        context: String,
        topic: String,
        notes: String
    ) -> Unit
) {
    var book by remember { mutableStateOf("") }
    var chapterVerse by remember { mutableStateOf("") }
    var selectedTestament by remember { mutableStateOf("Nuevo Testamento") }
    var topic by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var contextDesc by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.92f)
                .imePadding()
                .clip(RoundedCornerShape(24.dp))
                .testTag("dialog_add_verse"),
            color = MaterialTheme.colorScheme.background,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Añadir Versículo Manual",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Agrega tus versículos favoritos con su contexto",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("btn_close_add_verse")
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = "Cerrar")
                    }
                }

                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Testament Chips
                    Text(
                        text = "Testamento:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = selectedTestament == "Antiguo Testamento",
                            onClick = { selectedTestament = "Antiguo Testamento" },
                            label = { Text("Antiguo Testamento") },
                            modifier = Modifier.testTag("chip_antiguo")
                        )
                        FilterChip(
                            selected = selectedTestament == "Nuevo Testamento",
                            onClick = { selectedTestament = "Nuevo Testamento" },
                            label = { Text("Nuevo Testamento") },
                            modifier = Modifier.testTag("chip_nuevo")
                        )
                    }

                    // Book & Chapter:Verse
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = book,
                            onValueChange = { book = it },
                            label = { Text("Libro (ej. Salmos)") },
                            modifier = Modifier
                                .weight(1.3f)
                                .testTag("input_book"),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = chapterVerse,
                            onValueChange = { chapterVerse = it },
                            label = { Text("Capítulo y Verso") },
                            placeholder = { Text("ej. 23:1") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_chapter_verse"),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // Topic
                    OutlinedTextField(
                        value = topic,
                        onValueChange = { topic = it },
                        label = { Text("Tema o Categoría (ej. Paz, Fortaleza, Fe)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_topic"),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Biblical Text
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Texto Bíblico *") },
                        placeholder = { Text("«Porque de tal manera amó Dios al mundo...»") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_text"),
                        minLines = 3,
                        maxLines = 6,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Theological Context
                    OutlinedTextField(
                        value = contextDesc,
                        onValueChange = { contextDesc = it },
                        label = { Text("Contexto Teológico y Moral *") },
                        placeholder = { Text("Explica el significado teológico, a quién fue dirigido y su enseñanza...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_context"),
                        minLines = 2,
                        maxLines = 5,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Notes (Optional)
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Notas Personales (Opcional)") },
                        placeholder = { Text("Tus reflexiones personales para este versículo...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_initial_notes"),
                        minLines = 2,
                        maxLines = 4,
                        shape = RoundedCornerShape(12.dp)
                    )

                    if (errorMessage != null) {
                        Text(
                            text = errorMessage ?: "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            if (book.isBlank() || chapterVerse.isBlank() || text.isBlank() || contextDesc.isBlank()) {
                                errorMessage = "Por favor completa el libro, versículo, texto y contexto."
                            } else {
                                onAddVerse(
                                    book.trim(),
                                    chapterVerse.trim(),
                                    selectedTestament,
                                    text.trim(),
                                    contextDesc.trim(),
                                    topic.trim(),
                                    notes.trim()
                                )
                            }
                        },
                        modifier = Modifier.testTag("btn_confirm_add_verse")
                    ) {
                        Icon(Icons.Filled.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Guardar Versículo")
                    }
                }
            }
        }
    }
}

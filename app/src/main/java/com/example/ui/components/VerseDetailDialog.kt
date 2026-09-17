package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FormatColorReset
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.bible.BibleCatalog
import com.example.data.bible.BibleContextEngine
import com.example.data.bible.ContextGenerationResult
import com.example.data.bible.ContextSource
import com.example.data.bible.GeminiVerseContextService
import com.example.data.model.VerseEntity
import com.example.ui.theme.BibleHighlightColors
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VerseDetailDialog(
    verse: VerseEntity,
    fontScale: Float = 1.0f,
    onDismiss: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onHighlightSelected: (String) -> Unit,
    onSaveNotes: (String) -> Unit,
    onSaveVerseEdit: (reference: String, text: String, context: String, topic: String, bibleVersion: String) -> Unit,
    onShare: () -> Unit,
    onExportPdf: () -> Unit,
    onDelete: (() -> Unit)? = null,
    onEnrichContextAi: (() -> Unit)? = null,
    isEnrichingAi: Boolean = false
) {
    val coroutineScope = rememberCoroutineScope()
    var notesText by remember(verse.id) { mutableStateOf(verse.notes) }
    var isNotesModified by remember(verse.id) { mutableStateOf(false) }
    val currentHighlightColor = BibleHighlightColors.getColorFromHex(verse.highlightColor)

    // Edit mode state
    var isEditingVerse by remember(verse.id) { mutableStateOf(false) }
    var isGeneratingAiInEdit by remember(verse.id) { mutableStateOf(false) }
    var editContextSourceBadge by remember(verse.id) { mutableStateOf<String?>(null) }
    var editReference by remember(verse.id, isEditingVerse) { mutableStateOf(verse.reference) }
    var editTopic by remember(verse.id, isEditingVerse) { mutableStateOf(verse.topic) }
    var editBibleVersion by remember(verse.id, isEditingVerse) { mutableStateOf(verse.bibleVersion.ifBlank { "RVR1960" }) }
    var editText by remember(verse.id, isEditingVerse) { mutableStateOf(verse.text.removeSurrounding("«", "»")) }
    var editContext by remember(verse.id, isEditingVerse) { mutableStateOf(verse.context) }
    var editErrorMessage by remember(verse.id, isEditingVerse) { mutableStateOf<String?>(null) }
    var showVersionModal by remember { mutableStateOf(false) }

    if (showVersionModal) {
        BibleVersionSelectionDialog(
            currentVersionCode = editBibleVersion,
            onVersionSelected = { newVersion ->
                editBibleVersion = newVersion.code
            },
            onDismiss = { showVersionModal = false }
        )
    }

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
                .testTag("dialog_verse_detail"),
            color = MaterialTheme.colorScheme.background,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Top Bar: Title & Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isEditingVerse) "Editar Versículo" else verse.reference,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = (22 * fontScale).sp
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        if (isEditingVerse) {
                            Text(
                                text = "Modifica la traducción, texto o contexto teológico",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        } else {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    Text(
                                        text = verse.bibleVersion.ifBlank { "RVR1960" },
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Text(
                                    text = "${verse.testament} • ${verse.topic}",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }

                    Row {
                        if (!isEditingVerse) {
                            IconButton(
                                onClick = onFavoriteToggle,
                                modifier = Modifier.testTag("btn_detail_favorite")
                            ) {
                                Icon(
                                    imageVector = if (verse.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = "Favorito",
                                    tint = if (verse.isFavorite) Color(0xFFE53935) else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            IconButton(
                                onClick = { isEditingVerse = true },
                                modifier = Modifier.testTag("btn_detail_edit")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "Editar versículo y contexto",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        IconButton(
                            onClick = {
                                if (isEditingVerse) {
                                    isEditingVerse = false
                                } else {
                                    onDismiss()
                                }
                            },
                            modifier = Modifier.testTag("btn_detail_close")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Cerrar",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )

                if (isEditingVerse) {
                    // EDIT MODE FORM
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Puedes corregir o ampliar el contexto teológico y los datos del versículo.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        // Reference & Topic Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedTextField(
                                value = editReference,
                                onValueChange = { editReference = it },
                                label = { Text("Referencia (ej. Génesis 1:1)") },
                                modifier = Modifier
                                    .weight(1.3f)
                                    .testTag("input_edit_reference"),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp)
                            )
                            OutlinedTextField(
                                value = editTopic,
                                onValueChange = { editTopic = it },
                                label = { Text("Tema o Categoría") },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("input_edit_topic"),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp)
                            )
                        }

                        // Bible Version Selector Card
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { showVersionModal = true }
                                .testTag("card_edit_bible_version"),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                            ),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Versión de Traducción Bíblica:",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = MaterialTheme.colorScheme.primary
                                        ) {
                                            Text(
                                                text = editBibleVersion,
                                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                        Text(
                                            text = BibleCatalog.findVersion(editBibleVersion).name,
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                                OutlinedButton(
                                    onClick = { showVersionModal = true },
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.testTag("btn_edit_change_version")
                                ) {
                                    Text("Cambiar", fontSize = 12.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null, modifier = Modifier.size(16.dp))
                                }
                            }
                        }

                        // Biblical Text
                        OutlinedTextField(
                            value = editText,
                            onValueChange = { editText = it },
                            label = { Text("Texto Bíblico *") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_edit_text"),
                            minLines = 3,
                            maxLines = 6,
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Panel de Contexto Inteligente (Híbrido)
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.AutoAwesome,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = "Regenerar Contexto",
                                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }

                                    if (editContextSourceBadge != null) {
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = if (editContextSourceBadge!!.contains("IA")) {
                                                MaterialTheme.colorScheme.primary
                                            } else {
                                                MaterialTheme.colorScheme.secondaryContainer
                                            }
                                        ) {
                                            Text(
                                                text = editContextSourceBadge!!,
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                color = if (editContextSourceBadge!!.contains("IA")) {
                                                    MaterialTheme.colorScheme.onPrimary
                                                } else {
                                                    MaterialTheme.colorScheme.onSecondaryContainer
                                                },
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = {
                                            isGeneratingAiInEdit = true
                                            coroutineScope.launch {
                                                val parts = editReference.split(" ")
                                                val bName = verse.book.ifBlank { parts.dropLast(1).joinToString(" ").ifBlank { "Salmos" } }
                                                val cv = verse.chapterVerse.ifBlank { parts.lastOrNull() ?: "1:1" }
                                                val cvParts = cv.split(":")
                                                val ch = cvParts.getOrNull(0)?.filter { it.isDigit() }?.toIntOrNull() ?: 1
                                                val vs = cvParts.getOrNull(1) ?: "1"

                                                val res = GeminiVerseContextService.generateContext(
                                                    book = bName,
                                                    chapter = ch,
                                                    verse = vs,
                                                    verseText = editText,
                                                    bibleVersion = editBibleVersion
                                                )
                                                when (res) {
                                                    is ContextGenerationResult.Success -> {
                                                        editContext = res.contextText
                                                        editContextSourceBadge = if (res.source == ContextSource.AI_GEMINI) "✨ IA (Gemini)" else "Catálogo Local"
                                                    }
                                                    is ContextGenerationResult.Error -> {
                                                        editContext = res.fallbackContext
                                                        editContextSourceBadge = "Catálogo Local"
                                                    }
                                                }
                                                isGeneratingAiInEdit = false
                                            }
                                        },
                                        enabled = !isGeneratingAiInEdit,
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier
                                            .weight(1.3f)
                                            .testTag("btn_detail_enrich_ai")
                                    ) {
                                        if (isGeneratingAiInEdit) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(14.dp),
                                                strokeWidth = 2.dp,
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("Generando...", fontSize = 11.sp)
                                        } else {
                                            Icon(Icons.Filled.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Llenar con IA", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            val parts = editReference.split(" ")
                                            val bName = verse.book.ifBlank { parts.dropLast(1).joinToString(" ").ifBlank { "Salmos" } }
                                            val cv = verse.chapterVerse.ifBlank { parts.lastOrNull() ?: "1:1" }
                                            val cvParts = cv.split(":")
                                            val ch = cvParts.getOrNull(0)?.filter { it.isDigit() }?.toIntOrNull() ?: 1
                                            val vs = cvParts.getOrNull(1) ?: "1"
                                            editContext = BibleContextEngine.getLocalContext(bName, ch, vs, editText)
                                            editContextSourceBadge = "Catálogo Local"
                                        },
                                        enabled = !isGeneratingAiInEdit,
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier
                                            .weight(1f)
                                            .testTag("btn_detail_local_context")
                                    ) {
                                        Icon(Icons.Filled.MenuBook, contentDescription = null, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Local", fontSize = 11.sp)
                                    }
                                }
                            }
                        }

                        // Theological and Moral Context
                        OutlinedTextField(
                            value = editContext,
                            onValueChange = { 
                                editContext = it
                                editContextSourceBadge = "Personalizado"
                            },
                            label = { Text("Contexto Teológico y Moral *") },
                            placeholder = { Text("Explica el significado histórico, moral y teológico del pasaje...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_edit_context"),
                            minLines = 4,
                            maxLines = 8,
                            shape = RoundedCornerShape(12.dp)
                        )

                        if (editErrorMessage != null) {
                            Text(
                                text = editErrorMessage ?: "",
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
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { isEditingVerse = false },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("btn_cancel_edit_verse")
                        ) {
                            Text("Cancelar")
                        }

                        Button(
                            onClick = {
                                if (editReference.isBlank() || editText.isBlank() || editContext.isBlank()) {
                                    editErrorMessage = "Por favor completa la referencia, el texto y el contexto."
                                } else {
                                    onSaveVerseEdit(
                                        editReference.trim(),
                                        editText.trim(),
                                        editContext.trim(),
                                        editTopic.trim(),
                                        editBibleVersion.trim()
                                    )
                                    isEditingVerse = false
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("btn_save_edit_verse")
                        ) {
                            Icon(Icons.Filled.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Guardar Cambios")
                        }
                    }
                } else {
                    // NORMAL VIEWING CONTENT
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Biblical Text
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = currentHighlightColor?.copy(alpha = 0.28f)
                                    ?: MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                            )
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text(
                                    text = verse.text,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontFamily = FontFamily.Serif,
                                        fontStyle = FontStyle.Italic,
                                        fontSize = (20 * fontScale).sp,
                                        lineHeight = (30 * fontScale).sp,
                                        fontWeight = FontWeight.Medium
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Highlight Palette Selector
                        Text(
                            text = "Resaltar versículo:",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Clear highlight button
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .border(
                                        1.dp,
                                        MaterialTheme.colorScheme.outline,
                                        CircleShape
                                    )
                                    .background(MaterialTheme.colorScheme.surface)
                                    .clickable { onHighlightSelected("") }
                                    .testTag("color_clear"),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.FormatColorReset,
                                    contentDescription = "Sin resaltar",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            // Colors
                            BibleHighlightColors.list.forEach { opt ->
                                val isSelected = verse.highlightColor.equals(opt.hex, ignoreCase = true)
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(opt.color)
                                    .border(
                                        width = if (isSelected) 3.dp else 1.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black.copy(alpha = 0.15f),
                                        shape = CircleShape
                                    )
                                    .clickable { onHighlightSelected(opt.hex) }
                                    .testTag("color_${opt.name}"),
                                contentAlignment = Alignment.Center
                            ) {}
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Theological Context Section with Edit Context Button
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "CONTEXTO TEOLÓGICO",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.5.sp
                                    ),
                                    color = MaterialTheme.colorScheme.secondary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f, fill = false)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    if (onEnrichContextAi != null) {
                                        if (isEnrichingAi) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                                            ) {
                                                CircularProgressIndicator(
                                                    modifier = Modifier.size(13.dp),
                                                    strokeWidth = 2.dp,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = "Analizando...",
                                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        } else {
                                            TextButton(
                                                onClick = { onEnrichContextAi() },
                                                modifier = Modifier.testTag("btn_enrich_verse_ai"),
                                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                                enabled = true
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Filled.AutoAwesome,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(14.dp),
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = "Profundizar IA",
                                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        }
                                    }
                                    TextButton(
                                        onClick = { isEditingVerse = true },
                                        modifier = Modifier.testTag("btn_edit_context"),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Edit,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp),
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Editar",
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = verse.context,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = (15 * fontScale).sp,
                                    lineHeight = (22 * fontScale).sp
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Personal Notes Section
                    Text(
                        text = "Mis Notas Personales:",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = notesText,
                        onValueChange = {
                            notesText = it
                            isNotesModified = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_verse_notes"),
                        placeholder = { Text("Escribe aquí tus reflexiones, oraciones o notas de estudio...") },
                        minLines = 3,
                        maxLines = 6,
                        shape = RoundedCornerShape(12.dp)
                    )

                    if (isNotesModified) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                onSaveNotes(notesText)
                                isNotesModified = false
                            },
                            modifier = Modifier
                                .align(Alignment.End)
                                .testTag("btn_save_notes"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(Icons.Filled.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Guardar Nota")
                        }
                    }

                    if (verse.isCustom && onDelete != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = onDelete,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Filled.Delete, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Eliminar este versículo manual")
                        }
                    }
                }

                // Bottom Action Bar
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onShare,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("btn_detail_share")
                    ) {
                        Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Compartir")
                    }

                    Button(
                        onClick = onExportPdf,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("btn_detail_pdf")
                    ) {
                        Icon(Icons.Filled.PictureAsPdf, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Exportar PDF")
                    }
                }
            }
        }
    }
}
}

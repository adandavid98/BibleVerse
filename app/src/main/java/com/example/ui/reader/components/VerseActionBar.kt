package com.example.ui.reader.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.reader.model.HighlightPalette

@Composable
fun VerseActionBar(
    selectedCount: Int,
    onCopy: () -> Unit,
    onHighlight: (String) -> Unit,
    onRemoveHighlight: () -> Unit,
    onSaveToVerses: () -> Unit = {},
    onShareText: () -> Unit,
    onShareCard: () -> Unit,
    onClearSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showColorPalette by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = selectedCount > 0,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Optional Palette popup
            if (showColorPalette) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                        .shadow(8.dp, RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HighlightPalette.colors.forEach { colorItem ->
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(android.graphics.Color.parseColor(colorItem.hex)))
                                    .border(1.dp, Color.Black.copy(alpha = 0.2f), CircleShape)
                                    .clickable {
                                        onHighlight(colorItem.hex)
                                        showColorPalette = false
                                    }
                            )
                        }

                        // Remove highlight icon
                        IconButton(
                            onClick = {
                                onRemoveHighlight()
                                showColorPalette = false
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Default.FormatColorReset,
                                contentDescription = "Quitar subrayado",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            // Main Bar
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(12.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 6.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Selection Counter & Close
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        IconButton(
                            onClick = onClearSelection,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Deseleccionar",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(
                            text = "$selectedCount selec.",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Action Icons
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Highlight button
                        IconButton(
                            onClick = { showColorPalette = !showColorPalette },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                Icons.Default.BorderColor,
                                contentDescription = "Subrayar",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        // Save / Bookmark button
                        IconButton(
                            onClick = onSaveToVerses,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                Icons.Default.BookmarkBorder,
                                contentDescription = "Guardar en Versículos",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        // Copy button
                        IconButton(
                            onClick = onCopy,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                Icons.Default.ContentCopy,
                                contentDescription = "Copiar cita",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        // Share text button
                        IconButton(
                            onClick = onShareText,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                Icons.Default.Share,
                                contentDescription = "Compartir texto",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        // Share card / image button
                        IconButton(
                            onClick = onShareCard,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                Icons.Default.Image,
                                contentDescription = "Crear imagen",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

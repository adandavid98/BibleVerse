package com.example.ui.reader.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.vector.ImageVector
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
    onCompareVersions: () -> Unit = {},
    onShareText: () -> Unit,
    onShareCard: () -> Unit,
    onCrossReferences: () -> Unit = {},
    onClearSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = selectedCount > 0,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(16.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Drag handle indicator
                Box(
                    modifier = Modifier
                        .size(width = 36.dp, height = 4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f))
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Top Header Row: Counter, Close button & Palette Row (YouVersion style)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        IconButton(
                            onClick = onClearSelection,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Deseleccionar",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = "$selectedCount selec.",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Direct Highlight Color Palette Swatches (Immediate 1-tap highlighting)
                    Row(
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .horizontalScroll(rememberScrollState())
                            .padding(start = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HighlightPalette.colors.forEach { colorItem ->
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(Color(android.graphics.Color.parseColor(colorItem.hex)))
                                    .border(1.dp, Color.Black.copy(alpha = 0.15f), CircleShape)
                                    .clickable { onHighlight(colorItem.hex) }
                            )
                        }

                        // Remove highlight icon
                        IconButton(
                            onClick = onRemoveHighlight,
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                Icons.Default.FormatColorReset,
                                contentDescription = "Quitar color",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(12.dp))

                // Action Items Scrollable Row (YouVersion Style)
                // Full sized icons with descriptive text labels underneath, with scroll so nothing is clipped
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ActionPill(
                        icon = Icons.Default.CompareArrows,
                        label = "Comparar",
                        onClick = onCompareVersions
                    )

                    ActionPill(
                        icon = Icons.Default.Image,
                        label = "Imagen",
                        onClick = onShareCard
                    )

                    ActionPill(
                        icon = Icons.Default.Share,
                        label = "Compartir",
                        onClick = onShareText
                    )

                    ActionPill(
                        icon = Icons.Default.ContentCopy,
                        label = "Copiar",
                        onClick = onCopy
                    )

                    ActionPill(
                        icon = Icons.Default.Bookmark,
                        label = "Guardar",
                        onClick = onSaveToVerses
                    )

                    ActionPill(
                        icon = Icons.Default.MenuBook,
                        label = "Referencias",
                        onClick = onCrossReferences
                    )
                }
            }
        }
    }
}

@Composable
private fun ActionPill(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
            modifier = Modifier.size(44.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

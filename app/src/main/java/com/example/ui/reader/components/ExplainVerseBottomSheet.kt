package com.example.ui.reader.components

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun ExplainVerseBottomSheet(
    citation: String,
    content: String,
    source: String,
    isLoading: Boolean,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val density = LocalDensity.current

    val navBarBottomDp = remember(context, density) {
        val activity = generateSequence(context) { if (it is ContextWrapper) it.baseContext else null }
            .filterIsInstance<Activity>()
            .firstOrNull()
        val insets = activity?.window?.decorView?.let { decor ->
            ViewCompat.getRootWindowInsets(decor)?.getInsets(WindowInsetsCompat.Type.navigationBars())
        }
        val bottomPx = insets?.bottom ?: 0
        if (bottomPx > 0) {
            with(density) { bottomPx.toDp() }
        } else {
            36.dp
        }
    }
    val safeBottomPadding = maxOf(navBarBottomDp, 32.dp) + 16.dp

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.55f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onDismiss
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {}
                    ),
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 12.dp,
                            bottom = safeBottomPadding
                        )
                ) {
                    // Drag handle indicator
                    Box(
                        modifier = Modifier
                            .size(width = 38.dp, height = 4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.35f))
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Header Row: Icon, Title & Action Icons (Copiar, Compartir, Cerrar)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color(0xFFF59E0B).copy(alpha = 0.16f),
                                border = BorderStroke(1.dp, Color(0xFFF59E0B).copy(alpha = 0.4f)),
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Lightbulb,
                                        contentDescription = null,
                                        tint = Color(0xFFD97706),
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                            Column {
                                Text(
                                    text = "Explicación Bíblica",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = citation,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        // Header Action Icons
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    if (content.isNotBlank()) {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        val clip = ClipData.newPlainText("Explicación Bíblica", "$citation\n\n$content")
                                        clipboard.setPrimaryClip(clip)
                                        Toast.makeText(context, "Explicación copiada", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                enabled = content.isNotBlank()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copiar explicación",
                                    tint = if (content.isNotBlank()) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.35f),
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            IconButton(
                                onClick = {
                                    if (content.isNotBlank()) {
                                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                            type = "text/plain"
                                            putExtra(Intent.EXTRA_SUBJECT, "Explicación: $citation")
                                            putExtra(Intent.EXTRA_TEXT, "$citation\n\n$content\n\nCompartido desde BibleVerse")
                                        }
                                        context.startActivity(Intent.createChooser(shareIntent, "Compartir Explicación"))
                                    }
                                },
                                enabled = content.isNotBlank()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Compartir explicación",
                                    tint = if (content.isNotBlank()) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.35f),
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            IconButton(onClick = onDismiss) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Cerrar",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                    Spacer(modifier = Modifier.height(14.dp))

                    // Content Area: Fluid Scrollable Card
                    if (isLoading && content.isBlank()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(38.dp),
                                    strokeWidth = 3.dp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Arrojando luz sobre el contexto bíblico...",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    } else {
                        val contentScrollState = rememberScrollState()

                        // Auto-scroll seamlessly as characters and words cascade in
                        LaunchedEffect(content.length) {
                            if (isLoading && content.isNotBlank()) {
                                contentScrollState.animateScrollTo(contentScrollState.maxValue)
                            }
                        }

                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                            ),
                            border = BorderStroke(1.2.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.75f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(contentScrollState)
                                    .padding(18.dp)
                            ) {
                                if (source.isNotBlank()) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                                        modifier = Modifier.padding(bottom = 12.dp)
                                    ) {
                                        Text(
                                            text = "✨ $source",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                // Fluid content rendering in cascade without layout jumps
                                Text(
                                    text = content,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontSize = 16.sp,
                                        lineHeight = 26.sp
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                if (isLoading) {
                                    Spacer(modifier = Modifier.height(14.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(14.dp),
                                            strokeWidth = 2.dp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = "Escribiendo en cascada...",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                // Padding at the end so text never touches the bottom card border
                                Spacer(modifier = Modifier.height(28.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

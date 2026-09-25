package com.example.ui.reader.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.reader.export.CardBitmapRenderer
import com.example.ui.reader.model.ShareCardTemplate

@Composable
fun ShareTemplateDialog(
    verseText: String,
    citationText: String,
    onCopyCitation: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var selectedTemplate by remember { mutableStateOf(ShareCardTemplate.MINIMALIST) }
    val cleanVerseText = remember(verseText) { com.example.data.bible.OfflineBibleManager.cleanVerseText(verseText) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f)
                .shadow(16.dp, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Compartir Versículo",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Interactive Live Preview
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(18.dp))
                        .shadow(4.dp, RoundedCornerShape(18.dp))
                ) {
                    when (selectedTemplate) {
                        ShareCardTemplate.MINIMALIST -> MinimalistCardPreview(cleanVerseText, citationText)
                        ShareCardTemplate.SACRED_GRADIENT -> SacredGradientCardPreview(cleanVerseText, citationText)
                        ShareCardTemplate.PARCHMENT -> ParchmentCardPreview(cleanVerseText, citationText)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Template selector options
                Text(
                    text = "Elige un estilo:",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ShareCardTemplate.values().forEach { template ->
                        val isSelected = template == selectedTemplate
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { selectedTemplate = template },
                            color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(android.graphics.Color.parseColor(template.previewColorHex)))
                                        .border(1.dp, Color.Gray.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = template.title.substringBefore(" "),
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    textAlign = TextAlign.Center,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action Buttons
                Button(
                    onClick = {
                        CardBitmapRenderer.renderAndShare(
                            context = context,
                            verseText = cleanVerseText,
                            citationText = citationText,
                            template = selectedTemplate
                        )
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Compartir Imagen", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = {
                        onCopyCitation()
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Copiar Cita y Texto")
                }
            }
        }
    }
}

@Composable
private fun MinimalistCardPreview(verseText: String, citationText: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(16.dp)
            .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(12.dp))
            .padding(14.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                color = Color(0xFFF3F4F6),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "PALABRA SAGRADA",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4B5563),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "“$verseText”",
                fontFamily = FontFamily.Serif,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF111827),
                maxLines = 6
            )
            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Color(0xFFE5E7EB), thickness = 1.dp, modifier = Modifier.width(40.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = citationText,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color(0xFF1F2937)
            )
        }
    }
}

@Composable
private fun SacredGradientCardPreview(verseText: String, citationText: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0F172A),
                        Color(0xFF1E1B4B),
                        Color(0xFF312E81)
                    )
                )
            )
            .padding(16.dp)
            .border(1.dp, Color(0xFF38BDF8).copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(14.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "✦ SAGRADA ESCRITURA ✦",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFBBF24)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "“$verseText”",
                fontFamily = FontFamily.Serif,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                maxLines = 6
            )
            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Color(0xFFF59E0B), thickness = 1.dp, modifier = Modifier.width(50.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = citationText,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color(0xFFFDE68A)
            )
        }
    }
}

@Composable
private fun ParchmentCardPreview(verseText: String, citationText: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7EEDB))
            .padding(14.dp)
            .border(2.dp, Color(0xFF8B5A2B), RoundedCornerShape(8.dp))
            .padding(4.dp)
            .border(1.dp, Color(0xFFA07855), RoundedCornerShape(6.dp))
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "❖ SANTA BIBLIA ❖",
                fontFamily = FontFamily.Serif,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5C3A21)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "“$verseText”",
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF2C1D11),
                maxLines = 6
            )
            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Color(0xFF8B5A2B), thickness = 1.dp, modifier = Modifier.width(50.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = citationText,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color(0xFF4A2810)
            )
        }
    }
}

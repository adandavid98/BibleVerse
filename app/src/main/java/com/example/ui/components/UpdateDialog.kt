package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.BuildConfig
import com.example.update.AppUpdateInfo
import com.example.update.UpdateDownloadStatus

@Composable
fun UpdateDialog(
    updateInfo: AppUpdateInfo,
    downloadStatus: UpdateDownloadStatus,
    onStartDownload: () -> Unit,
    onInstallApk: () -> Unit,
    onOpenInBrowser: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .imePadding()
                .clip(RoundedCornerShape(22.dp))
                .testTag("dialog_app_update"),
            color = MaterialTheme.colorScheme.background,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.SystemUpdate,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Actualización Disponible",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "GitHub Releases",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("btn_close_update_dialog")
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = "Cerrar", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Version Info Card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = updateInfo.releaseTitle,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Versión actual: v${BuildConfig.VERSION_NAME} → Nueva: ${updateInfo.tagName}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        if (updateInfo.apkSizeBytes > 0) {
                            val sizeMb = String.format("%.1f MB", updateInfo.apkSizeBytes / (1024.0 * 1024.0))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = sizeMb,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Release notes section
                Text(
                    text = "Novedades y cambios:",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 140.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = updateInfo.releaseNotes.ifBlank { "Mejoras de rendimiento y correcciones generales." },
                        style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Download / Installation State Progress
                when (downloadStatus) {
                    is UpdateDownloadStatus.Downloading -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Descargando instalador APK...",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                val percent = (downloadStatus.progress * 100).toInt()
                                Text(
                                    text = "$percent%",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            LinearProgressIndicator(
                                progress = { downloadStatus.progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp))
                            )
                            if (downloadStatus.totalBytes > 0) {
                                val currentMb = downloadStatus.downloadedBytes / (1024.0 * 1024.0)
                                val totalMb = downloadStatus.totalBytes / (1024.0 * 1024.0)
                                Text(
                                    text = String.format("%.1f MB de %.1f MB", currentMb, totalMb),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    is UpdateDownloadStatus.ReadyToInstall -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "¡APK descargado! Listo para instalar.",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    is UpdateDownloadStatus.Error -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.error.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Icon(Icons.Filled.ErrorOutline, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = downloadStatus.message,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    else -> {}
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Action Buttons
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (downloadStatus is UpdateDownloadStatus.ReadyToInstall) {
                        Button(
                            onClick = onInstallApk,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("btn_install_apk"),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Filled.SystemUpdate, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Instalar Ahora en mi Celular")
                        }
                    } else if (downloadStatus !is UpdateDownloadStatus.Downloading) {
                        if (updateInfo.apkDownloadUrl != null) {
                            Button(
                                onClick = onStartDownload,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("btn_download_update"),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Filled.CloudDownload, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Descargar e Instalar APK")
                            }
                        }
                    }

                    // Direct Web Link / Fallback button
                    OutlinedButton(
                        onClick = onOpenInBrowser,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("btn_open_github_release"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Filled.OpenInBrowser, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ver o Descargar desde GitHub")
                    }

                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
                    ) {
                        Text("Recordar más tarde")
                    }
                }
            }
        }
    }
}

package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.collectAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.BuildConfig
import com.example.data.bible.BibleCatalog
import com.example.data.bible.VersionDownloadState
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import com.example.ui.components.CloudSyncCardContent
import com.example.ui.theme.AppReadingTheme
import com.example.ui.viewmodel.BibleUiState
import com.example.ui.viewmodel.BibleViewModel
import com.example.update.UpdateDownloadStatus

@Composable
fun SettingsTab(
    viewModel: BibleViewModel,
    uiState: BibleUiState,
    onBack: (() -> Unit)? = null,
    onOpenReadingPlans: ((initialTab: Int) -> Unit)? = null
) {
    val context = LocalContext.current
    var reminderHour by remember(uiState.reminderHour) { mutableIntStateOf(uiState.reminderHour) }
    var reminderMinute by remember(uiState.reminderMinute) { mutableIntStateOf(uiState.reminderMinute) }
    var reminderEnabled by remember(uiState.reminderEnabled) { mutableStateOf(uiState.reminderEnabled) }

    val activePlans by viewModel.activeCustomPlans.collectAsState()
    val completedPlans by viewModel.completedCustomPlans.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        if (onBack != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 6.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Regresar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "Configuración",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Ajustes, Copias y Nube",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
        }

        // Section: Planes de Lectura Bíblica e Historial
        Text(
            text = "PLANES DE LECTURA E HISTORIAL",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = MaterialTheme.colorScheme.secondary
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Planes de Lectura Bíblica",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${activePlans.size} activos • ${completedPlans.size} completados",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { onOpenReadingPlans?.invoke(0) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Mis Planes", fontSize = 12.sp)
                    }

                    Button(
                        onClick = { onOpenReadingPlans?.invoke(2) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.EmojiEvents, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Historial", fontSize = 12.sp)
                    }
                }
            }
        }

        // Section: Visual Themes (Modo Lectura Nocturna Anti-Fatiga)
        Text(
            text = "MODO DE LECTURA & VISUALIZACIÓN",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = MaterialTheme.colorScheme.secondary
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Tema de la aplicación:",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Modo Claro
                    ThemeOptionCard(
                        title = "Claro",
                        subtitle = "Día",
                        icon = Icons.Filled.LightMode,
                        isSelected = uiState.readingTheme == AppReadingTheme.LIGHT,
                        onClick = { viewModel.setReadingTheme(AppReadingTheme.LIGHT) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("theme_light")
                    )

                    // Modo Lectura Nocturna Sepia (Anti-Fatiga)
                    ThemeOptionCard(
                        title = "Nocturno",
                        subtitle = "Anti-fatiga",
                        icon = Icons.Filled.Nightlight,
                        isSelected = uiState.readingTheme == AppReadingTheme.SEPIA_NIGHT,
                        onClick = { viewModel.setReadingTheme(AppReadingTheme.SEPIA_NIGHT) },
                        modifier = Modifier
                            .weight(1.15f)
                            .testTag("theme_sepia")
                    )

                    // Modo Oscuro Profundo
                    ThemeOptionCard(
                        title = "Oscuro",
                        subtitle = "OLED",
                        icon = Icons.Filled.DarkMode,
                        isSelected = uiState.readingTheme == AppReadingTheme.DARK,
                        onClick = { viewModel.setReadingTheme(AppReadingTheme.DARK) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("theme_dark")
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Font size scale
                Text(
                    text = "Tamaño del texto bíblico:",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        "Normal" to 1.0f,
                        "Grande" to 1.18f,
                        "Muy Grande" to 1.35f
                    ).forEach { (label, scale) ->
                        val isSelected = kotlin.math.abs(uiState.fontSizeScale - scale) < 0.05f
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.setFontSizeScale(scale) },
                            label = { Text(label) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("font_scale_$label")
                        )
                    }
                }
            }
        }

        // Section: Daily Reminders / Notifications
        Text(
            text = "RECORDATORIOS DIARIOS DE VERSÍCULOS",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = MaterialTheme.colorScheme.secondary
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Notificación diaria",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Recibe el versículo del día y su reflexión cada mañana",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = reminderEnabled,
                        onCheckedChange = { enabled ->
                            reminderEnabled = enabled
                            viewModel.setDailyReminder(reminderHour, reminderMinute, enabled)
                        },
                        modifier = Modifier.testTag("switch_reminder")
                    )
                }

                if (reminderEnabled) {
                    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Hora preferida:",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            val timeStr = String.format("%02d:%02d", reminderHour, reminderMinute)
                            Text(
                                text = "$timeStr hrs",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            // Quick buttons to adjust hour
                            OutlinedButton(
                                onClick = {
                                    reminderHour = (reminderHour + 1) % 24
                                    viewModel.setDailyReminder(reminderHour, reminderMinute, true)
                                },
                                modifier = Modifier.testTag("btn_change_hour")
                            ) {
                                Text("+1 Hora")
                            }

                            Button(
                                onClick = {
                                    reminderHour = (reminderHour + 23) % 24
                                    viewModel.setDailyReminder(reminderHour, reminderMinute, true)
                                }
                            ) {
                                Text("-1 Hora")
                            }
                        }
                    }

                    // Test notification button
                    OutlinedButton(
                        onClick = {
                            viewModel.testDailyNotification()
                            Toast.makeText(context, "Notificación enviada", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("btn_test_notification")
                    ) {
                        Icon(Icons.Filled.NotificationsActive, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Probar Notificación Ahora")
                    }
                }
            }
        }

        // Section: Nube y Copia de Seguridad
        Text(
            text = "NUBE Y COPIA DE SEGURIDAD",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = MaterialTheme.colorScheme.secondary
        )

        val firebaseUserState by viewModel.firebaseUserState.collectAsStateWithLifecycle()
        val firebaseSyncOperation by viewModel.firebaseSyncOperation.collectAsStateWithLifecycle()

        CloudSyncCardContent(
            syncId = uiState.cloudSyncId,
            lastSyncTime = uiState.lastSyncTime,
            userState = firebaseUserState,
            syncOperation = firebaseSyncOperation,
            onDismiss = null,
            onSignInGoogle = { viewModel.signInWithGoogle(context) },
            onSignInAnonymous = { viewModel.signInAnonymously() },
            onSignInEmailPassword = { email, pass -> viewModel.signInWithEmailPassword(email, pass) },
            onSignOut = { viewModel.signOutFirebase(context) },
            onUploadFirestore = { viewModel.uploadToFirestore() },
            onDownloadFirestore = { viewModel.downloadFromFirestore() },
            onPerformBackup = { viewModel.performCloudBackup() },
            onPerformRestore = { viewModel.performCloudRestore(it) },
            permanentSha1 = viewModel.permanentSha1,
            currentWebClientId = viewModel.getResolvedFirebaseWebClientId(context),
            onSaveWebClientId = { id -> viewModel.saveFirebaseWebClientId(context, id) },
            isDialog = false
        )

        // Section: Versiones Bíblicas & Descarga Offline
        Text(
            text = "VERSIONES BÍBLICAS & MODO OFFLINE",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = MaterialTheme.colorScheme.secondary
        )

        val offlineStates by viewModel.offlineDownloadStates.collectAsStateWithLifecycle()

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Column {
                    Text(
                        text = "Traducciones para uso sin internet",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "RVR1960 está incluida permanentemente. Descarga versiones completas adicionales para leer 100% offline.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                BibleCatalog.versions.forEach { ver ->
                    val state = offlineStates[ver.code] ?: VersionDownloadState.Idle
                    val isRvr = ver.code.equals("RVR1960", ignoreCase = true)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = ver.code,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = ver.name,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1
                                )
                            }
                        }

                        when {
                            isRvr || state is VersionDownloadState.Downloaded -> {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFF10B981).copy(alpha = 0.15f)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Filled.CheckCircle,
                                            contentDescription = null,
                                            tint = Color(0xFF10B981),
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (isRvr) "Incluida" else "Descargada",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF10B981)
                                        )
                                    }
                                }
                            }
                            state is VersionDownloadState.Downloading -> {
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "${state.progressPercent}%",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    CircularProgressIndicator(
                                        progress = { state.progressPercent / 100f },
                                        modifier = Modifier.size(18.dp),
                                        strokeWidth = 2.dp
                                    )
                                }
                            }
                            else -> {
                                OutlinedButton(
                                    onClick = { viewModel.downloadOfflineVersion(ver.code) },
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                                    modifier = Modifier.height(30.dp)
                                ) {
                                    Text("Descargar", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Section: Updates and GitHub Releases
        Text(
            text = "ACTUALIZACIONES DE LA APLICACIÓN",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = MaterialTheme.colorScheme.secondary
        )

        val updateDownloadStatus by viewModel.updateDownloadStatus.collectAsStateWithLifecycle()
        val updateInfo by viewModel.updateInfo.collectAsStateWithLifecycle()
        val autoCheckUpdates by viewModel.autoCheckUpdates.collectAsStateWithLifecycle()

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Versión instalada",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "v${BuildConfig.VERSION_NAME.removePrefix("v").removePrefix("V")}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "Oficial",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                // Switch for automatic update checking on app start
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Buscar actualizaciones al abrir",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (autoCheckUpdates) "Avisa cuando haya una versión nueva" else "Solo buscar manualmente",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = autoCheckUpdates,
                        onCheckedChange = { viewModel.setAutoCheckUpdates(it) },
                        modifier = Modifier.testTag("switch_auto_check_updates")
                    )
                }

                // If update is available or status
                val currentStatus = updateDownloadStatus
                when (currentStatus) {
                    is UpdateDownloadStatus.Available -> {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { viewModel.setShowUpdateDialog(true) },
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Filled.SystemUpdate,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "¡Nueva versión disponible: ${currentStatus.info.tagName}!",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "Toca para ver novedades e instalar actualización",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    is UpdateDownloadStatus.UpToDate -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Tienes la versión más reciente.",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    else -> {}
                }

                // Button: Check for updates
                Button(
                    onClick = { viewModel.checkForUpdates(silent = false) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("btn_check_updates")
                ) {
                    if (updateDownloadStatus is UpdateDownloadStatus.Checking) {
                        Icon(Icons.Filled.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Buscando actualizaciones...")
                    } else {
                        Icon(Icons.Filled.SystemUpdate, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Buscar Actualizaciones Ahora")
                    }
                }
            }
        }
    }
}

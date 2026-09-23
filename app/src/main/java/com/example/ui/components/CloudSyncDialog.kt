package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.sync.CloudSyncState
import com.example.sync.FirebaseUserState

@Composable
fun CloudSyncDialog(
    syncId: String,
    lastSyncTime: String,
    userState: FirebaseUserState?,
    syncOperation: CloudSyncState,
    onDismiss: () -> Unit,
    onSignInGoogle: () -> Unit,
    onSignInAnonymous: () -> Unit,
    onSignInEmailPassword: (String, String) -> Unit = { _, _ -> },
    onSignOut: () -> Unit,
    onUploadFirestore: () -> Unit,
    onDownloadFirestore: () -> Unit,
    onPerformBackup: () -> Unit,
    onPerformRestore: (String) -> Unit,
    permanentSha1: String = "44:99:64:CA:F2:A6:54:72:7E:84:80:7C:25:2C:E4:FB:68:BD:96:03",
    currentWebClientId: String? = null,
    onSaveWebClientId: (String) -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        CloudSyncCardContent(
            syncId = syncId,
            lastSyncTime = lastSyncTime,
            userState = userState,
            syncOperation = syncOperation,
            onDismiss = onDismiss,
            onSignInGoogle = onSignInGoogle,
            onSignInAnonymous = onSignInAnonymous,
            onSignInEmailPassword = onSignInEmailPassword,
            onSignOut = onSignOut,
            onUploadFirestore = onUploadFirestore,
            onDownloadFirestore = onDownloadFirestore,
            onPerformBackup = onPerformBackup,
            onPerformRestore = onPerformRestore,
            permanentSha1 = permanentSha1,
            currentWebClientId = currentWebClientId,
            onSaveWebClientId = onSaveWebClientId,
            isDialog = true
        )
    }
}

@Composable
fun CloudSyncCardContent(
    syncId: String,
    lastSyncTime: String,
    userState: FirebaseUserState?,
    syncOperation: CloudSyncState,
    onDismiss: (() -> Unit)? = null,
    onSignInGoogle: () -> Unit,
    onSignInAnonymous: () -> Unit,
    onSignInEmailPassword: (String, String) -> Unit = { _, _ -> },
    onSignOut: () -> Unit,
    onUploadFirestore: () -> Unit,
    onDownloadFirestore: () -> Unit,
    onPerformBackup: () -> Unit,
    onPerformRestore: (String) -> Unit,
    permanentSha1: String = "44:99:64:CA:F2:A6:54:72:7E:84:80:7C:25:2C:E4:FB:68:BD:96:03",
    currentWebClientId: String? = null,
    onSaveWebClientId: (String) -> Unit = {},
    isDialog: Boolean = false
) {
    val clipboardManager = LocalClipboardManager.current

    // Email/Password login inputs
    var showEmailLogin by remember { mutableStateOf(false) }
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }

    // Google / Firebase configuration inputs
    var showConfigSection by remember { mutableStateOf(false) }
    var customClientIdInput by remember(currentWebClientId) { mutableStateOf(currentWebClientId ?: "") }
    var sha1Copied by remember { mutableStateOf(false) }
    var clientIdSaved by remember { mutableStateOf(false) }

    val cardModifier = if (isDialog) {
        Modifier
            .fillMaxWidth(0.94f)
            .heightIn(max = 680.dp)
            .imePadding()
            .padding(vertical = 16.dp)
            .testTag("dialog_cloud_sync")
    } else {
        Modifier
            .fillMaxWidth()
            .testTag("screen_cloud_sync")
    }

    Card(
        modifier = cardModifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .then(if (isDialog) Modifier.verticalScroll(rememberScrollState()) else Modifier),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Filled.CloudSync,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "Nube y Copia de Seguridad",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Tus notas, colores y favoritos seguros",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                // Estado de Operación Firebase
                when (syncOperation) {
                    is CloudSyncState.Loading -> {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                                Text(
                                    text = "Sincronizando con la nube...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                    is CloudSyncState.Success -> {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(Icons.Filled.CloudDone, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                                Text(
                                    text = syncOperation.message,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                    is CloudSyncState.Error -> {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.7f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(Icons.Filled.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(20.dp))
                                Text(
                                    text = syncOperation.message,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }
                    }
                    CloudSyncState.Idle -> { /* Idle */ }
                }

                // 1. SECCIÓN DE CUENTA FIREBASE / GOOGLE
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (userState != null)
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                        else
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                if (userState != null) {
                                    Text(
                                        text = userState.displayName ?: "Usuario Conectado",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = userState.email ?: "Sesión en la Nube",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                } else {
                                    Text(
                                        text = "Sin sesión iniciada",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Conecta con Firebase para sincronizar tus notas",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        if (userState == null) {
                            // Botón 1: Conexión rápida anónima a Firebase (no requiere configuración de Google OAuth Web Client)
                            Button(
                                onClick = onSignInAnonymous,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("btn_anonymous_signin"),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Filled.CloudSync, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Conectar con Nube Firebase Directa")
                            }

                            // Botón 2: Iniciar con Google
                            OutlinedButton(
                                onClick = onSignInGoogle,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("btn_google_signin"),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Filled.Login, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Iniciar Sesión con Google")
                            }

                            // Botón 3: Iniciar con Correo y Contraseña
                            OutlinedButton(
                                onClick = { showEmailLogin = !showEmailLogin },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Filled.Email, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(if (showEmailLogin) "Ocultar Correo / Contraseña" else "Iniciar con Correo / Contraseña")
                            }

                            if (showEmailLogin) {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedTextField(
                                        value = emailInput,
                                        onValueChange = { emailInput = it },
                                        label = { Text("Correo electrónico") },
                                        singleLine = true,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = passwordInput,
                                        onValueChange = { passwordInput = it },
                                        label = { Text("Contraseña (mínimo 6 car.)") },
                                        singleLine = true,
                                        visualTransformation = PasswordVisualTransformation(),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Button(
                                        onClick = {
                                            if (emailInput.isNotBlank() && passwordInput.isNotBlank()) {
                                                onSignInEmailPassword(emailInput, passwordInput)
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text("Entrar / Crear Cuenta")
                                    }
                                }
                            }

                            // Botón 4: Ajustes de Conexión Google & Firebase (SHA-1 / Client ID)
                            TextButton(
                                onClick = { showConfigSection = !showConfigSection },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Filled.Key, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (showConfigSection) "Ocultar Ajustes de Google / SHA-1" else "⚙️ Configurar Google Sign-In (SHA-1 / Client ID)")
                            }

                            if (showConfigSection) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Text(
                                            text = "1. Huella SHA-1 de esta App (Para Firebase Console)",
                                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = permanentSha1,
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Medium
                                            ),
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        OutlinedButton(
                                            onClick = {
                                                clipboardManager.setText(AnnotatedString(permanentSha1))
                                                sha1Copied = true
                                            },
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Icon(
                                                if (sha1Copied) Icons.Filled.CheckCircle else Icons.Filled.ContentCopy,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(if (sha1Copied) "¡Huella SHA-1 Copiada!" else "Copiar Huella SHA-1")
                                        }

                                        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                                        Text(
                                            text = "2. Google Web Client ID (OAuth 2.0)",
                                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        OutlinedTextField(
                                            value = customClientIdInput,
                                            onValueChange = {
                                                customClientIdInput = it
                                                clientIdSaved = false
                                            },
                                            label = { Text("Web Client ID (.apps.googleusercontent.com)", fontSize = 11.sp) },
                                            singleLine = true,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Button(
                                            onClick = {
                                                if (customClientIdInput.isNotBlank()) {
                                                    onSaveWebClientId(customClientIdInput.trim())
                                                    clientIdSaved = true
                                                }
                                            },
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text(if (clientIdSaved) "¡Client ID Guardado con Éxito!" else "Guardar Client ID")
                                        }

                                        Text(
                                            text = "Pasos: 1) En Firebase Console > Configuración del Proyecto > Tus Apps, agrega la huella SHA-1. 2) En Authentication > Sign-in method activa Google. 3) Pega aquí el ID de cliente web si no lo detecta automáticamente.",
                                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = onUploadFirestore,
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Filled.CloudUpload, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Subir a Nube", fontSize = 12.sp)
                                }

                                OutlinedButton(
                                    onClick = onDownloadFirestore,
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Filled.CloudDownload, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Bajar de Nube", fontSize = 12.sp)
                                }
                            }

                            TextButton(
                                onClick = onSignOut,
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Icon(Icons.Filled.Logout, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Cerrar Sesión", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }


                // Footer Close
                if (onDismiss != null) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Text("Cerrar", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
}

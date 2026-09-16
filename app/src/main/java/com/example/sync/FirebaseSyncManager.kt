package com.example.sync

import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.example.data.model.VerseEntity
import com.example.data.repository.VerseRepository
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.UUID

object FirebaseSyncManager {

    const val PERMANENT_KEYSTORE_SHA1 = "44:99:64:CA:F2:A6:54:72:7E:84:80:7C:25:2C:E4:FB:68:BD:96:03"
    const val PERMANENT_KEYSTORE_SHA256 = "51:CC:D0:49:AD:C0:CF:FB:C9:EB:53:C6:18:DC:A6:B4:5D:49:B4:30:8A:F2:0B:6D:76:D1:F7:FB:E8:C5:7F:D2"
    const val DEFAULT_WEB_CLIENT_ID = "233274499940-sq9d6u2vdoe4pu0pk72i5k997c3m2h6q.apps.googleusercontent.com"
    private const val PREFS_FIREBASE_AUTH = "bible_firebase_auth_prefs"
    private const val KEY_CUSTOM_WEB_CLIENT_ID = "custom_firebase_web_client_id"

    private const val TAG = "FirebaseSyncManager"
    private const val COLLECTION_USERS = "users"
    private const val COLLECTION_VERSES = "verses"
    private const val COLLECTION_METADATA = "metadata"

    private var authInstance: FirebaseAuth? = null
    private var firestoreInstance: FirebaseFirestore? = null

    private val _currentUserState = MutableStateFlow<FirebaseUserState?>(null)
    val currentUserState: StateFlow<FirebaseUserState?> = _currentUserState.asStateFlow()

    private val _syncOperationState = MutableStateFlow<CloudSyncState>(CloudSyncState.Idle)
    val syncOperationState: StateFlow<CloudSyncState> = _syncOperationState.asStateFlow()

    fun init(context: Context) {
        try {
            if (FirebaseApp.getApps(context).isEmpty()) {
                FirebaseApp.initializeApp(context)
            }
            getOrInitAuth(context)
            getOrInitFirestore(context)
        } catch (e: Exception) {
            Log.e(TAG, "Error inicializando Firebase en init: ${e.message}", e)
        }
    }

    private fun getOrInitAuth(context: Context? = null): FirebaseAuth? {
        if (authInstance != null) return authInstance
        try {
            if (context != null && FirebaseApp.getApps(context).isEmpty()) {
                FirebaseApp.initializeApp(context)
            }
            val a = FirebaseAuth.getInstance()
            authInstance = a
            a.addAuthStateListener { firebaseAuth ->
                val user = firebaseAuth.currentUser
                _currentUserState.value = user?.toUserState()
            }
            _currentUserState.value = a.currentUser?.toUserState()
            return a
        } catch (e: Exception) {
            Log.e(TAG, "Fallo al obtener FirebaseAuth: ${e.message}", e)
            return null
        }
    }

    private fun getOrInitFirestore(context: Context? = null): FirebaseFirestore? {
        if (firestoreInstance != null) return firestoreInstance
        try {
            if (context != null && FirebaseApp.getApps(context).isEmpty()) {
                FirebaseApp.initializeApp(context)
            }
            val db = FirebaseFirestore.getInstance()
            firestoreInstance = db
            return db
        } catch (e: Exception) {
            Log.e(TAG, "Fallo al obtener FirebaseFirestore: ${e.message}", e)
            return null
        }
    }

    private fun FirebaseUser.toUserState(): FirebaseUserState {
        return FirebaseUserState(
            uid = uid,
            email = email,
            displayName = displayName ?: email?.substringBefore('@') ?: "Usuario",
            photoUrl = photoUrl?.toString()
        )
    }

    fun isFirebaseInitialized(): Boolean {
        return authInstance != null || try { FirebaseAuth.getInstance() != null } catch (e: Exception) { false }
    }

    fun getCurrentUser(): FirebaseUserState? {
        val auth = getOrInitAuth()
        return auth?.currentUser?.toUserState()
    }

    fun getSavedWebClientId(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFS_FIREBASE_AUTH, Context.MODE_PRIVATE)
        return prefs.getString(KEY_CUSTOM_WEB_CLIENT_ID, null)?.trim()?.ifBlank { null }
    }

    fun saveWebClientId(context: Context, clientId: String) {
        val prefs = context.getSharedPreferences(PREFS_FIREBASE_AUTH, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_CUSTOM_WEB_CLIENT_ID, clientId.trim()).apply()
    }

    fun resolveServerClientId(context: Context, explicitClientId: String? = null): String? {
        if (!explicitClientId.isNullOrBlank()) {
            return explicitClientId.trim()
        }
        val fromPrefs = getSavedWebClientId(context)
        if (!fromPrefs.isNullOrBlank()) {
            return fromPrefs
        }
        val resId = context.resources.getIdentifier("default_web_client_id", "string", context.packageName)
        if (resId != 0) {
            val fromRes = context.getString(resId).trim()
            if (fromRes.isNotBlank()) return fromRes
        }
        return DEFAULT_WEB_CLIENT_ID
    }

    /**
     * Signs in with Google using Android Credential Manager.
     */
    suspend fun signInWithGoogle(
        context: Context,
        serverClientId: String? = null
    ): Result<FirebaseUserState> = withContext(Dispatchers.IO) {
        val auth = getOrInitAuth(context)
            ?: return@withContext Result.failure(IllegalStateException("No se pudo iniciar el servicio de autenticación de Firebase en este dispositivo."))

        val effectiveClientId = resolveServerClientId(context, serverClientId)
        if (effectiveClientId.isNullOrBlank()) {
            return@withContext Result.failure(
                IllegalStateException(
                    "Falta el 'Web Client ID' de Google. Agrégalo en Ajustes de Nube > Configuración o habilita Google Sign-In en Firebase Console y descarga el nuevo google-services.json."
                )
            )
        }

        try {
            val credentialManager = CredentialManager.create(context)

            val rawNonce = UUID.randomUUID().toString()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(rawNonce.toByteArray())
            val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

            val googleIdOptionBuilder = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(false)
                .setNonce(hashedNonce)
                .setServerClientId(effectiveClientId)

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOptionBuilder.build())
                .build()

            val result: GetCredentialResponse = withContext(Dispatchers.Main) {
                credentialManager.getCredential(request = request, context = context)
            }

            val credential = result.credential
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken

                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                val authResult = auth.signInWithCredential(firebaseCredential).await()
                val user = authResult.user
                if (user != null) {
                    val userState = user.toUserState()
                    _currentUserState.value = userState
                    Result.success(userState)
                } else {
                    Result.failure(Exception("No se pudo obtener el perfil de usuario de Google."))
                }
            } else {
                Result.failure(Exception("Credencial no compatible con Google ID Token."))
            }
        } catch (e: GetCredentialCancellationException) {
            Result.failure(Exception("Inicio de sesión con Google cancelado."))
        } catch (e: GoogleIdTokenParsingException) {
            Result.failure(Exception("Error al procesar credencial de Google: ${e.message}"))
        } catch (e: GetCredentialException) {
            val msg = e.message ?: ""
            val errorText = when {
                msg.contains("10") || msg.contains("DEVELOPER_ERROR", ignoreCase = true) ->
                    "Error de configuración (Código 10): Asegúrate de haber registrado la huella digital SHA-1 ($PERMANENT_KEYSTORE_SHA1) en Firebase Console."
                msg.contains("12500") || msg.contains("SIGN_IN_FAILED", ignoreCase = true) ->
                    "Error 12500: Verifica que el proveedor Google esté activo en Firebase Authentication."
                msg.contains("No credential", ignoreCase = true) || msg.contains("cancelled", ignoreCase = true) ->
                    "No se seleccionó ninguna cuenta de Google o se canceló el selector."
                else -> "Error en Credential Manager: ${e.localizedMessage ?: e.message}"
            }
            Result.failure(Exception(errorText))
        } catch (e: FirebaseAuthException) {
            val msg = e.message ?: ""
            val formatted = when {
                msg.contains("disabled", ignoreCase = true) || e.errorCode.contains("OPERATION_NOT_ALLOWED") ->
                    "El proveedor Google está deshabilitado en Firebase Console. Actívalo en Authentication > Sign-in method."
                else -> "Error de Firebase Auth: ${e.localizedMessage ?: msg}"
            }
            Result.failure(Exception(formatted))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Signs in with Email and Password or Creates account if doesn't exist
     */
    suspend fun signInWithEmailPassword(
        context: Context,
        email: String,
        pass: String
    ): Result<FirebaseUserState> = withContext(Dispatchers.IO) {
        val auth = getOrInitAuth(context)
            ?: return@withContext Result.failure(IllegalStateException("Servicio de autenticación no listo."))

        val trimmedEmail = email.trim()
        val trimmedPass = pass.trim()

        if (trimmedEmail.isEmpty() || !trimmedEmail.contains("@")) {
            return@withContext Result.failure(IllegalArgumentException("Ingresa un correo electrónico válido."))
        }
        if (trimmedPass.length < 6) {
            return@withContext Result.failure(IllegalArgumentException("La contraseña debe tener al menos 6 caracteres."))
        }

        try {
            // Intentar inicio de sesión primero
            val res = try {
                auth.signInWithEmailAndPassword(trimmedEmail, trimmedPass).await()
            } catch (e: FirebaseAuthInvalidUserException) {
                // Usuario no existe todavía: crear cuenta automáticamente
                Log.d(TAG, "Usuario no existe, creando cuenta nueva: ${e.message}")
                auth.createUserWithEmailAndPassword(trimmedEmail, trimmedPass).await()
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                return@withContext Result.failure(Exception("Contraseña incorrecta para este correo o formato inválido."))
            }

            val user = res.user
                ?: return@withContext Result.failure(Exception("Usuario nulo al iniciar sesión."))

            val userState = user.toUserState()
            _currentUserState.value = userState
            Result.success(userState)
        } catch (e: FirebaseAuthException) {
            val msg = e.message ?: ""
            val formatted = when {
                msg.contains("disabled", ignoreCase = true) ->
                    "El inicio con Correo/Contraseña está deshabilitado en Firebase Console. Habilítalo en Authentication > Sign-in method."
                msg.contains("email address is already in use", ignoreCase = true) ->
                    "Este correo ya está registrado con otra contraseña."
                else -> "Error de autenticación: ${e.localizedMessage ?: msg}"
            }
            Result.failure(Exception(formatted))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Signs in anonymously allowing real-cloud Firestore storage without requiring login credentials.
     */
    suspend fun signInAnonymously(context: Context? = null): Result<FirebaseUserState> = withContext(Dispatchers.IO) {
        val auth = getOrInitAuth(context)
            ?: return@withContext Result.failure(IllegalStateException("No se pudo iniciar el servicio de autenticación."))

        try {
            val res = auth.signInAnonymously().await()
            val user = res.user
                ?: return@withContext Result.failure(Exception("Usuario nulo al iniciar sesión anónima"))

            val userState = FirebaseUserState(
                uid = user.uid,
                email = "anonimo@dispositivo.local",
                displayName = "Sesión Nube Anónima",
                photoUrl = null
            )
            _currentUserState.value = userState
            Result.success(userState)
        } catch (e: FirebaseAuthException) {
            val msg = e.message ?: ""
            val formatted = if (msg.contains("disabled", ignoreCase = true)) {
                "La autenticación anónima está deshabilitada en Firebase Console. Actívala en Authentication > Sign-in method > Anónimo."
            } else {
                "Error en autenticación anónima: ${e.localizedMessage ?: msg}"
            }
            Result.failure(Exception(formatted))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signOut(context: Context) = withContext(Dispatchers.IO) {
        try {
            val credentialManager = CredentialManager.create(context)
            withContext(Dispatchers.Main) {
                try {
                    credentialManager.clearCredentialState(ClearCredentialStateRequest())
                } catch (e: Exception) {
                    Log.w(TAG, "Error limpiando estado de CredentialManager: ${e.message}")
                }
            }
            getOrInitAuth(context)?.signOut()
            _currentUserState.value = null
        } catch (e: Exception) {
            Log.e(TAG, "Error cerrando sesión: ${e.message}", e)
        }
    }

    /**
     * Uploads personalized items (favorites, highlights, notes, and custom verses) to Firestore.
     */
    suspend fun uploadToFirestore(
        verses: List<VerseEntity>,
        context: Context? = null
    ): Result<Int> = withContext(Dispatchers.IO) {
        val auth = getOrInitAuth(context)
        val user = auth?.currentUser
            ?: return@withContext Result.failure(IllegalStateException("Debes iniciar sesión para sincronizar."))
        val db = getOrInitFirestore(context)
            ?: return@withContext Result.failure(IllegalStateException("Firestore no está disponible."))

        _syncOperationState.value = CloudSyncState.Loading

        try {
            val batch = db.batch()
            val userDocRef = db.collection(COLLECTION_USERS).document(user.uid)
            val versesCollection = userDocRef.collection(COLLECTION_VERSES)

            var uploadCount = 0
            val modifiedVerses = verses.filter {
                it.isFavorite || it.notes.isNotBlank() || it.highlightColor.isNotBlank() || it.isCustom
            }

            for (v in modifiedVerses) {
                val docId = if (v.isCustom) {
                    "custom_${v.id}"
                } else {
                    v.reference.replace("/", "_").replace(" ", "_").trim()
                }

                val docRef = versesCollection.document(docId)
                val map = hashMapOf(
                    "id" to v.id,
                    "reference" to v.reference,
                    "book" to v.book,
                    "chapterVerse" to v.chapterVerse,
                    "testament" to v.testament,
                    "text" to v.text,
                    "context" to v.context,
                    "topic" to v.topic,
                    "isFavorite" to v.isFavorite,
                    "notes" to v.notes,
                    "highlightColor" to v.highlightColor,
                    "highlightedPhrases" to v.highlightedPhrases,
                    "isCustom" to v.isCustom,
                    "bibleVersion" to v.bibleVersion,
                    "updatedAt" to v.updatedAt
                )
                batch.set(docRef, map, SetOptions.merge())
                uploadCount++
            }

            val metaDoc = userDocRef.collection(COLLECTION_METADATA).document("sync_info")
            batch.set(
                metaDoc,
                mapOf(
                    "lastSyncTimestamp" to System.currentTimeMillis(),
                    "totalItems" to uploadCount,
                    "userEmail" to (user.email ?: "anónimo")
                ),
                SetOptions.merge()
            )

            batch.commit().await()
            _syncOperationState.value = CloudSyncState.Success("$uploadCount elementos respaldados en la nube.")
            Result.success(uploadCount)
        } catch (e: FirebaseFirestoreException) {
            val msg = when (e.code) {
                FirebaseFirestoreException.Code.PERMISSION_DENIED ->
                    "Permiso denegado en Firestore. Asegúrate de configurar las Reglas de Seguridad en Firebase Console para permitir lectura y escritura a usuarios autenticados."
                FirebaseFirestoreException.Code.UNAVAILABLE ->
                    "Servicio de Firestore no disponible. Revisa tu conexión a internet."
                else -> "Error de base de datos Firestore (${e.code}): ${e.localizedMessage ?: e.message}"
            }
            _syncOperationState.value = CloudSyncState.Error(msg)
            Result.failure(Exception(msg))
        } catch (e: Exception) {
            _syncOperationState.value = CloudSyncState.Error(e.localizedMessage ?: "Error al subir a Firestore")
            Result.failure(e)
        }
    }

    /**
     * Downloads user personalized data from Firestore and merges it with Room Database.
     */
    suspend fun downloadFromFirestore(
        repository: VerseRepository,
        allCurrentVerses: List<VerseEntity>,
        context: Context? = null
    ): Result<Int> = withContext(Dispatchers.IO) {
        val auth = getOrInitAuth(context)
        val user = auth?.currentUser
            ?: return@withContext Result.failure(IllegalStateException("Debes iniciar sesión para restaurar."))
        val db = getOrInitFirestore(context)
            ?: return@withContext Result.failure(IllegalStateException("Firestore no está disponible."))

        _syncOperationState.value = CloudSyncState.Loading

        try {
            val userDocRef = db.collection(COLLECTION_USERS).document(user.uid)
            val snapshot = userDocRef.collection(COLLECTION_VERSES).get().await()

            if (snapshot.isEmpty) {
                _syncOperationState.value = CloudSyncState.Success("No hay versículos en tu nube aún.")
                return@withContext Result.success(0)
            }

            val currentMapByRef = allCurrentVerses.associateBy { it.reference.trim() }
            var restoredCount = 0

            for (doc in snapshot.documents) {
                val reference = doc.getString("reference") ?: continue
                val existing = currentMapByRef[reference.trim()]

                val isFavorite = doc.getBoolean("isFavorite") ?: false
                val notes = doc.getString("notes") ?: ""
                val highlightColor = doc.getString("highlightColor") ?: ""
                val highlightedPhrases = doc.getString("highlightedPhrases") ?: ""
                val isCustom = doc.getBoolean("isCustom") ?: false
                val bibleVersion = doc.getString("bibleVersion") ?: "RVR1960"
                val updatedAt = doc.getLong("updatedAt") ?: System.currentTimeMillis()

                if (existing != null) {
                    val updated = existing.copy(
                        isFavorite = isFavorite,
                        notes = notes,
                        highlightColor = highlightColor,
                        highlightedPhrases = highlightedPhrases,
                        bibleVersion = if (doc.contains("bibleVersion")) bibleVersion else existing.bibleVersion,
                        updatedAt = updatedAt
                    )
                    repository.updateVerse(updated)
                    restoredCount++
                } else if (isCustom) {
                    val newCustom = VerseEntity(
                        book = doc.getString("book") ?: "Personal",
                        chapterVerse = doc.getString("chapterVerse") ?: "",
                        reference = reference,
                        testament = doc.getString("testament") ?: "Nuevo Testamento",
                        text = doc.getString("text") ?: "",
                        context = doc.getString("context") ?: "",
                        topic = doc.getString("topic") ?: "Personal",
                        isFavorite = isFavorite,
                        notes = notes,
                        highlightColor = highlightColor,
                        highlightedPhrases = highlightedPhrases,
                        isCustom = true,
                        bibleVersion = bibleVersion,
                        updatedAt = updatedAt
                    )
                    repository.insertVerse(newCustom)
                    restoredCount++
                }
            }

            _syncOperationState.value = CloudSyncState.Success("$restoredCount versículos restaurados desde tu cuenta.")
            Result.success(restoredCount)
        } catch (e: FirebaseFirestoreException) {
            val msg = when (e.code) {
                FirebaseFirestoreException.Code.PERMISSION_DENIED ->
                    "Permiso denegado al leer Firestore. Revisa las Reglas de Seguridad en Firebase Console."
                FirebaseFirestoreException.Code.UNAVAILABLE ->
                    "Servicio de Firestore no disponible. Revisa tu conexión a internet."
                else -> "Error de base de datos Firestore (${e.code}): ${e.localizedMessage ?: e.message}"
            }
            _syncOperationState.value = CloudSyncState.Error(msg)
            Result.failure(Exception(msg))
        } catch (e: Exception) {
            _syncOperationState.value = CloudSyncState.Error(e.localizedMessage ?: "Error al descargar de Firestore")
            Result.failure(e)
        }
    }
}

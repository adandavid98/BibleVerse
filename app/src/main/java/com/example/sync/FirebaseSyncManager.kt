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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
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

    private const val TAG = "FirebaseSyncManager"
    private const val COLLECTION_USERS = "users"
    private const val COLLECTION_VERSES = "verses"
    private const val COLLECTION_METADATA = "metadata"

    private val auth: FirebaseAuth? by lazy {
        try {
            FirebaseAuth.getInstance()
        } catch (e: Exception) {
            Log.w(TAG, "FirebaseAuth no disponible: ${e.message}")
            null
        }
    }

    private val firestore: FirebaseFirestore? by lazy {
        try {
            FirebaseFirestore.getInstance()
        } catch (e: Exception) {
            Log.w(TAG, "FirebaseFirestore no disponible: ${e.message}")
            null
        }
    }

    private val _currentUserState = MutableStateFlow<FirebaseUserState?>(null)
    val currentUserState: StateFlow<FirebaseUserState?> = _currentUserState.asStateFlow()

    private val _syncOperationState = MutableStateFlow<CloudSyncState>(CloudSyncState.Idle)
    val syncOperationState: StateFlow<CloudSyncState> = _syncOperationState.asStateFlow()

    init {
        auth?.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            _currentUserState.value = user?.toUserState()
        }
    }

    private fun FirebaseUser.toUserState(): FirebaseUserState {
        return FirebaseUserState(
            uid = uid,
            email = email,
            displayName = displayName ?: email?.substringBefore('@'),
            photoUrl = photoUrl?.toString()
        )
    }

    fun isFirebaseInitialized(): Boolean {
        return auth != null && firestore != null
    }

    fun getCurrentUser(): FirebaseUserState? {
        val user = auth?.currentUser
        return user?.toUserState()
    }

    /**
     * Signs in with Google using Android Credential Manager.
     * Note: serverClientId can be provided from Firebase Console (Web Client ID).
     */
    suspend fun signInWithGoogle(
        context: Context,
        serverClientId: String? = null
    ): Result<FirebaseUserState> = withContext(Dispatchers.IO) {
        val authInstance = auth
            ?: return@withContext Result.failure(IllegalStateException("Firebase Auth no está inicializado. Asegúrate de configurar google-services.json."))

        try {
            val credentialManager = CredentialManager.create(context)

            // Random nonce for security
            val rawNonce = UUID.randomUUID().toString()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(rawNonce.toByteArray())
            val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

            val googleIdOptionBuilder = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(false)
                .setNonce(hashedNonce)

            if (!serverClientId.isNullOrBlank()) {
                googleIdOptionBuilder.setServerClientId(serverClientId.trim())
            }

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
                val authResult = authInstance.signInWithCredential(firebaseCredential).await()
                val user = authResult.user
                if (user != null) {
                    val userState = user.toUserState()
                    _currentUserState.value = userState
                    Result.success(userState)
                } else {
                    Result.failure(Exception("No se pudo obtener el perfil de usuario de Firebase"))
                }
            } else {
                Result.failure(Exception("Tipo de credencial recibido no reconocido"))
            }
        } catch (e: GetCredentialCancellationException) {
            Result.failure(Exception("Inicio de sesión cancelado"))
        } catch (e: GoogleIdTokenParsingException) {
            Result.failure(Exception("Error al procesar el token de Google: ${e.message}"))
        } catch (e: GetCredentialException) {
            Result.failure(Exception("Error de Credential Manager: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Signs in anonymously if Google credentials aren't set up yet,
     * allowing instant real-cloud testing without blocking the user.
     */
    suspend fun signInAnonymously(): Result<FirebaseUserState> = withContext(Dispatchers.IO) {
        val authInstance = auth
            ?: return@withContext Result.failure(IllegalStateException("Firebase Auth no está configurado."))
        try {
            val res = authInstance.signInAnonymously().await()
            val user = res.user ?: return@withContext Result.failure(Exception("Usuario nulo al iniciar sesión anónima"))
            val userState = FirebaseUserState(
                uid = user.uid,
                email = "anonimo@dispositivo.local",
                displayName = "Sesión Nube Anónima",
                photoUrl = null
            )
            _currentUserState.value = userState
            Result.success(userState)
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
            auth?.signOut()
            _currentUserState.value = null
        } catch (e: Exception) {
            Log.e(TAG, "Error cerrando sesión: ${e.message}", e)
        }
    }

    /**
     * Uploads all personalized items (favorites, highlights, notes, and custom verses) to Firestore
     * under users/{uid}/verses/{verseRefKey}.
     */
    suspend fun uploadToFirestore(
        verses: List<VerseEntity>
    ): Result<Int> = withContext(Dispatchers.IO) {
        val user = auth?.currentUser
            ?: return@withContext Result.failure(IllegalStateException("Debes iniciar sesión para sincronizar."))
        val db = firestore
            ?: return@withContext Result.failure(IllegalStateException("Firestore no está inicializado."))

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
                // Sanitize document ID using sanitized reference or custom ID
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
                    "updatedAt" to v.updatedAt
                )
                batch.set(docRef, map, SetOptions.merge())
                uploadCount++
            }

            // Also record sync timestamp metadata
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
        } catch (e: Exception) {
            _syncOperationState.value = CloudSyncState.Error(e.localizedMessage ?: "Error al subir a Firestore")
            Result.failure(e)
        }
    }

    /**
     * Downloads user personalized data from Firestore and merges it with the local Room Database.
     */
    suspend fun downloadFromFirestore(
        repository: VerseRepository,
        allCurrentVerses: List<VerseEntity>
    ): Result<Int> = withContext(Dispatchers.IO) {
        val user = auth?.currentUser
            ?: return@withContext Result.failure(IllegalStateException("Debes iniciar sesión para restaurar."))
        val db = firestore
            ?: return@withContext Result.failure(IllegalStateException("Firestore no está inicializado."))

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
                val updatedAt = doc.getLong("updatedAt") ?: System.currentTimeMillis()

                if (existing != null) {
                    val updated = existing.copy(
                        isFavorite = isFavorite,
                        notes = notes,
                        highlightColor = highlightColor,
                        highlightedPhrases = highlightedPhrases,
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
                        updatedAt = updatedAt
                    )
                    repository.insertVerse(newCustom)
                    restoredCount++
                }
            }

            _syncOperationState.value = CloudSyncState.Success("$restoredCount versículos restaurados desde tu cuenta.")
            Result.success(restoredCount)
        } catch (e: Exception) {
            _syncOperationState.value = CloudSyncState.Error(e.localizedMessage ?: "Error al descargar de Firestore")
            Result.failure(e)
        }
    }
}

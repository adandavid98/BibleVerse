package com.example.sync

data class FirebaseUserState(
    val uid: String,
    val email: String?,
    val displayName: String?,
    val photoUrl: String?
)

sealed class CloudSyncState {
    object Idle : CloudSyncState()
    object Loading : CloudSyncState()
    data class Success(val message: String) : CloudSyncState()
    data class Error(val message: String) : CloudSyncState()
}

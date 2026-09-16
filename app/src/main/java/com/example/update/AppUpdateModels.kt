package com.example.update

data class AppUpdateInfo(
    val tagName: String,
    val versionName: String,
    val releaseTitle: String,
    val releaseNotes: String,
    val htmlUrl: String,
    val apkDownloadUrl: String?,
    val apkFileName: String?,
    val apkSizeBytes: Long,
    val publishedAt: String,
    val isUpdateAvailable: Boolean
)

sealed class UpdateDownloadStatus {
    object Idle : UpdateDownloadStatus()
    object Checking : UpdateDownloadStatus()
    data class Available(val info: AppUpdateInfo) : UpdateDownloadStatus()
    object UpToDate : UpdateDownloadStatus()
    data class Downloading(val progress: Float, val downloadedBytes: Long, val totalBytes: Long) : UpdateDownloadStatus()
    data class ReadyToInstall(val apkPath: String, val info: AppUpdateInfo) : UpdateDownloadStatus()
    data class Error(val message: String) : UpdateDownloadStatus()
}

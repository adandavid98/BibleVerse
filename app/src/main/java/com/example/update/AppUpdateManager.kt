package com.example.update

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

object AppUpdateManager {

    private const val PREFS_NAME = "bible_app_update_prefs"
    private const val KEY_GITHUB_REPO = "github_repo_owner_name"
    private const val KEY_IGNORED_VERSION = "ignored_update_version"
    private const val KEY_POSTPONE_TIMESTAMP = "postpone_update_timestamp"
    private const val KEY_AUTO_CHECK_ENABLED = "auto_check_updates_enabled"
    const val DEFAULT_REPO = "adandavid98/BibleVerse"

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .build()
    }

    fun getGitHubRepo(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_GITHUB_REPO, DEFAULT_REPO) ?: DEFAULT_REPO
    }

    fun setGitHubRepo(context: Context, repo: String) {
        val cleaned = repo.trim().removePrefix("https://github.com/").removeSuffix("/")
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_GITHUB_REPO, cleaned).apply()
    }

    fun isAutoCheckEnabled(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_AUTO_CHECK_ENABLED, true)
    }

    fun setAutoCheckEnabled(context: Context, enabled: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_AUTO_CHECK_ENABLED, enabled).apply()
    }

    fun postponeUpdate(context: Context, hours: Int = 24) {
        val postponeUntil = System.currentTimeMillis() + (hours * 3600 * 1000L)
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putLong(KEY_POSTPONE_TIMESTAMP, postponeUntil).apply()
    }

    fun ignoreVersion(context: Context, versionTag: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_IGNORED_VERSION, versionTag).apply()
    }

    fun shouldShowAutomaticPrompt(context: Context, versionTag: String): Boolean {
        if (!isAutoCheckEnabled(context)) return false
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val ignored = prefs.getString(KEY_IGNORED_VERSION, null)
        if (ignored.equals(versionTag, ignoreCase = true)) return false

        val postponedUntil = prefs.getLong(KEY_POSTPONE_TIMESTAMP, 0L)
        if (System.currentTimeMillis() < postponedUntil) return false

        return true
    }

    /**
     * Checks GitHub Releases for the latest release in the configured repository.
     */
    suspend fun checkForUpdate(context: Context): Result<AppUpdateInfo> = withContext(Dispatchers.IO) {
        try {
            val repo = getGitHubRepo(context)
            val apiUrl = "https://api.github.com/repos/$repo/releases/latest"

            val request = Request.Builder()
                .url(apiUrl)
                .header("Accept", "application/vnd.github.v3+json")
                .header("User-Agent", "VersiculosBiblicos-AndroidApp")
                .build()

            val response = httpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                if (response.code == 404) {
                    return@withContext Result.failure(
                        Exception("No se encontraron lanzamientos en el repositorio '$repo'. Asegúrate de haber publicado al menos un Release.")
                    )
                }
                return@withContext Result.failure(
                    Exception("Error del servidor de GitHub (${response.code}): ${response.message}")
                )
            }

            val bodyString = response.body?.string() ?: return@withContext Result.failure(
                Exception("Respuesta vacía desde GitHub Releases.")
            )

            val json = JSONObject(bodyString)
            val tagName = json.optString("tag_name", "v1.0")
            val releaseName = json.optString("name", tagName)
            val releaseNotes = json.optString("body", "Sin notas de la versión.")
            val htmlUrl = json.optString("html_url", "https://github.com/$repo/releases")
            val publishedAt = json.optString("published_at", "")

            var apkUrl: String? = null
            var apkName: String? = null
            var apkSize: Long = 0L

            val assets = json.optJSONArray("assets")
            if (assets != null) {
                for (i in 0 until assets.length()) {
                    val asset = assets.getJSONObject(i)
                    val name = asset.optString("name", "")
                    if (name.endsWith(".apk", ignoreCase = true)) {
                        apkUrl = asset.optString("browser_download_url")
                        apkName = name
                        apkSize = asset.optLong("size", 0L)
                        break
                    }
                }
            }

            val currentVersion = BuildConfig.VERSION_NAME
            val isNewer = compareVersions(tagName, currentVersion) > 0

            val info = AppUpdateInfo(
                tagName = tagName,
                versionName = tagName.removePrefix("v").removePrefix("V"),
                releaseTitle = if (releaseName.isNotBlank()) releaseName else tagName,
                releaseNotes = releaseNotes,
                htmlUrl = htmlUrl,
                apkDownloadUrl = apkUrl,
                apkFileName = apkName,
                apkSizeBytes = apkSize,
                publishedAt = publishedAt,
                isUpdateAvailable = isNewer
            )

            Result.success(info)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Downloads APK file and reports progress.
     */
    suspend fun downloadApk(
        context: Context,
        downloadUrl: String,
        onProgress: (progress: Float, downloadedBytes: Long, totalBytes: Long) -> Unit
    ): Result<File> = withContext(Dispatchers.IO) {
        try {
            val updatesDir = File(context.cacheDir, "updates").apply { mkdirs() }
            val destFile = File(updatesDir, "VersiculosBiblicos_latest.apk")
            if (destFile.exists()) {
                destFile.delete()
            }

            val request = Request.Builder()
                .url(downloadUrl)
                .header("User-Agent", "VersiculosBiblicos-AndroidApp")
                .build()

            val response = httpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("Error al descargar APK (${response.code})"))
            }

            val responseBody = response.body ?: return@withContext Result.failure(Exception("Cuerpo de descarga vacío"))
            val totalBytes = responseBody.contentLength()

            responseBody.byteStream().use { input ->
                FileOutputStream(destFile).use { output ->
                    val buffer = ByteArray(8 * 1024)
                    var bytesRead: Int
                    var totalDownloaded = 0L

                    while (input.read(buffer).also { bytesRead = it } != -1) {
                        output.write(buffer, 0, bytesRead)
                        totalDownloaded += bytesRead
                        val progress = if (totalBytes > 0) totalDownloaded.toFloat() / totalBytes.toFloat() else 0f
                        onProgress(progress, totalDownloaded, totalBytes)
                    }
                    output.flush()
                }
            }

            Result.success(destFile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Triggers package installer intent for the downloaded APK using FileProvider.
     */
    fun installApk(context: Context, apkFile: File) {
        if (!apkFile.exists()) {
            Toast.makeText(context, "El archivo APK no existe o fue eliminado.", Toast.LENGTH_SHORT).show()
            return
        }

        // On Android 8.0+ verify unknown app install permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!context.packageManager.canRequestPackageInstalls()) {
                Toast.makeText(
                    context,
                    "Permite instalar aplicaciones de fuentes desconocidas para completar la actualización",
                    Toast.LENGTH_LONG
                ).show()
                val settingsIntent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).apply {
                    data = Uri.parse("package:${context.packageName}")
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(settingsIntent)
                return
            }
        }

        try {
            val contentUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                apkFile
            )

            val installIntent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(contentUri, "application/vnd.android.package-archive")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
            }
            context.startActivity(installIntent)
        } catch (e: Exception) {
            Toast.makeText(context, "Error al iniciar el instalador: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
        }
    }

    fun openBrowser(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "No se pudo abrir el enlace: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Compares two semantic version strings (e.g., "v1.2.0" vs "1.1").
     * Returns > 0 if version1 > version2, < 0 if version1 < version2, 0 if equal.
     */
    fun compareVersions(version1: String, version2: String): Int {
        val clean1 = version1.trim().removePrefix("v").removePrefix("V").split("-")[0]
        val clean2 = version2.trim().removePrefix("v").removePrefix("V").split("-")[0]

        val parts1 = clean1.split(".").mapNotNull { it.toIntOrNull() }
        val parts2 = clean2.split(".").mapNotNull { it.toIntOrNull() }

        val maxLen = maxOf(parts1.size, parts2.size)
        for (i in 0 until maxLen) {
            val p1 = parts1.getOrElse(i) { 0 }
            val p2 = parts2.getOrElse(i) { 0 }
            if (p1 != p2) {
                return p1.compareTo(p2)
            }
        }
        return 0
    }
}

package com.example.sync

import android.content.Context
import com.example.data.model.VerseEntity
import com.example.data.repository.VerseRepository
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

object CloudSyncManager {

    private const val PREFS_NAME = "bible_cloud_sync_prefs"
    private const val KEY_SYNC_ID = "cloud_sync_id"
    private const val KEY_LAST_SYNC_TIME = "last_sync_time"
    private const val KEY_DEVICE_NAME = "sync_device_name"

    fun getOrGenerateSyncId(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        var syncId = prefs.getString(KEY_SYNC_ID, null)
        if (syncId.isNullOrBlank()) {
            val randomPart = UUID.randomUUID().toString().substring(0, 8).uppercase()
            syncId = "SYNC-$randomPart"
            prefs.edit().putString(KEY_SYNC_ID, syncId).apply()
        }
        return syncId
    }

    fun setSyncId(context: Context, syncId: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_SYNC_ID, syncId.trim()).apply()
    }

    fun getLastSyncTime(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val timeMillis = prefs.getLong(KEY_LAST_SYNC_TIME, 0L)
        return if (timeMillis == 0L) {
            "Nunca sincronizado"
        } else {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            sdf.format(Date(timeMillis))
        }
    }

    fun recordSyncSuccess(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putLong(KEY_LAST_SYNC_TIME, System.currentTimeMillis()).apply()
    }

    fun generateCloudPayload(syncId: String, verses: List<VerseEntity>): String {
        val root = JSONObject()
        root.put("version", 1)
        root.put("syncId", syncId)
        root.put("timestamp", System.currentTimeMillis())

        val array = JSONArray()
        for (v in verses) {
            // Only sync modified verses (favorites, notes, highlights, or custom verses) or all
            if (v.isFavorite || v.notes.isNotBlank() || v.highlightColor.isNotBlank() || v.isCustom) {
                val item = JSONObject().apply {
                    put("id", v.id)
                    put("book", v.book)
                    put("chapterVerse", v.chapterVerse)
                    put("reference", v.reference)
                    put("testament", v.testament)
                    put("text", v.text)
                    put("context", v.context)
                    put("topic", v.topic)
                    put("isFavorite", v.isFavorite)
                    put("notes", v.notes)
                    put("highlightColor", v.highlightColor)
                    put("highlightedPhrases", v.highlightedPhrases)
                    put("isCustom", v.isCustom)
                    put("updatedAt", v.updatedAt)
                }
                array.put(item)
            }
        }
        root.put("items", array)
        return root.toString(2)
    }

    suspend fun applyCloudPayload(
        payloadJson: String,
        repository: VerseRepository,
        allCurrentVerses: List<VerseEntity>
    ): Int {
        val root = JSONObject(payloadJson)
        val items = root.optJSONArray("items") ?: return 0
        var mergedCount = 0

        val currentMapByRef = allCurrentVerses.associateBy { it.reference.trim() }

        for (i in 0 until items.length()) {
            val obj = items.getJSONObject(i)
            val reference = obj.getString("reference").trim()
            val existing = currentMapByRef[reference]

            if (existing != null) {
                val incomingUpdatedAt = obj.optLong("updatedAt", System.currentTimeMillis())
                val updated = existing.copy(
                    isFavorite = if (obj.has("isFavorite")) obj.getBoolean("isFavorite") else existing.isFavorite,
                    notes = if (obj.has("notes")) obj.getString("notes") else existing.notes,
                    highlightColor = if (obj.has("highlightColor")) obj.getString("highlightColor") else existing.highlightColor,
                    highlightedPhrases = if (obj.has("highlightedPhrases")) obj.getString("highlightedPhrases") else existing.highlightedPhrases,
                    updatedAt = incomingUpdatedAt
                )
                repository.updateVerse(updated)
                mergedCount++
            } else if (obj.optBoolean("isCustom", false)) {
                // New custom verse from another device
                val newCustom = VerseEntity(
                    book = obj.optString("book", "Personal"),
                    chapterVerse = obj.optString("chapterVerse", ""),
                    reference = reference,
                    testament = obj.optString("testament", "Nuevo Testamento"),
                    text = obj.optString("text", ""),
                    context = obj.optString("context", ""),
                    topic = obj.optString("topic", "Personal"),
                    isFavorite = obj.optBoolean("isFavorite", false),
                    notes = obj.optString("notes", ""),
                    highlightColor = obj.optString("highlightColor", ""),
                    highlightedPhrases = obj.optString("highlightedPhrases", ""),
                    isCustom = true,
                    updatedAt = obj.optLong("updatedAt", System.currentTimeMillis())
                )
                repository.insertVerse(newCustom)
                mergedCount++
            }
        }
        return mergedCount
    }
}

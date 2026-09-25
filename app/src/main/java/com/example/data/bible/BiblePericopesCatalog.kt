package com.example.data.bible

import android.content.Context
import android.util.Log
import org.json.JSONObject
import java.util.concurrent.ConcurrentHashMap
import java.util.zip.GZIPInputStream

object BiblePericopesCatalog {

    private const val TAG = "BiblePericopesCatalog"
    private val headings = ConcurrentHashMap<String, String>()
    private val chaptersWithHeadings = ConcurrentHashMap.newKeySet<String>()

    @Volatile
    private var initialized = false

    fun init(context: Context) {
        if (initialized) return
        synchronized(this) {
            if (initialized) return
            try {
                loadFromAssets(context.applicationContext)
                initialized = true
                Log.d(TAG, "Cargadas ${headings.size} perícopas bíblicas correctamente.")
            } catch (e: Exception) {
                Log.e(TAG, "Error cargando catálogo de perícopas", e)
            }
        }
    }

    fun ensureInitialized(context: Context) {
        if (!initialized) {
            init(context)
        }
    }

    fun hasHeadingsForChapter(bookId: Int, chapter: Int): Boolean {
        return chaptersWithHeadings.contains("${bookId}_${chapter}")
    }

    fun getHeading(
        context: Context? = null,
        bookId: Int,
        chapter: Int,
        verse: Int,
        version: String = "RVR1960"
    ): String? {
        if (!initialized && context != null) {
            ensureInitialized(context)
        }

        val normVersion = when (version.uppercase().trim()) {
            "RV1960", "REINA-VALERA 1960" -> "RVR1960"
            else -> version.uppercase().trim()
        }

        // 1. Translation-specific override (e.g. 40_8_5@NTV)
        if (normVersion.isNotEmpty() && normVersion != "RVR1960") {
            val versionKey = "${bookId}_${chapter}_${verse}@$normVersion"
            val versionTitle = headings[versionKey]
            if (!versionTitle.isNullOrBlank()) {
                return versionTitle
            }
        }

        // 2. Canonical Spanish heading (RVR1960 and universal fallback)
        val canonicalKey = "${bookId}_${chapter}_${verse}"
        return headings[canonicalKey]
    }

    private fun loadFromAssets(context: Context) {
        var loaded = false
        // Try gzipped JSON first (compact asset)
        try {
            context.assets.open("bible/pericopes_es.json.gz").use { rawIn ->
                GZIPInputStream(rawIn).bufferedReader(Charsets.UTF_8).use { reader ->
                    parseJson(reader.readText())
                    loaded = true
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "No se pudo leer pericopes_es.json.gz, probando json sin comprimir...", e)
        }

        if (!loaded) {
            // Fallback to uncompressed JSON
            context.assets.open("bible/pericopes_es.json").bufferedReader(Charsets.UTF_8).use { reader ->
                parseJson(reader.readText())
            }
        }
    }

    private fun parseJson(jsonString: String) {
        val root = JSONObject(jsonString)
        val keys = root.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val title = root.optString(key, "").trim()
            if (title.isNotEmpty()) {
                headings[key] = title

                val base = if (key.contains('@')) key.substringBefore('@') else key
                val parts = base.split('_')
                if (parts.size >= 2) {
                    chaptersWithHeadings.add("${parts[0]}_${parts[1]}")
                }
            }
        }
    }
}

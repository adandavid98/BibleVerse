package com.example.data.bible

import android.content.Context
import android.util.Log
import org.json.JSONObject
import java.io.BufferedInputStream
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
        val assetNames = listOf(
            "bible/pericopes_es.json",
            "bible/pericopes_es.json.gz"
        )

        for (name in assetNames) {
            try {
                context.assets.open(name).use { rawStream ->
                    val bis = BufferedInputStream(rawStream)
                    bis.mark(4)
                    val b1 = bis.read()
                    val b2 = bis.read()
                    bis.reset()

                    val jsonString = if (b1 == 0x1f && b2 == 0x8b) {
                        GZIPInputStream(bis).bufferedReader(Charsets.UTF_8).use { it.readText() }
                    } else {
                        bis.bufferedReader(Charsets.UTF_8).use { it.readText() }
                    }
                    parseJson(jsonString)
                    Log.d(TAG, "Cargadas exitosamente ${headings.size} perícopas desde asset: $name")
                    return
                }
            } catch (e: Exception) {
                Log.w(TAG, "No se pudo cargar desde asset: $name", e)
            }
        }
        Log.e(TAG, "ADVERTENCIA: No se pudo cargar perícopas de ningún asset disponible.")
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

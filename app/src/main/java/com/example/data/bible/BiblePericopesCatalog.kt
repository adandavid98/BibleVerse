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

    private val DEDICATED_VERSIONS = setOf("TLA", "DHH", "DHH94PC", "NBLA", "LBLA", "NTV", "NVI")

    fun isDedicatedVersion(version: String): Boolean {
        val norm = when (version.uppercase().trim()) {
            "RV1960", "REINA-VALERA 1960" -> "RVR1960"
            else -> version.uppercase().trim()
        }
        return DEDICATED_VERSIONS.contains(norm)
    }

    fun hasHeadingsForChapter(bookId: Int, chapter: Int): Boolean {
        return chaptersWithHeadings.contains("${bookId}_${chapter}")
    }

    fun hasHeadingsForChapter(bookId: Int, chapter: Int, version: String): Boolean {
        val norm = when (version.uppercase().trim()) {
            "RV1960", "REINA-VALERA 1960" -> "RVR1960"
            else -> version.uppercase().trim()
        }
        return if (DEDICATED_VERSIONS.contains(norm)) {
            chaptersWithHeadings.contains("${bookId}_${chapter}@$norm")
        } else {
            chaptersWithHeadings.contains("${bookId}_${chapter}")
        }
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

        // 1. Translation-specific dedicated heading (e.g. 40_8_5@NTV)
        if (normVersion.isNotEmpty() && normVersion != "RVR1960") {
            val versionKey = "${bookId}_${chapter}_${verse}@$normVersion"
            val versionTitle = headings[versionKey]
            if (!versionTitle.isNullOrBlank()) {
                return versionTitle
            }
            // If this translation has its own dedicated pericope editorial committee,
            // DO NOT inject RVR1960 headings onto verses where this version has none!
            if (DEDICATED_VERSIONS.contains(normVersion)) {
                return null
            }
        }

        // 2. Canonical Spanish heading (RVR1960 and generic versions without dedicated catalog)
        val canonicalKey = "${bookId}_${chapter}_${verse}"
        return headings[canonicalKey]
    }

    private fun loadFromAssets(context: Context) {
        // 1. Base canónica RVR1960 (fallback universal para los 1,189 capítulos)
        loadAssetFile(context, "bible/pericopes_es.json", null)

        // 2. Catálogos dedicados por versión con redacción y estilo editorial propio
        loadAssetFile(context, "bible/pericopes_nvi.json", "NVI")
        loadAssetFile(context, "bible/pericopes_ntv.json", "NTV")
        loadAssetFile(context, "bible/pericopes_tla.json", "TLA")
        loadAssetFile(context, "bible/pericopes_dhh.json", "DHH")
        loadAssetFile(context, "bible/pericopes_lbla.json", "LBLA")
        loadAssetFile(context, "bible/pericopes_nbla.json", "NBLA")
    }

    private fun loadAssetFile(context: Context, assetPath: String, versionTag: String?) {
        try {
            context.assets.open(assetPath).use { rawStream ->
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
                parseJson(jsonString, versionTag)
                Log.d(TAG, "Cargado asset $assetPath (Versión: ${versionTag ?: "CANÓNICA"})")
            }
        } catch (e: Exception) {
            Log.w(TAG, "No se pudo cargar asset: $assetPath", e)
        }
    }

    private fun parseJson(jsonString: String, versionTag: String? = null) {
        val root = JSONObject(jsonString)
        val keys = root.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val title = root.optString(key, "").trim()
            if (title.isNotEmpty()) {
                val finalKey = when {
                    key.contains('@') -> key
                    versionTag != null -> "${key}@${versionTag.uppercase()}"
                    else -> key
                }

                headings[finalKey] = title

                val base = if (finalKey.contains('@')) finalKey.substringBefore('@') else finalKey
                val parts = base.split('_')
                if (parts.size >= 2) {
                    chaptersWithHeadings.add("${parts[0]}_${parts[1]}")
                    if (versionTag != null) {
                        chaptersWithHeadings.add("${parts[0]}_${parts[1]}@${versionTag.uppercase()}")
                    }
                }
            }
        }
    }
}

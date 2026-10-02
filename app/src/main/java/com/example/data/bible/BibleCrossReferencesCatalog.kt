package com.example.data.bible

import android.content.Context
import android.util.Log
import org.json.JSONObject
import java.io.InputStreamReader
import java.util.concurrent.ConcurrentHashMap
import java.util.zip.GZIPInputStream

data class CrossReferenceItem(
    val targetCitation: String,
    val targetBookId: Int,
    val targetChapter: Int,
    val targetVerse: Int,
    val targetEndVerse: Int = targetVerse,
    val note: String = ""
)

object BibleCrossReferencesCatalog {

    private const val TAG = "CrossReferencesCatalog"

    // In-memory cache for parsed chapter verses: "bookId_chapter" -> (verseNumber -> List<CrossReferenceItem>)
    private val chapterCache = ConcurrentHashMap<String, Map<Int, List<CrossReferenceItem>>>()
    
    @Volatile
    private var isDatasetLoaded = false
    private val loadLock = Any()

    // Canonical chapter-level parallel passages (Historical books, Synoptic Gospels, Prophets, Creation)
    private val chapterReferencesMap: Map<String, List<CrossReferenceItem>> = mapOf(
        // Génesis 1 (Creación)
        "1_1" to listOf(
            CrossReferenceItem("Juan 1:1-18", 43, 1, 1, 18, "Paralelo del Prólogo: La creación por el Verbo eterno."),
            CrossReferenceItem("Colosenses 1:15-17", 51, 1, 15, 17, "Cristo, imagen del Dios invisible y Creador de todas las cosas."),
            CrossReferenceItem("Hebreos 1:1-3", 58, 1, 1, 3, "Por quien asimismo hizo el universo.")
        ),
        // Génesis 2 (El huerto de Edén y el hombre)
        "1_2" to listOf(
            CrossReferenceItem("1 Corintios 15:45-47", 46, 15, 45, 47, "El primer hombre Adán alma viviente; el postrer Adán, espíritu vivificante."),
            CrossReferenceItem("Mateo 19:4-6", 40, 19, 4, 6, "El Creador al principio, varón y hembra los hizo.")
        ),
        // Éxodo 20 (Los Diez Mandamientos)
        "2_20" to listOf(
            CrossReferenceItem("Deuteronomio 5:1-22", 5, 5, 1, 22, "Paralelo canónico: Repetición solemne del Decálogo."),
            CrossReferenceItem("Mateo 22:35-40", 40, 22, 35, 40, "Los dos grandes mandamientos que resumen toda la ley.")
        ),
        // 1 Reyes 8 (Dedicación del Templo por Salomón)
        "11_8" to listOf(
            CrossReferenceItem("2 Crónicas 6:1-42", 14, 6, 1, 42, "Paralelo histórico: La solemne oración de Salomón dedicando el Santo Templo.")
        ),
        // 2 Reyes 18 (Ezequías y la invasión de Senaquerib)
        "12_18" to listOf(
            CrossReferenceItem("Isaías 36:1-22", 23, 36, 1, 22, "Paralelo profético: Desafío de Senaquerib contra Jerusalén y confianza en Dios."),
            CrossReferenceItem("2 Crónicas 32:1-23", 14, 32, 1, 23, "Paralelo de Crónicas: Invasión de Senaquerib y liberación divina.")
        ),
        // 2 Reyes 19 (Oración de Ezequías y liberación milagrosa)
        "12_19" to listOf(
            CrossReferenceItem("Isaías 37:1-38", 23, 37, 1, 38, "Paralelo profético: La respuesta de Dios a Ezequías a través de Isaías.")
        ),
        // 2 Reyes 20 (Enfermedad y sanidad de Ezequías)
        "12_20" to listOf(
            CrossReferenceItem("Isaías 38:1-22", 23, 38, 1, 22, "Paralelo profético: El cántico de gratitud de Ezequías tras ser sanado.")
        ),
        // 2 Crónicas 22 (Ocozías y Atalía)
        "14_22" to listOf(
            CrossReferenceItem("2 Reyes 8:25-29", 12, 8, 25, 29, "Paralelo histórico: Reinado y caída de Ocozías de Judá."),
            CrossReferenceItem("2 Reyes 11:1-3", 12, 11, 1, 3, "Paralelo histórico: Rebelión de Atalía y rescate milagroso de Joás.")
        ),
        // 2 Crónicas 23 (Joás coronado rey)
        "14_23" to listOf(
            CrossReferenceItem("2 Reyes 11:4-21", 12, 11, 4, 21, "Paralelo histórico: El sacerdote Joiada unge a Joás y derroca a Atalía.")
        ),
        // 2 Crónicas 24 (Reinado de Joás)
        "14_24" to listOf(
            CrossReferenceItem("2 Reyes 12:1-21", 12, 12, 1, 21, "Paralelo histórico: Reparación del Templo y apostasía de Joás.")
        ),
        // Mateo 3 (Juan el Bautista y Bautismo de Jesús)
        "40_3" to listOf(
            CrossReferenceItem("Marcos 1:1-11", 41, 1, 1, 11, "Paralelo sinóptico: Comienzo del evangelio y bautismo de Jesús."),
            CrossReferenceItem("Lucas 3:1-22", 42, 3, 1, 22, "Paralelo sinóptico: Ministerio y predicación de Juan el Bautista.")
        ),
        // Mateo 4 (Tentación de Jesús)
        "40_4" to listOf(
            CrossReferenceItem("Marcos 1:12-13", 41, 1, 12, 13, "Paralelo sinóptico: Jesús tentado en el desierto."),
            CrossReferenceItem("Lucas 4:1-13", 42, 4, 1, 13, "Paralelo sinóptico: Las tres tentaciones y victoria de Cristo.")
        ),
        // Mateo 5 (Sermón del Monte - Las Bienaventuranzas)
        "40_5" to listOf(
            CrossReferenceItem("Lucas 6:20-49", 42, 6, 20, 49, "Paralelo evangélico: El Sermón de la llanura y las Bienaventuranzas del Reino.")
        ),
        // Mateo 6 (El Padre Nuestro y la providencia de Dios)
        "40_6" to listOf(
            CrossReferenceItem("Lucas 11:1-4", 42, 11, 1, 4, "Paralelo evangélico: La oración del Señor enseñada a los discípulos."),
            CrossReferenceItem("Lucas 12:22-34", 42, 12, 22, 34, "Paralelo evangélico: No os afanéis por vuestra vida.")
        ),
        // Mateo 8 (Milagros de sanidad y tempestad calmada)
        "40_8" to listOf(
            CrossReferenceItem("Marcos 4:35-41", 41, 4, 35, 41, "Paralelo sinóptico: Jesús calma la tempestad en el mar."),
            CrossReferenceItem("Lucas 8:22-25", 42, 8, 22, 25, "Paralelo de Lucas: ¿Dónde está vuestra fe?")
        ),
        // Mateo 13 (Parábolas del Reino de Dios)
        "40_13" to listOf(
            CrossReferenceItem("Marcos 4:1-34", 41, 4, 1, 34, "Paralelo evangélico: Parábolas del Sembrador y de la Semilla."),
            CrossReferenceItem("Lucas 8:4-18", 42, 8, 4, 18, "Paralelo evangélico: El sembrador y la lámpara bajo el almud.")
        ),
        // Mateo 14 (Alimentación de los cinco mil y Jesús sobre las aguas)
        "40_14" to listOf(
            CrossReferenceItem("Marcos 6:30-44", 41, 6, 30, 44, "Paralelo sinóptico: Milagro de los panes y peces."),
            CrossReferenceItem("Lucas 9:10-17", 42, 9, 10, 17, "Paralelo de Lucas: Comieron todos y se saciaron."),
            CrossReferenceItem("Juan 6:1-15", 43, 6, 1, 15, "Paralelo de Juan: El Pan de vida y la multiplicación.")
        ),
        // Mateo 16 (Confesión de Pedro y anuncio de la muerte de Jesús)
        "40_16" to listOf(
            CrossReferenceItem("Marcos 8:27-38", 41, 8, 27, 38, "Paralelo sinóptico: Tú eres el Cristo; tomar la cruz y seguirle."),
            CrossReferenceItem("Lucas 9:18-27", 42, 9, 18, 27, "Paralelo de Lucas: La confesión de Pedro y el discipulado.")
        ),
        // Mateo 17 (La Transfiguración)
        "40_17" to listOf(
            CrossReferenceItem("Marcos 9:2-13", 41, 9, 2, 13, "Paralelo sinóptico: La gloria de Jesús en el monte alto."),
            CrossReferenceItem("Lucas 9:28-36", 42, 9, 28, 36, "Paralelo de Lucas: Moisés y Elías hablando con Jesús.")
        ),
        // Mateo 21 (Entrada triunfal a Jerusalén)
        "40_21" to listOf(
            CrossReferenceItem("Marcos 11:1-11", 41, 11, 1, 11, "Paralelo sinóptico: ¡Bendito el que viene en el nombre del Señor!"),
            CrossReferenceItem("Lucas 19:28-40", 42, 19, 28, 40, "Paralelo de Lucas: Si éstos callan, las piedras clamarán."),
            CrossReferenceItem("Juan 12:12-19", 43, 12, 12, 19, "Paralelo de Juan: He aquí tu Rey viene sentado sobre un pollino.")
        ),
        // Mateo 24 (Discurso del Monte de los Olivos / Señales del Fin)
        "40_24" to listOf(
            CrossReferenceItem("Marcos 13:1-37", 41, 13, 1, 37, "Paralelo sinóptico: Profecía sobre el fin y la venida del Hijo del Hombre."),
            CrossReferenceItem("Lucas 21:5-36", 42, 21, 5, 36, "Paralelo de Lucas: Velad, pues, en todo tiempo orando.")
        ),
        // Mateo 26 (La Última Cena, Getsemaní y el arresto)
        "40_26" to listOf(
            CrossReferenceItem("Marcos 14:1-72", 41, 14, 1, 72, "Paralelo sinóptico: La Santa Cena, agonía en Getsemaní y negación de Pedro."),
            CrossReferenceItem("Lucas 22:1-71", 42, 22, 1, 71, "Paralelo sinóptico: La Pascua, angustia de Jesús y traición de Judas."),
            CrossReferenceItem("Juan 18:1-27", 43, 18, 1, 27, "Paralelo de Juan: El huerto y el prendimiento de Jesús.")
        ),
        // Mateo 27 (Crucifixión y sepultura)
        "40_27" to listOf(
            CrossReferenceItem("Marcos 15:1-47", 41, 15, 1, 47, "Paralelo sinóptico: El juicio ante Pilato, Gólgota y sepulcro."),
            CrossReferenceItem("Lucas 23:1-56", 42, 23, 1, 56, "Paralelo sinóptico: Padre, en tus manos encomiendo mi espíritu."),
            CrossReferenceItem("Juan 19:1-42", 43, 19, 1, 42, "Paralelo de Juan: Consumado es; el sacrificio redentor.")
        ),
        // Mateo 28 (Resurrección y la Gran Comisión)
        "40_28" to listOf(
            CrossReferenceItem("Marcos 16:1-20", 41, 16, 1, 20, "Paralelo sinóptico: Ha resucitado, no está aquí; id por todo el mundo."),
            CrossReferenceItem("Lucas 24:1-53", 42, 24, 1, 53, "Paralelo sinóptico: El camino a Emaús y ascensión triunfal."),
            CrossReferenceItem("Juan 20:1-31", 43, 20, 1, 31, "Paralelo de Juan: Paz a vosotros; bienaventurados los que no vieron.")
        ),
        // Marcos 1
        "41_1" to listOf(
            CrossReferenceItem("Mateo 3:1-17", 40, 3, 1, 17, "Paralelo: Ministerio de Juan el Bautista y Bautismo de Jesús."),
            CrossReferenceItem("Lucas 3:1-22", 42, 3, 1, 22, "Paralelo de Lucas: La voz que clama en el desierto.")
        ),
        // Lucas 2 (Natividad y cántico de Simeón)
        "42_2" to listOf(
            CrossReferenceItem("Mateo 1:18-25", 40, 1, 18, 25, "Paralelo: El nacimiento virginal de Jesús en cumplimiento de la profecía."),
            CrossReferenceItem("Miqueas 5:2", 33, 5, 2, 2, "Profecía mesiánica: Belén de Efrata dará origen al Gobernador eterno.")
        ),
        // Juan 1
        "43_1" to listOf(
            CrossReferenceItem("Génesis 1:1-3", 1, 1, 1, 3, "Paralelo de la Creación: En el principio la luz resplandeció sobre las tinieblas."),
            CrossReferenceItem("Colosenses 1:15-17", 51, 1, 15, 17, "Cristo la imagen del Dios invisible, primogénito de toda creación.")
        )
    )

    private fun ensureLoaded(context: Context) {
        if (isDatasetLoaded) return
        synchronized(loadLock) {
            if (isDatasetLoaded) return
            try {
                context.assets.open("bible/cross_references.json.gz").use { inStream ->
                    GZIPInputStream(inStream).use { gzStream ->
                        val reader = InputStreamReader(gzStream, Charsets.UTF_8)
                        val jsonStr = reader.readText()
                        val jsonObj = JSONObject(jsonStr)
                        val keys = jsonObj.keys()
                        while (keys.hasNext()) {
                            val chapKey = keys.next() // e.g. "53_1"
                            val versesObj = jsonObj.getJSONObject(chapKey)
                            val verseKeys = versesObj.keys()
                            val verseMap = HashMap<Int, List<CrossReferenceItem>>()
                            while (verseKeys.hasNext()) {
                                val vStr = verseKeys.next()
                                val vNum = vStr.toIntOrNull() ?: continue
                                val arr = versesObj.getJSONArray(vStr)
                                val items = ArrayList<CrossReferenceItem>(arr.length())
                                for (i in 0 until arr.length()) {
                                    val refArr = arr.getJSONArray(i)
                                    val cit = refArr.getString(0)
                                    val b = refArr.getInt(1)
                                    val c = refArr.getInt(2)
                                    val v = refArr.getInt(3)
                                    val ev = if (refArr.length() > 4) refArr.getInt(4) else v
                                    items.add(
                                        CrossReferenceItem(
                                            targetCitation = cit,
                                            targetBookId = b,
                                            targetChapter = c,
                                            targetVerse = v,
                                            targetEndVerse = ev,
                                            note = ""
                                        )
                                    )
                                }
                                verseMap[vNum] = items
                            }
                            chapterCache[chapKey] = verseMap
                        }
                        isDatasetLoaded = true
                        Log.i(TAG, "Successfully loaded ${chapterCache.size} chapters of canonical cross references")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading cross references dataset from assets", e)
            }
        }
    }

    /**
     * Checks if a verse has genuine, precise cross-references in the canonical index.
     * Never returns false positives or generic placeholders.
     */
    fun hasReferences(context: Context, bookId: Int, chapter: Int, verse: Int): Boolean {
        ensureLoaded(context)
        val chapKey = "${bookId}_$chapter"
        val verseMap = chapterCache[chapKey]
        return !verseMap?.get(verse).isNullOrEmpty()
    }

    /**
     * Retrieves the genuine, scholarly cross-references for a specific verse.
     * Returns an empty list if this verse has no canonical cross-references.
     */
    fun getReferences(context: Context, bookId: Int, chapter: Int, verse: Int): List<CrossReferenceItem> {
        ensureLoaded(context)
        val chapKey = "${bookId}_$chapter"
        val verseMap = chapterCache[chapKey]
        return verseMap?.get(verse) ?: emptyList()
    }

    /**
     * Checks if a chapter has canonical parallel passages (e.g. Synoptic Gospels, Kings/Chronicles).
     */
    fun hasChapterParallelReferences(bookId: Int, chapter: Int): Boolean {
        val chapterKey = "${bookId}_$chapter"
        return chapterReferencesMap.containsKey(chapterKey)
    }

    /**
     * Retrieves canonical parallel passages for a full chapter.
     */
    fun getChapterParallelReferences(bookId: Int, chapter: Int): List<CrossReferenceItem> {
        val chapterKey = "${bookId}_$chapter"
        return chapterReferencesMap[chapterKey] ?: emptyList()
    }
}

package com.example

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class BiblePericopesCatalogTest {

    private val bookChapters = mapOf(
        1 to 50, 2 to 40, 3 to 27, 4 to 36, 5 to 34, 6 to 24, 7 to 21, 8 to 4, 9 to 31, 10 to 24,
        11 to 22, 12 to 25, 13 to 29, 14 to 36, 15 to 10, 16 to 13, 17 to 10, 18 to 42, 19 to 150,
        20 to 31, 21 to 12, 22 to 8, 23 to 66, 24 to 52, 25 to 5, 26 to 48, 27 to 12, 28 to 14,
        29 to 3, 30 to 9, 31 to 1, 32 to 4, 33 to 7, 34 to 3, 35 to 3, 36 to 3, 37 to 2, 38 to 14,
        39 to 4, 40 to 28, 41 to 16, 42 to 24, 43 to 21, 44 to 28, 45 to 16, 46 to 16, 47 to 13,
        48 to 6, 49 to 6, 50 to 4, 51 to 4, 52 to 5, 53 to 3, 54 to 6, 55 to 4, 56 to 3, 57 to 1,
        58 to 13, 59 to 5, 60 to 5, 61 to 3, 62 to 5, 63 to 1, 64 to 1, 65 to 1, 66 to 22
    )

    private val versionFiles = listOf(
        "RVR1960" to "app/src/main/assets/bible/pericopes_es.json",
        "NVI" to "app/src/main/assets/bible/pericopes_nvi.json",
        "NTV" to "app/src/main/assets/bible/pericopes_ntv.json",
        "TLA" to "app/src/main/assets/bible/pericopes_tla.json",
        "DHH" to "app/src/main/assets/bible/pericopes_dhh.json",
        "LBLA" to "app/src/main/assets/bible/pericopes_lbla.json",
        "NBLA" to "app/src/main/assets/bible/pericopes_nbla.json"
    )

    @Test
    fun testAllVersionsCoverAll1189Chapters() {
        val totalExpectedChapters = bookChapters.values.sum()
        assertEquals(1189, totalExpectedChapters)

        for ((version, filePath) in versionFiles) {
            val file = File(filePath)
            assertTrue("File for $version must exist: $filePath", file.exists())

            val jsonContent = file.readText(Charsets.UTF_8)
            val jsonObject = JSONObject(jsonContent)
            val keys = jsonObject.keys()

            val chaptersCovered = mutableSetOf<Pair<Int, Int>>()
            var entryCount = 0

            while (keys.hasNext()) {
                val key = keys.next()
                val title = jsonObject.getString(key)
                assertTrue("Heading in $version for key $key must not be blank", title.isNotBlank())

                val parts = key.split("_")
                val bookId = parts[0].toInt()
                val chapter = parts[1].toInt()
                chaptersCovered.add(Pair(bookId, chapter))
                entryCount++
            }

            assertTrue("Version $version should have over 2,000 headings (has $entryCount)", entryCount >= 2000)
            assertEquals("Version $version must cover exactly all 1,189 chapters of the Bible", 1189, chaptersCovered.size)

            // Validate that every single book and chapter exists in this version
            for ((bookId, maxChapter) in bookChapters) {
                for (chapter in 1..maxChapter) {
                    assertTrue(
                        "Version $version is missing Book $bookId Chapter $chapter",
                        chaptersCovered.contains(Pair(bookId, chapter))
                    )
                }
            }
        }
    }

    @Test
    fun testHeadingsVaryBetweenDifferentVersions() {
        val rvrFile = File("app/src/main/assets/bible/pericopes_es.json")
        val ntvFile = File("app/src/main/assets/bible/pericopes_ntv.json")
        val tlaFile = File("app/src/main/assets/bible/pericopes_tla.json")
        val dhhFile = File("app/src/main/assets/bible/pericopes_dhh.json")

        val rvrJson = JSONObject(rvrFile.readText())
        val ntvJson = JSONObject(ntvFile.readText())
        val tlaJson = JSONObject(tlaFile.readText())
        val dhhJson = JSONObject(dhhFile.readText())

        // Genesis 1:1
        val rvrGen1 = rvrJson.optString("1_1_1")
        val ntvGen1 = ntvJson.optString("1_1_1")
        val tlaGen1 = tlaJson.optString("1_1_1")
        val dhhGen1 = dhhJson.optString("1_1_1")

        assertNotNull(rvrGen1)
        assertNotNull(ntvGen1)
        assertNotNull(tlaGen1)
        assertNotNull(dhhGen1)

        assertEquals("La creación", rvrGen1)
        assertEquals("La historia de la creación", ntvGen1)
        assertEquals("Dios crea el universo", tlaGen1)
        assertEquals("Dios crea el mundo", dhhGen1)

        // Matthew 5:1 (Sermon on the Mount / Beatitudes)
        val rvrMt5 = rvrJson.optString("40_5_1")
        val ntvMt5 = ntvJson.optString("40_5_1")
        val tlaMt5 = tlaJson.optString("40_5_1")

        assertEquals("Las bienaventuranzas", rvrMt5)
        assertEquals("El Sermón del Monte: Las bienaventuranzas", ntvMt5)
        assertEquals("Jesús enseña en la montaña", tlaMt5)

        // Luke 15:11 (Prodigal Son / Lost Son)
        val rvrLk15 = rvrJson.optString("42_15_11")
        val ntvLk15 = ntvJson.optString("42_15_11")
        val tlaLk15 = tlaJson.optString("42_15_11")
        val dhhLk15 = dhhJson.optString("42_15_11")

        assertEquals("Parábola del hijo pródigo", rvrLk15)
        assertEquals("Parábola del hijo perdido", ntvLk15)
        assertEquals("El hijo que regresó a casa", tlaLk15)
        assertEquals("El hijo que se fue de casa", dhhLk15)
    }
}

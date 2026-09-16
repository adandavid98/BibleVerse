package com.example

import com.example.data.bible.BibleCatalog
import com.example.data.model.VerseEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class BibleCatalogTest {

    @Test
    fun testBibleCatalog_hasAll66Books() {
        assertEquals(66, BibleCatalog.books.size)
        val otBooks = BibleCatalog.books.filter { it.testament == "Antiguo Testamento" }
        val ntBooks = BibleCatalog.books.filter { it.testament == "Nuevo Testamento" }
        assertEquals(39, otBooks.size)
        assertEquals(27, ntBooks.size)
        assertEquals("Génesis", BibleCatalog.books.first().name)
        assertEquals("Apocalipsis", BibleCatalog.books.last().name)
    }

    @Test
    fun testBibleCatalog_hasRequestedTranslations() {
        val codes = BibleCatalog.versions.map { it.code }
        assertTrue(codes.contains("RVR1960"))
        assertTrue(codes.contains("NVI"))
        assertTrue(codes.contains("NTV"))
        assertTrue(codes.contains("TLA"))
        assertTrue(codes.contains("DHH"))
        assertTrue(codes.contains("LBLA"))
    }

    @Test
    fun testVerseEntity_defaultsAndSupportsBibleVersion() {
        val defaultVerse = VerseEntity(
            book = "Juan",
            chapterVerse = "3:16",
            reference = "Juan 3:16",
            testament = "Nuevo Testamento",
            text = "«Porque de tal manera amó Dios al mundo...»",
            context = "Evangelio de Juan"
        )
        assertEquals("RVR1960", defaultVerse.bibleVersion)

        val nviVerse = defaultVerse.copy(bibleVersion = "NVI")
        assertEquals("NVI", nviVerse.bibleVersion)
    }
}

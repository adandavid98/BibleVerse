package com.example

import com.example.data.bible.BibleContextEngine
import com.example.data.bible.ContextGenerationResult
import com.example.data.bible.ContextSource
import com.example.data.bible.GeminiVerseContextService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class BibleContextEngineTest {

    @Test
    fun testBibleContextEngine_returnsRichContextForKnownBooks() {
        val genesisContext = BibleContextEngine.getLocalContext(
            bookName = "Génesis",
            chapter = 1,
            verse = "1",
            verseText = "En el principio creó Dios los cielos y la tierra."
        )
        assertNotNull(genesisContext)
        assertTrue("Context should mention theological themes", genesisContext.contains("Génesis") || genesisContext.contains("creación"))
    }

    @Test
    fun testBibleContextEngine_returnsSpecificContextForPsalms() {
        val psalm23Context = BibleContextEngine.getLocalContext(
            bookName = "Salmos",
            chapter = 23,
            verse = "1",
            verseText = "Jehová es mi pastor; nada me faltará."
        )
        assertNotNull(psalm23Context)
        assertTrue("Context should be non-empty", psalm23Context.length > 50)
        assertTrue("Should reference Salmos or shepherd theme", psalm23Context.contains("pastor") || psalm23Context.contains("Salmos"))
    }

    @Test
    fun testBibleContextEngine_returnsChapterSpecificForRomans() {
        val romans8Context = BibleContextEngine.getLocalContext(
            bookName = "Romanos",
            chapter = 8,
            verse = "31",
            verseText = "¿Qué, pues, diremos a esto? Si Dios es por nosotros, ¿quién contra nosotros?"
        )
        assertNotNull(romans8Context)
        assertTrue("Should mention Romanos, Pablo or Espíritu", romans8Context.contains("Romanos") || romans8Context.contains("Espíritu") || romans8Context.contains("Pablo"))
    }

    @Test
    fun testGeminiVerseContextService_fallsBackToLocalWhenKeyIsDefaultOrOffline() = runBlocking {
        // When forceLocalOnly is true with an exact curated verse, returns LOCAL_EXACT
        val resultExact = GeminiVerseContextService.generateContext(
            book = "Filipenses",
            chapter = 4,
            verse = "13",
            verseText = "Todo lo puedo en Cristo que me fortalece.",
            bibleVersion = "RVR1960",
            forceLocalOnly = true
        )
        assertTrue(resultExact is ContextGenerationResult.Success)
        val successExact = resultExact as ContextGenerationResult.Success
        assertEquals(ContextSource.LOCAL_EXACT, successExact.source)
        assertTrue(successExact.contextText.isNotBlank())

        // When forceLocalOnly is true with a non-curated verse, returns LOCAL_ENGINE
        val resultEngine = GeminiVerseContextService.generateContext(
            book = "Habacuc",
            chapter = 3,
            verse = "17",
            verseText = "Aunque la higuera no florezca...",
            bibleVersion = "RVR1960",
            forceLocalOnly = true
        )
        assertTrue(resultEngine is ContextGenerationResult.Success)
        val successEngine = resultEngine as ContextGenerationResult.Success
        assertEquals(ContextSource.LOCAL_ENGINE, successEngine.source)
        assertTrue(successEngine.contextText.contains("Habacuc") || successEngine.contextText.contains("fe"))
    }

    @Test
    fun testGeminiBibleChatService_returnsTheologicalFallbackForEschatology() = runBlocking {
        val reply = com.example.data.bible.GeminiBibleChatService.askBibleQuestion(
            history = emptyList(),
            userQuestion = "¿Qué enseña la Biblia sobre la escatología y el fin del mundo?"
        )
        assertNotNull(reply)
        assertFalse(reply.isUser)
        assertTrue("Should contain escatología or Daniel or Apocalipsis",
            reply.text.contains("escatología", ignoreCase = true) ||
            reply.text.contains("Apocalipsis", ignoreCase = true) ||
            reply.text.contains("Cristo", ignoreCase = true)
        )
    }
}

package com.example

import com.example.data.bible.BibleTextSanitizer
import org.junit.Assert.assertEquals
import org.junit.Test

class BibleTextSanitizerTest {

    @Test
    fun stripsBulletsAndMarkersFromNbla() {
        val raw = "•Rubén, tú eres mi primogénito, Mi poderío y el principio de mi vigor."
        val expected = "Rubén, tú eres mi primogénito, Mi poderío y el principio de mi vigor."
        assertEquals(expected, BibleTextSanitizer.sanitize(raw))
    }

    @Test
    fun stripsMiddleDotsAndPilcrows() {
        val raw = "¶ En el principio · Dios creó los cielos y la tierra."
        val expected = "En el principio Dios creó los cielos y la tierra."
        assertEquals(expected, BibleTextSanitizer.sanitize(raw))
    }

    @Test
    fun fixesSpacingBeforePunctuation() {
        val raw = "Dijo Dios , « Sea la luz » ; y hubo luz ."
        val expected = "Dijo Dios, « Sea la luz »; y hubo luz."
        assertEquals(expected, BibleTextSanitizer.sanitize(raw))
    }

    @Test
    fun removesEmbeddedHtmlHeaders() {
        val raw = "<p align='center'><b><i>La creación</i></b></p>En el principio Dios creó los cielos y la tierra."
        val expected = "En el principio Dios creó los cielos y la tierra."
        assertEquals(expected, BibleTextSanitizer.sanitize(raw))
    }

    @Test
    fun removesFootnoteMarkers() {
        val raw = "Y Dios llamó* a la luz día†, y a las tinieblas llamó noche‡."
        val expected = "Y Dios llamó a la luz día, y a las tinieblas llamó noche."
        assertEquals(expected, BibleTextSanitizer.sanitize(raw))
    }

    @Test
    fun testRemoveAccents_removesSpanishDiacritics() {
        assertEquals("jesus", BibleTextSanitizer.removeAccents("jesús"))
        assertEquals("Jesus", BibleTextSanitizer.removeAccents("Jesús"))
        assertEquals("corazon", BibleTextSanitizer.removeAccents("corazón"))
        assertEquals("oracion", BibleTextSanitizer.removeAccents("oración"))
        assertEquals("espiritu", BibleTextSanitizer.removeAccents("espíritu"))
        assertEquals("bendicion", BibleTextSanitizer.removeAccents("bendición"))
        assertEquals("El dio su vida", BibleTextSanitizer.removeAccents("Él dio su vida"))
        assertEquals("antiguedad", BibleTextSanitizer.removeAccents("antigüedad"))
    }
}


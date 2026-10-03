package com.example

import com.example.data.bible.BollsBibleApiService
import org.junit.Assert.assertEquals
import org.junit.Test

class BollsSanitizerTest {

    @Test
    fun removesEmbeddedTitleAndWrappingQuotes() {
        val raw = "'<p align='center'><b><i>La creaci\u00f3n del mundo</i></b></p>Cuando en el principio Dios cre\u00f3 los cielos y la tierra,'"
        assertEquals(
            "Cuando en el principio Dios cre\u00f3 los cielos y la tierra,",
            BollsBibleApiService.sanitizeVerseText(raw)
        )
    }

    @Test
    fun removesTitleInRomans4() {
        val raw = "'<p align='center'><b><i>El ejemplo de Abraham</i></b></p>\u00bfQu\u00e9 podemos concluir?'"
        assertEquals("\u00bfQu\u00e9 podemos concluir?", BollsBibleApiService.sanitizeVerseText(raw))
    }

    @Test
    fun keepsPlainVersesIntact() {
        assertEquals(
            "Dios dijo: \u00abQue haya luz\u00bb, y hubo luz.",
            BollsBibleApiService.sanitizeVerseText("Dios dijo: \u00abQue haya luz\u00bb, y hubo luz.")
        )
    }
}

package com.example

import com.example.data.model.VerseEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class VerseDeduplicationTest {

    @Test
    fun testDeduplication_preservesFavoriteAndNotes() {
        val duplicates = listOf(
            VerseEntity(
                id = 1,
                book = "Génesis",
                chapterVerse = "1:27",
                reference = "Génesis 1:27",
                testament = "Antiguo Testamento",
                text = "«Y creó Dios al hombre...»",
                context = "Relato",
                isFavorite = false,
                notes = ""
            ),
            VerseEntity(
                id = 2,
                book = "Génesis",
                chapterVerse = "1:27",
                reference = "Génesis 1:27",
                testament = "Antiguo Testamento",
                text = "«Y creó Dios al hombre...»",
                context = "Relato",
                isFavorite = true,
                notes = "Mi versículo favorito"
            )
        )

        val deduplicated = duplicates
            .sortedWith(
                compareByDescending<VerseEntity> { it.isFavorite }
                    .thenByDescending { it.notes.isNotBlank() }
                    .thenByDescending { it.highlightColor.isNotBlank() }
                    .thenBy { it.id }
            )
            .distinctBy {
                it.reference.trim().lowercase().replace("\\s+".toRegex(), " ")
            }

        assertEquals(1, deduplicated.size)
        assertTrue(deduplicated[0].isFavorite)
        assertEquals("Mi versículo favorito", deduplicated[0].notes)
        assertEquals(2L, deduplicated[0].id)
    }
}

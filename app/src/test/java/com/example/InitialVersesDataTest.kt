package com.example

import com.example.data.initial.InitialVersesData
import com.example.service.NotificationHelper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class InitialVersesDataTest {

    @Test
    fun testInitialVersesHasExactly365Entries() {
        assertEquals("InitialVersesData should contain exactly 365 verses", 365, InitialVersesData.verses.size)
    }

    @Test
    fun testInitialVersesIntegrityAndContiguity() {
        val verses = InitialVersesData.verses
        val references = mutableSetOf<String>()

        verses.forEachIndexed { index, verse ->
            val expectedOrder = index + 1
            assertEquals("Order index should match position 1..365", expectedOrder, verse.orderIndex)
            assertTrue("Reference must not be blank at day $expectedOrder", verse.reference.isNotBlank())
            assertTrue("Text must not be blank at day $expectedOrder", verse.text.isNotBlank())
            assertTrue("Book must not be blank at day $expectedOrder", verse.book.isNotBlank())
            assertTrue("Context must not be blank at day $expectedOrder", verse.context.isNotBlank())
            assertTrue("Topic must not be blank at day $expectedOrder", verse.topic.isNotBlank())
            assertTrue("Testament must be Antiguo or Nuevo at day $expectedOrder", 
                verse.testament == "Antiguo Testamento" || verse.testament == "Nuevo Testamento")

            val normalizedRef = verse.reference.trim().lowercase().replace("\\s+".toRegex(), " ")
            assertTrue("Reference should be unique: ${verse.reference}", references.add(normalizedRef))
        }

        assertEquals("All 365 references should be distinct", 365, references.size)
    }

    @Test
    fun testNotificationHelperReturnsUniqueVersePerDay() {
        val seenOrderIndices = mutableSetOf<Int>()
        var previousBook: String? = null
        var previousTopic: String? = null

        for (day in 1..365) {
            val verse = NotificationHelper.getVerseOfTheDay(day)
            assertNotNull("Verse of the day must not be null for day $day", verse)
            val expectedOrder = ((day - 1) * 53) % 365 + 1
            assertEquals("Verse order index should match coprime daily rotation", expectedOrder, verse.orderIndex)
            assertTrue("Verse should be unique in 365-day cycle at day $day", seenOrderIndices.add(verse.orderIndex))
            assertTrue("Verse text should be valid for day $day", verse.text.isNotBlank())
            if (previousBook != null) {
                assertTrue("Consecutive days ($day) should not repeat the same book", verse.book != previousBook)
            }
            if (previousTopic != null) {
                assertTrue("Consecutive days ($day) should not repeat the same topic", verse.topic != previousTopic)
            }
            previousBook = verse.book
            previousTopic = verse.topic
        }

        assertEquals("All 365 days should cover all 365 unique verses", 365, seenOrderIndices.size)
    }
}

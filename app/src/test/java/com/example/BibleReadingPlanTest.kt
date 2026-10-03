package com.example

import com.example.data.bible.BibleReadingPlanCatalog
import com.example.data.bible.ReadingPlanType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BibleReadingPlanTest {

    @Test
    fun testBibleStoriesPlanHas180Days() {
        val plan = BibleReadingPlanCatalog.getPlan(ReadingPlanType.BIBLE_STORIES)
        assertEquals(180, plan.size)
        assertEquals(180, ReadingPlanType.BIBLE_STORIES.totalDays)
    }

    @Test
    fun testBibleStoriesPlanIntegrityAndContiguity() {
        val plan = BibleReadingPlanCatalog.getPlan(ReadingPlanType.BIBLE_STORIES)

        for (i in 0 until 180) {
            val day = plan[i]
            val expectedDayNumber = i + 1

            assertEquals("Day number should be contiguous", expectedDayNumber, day.dayNumber)
            assertTrue("Title should not be blank for day $expectedDayNumber", day.title.isNotBlank())
            assertTrue("Passages summary should not be blank for day $expectedDayNumber", day.passagesSummary.isNotBlank())
            assertTrue("Primary book ID should be between 1 and 66 for day $expectedDayNumber", day.primaryBookId in 1..66)
            assertTrue("Primary chapter should be > 0 for day $expectedDayNumber", day.primaryChapter > 0)
            assertTrue("Historical context should not be blank for day $expectedDayNumber", day.historicalContext.isNotBlank())
            assertTrue("Spiritual lesson should not be blank for day $expectedDayNumber", day.spiritualLesson.isNotBlank())
            assertTrue("Story narrative should not be blank for day $expectedDayNumber", day.storyNarrative.isNotBlank())
            assertTrue("Passages list should not be empty for day $expectedDayNumber", day.passages.isNotEmpty())
        }
    }

    @Test
    fun testExistingPlansMaintain365Days() {
        val traditional = BibleReadingPlanCatalog.getPlan(ReadingPlanType.TRADITIONAL)
        val chronological = BibleReadingPlanCatalog.getPlan(ReadingPlanType.CHRONOLOGICAL)

        assertEquals(365, traditional.size)
        assertEquals(365, chronological.size)
        assertEquals(365, ReadingPlanType.TRADITIONAL.totalDays)
        assertEquals(365, ReadingPlanType.CHRONOLOGICAL.totalDays)
    }
}

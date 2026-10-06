package com.example

import com.example.data.bible.BibleReadingPlanCatalog
import com.example.data.bible.ReadingPlanType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BibleReadingPlanTest {

    @Test
    fun testBibleStoriesPlanHas749Stories() {
        val plan = BibleReadingPlanCatalog.getPlan(ReadingPlanType.BIBLE_STORIES)
        assertEquals(749, plan.size)
        assertEquals(749, ReadingPlanType.BIBLE_STORIES.totalDays)
    }

    @Test
    fun testBibleStoriesPlanIntegrityAndContiguity() {
        val plan = BibleReadingPlanCatalog.getPlan(ReadingPlanType.BIBLE_STORIES)

        for (i in 0 until 749) {
            val day = plan[i]
            val expectedStoryNumber = i + 1

            assertEquals("Story number should be contiguous", expectedStoryNumber, day.dayNumber)
            assertTrue("Title should not be blank for story $expectedStoryNumber", day.title.isNotBlank())
            assertTrue("Passages summary should not be blank for story $expectedStoryNumber", day.passagesSummary.isNotBlank())
            assertTrue("Primary book ID should be between 1 and 66 for story $expectedStoryNumber", day.primaryBookId in 1..66)
            assertTrue("Primary chapter should be > 0 for story $expectedStoryNumber", day.primaryChapter > 0)
            assertTrue("Passages list should not be empty for story $expectedStoryNumber", day.passages.isNotEmpty())
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

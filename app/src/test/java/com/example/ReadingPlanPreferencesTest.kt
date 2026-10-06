package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.bible.ReadingPlanType
import com.example.data.preferences.ReadingPlanPreferences
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ReadingPlanPreferencesTest {

    private lateinit var preferences: ReadingPlanPreferences

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val prefs = context.getSharedPreferences("reading_plan_prefs", Context.MODE_PRIVATE)
        prefs.edit().clear().commit()
        preferences = ReadingPlanPreferences(context)
    }

    @Test
    fun testInitialState_decoupledDefaults() {
        val progress = preferences.progressFlow.value
        assertEquals(ReadingPlanType.TRADITIONAL, progress.activePlanType)
        assertEquals(1, preferences.getLastReadDay(ReadingPlanType.TRADITIONAL))
        assertEquals(1, preferences.getLastReadDay(ReadingPlanType.CHRONOLOGICAL))
        assertEquals(1, preferences.getLastReadDay(ReadingPlanType.BIBLE_STORIES))
        assertEquals(1, progress.currentDay)
    }

    @Test
    fun testIndependentTrackingPerPlan_day150AndDay5() {
        // User reaches Day 150 in TRADITIONAL
        preferences.setLastReadDay(ReadingPlanType.TRADITIONAL, 150)
        // User reaches Day 5 in CHRONOLOGICAL
        preferences.setLastReadDay(ReadingPlanType.CHRONOLOGICAL, 5)
        // User reaches Day 20 in BIBLE_STORIES
        preferences.setLastReadDay(ReadingPlanType.BIBLE_STORIES, 20)

        // Verify that each plan remembers its own day completely decoupled
        assertEquals(150, preferences.getLastReadDay(ReadingPlanType.TRADITIONAL))
        assertEquals(5, preferences.getLastReadDay(ReadingPlanType.CHRONOLOGICAL))
        assertEquals(20, preferences.getLastReadDay(ReadingPlanType.BIBLE_STORIES))

        // When switching active plan to TRADITIONAL, currentDay is 150
        preferences.setPlanType(ReadingPlanType.TRADITIONAL)
        assertEquals(150, preferences.progressFlow.value.currentDay)

        // When switching active plan to CHRONOLOGICAL, currentDay is 5
        preferences.setPlanType(ReadingPlanType.CHRONOLOGICAL)
        assertEquals(5, preferences.progressFlow.value.currentDay)

        // When switching active plan to BIBLE_STORIES, currentDay is 20
        preferences.setPlanType(ReadingPlanType.BIBLE_STORIES)
        assertEquals(20, preferences.progressFlow.value.currentDay)
    }

    @Test
    fun testIndependentCompletedDaysPerPlan() {
        preferences.setPlanType(ReadingPlanType.TRADITIONAL)
        preferences.toggleDayCompleted(1)
        preferences.toggleDayCompleted(2)

        preferences.setPlanType(ReadingPlanType.CHRONOLOGICAL)
        preferences.toggleDayCompleted(5)

        // TRADITIONAL has {1, 2}
        val tradPlan = preferences.progressFlow.value.plans[ReadingPlanType.TRADITIONAL]!!
        assertTrue(tradPlan.completedDays.contains(1))
        assertTrue(tradPlan.completedDays.contains(2))
        assertFalse(tradPlan.completedDays.contains(5))

        // CHRONOLOGICAL has only {5}
        val chronoPlan = preferences.progressFlow.value.plans[ReadingPlanType.CHRONOLOGICAL]!!
        assertTrue(chronoPlan.completedDays.contains(5))
        assertFalse(chronoPlan.completedDays.contains(1))
        assertFalse(chronoPlan.completedDays.contains(2))
    }
}

package com.example

import com.example.update.AppUpdateManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AppUpdateTest {

    @Test
    fun testVersionComparison_higherVersion() {
        assertTrue(AppUpdateManager.compareVersions("v1.1", "v1.0") > 0)
        assertTrue(AppUpdateManager.compareVersions("v2.0.0", "v1.9.9") > 0)
        assertTrue(AppUpdateManager.compareVersions("1.0.1", "1.0") > 0)
        assertTrue(AppUpdateManager.compareVersions("v1.10", "v1.2") > 0)
    }

    @Test
    fun testVersionComparison_sameVersion() {
        assertEquals(0, AppUpdateManager.compareVersions("v1.0", "1.0"))
        assertEquals(0, AppUpdateManager.compareVersions("1.0.0", "1.0"))
    }

    @Test
    fun testVersionComparison_lowerVersion() {
        assertTrue(AppUpdateManager.compareVersions("v1.0", "v1.1") < 0)
        assertTrue(AppUpdateManager.compareVersions("1.0", "1.0.1") < 0)
    }
}

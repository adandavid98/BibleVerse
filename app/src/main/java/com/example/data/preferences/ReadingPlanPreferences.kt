package com.example.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.data.bible.ReadingPlanType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ReadingPlanProgress(
    val activePlanType: ReadingPlanType = ReadingPlanType.TRADITIONAL,
    val completedDays: Set<Int> = emptySet()
) {
    val totalCompleted: Int get() = completedDays.size
    val progressPercentage: Float get() = (completedDays.size.toFloat() / 365f).coerceIn(0f, 1f)
    val nextPendingDay: Int get() = (1..365).firstOrNull { !completedDays.contains(it) } ?: 365
}

class ReadingPlanPreferences(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("reading_plan_prefs", Context.MODE_PRIVATE)

    private val _progressFlow = MutableStateFlow(loadProgress())
    val progressFlow: StateFlow<ReadingPlanProgress> = _progressFlow.asStateFlow()

    private fun loadProgress(): ReadingPlanProgress {
        val planName = prefs.getString(KEY_PLAN_TYPE, ReadingPlanType.TRADITIONAL.name) ?: ReadingPlanType.TRADITIONAL.name
        val planType = try {
            ReadingPlanType.valueOf(planName)
        } catch (_: Exception) {
            ReadingPlanType.TRADITIONAL
        }

        val completedSetString = prefs.getStringSet(KEY_COMPLETED_DAYS + "_" + planType.name, emptySet()) ?: emptySet()
        val completedDays = completedSetString.mapNotNull { it.toIntOrNull() }.toSet()

        return ReadingPlanProgress(
            activePlanType = planType,
            completedDays = completedDays
        )
    }

    fun setPlanType(type: ReadingPlanType) {
        prefs.edit().putString(KEY_PLAN_TYPE, type.name).apply()
        _progressFlow.value = loadProgress()
    }

    fun toggleDayCompleted(dayNumber: Int) {
        val current = _progressFlow.value
        val updated = if (current.completedDays.contains(dayNumber)) {
            current.completedDays - dayNumber
        } else {
            current.completedDays + dayNumber
        }
        val setString = updated.map { it.toString() }.toSet()
        prefs.edit().putStringSet(KEY_COMPLETED_DAYS + "_" + current.activePlanType.name, setString).apply()
        _progressFlow.value = current.copy(completedDays = updated)
    }

    fun setDayCompleted(dayNumber: Int, completed: Boolean) {
        val current = _progressFlow.value
        val updated = if (completed) {
            current.completedDays + dayNumber
        } else {
            current.completedDays - dayNumber
        }
        val setString = updated.map { it.toString() }.toSet()
        prefs.edit().putStringSet(KEY_COMPLETED_DAYS + "_" + current.activePlanType.name, setString).apply()
        _progressFlow.value = current.copy(completedDays = updated)
    }

    fun resetPlanProgress() {
        val current = _progressFlow.value
        prefs.edit().remove(KEY_COMPLETED_DAYS + "_" + current.activePlanType.name).apply()
        _progressFlow.value = current.copy(completedDays = emptySet())
    }

    companion object {
        private const val KEY_PLAN_TYPE = "key_active_plan_type"
        private const val KEY_COMPLETED_DAYS = "key_completed_days"
    }
}

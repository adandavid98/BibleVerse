package com.example.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.data.bible.ReadingPlanType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SinglePlanProgress(
    val planType: ReadingPlanType,
    val completedDays: Set<Int> = emptySet(),
    val lastReadDay: Int = 1
) {
    val totalDays: Int get() = planType.totalDays
    val totalCompleted: Int get() = completedDays.size
    val progressPercentage: Float get() = (completedDays.size.toFloat() / totalDays.toFloat().coerceAtLeast(1f)).coerceIn(0f, 1f)
    val nextPendingDay: Int get() = (1..totalDays).firstOrNull { !completedDays.contains(it) } ?: totalDays
    val currentDay: Int get() = lastReadDay.coerceIn(1, totalDays)
}

data class ReadingPlanProgress(
    val activePlanType: ReadingPlanType = ReadingPlanType.TRADITIONAL,
    val plans: Map<ReadingPlanType, SinglePlanProgress> = emptyMap()
) {
    val currentPlan: SinglePlanProgress
        get() = plans[activePlanType] ?: SinglePlanProgress(activePlanType)

    val completedDays: Set<Int> get() = currentPlan.completedDays
    val totalDays: Int get() = currentPlan.totalDays
    val totalCompleted: Int get() = currentPlan.totalCompleted
    val progressPercentage: Float get() = currentPlan.progressPercentage
    val nextPendingDay: Int get() = currentPlan.nextPendingDay
    val currentDay: Int get() = currentPlan.currentDay
}

class ReadingPlanPreferences(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("reading_plan_prefs", Context.MODE_PRIVATE)

    private val _progressFlow = MutableStateFlow(loadProgress())
    val progressFlow: StateFlow<ReadingPlanProgress> = _progressFlow.asStateFlow()

    private fun loadProgress(): ReadingPlanProgress {
        val planName = prefs.getString(KEY_PLAN_TYPE, ReadingPlanType.TRADITIONAL.name) ?: ReadingPlanType.TRADITIONAL.name
        val activePlanType = try {
            ReadingPlanType.valueOf(planName)
        } catch (_: Exception) {
            ReadingPlanType.TRADITIONAL
        }

        val allPlans = ReadingPlanType.values().associateWith { type ->
            val completedSetString = prefs.getStringSet(KEY_COMPLETED_DAYS + "_" + type.name, emptySet()) ?: emptySet()
            val completedDays = completedSetString.mapNotNull { it.toIntOrNull() }.toSet()
            val defaultDay = (1..type.totalDays).firstOrNull { !completedDays.contains(it) } ?: 1
            val lastReadDay = prefs.getInt(KEY_LAST_READ_DAY + "_" + type.name, defaultDay).coerceIn(1, type.totalDays)
            SinglePlanProgress(
                planType = type,
                completedDays = completedDays,
                lastReadDay = lastReadDay
            )
        }

        return ReadingPlanProgress(
            activePlanType = activePlanType,
            plans = allPlans
        )
    }

    fun getLastReadDay(type: ReadingPlanType): Int {
        val current = _progressFlow.value.plans[type]
        if (current != null) return current.currentDay

        val completedSetString = prefs.getStringSet(KEY_COMPLETED_DAYS + "_" + type.name, emptySet()) ?: emptySet()
        val completedDays = completedSetString.mapNotNull { it.toIntOrNull() }.toSet()
        val defaultDay = (1..type.totalDays).firstOrNull { !completedDays.contains(it) } ?: 1
        return prefs.getInt(KEY_LAST_READ_DAY + "_" + type.name, defaultDay).coerceIn(1, type.totalDays)
    }

    fun setLastReadDay(type: ReadingPlanType, dayNumber: Int) {
        val validDay = dayNumber.coerceIn(1, type.totalDays)
        prefs.edit().putInt(KEY_LAST_READ_DAY + "_" + type.name, validDay).apply()
        _progressFlow.value = loadProgress()
    }

    fun setPlanType(type: ReadingPlanType) {
        prefs.edit().putString(KEY_PLAN_TYPE, type.name).apply()
        _progressFlow.value = loadProgress()
    }

    fun toggleDayCompleted(dayNumber: Int, type: ReadingPlanType? = null) {
        val currentProgress = _progressFlow.value
        val targetType = type ?: currentProgress.activePlanType
        val targetPlan = currentProgress.plans[targetType] ?: SinglePlanProgress(targetType)

        val isNowCompleted = !targetPlan.completedDays.contains(dayNumber)
        val updatedCompleted = if (isNowCompleted) {
            targetPlan.completedDays + dayNumber
        } else {
            targetPlan.completedDays - dayNumber
        }

        val setString = updatedCompleted.map { it.toString() }.toSet()
        val editor = prefs.edit().putStringSet(KEY_COMPLETED_DAYS + "_" + targetType.name, setString)

        if (isNowCompleted && dayNumber == targetPlan.lastReadDay) {
            val nextDay = (1..targetType.totalDays).firstOrNull { !updatedCompleted.contains(it) && it > dayNumber }
                ?: (dayNumber + 1).coerceAtMost(targetType.totalDays)
            editor.putInt(KEY_LAST_READ_DAY + "_" + targetType.name, nextDay)
        }
        editor.apply()
        _progressFlow.value = loadProgress()
    }

    fun setDayCompleted(dayNumber: Int, completed: Boolean, type: ReadingPlanType? = null) {
        val currentProgress = _progressFlow.value
        val targetType = type ?: currentProgress.activePlanType
        val targetPlan = currentProgress.plans[targetType] ?: SinglePlanProgress(targetType)

        val updatedCompleted = if (completed) {
            targetPlan.completedDays + dayNumber
        } else {
            targetPlan.completedDays - dayNumber
        }

        val setString = updatedCompleted.map { it.toString() }.toSet()
        val editor = prefs.edit().putStringSet(KEY_COMPLETED_DAYS + "_" + targetType.name, setString)

        if (completed && dayNumber == targetPlan.lastReadDay) {
            val nextDay = (1..targetType.totalDays).firstOrNull { !updatedCompleted.contains(it) && it > dayNumber }
                ?: (dayNumber + 1).coerceAtMost(targetType.totalDays)
            editor.putInt(KEY_LAST_READ_DAY + "_" + targetType.name, nextDay)
        }
        editor.apply()
        _progressFlow.value = loadProgress()
    }

    fun resetPlanProgress(type: ReadingPlanType? = null) {
        val targetType = type ?: _progressFlow.value.activePlanType
        prefs.edit()
            .remove(KEY_COMPLETED_DAYS + "_" + targetType.name)
            .remove(KEY_LAST_READ_DAY + "_" + targetType.name)
            .apply()
        _progressFlow.value = loadProgress()
    }

    companion object {
        private const val KEY_PLAN_TYPE = "key_active_plan_type"
        private const val KEY_COMPLETED_DAYS = "key_completed_days"
        private const val KEY_LAST_READ_DAY = "key_last_read_day"
    }
}

package com.example.data.repository

import com.example.data.bible.CustomPlanGenerator
import com.example.data.local.CustomReadingPlanDao
import com.example.data.model.CustomPlanDayEntity
import com.example.data.model.CustomPlanWithDays
import com.example.data.model.CustomReadingPlanEntity
import com.example.data.model.PlanDistributionMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class CustomReadingPlanRepository(
    private val planDao: CustomReadingPlanDao
) {

    fun getActivePlansWithDays(): Flow<List<CustomPlanWithDays>> {
        return planDao.getActivePlansWithDays()
    }

    fun getCompletedPlansWithDays(): Flow<List<CustomPlanWithDays>> {
        return planDao.getCompletedPlansWithDays()
    }

    fun getPlanWithDays(planId: String): Flow<CustomPlanWithDays?> {
        return planDao.getPlanWithDays(planId)
    }

    suspend fun createPlan(
        title: String,
        description: String = "",
        selectedBookIds: List<Int>,
        mode: PlanDistributionMode,
        targetDays: Int = 30,
        chaptersPerDay: Int = 2
    ): CustomReadingPlanEntity {
        val (planEntity, dayEntities) = CustomPlanGenerator.generatePlan(
            title = title,
            description = description,
            selectedBookIds = selectedBookIds,
            mode = mode,
            targetDays = targetDays,
            chaptersPerDay = chaptersPerDay
        )
        planDao.insertFullPlan(planEntity, dayEntities)
        return planEntity
    }

    suspend fun toggleDayCompleted(planId: String, dayNumber: Int, completed: Boolean): Boolean {
        val timestamp = if (completed) System.currentTimeMillis() else null
        planDao.setDayCompleted(planId, dayNumber, completed, timestamp)

        // Check if all days in the plan are completed
        val total = planDao.getTotalDaysCount(planId)
        val pending = planDao.getPendingDaysCount(planId)
        val plan = planDao.getPlanById(planId)

        if (plan != null) {
            if (total > 0 && pending == 0 && !plan.isCompleted) {
                planDao.markPlanCompleted(planId, System.currentTimeMillis())
                return true // Plan just reached 100% completion!
            } else if (pending > 0 && plan.isCompleted) {
                planDao.reopenPlan(planId)
            }
        }
        return false
    }

    suspend fun restartPlan(planId: String) {
        planDao.restartPlan(planId)
    }

    suspend fun deletePlan(planId: String) {
        planDao.deletePlan(planId)
    }
}

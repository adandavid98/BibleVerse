package com.example.data.local

import androidx.room.*
import com.example.data.model.CustomPlanDayEntity
import com.example.data.model.CustomPlanWithDays
import com.example.data.model.CustomReadingPlanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomReadingPlanDao {

    @Query("SELECT * FROM custom_reading_plans WHERE isCompleted = 0 AND isArchived = 0 ORDER BY createdAt DESC")
    fun getActivePlans(): Flow<List<CustomReadingPlanEntity>>

    @Query("SELECT * FROM custom_reading_plans WHERE isCompleted = 1 ORDER BY completedAt DESC")
    fun getCompletedPlans(): Flow<List<CustomReadingPlanEntity>>

    @Transaction
    @Query("SELECT * FROM custom_reading_plans WHERE isCompleted = 0 AND isArchived = 0 ORDER BY createdAt DESC")
    fun getActivePlansWithDays(): Flow<List<CustomPlanWithDays>>

    @Transaction
    @Query("SELECT * FROM custom_reading_plans WHERE isCompleted = 1 ORDER BY completedAt DESC")
    fun getCompletedPlansWithDays(): Flow<List<CustomPlanWithDays>>

    @Transaction
    @Query("SELECT * FROM custom_reading_plans WHERE id = :planId")
    fun getPlanWithDays(planId: String): Flow<CustomPlanWithDays?>

    @Query("SELECT * FROM custom_plan_days WHERE planId = :planId ORDER BY dayNumber ASC")
    fun getDaysForPlan(planId: String): Flow<List<CustomPlanDayEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlan(plan: CustomReadingPlanEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDays(days: List<CustomPlanDayEntity>)

    @Transaction
    suspend fun insertFullPlan(plan: CustomReadingPlanEntity, days: List<CustomPlanDayEntity>) {
        insertPlan(plan)
        insertDays(days)
    }

    @Query("UPDATE custom_plan_days SET isCompleted = :completed, completedAt = :timestamp WHERE planId = :planId AND dayNumber = :dayNumber")
    suspend fun setDayCompleted(planId: String, dayNumber: Int, completed: Boolean, timestamp: Long?)

    @Query("SELECT * FROM custom_reading_plans WHERE id = :planId LIMIT 1")
    suspend fun getPlanById(planId: String): CustomReadingPlanEntity?

    @Query("SELECT COUNT(*) FROM custom_plan_days WHERE planId = :planId AND isCompleted = 0")
    suspend fun getPendingDaysCount(planId: String): Int

    @Query("SELECT COUNT(*) FROM custom_plan_days WHERE planId = :planId")
    suspend fun getTotalDaysCount(planId: String): Int

    @Query("UPDATE custom_reading_plans SET isCompleted = 1, completedAt = :completedAt WHERE id = :planId")
    suspend fun markPlanCompleted(planId: String, completedAt: Long = System.currentTimeMillis())

    @Query("UPDATE custom_reading_plans SET isCompleted = 0, completedAt = NULL WHERE id = :planId")
    suspend fun reopenPlan(planId: String)

    @Query("UPDATE custom_plan_days SET isCompleted = 0, completedAt = NULL WHERE planId = :planId")
    suspend fun resetAllDays(planId: String)

    @Transaction
    suspend fun restartPlan(planId: String) {
        reopenPlan(planId)
        resetAllDays(planId)
    }

    @Query("DELETE FROM custom_reading_plans WHERE id = :planId")
    suspend fun deletePlan(planId: String)
}

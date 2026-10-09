package com.example.data.model

import androidx.room.*

enum class PlanDistributionMode {
    BY_TARGET_DAYS,
    BY_CHAPTERS_PER_DAY
}

@Entity(tableName = "custom_reading_plans")
data class CustomReadingPlanEntity(
    @PrimaryKey
    val id: String, // UUID
    val title: String,
    val description: String = "",
    val selectedBookIds: String, // Comma separated IDs (ej. "40,41,42,43")
    val totalChapters: Int,
    val totalDays: Int,
    val distributionMode: String = PlanDistributionMode.BY_TARGET_DAYS.name,
    val chaptersPerDay: Int? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val startDate: Long = System.currentTimeMillis(),
    val completedAt: Long? = null,
    val isCompleted: Boolean = false,
    val isArchived: Boolean = false
)

@Entity(
    tableName = "custom_plan_days",
    foreignKeys = [
        ForeignKey(
            entity = CustomReadingPlanEntity::class,
            parentColumns = ["id"],
            childColumns = ["planId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("planId"),
        Index(value = ["planId", "dayNumber"], unique = true)
    ]
)
data class CustomPlanDayEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val planId: String,
    val dayNumber: Int,
    val passageSummary: String,
    val primaryBookId: Int,
    val primaryChapter: Int,
    val passagesJson: String, // JSON serialization of List<PlanPassageSegment>
    val isCompleted: Boolean = false,
    val completedAt: Long? = null
)

data class CustomPlanWithDays(
    @Embedded
    val plan: CustomReadingPlanEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "planId"
    )
    val days: List<CustomPlanDayEntity>
) {
    val totalDays: Int get() = plan.totalDays
    val completedDaysCount: Int get() = days.count { it.isCompleted }
    val progressPercentage: Float
        get() = if (totalDays > 0) (completedDaysCount.toFloat() / totalDays.toFloat()).coerceIn(0f, 1f) else 0f
    val nextPendingDay: CustomPlanDayEntity?
        get() = days.sortedBy { it.dayNumber }.firstOrNull { !it.isCompleted } ?: days.lastOrNull()
}

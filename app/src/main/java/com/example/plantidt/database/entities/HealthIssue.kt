package com.example.plantidt.database.entities

import androidx.room.Entity

@Entity
data class HealthIssue(
    val id: Long = 0,
    val plantId: Long,
    val issueType: HealthIssueType,
    val title: String,
    val description: String,
    val severity: IssueSeverity,
    val dateReported: String,
    val isResolved: Boolean = false,
    val resolution: String? = null,
    val iconResId: Int
)

enum class HealthIssueType {
    DISEASE,
    PEST,
    NUTRITION,
    WATERING,
    LIGHT,
    ENVIRONMENTAL,
    PHYSICAL_DAMAGE,
    UNKNOWN
}

enum class IssueSeverity {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}
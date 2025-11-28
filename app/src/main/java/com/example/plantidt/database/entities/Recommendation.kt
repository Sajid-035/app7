package com.example.plantidt.database.entities

import androidx.room.Entity

@Entity
data class Recommendation(
    val id: Long = 0,
    val plantId: Long,
    val recommendationType: RecommendationType,
    val title: String,
    val description: String,
    val priority: Priority,
    val dueDate: String? = null,
    val isCompleted: Boolean = false,
    val iconResId: Int
)

enum class RecommendationType {
    WATERING,
    FERTILIZING,
    PRUNING,
    REPOTTING,
    PEST_TREATMENT,
    DISEASE_TREATMENT,
    LIGHT_ADJUSTMENT,
    HUMIDITY_ADJUSTMENT,
    TEMPERATURE_ADJUSTMENT,
    GENERAL_CARE
}
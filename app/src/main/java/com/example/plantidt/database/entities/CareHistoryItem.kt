package com.example.plantidt.database.entities

import androidx.room.Entity

@Entity
data class CareHistoryItem(
    val id: Long = 0,
    val plantId: Long,
    val actionType: CareActionType,
    val actionDescription: String,
    val date: String, // Format: "yyyy-MM-dd"
    val notes: String? = null,
    val nextDueDate: String? = null,
    val iconResId: Int
)

enum class CareActionType {
    WATERING,
    FERTILIZING,
    PRUNING,
    REPOTTING,
    PEST_TREATMENT,
    DISEASE_TREATMENT,
    GENERAL_CARE
}

package com.example.plantidt.database.entities

import androidx.room.Entity

@Entity
data class CareInstruction(
    val id: Long = 0,
    val plantId: Long,
    val instructionType: CareInstructionType,
    val title: String,
    val description: String,
    val frequency: String? = null,
    val season: String? = null,
    val iconResId: Int,
    val priority: Priority = Priority.MEDIUM
)

enum class CareInstructionType {
    WATERING,
    FERTILIZING,
    PRUNING,
    REPOTTING,
    LIGHT,
    HUMIDITY,
    TEMPERATURE,
    PEST_PREVENTION,
    DISEASE_PREVENTION,
    GENERAL
}

enum class Priority {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}
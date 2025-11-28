package com.example.plantidt.database.entities

import androidx.room.Entity

@Entity
data class CommonPest(
    val id: Long = 0,
    val name: String,
    val description: String,
    val signs: List<String>,
    val treatment: List<String>,
    val preventionTips: List<String>,
    val severity: PestSeverity,
    val iconResId: Int,
    val imageResId: Int? = null,
    val affectedPlantTypes: List<String> = emptyList(),
    val seasonalActivity: List<String> = emptyList()
)

enum class PestSeverity {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}
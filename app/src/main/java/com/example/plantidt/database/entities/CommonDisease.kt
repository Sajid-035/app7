package com.example.plantidt.database.entities

import androidx.room.Entity

@Entity
data class CommonDisease(
    val id: Long = 0,
    val name: String,
    val description: String,
    val symptoms: List<String>,
    val treatment: List<String>,
    val preventionTips: List<String>,
    val severity: DiseaseSeverity,
    val iconResId: Int,
    val imageResId: Int? = null,
    val affectedPlantTypes: List<String> = emptyList()
)

enum class DiseaseSeverity {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}
package com.example.plantidt.database.entities

import androidx.room.Entity

@Entity
data class MyPlant(
    val id: Long = 0,
    val plantId: Long,
    val nickname: String,
    val dateAdded: String,
    val lastWatered: String? = null,
    val lastFertilized: String? = null,
    val nextWateringDate: String? = null,
    val nextFertilizingDate: String? = null,
    val healthStatus: HealthStatus,
    val notes: String? = null,
    val imageUri: String? = null,
    val location: String? = null,
    val customCareSchedule: Map<String, String> = emptyMap()
)

enum class HealthStatus {
    EXCELLENT,
    GOOD,
    FAIR,
    POOR,
    CRITICAL
}
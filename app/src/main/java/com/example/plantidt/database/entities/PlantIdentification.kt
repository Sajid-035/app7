package com.example.plantidt.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant_identifications")
data class PlantIdentification(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val plantId: String,
    val commonName: String,
    val scientificName: String,
    val family: String,
    val confidence: Float,
    val imageBase64: String,
    val timestamp: Long = System.currentTimeMillis(),
    val healthScore: Int = 0,
    val healthStatus: String = "Unknown"
)



package com.example.plantidt.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.plantidt.database.converters.Converters

@Entity(tableName = "plant_health_assessments")
data class PlantHealthAssessment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val plantId: String,
    val overallHealth: Int, // 0-100
    val lightHealth: Int,
    val waterHealth: Int,
    val nutritionHealth: Int,
    val diseaseRisk: Int,
    val pestRisk: Int,
    @TypeConverters(Converters::class)
    val healthIssues: List<String>,
    @TypeConverters(Converters::class)
    val recommendations: List<String>,
    val assessmentDate: Long = System.currentTimeMillis(),
    val imageBase64: String
)

package com.example.plantidt.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.plantidt.database.converters.Converters

@Entity(tableName = "plant_care_info")
data class PlantCareInfo(
    @PrimaryKey
    val plantId: String,
    val commonName: String,
    val scientificName: String,
    val family: String,
    val description: String,
    val careLevel: String, // Easy, Medium, Hard
    val lightRequirement: String, // Low, Medium, High, Bright indirect
    val waterRequirement: String, // Low, Medium, High
    val soilType: String,
    val temperature: String,
    val humidity: String,
    val fertilizer: String,
    val toxicity: String, // Pet safe, Toxic to pets, Toxic to humans
    val growthRate: String, // Slow, Medium, Fast
    val matureSize: String,
    val bloomTime: String,
    val propagationMethod: String,
    @TypeConverters(Converters::class)
    val careInstructions: List<String>,
    @TypeConverters(Converters::class)
    val commonProblems: List<String>,
    @TypeConverters(Converters::class)
    val tips: List<String>,
    val imageUrl: String = "",
    val lastUpdated: Long = System.currentTimeMillis()
)
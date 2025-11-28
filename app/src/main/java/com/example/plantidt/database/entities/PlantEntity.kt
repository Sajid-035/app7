package com.example.plantidt.database.entities

import androidx.room.Entity

@Entity
data class Plant(
    val id: Long = 0,
    val name: String,
    val scientificName: String,
    val family: String,
    val difficulty: CareDifficulty,
    val careInstructions: List<String>,
    val wateringFrequency: String,
    val lightRequirement: String,
    val humidity: String,
    val temperature: String,
    val soilType: String,
    val imageResId: Int? = null,
    val imageUrl: String? = null,
    val careTags: List<String> = emptyList(),
    val commonDiseases: List<Long> = emptyList(),
    val commonPests: List<Long> = emptyList()
)

enum class CareDifficulty {
    EASY,
    MEDIUM,
    HARD
}

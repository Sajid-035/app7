package com.example.plantidt.network

// Plant care response model and all nested types

data class PlantCareResponse(
    val plantName: String,
    val scientificName: String,
    val family: String,
    val description: String,
    val healthStatus: HealthStatus,
    val watering: WateringInfo,
    val lighting: LightingInfo,
    val temperature: TemperatureInfo,
    val humidity: HumidityInfo,
    val fertilizing: FertilizingInfo,
    val pruning: PruningInfo,
    val soil: SoilInfo,
    val commonProblems: List<String>,
    val toxicity: ToxicityInfo,
    val facts: List<String>
)

data class HealthStatus(
    val status: String,
    val score: Int,
    val level: String,
    val issues: List<String>
)

data class WateringInfo(
    val instructions: String,
    val frequency: Int,
    val daysUntilNext: Int,
    val urgency: String,
    val amount: String
)

data class LightingInfo(
    val requirement: String,
    val type: String,
    val hoursPerDay: Int,
    val instructions: String
)

data class TemperatureInfo(
    val description: String,
    val minTemp: Int,
    val maxTemp: Int,
    val preference: String
)

data class HumidityInfo(
    val description: String,
    val idealLevel: Int,
    val minLevel: Int,
    val maxLevel: Int
)

data class FertilizingInfo(
    val instructions: String,
    val frequency: Int,
    val daysUntilNext: Int,
    val type: String,
    val season: String
)

data class PruningInfo(
    val instructions: String,
    val season: String,
    val frequency: String
)

data class SoilInfo(
    val description: String,
    val type: String,
    val phRange: String
)

data class ToxicityInfo(
    val isToxic: Boolean,
    val toxicToPets: Boolean,
    val toxicToHumans: Boolean,
    val toxicityLevel: String,
    val symptoms: List<String>,
    val treatment: String
)

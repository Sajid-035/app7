package com.example.plantidt.utils

import com.example.plantidt.ComprehensivePlantResult
import com.example.plantidt.database.entities.PlantIdentification
import java.util.UUID

object PlantResultMapper {

    fun mapToEntity(result: ComprehensivePlantResult, imageBase64: String): PlantIdentification {
        return PlantIdentification(
            plantId = UUID.randomUUID().toString(),
            commonName = result.plantName,
            scientificName = result.scientificName,
            family = result.familyName ?: "", // Handle null safety if needed
            confidence = result.confidence.toFloat(),
            imageBase64 = imageBase64,
            timestamp = System.currentTimeMillis(),
            healthScore = if (result.isHealthy) 100 else calculateHealthScore(result.diseases),
            healthStatus = if (result.isHealthy) "Healthy" else "Issues Detected"
        )
    }

    private fun calculateHealthScore(diseases: List<com.example.plantidt.Disease>): Int {
        if (diseases.isEmpty()) return 100

        val avgConfidence = diseases.map { it.confidence }.average()
        return (100 - (avgConfidence * 100)).toInt().coerceIn(0, 100)
    }
}

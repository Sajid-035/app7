package com.example.plantidt.utils

import com.example.plantidt.database.entities.Plant
import com.example.plantidt.database.entities.CareDifficulty

object SearchHelper {

    fun filterPlantsByDifficulty(plants: List<Plant>, difficulty: CareDifficulty): List<Plant> {
        return plants.filter { it.difficulty == difficulty }
    }

    fun filterPlantsByCareTags(plants: List<Plant>, tags: List<String>): List<Plant> {
        return plants.filter { plant ->
            tags.any { tag -> plant.careTags.contains(tag) }
        }
    }

    fun searchPlantsByName(plants: List<Plant>, query: String): List<Plant> {
        val lowerQuery = query.lowercase()
        return plants.filter {
            it.name.lowercase().contains(lowerQuery) ||
                    it.scientificName.lowercase().contains(lowerQuery) ||
                    it.family.lowercase().contains(lowerQuery)
        }
    }

    fun sortPlantsByName(plants: List<Plant>): List<Plant> {
        return plants.sortedBy { it.name }
    }

    fun sortPlantsByDifficulty(plants: List<Plant>): List<Plant> {
        return plants.sortedBy { it.difficulty }
    }
}
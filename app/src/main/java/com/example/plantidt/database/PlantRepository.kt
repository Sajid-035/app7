package com.example.plantidt.database

import com.example.plantidt.database.entities.PlantCareInfo
import com.example.plantidt.database.entities.PlantHealthAssessment
import com.example.plantidt.database.entities.PlantIdentification
import com.example.plantidt.database.entities.UserGarden
import com.example.plantidt.network.PlantEntity
import kotlinx.coroutines.flow.Flow
import android.util.Log
import com.example.plantidt.ComprehensivePlantResult
import com.example.plantidt.database.daos.PlantDao
import com.example.plantidt.database.daos.PlantHealthAssessmentDao
import com.example.plantidt.database.entities.MyPlant

class PlantRepository(private val database: PlantDatabase) {

    private val plantDao = database.plantDao()
    private val careInfoDao = database.careInfoDao()
    private val healthAssessmentDao = database.healthAssessmentDao()
    private val gardenDao = database.gardenDao()

    // Existing methods for plant identification
    suspend fun saveIdentification(identification: PlantIdentification): Long {
        return plantDao.insertIdentification(identification)
    }

    suspend fun getIdentificationById(id: Long): PlantIdentification? {
        return try {
            plantDao.getIdentificationById(id)
        } catch (e: Exception) {
            Log.e("PlantRepository", "Error getting identification by ID: $id", e)
            null
        }
    }
    suspend fun getIdentificationsByPlantId(plantId: String): List<PlantIdentification> {
        return plantDao.getIdentificationsByPlantId(plantId)
    }

    suspend fun getAllIdentifications(): List<PlantIdentification> {
        return plantDao.getAllIdentifications()
    }

    suspend fun deleteIdentification(identification: PlantIdentification) {
        plantDao.deleteIdentification(identification)
    }

    // Existing methods for care info
    suspend fun saveCareInfo(careInfo: PlantCareInfo): Long {
        return careInfoDao.insertCareInfo(careInfo)
    }

    suspend fun getCareInfo(plantId: String): PlantCareInfo? {
        return careInfoDao.getCareInfoByPlantId(plantId)
    }


    suspend fun getAllCareInfos(): List<PlantCareInfo> {
        return careInfoDao.getAllCareInfos()
    }

    suspend fun updateCareInfo(careInfo: PlantCareInfo) {
        careInfoDao.updateCareInfo(careInfo)
    }

    suspend fun deleteCareInfo(careInfo: PlantCareInfo) {
        careInfoDao.deleteCareInfo(careInfo)
    }

    // Existing methods for health assessment
    suspend fun saveHealthAssessment(assessment: PlantHealthAssessment): Long {
        return healthAssessmentDao.insertHealthAssessment(assessment)
    }

    suspend fun getLatestHealthAssessment(plantId: String): PlantHealthAssessment? {
        return healthAssessmentDao.getLatestHealthAssessment(plantId)
    }

    suspend fun getAllHealthAssessments(plantId: String): List<PlantHealthAssessment> {
        return healthAssessmentDao.getAllHealthAssessments(plantId)
    }

    suspend fun updateHealthAssessment(assessment: PlantHealthAssessment) {
        healthAssessmentDao.updateHealthAssessment(assessment)
    }

    suspend fun deleteHealthAssessment(assessment: PlantHealthAssessment) {
        healthAssessmentDao.deleteHealthAssessment(assessment)
    }

    // NEW: Garden management methods
    suspend fun addToGarden(gardenPlant: UserGarden): Long {
        return gardenDao.insertGardenPlant(gardenPlant)
    }

    suspend fun isPlantInGarden(plantId: String): Boolean {
        return gardenDao.isPlantInGarden(plantId)
    }

    suspend fun getGardenPlant(plantId: String): UserGarden? {
        return gardenDao.getGardenPlantById(plantId)
    }

    fun getAllGardenPlants(): Flow<List<UserGarden>> {
        return gardenDao.getAllGardenPlants()
    }

    fun getFavoritePlants(): Flow<List<UserGarden>> {
        return gardenDao.getFavoritePlants()
    }

    suspend fun updateGardenPlant(gardenPlant: UserGarden) {
        gardenDao.updateGardenPlant(gardenPlant)
    }

    suspend fun removeFromGarden(plantId: String) {
        gardenDao.deleteGardenPlantById(plantId)
    }

    suspend fun removeFromGarden(gardenPlant: UserGarden) {
        gardenDao.deleteGardenPlant(gardenPlant)
    }

    // Garden plant care tracking
    suspend fun updateLastWatered(plantId: String, timestamp: Long = System.currentTimeMillis()) {
        gardenDao.updateLastWatered(plantId, timestamp)
    }

    suspend fun updateLastFertilized(plantId: String, timestamp: Long = System.currentTimeMillis()) {
        gardenDao.updateLastFertilized(plantId, timestamp)
    }

    suspend fun updateFavoriteStatus(plantId: String, isFavorite: Boolean) {
        gardenDao.updateFavoriteStatus(plantId, isFavorite)
    }

    suspend fun updateNickname(plantId: String, nickname: String) {
        gardenDao.updateNickname(plantId, nickname)
    }

    suspend fun updateNotes(plantId: String, notes: String) {
        gardenDao.updateNotes(plantId, notes)
    }

    suspend fun updateLocation(plantId: String, location: String) {
        gardenDao.updateLocation(plantId, location)
    }

    suspend fun updateReminderEnabled(plantId: String, enabled: Boolean) {
        gardenDao.updateReminderEnabled(plantId, enabled)
    }

    suspend fun updateWateringInterval(plantId: String, interval: Int) {
        gardenDao.updateWateringInterval(plantId, interval)
    }

    suspend fun updateFertilizingInterval(plantId: String, interval: Int) {
        gardenDao.updateFertilizingInterval(plantId, interval)
    }

    // Plant care reminders
    suspend fun getPlantsNeedingWater(): List<UserGarden> {
        return gardenDao.getPlantsNeedingWater(System.currentTimeMillis())
    }

    suspend fun getPlantsNeedingFertilizer(): List<UserGarden> {
        return gardenDao.getPlantsNeedingFertilizer(System.currentTimeMillis())
    }

    // Garden statistics
    suspend fun getGardenPlantCount(): Int {
        return gardenDao.getGardenPlantCount()
    }


    suspend fun getFavoritePlantCount(): Int {
        return gardenDao.getFavoritePlantCount()
    }

    // Combined data methods
    suspend fun getGardenPlantWithCareInfo(plantId: String): Pair<UserGarden?, PlantCareInfo?> {
        val gardenPlant = gardenDao.getGardenPlantById(plantId)
        val careInfo = careInfoDao.getCareInfoByPlantId(plantId)
        return Pair(gardenPlant, careInfo)
    }

    suspend fun getGardenPlantWithHealthAssessment(plantId: String): Pair<UserGarden?, PlantHealthAssessment?> {
        val gardenPlant = gardenDao.getGardenPlantById(plantId)
        val healthAssessment = healthAssessmentDao.getLatestHealthAssessment(plantId)
        return Pair(gardenPlant, healthAssessment)
    }

    // Search and filter methods
    suspend fun searchGardenPlants(query: String): List<UserGarden> {
        return gardenDao.getAllGardenPlants().let { flow ->
            // Since we can't directly search in Room without a proper search query,
            // we'll get all plants and filter them here
            // In a real app, you'd want to add a search query to the DAO
            val allPlants = mutableListOf<UserGarden>()
            flow.collect { plants ->
                allPlants.addAll(plants.filter { plant ->
                    plant.nickname.contains(query, ignoreCase = true) ||
                            plant.notes.contains(query, ignoreCase = true) ||
                            plant.location.contains(query, ignoreCase = true)
                })
            }
            allPlants
        }
    }

    suspend fun getPlantsByLocation(location: String): List<UserGarden> {
        return gardenDao.getAllGardenPlants().let { flow ->
            val plants = mutableListOf<UserGarden>()
            flow.collect { allPlants ->
                plants.addAll(allPlants.filter { it.location.equals(location, ignoreCase = true) })
            }
            plants
        }
    }
    suspend fun saveComprehensiveResult(result: ComprehensivePlantResult, imageBase64: String): Long {
        val plantIdentification = PlantIdentification(
            plantId = result.scientificName,
            commonName = result.plantName,
            scientificName = result.scientificName,
            family = result.familyName,
            confidence = result.confidence.toFloat(),
            healthScore = if (result.isHealthy) 100 else 60,
            healthStatus = if (result.isHealthy) "Healthy" else "Issues Detected",
            imageBase64 = imageBase64,
            timestamp = System.currentTimeMillis()
        )
        // FIX: Explicitly use 'this' to refer to the class property
        val identificationId = this.plantDao.insertIdentification(plantIdentification)

        val careInfo = PlantCareInfo(
            plantId = result.scientificName,
            commonName = result.plantName,
            scientificName = result.scientificName,
            family = result.familyName,
            description = result.plantFacts.description,
            careLevel = "Medium",
            lightRequirement = result.careInstructions.lighting,
            waterRequirement = result.careInstructions.watering,
            soilType = result.careInstructions.soilType,
            temperature = result.careInstructions.temperature,
            humidity = result.careInstructions.humidity,
            fertilizer = result.careInstructions.fertilizing,
            toxicity = if (result.toxicityInfo.isToxic) "Toxic" else "Non-toxic",
            growthRate = result.plantFacts.growthRate,
            matureSize = result.plantFacts.mature_size,
            bloomTime = result.plantFacts.bloomTime,
            propagationMethod = "Varies",
            careInstructions = listOf(),
            commonProblems = result.careInstructions.commonProblems,
            tips = result.plantFacts.uses,
            lastUpdated = System.currentTimeMillis()
        )
        // FIX: Explicitly use 'this' to refer to the class property
        this.careInfoDao.insertCareInfo(careInfo)

        if (!result.isHealthy) {
            val healthAssessment = PlantHealthAssessment(
                plantId = result.scientificName,
                overallHealth = 60,
                healthIssues = result.diseases.map { it.name },
                recommendations = result.diseases.map { it.treatment },
                imageBase64 = imageBase64,
                assessmentDate = System.currentTimeMillis(),
                lightHealth = 80,
                waterHealth = 50,
                nutritionHealth = 70,
                diseaseRisk = result.diseases.maxOfOrNull { it.confidence }?.times(100)?.toInt() ?: 0,
                pestRisk = 0
            )
            // FIX: Explicitly use 'this' to refer to the class property
            this.healthAssessmentDao.insertHealthAssessment(healthAssessment)
        }

        return identificationId
    }
}

// PASTE THIS ENTIRE FUNCTION INSIDE YOUR `PlantRepository` CLASS

// PASTE THIS INSIDE THE `PlantRepository` CLASS

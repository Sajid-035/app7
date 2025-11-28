package com.example.plantidt.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.example.plantidt.database.entities.UserGarden

@Dao
interface UserGardenDao {

    @Query("SELECT * FROM user_garden ORDER BY dateAdded DESC")
    fun getAllGardenPlants(): Flow<List<UserGarden>>

    @Query("SELECT * FROM user_garden WHERE isFavorite = 1 ORDER BY dateAdded DESC")
    fun getFavoritePlants(): Flow<List<UserGarden>>


    @Query("SELECT * FROM user_garden WHERE plantId = :plantId LIMIT 1")
    suspend fun getGardenPlantById(plantId: String): UserGarden?

    @Query("SELECT EXISTS(SELECT 1 FROM user_garden WHERE plantId = :plantId)")
    suspend fun isPlantInGarden(plantId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGardenPlant(gardenPlant: UserGarden): Long

    @Update
    suspend fun updateGardenPlant(gardenPlant: UserGarden)

    @Delete
    suspend fun deleteGardenPlant(gardenPlant: UserGarden)

    @Query("DELETE FROM user_garden WHERE plantId = :plantId")
    suspend fun deleteGardenPlantById(plantId: String)

    @Query("UPDATE user_garden SET lastWatered = :timestamp WHERE plantId = :plantId")
    suspend fun updateLastWatered(plantId: String, timestamp: Long)

    @Query("UPDATE user_garden SET lastFertilized = :timestamp WHERE plantId = :plantId")
    suspend fun updateLastFertilized(plantId: String, timestamp: Long)

    @Query("UPDATE user_garden SET isFavorite = :isFavorite WHERE plantId = :plantId")
    suspend fun updateFavoriteStatus(plantId: String, isFavorite: Boolean)

    @Query("UPDATE user_garden SET nickname = :nickname WHERE plantId = :plantId")
    suspend fun updateNickname(plantId: String, nickname: String)

    @Query("UPDATE user_garden SET notes = :notes WHERE plantId = :plantId")
    suspend fun updateNotes(plantId: String, notes: String)

    @Query("UPDATE user_garden SET location = :location WHERE plantId = :plantId")
    suspend fun updateLocation(plantId: String, location: String)

    @Query("UPDATE user_garden SET reminderEnabled = :enabled WHERE plantId = :plantId")
    suspend fun updateReminderEnabled(plantId: String, enabled: Boolean)

    @Query("UPDATE user_garden SET wateringInterval = :interval WHERE plantId = :plantId")
    suspend fun updateWateringInterval(plantId: String, interval: Int)

    @Query("UPDATE user_garden SET fertilizingInterval = :interval WHERE plantId = :plantId")
    suspend fun updateFertilizingInterval(plantId: String, interval: Int)

    // Get plants that need watering
    @Query("""
        SELECT * FROM user_garden 
        WHERE reminderEnabled = 1 
        AND (lastWatered + (wateringInterval * 24 * 60 * 60 * 1000)) <= :currentTime
        ORDER BY lastWatered ASC
    """)
    suspend fun getPlantsNeedingWater(currentTime: Long): List<UserGarden>

    // Get plants that need fertilizing
    @Query("""
        SELECT * FROM user_garden 
        WHERE reminderEnabled = 1 
        AND (lastFertilized + (fertilizingInterval * 24 * 60 * 60 * 1000)) <= :currentTime
        ORDER BY lastFertilized ASC
    """)
    suspend fun getPlantsNeedingFertilizer(currentTime: Long): List<UserGarden>

    @Query("SELECT COUNT(*) FROM user_garden")
    suspend fun getGardenPlantCount(): Int

    @Query("SELECT COUNT(*) FROM user_garden WHERE isFavorite = 1")
    suspend fun getFavoritePlantCount(): Int
}
package com.example.plantidt.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.plantidt.database.entities.PlantCareInfo

@Dao
interface PlantCareInfoDao {
    @Query("SELECT * FROM plant_care_info WHERE plantId = :plantId")
    suspend fun getCareInfoByPlantId(plantId: String): PlantCareInfo?

    @Query("SELECT * FROM plant_care_info")
    suspend fun getAllCareInfos(): List<PlantCareInfo>

    @Query("SELECT * FROM plant_care_info WHERE commonName LIKE :query OR scientificName LIKE :query")
    suspend fun searchPlants(query: String): List<PlantCareInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCareInfo(careInfo: PlantCareInfo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCareInfoList(careInfoList: List<PlantCareInfo>)

    @Update
    suspend fun updateCareInfo(careInfo: PlantCareInfo)

    @Delete
    suspend fun deleteCareInfo(careInfo: PlantCareInfo)

    @Query("SELECT COUNT(*) FROM plant_care_info")
    suspend fun getCareInfoCount(): Int

    @Query("DELETE FROM plant_care_info WHERE lastUpdated < :cutoffTime")
    suspend fun deleteOldCareInfo(cutoffTime: Long)
}
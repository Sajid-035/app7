package com.example.plantidt.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.plantidt.database.entities.PlantIdentification

@Dao
interface PlantDao {
    @Query("SELECT * FROM plant_identifications ORDER BY timestamp DESC")
    suspend fun getAllIdentifications(): List<PlantIdentification>

    @Query("SELECT * FROM plant_identifications WHERE id = :id")
    suspend fun getIdentificationById(id: Long): PlantIdentification?

    @Query("SELECT * FROM plant_identifications WHERE plantId = :plantId ORDER BY timestamp DESC")
    suspend fun getIdentificationsByPlantId(plantId: String): List<PlantIdentification>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIdentification(identification: PlantIdentification): Long

    @Update
    suspend fun updateIdentification(identification: PlantIdentification)

    @Delete
    suspend fun deleteIdentification(identification: PlantIdentification)

    @Query("DELETE FROM plant_identifications WHERE timestamp < :cutoffTime")
    suspend fun deleteOldIdentifications(cutoffTime: Long)

    @Query("SELECT COUNT(*) FROM plant_identifications")
    suspend fun getIdentificationCount(): Int
}
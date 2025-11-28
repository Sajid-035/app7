package com.example.plantidt.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.plantidt.database.entities.PlantIdentification

@Dao
interface PlantIdentificationDao {
    @Query("SELECT * FROM plant_identifications ORDER BY timestamp DESC")
    fun getAllIdentifications(): LiveData<List<PlantIdentification>>

    @Query("SELECT * FROM plant_identifications WHERE id = :id")
    suspend fun getIdentificationById(id: Long): PlantIdentification?

    @Query("SELECT * FROM plant_identifications WHERE plantId = :plantId")
    suspend fun getIdentificationsByPlantId(plantId: String): List<PlantIdentification>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIdentification(identification: PlantIdentification): Long

    @Delete
    suspend fun deleteIdentification(identification: PlantIdentification)

    @Query("DELETE FROM plant_identifications WHERE timestamp < :cutoffTime")
    suspend fun deleteOldIdentifications(cutoffTime: Long)
}
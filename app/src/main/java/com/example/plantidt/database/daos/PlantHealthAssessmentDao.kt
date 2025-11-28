package com.example.plantidt.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.plantidt.database.entities.PlantHealthAssessment

@Dao
interface PlantHealthAssessmentDao {
    @Query("SELECT * FROM plant_health_assessments WHERE plantId = :plantId ORDER BY assessmentDate DESC")
    suspend fun getAllHealthAssessments(plantId: String): List<PlantHealthAssessment>

    @Query("SELECT * FROM plant_health_assessments WHERE plantId = :plantId ORDER BY assessmentDate DESC LIMIT 1")
    suspend fun getLatestHealthAssessment(plantId: String): PlantHealthAssessment?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthAssessment(assessment: PlantHealthAssessment): Long

    @Update
    suspend fun updateHealthAssessment(assessment: PlantHealthAssessment)

    @Delete
    suspend fun deleteHealthAssessment(assessment: PlantHealthAssessment)

    @Query("DELETE FROM plant_health_assessments WHERE assessmentDate < :cutoffTime")
    suspend fun deleteOldHealthAssessments(cutoffTime: Long)

    @Query("SELECT COUNT(*) FROM plant_health_assessments WHERE plantId = :plantId")
    suspend fun getHealthAssessmentCount(plantId: String): Int
}
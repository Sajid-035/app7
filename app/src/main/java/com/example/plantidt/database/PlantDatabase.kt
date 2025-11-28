package com.example.plantidt.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.plantidt.database.converters.Converters
import com.example.plantidt.database.daos.PlantCareInfoDao
import com.example.plantidt.database.daos.PlantDao
import com.example.plantidt.database.daos.PlantHealthAssessmentDao
import com.example.plantidt.database.daos.UserGardenDao
import com.example.plantidt.database.entities.PlantCareInfo
import com.example.plantidt.database.entities.UserGarden
import com.example.plantidt.database.entities.PlantIdentification
import com.example.plantidt.database.entities.PlantHealthAssessment

@Database(
    entities = [
        PlantIdentification::class,
        PlantCareInfo::class,
        PlantHealthAssessment::class,
        UserGarden::class
    ],
    version = 2, // Increment version due to new table
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PlantDatabase : RoomDatabase() {

    abstract fun plantDao(): PlantDao
    abstract fun careInfoDao(): PlantCareInfoDao
    abstract fun healthAssessmentDao(): PlantHealthAssessmentDao
    abstract fun gardenDao(): UserGardenDao

    companion object {
        @Volatile
        private var INSTANCE: PlantDatabase? = null

        fun getDatabase(context: Context): PlantDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantDatabase::class.java,
                    "plant_database"
                )
                    .fallbackToDestructiveMigration() // For development - handle migrations properly in production
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}







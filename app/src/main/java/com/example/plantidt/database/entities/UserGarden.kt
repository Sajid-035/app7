package com.example.plantidt.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_garden",
    foreignKeys = [
        ForeignKey(
            entity = PlantCareInfo::class,
            parentColumns = ["plantId"],
            childColumns = ["plantId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["plantId"])]
)
data class UserGarden(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val plantId: String,
    val nickname: String = "",
    val notes: String = "",
    val dateAdded: Long = System.currentTimeMillis(),
    val lastWatered: Long = 0,
    val lastFertilized: Long = 0,
    val reminderEnabled: Boolean = false,
    val wateringInterval: Int = 7, // days
    val fertilizingInterval: Int = 30, // days
    val location: String = "", // e.g., "Living room", "Balcony"
    val isFavorite: Boolean = false
)
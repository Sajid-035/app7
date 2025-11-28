package com.example.plantidt.models

data class MyPlantItem(
    val id: Long,
    val name: String,
    val nickname: String,
    val scientificName: String,
    val healthStatus: String,
    val healthLevel: String, // excellent, good, fair, poor
    val imageBase64: String,
    val nextWatering: Long?,
    val nextFertilizing: Long?,
    val dateAdded: Long
)
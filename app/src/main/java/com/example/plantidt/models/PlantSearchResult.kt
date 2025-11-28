package com.example.plantidt.models

data class PlantSearchResult(
    val id: String,
    val commonName: String,
    val scientificName: String,
    val family: String,
    val careLevel: String, // Easy, Medium, Hard
    val imageUrl: String,
    val lowLight: Boolean,
    val lowWater: Boolean,
    val petSafe: Boolean,
    val airPurifying: Boolean
)

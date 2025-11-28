package com.example.plantidt.utils

class PlantCareCalculator {

    companion object {
        fun calculateNextWateringDate(lastWatered: Long, frequency: Int): Long {
            return lastWatered + (frequency * 24 * 60 * 60 * 1000L)
        }

        fun calculateNextFertilizingDate(lastFertilized: Long, frequency: Int): Long {
            return lastFertilized + (frequency * 24 * 60 * 60 * 1000L)
        }

        fun calculateHealthScore(
            wateringHealth: Int,
            lightHealth: Int,
            temperatureHealth: Int,
            humidityHealth: Int
        ): Int {
            return (wateringHealth + lightHealth + temperatureHealth + humidityHealth) / 4
        }

        fun getHealthLevel(score: Int): String {
            return when {
                score >= 90 -> "excellent"
                score >= 70 -> "good"
                score >= 50 -> "fair"
                else -> "poor"
            }
        }

        fun getDaysUntilNext(targetDate: Long): Int {
            val currentTime = System.currentTimeMillis()
            return ((targetDate - currentTime) / (1000 * 60 * 60 * 24)).toInt()
        }

        fun formatCareFrequency(days: Int): String {
            return when {
                days == 1 -> "Daily"
                days == 7 -> "Weekly"
                days == 14 -> "Bi-weekly"
                days == 30 -> "Monthly"
                days < 7 -> "Every $days days"
                days < 30 -> "Every ${days / 7} weeks"
                else -> "Every ${days / 30} months"
            }
        }
    }
}
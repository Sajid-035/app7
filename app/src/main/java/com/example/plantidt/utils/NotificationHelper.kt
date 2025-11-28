package com.example.plantidt.utils

import com.example.plantidt.database.entities.MyPlant
import com.example.plantidt.database.entities.HealthIssue

object NotificationHelper {

    fun createWateringReminder(plant: MyPlant): String {
        return "🌱 Time to water ${plant.nickname}!"
    }

    fun createFertilizingReminder(plant: MyPlant): String {
        return "🌿 Time to fertilize ${plant.nickname}!"
    }

    fun createHealthAlert(plant: MyPlant, issue: HealthIssue): String {
        return "⚠️ ${plant.nickname} needs attention: ${issue.title}"
    }

    fun createGeneralReminder(plant: MyPlant, action: String): String {
        return "🌺 Don't forget to $action ${plant.nickname}!"
    }

    fun createRepottingReminder(plant: MyPlant): String {
        return "🪴 Time to repot ${plant.nickname}!"
    }

    fun createPruningReminder(plant: MyPlant): String {
        return "✂️ Time to prune ${plant.nickname}!"
    }

    fun createHealthStatusNotification(plant: MyPlant): String {
        return when (plant.healthStatus) {
            com.example.plantidt.database.entities.HealthStatus.EXCELLENT -> "🌟 ${plant.nickname} is thriving!"
            com.example.plantidt.database.entities.HealthStatus.GOOD -> "😊 ${plant.nickname} is doing well!"
            com.example.plantidt.database.entities.HealthStatus.FAIR -> "😐 ${plant.nickname} needs some attention"
            com.example.plantidt.database.entities.HealthStatus.POOR -> "😟 ${plant.nickname} is struggling"
            com.example.plantidt.database.entities.HealthStatus.CRITICAL -> "🚨 ${plant.nickname} needs immediate care!"
        }
    }

    fun createCareCompletedMessage(plant: MyPlant, action: String): String {
        return "✅ Great job! You've completed $action for ${plant.nickname}"
    }

    fun createOverdueTaskNotification(plant: MyPlant, task: String, daysPast: Int): String {
        return "⏰ ${plant.nickname}'s $task is $daysPast days overdue!"
    }
}
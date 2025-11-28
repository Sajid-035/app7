package com.example.plantidt.utils

import com.example.plantidt.database.entities.HealthStatus
import com.example.plantidt.database.entities.CareDifficulty
import com.example.plantidt.database.entities.DiseaseSeverity
import com.example.plantidt.database.entities.PestSeverity
import com.example.plantidt.database.entities.CareInstructionType
import com.example.plantidt.database.entities.HealthIssueType
import com.example.plantidt.database.entities.IssueSeverity
import com.example.plantidt.database.entities.Priority
import com.example.plantidt.database.entities.RecommendationType
import com.example.plantidt.R
import com.example.plantidt.database.entities.CareActionType

object PlantDataHelper {

    fun getHealthStatusColor(status: HealthStatus): Int {
        return when (status) {
            HealthStatus.EXCELLENT, HealthStatus.GOOD -> R.color.health_good
            HealthStatus.FAIR -> R.color.health_fair
            HealthStatus.POOR, HealthStatus.CRITICAL -> R.color.health_poor
        }
    }

    fun getDifficultyColor(difficulty: CareDifficulty): Int {
        return when (difficulty) {
            CareDifficulty.EASY -> R.color.difficulty_easy
            CareDifficulty.MEDIUM -> R.color.difficulty_medium
            CareDifficulty.HARD -> R.color.difficulty_hard
        }
    }

    fun getSeverityColor(severity: DiseaseSeverity): Int {
        return when (severity) {
            DiseaseSeverity.LOW -> R.color.health_good
            DiseaseSeverity.MEDIUM -> R.color.health_fair
            DiseaseSeverity.HIGH -> R.color.warning
            DiseaseSeverity.CRITICAL -> R.color.health_poor
        }
    }

    fun getPestSeverityColor(severity: PestSeverity): Int {
        return when (severity) {
            PestSeverity.LOW -> R.color.health_good
            PestSeverity.MEDIUM -> R.color.health_fair
            PestSeverity.HIGH -> R.color.warning
            PestSeverity.CRITICAL -> R.color.health_poor
        }
    }

    fun getCareActionIcon(actionType: CareActionType): Int {
        return when (actionType) {
            CareActionType.WATERING -> R.drawable.ic_water_drop
            CareActionType.FERTILIZING -> R.drawable.ic_fertilizer
            CareActionType.PRUNING -> R.drawable.ic_plant_care
            CareActionType.REPOTTING -> R.drawable.ic_plant_care
            CareActionType.PEST_TREATMENT -> R.drawable.ic_pest_generic
            CareActionType.DISEASE_TREATMENT -> R.drawable.ic_disease_generic
            CareActionType.GENERAL_CARE -> R.drawable.ic_care
        }
    }

    fun getCareInstructionIcon(type: CareInstructionType): Int {
        return when (type) {
            CareInstructionType.WATERING -> R.drawable.ic_water_drop
            CareInstructionType.FERTILIZING -> R.drawable.ic_fertilizer
            CareInstructionType.PRUNING -> R.drawable.ic_plant_care
            CareInstructionType.REPOTTING -> R.drawable.ic_plant_care
            CareInstructionType.LIGHT -> R.drawable.ic_plant_care
            CareInstructionType.HUMIDITY -> R.drawable.ic_plant_care
            CareInstructionType.TEMPERATURE -> R.drawable.ic_plant_care
            CareInstructionType.PEST_PREVENTION -> R.drawable.ic_pest_generic
            CareInstructionType.DISEASE_PREVENTION -> R.drawable.ic_disease_generic
            CareInstructionType.GENERAL -> R.drawable.ic_care
        }
    }

    fun getHealthIssueIcon(type: HealthIssueType): Int {
        return when (type) {
            HealthIssueType.DISEASE -> R.drawable.ic_disease_generic
            HealthIssueType.PEST -> R.drawable.ic_pest_generic
            HealthIssueType.NUTRITION -> R.drawable.ic_fertilizer
            HealthIssueType.WATERING -> R.drawable.ic_water_drop
            HealthIssueType.LIGHT -> R.drawable.ic_plant_care
            HealthIssueType.ENVIRONMENTAL -> R.drawable.ic_warning
            HealthIssueType.PHYSICAL_DAMAGE -> R.drawable.ic_warning
            HealthIssueType.UNKNOWN -> R.drawable.ic_warning
        }
    }

    fun getIssueSeverityColor(severity: IssueSeverity): Int {
        return when (severity) {
            IssueSeverity.LOW -> R.color.health_good
            IssueSeverity.MEDIUM -> R.color.health_fair
            IssueSeverity.HIGH -> R.color.warning
            IssueSeverity.CRITICAL -> R.color.health_poor
        }
    }

    fun getPriorityColor(priority: Priority): Int {
        return when (priority) {
            Priority.LOW -> R.color.priority_low
            Priority.MEDIUM -> R.color.priority_medium
            Priority.HIGH -> R.color.priority_high
            Priority.CRITICAL -> R.color.priority_critical
        }
    }

    fun getRecommendationTypeIcon(type: RecommendationType): Int {
        return when (type) {
            RecommendationType.WATERING -> R.drawable.ic_water_drop
            RecommendationType.FERTILIZING -> R.drawable.ic_fertilizer
            RecommendationType.PRUNING -> R.drawable.ic_plant_care
            RecommendationType.REPOTTING -> R.drawable.ic_plant_care
            RecommendationType.PEST_TREATMENT -> R.drawable.ic_pest_generic
            RecommendationType.DISEASE_TREATMENT -> R.drawable.ic_disease_generic
            RecommendationType.LIGHT_ADJUSTMENT -> R.drawable.ic_light
            RecommendationType.HUMIDITY_ADJUSTMENT -> R.drawable.ic_humidity
            RecommendationType.TEMPERATURE_ADJUSTMENT -> R.drawable.ic_temperature
            RecommendationType.GENERAL_CARE -> R.drawable.ic_care
        }
    }
}
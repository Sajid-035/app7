package com.example.plantidt.utils

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

object Constants {

    // Default care frequencies (in days)
    const val DEFAULT_WATERING_FREQUENCY = 7
    const val DEFAULT_FERTILIZING_FREQUENCY = 30
    const val DEFAULT_PRUNING_FREQUENCY = 90
    const val DEFAULT_REPOTTING_FREQUENCY = 365

    // Date formats
    const val DATE_FORMAT_DISPLAY = "MMM dd, yyyy"
    const val DATE_FORMAT_STORAGE = "yyyy-MM-dd"
    const val DATE_FORMAT_SHORT = "MMM dd"

    // Notification IDs
    const val WATERING_NOTIFICATION_ID = 1001
    const val FERTILIZING_NOTIFICATION_ID = 1002
    const val HEALTH_ALERT_NOTIFICATION_ID = 1003

    // Preferences keys
    const val PREF_REMINDER_ENABLED = "reminder_enabled"
    const val PREF_REMINDER_TIME = "reminder_time"
    const val PREF_THEME = "theme"

    // Default values
    const val DEFAULT_REMINDER_TIME = "09:00"
    const val DEFAULT_THEME = "light"

    // Care tags
    val COMMON_CARE_TAGS = listOf(
        "Low light", "Bright light", "Direct sunlight",
        "Low water", "Medium water", "High water",
        "Low maintenance", "High maintenance",
        "Pet friendly", "Toxic to pets",
        "Air purifying", "Flowering", "Foliage"
    )

    // Plant families
    val COMMON_PLANT_FAMILIES = listOf(
        "Araceae", "Cactaceae", "Ficus", "Palmae",
        "Rosaceae", "Orchidaceae", "Succulent", "Fern"
    )
}
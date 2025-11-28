package com.example.plantidt.utils

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

object ValidationHelper {

    fun isValidPlantName(name: String): Boolean {
        return name.trim().isNotEmpty() && name.length <= 50
    }

    fun isValidNickname(nickname: String): Boolean {
        return nickname.trim().isNotEmpty() && nickname.length <= 30
    }

    fun isValidDate(date: String): Boolean {
        return date.toDate() != null
    }

    fun isValidWateringFrequency(frequency: Int): Boolean {
        return frequency in 1..365
    }

    fun isValidFertilizingFrequency(frequency: Int): Boolean {
        return frequency in 1..365
    }

    fun isValidNotes(notes: String): Boolean {
        return notes.length <= 500
    }
}

package com.example.plantidt.utils

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

object CareScheduleHelper {

    fun getNextWateringDate(lastWatered: String?, frequency: Int): String? {
        if (lastWatered == null) return null

        val lastWateredDate = lastWatered.toDate() ?: return null
        val calendar = Calendar.getInstance().apply {
            time = lastWateredDate
            add(Calendar.DAY_OF_MONTH, frequency)
        }

        return calendar.time.toDateString()
    }

    fun getNextFertilizingDate(lastFertilized: String?, frequency: Int): String? {
        if (lastFertilized == null) return null

        val lastFertilizedDate = lastFertilized.toDate() ?: return null
        val calendar = Calendar.getInstance().apply {
            time = lastFertilizedDate
            add(Calendar.DAY_OF_MONTH, frequency)
        }

        return calendar.time.toDateString()
    }

    fun isOverdue(dueDate: String?): Boolean {
        if (dueDate == null) return false

        val due = dueDate.toDate() ?: return false
        val today = Date()

        return due.before(today)
    }

    fun getDaysUntilDue(dueDate: String?): Int? {
        if (dueDate == null) return null

        val due = dueDate.toDate() ?: return null
        val today = Date()

        val diffInMillis = due.time - today.time
        return (diffInMillis / (1000 * 60 * 60 * 24)).toInt()
    }
}
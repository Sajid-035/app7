package com.example.plantidt.utils

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

// Date extensions
fun String.toDate(format: String = "yyyy-MM-dd"): Date? {
    return try {
        SimpleDateFormat(format, Locale.getDefault()).parse(this)
    } catch (e: Exception) {
        null
    }
}

fun Date.toDateString(format: String = "yyyy-MM-dd"): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(this)
}

fun Date.toDisplayString(): String {
    val today = Calendar.getInstance()
    val dateCalendar = Calendar.getInstance().apply { time = this@toDisplayString }

    val diffInDays = ((today.timeInMillis - dateCalendar.timeInMillis) / (1000 * 60 * 60 * 24)).toInt()

    return when {
        diffInDays == 0 -> "Today"
        diffInDays == 1 -> "Yesterday"
        diffInDays == -1 -> "Tomorrow"
        diffInDays < 0 -> "In ${-diffInDays} days"
        diffInDays < 7 -> "$diffInDays days ago"
        else -> SimpleDateFormat("MMM dd", Locale.getDefault()).format(this)
    }
}

// View extensions
fun View.setBackgroundColorRes(@ColorRes colorRes: Int) {
    setBackgroundColor(ContextCompat.getColor(context, colorRes))
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

// Context extensions
fun Context.getColorCompat(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}
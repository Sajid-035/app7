package com.example.plantidt

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class SetReminderActivity : AppCompatActivity() {

    private lateinit var plantNameText: TextView
    private lateinit var wateringReminderSwitch: SwitchMaterial
    private lateinit var wateringIntervalSlider: Slider
    private lateinit var wateringIntervalText: TextView
    private lateinit var wateringTimeButton: MaterialButton
    private lateinit var wateringTimeText: TextView

    private lateinit var fertilizingReminderSwitch: SwitchMaterial
    private lateinit var fertilizingIntervalSlider: Slider
    private lateinit var fertilizingIntervalText: TextView
    private lateinit var fertilizingTimeButton: MaterialButton
    private lateinit var fertilizingTimeText: TextView

    private lateinit var saveRemindersButton: MaterialButton
    private lateinit var cancelButton: MaterialButton

    private var wateringHour = 9
    private var wateringMinute = 0
    private var fertilizingHour = 9
    private var fertilizingMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_reminder)

        initializeViews()
        setupClickListeners()
        loadPlantData()
    }

    private fun initializeViews() {
        plantNameText = findViewById(R.id.plantNameText)

        wateringReminderSwitch = findViewById(R.id.wateringReminderSwitch)
        wateringIntervalSlider = findViewById(R.id.wateringIntervalSlider)
        wateringIntervalText = findViewById(R.id.wateringIntervalText)
        wateringTimeButton = findViewById(R.id.wateringTimeButton)
        wateringTimeText = findViewById(R.id.wateringTimeText)

        fertilizingReminderSwitch = findViewById(R.id.fertilizingReminderSwitch)
        fertilizingIntervalSlider = findViewById(R.id.fertilizingIntervalSlider)
        fertilizingIntervalText = findViewById(R.id.fertilizingIntervalText)
        fertilizingTimeButton = findViewById(R.id.fertilizingTimeButton)
        fertilizingTimeText = findViewById(R.id.fertilizingTimeText)

        saveRemindersButton = findViewById(R.id.saveRemindersButton)
        cancelButton = findViewById(R.id.cancelButton)
    }

    private fun setupClickListeners() {
        wateringReminderSwitch.setOnCheckedChangeListener { _, isChecked ->
            enableWateringControls(isChecked)
        }

        fertilizingReminderSwitch.setOnCheckedChangeListener { _, isChecked ->
            enableFertilizingControls(isChecked)
        }

        wateringIntervalSlider.addOnChangeListener { _, value, _ ->
            wateringIntervalText.text = "Every ${value.toInt()} days"
        }

        fertilizingIntervalSlider.addOnChangeListener { _, value, _ ->
            val weeks = value.toInt()
            fertilizingIntervalText.text = "Every $weeks weeks"
        }

        wateringTimeButton.setOnClickListener {
            showWateringTimePicker()
        }

        fertilizingTimeButton.setOnClickListener {
            showFertilizingTimePicker()
        }

        saveRemindersButton.setOnClickListener {
            saveReminders()
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun loadPlantData() {
        val plantName = intent.getStringExtra("PLANT_NAME") ?: "Unknown Plant"
        plantNameText.text = "Set reminders for $plantName"

        // Set default values
        wateringIntervalSlider.value = 7f
        wateringIntervalText.text = "Every 7 days"
        wateringTimeText.text = "9:00 AM"

        fertilizingIntervalSlider.value = 4f
        fertilizingIntervalText.text = "Every 4 weeks"
        fertilizingTimeText.text = "9:00 AM"

        // Initially disable controls
        enableWateringControls(false)
        enableFertilizingControls(false)
    }

    private fun enableWateringControls(enabled: Boolean) {
        wateringIntervalSlider.isEnabled = enabled
        wateringTimeButton.isEnabled = enabled
        wateringIntervalSlider.alpha = if (enabled) 1.0f else 0.5f
        wateringTimeButton.alpha = if (enabled) 1.0f else 0.5f
    }

    private fun enableFertilizingControls(enabled: Boolean) {
        fertilizingIntervalSlider.isEnabled = enabled
        fertilizingTimeButton.isEnabled = enabled
        fertilizingIntervalSlider.alpha = if (enabled) 1.0f else 0.5f
        fertilizingTimeButton.alpha = if (enabled) 1.0f else 0.5f
    }

    private fun showWateringTimePicker() {
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(wateringHour)
            .setMinute(wateringMinute)
            .setTitleText("Select watering time")
            .build()

        timePicker.addOnPositiveButtonClickListener {
            wateringHour = timePicker.hour
            wateringMinute = timePicker.minute
            wateringTimeText.text = formatTime(wateringHour, wateringMinute)
        }

        timePicker.show(supportFragmentManager, "WateringTimePicker")
    }

    private fun showFertilizingTimePicker() {
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(fertilizingHour)
            .setMinute(fertilizingMinute)
            .setTitleText("Select fertilizing time")
            .build()

        timePicker.addOnPositiveButtonClickListener {
            fertilizingHour = timePicker.hour
            fertilizingMinute = timePicker.minute
            fertilizingTimeText.text = formatTime(fertilizingHour, fertilizingMinute)
        }

        timePicker.show(supportFragmentManager, "FertilizingTimePicker")
    }

    private fun formatTime(hour: Int, minute: Int): String {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)

        val amPm = if (hour >= 12) "PM" else "AM"
        val displayHour = if (hour == 0) 12 else if (hour > 12) hour - 12 else hour

        return String.format("%d:%02d %s", displayHour, minute, amPm)
    }

    private fun saveReminders() {
        val plantName = intent.getStringExtra("PLANT_NAME") ?: "Unknown Plant"

        try {
            if (wateringReminderSwitch.isChecked) {
                scheduleWateringReminder(plantName)
            }

            if (fertilizingReminderSwitch.isChecked) {
                scheduleFertilizingReminder(plantName)
            }

            Snackbar.make(saveRemindersButton, "Reminders saved successfully!", Snackbar.LENGTH_SHORT).show()

            // Return to previous activity after a short delay
            saveRemindersButton.postDelayed({
                finish()
            }, 1500)

        } catch (e: Exception) {
            Snackbar.make(saveRemindersButton, "Failed to save reminders: ${e.message}", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun scheduleWateringReminder(plantName: String) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, PlantReminderReceiver::class.java).apply {
            putExtra("PLANT_NAME", plantName)
            putExtra("REMINDER_TYPE", "watering")
        }

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            plantName.hashCode() + 1, // Unique ID for watering
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, wateringHour)
            set(Calendar.MINUTE, wateringMinute)
            set(Calendar.SECOND, 0)

            // If the time has already passed today, schedule for tomorrow
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val intervalDays = wateringIntervalSlider.value.toLong()
        val intervalMillis = intervalDays * 24 * 60 * 60 * 1000

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            intervalMillis,
            pendingIntent
        )
    }

    private fun scheduleFertilizingReminder(plantName: String) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, PlantReminderReceiver::class.java).apply {
            putExtra("PLANT_NAME", plantName)
            putExtra("REMINDER_TYPE", "fertilizing")
        }

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            plantName.hashCode() + 2, // Unique ID for fertilizing
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, fertilizingHour)
            set(Calendar.MINUTE, fertilizingMinute)
            set(Calendar.SECOND, 0)

            // If the time has already passed today, schedule for tomorrow
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val intervalWeeks = fertilizingIntervalSlider.value.toLong()
        val intervalMillis = intervalWeeks * 7 * 24 * 60 * 60 * 1000

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            intervalMillis,
            pendingIntent
        )
    }
}

// Broadcast receiver for plant reminders
class PlantReminderReceiver : android.content.BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val plantName = intent.getStringExtra("PLANT_NAME") ?: "Your plant"
        val reminderType = intent.getStringExtra("REMINDER_TYPE") ?: "care"

        // Create notification
        val notificationTitle = when (reminderType) {
            "watering" -> "Time to water $plantName"
            "fertilizing" -> "Time to fertilize $plantName"
            else -> "Plant care reminder for $plantName"
        }

        val notificationText = when (reminderType) {
            "watering" -> "Don't forget to water your $plantName!"
            "fertilizing" -> "Your $plantName needs fertilizing today."
            else -> "Time to take care of your $plantName!"
        }

        // Here you would create and show the notification
        // For now, we'll just log it
        android.util.Log.d("PlantReminder", "$notificationTitle: $notificationText")
    }
}
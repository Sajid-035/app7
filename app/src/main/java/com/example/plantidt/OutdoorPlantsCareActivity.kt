package com.example.plantidt

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class OutdoorPlantsCareActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var outdoorGardenImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outdoor_plants_care)

        // Set status bar color and make it light
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        backButton = findViewById(R.id.back_button)
        outdoorGardenImage = findViewById(R.id.outdoor_garden_image)
    }

    private fun setupClickListeners() {
        // Back button click listener
        backButton.setOnClickListener {
            finish()
        }

        // Optional: Add click listener for the garden image if you want to show it in full screen
        outdoorGardenImage.setOnClickListener {
            // TODO: Implement full screen image view if needed
            // For now, just a simple click feedback
        }
    }
}
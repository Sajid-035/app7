package com.example.plantidt

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class IndoorPlantsCareActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var plantImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indoor_plants_care)

        // Set status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        backButton = findViewById(R.id.back_button)
        plantImage = findViewById(R.id.plant_image)
    }

    private fun setupClickListeners() {
        // Back button
        backButton.setOnClickListener {
            finish()
        }
    }
}
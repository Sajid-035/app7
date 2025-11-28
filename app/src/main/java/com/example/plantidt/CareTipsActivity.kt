package com.example.plantidt

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

class CareTipsActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var indoorPlantsCard: CardView
    private lateinit var outdoorPlantsCard: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care_tips)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        backButton = findViewById(R.id.back_button)
        indoorPlantsCard = findViewById(R.id.indoor_plants_card)
        outdoorPlantsCard = findViewById(R.id.outdoor_plants_card)
    }

    private fun setupClickListeners() {
        // Back button
        backButton.setOnClickListener { finish() }

        // Indoor plants card - Navigate to IndoorPlantsCareActivity
        indoorPlantsCard.setOnClickListener {
            val intent = Intent(this, IndoorPlantsCareActivity::class.java)
            startActivity(intent)
        }

        // Outdoor plants card - Navigate to OutdoorPlantsCareActivity
        outdoorPlantsCard.setOnClickListener {
            val intent = Intent(this, OutdoorPlantsCareActivity::class.java)
            startActivity(intent)
        }
    }
}
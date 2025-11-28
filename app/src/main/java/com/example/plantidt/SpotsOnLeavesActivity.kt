package com.example.plantidt

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class SpotsOnLeavesActivity : AppCompatActivity() {

    // Spot type data class
    data class SpotType(
        val title: String,
        val description: String,
        val imageRes: Int
    )

    // Define different types of spots on leaves
    private val spotTypes = listOf(
        SpotType(
            "Water-Soaked Spots",
            "Translucent, wet-looking spots that appear soaked with water",
            R.drawable.water_soaked_spots
        ),
        SpotType(
            "Transparent or Clear Spots",
            "See-through or glass-like spots on leaf surface",
            R.drawable.transparent_spots
        ),
        SpotType(
            "Dark-Colored Spots",
            "Black, brown, or dark purple spots on leaves",
            R.drawable.dark_colored_spots
        ),
        SpotType(
            "Silver or Gray Spots",
            "Metallic silver or gray colored spots on leaf tissue",
            R.drawable.silver_gray_spots
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spots_on_leaves)

        setupBackButton()
        setupUI()
        setupSpotCards()
    }

    private fun setupBackButton() {
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }
    }

    private fun setupUI() {
        findViewById<TextView>(R.id.titleText).text = "Spots on Leaves"
        findViewById<TextView>(R.id.subtitleText).text = "Identify the type of spots affecting the leaves"
    }

    private fun setupSpotCards() {
        // Hide all cards first (assuming you have up to 9 cards in your layout)
        (1..9).forEach { hideCard(it) }

        // Configure visible cards for spot types
        spotTypes.forEachIndexed { index, spotType ->
            val cardNumber = index + 1
            if (cardNumber > 9) return@forEachIndexed

            val card = findViewById<CardView>(resources.getIdentifier("card$cardNumber", "id", packageName))
            val image = findViewById<ImageView>(resources.getIdentifier("image$cardNumber", "id", packageName))
            val title = findViewById<TextView>(resources.getIdentifier("title$cardNumber", "id", packageName))
            val desc = findViewById<TextView>(resources.getIdentifier("desc$cardNumber", "id", packageName))

            card.visibility = View.VISIBLE
            image.setImageResource(spotType.imageRes)
            title.text = spotType.title
            desc.text = spotType.description
            card.setOnClickListener { handleSpotClick(spotType.title) }
        }
    }

    private fun hideCard(number: Int) {
        findViewById<CardView>(resources.getIdentifier("card$number", "id", packageName)).visibility = View.GONE
    }

    private fun handleSpotClick(spotTitle: String) {
        // Handle spot type selection
        // You can either show detailed information or proceed to diagnosis
        Toast.makeText(this, "Selected: $spotTitle", Toast.LENGTH_SHORT).show()

        // Here you could start another activity for detailed diagnosis
        // or implement further symptom analysis
    }
}
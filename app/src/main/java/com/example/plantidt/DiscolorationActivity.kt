package com.example.plantidt

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class DiscolorationActivity : AppCompatActivity() {

    // A data class to hold info for each card
    data class DiscolorationType(
        val name: String,
        val imageRes: Int,
        val description: String,
        val activityClass: Class<*>? = null
    )

    private val discolorationTypes = listOf(
        DiscolorationType(
            "Yellow Leaves",
            R.drawable.yellow_leaves,
            "Yellowing and fading",
            YellowLeavesActivity::class.java
        ),
        DiscolorationType(
            "Brown Leaves",
            R.drawable.brown_leaves,
            "Browning and wilting",
            BrownLeavesActivity::class.java
        ),
        DiscolorationType(
            "Purple Leaves",
            R.drawable.purple_leaves,
            "Purple discoloration"
        ),
        DiscolorationType(
            "Black Leaves",
            R.drawable.black_leaves,
            "Blackening and decay"
        ),
        DiscolorationType(
            "White Leaves",
            R.drawable.white_leaves,
            "White spots or patches"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discoloration)

        setupBackButton()
        setupDiscolorationCards()
    }

    private fun setupBackButton() {
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupDiscolorationCards() {
        discolorationTypes.forEachIndexed { index, type ->
            val cardNumber = index + 1

            val card = findViewById<MaterialCardView>(resources.getIdentifier("card$cardNumber", "id", packageName))
            val image = findViewById<ImageView>(resources.getIdentifier("image$cardNumber", "id", packageName))

            // Set the image for the card
            image.setImageResource(type.imageRes)

            // Set click listener for the card
            card.setOnClickListener {
                // Add ripple effect
                card.isPressed = true
                card.postDelayed({ card.isPressed = false }, 150)

                // Show toast message
                Toast.makeText(this, "Selected: ${type.name}", Toast.LENGTH_SHORT).show()

                // Launch specific activity if available
                if (type.activityClass != null) {
                    val intent = Intent(this, type.activityClass)
                    startActivity(intent)
                    // Add transition animation
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } else {
                    // For other types, you can implement similar detail activities later
                    Toast.makeText(this, "Coming soon: ${type.name} details", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Optional: Add custom back animation
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
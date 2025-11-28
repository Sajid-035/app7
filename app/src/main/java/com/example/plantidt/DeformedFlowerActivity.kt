package com.example.plantidt

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class DeformedFlowerActivity : AppCompatActivity() {

    // Sub-symptom data class
    data class SubSymptom(
        val title: String,
        val description: String,
        val imageRes: Int
    )

    // Define sub-symptoms for deformed flowers
    private val deformedFlowerSubSymptoms = listOf(
        SubSymptom(
            "Distorted Flower",
            "Flowers with irregular or twisted shapes",
            R.drawable.distorted_flower
        ),
        SubSymptom(
            "Joint Flowers",
            "Multiple flowers fused or joined together",
            R.drawable.joint_flowers
        ),
        SubSymptom(
            "Small Flower",
            "Flowers smaller than normal size",
            R.drawable.small_flower
        ),
        SubSymptom(
            "Discolored Flowers",
            "Flowers with abnormal or faded colors",
            R.drawable.discolored_flowers
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deformed_flower)

        setupBackButton()
        setupUI()
        setupSubSymptomCards()
    }

    private fun setupBackButton() {
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }
    }

    private fun setupUI() {
        findViewById<TextView>(R.id.titleText).text = "Deformed Flower"
        findViewById<TextView>(R.id.subtitleText).text = "Identify specific deformation issues"
    }

    private fun setupSubSymptomCards() {
        // Hide all cards first
        (1..9).forEach { hideCard(it) }

        // Configure visible cards
        deformedFlowerSubSymptoms.forEachIndexed { index, subSymptom ->
            val cardNumber = index + 1
            if (cardNumber > 9) return@forEachIndexed

            val card = findViewById<CardView>(resources.getIdentifier("card$cardNumber", "id", packageName))
            val image = findViewById<ImageView>(resources.getIdentifier("image$cardNumber", "id", packageName))
            val title = findViewById<TextView>(resources.getIdentifier("title$cardNumber", "id", packageName))
            val desc = findViewById<TextView>(resources.getIdentifier("desc$cardNumber", "id", packageName))

            card.visibility = View.VISIBLE
            image.setImageResource(subSymptom.imageRes)
            title.text = subSymptom.title
            desc.text = subSymptom.description
            card.setOnClickListener { handleSubSymptomClick(subSymptom.title) }
        }
    }

    private fun hideCard(number: Int) {
        findViewById<CardView>(resources.getIdentifier("card$number", "id", packageName)).visibility = View.GONE
    }

    private fun handleSubSymptomClick(subSymptomTitle: String) {
        when (subSymptomTitle) {
            "Distorted Flower" -> {
                // Navigate to the detailed distorted flower diagnosis activity
                val intent = Intent(this, DistortedFlowerActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "Joint Flowers" -> {
                // Navigate to joint flowers activity (you can create this later)
                val intent = Intent(this, JointFlowersActivity::class.java)
                try {
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } catch (e: Exception) {
                    Toast.makeText(this, "Joint Flowers diagnosis coming soon!", Toast.LENGTH_SHORT).show()
                }
            }
            /*"Small Flower" -> {
                // Navigate to small flower activity (you can create this later)
                val intent = Intent(this, SmallFlowerActivity::class.java)
                try {
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } catch (e: Exception) {
                    Toast.makeText(this, "Small Flower diagnosis coming soon!", Toast.LENGTH_SHORT).show()
                }
            }
            "Discolored Flowers" -> {
                // Navigate to discolored flowers activity (you can create this later)
                val intent = Intent(this, DiscoloredFlowersActivity::class.java)
                try {
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } catch (e: Exception) {
                    Toast.makeText(this, "Discolored Flowers diagnosis coming soon!", Toast.LENGTH_SHORT).show()
                }
            }*/
            else -> {
                Toast.makeText(this, "Details for: $subSymptomTitle", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
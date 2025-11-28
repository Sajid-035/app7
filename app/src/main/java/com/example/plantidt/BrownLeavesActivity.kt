package com.example.plantidt

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView

class BrownLeavesActivity : AppCompatActivity() {

    // Data class to hold brown leaf type information
    data class BrownLeafType(
        val title: String,
        val description: String,
        val imageRes: Int,
        val symptoms: List<Symptom>
    )

    data class Symptom(
        val description: String,
        val cause: String,
        val solution: String
    )

    private val brownLeafTypes = listOf(
        BrownLeafType(
            "Crispy Brown Tips and Edges",
            "This is perhaps the most frequent browning issue, especially for indoor tropical plants.",
            R.drawable.crispy_brown_tips, // You'll need to add this image
            listOf(
                Symptom(
                    "Plants transpire (release water vapor) through their leaves. In dry air, they lose moisture faster than their roots can absorb it, causing the farthest points—the tips and edges—to dry out first.",
                    "Low Humidity",
                    "Increase Humidity: Group plants together, use a pebble tray with water, or run a humidifier nearby. Misting: Mist the leaves a few times a week, but be aware this is a temporary fix."
                ),
                Symptom(
                    "Tap water contains minerals, chlorine, and fluoride. Over time, these can build up in the soil and \"burn\" the sensitive leaf tips. This is often the culprit if you have consistent humidity.",
                    "Water Quality (Salt/Mineral Buildup)",
                    "Use Filtered Water: Switch to distilled, rain, or filtered water. Flush the Soil: Once every few months, water the plant thoroughly, letting a large amount of water run through the drainage holes to flush out excess mineral salts."
                ),
                Symptom(
                    "Too much fertilizer creates an excess of salts in the soil, which has the same effect as mineral buildup from tap water.",
                    "Fertilizer Burn",
                    "Flush the soil as described above. Fertilize less frequently or dilute your fertilizer to half-strength. Never fertilize a dry plant."
                )
            )
        ),
        BrownLeafType(
            "Brown or Black Spots on Leaves",
            "Spots that appear on the surface of the leaf (not just the tips) often indicate a more localized problem like disease or pests.",
            R.drawable.brown_black_spots, // You'll need to add this image
            listOf(
                Symptom(
                    "Spots are often circular, can have a yellow \"halo\" around them, and may have a darker spot in the center. This is common in conditions with poor air circulation and wet leaves.",
                    "Fungal Leaf Spot Disease",
                    "Prune & Destroy: Immediately remove and dispose of the affected leaves. Do not compost them. Improve Airflow: Create more space between plants. Water the Soil, Not the Leaves: Avoid overhead watering. Apply a Fungicide: If the problem is widespread, use a copper-based or sulfur-based fungicide according to package directions."
                ),
                Symptom(
                    "Pests like scale or others pierce the leaf to feed, creating small, dead brown spots where the tissue has died. Inspect the top and underside of leaves closely for any insects.",
                    "Pest Damage",
                    "Isolate the plant. Manually remove any visible pests with a cotton swab dipped in rubbing alcohol. Spray the entire plant with neem oil or insecticidal soap."
                )
            )
        ),
        BrownLeafType(
            "Large, Soft, Dark Brown or Black Patches",
            "When the brown areas are large, spread quickly, and feel soft or mushy, it is a serious sign of rot.",
            R.drawable.large_soft_brown_patches, // You'll need to add this image
            listOf(
                Symptom(
                    "This is the most common cause. The soil has been kept too wet, suffocating the roots. The dying, rotting roots can no longer support the leaves, and the rot can travel up the stem into the leaves. The soil may have a foul, swampy smell.",
                    "Overwatering and Root Rot",
                    "Act Immediately: This can kill a plant quickly. Remove the plant from its pot. Inspect and Prune Roots: Gently wash the soil from the roots. Cut away any that are brown, black, and mushy, leaving only the firm, white roots. Prune Affected Leaves: Cut off all leaves and stems showing signs of soft rot. Repot: Repot the plant in a clean pot with fresh, well-draining soil. Water Sparingly: Wait a week before watering lightly. Allow the soil to dry out significantly between future waterings."
                )
            )
        ),
        BrownLeafType(
            "Entire Leaves Turn Brown, Dry, and Wither",
            "This is different from just brown tips; the entire leaf dies and becomes brittle.",
            R.drawable.entire_leaves_brown, // You'll need to add this image
            listOf(
                Symptom(
                    "The plant has been left dry for too long and is now sacrificing entire leaves to conserve water for its core survival. The soil will be bone-dry and may have pulled away from the sides of the pot.",
                    "Severe Underwatering",
                    "Rehydrate Thoroughly: Give the plant a deep, slow watering. For extremely dry soil, try bottom-watering: place the pot in a sink or tub with a few inches of water for an hour to let it absorb moisture from the bottom up. Prune Dead Leaves: The brown, crispy leaves will not recover. Cut them off to improve the plant's appearance."
                ),
                Symptom(
                    "A plant left in intense, direct sun or too close to a heat source can have its leaves effectively \"cooked.\"",
                    "Heat Stress or Sunburn",
                    "Move the plant to a cooler location with more appropriate, indirect light. Prune the dead leaves."
                )
            )
        ),
        BrownLeafType(
            "Lower Leaves Turn Brown and Drop Off",
            "If the browning is contained to the very oldest leaves at the bottom of the plant, it's often not a cause for alarm.",
            R.drawable.lower_leaves_brown, // You'll need to add this image
            listOf(
                Symptom(
                    "It is a normal part of a plant's life cycle to shed its oldest leaves to redirect energy to new growth. This process often involves the leaf turning yellow, then brown and crispy, before dropping. The rest of the plant looks healthy.",
                    "Natural Aging (Senescence)",
                    "No Action Needed: This is not a sign of a problem. Tidy Up: You can simply wait for the leaf to fall off or prune it for a cleaner appearance."
                )
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brown_leaves)

        setupBackButton()
        setupBrownLeafTypeCards()
    }

    private fun setupBackButton() {
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupBrownLeafTypeCards() {
        val containerLayout = findViewById<LinearLayout>(R.id.brownLeafTypesContainer)

        brownLeafTypes.forEach { leafType ->
            val cardView = createBrownLeafCard(leafType)
            containerLayout.addView(cardView)
        }
    }

    private fun createBrownLeafCard(leafType: BrownLeafType): MaterialCardView {
        val cardView = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(32, 16, 32, 16)
            }
            cardElevation = 8f
            radius = 16f
            setCardBackgroundColor(ContextCompat.getColor(this@BrownLeavesActivity, android.R.color.white))
        }

        val contentLayout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
        }

        // Header with image and title
        val headerLayout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, 0, 0, 16)
        }

        val imageView = ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(80, 80).apply {
                setMargins(0, 0, 16, 0)
            }
            setImageResource(leafType.imageRes)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }

        val titleLayout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
            orientation = LinearLayout.VERTICAL
        }

        val titleTextView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = leafType.title
            textSize = 18f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@BrownLeavesActivity, android.R.color.black))
        }

        val descriptionTextView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = leafType.description
            textSize = 14f
            setTextColor(ContextCompat.getColor(this@BrownLeavesActivity, android.R.color.darker_gray))
            setPadding(0, 8, 0, 0)
        }

        titleLayout.addView(titleTextView)
        titleLayout.addView(descriptionTextView)
        headerLayout.addView(imageView)
        headerLayout.addView(titleLayout)

        // Symptoms container (initially hidden)
        val symptomsContainer = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
            visibility = View.GONE
        }

        // Add symptoms
        leafType.symptoms.forEachIndexed { index, symptom ->
            val symptomCard = createSymptomCard(symptom, index + 1)
            symptomsContainer.addView(symptomCard)
        }

        contentLayout.addView(headerLayout)
        contentLayout.addView(symptomsContainer)
        cardView.addView(contentLayout)

        // Click listener to expand/collapse
        cardView.setOnClickListener {
            if (symptomsContainer.visibility == View.GONE) {
                symptomsContainer.visibility = View.VISIBLE
                cardView.cardElevation = 12f
            } else {
                symptomsContainer.visibility = View.GONE
                cardView.cardElevation = 8f
            }
        }

        return cardView
    }

    private fun createSymptomCard(symptom: Symptom, index: Int): MaterialCardView {
        val symptomCard = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 8)
            }
            cardElevation = 4f
            radius = 12f
            setCardBackgroundColor(ContextCompat.getColor(this@BrownLeavesActivity, android.R.color.background_light))
        }

        val symptomLayout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
        }

        val symptomTitle = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = "Cause ${if (index > 1) ('A' + index - 1).toString() else ""}"
            textSize = 14f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@BrownLeavesActivity, R.color.brown_primary))
            setPadding(0, 0, 0, 8)
        }

        val causeTitle = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = "Likely Cause: ${symptom.cause}"
            textSize = 15f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@BrownLeavesActivity, R.color.warning_orange))
            setPadding(0, 0, 0, 8)
        }

        val diagnosisTitle = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = "Diagnosis:"
            textSize = 13f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@BrownLeavesActivity, android.R.color.black))
            setPadding(0, 0, 0, 4)
        }

        val diagnosisText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = symptom.description
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@BrownLeavesActivity, android.R.color.black))
            setPadding(0, 0, 0, 8)
        }

        val solutionTitle = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = "Solution:"
            textSize = 13f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@BrownLeavesActivity, R.color.solution_blue))
            setPadding(0, 0, 0, 4)
        }

        val solutionText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = symptom.solution
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@BrownLeavesActivity, android.R.color.black))
        }

        symptomLayout.addView(symptomTitle)
        symptomLayout.addView(causeTitle)
        symptomLayout.addView(diagnosisTitle)
        symptomLayout.addView(diagnosisText)
        symptomLayout.addView(solutionTitle)
        symptomLayout.addView(solutionText)

        symptomCard.addView(symptomLayout)
        return symptomCard
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
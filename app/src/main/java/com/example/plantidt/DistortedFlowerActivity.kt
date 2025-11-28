package com.example.plantidt

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView

class DistortedFlowerActivity : AppCompatActivity() {

    // Data class to hold distorted flower type information
    data class DistortedFlowerType(
        val title: String,
        val description: String,
        val imageRes: Int,
        val symptoms: List<Symptom>
    )

    data class Symptom(
        val title: String,
        val description: String,
        val cause: String,
        val solution: String,
        val diagnosis: String? = null
    )

    private val distortedFlowerTypes = listOf(
        DistortedFlowerType(
            "Sucking Pests (Aphids & Thrips)",
            "This is one of the most common causes. Tiny insects feed on the tender, developing flower buds, damaging the cells and causing the flower to grow in a deformed way.",
            R.drawable.aphids_thrips, // You'll need to add this image
            listOf(
                Symptom(
                    "Twisted, Stunted, and Pockmarked Flowers",
                    "The flower buds may be small, fail to open properly, or open into a gnarled, twisted shape. You may see tiny insects clustered on the buds or stems. Thrips damage often includes silvery streaks or brown, papery patches on the petals.",
                    "Aphids and Thrips - These pests cluster on new growth and buds, secreting honeydew or creating silvery streaks on petals.",
                    "Physical Removal: Blast the buds with a strong jet of water from a hose to knock the pests off.\n\nInsecticidal Soap or Neem Oil: Spray the entire plant, focusing on the buds and undersides of leaves. This is most effective when done in the evening. Repeat every 5-7 days until the pest problem is gone.\n\nBeneficial Insects: Introduce ladybugs or lacewings to your garden to provide natural pest control.",
                    "Aphids: Look for clusters of small, pear-shaped insects (often green, black, or pink) on new growth and buds. You may also see a sticky residue called \"honeydew.\"\n\nThrips: These are very tiny, slender insects. You can often spot them by tapping an open flower over a white piece of paper. The damage appears as silvery streaks or discolored blotches on the petals."
                )
            )
        ),
        DistortedFlowerType(
            "Fungal or Viral Disease",
            "Diseases can infect the plant tissue, interrupting normal development and leading to rot or strange patterns.",
            R.drawable.fungal_viral_disease, // You'll need to add this image
            listOf(
                Symptom(
                    "Rotting Buds or Flecked Petals (Fungal)",
                    "Buds turn brown and mushy, often covered in a fuzzy grey mold, and fail to open. Opened flowers may develop brown spots or flecks on the petals, especially after rain.",
                    "Botrytis Blight (Grey Mold) - A fungal disease that thrives in humid conditions.",
                    "Sanitation is Key: Immediately prune and destroy any affected buds, flowers, and leaves. Do not compost them.\n\nImprove Air Circulation: Prune some of the plant's inner branches to allow more air to flow through.\n\nWater Carefully: Water at the base of the plant in the morning to avoid wetting the foliage and buds.\n\nFungicide: In severe or recurring cases, apply a fungicide specifically for Botrytis."
                ),
                Symptom(
                    "Strange Color Breaking or Streaks (Viral)",
                    "The flower has irregular, splotchy, or streaky color patterns that are not typical for the variety. This is famous in roses and tulips. The plant itself may look stunted.",
                    "Plant Viruses (e.g., Rose Mosaic Virus) - Viral infections that cannot be cured.",
                    "No Cure: Unfortunately, there is no cure for plant viruses.\n\nRemove and Destroy: To prevent the virus from potentially spreading to other plants (often by pests or contaminated tools), the best course of action is to remove and dispose of the entire plant.\n\nPrevention: Buy certified virus-free plants from reputable nurseries."
                )
            )
        ),
        DistortedFlowerType(
            "Nutrient Deficiency",
            "A lack of specific micronutrients, especially during bud formation, can lead to poor development.",
            R.drawable.nutrient_deficiency, // You'll need to add this image
            listOf(
                Symptom(
                    "Stunted Buds That Drop or Deformed Flowers",
                    "Flower buds are small, may turn yellow and fall off before opening (\"bud blast\"), or open into small, poorly formed flowers with weak color. This is often accompanied by other signs of poor plant health, like pale leaves.",
                    "Boron deficiency is a primary cause of poor flower and fruit development. Calcium deficiency can also play a role.",
                    "Use a Bloom-Booster Fertilizer: Apply a balanced fertilizer that is lower in nitrogen (N) and higher in phosphorus (P) and potassium (K), as these support flowering.\n\nCheck Micronutrients: If the problem persists, consider using a liquid seaweed or kelp fertilizer, which is rich in micronutrients like boron.\n\nGet a Soil Test: For persistent garden-wide issues, a soil test can tell you exactly which nutrients are missing."
                )
            )
        ),
        DistortedFlowerType(
            "Environmental or Chemical Stress",
            "Sudden environmental changes or exposure to chemicals during bud development can cause physical damage.",
            R.drawable.environmental_stress, // You'll need to add this image
            listOf(
                Symptom(
                    "Fused, Crinkled, or One-Sided Damage",
                    "Flowers may look like they've been physically damaged—crinkled, seared on one side, or have petals that are fused together. The damage is often erratic and doesn't follow a clear biological pattern.",
                    "Herbicide Drift: Wind can carry weed-killer spray from a lawn or neighboring property, causing severe distortion to broadleaf plants.\n\nSudden Temperature Swings: A late frost or a sudden, intense heatwave can damage delicate, developing bud tissues.\n\nIrregular Watering: Severe drought or waterlogging during bud formation can stress the plant.",
                    "Identify the Stressor: If it was a one-time weather event, future blooms should be fine. If herbicide drift is suspected, identify the source and try to prevent future exposure.\n\nPrune and Wait: Remove the damaged blooms. Provide the plant with consistent water and care, and it will likely recover and produce normal flowers later in the season.\n\nProtect Your Plants: Use frost cloths to protect sensitive plants from late cold snaps. Provide mulch to help regulate soil moisture and temperature."
                )
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distorted_flower)

        setupBackButton()
        setupDistortedFlowerTypeCards()
    }

    private fun setupBackButton() {
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupDistortedFlowerTypeCards() {
        val containerLayout = findViewById<LinearLayout>(R.id.distortedFlowerTypesContainer)

        distortedFlowerTypes.forEach { flowerType ->
            val cardView = createDistortedFlowerCard(flowerType)
            containerLayout.addView(cardView)
        }
    }

    private fun createDistortedFlowerCard(flowerType: DistortedFlowerType): MaterialCardView {
        val cardView = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(32, 16, 32, 16)
            }
            cardElevation = 8f
            radius = 16f
            setCardBackgroundColor(ContextCompat.getColor(this@DistortedFlowerActivity, android.R.color.white))
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
            setImageResource(flowerType.imageRes)
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
            text = flowerType.title
            textSize = 18f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@DistortedFlowerActivity, android.R.color.black))
        }

        val descriptionTextView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = flowerType.description
            textSize = 14f
            setTextColor(ContextCompat.getColor(this@DistortedFlowerActivity, android.R.color.darker_gray))
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
        flowerType.symptoms.forEachIndexed { index, symptom ->
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
            setCardBackgroundColor(ContextCompat.getColor(this@DistortedFlowerActivity, android.R.color.background_light))
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
            text = symptom.title
            textSize = 14f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@DistortedFlowerActivity, R.color.plant_green))
            setPadding(0, 0, 0, 8)
        }

        val symptomDescription = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = symptom.description
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@DistortedFlowerActivity, android.R.color.black))
            setPadding(0, 0, 0, 8)
        }

        // Add diagnosis section if available
        if (symptom.diagnosis != null) {
            val diagnosisTitle = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = "Diagnosis:"
                textSize = 13f
                setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(ContextCompat.getColor(this@DistortedFlowerActivity, R.color.plant_green))
                setPadding(0, 0, 0, 4)
            }

            val diagnosisText = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = symptom.diagnosis
                textSize = 13f
                setTextColor(ContextCompat.getColor(this@DistortedFlowerActivity, android.R.color.black))
                setPadding(0, 0, 0, 8)
            }

            symptomLayout.addView(symptomTitle)
            symptomLayout.addView(symptomDescription)
            symptomLayout.addView(diagnosisTitle)
            symptomLayout.addView(diagnosisText)
        } else {
            symptomLayout.addView(symptomTitle)
            symptomLayout.addView(symptomDescription)
        }

        val causeTitle = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = "Likely Cause:"
            textSize = 13f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@DistortedFlowerActivity, R.color.warning_orange))
            setPadding(0, 0, 0, 4)
        }

        val causeText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = symptom.cause
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@DistortedFlowerActivity, android.R.color.black))
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
            setTextColor(ContextCompat.getColor(this@DistortedFlowerActivity, R.color.solution_blue))
            setPadding(0, 0, 0, 4)
        }

        val solutionText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = symptom.solution
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@DistortedFlowerActivity, android.R.color.black))
        }

        symptomLayout.addView(causeTitle)
        symptomLayout.addView(causeText)
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
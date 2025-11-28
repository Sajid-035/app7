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

class PotassiumDeficiencyActivity : AppCompatActivity() {

    // Data class to hold potassium deficiency information
    data class DeficiencySection(
        val title: String,
        val description: String,
        val imageRes: Int,
        val details: List<DeficiencyDetail>
    )

    data class DeficiencyDetail(
        val title: String,
        val description: String,
        val items: List<String>? = null,
        val category: String = "info" // "cause", "symptom", "solution"
    )

    private val potassiumDeficiencySections = listOf(
        DeficiencySection(
            "Causes of Deficiency",
            "Understanding what leads to potassium deficiency in plants",
            R.drawable.ic_causes, // You'll need to add this image
            listOf(
                DeficiencyDetail(
                    "Insufficient Potassium in Soil",
                    "The most direct cause is that the soil simply lacks enough potassium to meet the plant's needs.",
                    null,
                    "cause"
                ),
                DeficiencyDetail(
                    "Soil Leaching",
                    "Potassium is water-soluble. In sandy or coarse soils, heavy rain or overwatering can wash the potassium out of the root zone before the plant can absorb it.",
                    null,
                    "cause"
                ),
                DeficiencyDetail(
                    "Incorrect Soil pH",
                    "If the soil is too acidic, it can \"lock up\" potassium, making it unavailable for the plant to absorb, even if it's present in the soil.",
                    null,
                    "cause"
                ),
                DeficiencyDetail(
                    "Nutrient Imbalance",
                    "An excess of other nutrients like calcium (Ca) or magnesium (Mg) can interfere with the plant's ability to uptake potassium.",
                    null,
                    "cause"
                )
            )
        ),
        DeficiencySection(
            "Symptoms & How to Identify It",
            "The most classic sign of potassium deficiency is yellowing along the leaf margins",
            R.drawable.ic_symptoms, // You'll need to add this image
            listOf(
                DeficiencyDetail(
                    "Key Symptom",
                    "You will see a distinct yellowing (chlorosis) that starts at the tip of the leaf and progresses down the outer edges (margins). The center of the leaf and the veins often remain green.",
                    null,
                    "symptom"
                ),
                DeficiencyDetail(
                    "Location",
                    "As a mobile nutrient, these symptoms will appear on the older, lower leaves first.",
                    null,
                    "symptom"
                ),
                DeficiencyDetail(
                    "Progression",
                    "As the deficiency worsens, the yellow edges will turn brown, dry, and crispy, looking scorched or burnt.",
                    null,
                    "symptom"
                ),
                DeficiencyDetail(
                    "Other Signs",
                    "Stems may be weak, and the plant will have poor flowering and fruiting. It will also be more susceptible to diseases.",
                    null,
                    "symptom"
                )
            )
        ),
        DeficiencySection(
            "Solutions",
            "Both short-term fixes and long-term soil health improvements",
            R.drawable.ic_solutions, // You'll need to add this image
            listOf(
                DeficiencyDetail(
                    "Short-Term Fix (Fast-Acting)",
                    "The fastest way to correct the issue:",
                    listOf(
                        "Liquid Potash/Potassium Fertilizer: Apply a water-soluble fertilizer rich in potassium. Look for products labeled \"liquid potash\" or \"bloom booster\" fertilizers, which typically have a high last number in their N-P-K ratio (e.g., 5-10-10).",
                        "Liquid Seaweed or Kelp Meal: These are excellent organic options that are rich in potassium and can be quickly absorbed by the plant when applied as a soil drench."
                    ),
                    "solution"
                ),
                DeficiencyDetail(
                    "Long-Term Fix (Soil Health)",
                    "Sustainable solutions for soil improvement:",
                    listOf(
                        "Amend the Soil: Add potassium-rich materials to your soil",
                        "• Greensand: A slow-release source of potassium",
                        "• Composted Manure: Especially from poultry",
                        "• Wood Ash: Use this very sparingly and only on acidic soils, as it will raise the soil pH significantly",
                        "• Compost: Especially compost made with lots of banana peels, fruit waste, and vegetable scraps",
                        "Get a Soil Test: This is the best long-term solution. A soil test will confirm the deficiency and, just as importantly, tell you your soil's pH so you can correct it if needed."
                    ),
                    "solution"
                )
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_potassium_deficiency)

        setupHeader()
        setupBackButton()
        setupDeficiencySections()
    }

    private fun setupHeader() {
        val headerTitle = findViewById<TextView>(R.id.headerTitle)
        val headerDescription = findViewById<TextView>(R.id.headerDescription)

        headerTitle.text = "Potassium (K) Deficiency"
        headerDescription.text = "Potassium is the great regulator. It's vital for overall plant vigor, water movement, disease resistance, and producing strong stems and quality fruit/flowers."
    }

    private fun setupBackButton() {
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupDeficiencySections() {
        val containerLayout = findViewById<LinearLayout>(R.id.deficiencySectionsContainer)

        potassiumDeficiencySections.forEach { section ->
            val cardView = createDeficiencyCard(section)
            containerLayout.addView(cardView)
        }
    }

    private fun createDeficiencyCard(section: DeficiencySection): MaterialCardView {
        val cardView = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(32, 16, 32, 16)
            }
            cardElevation = 8f
            radius = 16f
            setCardBackgroundColor(ContextCompat.getColor(this@PotassiumDeficiencyActivity, android.R.color.white))
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
            setImageResource(section.imageRes)
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
            text = section.title
            textSize = 18f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@PotassiumDeficiencyActivity, android.R.color.black))
        }

        val descriptionTextView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = section.description
            textSize = 14f
            setTextColor(ContextCompat.getColor(this@PotassiumDeficiencyActivity, android.R.color.darker_gray))
            setPadding(0, 8, 0, 0)
        }

        titleLayout.addView(titleTextView)
        titleLayout.addView(descriptionTextView)
        headerLayout.addView(imageView)
        headerLayout.addView(titleLayout)

        // Details container (initially hidden)
        val detailsContainer = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
            visibility = View.GONE
        }

        // Add details
        section.details.forEachIndexed { index, detail ->
            val detailCard = createDetailCard(detail, index + 1)
            detailsContainer.addView(detailCard)
        }

        contentLayout.addView(headerLayout)
        contentLayout.addView(detailsContainer)
        cardView.addView(contentLayout)

        // Click listener to expand/collapse
        cardView.setOnClickListener {
            if (detailsContainer.visibility == View.GONE) {
                detailsContainer.visibility = View.VISIBLE
                cardView.cardElevation = 12f
            } else {
                detailsContainer.visibility = View.GONE
                cardView.cardElevation = 8f
            }
        }

        return cardView
    }

    private fun createDetailCard(detail: DeficiencyDetail, index: Int): MaterialCardView {
        val detailCard = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 8)
            }
            cardElevation = 4f
            radius = 12f
            setCardBackgroundColor(ContextCompat.getColor(this@PotassiumDeficiencyActivity, android.R.color.background_light))
        }

        val detailLayout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
        }

        val detailTitle = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = detail.title
            textSize = 14f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(getColorForCategory(detail.category))
            setPadding(0, 0, 0, 8)
        }

        val detailDescription = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = detail.description
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@PotassiumDeficiencyActivity, android.R.color.black))
            setPadding(0, 0, 0, 8)
        }

        detailLayout.addView(detailTitle)
        detailLayout.addView(detailDescription)

        // Add items if they exist
        detail.items?.forEach { item ->
            val itemTextView = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = item
                textSize = 13f
                setTextColor(ContextCompat.getColor(this@PotassiumDeficiencyActivity, android.R.color.black))
                setPadding(16, 4, 0, 4)
            }
            detailLayout.addView(itemTextView)
        }

        detailCard.addView(detailLayout)
        return detailCard
    }

    private fun getColorForCategory(category: String): Int {
        return when (category) {
            "cause" -> ContextCompat.getColor(this, R.color.warning_orange)
            "symptom" -> ContextCompat.getColor(this, R.color.plant_green)
            "solution" -> ContextCompat.getColor(this, R.color.solution_blue)
            else -> ContextCompat.getColor(this, android.R.color.black)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
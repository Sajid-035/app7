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

class NitrogenDeficiencyActivity : AppCompatActivity() {

    // Data class to hold nitrogen deficiency information
    data class DeficiencyCategory(
        val title: String,
        val description: String,
        val imageRes: Int,
        val details: List<DeficiencyDetail>
    )

    data class DeficiencyDetail(
        val title: String,
        val description: String,
        val additionalInfo: String? = null
    )

    private val nitrogenDeficiencyCategories = listOf(
        DeficiencyCategory(
            "Causes of Nitrogen Deficiency",
            "Understanding why nitrogen deficiency occurs in your plants and garden soil.",
            R.drawable.nitrogen_causes, // You'll need to add this image
            listOf(
                DeficiencyDetail(
                    "Insufficient Nitrogen in Soil",
                    "The most common reason. Nitrogen is used in large quantities by plants and gets depleted quickly.",
                    "This is especially common in heavily planted gardens or when growing nitrogen-hungry crops like leafy greens, corn, or brassicas."
                ),
                DeficiencyDetail(
                    "High Carbon Materials",
                    "Adding materials with a high carbon-to-nitrogen ratio to your soil (like fresh sawdust, wood chips, or un-composted leaves) can temporarily cause a nitrogen deficiency.",
                    "The microbes that break down the carbon use up all the available nitrogen in the process, 'robbing' it from your plants. This is why it's important to compost high-carbon materials before adding them to soil."
                ),
                DeficiencyDetail(
                    "Soil Leaching",
                    "Like potassium, nitrogen is easily washed out of the soil by excessive rain or watering, especially in sandy soils.",
                    "Sandy soils are particularly prone to nitrogen leaching because they drain quickly and don't retain nutrients well."
                ),
                DeficiencyDetail(
                    "Waterlogged Soil",
                    "In overly wet, compacted soil, the bacteria that make nitrogen available to plants cannot function properly.",
                    "These beneficial bacteria need oxygen to convert nitrogen into forms that plants can use. Waterlogged conditions create an anaerobic environment that kills these bacteria."
                )
            )
        ),
        DeficiencyCategory(
            "Symptoms & Identification",
            "How to recognize nitrogen deficiency in your plants - the key visual indicators.",
            R.drawable.nitrogen_symptoms, // You'll need to add this image
            listOf(
                DeficiencyDetail(
                    "Key Symptom: Uniform Yellowing",
                    "A uniform pale green or yellowing (chlorosis) of the entire leaf, starting with the older, lower leaves.",
                    "Unlike potassium deficiency, the yellowing is not confined to the edges. The entire leaf surface becomes pale and yellow, creating a distinctive appearance."
                ),
                DeficiencyDetail(
                    "Location: Lower, Older Leaves First",
                    "Because nitrogen is a mobile nutrient, the plant moves nitrogen from the older, lower leaves to the new growth.",
                    "These lower leaves will be the first to turn yellow. The plant essentially 'sacrifices' old growth to support new growth, which is why you see this bottom-up progression."
                ),
                DeficiencyDetail(
                    "Progression: Upward Movement",
                    "The yellowing will gradually move up the plant. The entire plant will appear stunted, spindly, and weak.",
                    "New leaves will be small and pale. If left untreated, the plant will become increasingly weak and may stop growing altogether."
                ),
                DeficiencyDetail(
                    "Overall Plant Appearance",
                    "Nitrogen deficiency results in a general, uniform paleness across the affected plant.",
                    "The plant will have a 'washed out' appearance, with reduced vigor and slower growth rates. Stems may also appear thinner and weaker than normal."
                )
            )
        ),
        DeficiencyCategory(
            "Short-Term Solutions",
            "Fast-acting treatments to quickly address nitrogen deficiency in your plants.",
            R.drawable.nitrogen_quick_fix, // You'll need to add this image
            listOf(
                DeficiencyDetail(
                    "Liquid Fertilizer",
                    "Apply a balanced liquid fertilizer with a higher first number (Nitrogen), such as a 10-5-5.",
                    "Liquid fertilizers are absorbed quickly through both roots and leaves. Apply according to package directions, usually every 2-4 weeks during growing season."
                ),
                DeficiencyDetail(
                    "Fish Emulsion",
                    "A fantastic, fast-acting organic option. It's mixed with water and applied as a soil drench.",
                    "Fish emulsion is gentle on plants and provides not only nitrogen but also trace minerals. It typically shows results within 1-2 weeks of application."
                ),
                DeficiencyDetail(
                    "Blood Meal",
                    "This is a dry organic amendment, but it releases its nitrogen relatively quickly when watered into the soil.",
                    "Blood meal is very high in nitrogen (usually 12-15%) and works faster than most organic amendments. Apply carefully as it can burn plants if over-applied."
                )
            )
        ),
        DeficiencyCategory(
            "Long-Term Soil Health",
            "Sustainable solutions to build nitrogen levels and improve overall soil health.",
            R.drawable.nitrogen_long_term, // You'll need to add this image
            listOf(
                DeficiencyDetail(
                    "Amend the Soil",
                    "Regularly add nitrogen-rich organic matter to your soil. Excellent sources include well-rotted composted manure, alfalfa meal, cottonseed meal, and finished compost.",
                    "Well-rotted composted manure provides balanced nutrition. Alfalfa meal and cottonseed meal offer slow-release nitrogen. Finished compost is the single best all-around soil amendment."
                ),
                DeficiencyDetail(
                    "Use Cover Crops",
                    "In a garden setting, planting nitrogen-fixing cover crops like clover, vetch, or beans in the off-season will naturally add nitrogen back into the soil.",
                    "These plants have a symbiotic relationship with bacteria that convert atmospheric nitrogen into a form plants can use. When the cover crops decompose, they release this nitrogen into the soil."
                ),
                DeficiencyDetail(
                    "Mulch Correctly",
                    "Apply a layer of finished compost or well-rotted manure as a mulch, which will slowly feed the soil as it breaks down.",
                    "Avoid mixing high-carbon materials directly into your soil. Instead, compost them first or use them as a top mulch where they won't compete with plants for nitrogen."
                ),
                DeficiencyDetail(
                    "Soil Testing & Monitoring",
                    "Regular soil testing helps you understand your soil's nitrogen levels and pH, which affects nutrient availability.",
                    "Test your soil every 2-3 years, or annually if you're building new garden beds. Maintain proper soil pH (6.0-7.0 for most plants) to ensure nitrogen availability."
                )
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nitrogen_deficiency)

        setupBackButton()
        setupDeficiencyCards()
        setupHeaderInfo()
    }

    private fun setupBackButton() {
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupHeaderInfo() {
        val headerTitle = findViewById<TextView>(R.id.headerTitle)
        val headerDescription = findViewById<TextView>(R.id.headerDescription)

        headerTitle.text = "Nitrogen (N) Deficiency"
        headerDescription.text = "Nitrogen is the \"growth\" nutrient. It's the primary component of chlorophyll (what makes plants green) and is essential for producing lush, green leaves and strong stems."
    }

    private fun setupDeficiencyCards() {
        val containerLayout = findViewById<LinearLayout>(R.id.deficiencyContainer)

        nitrogenDeficiencyCategories.forEach { category ->
            val cardView = createDeficiencyCard(category)
            containerLayout.addView(cardView)
        }
    }

    private fun createDeficiencyCard(category: DeficiencyCategory): MaterialCardView {
        val cardView = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(32, 16, 32, 16)
            }
            cardElevation = 8f
            radius = 16f
            setCardBackgroundColor(ContextCompat.getColor(this@NitrogenDeficiencyActivity, android.R.color.white))
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
            setImageResource(category.imageRes)
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
            text = category.title
            textSize = 18f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@NitrogenDeficiencyActivity, android.R.color.black))
        }

        val descriptionTextView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = category.description
            textSize = 14f
            setTextColor(ContextCompat.getColor(this@NitrogenDeficiencyActivity, android.R.color.darker_gray))
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
        category.details.forEach { detail ->
            val detailCard = createDetailCard(detail)
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

    private fun createDetailCard(detail: DeficiencyDetail): MaterialCardView {
        val detailCard = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 8)
            }
            cardElevation = 4f
            radius = 12f
            setCardBackgroundColor(ContextCompat.getColor(this@NitrogenDeficiencyActivity, android.R.color.background_light))
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
            setTextColor(ContextCompat.getColor(this@NitrogenDeficiencyActivity, R.color.plant_green))
            setPadding(0, 0, 0, 8)
        }

        val detailDescription = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = detail.description
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@NitrogenDeficiencyActivity, android.R.color.black))
            setPadding(0, 0, 0, 8)
        }

        detailLayout.addView(detailTitle)
        detailLayout.addView(detailDescription)

        // Add additional info if available
        if (detail.additionalInfo != null) {
            val additionalInfoTitle = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = "Additional Information:"
                textSize = 13f
                setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(ContextCompat.getColor(this@NitrogenDeficiencyActivity, R.color.solution_blue))
                setPadding(0, 0, 0, 4)
            }

            val additionalInfoText = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = detail.additionalInfo
                textSize = 13f
                setTextColor(ContextCompat.getColor(this@NitrogenDeficiencyActivity, android.R.color.black))
                setTypeface(null, android.graphics.Typeface.ITALIC)
            }

            detailLayout.addView(additionalInfoTitle)
            detailLayout.addView(additionalInfoText)
        }

        detailCard.addView(detailLayout)
        return detailCard
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
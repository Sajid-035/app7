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

class ThripsActivity : AppCompatActivity() {

    // Data class to hold thrips information
    data class ThripsSection(
        val title: String,
        val description: String,
        val imageRes: Int,
        val details: List<ThripsDetail>
    )

    data class ThripsDetail(
        val title: String,
        val description: String,
        val steps: List<String>? = null,
        val symptoms: List<String>? = null,
        val causes: List<String>? = null,
        val confirmationMethod: String? = null
    )

    private val thripsSections = listOf(
        ThripsSection(
            "What Are Thrips?",
            "Tiny, slender insects that are very common and difficult to eliminate due to their rapid life cycle.",
            R.drawable.thrips_identification, // You'll need to add this image
            listOf(
                ThripsDetail(
                    "Physical Description",
                    "Thrips are tiny insects, about 1-2 mm long, that attack leaves, flowers, and new growth of plants.",
                    symptoms = listOf(
                        "Adult thrips are typically black or yellowish-brown",
                        "Have feathery wings, allowing them to fly from plant to plant",
                        "Larvae are smaller, pale white or yellow, and crawl on the plant's surface",
                        "They feed by scraping the surface of leaves and sucking out the contents"
                    )
                )
            )
        ),
        ThripsSection(
            "Causes & How They Spread",
            "Understanding how thrips arrive and multiply helps in prevention and control strategies.",
            R.drawable.thrips_spread, // You'll need to add this image
            listOf(
                ThripsDetail(
                    "Common Entry Points",
                    "Thrips can enter your space through various methods and reproduce rapidly once established.",
                    causes = listOf(
                        "Flying In: They can easily fly in through open windows or doors",
                        "Hitchhiking: They arrive on new plants, cut flowers from the garden, or even on your clothing",
                        "Rapid Reproduction: A single thrips can lay hundreds of eggs, leading to a fast-moving infestation"
                    )
                )
            )
        ),
        ThripsSection(
            "Symptoms & Identification",
            "Thrips damage is very characteristic and distinctive once you know what to look for.",
            R.drawable.thrips_damage, // You'll need to add this image
            listOf(
                ThripsDetail(
                    "Visual Symptoms",
                    "The primary signs of thrips damage are easily recognizable with careful observation.",
                    symptoms = listOf(
                        "Silvery or Stippled Leaves: The primary sign is patches on the leaves that look silvery, bleached, or scraped",
                        "Tiny Black Specks: You will see tiny black dots on the damaged areas. This is the thrips' excrement (frass) and is a key identifier",
                        "Deformed Growth: New leaves and flower buds may emerge twisted, deformed, or fail to open"
                    ),
                    confirmationMethod = "Look closely at the damaged areas, especially on the underside of leaves. You will see the tiny, moving larvae or adult thrips. Shaking a leaf over a white piece of paper will often dislodge them, making them easier to see."
                )
            )
        ),
        ThripsSection(
            "Solutions (Step-by-Step)",
            "Beating thrips requires persistence and interrupting their life cycle through multiple approaches.",
            R.drawable.thrips_treatment, // You'll need to add this image
            listOf(
                ThripsDetail(
                    "Step 1: Isolate and Clean",
                    "Immediate action to prevent spread and reduce population.",
                    steps = listOf(
                        "Isolate the infested plant immediately",
                        "Give the plant a strong shower with a hose or in your bathtub, physically blasting as many thrips off the leaves as possible"
                    )
                ),
                ThripsDetail(
                    "Step 2: Use Sticky Traps",
                    "Monitoring and catching flying adults.",
                    steps = listOf(
                        "Thrips are attracted to certain colors",
                        "Place blue or yellow sticky traps around your plants",
                        "This will catch the flying adults and help you monitor the severity of the infestation"
                    )
                ),
                ThripsDetail(
                    "Step 3: Treat with Sprays (Be Persistent!)",
                    "Regular treatment is essential since thrips lay eggs inside plant tissue where sprays can't reach.",
                    steps = listOf(
                        "Spray every 5-7 days for at least 3-4 weeks",
                        "Organic Option: Use commercial insecticidal soap or Neem Oil spray",
                        "Thoroughly coat every part of the plant, especially undersides of leaves and new growth",
                        "Most Effective Organic-Based Option: Look for sprays containing Spinosad - a natural substance highly effective against thrips"
                    )
                ),
                ThripsDetail(
                    "Step 4: Treat the Soil",
                    "Interrupting the soil-based life cycle stage.",
                    steps = listOf(
                        "Part of the thrips' life cycle occurs in the soil (the pupal stage)",
                        "Sprinkle a thin layer of diatomaceous earth on the surface of the soil to interrupt this stage"
                    )
                ),
                ThripsDetail(
                    "Step 5: Prevention",
                    "Long-term strategies to prevent future infestations.",
                    steps = listOf(
                        "Thoroughly inspect and quarantine any new plants for at least two weeks before introducing them to your collection",
                        "For outdoor gardens or greenhouses, release beneficial insects like predatory mites (Amblyseius cucumeris) or green lacewing larvae for excellent natural control"
                    )
                )
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thrips)

        setupBackButton()
        setupThripsCards()
    }

    private fun setupBackButton() {
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupThripsCards() {
        val containerLayout = findViewById<LinearLayout>(R.id.thripsContainer)

        thripsSections.forEach { section ->
            val cardView = createThripsCard(section)
            containerLayout.addView(cardView)
        }
    }

    private fun createThripsCard(section: ThripsSection): MaterialCardView {
        val cardView = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(32, 16, 32, 16)
            }
            cardElevation = 8f
            radius = 16f
            setCardBackgroundColor(ContextCompat.getColor(this@ThripsActivity, android.R.color.white))
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
            setTextColor(ContextCompat.getColor(this@ThripsActivity, android.R.color.black))
        }

        val descriptionTextView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = section.description
            textSize = 14f
            setTextColor(ContextCompat.getColor(this@ThripsActivity, android.R.color.darker_gray))
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
        section.details.forEach { detail ->
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

    private fun createDetailCard(detail: ThripsDetail): MaterialCardView {
        val detailCard = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 8)
            }
            cardElevation = 4f
            radius = 12f
            setCardBackgroundColor(ContextCompat.getColor(this@ThripsActivity, android.R.color.background_light))
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
            setTextColor(ContextCompat.getColor(this@ThripsActivity, R.color.plant_green))
            setPadding(0, 0, 0, 8)
        }

        val detailDescription = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = detail.description
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@ThripsActivity, android.R.color.black))
            setPadding(0, 0, 0, 8)
        }

        detailLayout.addView(detailTitle)
        detailLayout.addView(detailDescription)

        // Add symptoms if available
        detail.symptoms?.let { symptoms ->
            val symptomsTitle = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = "Key Points:"
                textSize = 13f
                setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(ContextCompat.getColor(this@ThripsActivity, R.color.warning_orange))
                setPadding(0, 0, 0, 4)
            }
            detailLayout.addView(symptomsTitle)

            symptoms.forEach { symptom ->
                val bulletPoint = TextView(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    text = "• $symptom"
                    textSize = 13f
                    setTextColor(ContextCompat.getColor(this@ThripsActivity, android.R.color.black))
                    setPadding(16, 2, 0, 2)
                }
                detailLayout.addView(bulletPoint)
            }
        }

        // Add causes if available
        detail.causes?.let { causes ->
            val causesTitle = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = "How They Spread:"
                textSize = 13f
                setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(ContextCompat.getColor(this@ThripsActivity, R.color.warning_orange))
                setPadding(0, 8, 0, 4)
            }
            detailLayout.addView(causesTitle)

            causes.forEach { cause ->
                val bulletPoint = TextView(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    text = "• $cause"
                    textSize = 13f
                    setTextColor(ContextCompat.getColor(this@ThripsActivity, android.R.color.black))
                    setPadding(16, 2, 0, 2)
                }
                detailLayout.addView(bulletPoint)
            }
        }

        // Add steps if available
        detail.steps?.let { steps ->
            val stepsTitle = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = "Action Steps:"
                textSize = 13f
                setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(ContextCompat.getColor(this@ThripsActivity, R.color.solution_blue))
                setPadding(0, 8, 0, 4)
            }
            detailLayout.addView(stepsTitle)

            steps.forEach { step ->
                val bulletPoint = TextView(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    text = "• $step"
                    textSize = 13f
                    setTextColor(ContextCompat.getColor(this@ThripsActivity, android.R.color.black))
                    setPadding(16, 2, 0, 2)
                }
                detailLayout.addView(bulletPoint)
            }
        }

        // Add confirmation method if available
        detail.confirmationMethod?.let { confirmation ->
            val confirmationTitle = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = "How to Confirm:"
                textSize = 13f
                setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(ContextCompat.getColor(this@ThripsActivity, R.color.plant_green))
                setPadding(0, 8, 0, 4)
            }

            val confirmationText = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = confirmation
                textSize = 13f
                setTextColor(ContextCompat.getColor(this@ThripsActivity, android.R.color.black))
                setPadding(0, 0, 0, 8)
            }

            detailLayout.addView(confirmationTitle)
            detailLayout.addView(confirmationText)
        }

        detailCard.addView(detailLayout)
        return detailCard
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
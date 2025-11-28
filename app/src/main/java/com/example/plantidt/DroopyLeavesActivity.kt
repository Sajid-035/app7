package com.example.plantidt

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView

class DroopyLeavesActivity : AppCompatActivity() {

    // Data class to hold droopy leaf type information
    data class DroopyLeafType(
        val title: String,
        val subtitle: String,
        val description: String,
        val imageRes: Int,
        val symptoms: List<DroopySymptom>
    )

    data class DroopySymptom(
        val diagnosis: String,
        val cause: String,
        val solutions: List<String>,
        val specialNote: String = ""
    )

    private val droopyLeafTypes = listOf(
        DroopyLeafType(
            "The Thirsty Droop",
            "Soft and Limp",
            "The most common drooping issue - your plant is simply asking for water.",
            R.drawable.thirsty_droop,
            listOf(
                DroopySymptom(
                    "The leaves feel soft and limp, not crispy. The entire plant is wilting, not just a few leaves. When you lift the pot, it feels light. The soil is dry to the touch, even an inch or two down.",
                    "Underwatering",
                    listOf(
                        "Water Thoroughly: Give the plant a deep drink immediately. Water until it runs freely from the drainage holes.",
                        "Try Bottom-Watering: For very dry soil, place the pot in a sink or basin with a few inches of water for 30-60 minutes.",
                        "Check More Often: Adjust your watering schedule. Don't let the soil get to this point again."
                    )
                )
            )
        ),
        DroopyLeafType(
            "The Suffocating Droop",
            "Limp and Yellowing",
            "Counterintuitively, overwatering can cause drooping because the roots can't function properly.",
            R.drawable.suffocating_droop,
            listOf(
                DroopySymptom(
                    "The leaves are droopy, but they may also look yellow, especially the lower ones. The soil is damp, wet, or even waterlogged. The pot feels heavy. There might be a faint, swampy smell from the soil.",
                    "Overwatering and Root Suffocation",
                    listOf(
                        "STOP WATERING: Do not give it another drop.",
                        "Ensure Drainage: Tip out any standing water from the saucer. Make sure the pot's drainage holes are not blocked.",
                        "Aerate the Soil: Gently poke some holes into the soil with a chopstick to help introduce air to the roots.",
                        "Wait: Allow the top 50% or more of the soil to dry out completely before watering again.",
                        "If Severe: If the soil stays wet for days, you may need to repot the plant into fresh, dry, well-draining soil."
                    )
                )
            )
        ),
        DroopyLeafType(
            "The Midday Faint",
            "Temporary Droop",
            "Your plant is having a temporary reaction to intense heat or light conditions.",
            R.drawable.midday_faint,
            listOf(
                DroopySymptom(
                    "The plant looks wilted and sad during the hottest, sunniest part of the day but perks back up in the evening or overnight. The soil is adequately moist.",
                    "Heat and Light Stress",
                    listOf(
                        "Move the Plant: Relocate it to a spot with less intense, direct afternoon sun.",
                        "Provide Shade: Use a sheer curtain to filter the intense sunlight during peak hours.",
                        "Find Better Location: A spot with bright, indirect light or morning sun is often ideal."
                    )
                )
            )
        ),
        DroopyLeafType(
            "The Crowded Droop",
            "Quick to Wilt",
            "When your plant has outgrown its home and needs more space for its roots.",
            R.drawable.crowded_droop,
            listOf(
                DroopySymptom(
                    "The plant droops just a day or two after watering. When you water, the water seems to run straight through the pot and out the bottom. You may see roots growing out of the drainage holes or coiling on the surface of the soil.",
                    "Root Bound Condition",
                    listOf(
                        "Repot the Plant: Move the plant to a pot that is 1-2 inches larger in diameter.",
                        "Loosen the Roots: Gently tease and loosen the coiled roots with your fingers before placing it in the new pot.",
                        "Use Fresh Soil: Use fresh potting mix to encourage the roots to grow outward into the new soil."
                    )
                )
            )
        ),
        DroopyLeafType(
            "The Adjustment Droop",
            "Post-Move Sadness",
            "Plants can be sensitive to changes in their environment and need time to adapt.",
            R.drawable.adjustment_droop,
            listOf(
                DroopySymptom(
                    "The plant was perfectly healthy, but it started drooping within a day or two of being repotted or moved to a new location.",
                    "Transplant Shock or Environmental Stress",
                    listOf(
                        "Be Patient: Give the plant time to adjust. It can take a week or two.",
                        "Provide Consistent Care: Keep the soil evenly moist but not soggy.",
                        "Minimize Stress: Place it in a good location and then leave it be.",
                        "Avoid Fertilizing: Do not fertilize a stressed plant. Wait until you see signs of new, healthy growth."
                    )
                )
            )
        ),
        DroopyLeafType(
            "The 'Sleeping' Droop",
            "Normal Behavior",
            "Some plants naturally move their leaves in response to day and night cycles.",
            R.drawable.sleeping_droop,
            listOf(
                DroopySymptom(
                    "The leaves droop or fold down every evening and then rise back up the next morning. This is predictable and rhythmic.",
                    "Natural Daily Cycle (Nyctinasty)",
                    listOf(
                        "No Action Needed! This is a sign of a healthy, responsive plant.",
                        "Enjoy the show - this is completely normal behavior."
                    ),
                    "This isn't a problem at all, but a fascinating plant behavior!"
                )
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_droopy_leaves)

        setupBackButton()
        setupDroopyLeafTypeCards()
    }

    private fun setupBackButton() {
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupDroopyLeafTypeCards() {
        val containerLayout = findViewById<LinearLayout>(R.id.droopyLeafTypesContainer)

        droopyLeafTypes.forEach { leafType ->
            val cardView = createDroopyLeafCard(leafType)
            containerLayout.addView(cardView)
        }
    }

    private fun createDroopyLeafCard(leafType: DroopyLeafType): MaterialCardView {
        val cardView = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(32, 16, 32, 16)
            }
            cardElevation = 8f
            radius = 16f
            setCardBackgroundColor(ContextCompat.getColor(this@DroopyLeavesActivity, android.R.color.white))
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
            setTextColor(ContextCompat.getColor(this@DroopyLeavesActivity, android.R.color.black))
        }

        val subtitleTextView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = leafType.subtitle
            textSize = 14f
            setTypeface(null, android.graphics.Typeface.ITALIC)
            setTextColor(ContextCompat.getColor(this@DroopyLeavesActivity, R.color.brown_primary))
            setPadding(0, 4, 0, 0)
        }

        val descriptionTextView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = leafType.description
            textSize = 14f
            setTextColor(ContextCompat.getColor(this@DroopyLeavesActivity, android.R.color.darker_gray))
            setPadding(0, 8, 0, 0)
        }

        titleLayout.addView(titleTextView)
        titleLayout.addView(subtitleTextView)
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
            val symptomCard = createDroopySymptomCard(symptom, index + 1)
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

    private fun createDroopySymptomCard(symptom: DroopySymptom, index: Int): MaterialCardView {
        val symptomCard = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 8)
            }
            cardElevation = 4f
            radius = 12f
            setCardBackgroundColor(ContextCompat.getColor(this@DroopyLeavesActivity, android.R.color.background_light))
        }

        val symptomLayout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
        }

        // Special note if exists
        if (symptom.specialNote.isNotEmpty()) {
            val specialNoteView = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = "⚠️ ${symptom.specialNote}"
                textSize = 14f
                setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(ContextCompat.getColor(this@DroopyLeavesActivity, R.color.solution_blue))
                setPadding(0, 0, 0, 12)
            }
            symptomLayout.addView(specialNoteView)
        }

        val causeTitle = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = "Likely Cause: ${symptom.cause}"
            textSize = 15f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@DroopyLeavesActivity, R.color.warning_orange))
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
            setTextColor(ContextCompat.getColor(this@DroopyLeavesActivity, android.R.color.black))
            setPadding(0, 0, 0, 4)
        }

        val diagnosisText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = symptom.diagnosis
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@DroopyLeavesActivity, android.R.color.black))
            setPadding(0, 0, 0, 8)
        }

        val solutionTitle = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = "Solutions:"
            textSize = 13f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@DroopyLeavesActivity, R.color.solution_blue))
            setPadding(0, 0, 0, 4)
        }

        // Add each solution as a separate text view
        symptom.solutions.forEachIndexed { solutionIndex, solution ->
            val solutionText = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = "• $solution"
                textSize = 13f
                setTextColor(ContextCompat.getColor(this@DroopyLeavesActivity, android.R.color.black))
                setPadding(0, 0, 0, 4)
            }
            symptomLayout.addView(solutionText)
        }

        symptomLayout.addView(causeTitle)
        symptomLayout.addView(diagnosisTitle)
        symptomLayout.addView(diagnosisText)
        symptomLayout.addView(solutionTitle)

        symptomCard.addView(symptomLayout)
        return symptomCard
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
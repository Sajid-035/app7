package com.example.plantidt

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView

class YellowLeavesActivity : AppCompatActivity() {

    // Data class to hold yellow leaf type information
    data class YellowLeafType(
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

    private val yellowLeafTypes = listOf(
        YellowLeafType(
            "Lower Leaves Turn Yellow and Drop",
            "This is one of the most common symptoms. The key is to check how the leaves feel and what the soil is like.",
            R.drawable.lower_leaves_yellow, // You'll need to add this image
            listOf(
                Symptom(
                    "The yellow leaves are soft, limp, and the soil is consistently damp.",
                    "Overwatering. The roots are waterlogged and can't absorb oxygen or nutrients, leading to root rot.",
                    "Stop watering immediately. Ensure the pot has drainage holes. Allow the top 50% of the soil to dry out completely before watering again."
                ),
                Symptom(
                    "The yellow leaves are uniform in color (not spotty) and the rest of the plant looks pale and has slow growth.",
                    "Nitrogen Deficiency. Nitrogen is a mobile nutrient, so the plant takes it from the old leaves to give to the new ones.",
                    "Use a balanced, all-purpose liquid fertilizer that contains nitrogen. Follow the package instructions."
                ),
                Symptom(
                    "Only one or two of the oldest leaves at the very bottom turn yellow and fall off, while the plant is otherwise healthy and growing.",
                    "Natural Aging (Senescence). This is normal. Plants shed old leaves to conserve energy for new growth.",
                    "No action is needed. You can simply remove the dead leaf."
                )
            )
        ),
        YellowLeafType(
            "New (Top) Leaves Turn Yellow",
            "When new growth is affected first, it often points to a specific nutrient issue or environmental stress.",
            R.drawable.new_leaves_yellow, // You'll need to add this image
            listOf(
                Symptom(
                    "The new leaves are yellow, but the veins remain dark green.",
                    "Iron Deficiency. Iron is immobile, so new leaves can't get it from old ones. This is often caused by a soil pH that is too high (alkaline).",
                    "Lower the soil pH using a fertilizer for acid-loving plants or apply an iron supplement like chelated iron."
                ),
                Symptom(
                    "The entire plant, especially the new growth, is pale green-yellow and looks weak and stretched out.",
                    "Not Enough Light. The plant cannot produce enough chlorophyll, which gives leaves their green color.",
                    "Move the plant to a location with more appropriate light. If natural light is insufficient, consider a grow light."
                )
            )
        ),
        YellowLeafType(
            "Yellowing Between the Veins",
            "This distinct pattern is called interveinal chlorosis and is a classic sign of a nutrient deficiency.",
            R.drawable.yellowing_between_veins, // You'll need to add this image
            listOf(
                Symptom(
                    "Yellowing between veins on new, upper leaves.",
                    "Iron Deficiency (as mentioned above).",
                    "Use an iron supplement or an acidic fertilizer."
                ),
                Symptom(
                    "Yellowing between veins on older, lower leaves, sometimes in a V-shape or marbled pattern.",
                    "Magnesium Deficiency.",
                    "Water the plant once with a solution of one tablespoon of Epsom salts (magnesium sulfate) dissolved in one gallon of water."
                )
            )
        ),
        YellowLeafType(
            "Yellow Spots or Patches",
            "Irregular spots or patches usually point to a localized problem like pests, disease, or sun damage.",
            R.drawable.yellow_spots_patches, // You'll need to add this image
            listOf(
                Symptom(
                    "Small yellow or white speckles (stippling) on the leaves. You may see fine webbing on the undersides.",
                    "Pest Infestation, especially spider mites. Aphids and other pests also cause spotting.",
                    "Isolate the plant. Wipe down the leaves and spray thoroughly with neem oil or insecticidal soap, paying close attention to the undersides."
                ),
                Symptom(
                    "Yellow spots that turn brown or black, sometimes with a \"halo\" effect.",
                    "Fungal or Bacterial Leaf Spot Disease. This thrives in damp conditions with poor airflow.",
                    "Prune and destroy affected leaves (do not compost). Improve air circulation and avoid wetting the foliage when you water. A fungicide may be necessary."
                ),
                Symptom(
                    "Large, bleached, or crispy yellow/white patches on the parts of the leaves most exposed to the window.",
                    "Sunburn. The intense, direct sun has scorched the leaves.",
                    "Move the plant to a spot with less intense, filtered sunlight. The burned patches will not recover, but the plant will produce new, healthy leaves."
                )
            )
        ),
        YellowLeafType(
            "Yellowing Leaf Tips and Edges",
            "When the yellowing is concentrated on the margins, the cause is often related to water quality, humidity, or fertilizer.",
            R.drawable.yellowing_tips_edges, // You'll need to add this image
            listOf(
                Symptom(
                    "Leaf tips and edges turn yellow, then brown and crispy.",
                    "A combination of underwatering, low humidity, or a buildup of salts in the soil from tap water or too much fertilizer.",
                    "Water more consistently and thoroughly. Increase humidity with a humidifier or pebble tray. Once every few months, \"flush\" the soil by pouring a large amount of distilled or filtered water through the pot to wash out excess salts. Reduce fertilizer use."
                )
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yellow_leaves)

        setupBackButton()
        setupYellowLeafTypeCards()
    }

    private fun setupBackButton() {
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupYellowLeafTypeCards() {
        val containerLayout = findViewById<LinearLayout>(R.id.yellowLeafTypesContainer)

        yellowLeafTypes.forEach { leafType ->
            val cardView = createYellowLeafCard(leafType)
            containerLayout.addView(cardView)
        }
    }

    private fun createYellowLeafCard(leafType: YellowLeafType): MaterialCardView {
        val cardView = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(32, 16, 32, 16)
            }
            cardElevation = 8f
            radius = 16f
            setCardBackgroundColor(ContextCompat.getColor(this@YellowLeavesActivity, android.R.color.white))
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
            setTextColor(ContextCompat.getColor(this@YellowLeavesActivity, android.R.color.black))
        }

        val descriptionTextView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = leafType.description
            textSize = 14f
            setTextColor(ContextCompat.getColor(this@YellowLeavesActivity, android.R.color.darker_gray))
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
            setCardBackgroundColor(ContextCompat.getColor(this@YellowLeavesActivity, android.R.color.background_light))
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
            text = "Symptom ${if (index > 1) ('A' + index - 1).toString() else ""}"
            textSize = 14f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@YellowLeavesActivity, R.color.plant_green))
            setPadding(0, 0, 0, 8)
        }

        val symptomDescription = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = symptom.description
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@YellowLeavesActivity, android.R.color.black))
            setPadding(0, 0, 0, 8)
        }

        val causeTitle = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = "Likely Cause:"
            textSize = 13f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@YellowLeavesActivity, R.color.warning_orange))
            setPadding(0, 0, 0, 4)
        }

        val causeText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = symptom.cause
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@YellowLeavesActivity, android.R.color.black))
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
            setTextColor(ContextCompat.getColor(this@YellowLeavesActivity, R.color.solution_blue))
            setPadding(0, 0, 0, 4)
        }

        val solutionText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = symptom.solution
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@YellowLeavesActivity, android.R.color.black))
        }

        symptomLayout.addView(symptomTitle)
        symptomLayout.addView(symptomDescription)
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
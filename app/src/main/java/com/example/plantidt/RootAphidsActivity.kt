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

class RootAphidsActivity : AppCompatActivity() {

    // Data class to hold root aphid information
    data class RootAphidType(
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

    private val rootAphidTypes = listOf(
        RootAphidType(
            "What Are Root Aphids?",
            "Root aphids are tiny, pear-shaped insects that feed on the sap from plant roots. They look like small, waxy, white, grey, or bluish specks, often resembling mineral deposits or perlite in the soil.",
            R.drawable.root_aphids_overview, // You'll need to add this image
            listOf(
                Symptom(
                    "General Plant Decline with No Obvious Cause",
                    "Root aphids are often a 'mystery pest' because they live below the soil, making them hard to spot. They cause a general decline in plant health for no obvious reason. The plant looks generally unhealthy, weak, or 'sad' despite proper care.",
                    "Tiny, pear-shaped insects that feed on the sap from plant roots. They move slowly and can build up in large numbers before being noticed. They look like small, waxy, white, grey, or bluish specks.",
                    "Immediate Inspection Required: If your plant shows these symptoms, you need to inspect the root system immediately. Gently slide the plant out of its pot and examine the root ball and soil for tiny, waxy insects clustered on the roots.\n\nLook for Secondary Signs: Check for white, powdery, or fuzzy-looking substance on roots, which is the waxy coating and honeydew secreted by the aphids.",
                    "The above-ground symptoms are often generic, making diagnosis tricky. You must physically inspect the roots to confirm the presence of these pests."
                )
            )
        ),
        RootAphidType(
            "Contaminated Soil or Plants",
            "The most common cause is bringing in a new plant or using potting soil that is already infested with root aphids.",
            R.drawable.contaminated_soil, // You'll need to add this image
            listOf(
                Symptom(
                    "New Plant Brings Infestation",
                    "A recently acquired plant begins to decline shortly after bringing it home, and the problem spreads to nearby plants. The new plant may have looked healthy initially but carried hidden root aphids.",
                    "Contaminated soil or plants from nurseries, garden centers, or other sources. The aphids may be present in small numbers initially and not cause immediate visible symptoms.",
                    "Quarantine New Plants: Always isolate new plants for 2-3 weeks before introducing them to your collection. Monitor them carefully for signs of decline.\n\nInspect Before Purchase: When buying plants, ask to see the root system if possible, or gently lift the plant to check the drainage holes for signs of pests.\n\nUse Quality Potting Mix: Only use fresh, sterile, high-quality potting mix from reputable sources. Avoid bargain soil that may be contaminated.\n\nTreat Preventively: Consider treating new plants with beneficial nematodes as a preventative measure.",
                    "Look for a pattern of plant decline that started after introducing new plants or repotting with new soil. Multiple plants may be affected if they share the same contaminated source."
                )
            )
        ),
        RootAphidType(
            "Ant Farming",
            "Ants 'farm' root aphids, protecting them from predators and moving them from plant to plant in exchange for the sweet 'honeydew' the aphids excrete.",
            R.drawable.ant_farming, // You'll need to add this image
            listOf(
                Symptom(
                    "Ants Around Plant Base with Plant Decline",
                    "You notice ants crawling around the base of your potted plants, going in and out of drainage holes or along the soil surface. This is combined with unexplained plant decline and yellowing leaves.",
                    "Ants protect root aphids from predators and move them from plant to plant in exchange for honeydew. If you see ants around your plants, it's a major red flag for root aphids.",
                    "Eliminate Ant Access: Use ant baits or barriers to prevent ants from reaching your plants. Place sticky traps around pot bases or use diatomaceous earth as a barrier.\n\nTreat Both Problems: You must address both the ants and the root aphids simultaneously. Removing only one will allow the other to re-establish.\n\nInspect All Plants: If ants are farming aphids, they may have spread them to multiple plants. Check all plants in the area.\n\nElevate Pots: Keep pots off the ground and away from ant trails. Use plant saucers that can be kept clean and ant-free.",
                    "The presence of ants around plant bases is often the first and most obvious sign of root aphid infestation. This is a major diagnostic clue that should trigger immediate root inspection."
                )
            )
        ),
        RootAphidType(
            "Outdoor Migration",
            "In gardens, root aphids can move from plant to plant through the soil, spreading the infestation across multiple plants.",
            R.drawable.outdoor_migration, // You'll need to add this image
            listOf(
                Symptom(
                    "Spreading Pattern of Plant Decline",
                    "Multiple plants in a garden area begin showing decline symptoms in a spreading pattern. Plants that were healthy become affected over time, particularly those in close proximity to initially affected plants.",
                    "Root aphids can move through soil from plant to plant, especially in garden beds where roots systems may interconnect or where soil provides a pathway for migration.",
                    "Create Barriers: Use physical barriers like buried edging to prevent soil-borne spread between plant areas.\n\nTreat Affected Area: Treat not just the obviously affected plants, but also nearby plants that may be in early stages of infestation.\n\nSoil Treatment: Apply beneficial nematodes to the entire affected soil area to hunt down migrating aphids.\n\nImprove Drainage: Ensure good drainage throughout the garden, as root aphids prefer moist conditions.\n\nMonitor Regularly: Check plants in expanding circles around known infestations to catch spread early.",
                    "Look for a pattern of plant decline that spreads outward from an initial point. Garden plants are more susceptible to this type of spread than isolated container plants."
                )
            )
        ),
        RootAphidType(
            "Symptoms & Identification",
            "Root aphids cause generic above-ground symptoms that can be confusing. Physical inspection of the root system is essential for proper diagnosis.",
            R.drawable.symptoms_identification, // You'll need to add this image
            listOf(
                Symptom(
                    "Stunted Growth and Yellowing Leaves",
                    "Plant shows stunted or slow growth, yellowing and wilting leaves even when properly watered. The plant looks generally unhealthy, weak, or 'sad' despite receiving proper care.",
                    "Root aphids feed on plant sap directly from the root system, weakening the plant's ability to uptake nutrients and water efficiently, leading to general decline symptoms.",
                    "Step 1: Gently slide the plant out of its pot.\n\nStep 2: Inspect the root ball and soil carefully. Look for tiny, waxy insects clustered on the roots.\n\nStep 3: Check for white, powdery, or fuzzy-looking substance on roots (waxy coating and honeydew).\n\nStep 4: If confirmed, immediately isolate the plant and begin treatment protocol.\n\nStep 5: Inspect all nearby plants for similar symptoms.",
                    "The above-ground symptoms are often generic and can mimic other problems like overwatering, nutrient deficiency, or root rot. Physical inspection of the root system is the only reliable way to confirm root aphid presence."
                )
            )
        ),
        RootAphidType(
            "Complete Treatment Protocol",
            "Treating root aphids requires a direct, systematic attack on the root system and complete habitat elimination.",
            R.drawable.treatment_protocol, // You'll need to add this image
            listOf(
                Symptom(
                    "Step-by-Step Root Aphid Elimination",
                    "A comprehensive treatment protocol that addresses the root system directly and prevents re-infestation. This is the most effective method for completely eliminating root aphids.",
                    "Root aphids live in the soil and on roots, so surface treatments are ineffective. Complete soil replacement and root cleaning is necessary for successful treatment.",
                    "Step 1 - Isolate: Immediately move infested plant away from all other plants to prevent spread.\n\nStep 2 - Root Cleaning: Remove plant from pot, discard ALL old soil in sealed bag (do not compost). Rinse entire root system under lukewarm running water to remove all soil and visible aphids.\n\nStep 3 - Disinfect: Scrub original pot with hot, soapy water to kill lingering aphids or eggs.\n\nStep 4 - Repot: Use fresh, sterile, high-quality potting mix only.\n\nStep 5 - Soil Drench: Apply neem oil solution (1-2 tsp pure neem oil + few drops dish soap in 1 quart water) to saturate entire root ball.\n\nStep 6 - Prevention: Introduce predatory nematodes for long-term control of soil-dwelling pests.",
                    "This treatment protocol must be followed completely - skipping steps or trying shortcuts often results in re-infestation. The entire process may need to be repeated if aphids return."
                )
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root_aphids)

        setupBackButton()
        setupRootAphidTypeCards()
    }

    private fun setupBackButton() {
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRootAphidTypeCards() {
        val containerLayout = findViewById<LinearLayout>(R.id.rootAphidTypesContainer)

        rootAphidTypes.forEach { aphidType ->
            val cardView = createRootAphidCard(aphidType)
            containerLayout.addView(cardView)
        }
    }

    private fun createRootAphidCard(aphidType: RootAphidType): MaterialCardView {
        val cardView = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(32, 16, 32, 16)
            }
            cardElevation = 8f
            radius = 16f
            setCardBackgroundColor(ContextCompat.getColor(this@RootAphidsActivity, android.R.color.white))
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
            setImageResource(aphidType.imageRes)
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
            text = aphidType.title
            textSize = 18f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(this@RootAphidsActivity, android.R.color.black))
        }

        val descriptionTextView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = aphidType.description
            textSize = 14f
            setTextColor(ContextCompat.getColor(this@RootAphidsActivity, android.R.color.darker_gray))
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
        aphidType.symptoms.forEachIndexed { index, symptom ->
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
            setCardBackgroundColor(ContextCompat.getColor(this@RootAphidsActivity, android.R.color.background_light))
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
            setTextColor(ContextCompat.getColor(this@RootAphidsActivity, R.color.plant_green))
            setPadding(0, 0, 0, 8)
        }

        val symptomDescription = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = symptom.description
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@RootAphidsActivity, android.R.color.black))
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
                setTextColor(ContextCompat.getColor(this@RootAphidsActivity, R.color.plant_green))
                setPadding(0, 0, 0, 4)
            }

            val diagnosisText = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = symptom.diagnosis
                textSize = 13f
                setTextColor(ContextCompat.getColor(this@RootAphidsActivity, android.R.color.black))
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
            setTextColor(ContextCompat.getColor(this@RootAphidsActivity, R.color.warning_orange))
            setPadding(0, 0, 0, 4)
        }

        val causeText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = symptom.cause
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@RootAphidsActivity, android.R.color.black))
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
            setTextColor(ContextCompat.getColor(this@RootAphidsActivity, R.color.solution_blue))
            setPadding(0, 0, 0, 4)
        }

        val solutionText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = symptom.solution
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@RootAphidsActivity, android.R.color.black))
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
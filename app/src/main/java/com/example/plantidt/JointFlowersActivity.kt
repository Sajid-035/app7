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

class JointFlowersActivity : AppCompatActivity() {

    // Data class to hold joint flower (fasciation) information
    data class JointFlowerType(
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

    private val jointFlowerTypes = listOf(
        JointFlowerType(
            "Random Genetic Mutation",
            "The most common cause of fasciation - a spontaneous genetic hiccup in the plant's code as cells divide at the growing tip.",
            R.drawable.genetic_mutation, // You'll need to add this image
            listOf(
                Symptom(
                    "Abnormally Wide, Flat Stems and Fused Flowers",
                    "Flowers or flower heads look like two or more have been fused together. The stem leading up to the flower is abnormally flat and wide. Creates a unique, ribbon-like or crested appearance that's often spectacular-looking.",
                    "Spontaneous, random hiccup in the plant's genetic code as the cells at the growing tip divide. It's not necessarily a sign of a sick plant or a problem you caused.",
                    "Enjoy the Uniqueness (Do Nothing): This is the best solution for most gardeners. A fasciated flower is a natural curiosity and can be a beautiful, strange, and interesting feature in your garden. It's a great conversation starter! No action needed - simply let the flower grow and appreciate its unique form. The rest of the plant will likely produce normal flowers.\n\nPrune It Off: If you find the appearance undesirable, or if the growth is so heavy that it's weighing down the plant, you can simply remove it using clean pruning shears. Cut the fasciated stem or flower off at its base or where it joins a main stem.",
                    "Look for flowers that appear to be multiple blooms fused into one continuous, crested bloom. The stem will be noticeably flattened and wider than normal. This is typically a one-time occurrence on an otherwise healthy plant."
                )
            )
        ),
        JointFlowerType(
            "Hormonal Imbalance",
            "A disruption in the plant's natural growth hormones (auxins) at the growing tip causes cells to multiply in a disorganized, flattened way.",
            R.drawable.hormonal_imbalance, // You'll need to add this image
            listOf(
                Symptom(
                    "Disorganized Cell Growth Pattern",
                    "Instead of neat, circular growth patterns, cells multiply in an disorganized, flattened way. The growing tip becomes wide and ribbon-like rather than maintaining its normal round point.",
                    "Disruption in the plant's natural growth hormones (auxins) at the growing tip, causing abnormal cell division patterns.",
                    "Monitor Plant Health: Ensure the plant is getting proper nutrition and isn't under stress from drought, over-watering, or other environmental factors.\n\nBalance Fertilization: Avoid over-fertilizing, especially with nitrogen, which can disrupt hormone balance. Use a balanced fertilizer according to package directions.\n\nReduce Stress Factors: Provide consistent watering, proper spacing, and adequate light to minimize hormonal disruptions.",
                    "Observe the pattern of growth - hormonal imbalances often affect multiple growing points and may recur. The plant may show other signs of stress like unusual leaf growth or delayed flowering."
                )
            )
        ),
        JointFlowerType(
            "Physical Injury or Damage",
            "Damage to a young, developing bud or growing tip can trigger fasciation through various forms of physical trauma.",
            R.drawable.physical_damage, // You'll need to add this image
            listOf(
                Symptom(
                    "Localized Fasciation After Damage",
                    "Fasciation appears specifically at sites where damage occurred to young, developing buds. The affected area shows the characteristic flattened, fused appearance while the rest of the plant remains normal.",
                    "Pest Damage: Mites or insects feeding on a tender bud.\nMechanical Damage: Accidentally pinching or crushing a growing tip with tools or during handling.\nEnvironmental Damage: A late frost or sudden, extreme temperature change that damages the developing cells.",
                    "Control Pests: Regularly inspect young plants for pests like aphids and mites and treat them early using insecticidal soap or neem oil.\n\nBe Careful with Handling: Handle young plants and developing buds gently to avoid physical damage. Use proper tools and techniques when pruning or staking.\n\nProtect from Frost: Use frost cloth to protect tender plants from late cold snaps. Monitor weather forecasts during vulnerable periods.\n\nPrevent Future Damage: Identify what caused the initial damage and take steps to prevent recurrence.",
                    "Look for evidence of the original damage - pest activity, tool marks, or frost damage. The fasciation will typically be localized to the damaged area, with normal growth elsewhere on the plant."
                )
            )
        ),
        JointFlowerType(
            "Bacterial or Viral Infection",
            "Certain pathogens can cause fasciation, with Rhodococcus fascians being a well-known bacterial culprit.",
            R.drawable.bacterial_infection, // You'll need to add this image
            listOf(
                Symptom(
                    "Fasciation with Witch's Broom Effect",
                    "Fasciated growth accompanied by dense clusters of shoots (witch's broom effect). Multiple growing points may be affected, and the plant may show other signs of infection like stunted growth or unusual leaf patterns.",
                    "The bacterium Rhodococcus fascians is a well-known culprit that can cause this type of distorted growth, sometimes along with a witch's broom effect (a dense cluster of shoots). Other bacterial or viral pathogens can also trigger fasciation.",
                    "Remove and Destroy Affected Parts: Using clean, sterilized pruning shears, remove all affected growth. Do not compost this material - dispose of it in the trash.\n\nSterilize Tools: Clean pruning tools with rubbing alcohol between cuts to prevent spreading the pathogen.\n\nImprove Air Circulation: Prune surrounding vegetation to improve air flow around the plant.\n\nAvoid Overhead Watering: Water at the base of the plant to keep foliage dry and reduce pathogen spread.\n\nMonitor for Recurrence: If fasciation returns or spreads to other plants, consider removing the entire plant to prevent further spread.",
                    "Look for multiple symptoms: fasciation combined with dense shoot clusters, stunted growth, or other abnormal growth patterns. The problem may affect multiple plants in the area if it's infectious."
                )
            )
        ),
        JointFlowerType(
            "Chemical Exposure",
            "Drift from herbicides or exposure to other chemicals can cause mutated growth patterns in sensitive plants.",
            R.drawable.chemical_exposure, // You'll need to add this image
            listOf(
                Symptom(
                    "Irregular Fasciation with Other Deformities",
                    "Fasciation appears alongside other plant deformities like twisted leaves, stunted growth, or unusual coloration. The damage pattern may be irregular and not follow normal biological patterns.",
                    "Drift from herbicides or exposure to other chemicals during sensitive growth periods. Even small amounts of certain chemicals can cause dramatic growth distortions in developing plant tissues.",
                    "Identify the Source: Try to determine what chemicals the plant may have been exposed to - herbicide drift from lawn treatments, pesticide overspray, or other chemical sources.\n\nFlush the Soil: If recent exposure is suspected, thoroughly water the area to help dilute and flush away any remaining chemicals.\n\nAvoid Herbicides: Be extremely careful with weed killers near your garden beds, as even a small amount of drift can cause damage.\n\nCreate Barriers: Use physical barriers or plant buffer zones to protect sensitive plants from chemical drift.\n\nRemove Affected Growth: Prune away damaged portions to allow the plant to focus energy on new, healthy growth.",
                    "Chemical damage often affects multiple plants in an area and may show a pattern based on wind direction or proximity to the chemical source. The damage may be more severe on one side of the plant or in a specific area of the garden."
                )
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joint_flowers)

        setupBackButton()
        setupJointFlowerTypeCards()
    }

    private fun setupBackButton() {
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupJointFlowerTypeCards() {
        val containerLayout = findViewById<LinearLayout>(R.id.jointFlowerTypesContainer)

        jointFlowerTypes.forEach { flowerType ->
            val cardView = createJointFlowerCard(flowerType)
            containerLayout.addView(cardView)
        }
    }

    private fun createJointFlowerCard(flowerType: JointFlowerType): MaterialCardView {
        val cardView = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(32, 16, 32, 16)
            }
            cardElevation = 8f
            radius = 16f
            setCardBackgroundColor(ContextCompat.getColor(this@JointFlowersActivity, android.R.color.white))
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
            setTextColor(ContextCompat.getColor(this@JointFlowersActivity, android.R.color.black))
        }

        val descriptionTextView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = flowerType.description
            textSize = 14f
            setTextColor(ContextCompat.getColor(this@JointFlowersActivity, android.R.color.darker_gray))
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
            setCardBackgroundColor(ContextCompat.getColor(this@JointFlowersActivity, android.R.color.background_light))
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
            setTextColor(ContextCompat.getColor(this@JointFlowersActivity, R.color.plant_green))
            setPadding(0, 0, 0, 8)
        }

        val symptomDescription = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = symptom.description
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@JointFlowersActivity, android.R.color.black))
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
                setTextColor(ContextCompat.getColor(this@JointFlowersActivity, R.color.plant_green))
                setPadding(0, 0, 0, 4)
            }

            val diagnosisText = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = symptom.diagnosis
                textSize = 13f
                setTextColor(ContextCompat.getColor(this@JointFlowersActivity, android.R.color.black))
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
            setTextColor(ContextCompat.getColor(this@JointFlowersActivity, R.color.warning_orange))
            setPadding(0, 0, 0, 4)
        }

        val causeText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = symptom.cause
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@JointFlowersActivity, android.R.color.black))
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
            setTextColor(ContextCompat.getColor(this@JointFlowersActivity, R.color.solution_blue))
            setPadding(0, 0, 0, 4)
        }

        val solutionText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = symptom.solution
            textSize = 13f
            setTextColor(ContextCompat.getColor(this@JointFlowersActivity, android.R.color.black))
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
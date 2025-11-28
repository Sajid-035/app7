package com.example.plantidt

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class ProblemSolutionActivity : AppCompatActivity() {

    private lateinit var problemTitleText: TextView
    private lateinit var plantNameText: TextView
    private lateinit var problemDescriptionText: TextView
    private lateinit var solutionStepsRecyclerView: RecyclerView
    private lateinit var preventionCard: MaterialCardView
    private lateinit var preventionText: TextView
    private lateinit var backButton: MaterialButton
    private lateinit var moreInfoButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem_solution)

        initializeViews()
        setupClickListeners()
        loadProblemData()
    }

    private fun initializeViews() {
        problemTitleText = findViewById(R.id.problemTitleText)
        plantNameText = findViewById(R.id.plantNameText)
        problemDescriptionText = findViewById(R.id.problemDescriptionText)
        solutionStepsRecyclerView = findViewById(R.id.solutionStepsRecyclerView)
        preventionCard = findViewById(R.id.preventionCard)
        preventionText = findViewById(R.id.preventionText)
        backButton = findViewById(R.id.backButton)
        moreInfoButton = findViewById(R.id.moreInfoButton)
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }

        moreInfoButton.setOnClickListener {
            showMoreInfo()
        }

        preventionCard.setOnClickListener {
            // Optional: Add animation or additional info when prevention card is clicked
            Toast.makeText(this, "Prevention tips are key to plant health!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadProblemData() {
        val problem = intent.getStringExtra("PROBLEM") ?: "Unknown Problem"
        val plantName = intent.getStringExtra("PLANT_NAME") ?: "Unknown Plant"

        problemTitleText.text = problem
        plantNameText.text = "Plant: $plantName"

        // Load problem-specific data
        val problemData = getProblemSolutionData(problem)

        problemDescriptionText.text = problemData.description
        preventionText.text = problemData.prevention

        // Setup solution steps
        setupSolutionSteps(problemData.solutionSteps)
    }

    private fun setupSolutionSteps(steps: List<SolutionStep>) {
        solutionStepsRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = SolutionStepsAdapter(steps)
        solutionStepsRecyclerView.adapter = adapter
    }

    private fun getProblemSolutionData(problem: String): ProblemSolutionData {
        return when (problem.lowercase()) {
            "yellowing leaves" -> ProblemSolutionData(
                description = "Yellowing leaves can indicate overwatering, nutrient deficiency, or natural aging of the plant.",
                solutionSteps = listOf(
                    SolutionStep(1, "Check soil moisture", "Feel the soil 1-2 inches deep. If it's soggy, reduce watering frequency."),
                    SolutionStep(2, "Examine drainage", "Ensure your pot has drainage holes and water can escape freely."),
                    SolutionStep(3, "Assess lighting", "Make sure your plant is getting adequate but not excessive light."),
                    SolutionStep(4, "Consider fertilizing", "If the plant hasn't been fertilized recently, it may need nutrients.")
                ),
                prevention = "Water only when the top inch of soil is dry, ensure proper drainage, and maintain consistent lighting conditions."
            )
            "brown leaf tips" -> ProblemSolutionData(
                description = "Brown leaf tips usually indicate low humidity, fluoride in water, or inconsistent watering.",
                solutionSteps = listOf(
                    SolutionStep(1, "Increase humidity", "Use a humidifier or place a water tray near the plant."),
                    SolutionStep(2, "Use filtered water", "Switch to distilled or filtered water to avoid chemicals."),
                    SolutionStep(3, "Trim brown tips", "Use clean scissors to cut off the brown portions just above healthy tissue."),
                    SolutionStep(4, "Check watering schedule", "Ensure you're watering consistently and not letting soil dry out completely.")
                ),
                prevention = "Maintain humidity levels between 40-60%, use filtered water, and keep a consistent watering schedule."
            )
            "wilting" -> ProblemSolutionData(
                description = "Wilting can be caused by underwatering, overwatering, root rot, or extreme temperatures.",
                solutionSteps = listOf(
                    SolutionStep(1, "Check soil moisture", "Determine if the soil is too dry or too wet."),
                    SolutionStep(2, "Adjust watering", "Water thoroughly if dry, or improve drainage if waterlogged."),
                    SolutionStep(3, "Inspect roots", "Look for black, mushy roots which indicate root rot."),
                    SolutionStep(4, "Relocate if needed", "Move plant away from heat sources or cold drafts.")
                ),
                prevention = "Water when the top inch of soil is dry, ensure proper drainage, and maintain stable temperatures."
            )
            "pest infestation" -> ProblemSolutionData(
                description = "Common pests include aphids, spider mites, mealybugs, and scale insects.",
                solutionSteps = listOf(
                    SolutionStep(1, "Identify the pest", "Look closely at leaves and stems to identify the specific pest."),
                    SolutionStep(2, "Isolate the plant", "Move affected plant away from other plants to prevent spread."),
                    SolutionStep(3, "Clean the plant", "Wipe leaves with a damp cloth or spray with water to remove pests."),
                    SolutionStep(4, "Apply treatment", "Use insecticidal soap or neem oil according to package directions.")
                ),
                prevention = "Inspect plants regularly, maintain proper humidity, and quarantine new plants before introducing them to your collection."
            )
            "fungal infection" -> ProblemSolutionData(
                description = "Fungal infections often appear as spots, patches, or fuzzy growth on leaves and stems.",
                solutionSteps = listOf(
                    SolutionStep(1, "Remove affected parts", "Cut off infected leaves and stems with clean, sharp scissors."),
                    SolutionStep(2, "Improve air circulation", "Ensure good airflow around the plant."),
                    SolutionStep(3, "Reduce humidity", "Lower humidity levels and avoid getting water on leaves."),
                    SolutionStep(4, "Apply fungicide", "Use a fungicidal spray if the infection is severe.")
                ),
                prevention = "Avoid overwatering, provide good air circulation, and don't let water sit on leaves."
            )
            "drooping stems" -> ProblemSolutionData(
                description = "Drooping stems can indicate structural weakness, overwatering, or lack of support.",
                solutionSteps = listOf(
                    SolutionStep(1, "Check plant structure", "Examine if the plant needs physical support or staking."),
                    SolutionStep(2, "Assess watering", "Ensure you're not overwatering which can weaken stems."),
                    SolutionStep(3, "Provide support", "Use stakes or ties to support heavy or weak stems."),
                    SolutionStep(4, "Prune if necessary", "Remove excess growth to reduce weight on main stems.")
                ),
                prevention = "Provide adequate support for tall plants, avoid overwatering, and prune regularly to maintain structure."
            )
            "slow growth" -> ProblemSolutionData(
                description = "Slow growth can result from insufficient light, nutrients, or being root-bound.",
                solutionSteps = listOf(
                    SolutionStep(1, "Increase light exposure", "Move plant to a brighter location or add grow lights."),
                    SolutionStep(2, "Check root system", "Look for roots circling the pot indicating need for repotting."),
                    SolutionStep(3, "Fertilize appropriately", "Use a balanced fertilizer during growing season."),
                    SolutionStep(4, "Monitor temperature", "Ensure plant is in optimal temperature range for growth.")
                ),
                prevention = "Provide adequate light, fertilize during growing season, and repot when root-bound."
            )
            else -> ProblemSolutionData(
                description = "This appears to be a general plant health issue. Monitor your plant closely and adjust care as needed.",
                solutionSteps = listOf(
                    SolutionStep(1, "Observe symptoms", "Take note of any changes in leaves, stems, or overall plant health."),
                    SolutionStep(2, "Review care routine", "Check if watering, lighting, and feeding schedules are appropriate."),
                    SolutionStep(3, "Adjust environment", "Consider changes in temperature, humidity, or location."),
                    SolutionStep(4, "Consult experts", "If problems persist, consider consulting a plant expert or extension service.")
                ),
                prevention = "Maintain consistent care routines and monitor your plant regularly for early signs of problems."
            )
        }
    }

    private fun showMoreInfo() {
        val problem = intent.getStringExtra("PROBLEM") ?: "plant problems"
        val searchQuery = "how to fix $problem in houseplants"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=${searchQuery.replace(" ", "+")}"))

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to open browser", Toast.LENGTH_SHORT).show()
        }
    }
}

// Data classes for problem solutions
data class ProblemSolutionData(
    val description: String,
    val solutionSteps: List<SolutionStep>,
    val prevention: String
)

data class SolutionStep(
    val stepNumber: Int,
    val title: String,
    val description: String
)

// Adapter for solution steps
class SolutionStepsAdapter(private val steps: List<SolutionStep>) :
    RecyclerView.Adapter<SolutionStepsAdapter.SolutionStepViewHolder>() {

    class SolutionStepViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val stepNumberText: TextView = itemView.findViewById(R.id.stepNumberText)
        val stepTitleText: TextView = itemView.findViewById(R.id.stepTitleText)
        val stepDescriptionText: TextView = itemView.findViewById(R.id.stepDescriptionText)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): SolutionStepViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_solution_step, parent, false)
        return SolutionStepViewHolder(view)
    }

    override fun onBindViewHolder(holder: SolutionStepViewHolder, position: Int) {
        val step = steps[position]
        holder.stepNumberText.text = step.stepNumber.toString()
        holder.stepTitleText.text = step.title
        holder.stepDescriptionText.text = step.description
    }

    override fun getItemCount(): Int = steps.size
}
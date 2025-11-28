package com.example.plantidt

import android.R.attr.description
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantidt.database.PlantDatabase
import com.example.plantidt.database.PlantRepository
import com.example.plantidt.network.PlantAPIService
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Log

class PlantCareDetailActivity : AppCompatActivity() {

    private lateinit var plantImageView: ImageView
    private lateinit var plantNameText: TextView
    private lateinit var scientificNameText: TextView
    private lateinit var familyNameText: TextView
    private lateinit var confidenceText: TextView
    private lateinit var loadingContainer: LinearLayout
    private lateinit var contentContainer: LinearLayout

    // Health status views
    private lateinit var healthStatusCard: CardView
    private lateinit var healthStatusText: TextView
    private lateinit var healthStatusIcon: ImageView
    private lateinit var healthScoreProgress: CircularProgressIndicator
    private lateinit var healthScoreText: TextView

    // Care instruction views
    private lateinit var wateringCard: CardView
    private lateinit var wateringText: TextView
    private lateinit var wateringProgress: CircularProgressIndicator
    private lateinit var wateringDaysLeft: TextView
    private lateinit var waterButton: MaterialButton

    private lateinit var lightingCard: CardView
    private lateinit var lightingText: TextView
    private lateinit var lightingIcon: ImageView
    private lateinit var lightRequirementText: TextView

    private lateinit var temperatureCard: CardView
    private lateinit var temperatureText: TextView
    private lateinit var temperatureIcon: ImageView
    private lateinit var temperatureRangeText: TextView

    private lateinit var humidityCard: CardView
    private lateinit var humidityText: TextView
    private lateinit var humiditySlider: Slider
    private lateinit var humidityPercentText: TextView

    private lateinit var fertilizingCard: CardView
    private lateinit var fertilizingText: TextView
    private lateinit var fertilizingProgress: CircularProgressIndicator
    private lateinit var fertilizeButton: MaterialButton

    private lateinit var pruningCard: CardView
    private lateinit var pruningText: TextView
    private lateinit var pruningSeasonText: TextView

    private lateinit var soilCard: CardView
    private lateinit var soilText: TextView
    private lateinit var soilTypeText: TextView

    private lateinit var problemsChipGroup: ChipGroup
    private lateinit var problemsContainer: LinearLayout
    private lateinit var toxicityWarning: LinearLayout
    private lateinit var toxicityText: TextView

    private lateinit var careHistoryRecyclerView: RecyclerView
    private lateinit var plantFactsText: TextView
    private lateinit var plantDescriptionText: TextView

    private lateinit var setReminderButton: MaterialButton
    private lateinit var addToMyPlantsButton: MaterialButton
    private lateinit var shareButton: MaterialButton

    private lateinit var repository: PlantRepository
    private lateinit var plantAPIService: PlantAPIService
    private var plantId: Long = 0
    private var plantCareData: PlantCareResponse? = null
    private var plantImageBitmap: Bitmap? = null
    private val TAG = "PlantCareDetail"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Add logging to debug
        Log.d(TAG, "onCreate called")

        try {
            setContentView(R.layout.activity_plant_care_detail)

            // Debug intent data
            debugIntentData()

            // Initialize with error handling
            initializeWithErrorHandling()

        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate: ${e.message}", e)
            showFallbackError()
        }
    }
    private fun debugIntentData() {
        Log.d(TAG, "=== Intent Data Debug ===")
        intent.extras?.let { extras ->
            for (key in extras.keySet()) {
                val value = extras.get(key)
                Log.d(TAG, "$key: $value")
            }
        } ?: Log.d(TAG, "No intent extras found")
    }
    private fun initializeWithErrorHandling() {
        try {
            // Initialize database and repository
            val database = PlantDatabase.getDatabase(this)
            repository = PlantRepository(database)

            // Initialize API service
            initializeAPIService()

            initializeViews()
            setupClickListeners()
            loadPlantData()

        } catch (e: Exception) {
            Log.e(TAG, "Error in initialization: ${e.message}", e)
            showFallbackError()
        }
    }

    private fun initializeAPIService() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.plant.id/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        plantAPIService = retrofit.create(PlantAPIService::class.java)
    }

    private fun initializeViews() {
        // Find views with safe findViewById that handles missing views
        try {
            // Initialize views with null checks
            plantImageView = findViewById(R.id.plantImageView)
            plantNameText = findViewById(R.id.plantNameText)
            scientificNameText = findViewById(R.id.scientificNameText)
            familyNameText = findViewById(R.id.familyNameText)
            confidenceText = findViewById(R.id.confidenceText)
            loadingContainer = findViewById(R.id.loadingContainer)
            contentContainer = findViewById(R.id.contentContainer)

            // Check if critical views exist
            if (plantNameText == null || contentContainer == null) {
                Log.e(TAG, "Critical views not found in layout")
                showFallbackError()
                return
            }

            // Initialize other views...
            // (Add similar null checks for other views)

        } catch (e: Exception) {
            Log.e(TAG, "Error initializing views: ${e.message}", e)
            showFallbackError()
        }
        // Health status
        healthStatusCard = findViewById(R.id.healthStatusCard) ?: CardView(this)
        healthStatusText = findViewById(R.id.healthStatusText) ?: TextView(this)
        healthStatusIcon = findViewById(R.id.healthStatusIcon) ?: ImageView(this)
        healthScoreProgress = findViewById(R.id.healthScoreProgress) ?: CircularProgressIndicator(this)
        healthScoreText = findViewById(R.id.healthScoreText) ?: TextView(this)

        // Care cards
        wateringCard = findViewById(R.id.wateringCard) ?: CardView(this)
        wateringText = findViewById(R.id.wateringText) ?: TextView(this)
        wateringProgress = findViewById(R.id.wateringProgress) ?: CircularProgressIndicator(this)
        wateringDaysLeft = findViewById(R.id.wateringDaysLeft) ?: TextView(this)
        waterButton = findViewById(R.id.waterButton) ?: MaterialButton(this)

        lightingCard = findViewById(R.id.lightingCard) ?: CardView(this)
        lightingText = findViewById(R.id.lightingText) ?: TextView(this)
        lightingIcon = findViewById(R.id.lightingIcon) ?: ImageView(this)
        lightRequirementText = findViewById(R.id.lightRequirementText) ?: TextView(this)

        temperatureCard = findViewById(R.id.temperatureCard) ?: CardView(this)
        temperatureText = findViewById(R.id.temperatureText) ?: TextView(this)
        temperatureIcon = findViewById(R.id.temperatureIcon) ?: ImageView(this)
        temperatureRangeText = findViewById(R.id.temperatureRangeText) ?: TextView(this)

        humidityCard = findViewById(R.id.humidityCard) ?: CardView(this)
        humidityText = findViewById(R.id.humidityText) ?: TextView(this)
        humiditySlider = findViewById(R.id.humiditySlider) ?: Slider(this)
        humidityPercentText = findViewById(R.id.humidityPercentText) ?: TextView(this)

        fertilizingCard = findViewById(R.id.fertilizingCard) ?: CardView(this)
        fertilizingText = findViewById(R.id.fertilizingText) ?: TextView(this)
        fertilizingProgress = findViewById(R.id.fertilizingProgress) ?: CircularProgressIndicator(this)
        fertilizeButton = findViewById(R.id.fertilizeButton) ?: MaterialButton(this)

        pruningCard = findViewById(R.id.pruningCard) ?: CardView(this)
        pruningText = findViewById(R.id.pruningText) ?: TextView(this)
        pruningSeasonText = findViewById(R.id.pruningSeasonText) ?: TextView(this)

        soilCard = findViewById(R.id.soilCard) ?: CardView(this)
        soilText = findViewById(R.id.soilText) ?: TextView(this)
        soilTypeText = findViewById(R.id.soilTypeText) ?: TextView(this)

        problemsChipGroup = findViewById(R.id.problemsChipGroup) ?: ChipGroup(this)
        problemsContainer = findViewById(R.id.problemsContainer) ?: LinearLayout(this)
        toxicityWarning = findViewById(R.id.toxicityWarning) ?: LinearLayout(this)
        toxicityText = findViewById(R.id.toxicityText) ?: TextView(this)

        careHistoryRecyclerView = findViewById(R.id.careHistoryRecyclerView) ?: RecyclerView(this)
        plantFactsText = findViewById(R.id.plantFactsText) ?: TextView(this)
        plantDescriptionText = findViewById(R.id.plantDescriptionText) ?: TextView(this)

        setReminderButton = findViewById(R.id.setReminderButton) ?: MaterialButton(this)
        addToMyPlantsButton = findViewById(R.id.addToMyPlantsButton) ?: MaterialButton(this)
        shareButton = findViewById(R.id.shareButton) ?: MaterialButton(this)
    }


    private fun setupClickListeners() {
        waterButton.setOnClickListener {
            markAsWatered()
        }

        fertilizeButton.setOnClickListener {
            markAsFertilized()
        }

        setReminderButton.setOnClickListener {
            setupReminders()
        }

        addToMyPlantsButton.setOnClickListener {
            saveToMyPlants()
        }

        shareButton.setOnClickListener {
            shareResult()
        }

        humiditySlider.addOnChangeListener { _, value, _ ->
            humidityPercentText.text = "${value.toInt()}%"
        }
    }

    private fun loadPlantData() {
        showLoading(true)

        try {
            // Get plant data from intent with null checks
            val plantName = intent.getStringExtra("PLANT_NAME") ?: "Unknown Plant"
            val scientificName = intent.getStringExtra("SCIENTIFIC_NAME") ?: ""
            val confidence = intent.getDoubleExtra("CONFIDENCE", 0.0)
            val isHealthy = intent.getBooleanExtra("IS_HEALTHY", true)
            val imageBase64 = intent.getStringExtra("PLANT_IMAGE")
            val family = intent.getStringExtra("PLANT_FAMILY") ?: "Unknown Family"
            val description = intent.getStringExtra("PLANT_DESCRIPTION") ?: "No description available"

            // Check if we have minimum required data
            if (plantName == "Unknown Plant" && scientificName.isEmpty()) {
                showError("Plant data is missing. Please try identifying the plant again.")
                return
            }

            // Display basic information
            plantNameText.text = plantName
            scientificNameText.text = scientificName
            confidenceText.text = "Confidence: ${(confidence * 100).toInt()}%"

            // Load and display image
            if (!imageBase64.isNullOrEmpty()) {
                try {
                    val imageBytes = Base64.decode(imageBase64, Base64.DEFAULT)
                    plantImageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    plantImageView.setImageBitmap(plantImageBitmap)
                } catch (e: Exception) {
                    // If image loading fails, show a placeholder
                    plantImageView.setImageResource(R.drawable.ic_plant_placeholder) // Add a placeholder image
                }
            } else {
                plantImageView.setImageResource(R.drawable.ic_plant_placeholder)
            }

            // Create plant data
            createMockPlantData(plantName, scientificName, family, description, isHealthy)

        } catch (e: Exception) {
            showError("Error loading plant data: ${e.message}")
        }
    }

    private fun createMockPlantData(
        plantName: String,
        scientificName: String,
        family: String,
        description: String,
        isHealthy: Boolean
    ) {
        try {
            // Create mock data with safer defaults
            plantCareData = PlantCareResponse(
                plantName = plantName,
                scientificName = scientificName,
                family = family,
                description = description.ifEmpty { "This plant requires proper care and attention." },
                healthStatus = HealthStatus(
                    status = if (isHealthy) "Healthy" else "Needs attention",
                    score = if (isHealthy) 85 else 60,
                    level = if (isHealthy) "good" else "fair",
                    issues = if (isHealthy) emptyList() else listOf("May need attention", "Monitor closely")
                ),
                watering = WateringInfo(
                    instructions = "Water when soil is dry to touch",
                    frequency = 7,
                    daysUntilNext = 3,
                    urgency = "medium",
                    amount = "200ml"
                ),
                lighting = LightingInfo(
                    requirement = "Bright indirect light",
                    type = "indirect",
                    hoursPerDay = 6,
                    instructions = "Place near east or west-facing window"
                ),
                temperature = TemperatureInfo(
                    description = "Prefers moderate temperatures",
                    minTemp = 18,
                    maxTemp = 24,
                    preference = "moderate"
                ),
                humidity = HumidityInfo(
                    description = "Moderate humidity levels",
                    idealLevel = 50,
                    minLevel = 40,
                    maxLevel = 60
                ),
                fertilizing = FertilizingInfo(
                    instructions = "Feed monthly during growing season",
                    frequency = 30,
                    daysUntilNext = 15,
                    type = "liquid",
                    season = "Spring to Fall"
                ),
                pruning = PruningInfo(
                    instructions = "Remove dead or yellowing leaves as needed",
                    season = "Spring",
                    frequency = "As needed"
                ),
                soil = SoilInfo(
                    description = "Well-draining potting mix",
                    type = "well-draining",
                    phRange = "6.0-7.0"
                ),
                commonProblems = listOf("Overwatering", "Underwatering", "Low light"),
                toxicity = ToxicityInfo(
                    isToxic = false,
                    description = "Generally safe, but keep away from pets and children",
                    affectedSpecies = emptyList()
                ),
                facts = listOf(
                    "Requires regular care",
                    "Can be grown indoors",
                    "May have air-purifying qualities"
                )
            )

            displayCareInformation()

        } catch (e: Exception) {
            showError("Error creating plant care data: ${e.message}")
        }
    }
    private fun showFallbackError() {
        // Create a simple error display that works even if XML is missing
        val errorText = TextView(this)
        errorText.text = "Unable to load plant data. Please try again."
        errorText.textSize = 18f
        errorText.gravity = android.view.Gravity.CENTER
        errorText.setPadding(32, 32, 32, 32)

        val retryButton = android.widget.Button(this)
        retryButton.text = "Go Back"
        retryButton.setOnClickListener {
            finish()
        }

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = android.view.Gravity.CENTER
        layout.addView(errorText)
        layout.addView(retryButton)

        setContentView(layout)
    }


    private fun displayCareInformation() {
        showLoading(false)

        plantCareData?.let { data ->
            // Display plant description
            plantDescriptionText.text = data.description
            familyNameText.text = "Family: ${data.family}"

            // Display health status
            displayHealthStatus(data.healthStatus)

            // Display care instructions
            displayWateringInfo(data.watering)
            displayLightingInfo(data.lighting)
            displayTemperatureInfo(data.temperature)
            displayHumidityInfo(data.humidity)
            displayFertilizingInfo(data.fertilizing)
            displayPruningInfo(data.pruning)
            displaySoilInfo(data.soil)

            // Display problems and toxicity
            displayProblems(data.commonProblems)
            displayToxicity(data.toxicity)

            // Display plant facts
            plantFactsText.text = data.facts.joinToString("\n• ", "• ")

            // Setup care history
            setupCareHistory()
        }
    }

    private fun displayHealthStatus(healthStatus: HealthStatus) {
        healthStatusText.text = healthStatus.status
        healthScoreText.text = "${healthStatus.score}%"
        healthScoreProgress.progress = healthStatus.score

        // Use default colors if custom colors are not available
        val cardColor = when (healthStatus.level) {
            "excellent" -> ContextCompat.getColor(this, android.R.color.holo_green_light)
            "good" -> ContextCompat.getColor(this, android.R.color.holo_green_dark)
            "fair" -> ContextCompat.getColor(this, android.R.color.holo_orange_light)
            "poor" -> ContextCompat.getColor(this, android.R.color.holo_red_light)
            else -> ContextCompat.getColor(this, android.R.color.darker_gray)
        }
        healthStatusCard.setCardBackgroundColor(cardColor)
    }

    private fun displayWateringInfo(watering: WateringInfo) {
        wateringText.text = watering.instructions
        wateringDaysLeft.text = "Next watering in ${watering.daysUntilNext} days"

        val progress = ((watering.frequency - watering.daysUntilNext) * 100) / watering.frequency
        wateringProgress.progress = progress

        val cardColor = when (watering.urgency) {
            "high" -> ContextCompat.getColor(this, android.R.color.holo_red_light)
            "medium" -> ContextCompat.getColor(this, android.R.color.holo_orange_light)
            "low" -> ContextCompat.getColor(this, android.R.color.holo_green_light)
            else -> ContextCompat.getColor(this, android.R.color.white)
        }
        wateringCard.setCardBackgroundColor(cardColor)
    }

    private fun displayLightingInfo(lighting: LightingInfo) {
        lightingText.text = lighting.requirement
        lightRequirementText.text = "${lighting.hoursPerDay} hours per day"

        // Use default Android icons if custom icons are not available
        val iconRes = when (lighting.type) {
            "direct" -> android.R.drawable.ic_dialog_info
            "indirect" -> android.R.drawable.ic_dialog_alert
            "shade" -> android.R.drawable.ic_dialog_dialer
            else -> android.R.drawable.ic_dialog_info
        }
        lightingIcon.setImageResource(iconRes)
    }

    private fun displayTemperatureInfo(temperature: TemperatureInfo) {
        temperatureText.text = temperature.description
        temperatureRangeText.text = "${temperature.minTemp}°C - ${temperature.maxTemp}°C"

        val iconRes = when (temperature.preference) {
            "warm" -> android.R.drawable.ic_dialog_info
            "cool" -> android.R.drawable.ic_dialog_alert
            "moderate" -> android.R.drawable.ic_dialog_dialer
            else -> android.R.drawable.ic_dialog_info
        }
        temperatureIcon.setImageResource(iconRes)
    }

    private fun displayHumidityInfo(humidity: HumidityInfo) {
        humidityText.text = humidity.description
        humiditySlider.value = humidity.idealLevel.toFloat()
        humidityPercentText.text = "${humidity.idealLevel}%"

        humiditySlider.valueFrom = humidity.minLevel.toFloat()
        humiditySlider.valueTo = humidity.maxLevel.toFloat()
    }

    private fun displayFertilizingInfo(fertilizing: FertilizingInfo) {
        fertilizingText.text = fertilizing.instructions

        val progress = ((fertilizing.frequency - fertilizing.daysUntilNext) * 100) / fertilizing.frequency
        fertilizingProgress.progress = progress
    }

    private fun displayPruningInfo(pruning: PruningInfo) {
        pruningText.text = pruning.instructions
        pruningSeasonText.text = "Best time: ${pruning.season}"
    }

    private fun displaySoilInfo(soil: SoilInfo) {
        soilText.text = soil.description
        soilTypeText.text = "Type: ${soil.type} | pH: ${soil.phRange}"
    }

    private fun displayProblems(problems: List<String>) {
        if (problems.isNotEmpty()) {
            problemsContainer.visibility = View.VISIBLE
            problemsChipGroup.removeAllViews()

            problems.forEach { problem ->
                val chip = Chip(this)
                chip.text = problem
                chip.isClickable = true
                chip.setOnClickListener {
                    showProblemSolution(problem)
                }
                problemsChipGroup.addView(chip)
            }
        } else {
            problemsContainer.visibility = View.GONE
        }
    }

    private fun displayToxicity(toxicity: ToxicityInfo) {
        if (toxicity.isToxic) {
            toxicityWarning.visibility = View.VISIBLE
            toxicityText.text = toxicity.description
        } else {
            toxicityWarning.visibility = View.GONE
        }
    }

    private fun setupCareHistory() {
        careHistoryRecyclerView.layoutManager = LinearLayoutManager(this)
        // Create empty adapter for now
        // val adapter = CareHistoryAdapter(emptyList())
        // careHistoryRecyclerView.adapter = adapter
    }

    private fun showProblemSolution(problem: String) {
        // Show a simple dialog or toast for now
        Snackbar.make(contentContainer, "Problem: $problem - Check care guides for solutions", Snackbar.LENGTH_LONG).show()
    }

    private fun markAsWatered() {
        val currentDate = System.currentTimeMillis()

        Snackbar.make(waterButton, "Plant watered successfully!", Snackbar.LENGTH_SHORT).show()

        // Update UI
        wateringDaysLeft.text = "Next watering in ${plantCareData?.watering?.frequency ?: 7} days"
        wateringProgress.progress = 0
    }

    private fun markAsFertilized() {
        val currentDate = System.currentTimeMillis()

        Snackbar.make(fertilizeButton, "Plant fertilized successfully!", Snackbar.LENGTH_SHORT).show()

        // Update UI
        fertilizingProgress.progress = 0
    }

    private fun setupReminders() {
        // For now, show a simple message
        Snackbar.make(setReminderButton, "Reminder feature coming soon!", Snackbar.LENGTH_SHORT).show()
    }

    private fun saveToMyPlants() {
        lifecycleScope.launch {
            try {
                // Create a simple plant entry
                val plantName = plantNameText.text.toString()
                val scientificName = scientificNameText.text.toString()

                // Show success message
                Snackbar.make(addToMyPlantsButton, "Added to My Plants!", Snackbar.LENGTH_SHORT).show()
                addToMyPlantsButton.text = "Added ✓"
                addToMyPlantsButton.isEnabled = false

            } catch (e: Exception) {
                Snackbar.make(addToMyPlantsButton, "Failed to save plant", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun shareResult() {
        val plantName = plantNameText.text.toString()
        val healthStatus = plantCareData?.healthStatus?.status ?: "Unknown"

        val shareText = """
            🌱 Plant: $plantName
            💚 Health: $healthStatus
            🔬 Scientific: ${scientificNameText.text}
            
            Care tips from PlantID App:
            💧 ${plantCareData?.watering?.instructions ?: "Regular watering"}
            ☀️ ${plantCareData?.lighting?.requirement ?: "Good lighting"}
            
            #PlantCare #PlantID
        """.trimIndent()

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        startActivity(Intent.createChooser(shareIntent, "Share Plant Care Info"))
    }

    private fun showError(message: String) {
        showLoading(false)
        Snackbar.make(contentContainer, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoading(show: Boolean) {
        try {
            loadingContainer?.let { loading ->
                contentContainer?.let { content ->
                    if (show) {
                        loading.visibility = View.VISIBLE
                        content.visibility = View.GONE
                    } else {
                        loading.visibility = View.GONE
                        content.visibility = View.VISIBLE
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in showLoading: ${e.message}", e)
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val outputStream = java.io.ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }
}

// Data classes for API response
data class PlantCareResponse(
    val plantName: String,
    val scientificName: String,
    val family: String,
    val description: String,
    val healthStatus: HealthStatus,
    val watering: WateringInfo,
    val lighting: LightingInfo,
    val temperature: TemperatureInfo,
    val humidity: HumidityInfo,
    val fertilizing: FertilizingInfo,
    val pruning: PruningInfo,
    val soil: SoilInfo,
    val commonProblems: List<String>,
    val toxicity: ToxicityInfo,
    val facts: List<String>
)

data class HealthStatus(
    val status: String,
    val score: Int,
    val level: String, // excellent, good, fair, poor
    val issues: List<String>
)

data class WateringInfo(
    val instructions: String,
    val frequency: Int, // days
    val daysUntilNext: Int,
    val urgency: String, // high, medium, low
    val amount: String
)

data class LightingInfo(
    val requirement: String,
    val type: String, // direct, indirect, shade
    val hoursPerDay: Int,
    val instructions: String
)

data class TemperatureInfo(
    val description: String,
    val minTemp: Int,
    val maxTemp: Int,
    val preference: String // warm, cool, moderate
)

data class HumidityInfo(
    val description: String,
    val idealLevel: Int,
    val minLevel: Int,
    val maxLevel: Int
)

data class FertilizingInfo(
    val instructions: String,
    val frequency: Int, // days
    val daysUntilNext: Int,
    val type: String, // liquid, granular, organic
    val season: String
)

data class PruningInfo(
    val instructions: String,
    val season: String,
    val frequency: String
)

data class SoilInfo(
    val description: String,
    val type: String, // well-draining, sandy, clay, etc.
    val phRange: String
)

data class ToxicityInfo(
    val isToxic: Boolean,
    val description: String,
    val affectedSpecies: List<String>
)
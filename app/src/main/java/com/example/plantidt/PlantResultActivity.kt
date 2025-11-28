package com.example.plantidt

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantidt.database.PlantDatabase
import com.example.plantidt.database.PlantRepository
import com.example.plantidt.database.entities.PlantIdentification
import com.example.plantidt.database.entities.PlantCareInfo
import com.example.plantidt.database.entities.PlantHealthAssessment
import com.example.plantidt.database.entities.UserGarden
import com.example.plantidt.adapters.CareInstructionAdapter
import com.example.plantidt.adapters.HealthIssueAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import com.google.android.material.appbar.MaterialToolbar

class PlantResultActivity : AppCompatActivity() {

    private lateinit var plantImage: ImageView
    private lateinit var plantName: TextView
    private lateinit var scientificName: TextView
    private lateinit var familyName: TextView
    private lateinit var confidenceText: TextView
    private lateinit var healthScoreText: TextView
    private lateinit var healthStatusText: TextView
    private lateinit var healthIndicator: View
    private lateinit var careInstructionsRecycler: RecyclerView
    private lateinit var healthIssuesRecycler: RecyclerView
    private lateinit var plantDetailsCard: MaterialCardView
    private lateinit var careRequirementsCard: MaterialCardView
    private lateinit var healthAssessmentCard: MaterialCardView
    private lateinit var addToGardenButton: MaterialButton
    private lateinit var shareButton: MaterialButton
    private lateinit var retryButton: MaterialButton
    private lateinit var loadingProgress: ProgressBar
    private lateinit var errorLayout: LinearLayout
    private lateinit var contentLayout: LinearLayout

    // Plant details views
    private lateinit var descriptionText: TextView
    private lateinit var careLevelChip: Chip
    private lateinit var lightRequirementText: TextView
    private lateinit var waterRequirementText: TextView
    private lateinit var soilTypeText: TextView
    private lateinit var temperatureText: TextView
    private lateinit var humidityText: TextView
    private lateinit var fertilizerText: TextView
    private lateinit var toxicityChip: Chip
    private lateinit var growthRateText: TextView
    private lateinit var matureSizeText: TextView
    private lateinit var bloomTimeText: TextView
    private lateinit var propagationText: TextView
    private lateinit var tagsChipGroup: ChipGroup

    private lateinit var repository: PlantRepository
    private lateinit var toolbar: MaterialToolbar
    private var plantIdentification: PlantIdentification? = null
    private var plantCareInfo: PlantCareInfo? = null
    private var healthAssessment: PlantHealthAssessment? = null
    private var currentBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_result)

        initializeViews()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Show the back arrow
        toolbar.setNavigationOnClickListener {
            // This handles the click on the back arrow
            onBackPressedDispatcher.onBackPressed()
        }
        setupRepository()
        setupRecyclerViews()
        setupClickListeners()

        // Get plant identification data from intent
        val identificationId = intent.getLongExtra("identification_id", -1)
        if (identificationId != -1L) {
            loadPlantData(identificationId)
        } else {
            showError("Unable to load plant data")
        }
    }

    private fun initializeViews() {
        plantImage = findViewById(R.id.plantImage)
        plantName = findViewById(R.id.plantName)
        scientificName = findViewById(R.id.scientificName)
        familyName = findViewById(R.id.familyName)
        confidenceText = findViewById(R.id.confidenceText)
        healthScoreText = findViewById(R.id.healthScoreText)
        healthStatusText = findViewById(R.id.healthStatusText)
        healthIndicator = findViewById(R.id.healthIndicator)
        careInstructionsRecycler = findViewById(R.id.careInstructionsRecycler)
        healthIssuesRecycler = findViewById(R.id.healthIssuesRecycler)
        plantDetailsCard = findViewById(R.id.plantDetailsCard)
        careRequirementsCard = findViewById(R.id.careRequirementsCard)
        healthAssessmentCard = findViewById(R.id.healthAssessmentCard)
        addToGardenButton = findViewById(R.id.addToGardenButton)
        shareButton = findViewById(R.id.shareButton)
        retryButton = findViewById(R.id.retryButton)
        loadingProgress = findViewById(R.id.loadingProgress)
        errorLayout = findViewById(R.id.errorLayout)
        contentLayout = findViewById(R.id.contentLayout)

        // Plant details views
        descriptionText = findViewById(R.id.descriptionText)
        careLevelChip = findViewById(R.id.careLevelChip)
        lightRequirementText = findViewById(R.id.lightRequirementText)
        waterRequirementText = findViewById(R.id.waterRequirementText)
        soilTypeText = findViewById(R.id.soilTypeText)
        temperatureText = findViewById(R.id.temperatureText)
        humidityText = findViewById(R.id.humidityText)
        toolbar = findViewById(R.id.toolbar)
        fertilizerText = findViewById(R.id.fertilizerText)
        toxicityChip = findViewById(R.id.toxicityChip)
        growthRateText = findViewById(R.id.growthRateText)
        matureSizeText = findViewById(R.id.matureSizeText)
        bloomTimeText = findViewById(R.id.bloomTimeText)
        propagationText = findViewById(R.id.propagationText)
        tagsChipGroup = findViewById(R.id.tagsChipGroup)
    }

    private fun setupRepository() {
        val database = PlantDatabase.getDatabase(this)
        repository = PlantRepository(database)
    }

    private fun setupRecyclerViews() {
        careInstructionsRecycler.layoutManager = LinearLayoutManager(this)
        healthIssuesRecycler.layoutManager = LinearLayoutManager(this)
    }

    private fun setupClickListeners() {
        addToGardenButton.setOnClickListener {
            addPlantToGarden()
        }

        shareButton.setOnClickListener {
            sharePlantInfo()
        }

        retryButton.setOnClickListener {
            finish()
        }
    }

    private suspend fun checkIfPlantInGarden(): Boolean {
        return plantIdentification?.let { identification ->
            repository.isPlantInGarden(identification.plantId)
        } ?: false
    }

    private suspend fun savePlantToGarden() {
        plantIdentification?.let { identification ->
            val gardenEntry = UserGarden(
                plantId = identification.plantId,
                nickname = identification.commonName,
                notes = "",
                dateAdded = System.currentTimeMillis(),
                lastWatered = 0,
                lastFertilized = 0,
                reminderEnabled = true,
                wateringInterval = 7, // Default to weekly watering
                fertilizingInterval = 30, // Default to monthly fertilizing
                location = "Unknown",
                isFavorite = false
            )

            repository.addToGarden(gardenEntry)
        }
    }

    private fun checkGardenStatus() {
        lifecycleScope.launch {
            try {
                val isInGarden = checkIfPlantInGarden()
                runOnUiThread {
                    if (isInGarden) {
                        addToGardenButton.text = "In Garden"
                        addToGardenButton.isEnabled = true
                        addToGardenButton.setIconResource(R.drawable.ic_garden)
                        addToGardenButton.setOnClickListener {
                            showGardenOptions()
                        }
                    } else {
                        addToGardenButton.text = "Add to Garden"
                        addToGardenButton.isEnabled = true
                        addToGardenButton.setIconResource(R.drawable.ic_add)
                        addToGardenButton.setOnClickListener {
                            addPlantToGarden()
                        }
                    }
                }
            } catch (e: Exception) {
                showSnackbar("Error checking garden status: ${e.message}")
            }
        }
    }

    private fun showGardenOptions() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Garden Options")
            .setItems(arrayOf(
                "Save to My Garden", // Updated this option
                "Edit Nickname",
                "Update Care Schedule",
                "Mark as Favorite",
                "Remove from Garden"
            )) { _, which ->
                when (which) {
                    0 -> viewInGarden() // This will now save to garden
                    1 -> editNickname()
                    2 -> updateCareSchedule()
                    3 -> toggleFavorite()
                    4 -> removeFromGarden()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun viewInGarden() {
        lifecycleScope.launch {
            try {
                // Check if plant is already in garden
                val isInGarden = checkIfPlantInGarden()

                if (!isInGarden) {
                    // Save plant to garden first
                    savePlantToGarden()

                    // Update the button state
                    runOnUiThread {
                        addToGardenButton.text = "In Garden"
                        addToGardenButton.setIconResource(R.drawable.ic_garden)
                        addToGardenButton.setOnClickListener {
                            showGardenOptions()
                        }
                    }

                    showSnackbar("Plant saved to your garden!")
                } else {
                    showSnackbar("Plant is already in your garden!")
                }

                // Navigate to MyPlantsActivity to view the garden
                val intent = Intent(this@PlantResultActivity, MyPlantsActivity::class.java)
                intent.putExtra("SHOW_PLANTS_TAB", true) // Ensure plants tab is selected
                startActivity(intent)

            } catch (e: Exception) {
                showSnackbar("Error saving plant to garden: ${e.message}")
            }
        }
    }

    private fun saveToGardenAndNavigate() {
        lifecycleScope.launch {
            try {
                val isInGarden = checkIfPlantInGarden()

                if (!isInGarden) {
                    savePlantToGarden()
                    showSnackbar("Plant saved to your garden!")

                    // Update button state
                    runOnUiThread {
                        addToGardenButton.text = "In Garden"
                        addToGardenButton.setIconResource(R.drawable.ic_garden)
                        addToGardenButton.setOnClickListener {
                            showGardenOptions()
                        }
                    }
                }

                // Navigate to MyPlantsActivity
                val intent = Intent(this@PlantResultActivity, MyPlantsActivity::class.java)
                intent.putExtra("SHOW_PLANTS_TAB", true)
                startActivity(intent)

            } catch (e: Exception) {
                showSnackbar("Error saving plant: ${e.message}")
            }
        }
    }

    private fun editNickname() {
        val input = android.widget.EditText(this)
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Edit Nickname")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val nickname = input.text.toString().trim()
                if (nickname.isNotEmpty()) {
                    lifecycleScope.launch {
                        try {
                            plantIdentification?.let { identification ->
                                repository.updateNickname(identification.plantId, nickname)
                                showSnackbar("Nickname updated!")
                            }
                        } catch (e: Exception) {
                            showSnackbar("Error updating nickname: ${e.message}")
                        }
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateCareSchedule() {
        showSnackbar("Care schedule update (not implemented)")
    }

    private fun toggleFavorite() {
        lifecycleScope.launch {
            try {
                plantIdentification?.let { identification ->
                    val gardenPlant = repository.getGardenPlant(identification.plantId)
                    gardenPlant?.let { plant ->
                        val newFavoriteStatus = !plant.isFavorite
                        repository.updateFavoriteStatus(identification.plantId, newFavoriteStatus)
                        val message = if (newFavoriteStatus) "Added to favorites!" else "Removed from favorites!"
                        showSnackbar(message)
                    }
                }
            } catch (e: Exception) {
                showSnackbar("Error updating favorite status: ${e.message}")
            }
        }
    }

    private fun removeFromGarden() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Remove from Garden")
            .setMessage("Are you sure you want to remove this plant from your garden?")
            .setPositiveButton("Remove") { _, _ ->
                lifecycleScope.launch {
                    try {
                        plantIdentification?.let { identification ->
                            repository.removeFromGarden(identification.plantId)
                            runOnUiThread {
                                addToGardenButton.text = "Add to Garden"
                                addToGardenButton.setIconResource(R.drawable.ic_add)
                                addToGardenButton.setOnClickListener {
                                    addPlantToGarden()
                                }
                            }
                            showSnackbar("Plant removed from garden")
                        }
                    } catch (e: Exception) {
                        showSnackbar("Error removing plant: ${e.message}")
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


    private fun loadPlantData(identificationId: Long) {
        showLoading(true)

        lifecycleScope.launch {
            try {
                plantIdentification = repository.getIdentificationById(identificationId)

                plantIdentification?.let { identification ->
                    // The data should now already be in the database, so we just load it.
                    plantCareInfo = repository.getCareInfo(identification.plantId)
                    healthAssessment = repository.getLatestHealthAssessment(identification.plantId)

                    // No more need to call fetchPlantCareInfo()
                    displayPlantData()
                }

                checkGardenStatus()
            } catch (e: Exception) {
                showError("Error loading plant data: ${e.message}")
            }
        }
    }



    private fun displayPlantData() {
        runOnUiThread {
            showLoading(false)

            plantIdentification?.let { identification ->
                if (identification.imageBase64.isNotEmpty()) {
                    try {
                        val imageBytes = Base64.decode(identification.imageBase64, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        plantImage.setImageBitmap(bitmap)
                        currentBitmap = bitmap
                    } catch (e: Exception) {
                        plantImage.setImageResource(R.drawable.ic_plant_placeholder)
                    }
                }

                plantName.text = identification.commonName
                scientificName.text = identification.scientificName
                familyName.text = identification.family
                confidenceText.text = "Confidence: ${(identification.confidence * 100).toInt()}%"

                if (identification.healthScore > 0) {
                    healthScoreText.text = "${identification.healthScore}/100"
                    healthStatusText.text = identification.healthStatus
                    setHealthIndicatorColor(identification.healthScore)
                } else {
                    healthAssessmentCard.visibility = View.GONE
                }
            }

            plantCareInfo?.let { careInfo ->
                displayCareInfo(careInfo)
            }

            healthAssessment?.let { assessment ->
                displayHealthAssessment(assessment)
            }
        }
    }

    // In PlantResultActivity.kt

    private fun displayCareInfo(careInfo: PlantCareInfo) {
        descriptionText.text = careInfo.description

        careLevelChip.text = careInfo.careLevel
        when (careInfo.careLevel.lowercase()) {
            "easy" -> careLevelChip.setChipBackgroundColorResource(R.color.care_level_easy)
            "medium" -> careLevelChip.setChipBackgroundColorResource(R.color.care_level_medium)
            "hard" -> careLevelChip.setChipBackgroundColorResource(R.color.care_level_hard)
        }

        lightRequirementText.text = careInfo.lightRequirement
        waterRequirementText.text = careInfo.waterRequirement
        soilTypeText.text = careInfo.soilType
        temperatureText.text = careInfo.temperature
        humidityText.text = careInfo.humidity
        fertilizerText.text = careInfo.fertilizer

        toxicityChip.text = careInfo.toxicity
        toxicityChip.setChipBackgroundColorResource(
            if (careInfo.toxicity.contains("safe", ignoreCase = true))
                R.color.pet_safe else R.color.toxic
        )

        growthRateText.text = careInfo.growthRate
        matureSizeText.text = careInfo.matureSize
        bloomTimeText.text = careInfo.bloomTime
        propagationText.text = careInfo.propagationMethod

        addTagsToChipGroup(careInfo)

        // --- FIX FOR THE WARNING ---
        // Check if the list of instructions is not empty before setting the adapter.
        if (careInfo.careInstructions.isNotEmpty()) {
            // If there is data, make sure the RecyclerView is visible and set the adapter.
            careInstructionsRecycler.visibility = View.VISIBLE
            val careAdapter = CareInstructionAdapter(careInfo.careInstructions)
            careInstructionsRecycler.adapter = careAdapter
        } else {
            // If there is no data, hide the RecyclerView to prevent the warning.
            careInstructionsRecycler.visibility = View.GONE
        }
    }

    private fun displayHealthAssessment(assessment: PlantHealthAssessment) {
        healthScoreText.text = "${assessment.overallHealth}/100"
        healthStatusText.text = getHealthStatusText(assessment.overallHealth)
        setHealthIndicatorColor(assessment.overallHealth)

        // --- FIX FOR THE WARNING ---
        // Your existing code here is already good, we just add the visibility toggle.
        if (assessment.healthIssues.isNotEmpty()) {
            // If there are issues, make sure the RecyclerView is visible and set the adapter.
            healthIssuesRecycler.visibility = View.VISIBLE
            val issuesAdapter = HealthIssueAdapter(assessment.healthIssues, assessment.recommendations)
            healthIssuesRecycler.adapter = issuesAdapter
        } else {
            // If there are no issues, hide the RecyclerView to prevent the warning.
            healthIssuesRecycler.visibility = View.GONE
        }
    }

    private fun addTagsToChipGroup(careInfo: PlantCareInfo) {
        tagsChipGroup.removeAllViews()

        val tags = mutableListOf<String>()
        if (careInfo.lightRequirement.contains("low", ignoreCase = true)) tags.add("Low Light")
        if (careInfo.waterRequirement.contains("low", ignoreCase = true)) tags.add("Low Water")
        if (careInfo.toxicity.contains("safe", ignoreCase = true)) tags.add("Pet Safe")
        if (careInfo.growthRate.contains("fast", ignoreCase = true)) tags.add("Fast Growing")

        tags.forEach { tag ->
            val chip = Chip(this)
            chip.text = tag
            chip.isClickable = false
            chip.isCheckable = false
            tagsChipGroup.addView(chip)
        }
    }

    private fun setHealthIndicatorColor(healthScore: Int) {
        val colorRes = when {
            healthScore >= 80 -> R.color.health_excellent
            healthScore >= 60 -> R.color.health_good
            healthScore >= 40 -> R.color.health_fair
            else -> R.color.health_poor
        }
        healthIndicator.setBackgroundColor(getColor(colorRes))
    }

    private fun getHealthStatusText(score: Int): String {
        return when {
            score >= 80 -> "Excellent Health"
            score >= 60 -> "Good Health"
            score >= 40 -> "Fair Health"
            else -> "Poor Health"
        }
    }

    private fun addPlantToGarden() {
        lifecycleScope.launch {
            try {
                val isInGarden = checkIfPlantInGarden()

                if (isInGarden) {
                    showSnackbar("Plant is already in your garden!")
                    return@launch
                }

                savePlantToGarden()

                runOnUiThread {
                    addToGardenButton.text = "In Garden"
                    addToGardenButton.setIconResource(R.drawable.ic_garden)
                    addToGardenButton.setOnClickListener {
                        showGardenOptions()
                    }
                }

                showSnackbar("Plant added to your garden!")

            } catch (e: Exception) {
                showSnackbar("Error adding plant to garden: ${e.message}")
            }
        }
    }

    private fun sharePlantInfo() {
        plantIdentification?.let { identification ->
            val shareText = buildShareText(identification)

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
                putExtra(Intent.EXTRA_SUBJECT, "Plant Identification: ${identification.commonName}")
            }

            currentBitmap?.let { bitmap ->
                val imageUri = saveBitmapToCache(bitmap)
                imageUri?.let { uri ->
                    shareIntent.apply {
                        type = "image/*"
                        putExtra(Intent.EXTRA_STREAM, uri)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                }
            }

            startActivity(Intent.createChooser(shareIntent, "Share Plant Information"))
        }
    }

    private fun buildShareText(identification: PlantIdentification): String {
        val confidence = (identification.confidence * 100).toInt()

        return buildString {
            appendLine("🌱 Plant Identification")
            appendLine("━━━━━━━━━━━━━━━━━━━━")
            appendLine("Common Name: ${identification.commonName}")
            appendLine("Scientific Name: ${identification.scientificName}")
            appendLine("Family: ${identification.family}")
            appendLine("Confidence: $confidence%")

            if (identification.healthScore > 0) {
                appendLine("Health Score: ${identification.healthScore}/100")
                appendLine("Health Status: ${identification.healthStatus}")
            }

            plantCareInfo?.let { careInfo ->
                appendLine()
                appendLine("💡 Care Tips:")
                appendLine("• Light: ${careInfo.lightRequirement}")
                appendLine("• Water: ${careInfo.waterRequirement}")
                appendLine("• Care Level: ${careInfo.careLevel}")
                appendLine("• Toxicity: ${careInfo.toxicity}")
            }

            appendLine()
            appendLine("Identified using PlantID App")
        }
    }

    private fun saveBitmapToCache(bitmap: Bitmap): Uri? {
        return try {
            val cachePath = File(cacheDir, "images")
            cachePath.mkdirs()

            val file = File(cachePath, "plant_image_${System.currentTimeMillis()}.png")
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.close()

            FileProvider.getUriForFile(
                this,
                "${applicationContext.packageName}.fileprovider",
                file
            )
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            loadingProgress.visibility = View.VISIBLE
            contentLayout.visibility = View.GONE
            errorLayout.visibility = View.GONE
        } else {
            loadingProgress.visibility = View.GONE
            contentLayout.visibility = View.VISIBLE
            errorLayout.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        runOnUiThread {
            loadingProgress.visibility = View.GONE
            contentLayout.visibility = View.GONE
            errorLayout.visibility = View.VISIBLE

            val errorText = errorLayout.findViewById<TextView>(R.id.errorText)
            errorText?.text = message
        }
    }

    private fun showSnackbar(message: String) {
        runOnUiThread {
            Snackbar.make(contentLayout, message, Snackbar.LENGTH_SHORT).show()
        }
    }
}
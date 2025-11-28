package com.example.plantidt

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantidt.database.PlantDatabase
import com.example.plantidt.database.PlantRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton


import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.plantidt.database.entities.UserGarden
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MyPlantsActivity : AppCompatActivity() {

    private lateinit var emptyStateLayout: LinearLayout
    private lateinit var emptyReminderStateLayout: LinearLayout
    private lateinit var plantsContentLayout: LinearLayout
    private lateinit var remindersContentLayout: LinearLayout
    private lateinit var plantsRecyclerView: RecyclerView
    private lateinit var remindersRecyclerView: RecyclerView
    private lateinit var tabPlants: TextView
    private lateinit var tabReminders: TextView
    private lateinit var addPlantFab: FloatingActionButton
    private lateinit var tabIndicator: View
    private lateinit var plantIdentificationService: ComprehensivePlantIdentificationService
    private lateinit var repository: PlantRepository
    private var currentPhotoPath: String? = null
    private var progressDialog: ProgressDialog? = null

    private var hasPlants = false
    private var hasReminders = false
    private val plants = mutableListOf<PlantItem>()
    private val reminders = mutableListOf<ReminderItem>()
    private var currentTabIsPlants = true
    private var isActivityDestroyed = false


    // REMOVED: Activity result launchers (we'll use MainActivity's functionality)
    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            currentPhotoPath?.let { path ->
                val bitmap = BitmapFactory.decodeFile(path)
                if (bitmap != null) {
                    showToast("Photo captured successfully! Analyzing...")
                    val imageUri = FileProvider.getUriForFile(
                        this,
                        "com.example.plantidt.fileprovider",
                        File(path)
                    )
                    identifyPlantInMyPlants(imageUri)
                } else {
                    showError("Failed to load captured photo")
                }
            }
        }
    }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageUri = result.data?.data
            if (imageUri != null) {
                showToast("Image selected successfully! Analyzing...")
                identifyPlantInMyPlants(imageUri)
            } else {
                showError("Failed to select image")
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_plants)

        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        val database = PlantDatabase.getDatabase(this)
        repository = PlantRepository(database)
        plantIdentificationService = ComprehensivePlantIdentificationService(this)

        initializeViews()
        setupClickListeners()
        setupRecyclerViews()
        loadPlantsFromDatabaseSafe()
        // updateUI()

        // Set initial tab state
        setActiveTab(true)
        val showPlantsTab = intent.getBooleanExtra("SHOW_PLANTS_TAB", true)
        if (showPlantsTab) {
            setActiveTab(true) // Show plants tab
        } else {
            setActiveTab(false) // Show reminders tab
        }
    }

    private fun initializeViews() {
        emptyStateLayout = findViewById(R.id.emptyStateLayout)
        emptyReminderStateLayout = findViewById(R.id.emptyReminderStateLayout)
        plantsContentLayout = findViewById(R.id.plantsContentLayout)
        remindersContentLayout = findViewById(R.id.remindersContentLayout)
        plantsRecyclerView = findViewById(R.id.plantsRecyclerView)
        remindersRecyclerView = findViewById(R.id.remindersRecyclerView)
        tabPlants = findViewById(R.id.tabPlants)
        tabReminders = findViewById(R.id.tabReminders)
        addPlantFab = findViewById(R.id.addPlantFab)
        tabIndicator = findViewById(R.id.tabIndicator)
    }

    private fun setupClickListeners() {
        // Back button
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            finish()
        }

        // Add plant/reminder FAB
        addPlantFab.setOnClickListener {
            if (currentTabIsPlants) {
                showAddPlantOptions()
            } else {
                // Directly open SetReminderActivity for custom reminder
                openSetReminderActivity()
            }
        }

        // Tab switching
        tabPlants.setOnClickListener {
            if (!currentTabIsPlants) {
                setActiveTab(true)
            }
        }

        tabReminders.setOnClickListener {
            if (currentTabIsPlants) {
                setActiveTab(false)
            }
        }

        // Favorite button
        findViewById<ImageView>(R.id.favoriteButton).setOnClickListener {
            showToast("Favorites - Coming soon!")
        }
    }

    private fun loadPlantsFromDatabaseSafe() {
        lifecycleScope.launch {
            try {
                repository.getAllGardenPlants()
                    .flowOn(Dispatchers.IO) // Ensure database operations on IO thread
                    .collect { gardenPlants ->
                        // Check if fragment/activity is still active
                        if (!isActive) return@collect

                        // Update UI on main thread (lifecycleScope is already Main)
                        updatePlantsUI(gardenPlants)
                    }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun updatePlantsUI(gardenPlants: List<UserGarden>) {
        // Double-check before any UI operations
        if (isFinishing || isDestroyed) return

        plants.clear()

        gardenPlants.forEach { gardenPlant ->
            plants.add(PlantItem(
                id = gardenPlant.plantId.hashCode(),
                name = gardenPlant.nickname,
                type = "Garden Plant",
                imageResource = R.drawable.plant_sample,
                location = gardenPlant.location,
                plantId = gardenPlant.plantId
            ))
        }

        hasPlants = plants.isNotEmpty()

        if (!isFinishing && !isDestroyed) {
            updateUI()
            plantsRecyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun handleError(exception: Exception) {
        if (isActive && !isFinishing && !isDestroyed) {
            showError("Error loading plants: ${exception.message}")
        } else {
            Log.e("MyPlantsActivity", "Error loading plants (activity not active): ${exception.message}")
        }
    }

    private fun setupRecyclerViews() {
        // Plants RecyclerView - Updated to handle plant clicks
        plantsRecyclerView.layoutManager = GridLayoutManager(this, 2)
        plantsRecyclerView.adapter = PlantsAdapter(plants) { plant ->
            // Navigate to PlantResultActivity with the plant's identification
            navigateToPlantDetails(plant)
        }

        // Reminders RecyclerView
        remindersRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        remindersRecyclerView.adapter = RemindersAdapter(reminders) { reminder ->
            showToast("Clicked on ${reminder.title}")
        }
    }

    // NEW: Navigate to plant details
    private fun navigateToPlantDetails(plant: PlantItem) {
        lifecycleScope.launch {
            try {
                // Get the most recent identification for this plant
                val identifications = repository.getIdentificationsByPlantId(plant.plantId)

                if (identifications.isNotEmpty()) {
                    // Get the most recent identification (they should be ordered by timestamp DESC)
                    val latestIdentification = identifications.first()

                    // Navigate to PlantResultActivity
                    val intent = Intent(this@MyPlantsActivity, PlantResultActivity::class.java)
                    intent.putExtra("identification_id", latestIdentification.id)
                    intent.putExtra("RETURN_TO", "MY_PLANTS")
                    startActivity(intent)
                } else {
                    // If no identification found, show a message
                    showToast("No identification data found for this plant")
                }
            } catch (e: Exception) {
                showError("Error loading plant details: ${e.message}")
            }
        }
    }

    private fun updateUI() {
        if (currentTabIsPlants) {
            updatePlantsUI()
        } else {
            updateRemindersUI()
        }

        // Update FAB icon and description based on current tab
        if (currentTabIsPlants) {
            addPlantFab.setImageResource(R.drawable.ic_plant_add)
            addPlantFab.contentDescription = "Add Plant"
        } else {
            addPlantFab.setImageResource(R.drawable.ic_reminder_add)
            addPlantFab.contentDescription = "Add Reminder"
        }

        // FAB is always visible
        addPlantFab.visibility = View.VISIBLE
    }

    private fun updatePlantsUI() {
        // Hide all other layouts first
        emptyReminderStateLayout.visibility = View.GONE
        remindersContentLayout.visibility = View.GONE

        if (hasPlants) {
            emptyStateLayout.visibility = View.GONE
            plantsContentLayout.visibility = View.VISIBLE
        } else {
            emptyStateLayout.visibility = View.VISIBLE
            plantsContentLayout.visibility = View.GONE
        }
    }

    private fun updateRemindersUI() {
        // Hide all other layouts first
        emptyStateLayout.visibility = View.GONE
        plantsContentLayout.visibility = View.GONE

        if (hasReminders) {
            emptyReminderStateLayout.visibility = View.GONE
            remindersContentLayout.visibility = View.VISIBLE
        } else {
            emptyReminderStateLayout.visibility = View.VISIBLE
            remindersContentLayout.visibility = View.GONE
        }
    }

    private fun setActiveTab(isPlantsTab: Boolean) {
        currentTabIsPlants = isPlantsTab

        // Update tab colors and styles
        if (isPlantsTab) {
            tabPlants.setTextColor(ContextCompat.getColor(this, R.color.primary_green))
            tabPlants.setTypeface(null, android.graphics.Typeface.BOLD)

            tabReminders.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))
            tabReminders.setTypeface(null, android.graphics.Typeface.NORMAL)

            // Move tab indicator to plants tab (left side)
            animateTabIndicator(true)
        } else {
            tabPlants.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))
            tabPlants.setTypeface(null, android.graphics.Typeface.NORMAL)

            tabReminders.setTextColor(ContextCompat.getColor(this, R.color.primary_green))
            tabReminders.setTypeface(null, android.graphics.Typeface.BOLD)

            // Move tab indicator to reminders tab (right side)
            animateTabIndicator(false)
        }

        // Update UI based on current tab
        updateUI()
    }

    private fun animateTabIndicator(toPlantsTab: Boolean) {
        val tabLayout = findViewById<LinearLayout>(R.id.tabLayout)
        val tabContainer = findViewById<RelativeLayout>(R.id.tabIndicatorContainer)

        tabLayout.post {
            val tabWidth = tabLayout.width / 2f
            val indicatorWidth = tabIndicator.width.toFloat()

            val plantsTabCenter = (tabWidth / 2f) - (indicatorWidth / 2f)
            val remindersTabCenter = tabWidth + (tabWidth / 2f) - (indicatorWidth / 2f)

            val targetX = if (toPlantsTab) plantsTabCenter else remindersTabCenter

            tabIndicator.animate()
                .x(targetX)
                .setDuration(300)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
        }
    }

    private fun showAddPlantOptions() {
        val options = arrayOf("Take Photo", "Upload from Gallery", "Add Manually")

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Add New Plant")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> navigateToMainActivityForCapture()
                    1 -> navigateToMainActivityForUpload()
                    2 -> addManualPlant()
                }
            }
            .show()
    }

    // NEW: Navigate to MainActivity for photo capture
    private fun navigateToMainActivityForCapture() {
        capturePhoto()
    }

    // UPDATED: Handle photo upload directly
    private fun navigateToMainActivityForUpload() {
        uploadImage()
    }

    // Open SetReminderActivity directly for custom reminder
    private fun openSetReminderActivity() {
        val intent = Intent(this, SetReminderActivity::class.java)
        startActivity(intent)
    }
    private fun capturePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            val photoFile = try {
                createImageFile()
            } catch (ex: IOException) {
                showError("Error creating image file")
                return
            }

            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    "com.example.plantidt.fileprovider",
                    it
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                takePictureLauncher.launch(intent)
            }
        } else {
            showError("Camera not available")
        }
    }
    private fun uploadImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        pickImageLauncher.launch(intent)
    }
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "PLANT_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }
    // ADD: Plant identification within MyPlants
    private fun identifyPlantInMyPlants(imageUri: Uri) {
        showLoading("Analyzing plant image...")

        lifecycleScope.launch {
            try {
                val result = plantIdentificationService.identifyPlantComprehensively(imageUri)
                hideLoading()

                if (result.isSuccess) {
                    val plantResult = result.getOrNull()!!
                    val imageBase64 = convertImageToBase64(imageUri)

                    // Save the result to database
                    val newIdentificationId = repository.saveComprehensiveResult(plantResult, imageBase64)

                    // Navigate to PlantResultActivity with return flag
                    val intent = Intent(this@MyPlantsActivity, PlantResultActivity::class.java)
                    intent.putExtra("identification_id", newIdentificationId)
                    intent.putExtra("RETURN_TO", "MY_PLANTS")
                    startActivity(intent)

                } else {
                    result.exceptionOrNull()?.let { error ->
                        handleIdentificationError(error)
                    }
                }
            } catch (e: Exception) {
                hideLoading()
                showError("Unexpected error during plant identification: ${e.message}")
            }
        }
    }
    // ADD: Helper methods
    private fun convertImageToBase64(uri: Uri): String {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            val maxHeight = 1024
            val maxWidth = 1024
            val width = originalBitmap.width
            val height = originalBitmap.height
            val ratio: Float = width.toFloat() / height.toFloat()

            val finalWidth: Int
            val finalHeight: Int

            if (width > maxWidth || height > maxHeight) {
                if (ratio > 1) {
                    finalWidth = maxWidth
                    finalHeight = (maxWidth / ratio).toInt()
                } else {
                    finalHeight = maxHeight
                    finalWidth = (maxHeight * ratio).toInt()
                }
            } else {
                finalWidth = width
                finalHeight = height
            }

            val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, finalWidth, finalHeight, true)
            val byteArrayOutputStream = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()

            Base64.encodeToString(byteArray, Base64.DEFAULT)
        } catch (e: Exception) {
            Log.e("ImageConversion", "Failed to convert and resize image", e)
            ""
        }
    }

    private fun showLoading(message: String) {
        progressDialog = ProgressDialog(this).apply {
            setTitle("Please wait")
            setMessage(message)
            setCancelable(false)
            show()
        }
    }

    private fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    private fun handleIdentificationError(error: Throwable) {
        val errorMessage = when {
            error.message?.contains("NetworkError") == true ->
                "Network connection failed. Please check your internet connection."
            error.message?.contains("API_LIMIT_EXCEEDED") == true ->
                "Daily API limit exceeded. Please try again tomorrow."
            error.message?.contains("INVALID_API_KEY") == true ->
                "API configuration error. Please contact support."
            error.message?.contains("TimeoutError") == true ->
                "Request timed out. Please try again."
            else ->
                "Plant identification failed: ${error.message}"
        }
        showError(errorMessage)
    }

    private fun showError(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideLoading()
        isActivityDestroyed = true
    }

    override fun onResume() {
        super.onResume()
        // Refresh the plants list when returning to this activity
        loadPlantsFromDatabaseSafe()
    }

    // REMOVED: capturePhoto() and uploadImage() methods - now handled by MainActivity

    private fun addManualPlant() {
        addSamplePlant("Monstera Deliciosa")
        showToast("Plant added manually!")
    }

    private fun addSamplePlant(name: String) {
        plants.add(PlantItem(
            id = plants.size + 1,
            name = name,
            type = "Indoor",
            imageResource = R.drawable.plant_sample,
            location = "Living Room",
            plantId = "sample_plant_${System.currentTimeMillis()}" // Add a unique plantId
        ))

        hasPlants = true
        updateUI()
        plantsRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun addSampleReminder(title: String) {
        reminders.add(ReminderItem(
            id = reminders.size + 1,
            title = title,
            description = "Remember to take care of your plants",
            time = "09:00 AM",
            isActive = true
        ))

        hasReminders = true
        updateUI()
        remindersRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

// Updated PlantItem data class to include plantId
data class PlantItem(
    val id: Int,
    val name: String,
    val type: String,
    val imageResource: Int,
    val location: String,
    val plantId: String // Added plantId to link to identification data
)

data class ReminderItem(
    val id: Int,
    val title: String,
    val description: String,
    val time: String,
    val isActive: Boolean
)

// Adapters remain the same
class PlantsAdapter(
    private val plants: List<PlantItem>,
    private val onPlantClick: (PlantItem) -> Unit
) : RecyclerView.Adapter<PlantsAdapter.PlantViewHolder>() {

    class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plantImage: ImageView = itemView.findViewById(R.id.plantImage)
        val plantName: TextView = itemView.findViewById(R.id.plantName)
        val plantType: TextView = itemView.findViewById(R.id.plantType)
        val plantCard: CardView = itemView.findViewById(R.id.plantCard)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): PlantViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plants[position]

        holder.plantName.text = plant.name
        holder.plantType.text = plant.type
        holder.plantImage.setImageResource(plant.imageResource)

        holder.plantCard.setOnClickListener {
            onPlantClick(plant)
        }

        // Add subtle animation on bind
        holder.plantCard.alpha = 0f
        holder.plantCard.animate()
            .alpha(1f)
            .setDuration(300)
            .setStartDelay(position * 50L)
            .start()
    }

    override fun getItemCount(): Int = plants.size
}

class RemindersAdapter(
    private val reminders: List<ReminderItem>,
    private val onReminderClick: (ReminderItem) -> Unit
) : RecyclerView.Adapter<RemindersAdapter.ReminderViewHolder>() {

    class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reminderTitle: TextView = itemView.findViewById(R.id.reminderTitle)
        val reminderDescription: TextView = itemView.findViewById(R.id.reminderDescription)
        val reminderTime: TextView = itemView.findViewById(R.id.reminderTime)
        val reminderCard: CardView = itemView.findViewById(R.id.reminderCard)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ReminderViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]

        holder.reminderTitle.text = reminder.title
        holder.reminderDescription.text = reminder.description
        holder.reminderTime.text = reminder.time

        holder.reminderCard.setOnClickListener {
            onReminderClick(reminder)
        }

        // Add subtle animation on bind
        holder.reminderCard.alpha = 0f
        holder.reminderCard.animate()
            .alpha(1f)
            .setDuration(300)
            .setStartDelay(position * 50L)
            .start()
    }

    override fun getItemCount(): Int = reminders.size
}
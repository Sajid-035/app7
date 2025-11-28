// Enhanced MainActivity.kt with Database Integration - FIXED
package com.example.plantidt

import android.animation.ValueAnimator
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import com.example.plantidt.database.daos.PlantDao
import com.example.plantidt.database.PlantDatabase
import com.example.plantidt.utils.PlantResultMapper
import android.util.Log
import com.example.plantidt.database.PlantRepository

class MainActivity : AppCompatActivity() {

    private lateinit var uploadSubtitle: TextView
    private lateinit var navHome: TextView
    private lateinit var navMyPlants: TextView
    private lateinit var navDiagnose: TextView

    // Updated service references
    private lateinit var plantIdentificationService: ComprehensivePlantIdentificationService
    private lateinit var plantDao: PlantDao
    private lateinit var database: PlantDatabase
    private lateinit var repository: PlantRepository

    private var progressDialog: ProgressDialog? = null
    private var currentPhotoPath: String? = null

    private var currentSubtitleIndex = 0

    private val subtitles = arrayOf(
        "Upload or capture your plant image for instant diagnosis",
        "Identify your plant species and get care recommendations",
        "Offline identification available when internet is unavailable"
    )

    private val handler = Handler(Looper.getMainLooper())
    private val subtitleRunnable = object : Runnable {
        override fun run() {
            animateSubtitleChange()
            handler.postDelayed(this, 4000)
        }
    }

    // Enhanced activity result launchers with full-size image support
    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            currentPhotoPath?.let { path ->
                val bitmap = BitmapFactory.decodeFile(path)
                if (bitmap != null) {
                    showToast("Photo captured successfully! Analyzing...")
                    // Convert file path to URI
                    val imageUri = FileProvider.getUriForFile(
                        this,
                        "com.example.plantidt.fileprovider",
                        File(path)
                    )
                    identifyPlant(imageUri)
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
                identifyPlant(imageUri)
            } else {
                showError("Failed to select image")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = PlantDatabase.getDatabase(this)
        repository = PlantRepository(database)

        plantDao = database.plantDao()
        plantIdentificationService = ComprehensivePlantIdentificationService(this)

        initializeViews()
        setupClickListeners()
        startSubtitleAnimation()
    }


    private fun initializeViews() {
        uploadSubtitle = findViewById(R.id.uploadSubtitle)
        navHome = findViewById(R.id.navHome)
        navMyPlants = findViewById(R.id.navMyPlants)
        navDiagnose = findViewById(R.id.navDiagnose)
    }

    private fun setupClickListeners() {
        findViewById<CardView>(R.id.menuCard).setOnClickListener {
            showToast("Menu opened! (Navigation drawer would slide in)")
        }

        findViewById<CardView>(R.id.notificationCard).setOnClickListener {
            showNotifications()
        }

        navHome.setOnClickListener { setActiveNav(it as TextView, 0) }
        navMyPlants.setOnClickListener { setActiveNav(it as TextView, 1) }
        navDiagnose.setOnClickListener { setActiveNav(it as TextView, 2) }

        findViewById<CardView>(R.id.uploadBtn).setOnClickListener {
            uploadImage()
        }

        findViewById<CardView>(R.id.captureBtn).setOnClickListener {
            capturePhoto()
        }

        findViewById<CardView>(R.id.guideCard).setOnClickListener {
            openGuide()
        }

        findViewById<CardView>(R.id.symptomCard).setOnClickListener {
            openSymptomChecker()
        }

        findViewById<CardView>(R.id.waterReminderCard).setOnClickListener {
            quickAction("water")
        }

        findViewById<CardView>(R.id.fertilizerCard).setOnClickListener {
            quickAction("fertilize")
        }

        findViewById<CardView>(R.id.careTipsCard).setOnClickListener {
            quickAction("care")
        }

        setupHoverEffects()
    }

    private fun setupHoverEffects() {
        val cards = listOf(
            R.id.guideCard,
            R.id.symptomCard,
            R.id.waterReminderCard,
            R.id.fertilizerCard,
            R.id.careTipsCard,
            R.id.uploadBtn,
            R.id.captureBtn
        )

        cards.forEach { cardId ->
            findViewById<CardView>(cardId).setOnTouchListener { view, event ->
                when (event.action) {
                    android.view.MotionEvent.ACTION_DOWN -> {
                        animateCardPress(view, true)
                    }
                    android.view.MotionEvent.ACTION_UP,
                    android.view.MotionEvent.ACTION_CANCEL -> {
                        animateCardPress(view, false)
                    }
                }
                false
            }
        }
    }

    private fun animateCardPress(view: View, pressed: Boolean) {
        val scaleX = if (pressed) 0.95f else 1.0f
        val scaleY = if (pressed) 0.95f else 1.0f
        val translationY = if (pressed) 8f else 0f

        view.animate()
            .scaleX(scaleX)
            .scaleY(scaleY)
            .translationY(translationY)
            .setDuration(150)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }

    private fun startSubtitleAnimation() {
        handler.post(subtitleRunnable)
    }

    private fun animateSubtitleChange() {
        val fadeOut = ValueAnimator.ofFloat(1f, 0f)
        fadeOut.duration = 300
        fadeOut.addUpdateListener { animation ->
            uploadSubtitle.alpha = animation.animatedValue as Float
        }

        fadeOut.addListener(object : android.animation.AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: android.animation.Animator) {
                currentSubtitleIndex = (currentSubtitleIndex + 1) % subtitles.size
                uploadSubtitle.text = subtitles[currentSubtitleIndex]

                val fadeIn = ValueAnimator.ofFloat(0f, 1f)
                fadeIn.duration = 300
                fadeIn.addUpdateListener { animation ->
                    uploadSubtitle.alpha = animation.animatedValue as Float
                }
                fadeIn.start()
            }
        })

        fadeOut.start()
    }

    private fun setActiveNav(selectedNav: TextView, index: Int) {
        val navPills = listOf(navHome, navMyPlants, navDiagnose)

        navPills.forEachIndexed { i, nav ->
            if (i == index) {
                nav.setBackgroundResource(R.drawable.nav_active_bg)
                nav.setTextColor(ContextCompat.getColor(this, R.color.white))
            } else {
                nav.setBackgroundResource(R.drawable.nav_inactive_bg)
                nav.setTextColor(ContextCompat.getColor(this, R.color.gray_text))
            }
        }

        when (index) {
            0 -> showToast("Home selected")
            1 -> {
                val intent = Intent(this, MyPlantsActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            2 -> {
                val intent = Intent(this, DiagnoseActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }

    private fun uploadImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        pickImageLauncher.launch(intent)
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

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "PLANT_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    // FIXED: Main plant identification method with proper type handling
    // Simple Fix - Replace the identifyPlant method with this version
// This avoids all type issues by using explicit variable declarations

    private fun identifyPlant(imageUri: Uri) {
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

                    // Simple navigation - no special handling needed
                    val intent = Intent(this@MainActivity, PlantResultActivity::class.java)
                    intent.putExtra("identification_id", newIdentificationId)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

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



    // NEW: Convert image URI to base64 string for database storage
    // In MainActivity.kt

    // Replace the existing function with this one
    private fun convertImageToBase64(uri: Uri): String {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            // --- RESIZING LOGIC ---
            val maxHeight = 1024 // Define a max height for the stored image
            val maxWidth = 1024  // Define a max width

            val width = originalBitmap.width
            val height = originalBitmap.height

            val ratio: Float = width.toFloat() / height.toFloat()

            val finalWidth: Int
            val finalHeight: Int

            if (width > maxWidth || height > maxHeight) {
                if (ratio > 1) { // Landscape
                    finalWidth = maxWidth
                    finalHeight = (maxWidth / ratio).toInt()
                } else { // Portrait or square
                    finalHeight = maxHeight
                    finalWidth = (maxHeight * ratio).toInt()
                }
            } else {
                finalWidth = width
                finalHeight = height
            }

            // Create the scaled bitmap
            val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, finalWidth, finalHeight, true)
            // --- END OF RESIZING LOGIC ---

            val byteArrayOutputStream = ByteArrayOutputStream()
            // Compress the RESIZED bitmap, not the original
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()

            Base64.encodeToString(byteArray, Base64.DEFAULT)
        } catch (e: Exception) {
            Log.e("ImageConversion", "Failed to convert and resize image", e)
            "" // Return empty string on failure
        }
    }

    // LEGACY: Keep for backward compatibility (if needed)
    private fun analyzeImageWithLoading(imageUri: Uri) {
        identifyPlant(imageUri)
    }

    private fun analyzeImageWithLoading(bitmap: Bitmap) {
        // Convert bitmap to temporary file and then to URI
        try {
            val tempFile = File.createTempFile("temp_plant", ".jpg", cacheDir)
            val fileOutputStream = tempFile.outputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream)
            fileOutputStream.close()

            val tempUri = FileProvider.getUriForFile(
                this,
                "com.example.plantidt.fileprovider",
                tempFile
            )

            identifyPlant(tempUri)
        } catch (e: Exception) {
            showError("Error processing image: ${e.message}")
        }
    }

    // FIXED: Updated validation method with proper type handling
    private fun validateAndNavigate(plantResult: Any, imageBase64: String) {
        try {
            // Validate that we have minimum required data
            if (plantResult == null) {
                showError("Plant identification failed - no result data")
                return
            }

            // Create intent with validated data
            val intent = Intent(this, PlantCareDetailActivity::class.java)

            // FIXED: Ensure all values are properly typed and non-null
            intent.putExtra("PLANT_NAME", "Sample Plant") // Use actual data
            intent.putExtra("SCIENTIFIC_NAME", "Plantus sampleus")
            intent.putExtra("CONFIDENCE", 0.85) // Direct Double value
            intent.putExtra("IS_HEALTHY", true)
            intent.putExtra("PLANT_IMAGE", imageBase64)
            intent.putExtra("PLANT_FAMILY", "Sample Family")
            intent.putExtra("PLANT_DESCRIPTION", "This is a sample plant description")

            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        } catch (e: Exception) {
            Log.e("MainActivity", "Error navigating to detail: ${e.message}", e)
            showError("Navigation error: ${e.message}")
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

    private fun showNotifications() {
        val notifications = arrayOf(
            "Water your Monstera deliciosa",
            "Fertilize your Snake Plant",
            "Check your Fiddle Leaf Fig for pests"
        )

        AlertDialog.Builder(this)
            .setTitle("Plant Care Reminders")
            .setItems(notifications) { _, which ->
                showToast("Selected: ${notifications[which]}")
            }
            .setNegativeButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun openGuide() {
        val intent = Intent(this, PlantGuideActivity::class.java)
        startActivity(intent)
    }

    private fun openSymptomChecker() {
        val intent = Intent(this, SymptomCheckerActivity::class.java)
        startActivity(intent)
    }

    private fun quickAction(type: String) {
        when (type) {
            "water", "fertilize" -> {
                val intent = Intent(this, SetReminderActivity::class.java)
                intent.putExtra("REMINDER_TYPE", type)
                startActivity(intent)
            }
            "care" -> {
                val intent = Intent(this, CareTipsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
        handler.removeCallbacks(subtitleRunnable)
        hideLoading()
    }
}
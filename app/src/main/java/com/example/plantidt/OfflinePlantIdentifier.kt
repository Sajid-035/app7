package com.example.plantidt

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.Interpreter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class OfflinePlantIdentifier(private val context: Context) {

    private var interpreter: Interpreter? = null
    private var isModelLoaded = false

    // --- FIX: This will be loaded from the assets file ---
    private lateinit var plantLabels: List<String>

    // --- These constants are fine for preprocessing ---
    private val imageMean = 127.5f
    private val imageStd = 127.5f

    init {
        // Load both the model AND the labels
        loadModelAndLabels()
    }

    // In OfflinePlantIdentifier.kt

    private fun loadModelAndLabels() {
        try {
            // --- NEW DEBUGGING CODE ---
            Log.d("ASSET_DEBUG", "Listing all files in the root of the assets folder:")
            val assetList = context.assets.list("")
            if (assetList.isNullOrEmpty()) {
                Log.d("ASSET_DEBUG", "The assets folder appears to be empty or does not exist in the APK.")
            } else {
                assetList.forEach { fileName ->
                    Log.d("ASSET_DEBUG", "Found asset: $fileName")
                }
            }
            // --- END OF DEBUGGING CODE ---


            // Load the TensorFlow Lite model
            val modelBuffer = loadModelFile("plant_model.tflite")
            val options = Interpreter.Options().apply {
                setNumThreads(4)
            }
            interpreter = Interpreter(modelBuffer, options)

            // Load labels from plant_labels.txt
            plantLabels = loadLabelsFile("plant_labels.txt")

            isModelLoaded = true
            Log.d("OfflinePlantIdentifier", "Model and ${plantLabels.size} labels loaded successfully.")
        } catch (e: Exception) {
            Log.e("OfflinePlantIdentifier", "Error loading model or labels", e)
            isModelLoaded = false
        }
    }

    // --- FIX: New function to load the labels file ---
    @Throws(IOException::class)
    private fun loadLabelsFile(fileName: String): List<String> {
        val labels = mutableListOf<String>()
        context.assets.open(fileName).use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    labels.add(line!!)
                }
            }
        }
        return labels
    }

    private fun loadModelFile(modelName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    suspend fun identifyPlant(bitmap: Bitmap): PlantIdentificationResult {
        return withContext(Dispatchers.Default) {
            if (isModelLoaded) {
                try {
                    runInference(bitmap)
                } catch (e: Exception) {
                    Log.e("OfflinePlantIdentifier", "Error during inference", e)
                    getFallbackResult(bitmap)
                }
            } else {
                getFallbackResult(bitmap)
            }
        }
    }

    // In OfflinePlantIdentifier.kt

    // In OfflinePlantIdentifier.kt

    private fun runInference(bitmap: Bitmap): PlantIdentificationResult {
        if (!::plantLabels.isInitialized) {
            Log.e("OfflinePlantIdentifier", "CRITICAL: Labels were not loaded. Cannot run inference.")
            return getFallbackResult(bitmap)
        }

        val currentInterpreter = this.interpreter ?: run {
            Log.e("OfflinePlantIdentifier", "CRITICAL: Interpreter is null. Cannot run inference.")
            return getFallbackResult(bitmap)
        }

        val inputBuffer = preprocessImage(bitmap)
        Log.d("OfflinePlantIdentifier", "Image preprocessed successfully for inference.")

        // --- THE FIX IS HERE ---
        // 1. Create an output buffer for BYTES (UINT8), not floats.
        val outputBuffer = Array(1) { ByteArray(plantLabels.size) }
        Log.d("OfflinePlantIdentifier", "Running inference with interpreter. Output buffer size: ${plantLabels.size}")

        try {
            currentInterpreter.run(inputBuffer, outputBuffer)
        } catch (e: Exception) {
            Log.e("OfflinePlantIdentifier", "TFLite interpreter.run() threw an exception!", e)
            return getFallbackResult(bitmap)
        }
        Log.d("OfflinePlantIdentifier", "Inference completed.")

        // 2. Process the BYTE results.
        val confidences = outputBuffer[0]
        var maxIndex = -1
        var maxConfidence = -1f

        // Iterate through the byte array to find the max confidence.
        for (i in confidences.indices) {
            // Convert the unsigned byte (0-255) to a float probability (0.0-1.0)
            val confidence = (confidences[i].toInt() and 0xFF) / 255.0f
            if (confidence > maxConfidence) {
                maxConfidence = confidence
                maxIndex = i
            }
        }
        // --- END OF FIX ---

        if (maxIndex == -1) {
            Log.e("OfflinePlantIdentifier", "Could not find a max confidence value.")
            return getFallbackResult(bitmap)
        }

        // Logging the top 5 results
        val top5Indices = confidences.indices.sortedByDescending { (it.toInt() and 0xFF) }.take(5)
        Log.d("OfflinePlantIdentifier", "--- Top 5 Predictions ---")
        top5Indices.forEach { index ->
            val confidence = (confidences[index].toInt() and 0xFF) / 255.0f
            Log.d("OfflinePlantIdentifier", "  - Label: '${plantLabels[index]}', Confidence: $confidence")
        }
        Log.d("OfflinePlantIdentifier", "--------------------------")

        val plantName = plantLabels[maxIndex].replaceFirstChar { it.titlecase() }
        val scientificName = plantName

        return PlantIdentificationResult(
            plantName = plantName,
            scientificName = scientificName,
            confidence = maxConfidence.toDouble(), // Use the float confidence we calculated
            isHealthy = true,
            diseases = emptyList(),
            careInstructions = getBasicCareInstructions(plantName)
        )
    }

    // In OfflinePlantIdentifier.kt

    private fun preprocessImage(bitmap: Bitmap): ByteBuffer {
        val modelInputWidth = 224
        val modelInputHeight = 224

        // --- FIX 1: Correct Buffer Size ---
        // The model expects UINT8 values (1 byte per channel), not FLOAT (4 bytes).
        // The size is width * height * 3 channels * 1 byte.
        val inputBuffer = ByteBuffer.allocateDirect(modelInputWidth * modelInputHeight * 3)
        inputBuffer.order(ByteOrder.nativeOrder())

        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, modelInputWidth, modelInputHeight, true)

        val pixels = IntArray(modelInputWidth * modelInputHeight)
        scaledBitmap.getPixels(pixels, 0, scaledBitmap.width, 0, 0, scaledBitmap.width, scaledBitmap.height)

        var pixelIndex = 0
        for (i in 0 until modelInputWidth) {
            for (j in 0 until modelInputHeight) {
                val pixelValue = pixels[pixelIndex++]

                // --- FIX 2: Put Bytes, Not Floats ---
                // The model expects byte values from 0-255. No normalization is needed for this specific model.
                // Get the R, G, B channels as bytes.
                val r = (pixelValue shr 16 and 0xFF).toByte()
                val g = (pixelValue shr 8 and 0xFF).toByte()
                val b = (pixelValue and 0xFF).toByte()

                // Put the bytes into the buffer.
                inputBuffer.put(r)
                inputBuffer.put(g)
                inputBuffer.put(b)
            }
        }

        return inputBuffer
    }

    private fun getFallbackResult(bitmap: Bitmap? = null): PlantIdentificationResult {
        // Basic fallback using simple image analysis
        val plantName = if (bitmap != null) {
            analyzeImageBasically(bitmap)
        } else {
            "Unknown Plant"
        }

        return PlantIdentificationResult(
            plantName = plantName,
            scientificName = "Unknown",
            confidence = 0.5, // Low confidence for fallback
            isHealthy = true,
            diseases = emptyList(),
            careInstructions = getBasicCareInstructions(plantName)
        )
    }

    private fun analyzeImageBasically(bitmap: Bitmap): String {
        // Very basic analysis based on dominant colors
        val colors = analyzeColors(bitmap)

        return when {
            colors.greenRatio > 0.6 -> "Leafy Plant"
            colors.brownRatio > 0.3 -> "Woody Plant"
            colors.redRatio > 0.3 -> "Flowering Plant"
            else -> "Unknown Plant"
        }
    }

    private fun analyzeColors(bitmap: Bitmap): ColorAnalysis {
        val smallBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true)
        val pixels = IntArray(100 * 100)
        smallBitmap.getPixels(pixels, 0, 100, 0, 0, 100, 100)

        var greenCount = 0
        var brownCount = 0
        var redCount = 0

        for (pixel in pixels) {
            val r = (pixel shr 16) and 0xFF
            val g = (pixel shr 8) and 0xFF
            val b = pixel and 0xFF

            when {
                g > r && g > b && g > 100 -> greenCount++
                r > 100 && g > 50 && b < 100 -> brownCount++
                r > g && r > b && r > 100 -> redCount++
            }
        }

        val total = pixels.size
        return ColorAnalysis(
            greenRatio = greenCount.toDouble() / total,
            brownRatio = brownCount.toDouble() / total,
            redRatio = redCount.toDouble() / total
        )
    }

    private fun getBasicCareInstructions(plantName: String): String {
        return when {
            plantName.contains("Monstera", ignoreCase = true) ->
                "Water when top inch of soil is dry. Provide bright, indirect light. Humidity 40-50%."
            plantName.contains("Ficus", ignoreCase = true) ->
                "Water when top 2 inches of soil are dry. Needs bright, indirect light. Avoid drafts."
            plantName.contains("Snake Plant", ignoreCase = true) ->
                "Water sparingly, every 2-3 weeks. Tolerates low light. Very low maintenance."
            plantName.contains("Pothos", ignoreCase = true) ->
                "Water when soil feels dry. Tolerates low to bright light. Easy to propagate."
            plantName.contains("Aloe", ignoreCase = true) ->
                "Water sparingly. Needs bright light. Well-draining soil is essential."
            plantName.contains("Rose", ignoreCase = true) ->
                "Water regularly but avoid wet leaves. Needs full sun. Prune regularly."
            plantName.contains("Leafy Plant", ignoreCase = true) ->
                "Most leafy plants need regular watering and indirect light. Check soil moisture regularly."
            else ->
                "Provide appropriate light, water when soil feels dry, and maintain good drainage."
        }
    }

    fun cleanup() {
        interpreter?.close()
        interpreter = null
    }
}

data class ColorAnalysis(
    val greenRatio: Double,
    val brownRatio: Double,
    val redRatio: Double
)
package com.example.plantidt

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit
import com.example.plantidt.network.ToxicityInfo
import org.json.JSONException
import java.util.Locale

// Basic Plant Identification Result data class (for offline fallback)
data class PlantIdentificationResult(
    val plantName: String,
    val scientificName: String,
    val confidence: Double,
    val isHealthy: Boolean,
    val diseases: List<Disease>,
    val careInstructions: String,
)

// Disease data class
data class Disease(
    val name: String,
    val confidence: Double,
    val description: String,
    val treatment: String
)

// Enhanced data models for comprehensive plant information
data class ComprehensivePlantResult(
    val plantName: String,
    val scientificName: String,
    val familyName: String,
    val confidence: Double,
    val isHealthy: Boolean,
    val diseases: List<Disease>,
    val careInstructions: DetailedCareInstructions,
    val plantFacts: PlantFacts,
    val toxicityInfo: ToxicityInfo,
    val isOfflineResult: Boolean = false
)

data class DetailedCareInstructions(
    val watering: String,
    val lighting: String,
    val temperature: String,
    val humidity: String,
    val fertilizing: String,
    val pruning: String,
    val soilType: String,
    val commonProblems: List<String>
)

data class PlantFacts(
    val origin: String,
    val description: String,
    val bloomTime: String,
    val mature_size: String,
    val growthRate: String,
    val lifespan: String,
    val uses: List<String>
)


class ComprehensivePlantIdentificationService(private val context: Context) {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Multiple API endpoints for comprehensive plant data
    private val PLANT_ID_API_KEY = "aj4sND2aJyS5FCVKcZGFmXnRxQ7H6IBoqAOV1tf168l0rTp7aD"
    private val PLANT_NET_API_KEY = "2b10sZr7hIRPfdIF6AwzqzwYu"
    private val TREFLE_API_KEY = "4v4oSCzZe7_MyUd0jMD1ekjGD4_ZOgi_3eMKtYNaYMo"
    private val GBIF_API_URL = "https://api.gbif.org/v1/"

    // Primary identification using Plant.id API
    // In ComprehensivePlantIdentificationService.kt

    suspend fun identifyPlantComprehensively(imageUri: Uri): Result<ComprehensivePlantResult> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("OFFLINE_DEBUG", "Starting comprehensive identification...")
                val bitmap = uriToBitmap(imageUri)
                val base64Image = bitmapToBase64(bitmap)

                val identificationResult = identifyWithPlantId(base64Image)

                if (identificationResult.isSuccess) {
                    Log.d("OFFLINE_DEBUG", "Online identification was successful.")
                    val basicResult = identificationResult.getOrThrow()
                    val enrichedResult = enrichPlantData(basicResult)
                    Result.success(enrichedResult)
                } else {
                    Log.w("OFFLINE_DEBUG", "Online call was unsuccessful. Falling back to offline path A.")
                    val offlineResult = getOfflineFallback(bitmap)
                    Log.d("OFFLINE_DEBUG", "Offline fallback A produced: ${offlineResult.plantName}")
                    Result.success(offlineResult)
                }
            } catch (e: Exception) {
                Log.e("OFFLINE_DEBUG", "Exception caught! Falling back to offline path B.", e)
                try {
                    val bitmap = uriToBitmap(imageUri)
                    val offlineResult = getOfflineFallback(bitmap)
                    Log.d("OFFLINE_DEBUG", "Offline fallback B produced: ${offlineResult.plantName}")
                    Result.success(offlineResult)
                } catch (fallbackException: Exception) {
                    Log.e("OFFLINE_DEBUG", "CRITICAL: The offline fallback itself failed!", fallbackException)
                    Result.failure(fallbackException)
                }
            }
        }
    }
    // In ComprehensivePlantIdentificationService.kt

    // In ComprehensivePlantIdentificationService.kt

    private suspend fun identifyWithPlantId(base64Image: String): Result<ComprehensivePlantResult> {
        return withContext(Dispatchers.IO) {
            try {
                // Define the JSON object first
                val json = JSONObject().apply {
                    put("api_key", PLANT_ID_API_KEY)
                    put("images", JSONArray().apply { put(base64Image) })
                    put("modifiers", JSONArray().apply {
                        put("crops_fast")
                        put("similar_images")

                        put("disease_similar_images")
                    })
                    put("plant_language", "en")
                    put("plant_details", JSONArray().apply {
                        put("common_names")
                        put("url")
                        put("name_authority")
                        put("wiki_description")
                        put("taxonomy")
                        put("synonyms")
                        put("edible_parts")
                        put("watering")
                        put("propagation_methods")
                    })
                }

                // Now build the request
                val requestBody = json.toString().toRequestBody("application/json".toMediaType())
                val request = Request.Builder()
                    .url("https://api.plant.id/v2/identify")
                    .post(requestBody)
                    .build()

                // Execute the call and get the response
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string() // Read the body ONCE and store it

                // **** CORRECTED LOGGING BLOCK ****
                // Logs are now placed after the network call is complete
                Log.d("API_DEBUG", "Request URL: https://api.plant.id/v2/identify")
                Log.d("API_DEBUG", "Response Code: ${response.code}")
                Log.d("API_DEBUG", "Response Body: $responseBody")
                // *******************************

                // Process the response
                if (response.isSuccessful) {
                    if (!responseBody.isNullOrEmpty()) {
                        val result = parsePlantIdResponse(responseBody)
                        // Add a check for "Unknown" to help with debugging
                        if (result.plantName == "Unknown Plant") {
                            Log.w("API_DEBUG", "Parsing resulted in 'Unknown Plant'. The 'suggestions' array was likely empty in the response body.")
                        }
                        Result.success(result)
                    } else {
                        Log.e("API_DEBUG", "API call successful but response body was empty.")
                        Result.failure(Exception("Empty response body"))
                    }
                } else {
                    Log.e("API_DEBUG", "API Error. Code: ${response.code}, Body: $responseBody")
                    Result.failure(Exception("API Error: ${response.code}"))
                }
            } catch (e: Exception) {
                Log.e("API_DEBUG", "An exception occurred during the API call.", e)
                Result.failure(e)
            }
        }
    }
    private suspend fun enrichPlantData(basicResult: ComprehensivePlantResult): ComprehensivePlantResult {
        // Enrich with additional data from multiple sources
        val enrichedCareInstructions = getDetailedCareInstructions(basicResult.scientificName)
        val plantFacts = getPlantFacts(basicResult.scientificName)
        val toxicityInfo = getToxicityInfo(basicResult.scientificName)

        return basicResult.copy(
            careInstructions = enrichedCareInstructions,
            plantFacts = plantFacts,
            toxicityInfo = toxicityInfo
        )
    }

    // In ComprehensivePlantIdentificationService.kt

    // In ComprehensivePlantIdentificationService.kt

    private fun parsePlantIdResponse(responseBody: String): ComprehensivePlantResult {
        try {
            val jsonResponse = JSONObject(responseBody)
            val suggestions = jsonResponse.optJSONArray("suggestions")

            if (suggestions != null && suggestions.length() > 0) {
                val bestMatch = suggestions.getJSONObject(0)
                val plantDetails = bestMatch.getJSONObject("plant_details")

                // --- FIX IS HERE ---

                // 1. Get the primary scientific name. This is our reliable fallback.
                val scientificName = bestMatch.getString("plant_name")

                // 2. Try to get a user-friendly common name.
                val plantName = try {
                    val commonNamesArray = plantDetails.optJSONArray("common_names")
                    if (commonNamesArray != null && commonNamesArray.length() > 0) {
                        // Get the first common name and capitalize it nicely.
                        commonNamesArray.getString(0).replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                        }
                    } else {
                        // If no common names are available, use the scientific name as the display name.
                        scientificName
                    }
                } catch (e: Exception) {
                    // In case of any error, fall back to the scientific name.
                    scientificName
                }

                // --- END OF FIX ---

                val familyName = try {
                    plantDetails.getJSONObject("taxonomy").getString("family")
                } catch (e: Exception) {
                    ""
                }

                val confidence = bestMatch.getDouble("probability")
                val diseases = parseDiseasesFromResponse(jsonResponse)
                val isHealthy = diseases.isEmpty()

                val basicCareInstructions = DetailedCareInstructions(
                    watering = extractWateringInfo(plantDetails),
                    lighting = extractLightingInfo(plantDetails),
                    temperature = "Room temperature (18-24°C)",
                    humidity = "Moderate humidity (40-60%)",
                    fertilizing = "Monthly during growing season",
                    pruning = "As needed to maintain shape",
                    soilType = "Well-draining potting mix",
                    commonProblems = listOf("Overwatering", "Insufficient light", "Pests")
                )

                val plantFacts = PlantFacts(
                    origin = "Unknown",
                    description = plantDetails.optJSONObject("wiki_description")?.optString("value") ?: "No description available.",
                    bloomTime = "Varies by species",
                    mature_size = "Varies by species",
                    growthRate = "Moderate",
                    lifespan = "Perennial",
                    uses = listOf("Ornamental", "Houseplant")
                )

                val toxicityInfo = ToxicityInfo(
                    isToxic = false,
                    toxicToPets = false,
                    toxicToHumans = false,
                    toxicityLevel = "Unknown",
                    symptoms = emptyList(),
                    treatment = "Consult healthcare provider if ingested"
                )

                return ComprehensivePlantResult(
                    plantName = plantName, // Now this will be the common name (e.g., "Monterey Cypress")
                    scientificName = scientificName, // This remains the scientific name (e.g., "Hesperocyparis macrocarpa")
                    familyName = familyName,
                    confidence = confidence,
                    isHealthy = isHealthy,
                    diseases = diseases,
                    careInstructions = basicCareInstructions,
                    plantFacts = plantFacts,
                    toxicityInfo = toxicityInfo,
                    isOfflineResult = false
                )
            }

            Log.w("API_DEBUG", "The 'suggestions' array was missing or empty in the API response. Returning 'Unknown Plant'.")
            return getUnknownPlantResult()

        } catch (e: JSONException) {
            Log.e("API_DEBUG", "Failed to parse the JSON response.", e)
            return getUnknownPlantResult()
        }
    }

    private suspend fun getDetailedCareInstructions(scientificName: String): DetailedCareInstructions {
        return withContext(Dispatchers.IO) {
            try {
                // Query Trefle API for detailed care information
                val url = "https://trefle.io/api/v1/plants/search?token=$TREFLE_API_KEY&q=$scientificName"
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        return@withContext parseTrefleResponse(responseBody)
                    }
                }
            } catch (e: Exception) {
                Log.e("PlantService", "Error fetching care instructions", e)
            }

            // Fallback to knowledge-based care instructions
            getKnowledgeBasedCareInstructions(scientificName)
        }
    }

    private fun parseTrefleResponse(responseBody: String): DetailedCareInstructions {
        try {
            val jsonResponse = JSONObject(responseBody)
            val data = jsonResponse.getJSONArray("data")

            if (data.length() > 0) {
                val plant = data.getJSONObject(0)

                return DetailedCareInstructions(
                    watering = plant.optString("watering", "Regular watering"),
                    lighting = plant.optString("light", "Bright, indirect light"),
                    temperature = plant.optString("temperature", "18-24°C"),
                    humidity = plant.optString("humidity", "Moderate"),
                    fertilizing = plant.optString("fertilizing", "Monthly during growing season"),
                    pruning = plant.optString("pruning", "As needed"),
                    soilType = plant.optString("soil", "Well-draining soil"),
                    commonProblems = listOf("Overwatering", "Pests", "Insufficient light")
                )
            }
        } catch (e: Exception) {
            Log.e("PlantService", "Error parsing Trefle response", e)
        }

        return getDefaultCareInstructions()
    }

    private fun getKnowledgeBasedCareInstructions(scientificName: String): DetailedCareInstructions {
        return when {
            scientificName.contains("Monstera", ignoreCase = true) -> DetailedCareInstructions(
                watering = "Water when top 1-2 inches of soil are dry. Reduce watering in winter.",
                lighting = "Bright, indirect light. Avoid direct sunlight which can scorch leaves.",
                temperature = "18-27°C (65-80°F). Avoid temperatures below 15°C.",
                humidity = "50-70% humidity. Use humidifier or pebble tray in dry conditions.",
                fertilizing = "Feed monthly with balanced liquid fertilizer during growing season.",
                pruning = "Remove dead or yellowing leaves. Prune aerial roots if desired.",
                soilType = "Well-draining potting mix with peat moss and perlite.",
                commonProblems = listOf("Overwatering", "Low humidity", "Insufficient light", "Spider mites")
            )

            scientificName.contains("Ficus", ignoreCase = true) -> DetailedCareInstructions(
                watering = "Water when top 2 inches of soil are dry. Avoid overwatering.",
                lighting = "Bright, indirect light. Can tolerate some direct morning sun.",
                temperature = "18-24°C (65-75°F). Avoid cold drafts and sudden temperature changes.",
                humidity = "40-50% humidity. Regular misting can help.",
                fertilizing = "Feed bi-weekly during growing season with diluted fertilizer.",
                pruning = "Regular pruning to maintain shape. Remove dead or damaged leaves.",
                soilType = "Well-draining potting soil with good aeration.",
                commonProblems = listOf("Leaf drop from stress", "Overwatering", "Scale insects", "Drafts")
            )

            scientificName.contains("Sansevieria", ignoreCase = true) -> DetailedCareInstructions(
                watering = "Water every 2-3 weeks. Allow soil to dry completely between waterings.",
                lighting = "Tolerates low light but prefers bright, indirect light.",
                temperature = "15-27°C (60-80°F). Very tolerant of temperature fluctuations.",
                humidity = "Low to moderate humidity. Very adaptable.",
                fertilizing = "Feed once or twice during growing season with diluted fertilizer.",
                pruning = "Remove damaged leaves at soil level. Divide when overcrowded.",
                soilType = "Well-draining cactus or succulent mix.",
                commonProblems = listOf("Overwatering", "Root rot", "Mealybugs")
            )

            else -> getDefaultCareInstructions()
        }
    }

    private fun getDefaultCareInstructions(): DetailedCareInstructions {
        return DetailedCareInstructions(
            watering = "Water when top inch of soil feels dry",
            lighting = "Bright, indirect light",
            temperature = "18-24°C (65-75°F)",
            humidity = "Moderate humidity (40-60%)",
            fertilizing = "Monthly during growing season",
            pruning = "Remove dead or damaged parts as needed",
            soilType = "Well-draining potting mix",
            commonProblems = listOf("Overwatering", "Insufficient light", "Pests")
        )
    }

    private suspend fun getPlantFacts(scientificName: String): PlantFacts {
        return withContext(Dispatchers.IO) {
            try {
                // Query GBIF API for taxonomic and ecological information
                val url = "$GBIF_API_URL/species/search?q=$scientificName&limit=1"
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        return@withContext parseGBIFResponse(responseBody)
                    }
                }
            } catch (e: Exception) {
                Log.e("PlantService", "Error fetching plant facts", e)
            }

            getDefaultPlantFacts()
        }
    }

    private fun parseGBIFResponse(responseBody: String): PlantFacts {
        try {
            val jsonResponse = JSONObject(responseBody)
            val results = jsonResponse.getJSONArray("results")

            if (results.length() > 0) {
                val plant = results.getJSONObject(0)

                return PlantFacts(
                    origin = plant.optString("origin", "Unknown"),
                    description = plant.optString("description", ""),
                    bloomTime = "Varies by species",
                    mature_size = "Varies by species",
                    growthRate = "Moderate",
                    lifespan = "Perennial",
                    uses = listOf("Ornamental", "Houseplant")
                )
            }
        } catch (e: Exception) {
            Log.e("PlantService", "Error parsing GBIF response", e)
        }

        return getDefaultPlantFacts()
    }

    private fun getDefaultPlantFacts(): PlantFacts {
        return PlantFacts(
            origin = "Unknown",
            description = "A beautiful plant species",
            bloomTime = "Varies by species",
            mature_size = "Varies by species",
            growthRate = "Moderate",
            lifespan = "Perennial",
            uses = listOf("Ornamental", "Houseplant")
        )
    }

    private suspend fun getToxicityInfo(scientificName: String): ToxicityInfo {
        return withContext(Dispatchers.IO) {
            // This would typically query a toxicity database like ASPCA or other veterinary databases
            // For now, we'll use a knowledge-based approach with common toxic plants

            val knownToxicPlants = mapOf(
                "Philodendron" to ToxicityInfo(
                    isToxic = true,
                    toxicToPets = true,
                    toxicToHumans = true,
                    toxicityLevel = "Medium",
                    symptoms = listOf("Oral irritation", "Difficulty swallowing", "Vomiting", "Diarrhea"),
                    treatment = "Rinse mouth immediately. Seek medical attention if symptoms persist."
                ),
                "Dieffenbachia" to ToxicityInfo(
                    isToxic = true,
                    toxicToPets = true,
                    toxicToHumans = true,
                    toxicityLevel = "High",
                    symptoms = listOf("Severe oral irritation", "Difficulty speaking", "Swelling of mouth and throat"),
                    treatment = "Immediate medical attention required. Rinse mouth thoroughly."
                ),
                "Monstera" to ToxicityInfo(
                    isToxic = true,
                    toxicToPets = true,
                    toxicToHumans = true,
                    toxicityLevel = "Medium",
                    symptoms = listOf("Oral irritation", "Difficulty swallowing", "Vomiting"),
                    treatment = "Rinse mouth with water. Contact veterinarian if pet shows symptoms."
                ),
                "Pothos" to ToxicityInfo(
                    isToxic = true,
                    toxicToPets = true,
                    toxicToHumans = true,
                    toxicityLevel = "Medium",
                    symptoms = listOf("Oral irritation", "Vomiting", "Difficulty swallowing"),
                    treatment = "Rinse mouth immediately. Monitor for symptoms."
                ),
                "Ficus" to ToxicityInfo(
                    isToxic = true,
                    toxicToPets = true,
                    toxicToHumans = false,
                    toxicityLevel = "Low",
                    symptoms = listOf("Skin irritation", "Mild stomach upset in pets"),
                    treatment = "Wash skin if irritated. Monitor pets for digestive issues."
                ),
                "Sansevieria" to ToxicityInfo(
                    isToxic = true,
                    toxicToPets = true,
                    toxicToHumans = false,
                    toxicityLevel = "Low",
                    symptoms = listOf("Nausea", "Vomiting", "Diarrhea in pets"),
                    treatment = "Generally mild. Provide water and monitor."
                ),
                "Aloe" to ToxicityInfo(
                    isToxic = false,
                    toxicToPets = true,
                    toxicToHumans = false,
                    toxicityLevel = "Low",
                    symptoms = listOf("Vomiting", "Diarrhea", "Lethargy in pets"),
                    treatment = "Monitor pets. Provide water and contact vet if symptoms worsen."
                )
            )

            // Check if plant is in known toxic plants
            val plantGenus = scientificName.split(" ").firstOrNull()?.lowercase() ?: ""

            knownToxicPlants.entries.find {
                plantGenus.contains(it.key.lowercase()) || scientificName.contains(it.key, ignoreCase = true)
            }?.value ?: ToxicityInfo(
                isToxic = false,
                toxicToPets = false,
                toxicToHumans = false,
                toxicityLevel = "Unknown",
                symptoms = emptyList(),
                treatment = "Consult healthcare provider or veterinarian if ingested"
            )
        }
    }

    private fun parseDiseasesFromResponse(jsonResponse: JSONObject): List<Disease> {
        val diseases = mutableListOf<Disease>()

        try {
            if (jsonResponse.has("health_assessment")) {
                val healthAssessment = jsonResponse.getJSONObject("health_assessment")

                if (healthAssessment.has("diseases")) {
                    val diseasesArray = healthAssessment.getJSONArray("diseases")
                    for (i in 0 until diseasesArray.length()) {
                        val disease = diseasesArray.getJSONObject(i)
                        diseases.add(
                            Disease(
                                name = disease.getString("name"),
                                confidence = disease.getDouble("probability"),
                                description = disease.optString("description", "No description available"),
                                treatment = disease.optString("treatment", "Consult a plant specialist for treatment options")
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.w("PlantService", "Error parsing disease information", e)
        }

        return diseases
    }

    private fun extractWateringInfo(plantDetails: JSONObject): String {
        return try {
            val watering = plantDetails.optJSONObject("watering")
            if (watering != null) {
                val frequency = watering.optString("frequency", "")
                val amount = watering.optString("amount", "")
                if (frequency.isNotEmpty() && amount.isNotEmpty()) {
                    "$frequency - $amount"
                } else if (frequency.isNotEmpty()) {
                    frequency
                } else {
                    "Water when top inch of soil is dry"
                }
            } else {
                "Water when top inch of soil is dry"
            }
        } catch (e: Exception) {
            "Water when top inch of soil is dry"
        }
    }

    private fun extractLightingInfo(plantDetails: JSONObject): String {
        return try {
            val lighting = plantDetails.optString("light", "")
            if (lighting.isNotEmpty()) {
                lighting
            } else {
                "Bright, indirect light"
            }
        } catch (e: Exception) {
            "Bright, indirect light"
        }
    }

    private suspend fun getOfflineFallback(bitmap: Bitmap): ComprehensivePlantResult {
        // Use offline identifier as fallback
        val offlineIdentifier = OfflinePlantIdentifier(context)
        val basicResult = offlineIdentifier.identifyPlant(bitmap)

        return ComprehensivePlantResult(
            plantName = basicResult.plantName,
            scientificName = basicResult.scientificName,
            familyName = "",
            confidence = basicResult.confidence,
            isHealthy = basicResult.isHealthy,
            diseases = basicResult.diseases,
            careInstructions = convertToDetailedCareInstructions(basicResult.careInstructions),
            plantFacts = getDefaultPlantFacts(),
            toxicityInfo = ToxicityInfo(
                isToxic = false,
                toxicToPets = false,
                toxicToHumans = false,
                toxicityLevel = "Unknown",
                symptoms = emptyList(),
                treatment = "Consult healthcare provider if ingested"
            ),
            isOfflineResult = true
        )
    }

    private fun convertToDetailedCareInstructions(basicCare: String): DetailedCareInstructions {
        return DetailedCareInstructions(
            watering = if (basicCare.contains("water", ignoreCase = true)) basicCare else "Water when top inch of soil is dry",
            lighting = if (basicCare.contains("light", ignoreCase = true)) basicCare else "Bright, indirect light",
            temperature = "18-24°C (65-75°F)",
            humidity = "Moderate humidity (40-60%)",
            fertilizing = "Monthly during growing season",
            pruning = "Remove dead or damaged parts as needed",
            soilType = "Well-draining potting mix",
            commonProblems = listOf("Overwatering", "Insufficient light", "Pests")
        )
    }

    private fun getUnknownPlantResult(): ComprehensivePlantResult {
        return ComprehensivePlantResult(
            plantName = "Unknown Plant",
            scientificName = "Unknown",
            familyName = "",
            confidence = 0.0,
            isHealthy = true,
            diseases = emptyList(),
            careInstructions = getDefaultCareInstructions(),
            plantFacts = getDefaultPlantFacts(),
            toxicityInfo = ToxicityInfo(
                isToxic = false,
                toxicToPets = false,
                toxicToHumans = false,
                toxicityLevel = "Unknown",
                symptoms = emptyList(),
                treatment = "Consult healthcare provider if ingested"
            ),
            isOfflineResult = false
        )
    }

    // Helper methods
    private fun uriToBitmap(uri: Uri): Bitmap {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = android.graphics.BitmapFactory.decodeStream(inputStream)
        inputStream?.close()
        return bitmap ?: throw IllegalArgumentException("Unable to decode image from URI")
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
    }

    // Disease adapter for RecyclerView
    class DiseaseAdapter(private val diseases: List<Disease>) :
        androidx.recyclerview.widget.RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder>() {

        class DiseaseViewHolder(view: android.view.View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
            val diseaseNameText: android.widget.TextView = view.findViewById(R.id.diseaseNameText)
            val diseaseConfidenceText: android.widget.TextView = view.findViewById(R.id.diseaseConfidenceText)
            val diseaseDescriptionText: android.widget.TextView = view.findViewById(R.id.diseaseDescriptionText)
            val diseaseTreatmentText: android.widget.TextView = view.findViewById(R.id.diseaseTreatmentText)
        }

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): DiseaseViewHolder {
            val view = android.view.LayoutInflater.from(parent.context)
                .inflate(R.layout.item_disease, parent, false)
            return DiseaseViewHolder(view)
        }

        override fun onBindViewHolder(holder: DiseaseViewHolder, position: Int) {
            val disease = diseases[position]
            holder.diseaseNameText.text = disease.name
            holder.diseaseConfidenceText.text = "Confidence: ${(disease.confidence * 100).toInt()}%"
            holder.diseaseDescriptionText.text = disease.description
            holder.diseaseTreatmentText.text = "Treatment: ${disease.treatment}"
        }

        override fun getItemCount() = diseases.size
    }
}
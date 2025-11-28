// First, let's fix the PlantAPIService.kt
package com.example.plantidt.network

import com.example.plantidt.network.PlantCareResponse
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

interface PlantAPIService {

    @POST("identify")
    suspend fun identifyPlant(@Body request: PlantIdentificationRequest): Response<PlantIdentificationResponse>

    @GET("plant-details/{plantName}")
    suspend fun getPlantCareInfo(
        @Path("plantName") plantName: String,
        @Query("scientific_name") scientificName: String? = null
    ): Response<PlantCareResponse>

    @POST("health-assessment")
    suspend fun assessPlantHealth(@Body request: HealthAssessmentRequest): Response<HealthAssessmentResponse>

    @GET("search")
    suspend fun searchPlants(@Query("q") query: String): Response<PlantSearchResponse>

    @POST("disease-identification")
    suspend fun identifyDisease(@Body request: DiseaseIdentificationRequest): Response<DiseaseIdentificationResponse>
}

// Fixed Request/Response data classes with proper JSON annotations
data class PlantIdentificationRequest(
    @SerializedName("images") val images: List<String>, // Base64 encoded images
    @SerializedName("modifiers") val modifiers: List<String> = listOf("crops_fast", "similar_images", "health_assessment"),
    @SerializedName("plant_language") val plant_language: String = "en",
    @SerializedName("plant_details") val plant_details: List<String> = listOf(
        "common_names", "url", "name_authority", "wiki_description",
        "taxonomy", "synonyms", "edible_parts", "watering", "propagation_methods"
    )
)

data class PlantIdentificationResponse(
    @SerializedName("id") val id: String,
    @SerializedName("custom_id") val custom_id: String?,
    @SerializedName("meta_data") val meta_data: MetaData,
    @SerializedName("uploaded_datetime") val uploaded_datetime: String,
    @SerializedName("finished_datetime") val finished_datetime: String,
    @SerializedName("suggestions") val suggestions: List<PlantSuggestion>,
    @SerializedName("is_plant_probability") val is_plant_probability: Double,
    @SerializedName("is_plant") val is_plant: Boolean,
    @SerializedName("health_assessment") val health_assessment: HealthAssessmentResult?
)

data class MetaData(
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?,
    @SerializedName("date") val date: String?,
    @SerializedName("datetime") val datetime: String?
)

data class PlantSuggestion(
    @SerializedName("id") val id: String,
    @SerializedName("plant_name") val plant_name: String,
    @SerializedName("plant_details") val plant_details: PlantDetails,
    @SerializedName("probability") val probability: Double,
    @SerializedName("confirmed") val confirmed: Boolean,
    @SerializedName("similar_images") val similar_images: List<SimilarImage>
)

data class PlantDetails(
    @SerializedName("common_names") val common_names: List<String>,
    @SerializedName("url") val url: String,
    @SerializedName("name_authority") val name_authority: String,
    @SerializedName("wiki_description") val wiki_description: WikiDescription,
    @SerializedName("taxonomy") val taxonomy: Taxonomy,
    @SerializedName("synonyms") val synonyms: List<String>,
    @SerializedName("edible_parts") val edible_parts: List<String>,
    @SerializedName("watering") val watering: WateringDetails,
    @SerializedName("propagation_methods") val propagation_methods: List<String>
)

data class WikiDescription(
    @SerializedName("value") val value: String,
    @SerializedName("citation") val citation: String,
    @SerializedName("license_name") val license_name: String,
    @SerializedName("license_url") val license_url: String
)

data class Taxonomy(
    @SerializedName("class") val class_name: String,
    @SerializedName("family") val family: String,
    @SerializedName("genus") val genus: String,
    @SerializedName("kingdom") val kingdom: String,
    @SerializedName("order") val order: String,
    @SerializedName("phylum") val phylum: String
)

data class WateringDetails(
    @SerializedName("max") val max: Int,
    @SerializedName("min") val min: Int
)

data class SimilarImage(
    @SerializedName("id") val id: String,
    @SerializedName("similarity") val similarity: Double,
    @SerializedName("url") val url: String,
    @SerializedName("url_small") val url_small: String
)

data class HealthAssessmentResult(
    @SerializedName("is_healthy") val is_healthy: Boolean,
    @SerializedName("diseases") val diseases: List<Disease>,
    @SerializedName("is_healthy_probability") val is_healthy_probability: Double
)

data class Disease(
    @SerializedName("entity_id") val entity_id: String,
    @SerializedName("name") val name: String,
    @SerializedName("probability") val probability: Double,
    @SerializedName("disease_details") val disease_details: DiseaseDetails
)

data class DiseaseDetails(
    @SerializedName("local_name") val local_name: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url: String,
    @SerializedName("treatment") val treatment: Treatment,
    @SerializedName("classification") val classification: List<String>,
    @SerializedName("common_names") val common_names: List<String>,
    @SerializedName("cause") val cause: String
)

data class Treatment(
    @SerializedName("chemical") val chemical: List<String>,
    @SerializedName("biological") val biological: List<String>,
    @SerializedName("prevention") val prevention: List<String>
)

// Health Assessment Request
data class HealthAssessmentRequest(
    @SerializedName("images") val images: List<String>,
    @SerializedName("modifiers") val modifiers: List<String> = listOf("crops_fast", "similar_images"),
    @SerializedName("plant_language") val plant_language: String = "en",
    @SerializedName("disease_details") val disease_details: List<String> = listOf(
        "local_name", "description", "url", "treatment", "classification",
        "common_names", "cause"
    )
)

data class HealthAssessmentResponse(
    @SerializedName("id") val id: String,
    @SerializedName("custom_id") val custom_id: String?,
    @SerializedName("meta_data") val meta_data: MetaData,
    @SerializedName("uploaded_datetime") val uploaded_datetime: String,
    @SerializedName("finished_datetime") val finished_datetime: String,
    @SerializedName("is_plant") val is_plant: Boolean,
    @SerializedName("is_plant_probability") val is_plant_probability: Double,
    @SerializedName("health_assessment") val health_assessment: HealthAssessmentResult
)

// Plant Search
data class PlantSearchResponse(
    @SerializedName("entities") val entities: List<PlantEntity>,
    @SerializedName("total_entities") val total_entities: Int
)

data class PlantEntity(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("matched_in") val matched_in: String,
    @SerializedName("access_token") val access_token: String,
    @SerializedName("entity_name") val entity_name: String,
    @SerializedName("matched_in_type") val matched_in_type: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: PlantImage,
    @SerializedName("structured_name") val structured_name: StructuredName,
    @SerializedName("taxonomy") val taxonomy: Taxonomy,
    @SerializedName("common_names") val common_names: List<String>,
    @SerializedName("edible_parts") val edible_parts: List<String>,
    @SerializedName("watering") val watering: WateringDetails,
    @SerializedName("propagation_methods") val propagation_methods: List<String>,
    @SerializedName("care_guides") val care_guides: CareGuides?,
    @SerializedName("toxicity") val toxicity: ToxicityDetails?
)

data class PlantImage(
    @SerializedName("value") val value: String,
    @SerializedName("citation") val citation: String,
    @SerializedName("license_name") val license_name: String,
    @SerializedName("license_url") val license_url: String
)

data class StructuredName(
    @SerializedName("genus") val genus: String,
    @SerializedName("species") val species: String,
    @SerializedName("author") val author: String
)

data class CareGuides(
    @SerializedName("watering") val watering: WateringGuide,
    @SerializedName("light") val light: LightGuide,
    @SerializedName("humidity") val humidity: HumidityGuide,
    @SerializedName("temperature") val temperature: TemperatureGuide,
    @SerializedName("fertilizing") val fertilizing: FertilizingGuide,
    @SerializedName("pruning") val pruning: PruningGuide,
    @SerializedName("repotting") val repotting: RepottingGuide,
    @SerializedName("soil") val soil: SoilGuide
)

data class WateringGuide(
    @SerializedName("frequency") val frequency: String,
    @SerializedName("amount") val amount: String,
    @SerializedName("method") val method: String,
    @SerializedName("seasonal_variation") val seasonal_variation: String,
    @SerializedName("signs_of_overwatering") val signs_of_overwatering: List<String>,
    @SerializedName("signs_of_underwatering") val signs_of_underwatering: List<String>
)

data class LightGuide(
    @SerializedName("requirement") val requirement: String,
    @SerializedName("type") val type: String,
    @SerializedName("hours_per_day") val hours_per_day: Int,
    @SerializedName("seasonal_changes") val seasonal_changes: String,
    @SerializedName("signs_of_too_much_light") val signs_of_too_much_light: List<String>,
    @SerializedName("signs_of_too_little_light") val signs_of_too_little_light: List<String>
)

data class HumidityGuide(
    @SerializedName("ideal_range") val ideal_range: String,
    @SerializedName("methods_to_increase") val methods_to_increase: List<String>,
    @SerializedName("signs_of_low_humidity") val signs_of_low_humidity: List<String>,
    @SerializedName("signs_of_high_humidity") val signs_of_high_humidity: List<String>
)

data class TemperatureGuide(
    @SerializedName("ideal_range") val ideal_range: String,
    @SerializedName("minimum_temperature") val minimum_temperature: Int,
    @SerializedName("maximum_temperature") val maximum_temperature: Int,
    @SerializedName("seasonal_preferences") val seasonal_preferences: String,
    @SerializedName("cold_damage_signs") val cold_damage_signs: List<String>,
    @SerializedName("heat_stress_signs") val heat_stress_signs: List<String>
)

data class FertilizingGuide(
    @SerializedName("frequency") val frequency: String,
    @SerializedName("type") val type: String,
    @SerializedName("npk_ratio") val npk_ratio: String,
    @SerializedName("seasonal_schedule") val seasonal_schedule: String,
    @SerializedName("signs_of_overfertilizing") val signs_of_overfertilizing: List<String>,
    @SerializedName("signs_of_underfertilizing") val signs_of_underfertilizing: List<String>
)

data class PruningGuide(
    @SerializedName("frequency") val frequency: String,
    @SerializedName("best_time") val best_time: String,
    @SerializedName("technique") val technique: String,
    @SerializedName("tools_needed") val tools_needed: List<String>,
    @SerializedName("what_to_prune") val what_to_prune: List<String>
)

data class RepottingGuide(
    @SerializedName("frequency") val frequency: String,
    @SerializedName("best_time") val best_time: String,
    @SerializedName("pot_size_increase") val pot_size_increase: String,
    @SerializedName("signs_needs_repotting") val signs_needs_repotting: List<String>,
    @SerializedName("aftercare") val aftercare: List<String>
)

data class SoilGuide(
    @SerializedName("type") val type: String,
    @SerializedName("ph_range") val ph_range: String,
    @SerializedName("drainage") val drainage: String,
    @SerializedName("nutrients") val nutrients: List<String>,
    @SerializedName("amendments") val amendments: List<String>
)

data class ToxicityDetails(
    @SerializedName("to_humans") val to_humans: ToxicityLevel,
    @SerializedName("to_dogs") val to_dogs: ToxicityLevel,
    @SerializedName("to_cats") val to_cats: ToxicityLevel,
    @SerializedName("symptoms") val symptoms: List<String>,
    @SerializedName("treatment") val treatment: String
)

data class ToxicityLevel(
    @SerializedName("level") val level: String, // none, mild, moderate, severe
    @SerializedName("description") val description: String
)

// Disease Identification
data class DiseaseIdentificationRequest(
    @SerializedName("images") val images: List<String>,
    @SerializedName("modifiers") val modifiers: List<String> = listOf("crops_fast", "similar_images"),
    @SerializedName("plant_language") val plant_language: String = "en",
    @SerializedName("disease_details") val disease_details: List<String> = listOf(
        "local_name", "description", "url", "treatment", "classification",
        "common_names", "cause"
    )
)

data class DiseaseIdentificationResponse(
    @SerializedName("id") val id: String,
    @SerializedName("custom_id") val custom_id: String?,
    @SerializedName("meta_data") val meta_data: MetaData,
    @SerializedName("uploaded_datetime") val uploaded_datetime: String,
    @SerializedName("finished_datetime") val finished_datetime: String,
    @SerializedName("is_plant") val is_plant: Boolean,
    @SerializedName("is_plant_probability") val is_plant_probability: Double,
    @SerializedName("health_assessment") val health_assessment: HealthAssessmentResult,
    @SerializedName("suggestions") val suggestions: List<DiseaseSuggestion>
)

data class DiseaseSuggestion(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("probability") val probability: Double,
    @SerializedName("similar_images") val similar_images: List<SimilarImage>,
    @SerializedName("details") val details: DiseaseDetails
)
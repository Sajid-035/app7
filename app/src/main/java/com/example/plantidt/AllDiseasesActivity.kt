package com.example.plantidt

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AllDiseasesActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var diseasesRecyclerView: RecyclerView
    private lateinit var backButton: ImageView
    private lateinit var titleText: TextView
    private lateinit var resultCountText: TextView

    private lateinit var diseaseAdapter: DiseaseListAdapter
    private val allDiseases = mutableListOf<DiseaseItem>()
    private val filteredDiseases = mutableListOf<DiseaseItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_diseases)

        initializeViews()
        setupDiseaseData()
        setupRecyclerView()
        setupSearchFunctionality()
        setupClickListeners()
    }

    private fun initializeViews() {
        searchEditText = findViewById(R.id.searchEditText)
        diseasesRecyclerView = findViewById(R.id.diseasesRecyclerView)
        backButton = findViewById(R.id.backButton)
        titleText = findViewById(R.id.titleText)
        resultCountText = findViewById(R.id.resultCountText)
    }

    private fun setupDiseaseData() {
        // Nutritional Deficiencies
        val nutritionalDeficiencies = listOf(
            "Potassium deficiency",
            "Nitrogen deficiency",
            "Phosphorus deficiency",
            "Magnesium deficiency",
            "Iron deficiency",
            "Boron deficiency",
            "Calcium deficiency",
            "Manganese deficiency",
            "Sulfur deficiency",
            "Zinc deficiency"
        )

        // Physiological Disorders
        val physiologicalDisorders = listOf(
            "Cork spot",
            "Aster yellow",
            "Nutrient excess",
            "Cold weather/ Frost damage",
            "Lack of light"
        )

        // Bacterial Diseases
        val bacterialDiseases = listOf(
            "Bacterial wilt",
            "Fire blight",
            "Bacterial canker",
            "Crown gall"
        )

        // Viral Diseases
        val viralDiseases = listOf(
            "Mosaic virus",
            "Psorosis"
        )

        // Fungal Diseases
        val fungalDiseases = listOf(
            "Anthracnose",
            "Black knot",
            "Rhizoctonia Blight",
            "Clubroot",
            "Fungal cankers",
            "Damping off",
            "Fusarium wilt",
            "Leaf blister",
            "Powdery mildew",
            "Downy mildew",
            "Root rot",
            "Rust",
            "Scab",
            "Smuts",
            "Snow mold",
            "Sooty mold",
            "Verticillium wilt",
            "Heminthosporium disease",
            "Phyllosticta leaf spot",
            "Phoma blight",
            "Cercospora blight",
            "Aloe rust",
            "Needle blight",
            "Crown rot disease",
            "Stem rot disease",
            "Brown spot disease",
            "Bayoud disease",
            "Rhabdocline needle cast disease",
            "Dieback"
        )

        // Add all diseases to the main list with categories
        nutritionalDeficiencies.forEach {
            allDiseases.add(DiseaseItem(it, "Nutritional Deficiency", getIconForDisease(it)))
        }
        physiologicalDisorders.forEach {
            allDiseases.add(DiseaseItem(it, "Physiological Disorder", getIconForDisease(it)))
        }
        bacterialDiseases.forEach {
            allDiseases.add(DiseaseItem(it, "Bacterial Disease", getIconForDisease(it)))
        }
        viralDiseases.forEach {
            allDiseases.add(DiseaseItem(it, "Viral Disease", getIconForDisease(it)))
        }
        fungalDiseases.forEach {
            allDiseases.add(DiseaseItem(it, "Fungal Disease", getIconForDisease(it)))
        }

        // Initially show all diseases
        filteredDiseases.clear()
        filteredDiseases.addAll(allDiseases)
        updateResultCount()
    }

    private fun getIconForDisease(diseaseName: String): Int {
        return when {
            diseaseName.contains("potassium", ignoreCase = true) -> R.drawable.ic_potassium
            diseaseName.contains("nitrogen", ignoreCase = true) -> R.drawable.ic_nitrogen
            diseaseName.contains("phosphorus", ignoreCase = true) -> R.drawable.ic_phosphorus
            diseaseName.contains("iron", ignoreCase = true) -> R.drawable.ic_iron
            diseaseName.contains("magnesium", ignoreCase = true) -> R.drawable.ic_magnesium
            diseaseName.contains("calcium", ignoreCase = true) -> R.drawable.ic_calcium
            diseaseName.contains("boron", ignoreCase = true) -> R.drawable.ic_boron
            diseaseName.contains("manganese", ignoreCase = true) -> R.drawable.ic_manganese
            diseaseName.contains("sulfur", ignoreCase = true) -> R.drawable.ic_sulfur
            diseaseName.contains("zinc", ignoreCase = true) -> R.drawable.ic_zinc
            diseaseName.contains("bacterial", ignoreCase = true) -> R.drawable.ic_bacteria
            diseaseName.contains("viral", ignoreCase = true) || diseaseName.contains("virus", ignoreCase = true) -> R.drawable.ic_virus
            diseaseName.contains("fungal", ignoreCase = true) || diseaseName.contains("mildew", ignoreCase = true) ||
                    diseaseName.contains("rust", ignoreCase = true) || diseaseName.contains("rot", ignoreCase = true) -> R.drawable.ic_fungus
            else -> R.drawable.ic_disease_default
        }
    }

    private fun setupRecyclerView() {
        diseaseAdapter = DiseaseListAdapter(filteredDiseases) { disease ->
            onDiseaseClicked(disease)
        }
        diseasesRecyclerView.layoutManager = LinearLayoutManager(this)
        diseasesRecyclerView.adapter = diseaseAdapter
    }

    private fun setupSearchFunctionality() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterDiseases(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterDiseases(query: String) {
        val lowerQuery = query.lowercase()

        filteredDiseases.clear()

        if (query.isEmpty()) {
            filteredDiseases.addAll(allDiseases)
        } else {
            filteredDiseases.addAll(
                allDiseases.filter { disease ->
                    disease.name.lowercase().contains(lowerQuery) ||
                            disease.category.lowercase().contains(lowerQuery)
                }
            )
        }

        diseaseAdapter.notifyDataSetChanged()
        updateResultCount()
    }

    private fun updateResultCount() {
        resultCountText.text = "${filteredDiseases.size} diseases found"
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun onDiseaseClicked(disease: DiseaseItem) {
        // For now, show a toast with disease info
        when (disease.name.lowercase()) {
            "potassium deficiency" -> {
                val intent = Intent(this, PotassiumDeficiencyActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "nitrogen deficiency" -> {
                val intent = Intent(this, NitrogenDeficiencyActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "phosphorus deficiency" -> {
                // Add other deficiency activities here when they're created
                Toast.makeText(this, "Phosphorus deficiency details coming soon!", Toast.LENGTH_SHORT).show()
            }
            "iron deficiency" -> {
                // Add other deficiency activities here when they're created
                Toast.makeText(this, "Iron deficiency details coming soon!", Toast.LENGTH_SHORT).show()
            }
            "magnesium deficiency" -> {
                // Add other deficiency activities here when they're created
                Toast.makeText(this, "Magnesium deficiency details coming soon!", Toast.LENGTH_SHORT).show()
            }
            "calcium deficiency" -> {
                // Add other deficiency activities here when they're created
                Toast.makeText(this, "Calcium deficiency details coming soon!", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Selected: ${disease.name}\nCategory: ${disease.category}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

// Data class for disease items
data class DiseaseItem(
    val name: String,
    val category: String,
    val iconResId: Int
)
package com.example.plantidt

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Remove the duplicate DiagnosisItem data class - it's already defined in DiagnosisModels.kt

class DiagnoseActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var diseaseRecyclerView: RecyclerView
    private lateinit var pestRecyclerView: RecyclerView
    private lateinit var backButton: ImageView

    private lateinit var diseaseAdapter: DiagnosisAdapter
    private lateinit var pestAdapter: DiagnosisAdapter

    private val diseaseList = listOf(
        DiagnosisItem("Potassium deficiency", "potassium_deficiency", R.drawable.ic_potassium),
        DiagnosisItem("Nitrogen deficiency", "nitrogen_deficiency", R.drawable.ic_nitrogen),
        DiagnosisItem("Phosphorus deficiency", "phosphorus_deficiency", R.drawable.ic_phosphorus),
        DiagnosisItem("Iron deficiency", "iron_deficiency", R.drawable.ic_iron),
        DiagnosisItem("Magnesium deficiency", "magnesium_deficiency", R.drawable.ic_magnesium),
        DiagnosisItem("Calcium deficiency", "calcium_deficiency", R.drawable.ic_calcium)
    )

    private val pestList = listOf(
        DiagnosisItem("Root aphids", "root_aphids", R.drawable.ic_aphids),
        DiagnosisItem("Thrips", "thrips", R.drawable.ic_thrips),
        DiagnosisItem("Spider mites", "spider_mites", R.drawable.ic_spider_mites),
        DiagnosisItem("Whiteflies", "whiteflies", R.drawable.ic_whiteflies),
        DiagnosisItem("Scale insects", "scale_insects", R.drawable.ic_scale),
        DiagnosisItem("Mealybugs", "mealybugs", R.drawable.ic_mealybugs)
    )

    private var filteredDiseaseList = diseaseList.toMutableList()
    private var filteredPestList = pestList.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnose)

        initializeViews()
        setupRecyclerViews()
        setupSearchFunctionality()
        setupClickListeners()

        // Add entrance animation
        animateEntranceCards()
    }

    private fun initializeViews() {
        searchEditText = findViewById(R.id.searchEditText)
        diseaseRecyclerView = findViewById(R.id.diseaseRecyclerView)
        pestRecyclerView = findViewById(R.id.pestRecyclerView)
        backButton = findViewById(R.id.backButton)
    }

    private fun setupRecyclerViews() {
        // Disease RecyclerView
        diseaseAdapter = DiagnosisAdapter(filteredDiseaseList) { item ->
            openDiagnosisDetail(item)
        }
        diseaseRecyclerView.layoutManager = GridLayoutManager(this, 2)
        diseaseRecyclerView.adapter = diseaseAdapter

        // Pest RecyclerView
        pestAdapter = DiagnosisAdapter(filteredPestList) { item ->
            openDiagnosisDetail(item)
        }
        pestRecyclerView.layoutManager = GridLayoutManager(this, 2)
        pestRecyclerView.adapter = pestAdapter
    }

    private fun setupSearchFunctionality() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterItems(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterItems(query: String) {
        val lowerQuery = query.lowercase()

        // Filter diseases
        filteredDiseaseList.clear()
        filteredDiseaseList.addAll(
            diseaseList.filter {
                it.name.lowercase().contains(lowerQuery) ||
                        it.type.lowercase().contains(lowerQuery)
            }
        )

        // Filter pests
        filteredPestList.clear()
        filteredPestList.addAll(
            pestList.filter {
                it.name.lowercase().contains(lowerQuery) ||
                        it.type.lowercase().contains(lowerQuery)
            }
        )

        // Update adapters
        diseaseAdapter.notifyDataSetChanged()
        pestAdapter.notifyDataSetChanged()

        // Show/hide sections based on results
        findViewById<View>(R.id.diseaseSection).visibility =
            if (filteredDiseaseList.isEmpty()) View.GONE else View.VISIBLE
        findViewById<View>(R.id.pestSection).visibility =
            if (filteredPestList.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }

        // See more buttons
        findViewById<CardView>(R.id.seeMoreDiseaseCard).setOnClickListener {
            showAllDiseases()
        }

        findViewById<CardView>(R.id.seeMorePestCard).setOnClickListener {
            showAllPests()
        }

        // Add touch feedback to cards
        setupCardTouchFeedback()
    }

    private fun setupCardTouchFeedback() {
        val cards = listOf(
            R.id.seeMoreDiseaseCard,
            R.id.seeMorePestCard
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
        val elevation = if (pressed) 4f else 8f

        view.animate()
            .scaleX(scaleX)
            .scaleY(scaleY)
            .translationZ(elevation)
            .setDuration(150)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }

    private fun animateEntranceCards() {
        val cards = listOf(
            findViewById<View>(R.id.diseaseSection),
            findViewById<View>(R.id.pestSection)
        )

        cards.forEachIndexed { index, card ->
            card.alpha = 0f
            card.translationY = 100f

            card.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(300)
                .setStartDelay((index * 100).toLong())
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
        }
    }

    private fun openDiagnosisDetail(item: DiagnosisItem) {
        when (item.type) {
            "potassium_deficiency" -> {
                val intent = Intent(this, PotassiumDeficiencyActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "nitrogen_deficiency" -> {
                val intent = Intent(this, PotassiumDeficiencyActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        "phosphorus_deficiency" -> {
            // Add when PhosphorusDeficiencyActivity is created
            Toast.makeText(this, "Phosphorus deficiency details coming soon!", Toast.LENGTH_SHORT).show()
        }
        "iron_deficiency" -> {
            // Add when IronDeficiencyActivity is created
            Toast.makeText(this, "Iron deficiency details coming soon!", Toast.LENGTH_SHORT).show()
        }
        "magnesium_deficiency" -> {
            // Add when MagnesiumDeficiencyActivity is created
            Toast.makeText(this, "Magnesium deficiency details coming soon!", Toast.LENGTH_SHORT).show()
        }
        "calcium_deficiency" -> {
            // Add when CalciumDeficiencyActivity is created
            Toast.makeText(this, "Calcium deficiency details coming soon!", Toast.LENGTH_SHORT).show()
        }
            "thrips" -> {
                val intent = Intent(this, ThripsActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            "root_aphids" -> {
                val intent = Intent(this, RootAphidsActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            else -> {
                // For other items, show a toast since their detail activities don't exist yet
                Toast.makeText(this, "Opening details for: ${item.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showAllDiseases() {
        val intent = Intent(this, AllDiseasesActivity::class.java)
        startActivity(intent)
        // Add slide animation
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun showAllPests() {
        val intent = Intent(this, AllPestsActivity::class.java)
        startActivity(intent)
        // Add slide animation
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
package com.example.plantidt

import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

class SymptomDetailsActivity : AppCompatActivity() {

    private lateinit var partType: String
    private lateinit var partName: String

    // Symptom data classes
    data class Symptom(
        val title: String,
        val description: String,
        val imageRes: Int
    )

    // Define symptoms for all plant parts
    private val flowerSymptoms = listOf(
        Symptom("Deformed Flower", "Abnormal shape, size, or color", R.drawable.deformed_flower),
        Symptom("Dull and Dry Flower", "Lack of luster or faded appearance", R.drawable.dull_dry_flower),
        Symptom("Premature Bud Falling", "Early dropping of flower buds", R.drawable.premature_bud_falling),
        Symptom("No or Less Flowering", "Absence of blooms", R.drawable.no_less_flowering),
        Symptom("Spots", "Various spots on flower", R.drawable.flower_spots),
        Symptom("Sign of Pests", "Visible pest damage", R.drawable.flower_pests)
    )

    private val leavesSymptoms = listOf(
        Symptom("Discoloration", "Leaves turning Yellow, Brown, Black, Purple, or White", R.drawable.leaf_discoloration),
        Symptom("Droopy Leaves", "Leaves are getting droopy", R.drawable.droopy_leaves),
        Symptom("Spots on Leaves", "Various spots on the leaves", R.drawable.spots_on_leaves),
        Symptom("Leaf Curl", "Leaf curl in different patterns", R.drawable.leaf_curl),
        Symptom("Holes in Leaves", "Unnatural holes in leaves", R.drawable.holes_in_leaves),
        Symptom("Falling of Leaves", "Falling of all types of leaves", R.drawable.falling_leaves)
    )

    private val stemSymptoms = listOf(
        Symptom("Damping Off", "Seedling stem collapse", R.drawable.stem_damping),
        Symptom("Physical Damage", "Visible wounds on stem", R.drawable.stem_damage),
        Symptom("Decay", "Rotting stem tissue", R.drawable.stem_decay),
        Symptom("Fungal Growth", "Mold or mildew present", R.drawable.stem_fungus),
        Symptom("Lesions", "Discolored stem patches", R.drawable.stem_lesions),
        Symptom("Knots", "Abnormal growths", R.drawable.stem_knots),
        Symptom("Sign of Pests", "There are pests visible on the plant stem", R.drawable.stem_pest)
    )

    private val fruitSymptoms = listOf(
        Symptom("Poor Coloring", "Lack of typical colors in the fruits", R.drawable.fruit_poor_color),
        Symptom("Rot", "Decay or rotting of the fruit", R.drawable.fruit_rot),
        Symptom("Deformity", "Deformed or unusually shaped fruits", R.drawable.fruit_deformity),
        Symptom("Premature Fruit Fall", "Fruit falls before maturing", R.drawable.fruit_premature_fall),
        Symptom("Spots on Fruits", "Discolored spots on fruit surface", R.drawable.fruit_spots),
        Symptom("Sign of Pests", "Pest infestation damage", R.drawable.fruit_pests)
    )

    private val wholePlantSymptoms = listOf(
        Symptom("Wilting", "Plant appears weak and dull", R.drawable.plant_wilting),
        Symptom("Decaying Plant", "Foul odor and frail appearance", R.drawable.plant_decay),
        Symptom("Damping Off", "Sudden collapse of the plant", R.drawable.plant_damping_off),
        Symptom("Drying & Drooping", "Plant becomes dry and droops", R.drawable.plant_drying),
        Symptom("Stunted Plant", "Halted or short growth", R.drawable.plant_stunted),
        Symptom("Flattening", "Stem/flower grows abnormally flat", R.drawable.plant_flattening),
        Symptom("Formation of Tumor", "Tumor-like growths", R.drawable.plant_tumors),
        Symptom("Proliferation of Shoots", "Excessive new shoot growth", R.drawable.plant_shoots),
        Symptom("Sign of Pests", "Visible pest infestation", R.drawable.plant_pests)
    )

    private val rootSymptoms = listOf(
        Symptom("Root Desiccation", "Dried out roots", R.drawable.root_dry),
        Symptom("Root Rot", "Decaying root tissue", R.drawable.root_rot),
        Symptom("Fungal Growth", "Fungal presence on roots", R.drawable.root_fungus),
        Symptom("Gall Formation", "Swollen root nodules", R.drawable.root_galls),
        Symptom("White Crust", "Salt/mineral buildup", R.drawable.root_crust),
        Symptom("Sign of Pests", "Root pest infestation", R.drawable.root_pests)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptom_details)

        partType = intent.getStringExtra("PART_TYPE") ?: ""
        partName = intent.getStringExtra("PART_NAME") ?: ""

        setupBackButton()
        setupUI()
        setupSymptomCards()
    }

    private fun setupBackButton() {
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }
    }

    private fun setupUI() {
        findViewById<TextView>(R.id.titleText).text = partName
        findViewById<TextView>(R.id.subtitleText).text = "Identify issues affecting the ${partName.lowercase()}"
    }

    private fun setupSymptomCards() {
        when (partType) {
            "flower" -> setupSymptoms(flowerSymptoms)
            "stem" -> setupSymptoms(stemSymptoms)
            "leaves" -> setupSymptoms(leavesSymptoms)
            "fruit" -> setupSymptoms(fruitSymptoms)
            "whole_plant" -> setupSymptoms(wholePlantSymptoms)
            "root" -> setupSymptoms(rootSymptoms)
            else -> setupSymptoms(emptyList())
        }
    }

    private fun setupSymptoms(symptoms: List<Symptom>) {
        // Hide all cards first
        (1..9).forEach { hideCard(it) }

        // Configure visible cards
        symptoms.forEachIndexed { index, symptom ->
            val cardNumber = index + 1
            if (cardNumber > 9) return@forEachIndexed

            val card = findViewById<CardView>(resources.getIdentifier("card$cardNumber", "id", packageName))
            val image = findViewById<ImageView>(resources.getIdentifier("image$cardNumber", "id", packageName))
            val title = findViewById<TextView>(resources.getIdentifier("title$cardNumber", "id", packageName))
            val desc = findViewById<TextView>(resources.getIdentifier("desc$cardNumber", "id", packageName))

            card.visibility = View.VISIBLE
            image.setImageResource(symptom.imageRes)
            title.text = symptom.title
            desc.text = symptom.description
            card.setOnClickListener { handleSymptomClick(symptom.title) }
        }
    }

    private fun hideCard(number: Int) {
        findViewById<CardView>(resources.getIdentifier("card$number", "id", packageName)).visibility = View.GONE
    }

    private fun handleSymptomClick(symptomTitle: String) {
        when (symptomTitle) {
            "Deformed Flower" -> {
                val intent = Intent(this, DeformedFlowerActivity::class.java)
                startActivity(intent)
            }
            "Dull and Dry Flower" -> {
                val intent = Intent(this, DullAndDryFlowerActivity::class.java)
                startActivity(intent)
            }
            "Discoloration" -> {
                val intent = Intent(this, DiscolorationActivity::class.java)
                startActivity(intent)
            }
            "Droopy Leaves" -> {
                val intent = Intent(this, DroopyLeavesActivity::class.java)
                startActivity(intent)
            }
            "Spots on Leaves" -> {
                val intent = Intent(this, SpotsOnLeavesActivity::class.java)
                startActivity(intent)
            }
            else -> {
                // Handle other symptom selections, maybe show a Toast for now
                Toast.makeText(this, "Details for: $symptomTitle", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
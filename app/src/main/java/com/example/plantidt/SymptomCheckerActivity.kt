package com.example.plantidt

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

class SymptomCheckerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptom_checker)

        setupToolbar()
        setupCardClickListeners()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "Choose Affected Part"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupCardClickListeners() {
        findViewById<CardView>(R.id.cardLeaves).setOnClickListener {
            navigateToSymptomDetails("leaves", "Leaves")
        }

        findViewById<CardView>(R.id.cardFlower).setOnClickListener {
            navigateToSymptomDetails("flower", "Flower")
        }

        findViewById<CardView>(R.id.cardStem).setOnClickListener {
            navigateToSymptomDetails("stem", "Stem")
        }

        findViewById<CardView>(R.id.cardFruits).setOnClickListener {
            navigateToSymptomDetails("fruit", "Fruits")
        }

        findViewById<CardView>(R.id.cardRoots).setOnClickListener {
            navigateToSymptomDetails("root", "Roots")
        }

        findViewById<CardView>(R.id.cardWholePlant).setOnClickListener {
            navigateToSymptomDetails("whole_plant", "Whole Plant")
        }
    }

    private fun navigateToSymptomDetails(partType: String, partName: String) {
        // Navigate to the symptom details activity
        val intent = Intent(this, SymptomDetailsActivity::class.java)
        intent.putExtra("PART_TYPE", partType)
        intent.putExtra("PART_NAME", partName)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
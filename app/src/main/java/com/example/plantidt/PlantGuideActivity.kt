package com.example.plantidt


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

class PlantGuideActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var guideAdapter: GuideAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_guide)

        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "Plant Care Guide"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewGuides)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val guides = listOf(
            GuideItem("Watering Guide", R.drawable.ic_water, GuideType.WATERING, "#4CAF50"),
            GuideItem("Temperature Guide", R.drawable.ic_temperature, GuideType.TEMPERATURE, "#FF9800"),
            GuideItem("Sunlight Guide", R.drawable.ic_sun, GuideType.SUNLIGHT, "#FFC107"),
            GuideItem("Soil Guide", R.drawable.ic_soil, GuideType.SOIL, "#795548"),
            GuideItem("Fertilizing Guide", R.drawable.ic_fertilizer, GuideType.FERTILIZING, "#8BC34A"),
            GuideItem("Repotting Guide", R.drawable.ic_pot, GuideType.REPOTTING, "#607D8B"),
            GuideItem("Pest Guide", R.drawable.ic_bug, GuideType.PEST, "#F44336"),
            GuideItem("Disease Guide", R.drawable.ic_disease, GuideType.DISEASE, "#E91E63"),
            GuideItem("Humidity Guide", R.drawable.ic_humidity, GuideType.HUMIDITY, "#2196F3"),
            GuideItem("Sowing Guide", R.drawable.ic_seed, GuideType.SOWING, "#9C27B0"),
            GuideItem("Pruning Guide", R.drawable.ic_scissors, GuideType.PRUNING, "#00BCD4"),
            GuideItem("Harvesting Guide", R.drawable.ic_harvest, GuideType.HARVESTING, "#CDDC39"),
            GuideItem("Soil Tilling Guide", R.drawable.ic_tilling, GuideType.TILLING, "#6D4C41")
        )

        guideAdapter = GuideAdapter(guides) { guideType ->
            openGuideDetail(guideType)
        }
        recyclerView.adapter = guideAdapter
    }

    private fun openGuideDetail(guideType: GuideType) {
        val intent = Intent(this, GuideDetailActivity::class.java)
        intent.putExtra("GUIDE_TYPE", guideType.name)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}


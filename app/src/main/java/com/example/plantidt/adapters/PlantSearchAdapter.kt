package com.example.plantidt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantidt.R
import com.example.plantidt.models.PlantSearchResult

class PlantSearchAdapter(
    private val plants: List<PlantSearchResult>,
    private val onItemClick: (PlantSearchResult) -> Unit
) : RecyclerView.Adapter<PlantSearchAdapter.PlantSearchViewHolder>() {

    class PlantSearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plantImage: ImageView = view.findViewById(R.id.plantImage)
        val plantName: TextView = view.findViewById(R.id.plantName)
        val scientificName: TextView = view.findViewById(R.id.scientificName)
        val familyName: TextView = view.findViewById(R.id.familyName)
        val difficultyText: TextView = view.findViewById(R.id.difficultyText)
        val careTagsText: TextView = view.findViewById(R.id.careTagsText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantSearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plant_search, parent, false)
        return PlantSearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantSearchViewHolder, position: Int) {
        val plant = plants[position]

        holder.plantName.text = plant.commonName
        holder.scientificName.text = plant.scientificName
        holder.familyName.text = plant.family
        holder.difficultyText.text = plant.careLevel

        // Set care tags
        val careTags = mutableListOf<String>()
        if (plant.lowLight) careTags.add("Low Light")
        if (plant.lowWater) careTags.add("Low Water")
        if (plant.petSafe) careTags.add("Pet Safe")
        if (plant.airPurifying) careTags.add("Air Purifying")

        holder.careTagsText.text = careTags.joinToString(" • ")

        // Load plant image
        if (plant.imageUrl.isNotEmpty()) {
            // Load image using your preferred image loading library (Glide, Picasso, etc.)
            // Glide.with(holder.itemView.context)
            //     .load(plant.imageUrl)
            //     .into(holder.plantImage)
        }

        holder.itemView.setOnClickListener {
            onItemClick(plant)
        }
    }

    override fun getItemCount() = plants.size
}
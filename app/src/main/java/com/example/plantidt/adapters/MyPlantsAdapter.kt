package com.example.plantidt.adapters

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantidt.R
import com.example.plantidt.models.MyPlantItem
import java.text.SimpleDateFormat
import java.util.Locale

class MyPlantsAdapter(
    private val myPlants: List<MyPlantItem>,
    private val onItemClick: (MyPlantItem) -> Unit,
    private val onCareClick: (MyPlantItem, String) -> Unit
) : RecyclerView.Adapter<MyPlantsAdapter.MyPlantViewHolder>() {

    class MyPlantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plantImage: ImageView = view.findViewById(R.id.plantImage)
        val plantName: TextView = view.findViewById(R.id.plantName)
        val plantNickname: TextView = view.findViewById(R.id.plantNickname)
        val healthStatus: TextView = view.findViewById(R.id.healthStatus)
        val nextWateringText: TextView = view.findViewById(R.id.nextWateringText)
        val nextFertilizingText: TextView = view.findViewById(R.id.nextFertilizingText)
        val waterButton: ImageView = view.findViewById(R.id.waterButton)
        val fertilizeButton: ImageView = view.findViewById(R.id.fertilizeButton)
        val healthIndicator: View = view.findViewById(R.id.healthIndicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPlantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_my_plant, parent, false)
        return MyPlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyPlantViewHolder, position: Int) {
        val plant = myPlants[position]

        holder.plantName.text = plant.name
        holder.plantNickname.text = plant.nickname
        holder.healthStatus.text = plant.healthStatus

        // Set health indicator color
        when (plant.healthLevel) {
            "excellent" -> holder.healthIndicator.setBackgroundColor(
                holder.itemView.context.getColor(R.color.health_excellent)
            )
            "good" -> holder.healthIndicator.setBackgroundColor(
                holder.itemView.context.getColor(R.color.health_good)
            )
            "fair" -> holder.healthIndicator.setBackgroundColor(
                holder.itemView.context.getColor(R.color.health_fair)
            )
            "poor" -> holder.healthIndicator.setBackgroundColor(
                holder.itemView.context.getColor(R.color.health_poor)
            )
        }

        // Set next care dates
        val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())

        if (plant.nextWatering != null) {
            val days = ((plant.nextWatering - System.currentTimeMillis()) / (1000 * 60 * 60 * 24)).toInt()
            holder.nextWateringText.text = when {
                days < 0 -> "Overdue"
                days == 0 -> "Today"
                days == 1 -> "Tomorrow"
                else -> "in $days days"
            }
        } else {
            holder.nextWateringText.text = "No schedule"
        }

        if (plant.nextFertilizing != null) {
            val days = ((plant.nextFertilizing - System.currentTimeMillis()) / (1000 * 60 * 60 * 24)).toInt()
            holder.nextFertilizingText.text = when {
                days < 0 -> "Overdue"
                days == 0 -> "Today"
                days == 1 -> "Tomorrow"
                else -> "in $days days"
            }
        } else {
            holder.nextFertilizingText.text = "No schedule"
        }

        // Load plant image
        if (plant.imageBase64.isNotEmpty()) {
            try {
                val imageBytes = Base64.decode(plant.imageBase64, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                holder.plantImage.setImageBitmap(bitmap)
            } catch (e: Exception) {
                holder.plantImage.setImageResource(R.drawable.ic_plant_placeholder)
            }
        } else {
            holder.plantImage.setImageResource(R.drawable.ic_plant_placeholder)
        }

        // Set click listeners
        holder.itemView.setOnClickListener {
            onItemClick(plant)
        }

        holder.waterButton.setOnClickListener {
            onCareClick(plant, "water")
        }

        holder.fertilizeButton.setOnClickListener {
            onCareClick(plant, "fertilize")
        }
    }

    override fun getItemCount() = myPlants.size
}
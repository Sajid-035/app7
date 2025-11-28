package com.example.plantidt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class DiseaseListAdapter(
    private val diseases: List<DiseaseItem>,
    private val onItemClick: (DiseaseItem) -> Unit
) : RecyclerView.Adapter<DiseaseListAdapter.DiseaseViewHolder>() {

    class DiseaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.diseaseCard)
        val diseaseIcon: ImageView = itemView.findViewById(R.id.diseaseIcon)
        val diseaseNameText: TextView = itemView.findViewById(R.id.diseaseNameText)
        val diseaseCategoryText: TextView = itemView.findViewById(R.id.diseaseCategoryText)
        val moreInfoArrow: ImageView = itemView.findViewById(R.id.moreInfoArrow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiseaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_disease_list, parent, false)
        return DiseaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiseaseViewHolder, position: Int) {
        val disease = diseases[position]

        holder.diseaseNameText.text = disease.name
        holder.diseaseCategoryText.text = disease.category
        holder.diseaseIcon.setImageResource(disease.iconResId)

        // Set category color based on type
        val categoryColor = when (disease.category) {
            "Nutritional Deficiency" -> R.color.nutrition_color
            "Bacterial Disease" -> R.color.bacterial_color
            "Viral Disease" -> R.color.viral_color
            "Fungal Disease" -> R.color.fungal_color
            "Physiological Disorder" -> R.color.physiological_color
            else -> R.color.default_category_color
        }

        holder.diseaseCategoryText.setTextColor(
            holder.itemView.context.resources.getColor(categoryColor, null)
        )

        // Click listener
        holder.cardView.setOnClickListener {
            onItemClick(disease)
        }

        // Add touch feedback
        holder.cardView.setOnTouchListener { view, event ->
            when (event.action) {
                android.view.MotionEvent.ACTION_DOWN -> {
                    view.animate()
                        .scaleX(0.98f)
                        .scaleY(0.98f)
                        .alpha(0.8f)
                        .setDuration(100)
                        .start()
                }
                android.view.MotionEvent.ACTION_UP,
                android.view.MotionEvent.ACTION_CANCEL -> {
                    view.animate()
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .alpha(1.0f)
                        .setDuration(100)
                        .start()
                }
            }
            false
        }
    }

    override fun getItemCount(): Int = diseases.size
}
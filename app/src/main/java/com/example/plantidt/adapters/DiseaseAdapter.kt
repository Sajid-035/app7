package com.example.plantidt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantidt.R
import com.example.plantidt.database.entities.CommonDisease

class DiseaseAdapter(
    private val diseases: List<CommonDisease>,
    private val onItemClick: (CommonDisease) -> Unit = {}
) : RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder>() {

    class DiseaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val diseaseIcon: ImageView = view.findViewById(R.id.diseaseIcon)
        val diseaseName: TextView = view.findViewById(R.id.diseaseNameText)
        val diseaseDescription: TextView = view.findViewById(R.id.diseaseDescriptionText)
        val symptomsText: TextView = view.findViewById(R.id.symptomCard)
        val treatmentText: TextView = view.findViewById(R.id.treatmentText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiseaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_disease, parent, false)
        return DiseaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiseaseViewHolder, position: Int) {
        val disease = diseases[position]

        holder.diseaseName.text = disease.name
        holder.diseaseDescription.text = disease.description

        // Set disease icon - use the iconResId from the entity, or fallback to type-based icons
        if (disease.iconResId != 0) {
            holder.diseaseIcon.setImageResource(disease.iconResId)
        } else {
            // Fallback to type-based icons
            when {
                disease.name.contains("fungal", ignoreCase = true) ->
                    holder.diseaseIcon.setImageResource(R.drawable.ic_fungal_disease)
                disease.name.contains("bacterial", ignoreCase = true) ->
                    holder.diseaseIcon.setImageResource(R.drawable.ic_bacterial_disease)
                disease.name.contains("viral", ignoreCase = true) ->
                    holder.diseaseIcon.setImageResource(R.drawable.ic_viral_disease)
                else ->
                    holder.diseaseIcon.setImageResource(R.drawable.ic_disease)
            }
        }

        // Set symptoms
        if (disease.symptoms.isNotEmpty()) {
            val context = holder.itemView.context
            holder.symptomsText.text = context.getString(R.string.symptoms_label, disease.symptoms.joinToString(", "))
            holder.symptomsText.visibility = View.VISIBLE
        } else {
            holder.symptomsText.visibility = View.GONE
        }

        // Set treatment
        if (disease.treatment.isNotEmpty()) {
            val context = holder.itemView.context
            holder.treatmentText.text = context.getString(R.string.treatment_label, disease.treatment.joinToString(", "))
            holder.treatmentText.visibility = View.VISIBLE
        } else {
            holder.treatmentText.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            onItemClick(disease)
        }
    }

    override fun getItemCount() = diseases.size
}
package com.example.plantidt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantidt.R
import com.example.plantidt.database.entities.CommonPest

class PestAdapter(
    private val pests: List<CommonPest>,
    private val onItemClick: (CommonPest) -> Unit = {}
) : RecyclerView.Adapter<PestAdapter.PestViewHolder>() {

    class PestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pestIcon: ImageView = view.findViewById(R.id.pestIcon)
        val pestName: TextView = view.findViewById(R.id.pestName)
        val pestDescription: TextView = view.findViewById(R.id.pestDescription)
        val signsText: TextView = view.findViewById(R.id.signsText)
        val treatmentText: TextView = view.findViewById(R.id.treatmentText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pest, parent, false)
        return PestViewHolder(view)
    }

    override fun onBindViewHolder(holder: PestViewHolder, position: Int) {
        val pest = pests[position]

        holder.pestName.text = pest.name
        holder.pestDescription.text = pest.description

        // Set pest icon based on type
        when {
            pest.name.contains("aphid", ignoreCase = true) ->
                holder.pestIcon.setImageResource(R.drawable.ic_aphids)
            pest.name.contains("spider mite", ignoreCase = true) ->
                holder.pestIcon.setImageResource(R.drawable.ic_spider_mites)
            pest.name.contains("scale", ignoreCase = true) ->
                holder.pestIcon.setImageResource(R.drawable.ic_scale)
            pest.name.contains("whitefly", ignoreCase = true) ->
                holder.pestIcon.setImageResource(R.drawable.ic_whiteflies)
            else ->
                holder.pestIcon.setImageResource(R.drawable.ic_pest_generic)
        }

        // Set signs
        if (pest.signs.isNotEmpty()) {
            holder.signsText.text = "Signs: ${pest.signs.joinToString(", ")}"
            holder.signsText.visibility = View.VISIBLE
        } else {
            holder.signsText.visibility = View.GONE
        }

        // Set treatment
        if (pest.treatment.isNotEmpty()) {
            holder.treatmentText.text = "Treatment: ${pest.treatment.joinToString(", ")}"
            holder.treatmentText.visibility = View.VISIBLE
        } else {
            holder.treatmentText.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            onItemClick(pest)
        }
    }

    override fun getItemCount() = pests.size
}

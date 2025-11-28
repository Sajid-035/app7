package com.example.plantidt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PestListAdapter(
    private val pestList: List<DiagnosisItem>,
    private val onItemClick: (DiagnosisItem) -> Unit
) : RecyclerView.Adapter<PestListAdapter.PestViewHolder>() {

    class PestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pestCard: CardView = itemView.findViewById(R.id.pestCard)
        val pestIcon: ImageView = itemView.findViewById(R.id.pestIcon)
        val pestName: TextView = itemView.findViewById(R.id.pestName)
        val pestCategory: TextView = itemView.findViewById(R.id.pestCategory)
        val arrowIcon: ImageView = itemView.findViewById(R.id.arrowIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pest_list, parent, false)
        return PestViewHolder(view)
    }

    override fun onBindViewHolder(holder: PestViewHolder, position: Int) {
        val pest = pestList[position]

        holder.pestIcon.setImageResource(pest.iconResId)
        holder.pestName.text = pest.name
        holder.pestCategory.text = "Plant Pest"

        // Add click listener
        holder.pestCard.setOnClickListener {
            onItemClick(pest)
        }

        // Add touch feedback
        holder.pestCard.setOnTouchListener { view, event ->
            when (event.action) {
                android.view.MotionEvent.ACTION_DOWN -> {
                    view.animate()
                        .scaleX(0.95f)
                        .scaleY(0.95f)
                        .setDuration(100)
                        .start()
                }
                android.view.MotionEvent.ACTION_UP,
                android.view.MotionEvent.ACTION_CANCEL -> {
                    view.animate()
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .setDuration(100)
                        .start()
                }
            }
            false
        }
    }

    override fun getItemCount(): Int = pestList.size
}
package com.example.plantidt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

// Data class for diagnosis items
data class DiagnosisItem(
    val name: String,
    val type: String,
    val iconResId: Int
)

// Adapter for diagnosis RecyclerView
class DiagnosisAdapter(
    private val items: List<DiagnosisItem>,
    private val onItemClick: (DiagnosisItem) -> Unit
) : RecyclerView.Adapter<DiagnosisAdapter.DiagnosisViewHolder>() {

    class DiagnosisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.diagnosisCard)
        val iconImageView: ImageView = itemView.findViewById(R.id.diagnosisIcon)
        val nameTextView: TextView = itemView.findViewById(R.id.diagnosisName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiagnosisViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_diagnosis, parent, false)
        return DiagnosisViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiagnosisViewHolder, position: Int) {
        val item = items[position]

        holder.nameTextView.text = item.name
        holder.iconImageView.setImageResource(item.iconResId)

        // Set click listener
        holder.cardView.setOnClickListener {
            onItemClick(item)
        }

        // Add touch feedback
        holder.cardView.setOnTouchListener { view, event ->
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

    override fun getItemCount(): Int = items.size
}
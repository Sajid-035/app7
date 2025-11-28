package com.example.plantidt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class GuideAdapter(
    private val guides: List<GuideItem>,
    private val onItemClick: (GuideType) -> Unit
) : RecyclerView.Adapter<GuideAdapter.GuideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_guide_card, parent, false)
        return GuideViewHolder(view)
    }

    override fun onBindViewHolder(holder: GuideViewHolder, position: Int) {
        holder.bind(guides[position])
    }

    override fun getItemCount() = guides.size

    inner class GuideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: CardView = itemView.findViewById(R.id.cardViewGuide)
        private val imageView: ImageView = itemView.findViewById(R.id.ivGuideIcon)
        private val textView: TextView = itemView.findViewById(R.id.tvGuideName)

        fun bind(guide: GuideItem) {
            textView.text = guide.name
            imageView.setImageResource(guide.iconRes)

            // Set card background color
            try {
                val color = android.graphics.Color.parseColor(guide.color)
                cardView.setCardBackgroundColor(color)
            } catch (e: IllegalArgumentException) {
                cardView.setCardBackgroundColor(
                    ContextCompat.getColor(itemView.context, R.color.default_card_color)
                )
            }

            // Set click listener
            cardView.setOnClickListener {
                onItemClick(guide.type)
            }

            // Add ripple effect
            cardView.isClickable = true
            cardView.isFocusable = true
        }
    }
}

// Data Classes
data class GuideItem(
    val name: String,
    val iconRes: Int,
    val type: GuideType,
    val color: String
)

enum class GuideType {
    WATERING, TEMPERATURE, SUNLIGHT, SOIL, FERTILIZING, REPOTTING,
    PEST, DISEASE, HUMIDITY, SOWING, PRUNING, HARVESTING, TILLING
}
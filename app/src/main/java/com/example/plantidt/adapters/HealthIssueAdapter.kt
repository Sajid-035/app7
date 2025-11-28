package com.example.plantidt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantidt.R

class HealthIssueAdapter(
    private val healthIssues: List<String>,
    private val recommendations: List<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ISSUE = 0
        private const val VIEW_TYPE_RECOMMENDATION = 1
        private const val VIEW_TYPE_HEADER = 2
    }

    private data class ListItem(
        val text: String,
        val type: Int
    )

    private val items = mutableListOf<ListItem>()

    init {
        // Add health issues section
        if (healthIssues.isNotEmpty()) {
            items.add(ListItem("Health Issues", VIEW_TYPE_HEADER))
            healthIssues.forEach { issue ->
                items.add(ListItem(issue, VIEW_TYPE_ISSUE))
            }
        }

        // Add recommendations section
        if (recommendations.isNotEmpty()) {
            items.add(ListItem("Recommendations", VIEW_TYPE_HEADER))
            recommendations.forEach { recommendation ->
                items.add(ListItem(recommendation, VIEW_TYPE_RECOMMENDATION))
            }
        }
    }

    class HealthIssueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val issueIcon: ImageView = itemView.findViewById(R.id.issueIcon)
        val issueText: TextView = itemView.findViewById(R.id.issueText)
    }

    class RecommendationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recommendationIcon: ImageView = itemView.findViewById(R.id.recommendationIcon)
        val recommendationText: TextView = itemView.findViewById(R.id.recommendationText)
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerText: TextView = itemView.findViewById(R.id.headerText)
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ISSUE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.health_issue_item, parent, false)
                HealthIssueViewHolder(view)
            }
            VIEW_TYPE_RECOMMENDATION -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recommendation, parent, false)
                RecommendationViewHolder(view)
            }
            VIEW_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_section_header, parent, false)
                HeaderViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        when (holder) {
            is HealthIssueViewHolder -> {
                holder.issueText.text = item.text

                // Set appropriate icon based on issue type
                val iconRes = when {
                    item.text.contains("yellow", ignoreCase = true) -> R.drawable.ic_leaf_yellow
                    item.text.contains("brown", ignoreCase = true) -> R.drawable.ic_leaf_brown
                    item.text.contains("droop", ignoreCase = true) ||
                            item.text.contains("wilt", ignoreCase = true) -> R.drawable.ic_plant_drooping
                    item.text.contains("spot", ignoreCase = true) ||
                            item.text.contains("disease", ignoreCase = true) -> R.drawable.ic_disease
                    item.text.contains("pest", ignoreCase = true) ||
                            item.text.contains("bug", ignoreCase = true) -> R.drawable.ic_pest
                    item.text.contains("root", ignoreCase = true) -> R.drawable.ic_roots
                    item.text.contains("growth", ignoreCase = true) -> R.drawable.ic_growth
                    else -> R.drawable.ic_warning
                }

                holder.issueIcon.setImageResource(iconRes)
            }

            is RecommendationViewHolder -> {
                holder.recommendationText.text = item.text

                // Set appropriate icon based on recommendation type
                val iconRes = when {
                    item.text.contains("water", ignoreCase = true) -> R.drawable.ic_water_drop
                    item.text.contains("light", ignoreCase = true) -> R.drawable.ic_sun
                    item.text.contains("fertilize", ignoreCase = true) -> R.drawable.ic_fertilizer
                    item.text.contains("prune", ignoreCase = true) -> R.drawable.ic_scissors
                    item.text.contains("repot", ignoreCase = true) -> R.drawable.ic_pot
                    item.text.contains("humidity", ignoreCase = true) -> R.drawable.ic_humidity
                    item.text.contains("drainage", ignoreCase = true) -> R.drawable.ic_drainage
                    item.text.contains("temperature", ignoreCase = true) -> R.drawable.ic_thermometer
                    item.text.contains("ventilation", ignoreCase = true) ||
                            item.text.contains("air", ignoreCase = true) -> R.drawable.ic_air_circulation
                    else -> R.drawable.ic_recommendation
                }

                holder.recommendationIcon.setImageResource(iconRes)
            }

            is HeaderViewHolder -> {
                holder.headerText.text = item.text
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
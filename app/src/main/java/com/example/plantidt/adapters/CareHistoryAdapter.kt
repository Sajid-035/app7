package com.example.plantidt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantidt.R
import com.example.plantidt.database.entities.CareHistoryItem
import com.example.plantidt.database.entities.CareActionType
import java.text.SimpleDateFormat
import java.util.Locale

class CareHistoryAdapter(private val careHistory: List<CareHistoryItem>) :
    RecyclerView.Adapter<CareHistoryAdapter.CareHistoryViewHolder>() {

    class CareHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val actionIcon: ImageView = view.findViewById(R.id.actionIcon)
        val actionText: TextView = view.findViewById(R.id.actionText)
        val dateText: TextView = view.findViewById(R.id.dateText)
        val notesText: TextView = view.findViewById(R.id.notesText)
        val nextDueText: TextView = view.findViewById(R.id.nextDueText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CareHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_care_history, parent, false)
        return CareHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CareHistoryViewHolder, position: Int) {
        val item = careHistory[position]

        // Set action icon and text based on enum
        when (item.actionType) {
            CareActionType.WATERING -> {
                holder.actionIcon.setImageResource(R.drawable.ic_water_drop)
                holder.actionText.text = "Watered"
            }
            CareActionType.FERTILIZING -> {
                holder.actionIcon.setImageResource(R.drawable.ic_fertilizer)
                holder.actionText.text = "Fertilized"
            }
            CareActionType.PRUNING -> {
                holder.actionIcon.setImageResource(R.drawable.ic_pruning)
                holder.actionText.text = "Pruned"
            }
            CareActionType.REPOTTING -> {
                holder.actionIcon.setImageResource(R.drawable.ic_repot)
                holder.actionText.text = "Repotted"
            }
            CareActionType.PEST_TREATMENT -> {
                holder.actionIcon.setImageResource(R.drawable.ic_care)
                holder.actionText.text = "Pest Treatment"
            }
            CareActionType.DISEASE_TREATMENT -> {
                holder.actionIcon.setImageResource(R.drawable.ic_care)
                holder.actionText.text = "Disease Treatment"
            }
            CareActionType.GENERAL_CARE -> {
                holder.actionIcon.setImageResource(R.drawable.ic_care)
                holder.actionText.text = "General Care"
            }
        }

        // Set date (assuming date is in "yyyy-MM-dd" format)
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val date = inputFormat.parse(item.date)
            holder.dateText.text = if (date != null) {
                outputFormat.format(date)
            } else {
                item.date
            }
        } catch (e: Exception) {
            // If date parsing fails, show the original date string
            holder.dateText.text = item.date
        }

        // Set notes (handle nullable)
        if (!item.notes.isNullOrEmpty()) {
            holder.notesText.text = item.notes
            holder.notesText.visibility = View.VISIBLE
        } else {
            holder.notesText.visibility = View.GONE
        }

        // Set next due date (handle nullable)
        if (!item.nextDueDate.isNullOrEmpty()) {
            try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val nextDate = inputFormat.parse(item.nextDueDate)
                holder.nextDueText.text = if (nextDate != null) {
                    "Next: ${outputFormat.format(nextDate)}"
                } else {
                    "Next: ${item.nextDueDate}"
                }
                holder.nextDueText.visibility = View.VISIBLE
            } catch (e: Exception) {
                // If date parsing fails, show the original date string
                holder.nextDueText.text = "Next: ${item.nextDueDate}"
                holder.nextDueText.visibility = View.VISIBLE
            }
        } else {
            holder.nextDueText.visibility = View.GONE
        }
    }

    override fun getItemCount() = careHistory.size
}
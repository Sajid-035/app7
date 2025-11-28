package com.example.plantidt.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantidt.R

data class CareHistoryItem(
    val date: String,
    val action: String
)

class CareHistoryAdapter(private val items: List<CareHistoryItem>) :
    RecyclerView.Adapter<CareHistoryAdapter.CareHistoryViewHolder>() {

    class CareHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateText: TextView = view.findViewById(R.id.dateText)
        val actionText: TextView = view.findViewById(R.id.actionText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CareHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_care_history, parent, false)
        return CareHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CareHistoryViewHolder, position: Int) {
        val item = items[position]
        holder.dateText.text = item.date
        holder.actionText.text = item.action
    }

    override fun getItemCount(): Int = items.size
}

package com.example.plantidt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantidt.R

class CareInstructionAdapter(
    private val instructions: List<String>
) : RecyclerView.Adapter<CareInstructionAdapter.CareInstructionViewHolder>() {

    class CareInstructionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val instructionIcon: ImageView = itemView.findViewById(R.id.instructionIcon)
        val instructionText: TextView = itemView.findViewById(R.id.instructionText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CareInstructionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_care_instruction, parent, false)
        return CareInstructionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CareInstructionViewHolder, position: Int) {
        val instruction = instructions[position]
        holder.instructionText.text = instruction

        // Set appropriate icon based on instruction content
        val iconRes = when {
            instruction.contains("water", ignoreCase = true) -> R.drawable.ic_water_drop
            instruction.contains("light", ignoreCase = true) ||
                    instruction.contains("sun", ignoreCase = true) -> R.drawable.ic_sun
            instruction.contains("fertilize", ignoreCase = true) ||
                    instruction.contains("feed", ignoreCase = true) -> R.drawable.ic_fertilizer
            instruction.contains("prune", ignoreCase = true) ||
                    instruction.contains("trim", ignoreCase = true) -> R.drawable.ic_scissors
            instruction.contains("repot", ignoreCase = true) -> R.drawable.ic_pot
            instruction.contains("humidity", ignoreCase = true) ||
                    instruction.contains("mist", ignoreCase = true) -> R.drawable.ic_humidity
            instruction.contains("temperature", ignoreCase = true) -> R.drawable.ic_thermometer
            instruction.contains("rotate", ignoreCase = true) -> R.drawable.ic_rotate
            instruction.contains("air", ignoreCase = true) ||
                    instruction.contains("circulation", ignoreCase = true) -> R.drawable.ic_air_circulation
            else -> R.drawable.ic_plant_care
        }

        holder.instructionIcon.setImageResource(iconRes)
    }

    override fun getItemCount(): Int = instructions.size
}
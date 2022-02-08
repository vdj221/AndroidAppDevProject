package edu.rosehulman.roselaundrytracker.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import edu.rosehulman.roselaundrytracker.LaundryInventoryFragment
import edu.rosehulman.roselaundrytracker.R


import edu.rosehulman.roselaundrytracker.model.ClothingItem
import edu.rosehulman.roselaundrytracker.model.ClothingViewModel

class ClothingInventoryAdapter(val fragment: LaundryInventoryFragment): RecyclerView.Adapter<ClothingInventoryAdapter.ClothingInventoryViewHolder>() {
    val model = ViewModelProvider(fragment.requireActivity()).get(ClothingViewModel::class.java)

    inner class ClothingInventoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val typeView: TextView = itemView.findViewById(R.id.row_clothing_type)
        val nameView: TextView = itemView.findViewById(R.id.row_clothing_name)
        val colorView: TextView = itemView.findViewById(R.id.row_clothing_color)
        val materialView : TextView = itemView.findViewById(R.id.row_quote_text_material)

        init {
            itemView.setOnLongClickListener {
                model.updateCurrentPos(adapterPosition)
                model.toggleCurrentItem()
                notifyItemChanged(adapterPosition)
                true
            }
        }

        fun bind(clothing: ClothingItem){
            typeView.text = clothing.type
            nameView.text = clothing.name
            colorView.text = clothing.color
            materialView.text = clothing.material
            itemView.setBackgroundColor(
                if(clothing.isSelected) {
                    MaterialColors.getColor(fragment.requireContext(), androidx.appcompat.R.attr.color,
                        Color.YELLOW)
                } else {
                    MaterialColors.getColor(fragment.requireContext(), androidx.appcompat.R.attr.color, Color.RED)
                }
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothingInventoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_clothing_inventory, parent, false)
        return ClothingInventoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClothingInventoryViewHolder, position: Int) {
        holder.bind(model.getClothingAt(position))
    }

     fun addClothing(clothingItem: ClothingItem){
        model.addClothing(clothingItem)
        notifyDataSetChanged()
    }

    override fun getItemCount() = model.size()
    fun deleteSelected() {
        model.deleteSelected()
        notifyDataSetChanged()
    }
}
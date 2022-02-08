package edu.rosehulman.roselaundrytracker.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import edu.rosehulman.roselaundrytracker.LaundryLoadFragment
import edu.rosehulman.roselaundrytracker.R
import edu.rosehulman.roselaundrytracker.model.ClothingItem
import edu.rosehulman.roselaundrytracker.model.ClothingViewModel
import edu.rosehulman.roselaundrytracker.model.LaundryLoad

import edu.rosehulman.roselaundrytracker.model.LaundryLoadViewModel

class LoadAdapter(val fragment: LaundryLoadFragment): RecyclerView.Adapter<LoadAdapter.LoadViewHolder>() {
    val model = ViewModelProvider(fragment.requireActivity()).get(LaundryLoadViewModel::class.java)
    val model2 = ViewModelProvider(fragment.requireActivity()).get(ClothingViewModel::class.java)



    inner class LoadViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val checkBox: ImageView = itemView.findViewById(R.id.is_checked_icon)
        val nameText: TextView = itemView.findViewById(R.id.clothing_name)
        init {
            checkBox.setOnClickListener {

            }
        }


        fun bind(load: LaundryLoad){
            
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_clothing_inventory, parent, false)
        return LoadViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LoadViewHolder, position: Int) {
        holder.bind(model.getLoadAt(position))
    }


    override fun getItemCount() = model.size()


    fun addLoad(laundryLoad: LaundryLoad) {
        model.addLoad(laundryLoad)
    }
}
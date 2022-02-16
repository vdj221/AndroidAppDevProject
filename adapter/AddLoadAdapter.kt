package edu.rosehulman.roselaundrytracker.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.roselaundrytracker.fragment.AddLoadFragment
import edu.rosehulman.roselaundrytracker.Constants
import edu.rosehulman.roselaundrytracker.R
import edu.rosehulman.roselaundrytracker.model.ClothingItem
import edu.rosehulman.roselaundrytracker.model.ClothingViewModel

import edu.rosehulman.roselaundrytracker.model.LaundryLoadViewModel

class AddLoadAdapter(val fragment: AddLoadFragment, var isEditLoadFragment: Boolean): RecyclerView.Adapter<AddLoadAdapter.LoadViewHolder>() {
    val model = ViewModelProvider(fragment.requireActivity()).get(LaundryLoadViewModel::class.java)
    val model2 = ViewModelProvider(fragment.requireActivity()).get(ClothingViewModel::class.java)
    //This adapter is for creating loads
    inner class LoadViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val checkBox: ImageView = itemView.findViewById(R.id.is_checked_icon)
        val nameText: TextView = itemView.findViewById(R.id.clothing_name)

        init {
            Log.d(Constants.TAG, model2.clothes.toString())
            checkBox.setOnClickListener {
                model2.updateCurrentPos(adapterPosition)
                model2.toggleCurrentItem()
                notifyDataSetChanged()
            }
        }

        fun bind(clothingItem: ClothingItem){
            nameText.text = clothingItem.name
            if(clothingItem.isSelected) {
                checkBox.setImageResource(R.drawable.ic_baseline_check_box_24)
            } else {
                checkBox.setImageResource(R.drawable.ic_baseline_check_box_outline_blank_24)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.laundry_item_selectable, parent, false)
        return LoadViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LoadViewHolder, position: Int) {
        Log.d(Constants.TAG, model2.clothes.toString())
        holder.bind(model2.getClothingAt(position))
    }

    fun addListener(fragmentName: String) {
        model2.addListener(fragmentName) {
            notifyDataSetChanged()
        }
        model.addListener(fragmentName) {
            notifyDataSetChanged()
        }
    }
    override fun getItemCount() = model2.size()

    fun addLoad(machineNumber: Int, machineType: String, time: Long) {
        val arr = getSelected()
        val newLoad = model.addLoad(machineNumber, machineType, arr, time) {
            notifyDataSetChanged()
        }
        for(item in newLoad.contents) {
            model2.ref.document(item.id).set(item)
        }
    }

    fun getSelected(): ArrayList<ClothingItem> {
        return model2.getSelected()
    }

    fun removeListener(fragmentName: String) {
        model2.removeListener(fragmentName)
        model.removeListener(fragmentName)
    }

    fun updateCurrentLoad(machineNumber: Int, machineType: String, time: Long) {
        model.updateCurrentLoad(machineNumber, machineType, getSelected(), time) {
            notifyDataSetChanged()
        }
    }

    fun deleteCurrentLoad() {
        model.deleteCurrentLoad()
        notifyDataSetChanged()
    }

}
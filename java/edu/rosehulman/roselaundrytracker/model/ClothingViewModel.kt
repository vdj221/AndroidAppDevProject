package edu.rosehulman.roselaundrytracker.model

import android.util.Log
import androidx.lifecycle.ViewModel

class ClothingViewModel: ViewModel() {
    val clothes = ArrayList<ClothingItem>()
    var curPos = 0


    fun updateCurrentPos(pos: Int) {
        curPos = pos
    }

    fun clear() {
        clothes.clear()
    }

    fun getClothingAt(pos: Int) = clothes[pos]
    fun getCurrentClothing() = getClothingAt(curPos)
    fun size() = clothes.size

    fun addClothing(clothingItem: ClothingItem) {
        clothes.add(clothingItem)
    }





    fun updateCurrentQuote(type: String, color: String, name:String, material:String) {
        clothes[curPos].type = type
        clothes[curPos].color = color
        clothes[curPos].name = name
        clothes[curPos].material = material

    }

    fun removeCurrentQuote() {
       clothes.remove(getCurrentClothing())
    }

    fun toggleCurrentItem() {
        getCurrentClothing().isSelected = !getCurrentClothing().isSelected
    }

    fun deleteSelected() {
        val toDelete = ArrayList<ClothingItem>()
        for(c in clothes) {
            if(c.isSelected) {
                toDelete.add(c)
            }
        }
        for(x in toDelete) {
            clothes.remove(x)
        }
    }



}
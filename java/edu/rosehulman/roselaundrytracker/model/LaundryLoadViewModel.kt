package edu.rosehulman.roselaundrytracker.model

import androidx.lifecycle.ViewModel

class LaundryLoadViewModel: ViewModel() {
    var loads = ArrayList<LaundryLoad>()
    var curPos = 0

    fun addLoad(newLoad: LaundryLoad){
        loads.add(newLoad)
    }

    fun getLoadAt(position: Int) = loads[position]

    fun getCurrentLoad() = getLoadAt(curPos)

    fun size() = loads.size

    fun addToCurrentLoad(clothingItem: ClothingItem) {
        loads[curPos].addToLoad(clothingItem)
    }

    fun updateCurrentPos(adapterPosition: Int) {
        curPos = adapterPosition
    }
}
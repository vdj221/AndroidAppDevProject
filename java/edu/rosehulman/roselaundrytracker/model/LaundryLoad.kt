package edu.rosehulman.roselaundrytracker.model

import android.os.CountDownTimer

data class LaundryLoad(var machineNumber: Int, var machineType: String, var contents: ArrayList<ClothingItem>, var timer: CountDownTimer  ) {
    fun addToLoad(clothingItem: ClothingItem) {
        contents.add(clothingItem)
    }
}

package edu.rosehulman.roselaundrytracker.model

data class ClothingItem(var name: String, var type: String, var color: String, var material: String, var isWearable:Boolean = false, var isSelected:Boolean = false) {
}
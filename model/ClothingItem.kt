package edu.rosehulman.roselaundrytracker.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp

data class ClothingItem(var name: String = "", var type: String = "", var color: String = "", var material: String = "", var isWearable:Boolean = false, var isSelected:Boolean = false) {
    var owner = ""
    @get:Exclude
    var id = ""
    var load = ""
    @ServerTimestamp
    var created: Timestamp? = null
    companion object {
        const val COLLECTION_PATH = "clothes"

        fun from(snapshot: DocumentSnapshot?): ClothingItem {
            val item = snapshot?.toObject(ClothingItem::class.java)!!
            item.id = snapshot.id
            return item
        }
    }
}
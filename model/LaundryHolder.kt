package edu.rosehulman.roselaundrytracker.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp

data class LaundryHolder(var machineNumber: Int = -1, var isDryer: Boolean = false, var time: Long = 69420, var owner: String = "") {

    @get:Exclude
    var id = ""
    @ServerTimestamp
    var created: Timestamp? = null
}
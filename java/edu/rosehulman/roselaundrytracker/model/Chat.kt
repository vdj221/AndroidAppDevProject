package edu.rosehulman.roselaundrytracker.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp

data class Chat(val contents: String, val sender: String, val receiver: String) {
    @get:Exclude
    var id = ""
    @ServerTimestamp
    var created: Timestamp? = null

    companion object {
        fun from(snapshot: DocumentSnapshot?): Chat {
            val chat = snapshot?.toObject(Chat::class.java)!!
            chat.id = snapshot.id
            return chat
        }
        const val CREATED_KEY = "created"
    }
}
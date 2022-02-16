package edu.rosehulman.roselaundrytracker.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp

data class Chat(val contents: String = "", val sender: String = "", val messengers: ArrayList<String> = ArrayList()) {
    @get:Exclude
    var id = ""
    @ServerTimestamp
    var created: Timestamp? = null
    fun createdTime() = created!!.toDate()
    fun notMe(me: User): String {
        if(sender == me.name) {
            for(person in messengers) {
                if(person != me.name) {
                    return person
                }
            }
        }
        return sender
    }
    companion object {
        fun from(snapshot: DocumentSnapshot?): Chat {
            val chat = snapshot?.toObject(Chat::class.java)!!
            chat.id = snapshot.id
            return chat
        }
        const val COLLECTION_PATH = "chats"
    }
}
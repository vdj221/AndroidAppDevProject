package edu.rosehulman.roselaundrytracker.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.rosehulman.roselaundrytracker.Constants
import java.util.*

class ChatViewModel: ViewModel() {
    private lateinit var user: User
    private lateinit var otherUser: User
    private val messages = ArrayList<Chat>()
    fun getUserId() = user.uid
    fun getMsgAt(pos: Int) = messages[pos]
    fun size() = messages.size
    fun addMsg(chat: Chat) {
        messages.add(chat)
    }
    fun clear() {
        messages.clear()
    }

    lateinit var ref: CollectionReference
    fun addListener(fragmentName: String, observer: () -> Unit) {
        val uid = Firebase.auth.uid!!
        ref = Firebase.firestore.collection(Constants.CHAT_PATH)
        Log.d(Constants.TAG, "Retrieving chats")
        val subscription = ref
            .orderBy(Chat.CREATED_KEY, Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot: QuerySnapshot?, error: FirebaseFirestoreException? ->
                error?.let {
                    Log.d(Constants.TAG, "Error: $it")
                    return@addSnapshotListener
                }
                clear()

            }
    }
}
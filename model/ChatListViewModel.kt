package edu.rosehulman.roselaundrytracker.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.rosehulman.roselaundrytracker.Constants

class ChatListViewModel: ViewModel() {
    lateinit var me: User
    lateinit var ref: CollectionReference
    val users = HashMap<String, User>()
    val lastChats = ArrayList<Chat>()
    private val subscriptions = HashMap<String, ListenerRegistration>()

    fun size() = lastChats.size

    fun addListener(fragmentName: String, observer: () -> Unit) {
        val lastMsgs = HashMap<User, Chat>()
        val uid = Firebase.auth.currentUser!!.uid
        val usersRef = Firebase.firestore.collection(User.COLLECTION_PATH)
        ref = Firebase.firestore.collection(Chat.COLLECTION_PATH)
        usersRef.addSnapshotListener { snapshot: QuerySnapshot?, error: FirebaseFirestoreException? ->
            error?.let {
                Log.e(Constants.TAG, "Error: $it")
                return@addSnapshotListener
            }
            snapshot?.documents?.forEach {
                var user = User.from(it)
                users[user.name] = user
            }
            for(user in users.values) {
                if(user.id == uid) {
                    me = user
                    break
                }
            }
            val query = ref.whereArrayContains("messengers", me.id)
            val subscription = query
                .orderBy(Constants.CREATED_KEY, Query.Direction.ASCENDING)
                .addSnapshotListener { snapshot: QuerySnapshot?, error: FirebaseFirestoreException? ->
                    error?.let {
                        Log.e(Constants.TAG, "Error: $it")
                        return@addSnapshotListener
                    }
                    snapshot?.documents?.forEach {
                        var newChat = Chat.from(it)
                        var u = newChat.notMe(me)
                        lastMsgs[users[u]!!] = newChat
                    }
                    for (msg in lastMsgs.values) {
                        lastChats.add(msg)
                    }
                }
            observer()
            subscriptions[fragmentName] = subscription
        }
    }

    fun getMsgAt(position: Int) = lastChats[position]
}
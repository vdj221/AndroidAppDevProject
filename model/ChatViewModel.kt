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
    private lateinit var uid: String
    private lateinit var me: User
    private lateinit var uid2: String
    private lateinit var u: User
    private val messages = ArrayList<Chat>()
    private val users = HashMap<String, User>()
    private val subscriptions = HashMap<String, ListenerRegistration>()
    private lateinit var ref: CollectionReference
    var curPos = 0
    fun getMsgAt(pos: Int) = messages[pos]
    fun size() = messages.size

    fun addMsg(contents: String, receiver: String) {
        val messengers = arrayListOf(me.name, receiver)
        Log.d(Constants.TAG, "Messengers array: $messengers")
        ref.add(Chat(contents, me.name, messengers))
    }


    fun addListener(fragmentName: String, receiver: String, observer: () -> Unit) {
        val userRef = Firebase.firestore.collection(User.COLLECTION_PATH)
        uid = Firebase.auth.currentUser!!.uid
        ref = Firebase.firestore.collection(Constants.CHAT_PATH)
        me = User(Firebase.auth.currentUser!!.displayName!!,Firebase.auth.currentUser!!.email!!,true)
        me.id = uid
        userRef.addSnapshotListener { snapshot: QuerySnapshot?, error: FirebaseFirestoreException? ->
            error?.let {
                Log.d(Constants.TAG, "Error: $it")
                return@addSnapshotListener
            }
            Log.d(Constants.TAG, "Retrieving users")
            snapshot?.documents?.forEach{
                Log.d(Constants.TAG, "Creating user for snapshot $it")
                val newUser = User.from(it)
                Log.d(Constants.TAG, "New user: $newUser")
                users[newUser.name] = newUser
                Log.d(Constants.TAG, "UID: ${newUser.id}")
                Log.d(Constants.TAG, "Users Hashmap: $users")
                Log.d(Constants.TAG, "Users[recevier] = ${users[receiver].toString()}")
            }
            Log.d(Constants.TAG, "HashMap Contents: $users")
            u = users[receiver]!!
            Log.d(Constants.TAG, "User ID: ${u.id}")
            uid2 = u.id
            Log.d(Constants.TAG, "Retrieving chats")
            var query = ref.whereArrayContains("messengers",me.name)
            val subscription = query
                .orderBy(Constants.CREATED_KEY, Query.Direction.ASCENDING)
                .addSnapshotListener { snapshot: QuerySnapshot?, error: FirebaseFirestoreException? ->
                    error?.let {
                        Log.d(Constants.TAG, "Error: $it")
                        return@addSnapshotListener
                    }
                    Log.d(Constants.TAG, "Documents: ${snapshot?.documents?.size}")
                    messages.clear()
                    snapshot?.documents?.forEach {
                        var newChat = Chat.from(it)
                        if(newChat.messengers.contains(receiver))
                            messages.add(newChat)
                    }
                    observer()
                    Log.d(Constants.TAG, "Messages size: ${messages.size}")
                }
            subscriptions[fragmentName] = subscription
        }
    }

    fun removeListener(fragmentName: String) {
        subscriptions[fragmentName]?.remove()
        subscriptions.remove(fragmentName)
    }
}
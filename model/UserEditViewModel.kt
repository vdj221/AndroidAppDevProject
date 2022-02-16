package edu.rosehulman.roselaundrytracker.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.rosehulman.roselaundrytracker.Constants

class UserEditViewModel: ViewModel() {
    fun update(newName: String, newEmail: String, newHasCompletedSetup: Boolean) {
        if(user == null) {
            user = User(newName, newEmail)
            ref.set(this)
        } else {
            with(user!!) {
                name = newName
                email = newEmail
                hasCompletedSetup = newHasCompletedSetup
                ref.set(this)
            }
        }
    }
    lateinit var uid: String
    var user: User? = null
    lateinit var ref: DocumentReference

    fun getOrMakeUser(observer: () -> Unit) {
        uid = Firebase.auth.currentUser!!.uid
        ref = Firebase.firestore.collection(User.COLLECTION_PATH).document(uid)
        if(user != null) {
            observer()
        } else {
            Log.d(Constants.TAG, "Adding success listener")
            ref.get().addOnSuccessListener { snapshot: DocumentSnapshot ->
                Log.d(Constants.TAG, "Filling new user")
                if (snapshot.exists()) {
                    Log.d(Constants.TAG,"Snapshot: $snapshot")
                    user = snapshot.toObject(User::class.java)
                } else {
                    user = User(Firebase.auth.currentUser!!.displayName!!)
                    ref.set(user!!)
                }
                user!!.hasCompletedSetup = true
                observer()
            }
        }
    }

    fun getHasCompletedSetup() = user?.hasCompletedSetup?: false
}
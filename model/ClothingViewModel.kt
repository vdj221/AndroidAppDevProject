package edu.rosehulman.roselaundrytracker.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.rosehulman.roselaundrytracker.Constants

class ClothingViewModel: ViewModel() {
    val clothes = ArrayList<ClothingItem>()
    var curPos = 0
    lateinit var uid: String
    val subscriptions = HashMap<String, ListenerRegistration>()
    lateinit var ref: CollectionReference

    fun updateCurrentPos(pos: Int) {
        curPos = pos
    }

    fun clear() {
        clothes.clear()
    }

    fun getClothingAt(pos: Int) = clothes[pos]
    fun getCurrentClothing() = getClothingAt(curPos)
    fun size() = clothes.size

    fun addClothing(clothingItem: ClothingItem) {
        clothingItem.owner = uid
        ref.add(clothingItem)
    }

    fun removeCurrentItem() {
        ref.document(getCurrentClothing().id).delete()
        curPos = 0
    }

    fun toggleCurrentItem() {
        getCurrentClothing().isSelected = !getCurrentClothing().isSelected
    }

    fun deleteSelected() {
        val toDelete = ArrayList<ClothingItem>()
        for(c in clothes) {
            if(c.isSelected) {
                toDelete.add(c)
            }
        }
        for(x in toDelete) {
            clothes.remove(x)
            ref.document(x.id).delete()
        }
    }

    fun deselectAll() {
        for(item in clothes)
            item.isSelected = false
    }

    fun getSelected(): ArrayList<ClothingItem> {
        val selected = ArrayList<ClothingItem>()
        for(item in clothes) {
            if(item.isSelected) selected.add(item)
        }
        deselectAll()
        return selected
    }

    fun addListener(fragmentName: String, observer: () -> Unit) {
        val auth = Firebase.auth
        val user = auth.currentUser!!
        uid = user.uid
        ref = Firebase.firestore.collection(ClothingItem.COLLECTION_PATH)
        val query = ref.whereEqualTo("owner", uid)
        clear()
        val subscription = query
            .addSnapshotListener { snapshot: QuerySnapshot?, error: FirebaseFirestoreException? ->
                error?.let {
                    Log.d(Constants.TAG, "Error: $it")
                    return@addSnapshotListener
                }
                clothes.clear()
                snapshot?.documents?.forEach {
                    clothes.add(ClothingItem.from(it))
                }
                observer()
            }
        subscriptions[fragmentName] = subscription
    }

    fun removeListener(fragmentName: String) {
        subscriptions[fragmentName]?.remove()
        subscriptions.remove(fragmentName)
    }

    fun getAllNotInLoads(): ArrayList<ClothingItem> {
        val out = ArrayList<ClothingItem>()
        for(item in clothes) {
            if(item.load.isBlank()) {
                out.add(item)
            }
        }
        return out
    }


}
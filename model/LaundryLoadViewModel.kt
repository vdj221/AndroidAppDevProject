package edu.rosehulman.roselaundrytracker.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.rosehulman.roselaundrytracker.Constants
import edu.rosehulman.roselaundrytracker.fragment.LaundryLoadFragment
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

class LaundryLoadViewModel: ViewModel() {
    private var loads = ArrayList<LaundryLoad>()
    private var curPos = 0
    lateinit var ref: CollectionReference
    lateinit var uid: String
    private var onlyOwned = true
    private val subscriptions = HashMap<String, ListenerRegistration>()
    fun getPreference() = onlyOwned
    fun addLoad(machineNumber: Int, machineType: String, contents: ArrayList<ClothingItem>, time: Long, observer: () -> Unit): LaundryLoad {
        var holder = LaundryHolder(machineNumber, machineType.lowercase(Locale.getDefault()) == "dryer", time * LaundryLoadFragment.SEC_TO_MIN, uid)
//        val query = ref.whereEqualTo("owner",id).whereEqualTo("machineNumber",machineNumber)
        holder.id = newId()
        ref.document(holder.id).set(holder)
        var load = LaundryLoad(holder,observer)
        load.addMany(contents)
        return load
    }
    
    fun updateCurrentLoad(machineNumber: Int, machineType: String, contents: ArrayList<ClothingItem>, time: Long, observer: () -> Unit){
        val holder = LaundryHolder(machineNumber, machineType.lowercase(Locale.getDefault()) == "dryer", time * LaundryLoadFragment.SEC_TO_MIN, uid)
        holder.id = getCurrentLoad().getId()
        val load = LaundryLoad(holder, observer)
        load.addMany(contents)
        ref.document(getCurrentLoad().getId()).set(load.laundryHolder)
    }

    fun getLoadAt(position: Int) = loads[position]

    fun getCurrentLoad() = getLoadAt(curPos)

    fun size() = loads.size

    fun addToCurrentLoad(clothingItem: ClothingItem) {
        loads[curPos].addToLoad(clothingItem)
    }

    fun deleteCurrentLoad() {
        for(item in loads[curPos].contents) {
            item.load = ""
        }
        ref.document(getCurrentLoad().getId()).delete()
        curPos = 0
    }

    fun updateCurrentPos(adapterPosition: Int) {
        curPos = adapterPosition
    }

    fun setObserver(function: () -> Unit) {
        for(load in loads) {
            load.observer = function
        }
    }

    fun addListener(fragmentName: String, observer: () -> Unit) {
        lateinit var subscription: ListenerRegistration
        loads.clear()
        val auth = Firebase.auth
        val user = auth.currentUser!!
        val clothes = ArrayList<ClothingItem>()
        uid = user.uid
        ref = Firebase.firestore.collection(LaundryLoad.COLLECTION_PATH)
        val ref2 = Firebase.firestore.collection(ClothingItem.COLLECTION_PATH)
        val inLoadQuery = ref2.whereNotEqualTo("load","")
        inLoadQuery.addSnapshotListener { snapshot: QuerySnapshot?, error: FirebaseFirestoreException? ->
            error?.let {
                Log.d(Constants.TAG, "Error: $it")
                return@addSnapshotListener
            }
            clothes.clear()
            snapshot?.documents?.forEach {
                clothes.add(ClothingItem.from(it))
            }
        }
        if(onlyOwned) {
            val query = ref.whereEqualTo("owner",uid)
            subscription = query
                .addSnapshotListener { snapshot: QuerySnapshot?, error: FirebaseFirestoreException? ->
                    error?.let {
                        Log.d(Constants.TAG, "Error: $it")
                        return@addSnapshotListener
                    }
                    loads.clear()
                    Log.d(Constants.TAG,"Documents: ${snapshot?.documents?.size}")
                    retrieveLoads(snapshot, clothes, observer)
                    Log.d(Constants.TAG,"Loads: ${loads.size}")
                }
        } else {
            subscription = ref
                .addSnapshotListener { snapshot: QuerySnapshot?, error: FirebaseFirestoreException? ->
                    error?.let {
                        Log.d(Constants.TAG, "Error: $it")
                        return@addSnapshotListener
                    }
                    loads.clear()
                    Log.d(Constants.TAG,"Documents: ${snapshot?.documents?.size}")
                    retrieveLoads(snapshot, clothes, observer)
                    Log.d(Constants.TAG,"Loads: ${loads.size}")
                }
        }
        subscriptions[fragmentName] = subscription
        observer()
    }

    private fun retrieveLoads(snapshot: QuerySnapshot?, clothes: ArrayList<ClothingItem>, observer: () -> Unit) {
        snapshot?.documents?.forEach {
            loads.add(LaundryLoad.from(it, observer))
        }
        for (load in loads) {
            for (item in clothes) {
                if (item.load == load.getId()) {
                    load.addToLoad(item)
                }
            }
        }
    }

    fun removeListener(fragmentName: String) {
        for(load in loads) {
            ref.document(load.getId()).set(load.laundryHolder)
        }
        subscriptions[fragmentName]?.remove()
        subscriptions.remove(fragmentName)
    }

    fun togglePreference() {
        onlyOwned = !onlyOwned
    }

    companion object {
        const val ID_LENGTH = 20
        fun newId(): String {
            var id = ""
            val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
            while(id.length < ID_LENGTH) {
                id += charset.random().toString()
            }
            return id
        }
    }
}
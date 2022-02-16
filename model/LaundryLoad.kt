package edu.rosehulman.roselaundrytracker.model

import android.os.CountDownTimer
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import edu.rosehulman.roselaundrytracker.Constants
import edu.rosehulman.roselaundrytracker.fragment.LaundryLoadFragment

data class LaundryLoad(val laundryHolder: LaundryHolder, var observer: () -> Unit) {
    var timeString = ""
    var contents = ArrayList<ClothingItem>()
    lateinit var timer: CountDownTimer
    fun addToLoad(clothingItem: ClothingItem) {
        contents.add(clothingItem)
        clothingItem.load = laundryHolder.id
    }

    fun addMany(clothes: Collection<ClothingItem>) {
        for(item in clothes) {
            addToLoad(item)
        }
    }

    fun updateTime(newTimeString: String) {
        timeString = newTimeString
        laundryHolder.time--
        Log.d(Constants.TAG, "Time left: $newTimeString")
    }

    init {
        timer = object : CountDownTimer(
            laundryHolder.time * LaundryLoadFragment.MILLI_TO_SEC,
            LaundryLoadFragment.MILLI_TO_SEC
        ) {

            override fun onTick(millisUntilFinished: Long) {
                var min = millisUntilFinished / LaundryLoadFragment.MILLI_TO_MIN
                var sec = (millisUntilFinished / LaundryLoadFragment.MILLI_TO_SEC) % 60
                var secText = ""
                if (sec < 10) {
                    secText += "0"
                }
                secText += sec.toString()
                var ts = "$min:$secText"
                //Log.d(Constants.TAG, "Time left: $ts")
                updateTime(ts)
                if(observer != null) {
                    observer!!()
                }
            }

            override fun onFinish() {
                Log.d(Constants.TAG, "Done")
                updateTime("Done")
                if(observer != null) {
                    observer!!()
                }
            }
        }.start()
        if(observer != null) {
            observer!!()
        }
    }

    fun setId(id: String) {
        laundryHolder.id = id
    }

    fun getId() = laundryHolder.id
    fun getOwner() = laundryHolder.owner
    fun getMachineNumber() = laundryHolder.machineNumber
    fun getMachineType(): String {
        if(laundryHolder.isDryer) {
            return "Dryer"
        } else {
            return "Washer"
        }
    }

    fun size() = contents.size

    companion object {
        fun from(snapshot: DocumentSnapshot?, observer: () -> Unit): LaundryLoad {
            val holder = snapshot?.toObject(LaundryHolder::class.java)!!
            val load = LaundryLoad(holder, observer)
            load.setId(snapshot!!.id)
            return load
        }
        const val COLLECTION_PATH = "loads"
    }
}

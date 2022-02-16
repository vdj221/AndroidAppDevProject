package edu.rosehulman.roselaundrytracker.model

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import edu.rosehulman.roselaundrytracker.Constants

data class User(
    var name: String = "",
    var email: String = "",
    var hasCompletedSetup: Boolean = false
) {
/*    var hall: Hall
//    init {
//        val hallName = resHall.lowercase()
//        when(hallName) {
//            "deming" -> {
//                hall = Hall.Deming
//            }
//            "bsb" -> {
//                hall = Hall.BSB
//            }
//            "speed" -> {
//                hall = Hall.Speed
//            }
//            "percopo" -> {
//                hall = Hall.Percopo
//            }
//            "mees" -> {
//                hall = Hall.Mees
//            }
//            "scharpenberg" -> {
//                hall = Hall.Scharpenberg
//            }
//            "blumberg" -> {
//                hall = Hall.Blumberg
//            }
//            "apartments" -> {
//                hall = Hall.APARTMENTS
//            }
//            "lakeside" -> {
//                hall = Hall.LAKESIDE
//            }
//            else -> {
//                hall = Hall.OFF_CAMPUS
//            }
//        }
//    }
//    enum class Hall {
//        Deming,
//        BSB,
//        Speed,
//        Percopo,
//        Mees,
//        Blumberg,
//        Scharpenberg,
//        APARTMENTS,
//        LAKESIDE,
//        OFF_CAMPUS;
//    } */
    @get:Exclude
    lateinit var id: String
    @ServerTimestamp
    var created: Timestamp? = null
    companion object{
        const val COLLECTION_PATH = "users"
        fun from(snapshot: DocumentSnapshot?): User {
            Log.d(Constants.TAG,"Snapshot: $snapshot")
            val user = snapshot?.toObject(User::class.java)
            user!!.id = snapshot.id
            return user
        }
    }
}
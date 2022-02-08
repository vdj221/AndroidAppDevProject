package edu.rosehulman.roselaundrytracker.model

data class User(
    var name: String = "",
    var age: Int = -1,
    var major: String = "CpE",
    var hasCompletedSetup: Boolean = false,
    var storageUriString: String = ""
) {
    lateinit var uid: String
    companion object{
        const val COLLECTION_PATH = "users"
    }
}
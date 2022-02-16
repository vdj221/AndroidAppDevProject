package edu.rosehulman.roselaundrytracker

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.rosehulman.roselaundrytracker.databinding.ActivityMainBinding
import edu.rosehulman.roselaundrytracker.model.LaundryLoadViewModel
import edu.rosehulman.roselaundrytracker.model.UserEditViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeAuthListener()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment_activity_main)
    }

    override fun onStart() {
        super.onStart()
        Firebase.auth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        Firebase.auth.removeAuthStateListener(authStateListener)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_home_shortcut -> {
                navController.navigate(R.id.navigation_main_page)
                true
            }
            R.id.action_user_settings -> {
                navController.navigate(R.id.navigation_user_edit)
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeAuthListener() {
        authStateListener = FirebaseAuth.AuthStateListener { auth: FirebaseAuth ->
            val user = auth.currentUser
            if(user == null) {
                setupAuthUI()
            } else {
                with(user) {
                    Log.d(Constants.TAG, "User: $uid")
                }
                val userModel = ViewModelProvider(this).get(UserEditViewModel::class.java)
                userModel.getOrMakeUser {
                    if(userModel.getHasCompletedSetup()) {
                        val id = navController.currentDestination!!.id
                        if(id == R.id.navigation_splash) {
                            navController.navigate(R.id.navigation_main_page)
                        }
                    } else {
                        navController.navigate(R.id.navigation_user_edit)
                    }
                }
            }
        }
    }

    private fun setupAuthUI() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .setLogo(R.mipmap.laundry_machine_clipart)
            .build()
        signInLauncher.launch(signInIntent)
    }
}
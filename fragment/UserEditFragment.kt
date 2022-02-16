package edu.rosehulman.roselaundrytracker.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.rosehulman.roselaundrytracker.Constants
import edu.rosehulman.roselaundrytracker.databinding.FragmentUserEditBinding
import edu.rosehulman.roselaundrytracker.model.User
import edu.rosehulman.roselaundrytracker.model.UserEditViewModel

class UserEditFragment : Fragment() {
    private lateinit var binding: FragmentUserEditBinding
    private lateinit var model: UserEditViewModel

    companion object {
        const val fragmentName = "UserEditFragment"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentUserEditBinding.inflate(inflater, container, false)
        model = ViewModelProvider(requireActivity()).get(UserEditViewModel::class.java)
        model.getOrMakeUser {
            with(model.user!!) {
                Log.d(Constants.TAG, "$this")
                binding.newName.setText(name)
                binding.newEmail.setText(email)
            }
        }
        binding.logoutButton.setOnClickListener {
            Firebase.auth.signOut()
        }
        binding.updateButton.setOnClickListener {
            with(binding) {
                model.update(newName.text.toString(),newEmail.text.toString(),true)
            }
        }
        return binding.root
    }

}
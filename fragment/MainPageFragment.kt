package edu.rosehulman.roselaundrytracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.rosehulman.roselaundrytracker.R
import edu.rosehulman.roselaundrytracker.databinding.FragmentMainPageBinding

class MainPageFragment: Fragment() {
    lateinit var binding:FragmentMainPageBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainPageBinding.inflate(inflater, container, false)
//        binding.chatButton.setOnClickListener{
//            findNavController().navigate(R.id.navigation_chat_list)
//        }
        binding.laundryInventoryButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_laundry_inventory)
        }
        binding.laundryInstructionsButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_laundry_instructions)
        }
        binding.currentLoadButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_current_load)
        }
        return binding.root
    }
}
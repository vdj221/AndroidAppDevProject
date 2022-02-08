package edu.rosehulman.roselaundrytracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import edu.rosehulman.roselaundrytracker.databinding.FragmentInstructionsMainPageBinding

class InstructionsMainPageFragment: androidx.fragment.app.Fragment() {
    private lateinit var binding: FragmentInstructionsMainPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInstructionsMainPageBinding.inflate(inflater, container, false)
        binding.washingInstructionsButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_washing_instructions)
        }
        binding.dryingInstructionsButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_drying_instructions)
        }
        return binding.root
    }
}
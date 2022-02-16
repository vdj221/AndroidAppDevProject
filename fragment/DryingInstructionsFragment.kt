package edu.rosehulman.roselaundrytracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.rosehulman.roselaundrytracker.databinding.FragmentDryingInstructionsBinding

class DryingInstructionsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDryingInstructionsBinding.inflate(inflater, container, false).root
}
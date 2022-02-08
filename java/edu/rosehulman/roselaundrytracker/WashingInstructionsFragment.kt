package edu.rosehulman.roselaundrytracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.rosehulman.roselaundrytracker.databinding.FragmentWashingInstructionsBinding

class WashingInstructionsFragment: Fragment() {override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentWashingInstructionsBinding
    .inflate(inflater, container, false).root
}

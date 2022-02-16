package edu.rosehulman.roselaundrytracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.rosehulman.roselaundrytracker.R
import edu.rosehulman.roselaundrytracker.adapter.AddLoadAdapter
import edu.rosehulman.roselaundrytracker.databinding.FragmentAddLoadBinding

open class AddLoadFragment: Fragment() {
    private lateinit var binding: FragmentAddLoadBinding
    open lateinit var adapter: AddLoadAdapter
    companion object {
        const val fragmentName = "AddLoadFragment"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddLoadBinding.inflate(inflater, container, false)
        adapter = AddLoadAdapter(this, false)
        adapter.addListener(fragmentName)
        binding.clothingList.adapter = adapter
        binding.clothingList.layoutManager = LinearLayoutManager(requireContext())
        binding.confirmButton.setOnClickListener {
            with(binding!!) {
                adapter.addLoad(machineNumberEditText.text.toString().toInt(), machineTypeEditText.text.toString(),machineTimerEditText.text.toString().toLong()!!)
                findNavController().navigate(R.id.navigation_current_load)
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.removeListener(fragmentName)
    }
}
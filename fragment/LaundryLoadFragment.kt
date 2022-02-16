package edu.rosehulman.roselaundrytracker.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.rosehulman.roselaundrytracker.R
import edu.rosehulman.roselaundrytracker.adapter.LaundryLoadAdapter
import edu.rosehulman.roselaundrytracker.databinding.FragmentLaundryLoadBinding

class LaundryLoadFragment: Fragment() {
    lateinit var binding: FragmentLaundryLoadBinding
    lateinit var adapter: LaundryLoadAdapter

    companion object {
        const val MILLI_TO_MIN: Long = 60000
        const val MILLI_TO_SEC: Long = 1000
        const val SEC_TO_MIN: Long = 60
        const val fragmentName = "LaundryLoadFragment"
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentLaundryLoadBinding.inflate(inflater, container,false)
            adapter = LaundryLoadAdapter(this)
            adapter.addListener(fragmentName)
            binding.loadRecyclerView.adapter = adapter
            binding.loadRecyclerView.setHasFixedSize(true)
            binding.loadRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.fab.setOnClickListener {
                findNavController().navigate(R.id.navigation_add_load)
            }
            binding.toggleFab.setOnClickListener {
                adapter.togglePreferences(fragmentName)
                if(adapter.getPreference()) {
                    binding.toggleFab.setImageResource(R.drawable.ic_baseline_toggle_on_24)
                } else {
                    binding.toggleFab.setImageResource(R.drawable.ic_baseline_toggle_off_24)
                }
            }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.removeListener(fragmentName)
    }




}
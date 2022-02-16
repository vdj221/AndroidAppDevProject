package edu.rosehulman.roselaundrytracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import edu.rosehulman.roselaundrytracker.adapter.ClothingInventoryAdapter
import edu.rosehulman.roselaundrytracker.databinding.FragmentLaundryInventoryBinding
import android.app.AlertDialog
import android.content.DialogInterface

import android.widget.EditText
import android.widget.LinearLayout
import edu.rosehulman.roselaundrytracker.model.ClothingItem


class LaundryInventoryFragment: Fragment() {
    lateinit var binding: FragmentLaundryInventoryBinding
    lateinit var adapter: ClothingInventoryAdapter

    companion object {
        const val fragmentName = "LaundryInventoryFragment"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLaundryInventoryBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        adapter = ClothingInventoryAdapter(this)
        adapter.addListener(fragmentName)
        binding.clothingList.adapter = adapter
        binding.clothingList.setHasFixedSize(true)
        binding.clothingList.layoutManager = LinearLayoutManager(requireContext())
        binding. clothingList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.fab.setOnClickListener {
            val layout = LinearLayout(context)
            val builder = AlertDialog.Builder(this.requireContext())
            builder.setTitle("Add New Clothing Item")
            val material = EditText(this.requireContext())
            material.setHint("Material")
            val color = EditText(this.requireContext())
            color.setHint("Color")
            val type = EditText(this.requireContext())
            type.setHint("Type")
            val name = EditText(this.requireContext())
            name.setHint("Name")

            layout.addView(name)
            layout.addView(type)
            layout.addView(color)
            layout.addView(material)
            builder.setView(layout)

            builder.setPositiveButton("Ok") { dialogInterface: DialogInterface, i: Int ->
                adapter.addClothing(ClothingItem(name.text.toString(), type.text.toString(), color.text.toString(), material.text.toString()))
            }
            builder.show()
        }
        binding.deleteButton.setOnClickListener {
            adapter.deleteSelected()
        }
        return binding.root
    }
}

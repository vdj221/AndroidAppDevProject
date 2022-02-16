package edu.rosehulman.roselaundrytracker.adapter

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.roselaundrytracker.Constants
import edu.rosehulman.roselaundrytracker.fragment.LaundryLoadFragment
import edu.rosehulman.roselaundrytracker.R
import edu.rosehulman.roselaundrytracker.model.LaundryLoad
import edu.rosehulman.roselaundrytracker.model.LaundryLoadViewModel

class LaundryLoadAdapter(val fragment: LaundryLoadFragment): RecyclerView.Adapter<LaundryLoadAdapter.LoadViewHolder>() {
    val model = ViewModelProvider(fragment.requireActivity()).get(LaundryLoadViewModel::class.java)
    //This is for looking at the loads after they have been created
    init {
        model.setObserver {
            Log.d(Constants.TAG, "LaundryLoadObserver")
            notifyDataSetChanged()
        }
    }
    inner class LoadViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val loadNumber: TextView = itemView.findViewById(R.id.row_machine_number)
        private val loadType: TextView = itemView.findViewById(R.id.row_machine_type)
        private val viewButton: ImageView = itemView.findViewById(R.id.row_load_contents)
        private val timerText: TextView = itemView.findViewById(R.id.row_timer)

        init {
            viewButton.setOnClickListener {
                model.updateCurrentPos(adapterPosition)
                if(model.getCurrentLoad().getOwner() == model.uid) {
                    var loadContents = ""
                    for (item in model.getCurrentLoad().contents) {
                        loadContents += item.name
                        loadContents += '\n'
                    }
                    Log.d(Constants.TAG, "Load Contents: $loadContents")
                    val layout = LinearLayout(fragment.context)
                    val builder = AlertDialog.Builder(fragment.requireContext())
                    builder.setTitle("View Load Contents")
                    val names = TextView(fragment.requireContext())
                    names.setText(loadContents)
                    layout.addView(names)
                    builder.setView(layout)
                    builder.show()
                } else {
                    deniedDialog()
                }
            }
            itemView.setOnClickListener {
                model.updateCurrentPos(adapterPosition)
                if(model.getCurrentLoad().getOwner() == model.uid) {
                    itemView.findNavController().navigate(R.id.navigation_edit_load)
                } else {
                    deniedDialog()
                }
            }
        }

        fun bind(laundryLoad: LaundryLoad) {
            loadNumber.text = laundryLoad.getMachineNumber().toString()
            loadType.text = laundryLoad.getMachineType()
            timerText.text = laundryLoad.timeString
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LoadViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_laundry_load, parent, false))

    override fun onBindViewHolder(holder: LoadViewHolder, position: Int) {
        model.updateCurrentPos(position)
        holder.bind(model.getCurrentLoad())
    }

    private fun deniedDialog() {
        val msg = TextView(fragment.requireContext())
        val builder = AlertDialog.Builder(fragment.requireContext())
        val layout = LinearLayout(fragment.context)
        builder.setTitle("Access Denied")
        msg.text = "You do not own this load"
        layout.addView(msg)
        builder.setView(layout)
        builder.show()
    }

    fun addListener(fragmentName: String) {
        model.addListener(fragmentName) {
            notifyDataSetChanged()
        }
    }

    fun removeListener(fragmentName: String) {
        model.removeListener(fragmentName)
    }

    override fun getItemCount() = model.size()
    fun togglePreferences(fragmentName: String) {
        model.removeListener(fragmentName)
        model.togglePreference()
        model.addListener(fragmentName) {
            notifyDataSetChanged()
        }
    }

    fun getPreference() = model.getPreference()
}
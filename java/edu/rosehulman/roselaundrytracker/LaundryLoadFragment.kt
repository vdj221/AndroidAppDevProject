package edu.rosehulman.roselaundrytracker


import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.rosehulman.roselaundrytracker.adapter.LoadAdapter
import edu.rosehulman.roselaundrytracker.databinding.FragmentLaundryLoadBinding
import edu.rosehulman.roselaundrytracker.model.LaundryLoad

class LaundryLoadFragment: Fragment() {
    lateinit var binding: FragmentLaundryLoadBinding

    lateinit var adapter: LoadAdapter

    companion object {
        const val MILLI_TO_MIN: Long = 60000
        const val MILLI_TO_SEC: Long = 1000
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentLaundryLoadBinding.inflate(inflater, container,false)
            adapter = LoadAdapter(this)
            binding.loadRecyclerView.adapter = adapter
            binding.loadRecyclerView.setHasFixedSize(true)
            binding.loadRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val clothes = Array<String>(adapter.model2.clothes.size){""}
        for(i in 0 until adapter.model2.clothes.size) {
            clothes[i] = adapter.model2.clothes.get(i).name
        }
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.navigation_add_load)
//            val checkedItems = ArrayList<Int>()
//            val layout = LinearLayout(context)
//            Log.d("add load", "entered add load in fragment")
//            val builder = AlertDialog.Builder(this.requireContext())
//            builder.setTitle("Add New Load")
////            builder.setMultiChoiceItems(clothes, null) { dialog, index, checked ->
////                if (checked) {
////                    checkedItems.add(index)
////                } else if (checkedItems.contains(index)) {
////                    checkedItems.remove(index)
////                }
////            }
//            val contents = ListView(this.requireContext())
//            val arrAdapter = ArrayAdapter(this.requireContext(), R.layout.laundry_load_spinner, R.id.textView, clothes)
//            contents.adapter = arrAdapter
//
//            val machineNumber = EditText(this.requireContext())
//            machineNumber.setHint("Machine Number")
//            val machineType = EditText(this.requireContext())
//            machineType.setHint("Machine Type")
//
////            val contents = EditText(this.requireContext())
////            contents.setHint("Laundry Contents")
//            val timerInput = EditText(this.requireContext())
//            timerInput.setHint("Timer")
//            timerInput.setText("30")
//            if(timerInput.text.isNotBlank()) {
//                val time = timerInput.text?.toString()?.toLong()
//                val timer = object : CountDownTimer(time!! * MILLI_TO_MIN, MILLI_TO_SEC) {
//
//                    override fun onTick(millisUntilFinished: Long) {
//                        var min = millisUntilFinished / MILLI_TO_MIN
//                        var sec = millisUntilFinished / MILLI_TO_SEC
//                        lateinit var secText: String
//                        if (sec < 10) {
//                            secText = "0" + sec.toString()
//                        } else {
//                            secText = sec.toString()
//                        }
//                        binding.TimerLabel.setText("$min:$secText")
//                    }
//
//                    override fun onFinish() {
//                        binding.TimerLabel.setText("done!")
//                    }
//                }.start()
//                layout.addView(machineNumber)
//                layout.addView(machineType)
//    //            layout.addView(contents)
//                layout.addView(timerInput)
//                layout.addView(contents)
////                layout.addView(checkedItems)
//                builder.setView(layout)
//                builder.setPositiveButton("Ok") { dialogInterface: DialogInterface, i: Int ->
//                    adapter.addLoad(LaundryLoad(machineNumber.text.toString().toInt(), machineType.text.toString(), checkedItems, timer))
//                }
//                builder.show()
//            }
        }
        return binding.root
    }




}
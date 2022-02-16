package edu.rosehulman.roselaundrytracker.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import edu.rosehulman.roselaundrytracker.Constants
import edu.rosehulman.roselaundrytracker.R
import edu.rosehulman.roselaundrytracker.adapter.ChatAdapter
import edu.rosehulman.roselaundrytracker.databinding.FragmentChatWindowBinding
import java.lang.NullPointerException

class NewChatWindowFragment : androidx.fragment.app.Fragment() {
    lateinit var binding: FragmentChatWindowBinding
    lateinit var adapter:ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatWindowBinding.inflate(inflater, container, false)
        adapter = ChatAdapter(this)

        binding.texts.adapter = adapter
        val llm = LinearLayoutManager(requireContext())
        llm.stackFromEnd = true
        binding.texts.layoutManager = llm
        binding.sendMessageButton.setOnClickListener {
            Log.d(Constants.TAG, "entered add")
            Log.d(Constants.TAG, "${binding.messageContents.text.toString()}")
            adapter.addListener(this.toString(), binding.messageReceiver.text.toString())
            adapter.addChat(
                binding.messageContents.text.toString(),
                binding.messageReceiver.text.toString()
            )
            binding.messageContents.setText("")
        }
        return binding.root
    }
}
package edu.rosehulman.roselaundrytracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import edu.rosehulman.roselaundrytracker.databinding.FragmentNewChatBinding


class NewChatFragment : Fragment() {
    private lateinit var binding: FragmentNewChatBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewChatBinding.inflate(inflater, container, false)
        binding.sendButton.setOnClickListener {
            sendMessage(binding.messageText.text.toString(), binding.recipientsName.text.toString())
            binding.messageText.clearComposingText()
            binding.recipientsName.clearComposingText()
        }
        return binding.root
    }

    fun sendMessage(msg: String, recipient: String) {
        //TODO: Implement how to send message
    }
    companion object {

    }
}
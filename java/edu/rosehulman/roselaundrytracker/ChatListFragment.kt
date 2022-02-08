package edu.rosehulman.roselaundrytracker

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.rosehulman.roselaundrytracker.databinding.FragmentChatListBinding


class ChatListFragment: androidx.fragment.app.Fragment() {
    lateinit var binding: FragmentChatListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatListBinding.inflate(inflater, container, false)

        return binding.root
    }
}
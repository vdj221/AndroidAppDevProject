package edu.rosehulman.roselaundrytracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.rosehulman.roselaundrytracker.R
import edu.rosehulman.roselaundrytracker.adapter.ChatListAdapter
import edu.rosehulman.roselaundrytracker.databinding.FragmentChatListBinding


class ChatListFragment: androidx.fragment.app.Fragment() {
    lateinit var binding: FragmentChatListBinding
    lateinit var adapter: ChatListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatListBinding.inflate(inflater, container, false)
        adapter = ChatListAdapter(this)
        binding.lastChats.adapter = adapter
        binding.lastChats.layoutManager = LinearLayoutManager(requireContext())
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.navigation_add_chat)
        }
        return binding.root
    }
}
package edu.rosehulman.roselaundrytracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.roselaundrytracker.R
import edu.rosehulman.roselaundrytracker.fragment.ChatListFragment
import edu.rosehulman.roselaundrytracker.model.Chat
import edu.rosehulman.roselaundrytracker.model.ChatListViewModel

class ChatListAdapter(val fragment: ChatListFragment): RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {
    val model = ViewModelProvider(fragment.requireActivity()).get(ChatListViewModel::class.java)
    inner class ChatListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name_label)
        val dateTextView: TextView = itemView.findViewById(R.id.time_label)
        val msgTextView: TextView = itemView.findViewById(R.id.last_message)

        init {
            itemView.setOnClickListener {
                val to: EditText = itemView.findViewById(R.id.to)
                to.setText(nameTextView.text)
                itemView.findNavController().navigate(R.id.navigation_add_chat)
            }
        }
        fun bind(message: Chat) {
            nameTextView.text = message.notMe(model.me)
            dateTextView.text = message.created!!.toDate().toString()
            msgTextView.text = message.contents
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChatListViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_chat_list, parent, false))

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.bind(model.getMsgAt(position))
    }

    override fun getItemCount() = model.size()
}
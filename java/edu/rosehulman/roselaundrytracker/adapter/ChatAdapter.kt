package edu.rosehulman.roselaundrytracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.roselaundrytracker.ChatWindowFragment
import edu.rosehulman.roselaundrytracker.R
import edu.rosehulman.roselaundrytracker.model.Chat
import edu.rosehulman.roselaundrytracker.model.ChatViewModel

class ChatAdapter(val fragment: ChatWindowFragment):
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    val model = ViewModelProvider(fragment.requireActivity()).get(ChatViewModel::class.java)
    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contentsView: TextView = itemView.findViewById(R.id.message_text)
        val timeView: TextView = itemView.findViewById(R.id.time_label)

        fun bind(chat: Chat) {
            contentsView.text = chat.contents
            timeView.text = chat.created.toString()
            if(chat.sender === model.getUserId())
                contentsView.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
            else
                contentsView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_msg, parent, false))

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(model.getMsgAt(position))
    }

    override fun getItemCount() = model.size()
}
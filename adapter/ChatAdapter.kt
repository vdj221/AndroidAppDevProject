package edu.rosehulman.roselaundrytracker.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.roselaundrytracker.Constants
import edu.rosehulman.roselaundrytracker.fragment.NewChatWindowFragment
import edu.rosehulman.roselaundrytracker.R
import edu.rosehulman.roselaundrytracker.model.Chat
import edu.rosehulman.roselaundrytracker.model.ChatViewModel
import java.lang.Exception


class ChatAdapter(val fragment: NewChatWindowFragment):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val messageSENT = 0
    private val messageReceived = 1

    private val mContext: Context? = null
    val model = ViewModelProvider(fragment.requireActivity()).get(ChatViewModel::class.java)

        inner class ReceivedMessageHolder  (itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            var messageText: TextView = itemView.findViewById<View>(R.id.text_gchat_user_other) as TextView
            var timeText: TextView = itemView.findViewById<View>(R.id.text_gchat_timestamp_other) as TextView
            var nameText: TextView = itemView.findViewById<View>(R.id.text_gchat_message_other) as TextView
            fun bind(message: Chat) {
                Log.d("Adapter", "Contents:${message.contents}")

                messageText.text = message.contents

                // Format the stored timestamp into a readable String using method.
    //            timeText.setText(Utils.formatDateTime(message.createdAt))
                nameText.text = message.sender
                timeText.text = message.created!!.toDate().toString()
                // Insert the profile image from the URL into the ImageView.

            }

        }


    inner class SentMessageHolder (itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var messageText: TextView = itemView.findViewById<View>(R.id.text_gchat_message_me) as TextView
        var timeText: TextView = itemView.findViewById<View>(R.id.text_gchat_date_me) as TextView

        fun bind(message: Chat) {
            Log.d("Adapter", "Contents:${message.contents}")
            messageText.text = message.contents
            if(message.created != null)
            timeText.text = message.created!!.toDate().toString()

            // Format the stored timestamp into a readable String using method.
//            timeText.setText(Utils.formatDateTime(message.createdAt))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentMessageHolder {
        Log.d(Constants.TAG,"Displaying new message")
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.row_my_chat,
                        parent,
                        false
                    )
            return SentMessageHolder(view)


    }

    /* override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
         val message: Chat = mMessageList!![position] as Chat
         holder.bind(message)
 //        when (holder.itemViewType) {
 //            VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageHolder).bind(message)
 //            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedMessageHolder).bind(message)
 //        }
     }
     */

    fun addChat(contents: String,  receiver: String){
        model.addMsg(contents, receiver)
        notifyDataSetChanged()
    }


    override fun getItemCount() = model.size()


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message: Chat = model.getMsgAt(position) as Chat
        Log.d(Constants.TAG, "Holder: ${holder.itemViewType}")
        when (holder.itemViewType) {
            messageSENT-> (holder as SentMessageHolder).bind(message)
            messageReceived -> (holder as ReceivedMessageHolder).bind(message)
        }
    }

    fun addListener(fragmentName: String, receiver: String) {
            try {
                model.addListener(fragmentName, receiver) {
                    notifyDataSetChanged()
                }
            } catch(e: Exception) {
                throw e
            }

    }

    fun removeListener(fragmentName: String) {
        model.removeListener(fragmentName)
    }

}

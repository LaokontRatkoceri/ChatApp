package com.example.chatapp.ui.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Data.Message
import com.example.chatapp.R
import com.example.chatapp.databinding.ChatActivityFragmentBinding
import com.example.chatapp.databinding.MessageReceivedBinding
import com.example.chatapp.databinding.MessagesSentBinding
import com.example.chatapp.databinding.UserItemLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ChatAdapter(var context: Context,val messageList:ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2


     class SentMsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: MessagesSentBinding = MessagesSentBinding.bind(itemView)
    }
     class ReceiverMsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: MessageReceivedBinding = MessageReceivedBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            val view : View = LayoutInflater.from(context).inflate(R.layout.message_received, parent, false)
            return ReceiverMsHolder(view)
        }else{
            val view : View = LayoutInflater.from(context).inflate(R.layout.messages_sent, parent, false)
            return SentMsHolder(view)
        }
    }

    override fun getItemCount(): Int = messageList.size

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if (holder.javaClass == SentMsHolder::class.java){
            val viewHolder = holder as SentMsHolder

            val currentMessage = messageList[position]

            holder.binding.MessageSent.text = currentMessage.message.toString()

        }else{
            val viewHolder = holder as ReceiverMsHolder

            holder.binding.messages.text = currentMessage.message.toString()
        }
    }


}
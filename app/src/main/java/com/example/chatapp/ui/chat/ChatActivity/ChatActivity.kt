package com.example.chatapp.ui.chat.ChatActivity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.Data.Message
import com.example.chatapp.databinding.ChatActivityFragmentBinding
import com.example.chatapp.ui.chat.adapter.ChatAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.Calendar
import java.util.Date


class ChatActivity:Fragment() {

    private lateinit var binding: ChatActivityFragmentBinding
    val args: ChatActivityArgs by navArgs()
    var adapter: ChatAdapter? = null
    private lateinit var messageAdapter: ChatAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference

    var receiverRoom : String? = null
    var senderRoom : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChatActivityFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.TextFragment.text = args.emailId

        binding.BackButton.setOnClickListener {
            findNavController().navigate(ChatActivityDirections.actionChatActivityToChatMessages())
        }

        mDbRef = FirebaseDatabase.getInstance().getReference()

        var receiverId = args.uid
        var senderId = FirebaseAuth.getInstance().currentUser?.uid

        messageList = ArrayList()
        messageAdapter = ChatAdapter(requireContext(), messageList)

        senderRoom = receiverId + senderId
        receiverRoom = senderId + receiverId

        binding.Recyclechat.layoutManager = LinearLayoutManager(requireContext())
        binding.Recyclechat.adapter = messageAdapter


        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for(postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


        binding.SendButton.setOnClickListener {

            val message = binding.LoginEditText.text
            val messageObject = Message(message.toString(), senderId)


            mDbRef.child("chats")
                .child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            binding.LoginEditText.setText("")
        }
    }
}






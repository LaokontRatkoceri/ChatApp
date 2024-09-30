package com.example.chatapp.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.Data.User
import com.example.chatapp.databinding.ChatMessagesBinding
import com.example.chatapp.ui.chat.adapter.UserAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class chatFragment : Fragment() {
    private lateinit var chatAdapter: UserAdapter
    private var _binding: ChatMessagesBinding? = null
    private val binding get() = _binding!!
    private var mUsers = mutableListOf<User>()

   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChatMessagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.TextFragment.text = "Messages"

        // Initialize RecyclerView and Adapter
        chatAdapter = UserAdapter(mUsers){
            user -> onItemClick(user)
        }
        binding.RecycleUsers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatAdapter
        }

        // Fetch users from Firebase
        fetchUsers()
    }

    private fun fetchUsers() {
        val firebaseUserID = FirebaseAuth.getInstance().currentUser!!.uid

        val databaseReference = FirebaseDatabase.getInstance().getReference("users")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mUsers.clear()  // Clear the list to avoid duplicates
                for (snapshot in dataSnapshot.children) {
                    val user = snapshot.getValue(User::class.java)
                    if (user != null && user.uid != firebaseUserID) {
                        mUsers.add(user)
                    }
                }
                chatAdapter.notifyDataSetChanged()  // Notify adapter about data change
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("FirebaseError", databaseError.message)
            }
        })
    }
    private fun onItemClick(user: User) {
        val action = chatFragmentDirections.actionChatMessagesToChatActivity(user.Email.toString(), user.uid.toString())
        findNavController().navigate(action)
    }
}

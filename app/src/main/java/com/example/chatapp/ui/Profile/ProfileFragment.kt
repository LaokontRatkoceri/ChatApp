package com.example.chatapp.ui.Profile

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chatapp.Data.User
import com.example.chatapp.databinding.FragmentHomeBinding
import com.example.chatapp.databinding.ProfileFragmentBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class ProfileFragment: Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var binding: ProfileFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.TextFragment.text = "Profile"

        readData()

    }

    fun readData(){

        val uid = Firebase.auth.currentUser?.uid
        val uidRef = Firebase.database.reference.child("users").child(uid.toString())
        uidRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val snapshot = it.result
                val name = snapshot.child("firstName").getValue(String::class.java)
                val lastName = snapshot.child("lastName").getValue(String::class.java)
                val email = snapshot.child("email").getValue(String::class.java)
                binding.NameText.text = "$name $lastName"
                binding.NumberFolowers.text = "0"
                binding.NumberPost.text = "0"
                binding.NumberFolowing.text = "0"

                Log.d(TAG, "$name/$email/$lastName")
            } else {
                Log.d(TAG, "${it.exception?.message}") //Never ignore potential errors!
            }
        }
    }
}
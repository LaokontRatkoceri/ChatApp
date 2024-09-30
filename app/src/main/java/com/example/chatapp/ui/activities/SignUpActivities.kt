package com.example.chatapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.Data.User
import com.example.chatapp.Utils.isValidEmail
import com.example.chatapp.Utils.isValidPassword
import com.example.chatapp.databinding.SignupActivityBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignUpActivities: AppCompatActivity() {

    private lateinit var binding: SignupActivityBinding


    private lateinit var auth: FirebaseAuth

    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = SignupActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        FirebaseApp.initializeApp(this)
        database = Firebase.database.reference

        binding.SignupButton.setOnClickListener {
            if(binding.UsernameText.text.isValidEmail()
                && binding.PasswordEditText.text.isValidPassword()){
                signUpUser()
            } else {
                Log.d("SignIn", "The Email or the Password doesn't meat the requirements")
            }
        }

    }


    private fun signUpUser() {
        val email =binding.UsernameText.text
        val pass = binding.PasswordEditText.text
        val FirstName = binding.FirstnameEditText.text
        val LastName = binding.LastnameEditText.text


        if (email.toString().isBlank() || pass.toString().isBlank() || FirstName.toString().isBlank() || LastName.toString().isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()

        }

        auth.createUserWithEmailAndPassword(
            email.toString(),pass.toString(),
        ).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {

                val user = auth.currentUser
                println("Register successfull ${user.toString()}")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)


                user?.let {
                    val userRef = database.child("users").child(it.uid)
                    userRef.setValue(User(user.uid, FirstName.toString(), LastName.toString(), null, it.email,
                        pass.toString(), emptyList()
                    ))
                }
            } else {
                Toast.makeText(
                    baseContext,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }



    }
}
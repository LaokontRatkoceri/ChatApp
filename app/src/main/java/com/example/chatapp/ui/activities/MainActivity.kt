package com.example.chatapp.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import android.util.Log
import android.widget.Toast
import com.example.chatapp.Data.User
import com.example.chatapp.R
import com.example.chatapp.Utils.isValidEmail
import com.example.chatapp.Utils.isValidPassword
import com.example.chatapp.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var auth: FirebaseAuth

    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        FirebaseApp.initializeApp(this)

        database = FirebaseDatabase.getInstance().reference

        binding.LoginButton.setOnClickListener {
            if(binding.LoginEditText.text.isValidEmail()
                && binding.PasswordEditText.text.isValidPassword()){
                login()
            } else {
                Log.d("SignIn", "No data available")
            }
        }

        binding.SignupButton.setOnClickListener {
            val intent = Intent(this, SignUpActivities::class.java)
            startActivity(intent)
        }


    }

    private fun login() {
        val email = binding.LoginEditText.text.toString()
        val pass = binding.PasswordEditText.text.toString()

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = auth.currentUser
                    println("Login successful ${user.toString()}")

                    val intent = Intent(this, ActivityFragment::class.java)
                    startActivity(intent)
                    user?.let {
                        val userRef = database.child("users").child(it.uid)
                        userRef.get().addOnSuccessListener { snapshot ->
                            if (snapshot.exists()) {
                                val userData = snapshot.getValue(User::class.java)
                                Log.d("SignIn", "User data: $userData")
                            } else {
                                Log.d("SignIn", "No data available")
                            }
                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun validetes() = binding.LoginEditText.text.isValidEmail() && binding.PasswordEditText.text.isValidPassword()

}
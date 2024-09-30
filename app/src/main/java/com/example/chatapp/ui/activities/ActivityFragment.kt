package com.example.chatapp.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityMainBinding
import com.example.chatapp.databinding.FragmentActivityBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivityFragment: AppCompatActivity()  {

    private lateinit var binding: FragmentActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val navView : BottomNavigationView = findViewById(R.id.BottomNavigation)
        navView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.ChatActivity) {
                navView.visibility = View.GONE
                binding.AddButton.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
                binding.AddButton.visibility = View.VISIBLE

            }
        }
    }


}
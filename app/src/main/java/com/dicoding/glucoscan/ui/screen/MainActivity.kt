package com.dicoding.glucoscan.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.databinding.ActivityMainBinding
import com.dicoding.glucoscan.ui.screen.login.SignInActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navBot = binding.bottomNav
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navBot.setupWithNavController(navController)

    }
}
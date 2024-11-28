package com.dicoding.glucoscan.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.glucoscan.data.EncryptedSharedPreference.deleteUID
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.databinding.ActivityMainBinding
import com.dicoding.glucoscan.ui.screen.login.SignInActivity
import com.dicoding.glucoscan.ui.screen.scan.CameraActivity
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
        navBot.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_history -> {
                    navController.navigate(R.id.navigation_history)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_scan -> {
                    val intent = Intent(this, CameraActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener false
                }
                R.id.navigation_notifications -> {
                    navController.navigate(R.id.navigation_notifications)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_setting -> {
                    navController.navigate(R.id.navigation_setting)
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener true
                }
            }
        }

    }
}
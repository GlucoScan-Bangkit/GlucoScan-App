package com.dicoding.glucoscan.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.databinding.ActivityMainBinding
import com.dicoding.glucoscan.helper.ViewModelFactory
import com.dicoding.glucoscan.ui.screen.MainViewModel
import com.dicoding.glucoscan.ui.screen.login.SignInActivity
import com.dicoding.glucoscan.ui.screen.scan.CameraActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels(){
        ViewModelFactory.getInstance(application)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getDashboard()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = Bundle()
        viewModel.getDashboard()
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        viewModel.user.observe(this){ result ->
            when(result){
                is Result.Success -> {
                    result.data.user?.let {
                        bundle.putParcelable("user", it)
                    }
                    navController.navigate(R.id.navigation_home, bundle)
                }

                is Result.Error -> {
                    //
                }
                Result.Loading -> {
                    //
                }
            }
        }
        val navBot = binding.bottomNav
        navBot.setupWithNavController(navController)
        navBot.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home, bundle)
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
                    navController.navigate(R.id.navigation_setting, bundle)
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener true
                }
            }
        }
    }
}
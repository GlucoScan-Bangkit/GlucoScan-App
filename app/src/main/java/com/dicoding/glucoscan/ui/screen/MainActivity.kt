package com.dicoding.glucoscan.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.databinding.ActivityMainBinding
import com.dicoding.glucoscan.helper.ViewModelFactory
import com.dicoding.glucoscan.ui.screen.MainViewModel
import com.dicoding.glucoscan.ui.screen.login.SignInActivity
import com.dicoding.glucoscan.ui.screen.scan.CameraActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navBot: BottomNavigationView
    private val viewModel: MainViewModel by viewModels(){
        ViewModelFactory.getInstance(application)
    }
    private val bundle = Bundle()
    private var lastNav: Int = 2131296647

    override fun onResume() {
        super.onResume()
        viewModel.getDashboard()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getDashboard()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        navBot = binding.bottomNav
        navBot.setupWithNavController(navController)
        viewModel.user.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    result.data.user?.let {
                        bundle.putParcelable("user", it)
                    }
                    onNavigationItemSelected(lastNav)
                }

                is Result.Error -> {
                    //
                }

                Result.Loading -> {
                    //
                }
            }
        }

        setupAction()
    }
    private fun  setupAction(){
        navBot.setOnItemSelectedListener { item ->
            if (item.toString() != "Scan") {
                lastNav = item.itemId
            }
            onNavigationItemSelected(item.itemId)
        }
    }

    private fun onNavigationItemSelected(item: Int): Boolean {
        when(item) {
            R.id.navigation_home -> {
                navController.navigate(R.id.navigation_home, bundle)
                return true
            }
            R.id.navigation_history -> {
                navController.navigate(R.id.navigation_history)
                return true
            }
            R.id.navigation_scan -> {
                val intent = Intent(this, CameraActivity::class.java)
                startActivity(intent)
                return false
            }
            R.id.navigation_notifications -> {
                navController.navigate(R.id.navigation_notifications)
                return true
            }
            R.id.navigation_setting -> {
                navController.navigate(R.id.navigation_setting, bundle)
                return true
            }
            else -> {
                return true
            }
        }
    }
}
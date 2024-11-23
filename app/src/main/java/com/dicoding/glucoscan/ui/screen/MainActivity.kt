package com.dicoding.glucoscan.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.glucoscan.data.EncryptedSharedPreference.deleteUID
import com.dicoding.glucoscan.databinding.ActivityMainBinding
import com.dicoding.glucoscan.ui.screen.login.SignInActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnLogOut.setOnClickListener {
            auth.signOut()
            deleteUID(this)
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
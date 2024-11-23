package com.dicoding.glucoscan.ui.screen.usereditor

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.databinding.ActivityPasswordEditorBinding

class PasswordEditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPasswordEditorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
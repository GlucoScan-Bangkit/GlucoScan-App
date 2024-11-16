package com.dicoding.glucoscan.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEmail.isEnabled = false

        binding.tietEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnEmail.isEnabled = !binding.tietEmail.text.isNullOrEmpty() && !binding.tietPassword.text.isNullOrEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {
                //
            }

        })

        binding.tietPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnEmail.isEnabled = !binding.tietEmail.text.isNullOrEmpty() && !binding.tietPassword.text.isNullOrEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {
                //
            }

        })
    }
}
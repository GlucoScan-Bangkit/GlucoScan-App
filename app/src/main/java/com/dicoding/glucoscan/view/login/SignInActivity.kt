package com.dicoding.glucoscan.view.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.glucoscan.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity(), TextWatcher {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        check if input is empty
        binding.btnEmail.isEnabled = false
        binding.tietEmail.addTextChangedListener(this)
        binding.tietPassword.addTextChangedListener(this)

        binding.btnSignup.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        //
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        binding.btnEmail.isEnabled = !binding.tietEmail.text.isNullOrEmpty() && !binding.tietPassword.text.isNullOrEmpty()
    }

    override fun afterTextChanged(p0: Editable?) {
        //
    }
}
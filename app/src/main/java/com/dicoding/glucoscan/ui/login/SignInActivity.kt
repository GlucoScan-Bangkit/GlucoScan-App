package com.dicoding.glucoscan.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.glucoscan.databinding.ActivitySignInBinding
import com.dicoding.glucoscan.helper.ViewModelFactory
import com.dicoding.glucoscan.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity(), TextWatcher {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    private val signInViewModel: SignInViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

//        check if input is empty
        binding.btnEmail.isEnabled = false
        binding.tietEmail.addTextChangedListener(this)
        binding.tietPassword.addTextChangedListener(this)

        binding.btnEmail.setOnClickListener{
            signInViewModel.setEmailPassword(binding.tietEmail.text.toString(), binding.tietPassword.text.toString())
            signInViewModel.signIn()
        }

        binding.btnSignup.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
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
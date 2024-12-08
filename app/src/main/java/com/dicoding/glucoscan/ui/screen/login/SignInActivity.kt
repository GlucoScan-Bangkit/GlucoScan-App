package com.dicoding.glucoscan.ui.screen.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.data.EncryptedSharedPreference.getToken
import com.dicoding.glucoscan.data.EncryptedSharedPreference.saveUID
import com.dicoding.glucoscan.data.Result
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

        setupView()
        setupButton()
    }

    private fun setupView(){
        signInViewModel.loginResult.observe(this){ result ->
            when(result){
                is Result.Success -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    val user = result.data.user
                    saveUID(
                        token = user?.token.toString(),
                        context = this
                    )
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupButton(){

//        set inputBox
        binding.emailInput.title.text = getString(R.string.email)
        binding.emailInput.input.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        binding.passwordInput.title.text = getString(R.string.password)
        binding.passwordInput.input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        //        check if input is empty
        binding.btnEmail.isEnabled = false
        binding.emailInput.input.addTextChangedListener(this)
        binding.passwordInput.input.addTextChangedListener(this)

        binding.btnEmail.setOnClickListener{
            signInViewModel.setEmailPassword(binding.emailInput.input.text.toString(), binding.passwordInput.input.text.toString())
            signInViewModel.signIn2()
        }

        binding.btnSignup.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null || getToken(this) != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        //
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        binding.btnEmail.isEnabled = !binding.emailInput.input.text.isNullOrEmpty() && !binding.passwordInput.input.text.isNullOrEmpty()
    }

    override fun afterTextChanged(p0: Editable?) {
        //
    }
}
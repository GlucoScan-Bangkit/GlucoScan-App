package com.dicoding.glucoscan.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.databinding.ActivitySignUpBinding
import com.dicoding.glucoscan.helper.ViewModelFactory

class SignUpActivity : AppCompatActivity(), TextWatcher {
    private lateinit var binding: ActivitySignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEmail.isEnabled = false
        binding.tietEmail.addTextChangedListener(this)
        binding.tietPassword.addTextChangedListener(this)
        binding.tietPasswordConfirmation.addTextChangedListener(this)

        binding.btnEmail.setOnClickListener {
            signUpViewModel.setDatatoSignUp(binding.tietEmail.text.toString(), binding.tietPassword.text.toString())
//            val intent = Intent(this, VerificationActivity::class.java)
//            startActivity(intent)
            signUpViewModel.signUp()
        }
        binding.btnSignin.setOnClickListener {
            finish()
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        //
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        binding.btnEmail.isEnabled = validateForm()
    }

    override fun afterTextChanged(p0: Editable?) {
        when(p0){
            binding.tietEmail.editableText -> {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.tietEmail.text.toString()).matches()){
                    binding.tietEmail.error = getString(R.string.email_tidak_valid)
                }
            }
            binding.tietPassword.editableText -> {
                if (binding.tietPassword.text.toString().length < 8){
                    binding.tietPassword.error = getString(R.string.password_minimal_8_karakter)
                }
            }
            binding.tietPasswordConfirmation.editableText -> {
                if (binding.tietPassword.text.toString() != binding.tietPasswordConfirmation.text.toString()){
                    binding.tietPasswordConfirmation.error = getString(R.string.password_tidak_sama)
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        val email = binding.tietEmail.text.toString()
        val password = binding.tietPassword.text.toString()
        val passwordConfirmation = binding.tietPasswordConfirmation.text.toString()
        return email.isNotEmpty() && password.isNotEmpty() && passwordConfirmation.isNotEmpty() && password == passwordConfirmation && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 8
    }
}
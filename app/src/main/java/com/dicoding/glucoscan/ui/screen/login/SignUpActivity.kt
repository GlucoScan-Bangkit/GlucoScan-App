package com.dicoding.glucoscan.ui.screen.login

import android.os.Bundle
import android.text.Editable
import android.text.InputType
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

//        set inputBox
        binding.emailInput.title.text = getString(R.string.email)
        binding.emailInput.input.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        binding.passwordInput.title.text = getString(R.string.password)
        binding.passwordInput.input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        binding.confirmationPasswordInput.title.text = getString(R.string.confirm_password)
        binding.confirmationPasswordInput.input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

//        setButton
        binding.btnEmail.isEnabled = false
        binding.emailInput.input.addTextChangedListener(this)
        binding.passwordInput.input.addTextChangedListener(this)
        binding.confirmationPasswordInput.input.addTextChangedListener(this)

        binding.btnEmail.setOnClickListener {
            signUpViewModel.setDatatoSignUp(binding.emailInput.input.text.toString(), binding.passwordInput.input.text.toString())
//            val intent = Intent(this, VerificationActivity::class.java)
//            startActivity(intent)
            signUpViewModel.signUp2()
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
            binding.emailInput.input.editableText -> {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailInput.input.text.toString()).matches()){
                    binding.emailInput.input.error = getString(R.string.email_invalid)
                }
            }
            binding.passwordInput.input.editableText -> {
                if (binding.passwordInput.input.text.toString().length < 8){
                    binding.passwordInput.input.error = getString(R.string.password_invalid)
                }
            }
            binding.confirmationPasswordInput.input.editableText -> {
                if (binding.passwordInput.input.text.toString() != binding.confirmationPasswordInput.input.text.toString()){
                    binding.confirmationPasswordInput.input.error = getString(R.string.wrong_password_confirmation)
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        val email = binding.emailInput.input.text.toString()
        val password = binding.passwordInput.input.text.toString()
        val passwordConfirmation = binding.confirmationPasswordInput.input.text.toString()
        return email.isNotEmpty() && password.isNotEmpty() && passwordConfirmation.isNotEmpty() && password == passwordConfirmation && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 8
    }
}
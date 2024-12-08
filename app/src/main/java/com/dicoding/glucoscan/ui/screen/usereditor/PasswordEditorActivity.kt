package com.dicoding.glucoscan.ui.screen.usereditor

import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.databinding.ActivityPasswordEditorBinding
import com.dicoding.glucoscan.helper.ViewModelFactory

class PasswordEditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPasswordEditorBinding
    private val viewModel: PasswordEditorViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView(){
//        set input text
        binding.passwordInput.title.text = getString(R.string.password)
        binding.passwordInput.input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        binding.newPasswordInput.title.text = getString(R.string.new_password)
        binding.newPasswordInput.input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        binding.confirmationPasswordInput.title.text = getString(R.string.confirmation_password)
        binding.confirmationPasswordInput.input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        binding.btnUpdate.isEnabled = false


        viewModel.result.observe(this){ result ->
            when(result){
                is Result.Success -> {
                    Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Result.Error -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    //
                }
            }
        }
    }

    private fun setupAction(){

        binding.icBack.setOnClickListener{
            finish()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnUpdate.setOnClickListener {
            val password = binding.passwordInput.input.text.toString()
            val newPassword = binding.newPasswordInput.input.text.toString()
            val confirmationPassword = binding.confirmationPasswordInput.input.text.toString()
            viewModel.updatePassword(password, newPassword)
        }
    }
}
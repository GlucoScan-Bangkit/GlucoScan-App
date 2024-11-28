package com.dicoding.glucoscan.ui.screen.usereditor

import android.os.Bundle
import android.text.InputType
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

//        set input text
        binding.passwordInput.title.text = getString(R.string.password)
        binding.passwordInput.input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        binding.newPasswordInput.title.text = getString(R.string.new_password)
        binding.newPasswordInput.input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        binding.confirmationPasswordInput.title.text = getString(R.string.confirmation_password)
        binding.confirmationPasswordInput.input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        binding.icBack.setOnClickListener{
            finish()
        }
    }
}
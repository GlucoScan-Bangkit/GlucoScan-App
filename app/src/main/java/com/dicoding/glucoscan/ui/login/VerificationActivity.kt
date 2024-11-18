package com.dicoding.glucoscan.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.databinding.ActivityVerificationBinding
import com.dicoding.glucoscan.helper.FirebaseHelper

class VerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding
//    private lateinit var firebaseHelper: FirebaseHelper

    val otpEditText = listOf(binding.otp1, binding.otp2, binding.otp3, binding.otp4, binding.otp5, binding.otp6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        firebaseHelper = FirebaseHelper()
        binding.btnEmail.setOnClickListener {
            finish()
        }

        binding.btnEmail.isEnabled = false
        otpEditText.forEachIndexed{ index, editText ->
            editText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    if (p0?.length == 1 && index != otpEditText.lastIndex){
                        otpEditText[index + 1].requestFocus()
                    } else if (p0?.length == 0 && index != 0){
                        otpEditText[index - 1].requestFocus()
                    }

                    binding.btnEmail.isEnabled = checkAllFieldsFilled()
                }

            })
        }
    }

    fun checkAllFieldsFilled(): Boolean{
        return otpEditText.all { it.text?.isNotEmpty() == true }
    }
}
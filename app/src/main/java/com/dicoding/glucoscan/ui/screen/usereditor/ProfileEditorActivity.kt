package com.dicoding.glucoscan.ui.screen.usereditor

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.databinding.ActivityProfileEditorBinding
import com.dicoding.glucoscan.helper.ViewModelFactory

class ProfileEditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileEditorBinding
    private val viewModel: ProfileEditorViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.usernameInput.title.text = getString(R.string.fullname)
        binding.emailInput.title.text = getString(R.string.email)
        binding.telpInput.title.text = "Nomor telepon"
        binding.ageInput.title.text = getString(R.string.age)
        binding.genderInput.title.text = getString(R.string.gender)


        setupAction()
    }

    private fun setupAction() {
        binding.btnChangeImage.setOnClickListener {
            startGallery()
        }

        binding.icBack.setOnClickListener {
            finish()
        }

        binding.btnSubmit.setOnClickListener {
            val name = binding.usernameInput.input.text.toString()
            val email = binding.emailInput.input.text.toString()
            val telp = binding.telpInput.input.text.toString()
            val age = binding.ageInput.input.text.toString().toIntOrNull()
            val gender = binding.genderInput.input.text.toString().toBooleanStrictOrNull()

            viewModel.changeData(name, email, telp, age, gender)
        }
    }

    private fun startGallery(){
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            binding.ivProfile.setImageURI(uri)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }
}
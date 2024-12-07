package com.dicoding.glucoscan.ui.screen.usereditor

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.databinding.ActivityProfileEditorBinding

class ProfileEditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileEditorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.usernameInput.title.text = getString(R.string.fullname)
        binding.emailInput.title.text = getString(R.string.email)
        binding.telpInput.title.text = "Nomor telepon"
        binding.ageInput.title.text = getString(R.string.age)
        binding.genderInput.title.text = getString(R.string.gender)

        binding.btnChangeImage.setOnClickListener {
            startGallery()
        }

        binding.icBack.setOnClickListener{
            finish()
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
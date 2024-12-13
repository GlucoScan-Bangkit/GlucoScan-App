package com.dicoding.glucoscan.ui.screen.usereditor

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.data.response.UserData
import com.dicoding.glucoscan.databinding.ActivityProfileEditorBinding
import com.dicoding.glucoscan.helper.ViewModelFactory

class ProfileEditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileEditorBinding
    private val viewModel: ProfileEditorViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private var uri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        val user = intent.extras?.getParcelable<UserData>("user")

        Glide.with(this)
            .load(user?.profilePicture)
            .into(binding.ivProfile)

        binding.usernameInput.title.text = getString(R.string.fullname)
        binding.usernameInput.input.setText(user?.name)
        binding.emailInput.title.text = getString(R.string.email)
        binding.emailInput.input.setText(user?.email)
        binding.telpInput.title.text = "Nomor telepon"
        binding.telpInput.input.setText(user?.noHp)
        binding.ageInput.title.text = getString(R.string.age)
        binding.ageInput.input.setText(user?.age.toString())

        if(user?.gender?.toBooleanStrictOrNull() == true){
            binding.radioButtonMale.background.setTint(resources.getColor(R.color.blue_500))
            binding.radioButtonMale.setTextColor(resources.getColor(R.color.white))
            binding.radioButtonFemale.background.setTint(resources.getColor(R.color.white))
            binding.radioButtonFemale.setTextColor(resources.getColor(R.color.blue_500))
            binding.radioButtonMale.isChecked = true
        } else if (user?.gender?.toBooleanStrictOrNull() == false) {
            binding.radioButtonFemale.background.setTint(resources.getColor(R.color.blue_500))
            binding.radioButtonFemale.setTextColor(resources.getColor(R.color.white))
            binding.radioButtonMale.background.setTint(resources.getColor(R.color.white))
            binding.radioButtonMale.setTextColor(resources.getColor(R.color.blue_500))
            binding.radioButtonFemale.isChecked = true
        } else {
            binding.radioButtonFemale.background.setTint(resources.getColor(R.color.white))
            binding.radioButtonFemale.setTextColor(resources.getColor(R.color.blue_500))
            binding.radioButtonMale.background.setTint(resources.getColor(R.color.white))
            binding.radioButtonMale.setTextColor(resources.getColor(R.color.blue_500))
        }

        viewModel.result.observe(this){ result ->
            when(result){
                is Result.Success -> {
                    Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {

                }
                is Result.Error -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupAction() {
        var gender: Boolean? = null

        binding.radioGroupGender.setOnCheckedChangeListener { radioGroup, i ->
            gender = when(i){
                R.id.radioButtonMale -> {
                    binding.radioButtonMale.background.setTint(resources.getColor(R.color.blue_500))
                    binding.radioButtonMale.setTextColor(resources.getColor(R.color.white))
                    binding.radioButtonFemale.background.setTint(resources.getColor(R.color.white))
                    binding.radioButtonFemale.setTextColor(resources.getColor(R.color.blue_500))
                    binding.radioButtonMale.isChecked = true
                    true
                }
                R.id.radioButtonFemale -> {
                    binding.radioButtonFemale.background.setTint(resources.getColor(R.color.blue_500))
                    binding.radioButtonFemale.setTextColor(resources.getColor(R.color.white))
                    binding.radioButtonMale.background.setTint(resources.getColor(R.color.white))
                    binding.radioButtonMale.setTextColor(resources.getColor(R.color.blue_500))
                    binding.radioButtonFemale.isChecked = true
                    false
                }
                else -> {
                    null
                }
            }
            Log.d("ProfileEditorActivity", "gender: $gender")
        }

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
            Log.d("ProfileEditorActivity", "name: $name, email: $email, telp: $telp, age: $age, gender: $gender")

            viewModel.changeData(name, email, telp, age, gender, uri)
        }
    }

    private fun startGallery(){
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            val mimeType = contentResolver.getType(uri)
            if (mimeType == "image/jpeg" || mimeType == "image/png") {
                binding.ivProfile.setImageURI(uri)
                this.uri = uri
            } else {
                Toast.makeText(this, "Invalid file type. Please select a JPEG or PNG image.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }
}
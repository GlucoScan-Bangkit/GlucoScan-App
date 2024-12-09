package com.dicoding.glucoscan.ui.screen.scan

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.databinding.ActivityScanBinding
import com.dicoding.glucoscan.helper.ViewModelFactory
import com.dicoding.glucoscan.ui.MainActivity
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanBinding
    private val viewModel: ScanViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image = intent.getStringExtra(EXTRA_CAMERAX_IMAGE)?.toUri()
        image?.let { uri ->
            viewModel.scanImage(uri)
        }

        viewModel.scanResponse.observe(this) { result ->
            when(result){
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.containerWaiting.visibility = View.GONE
                    binding.containerScanFailed.visibility = View.VISIBLE
                }
                Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.containerWaiting.visibility = View.GONE
                    binding.containerScanSuccess.visibility = View.VISIBLE
                    val scanResponse = result.data
                    val sugarContent = scanResponse.data?.sugarContent
                    val scanDate = scanResponse.data?.scanDate
                    
                    binding.scanSuccessResult.tvSugar.text = sugarContent
                    binding.scanSuccessResult.tvTime.text = scanDate
                }
            }
        }

        setupButton()
    }

    private fun setupButton(){
//        failed button
        binding.btnTryAgain.setOnClickListener {
            finish()
        }
        binding.btnCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
//        success button
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    companion object{
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
    }
}
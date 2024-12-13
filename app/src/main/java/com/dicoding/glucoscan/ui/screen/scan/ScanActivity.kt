package com.dicoding.glucoscan.ui.screen.scan

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.databinding.ActivityScanBinding
import com.dicoding.glucoscan.helper.ViewModelFactory
import com.dicoding.glucoscan.helper.changeFormatTimestamp
import com.dicoding.glucoscan.ui.MainActivity
import com.google.android.material.textfield.TextInputLayout
import java.text.DecimalFormat
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
                    Log.e("ScanActivity", "onCreate: ${result.error}")
                }
                Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    val scanResponse = result.data
                    val sugarContent = scanResponse.data?.sugarContent
                    var scanDate = scanResponse.data?.scanDate

                    if (scanDate != null) {
                        scanDate = changeFormatTimestamp(scanDate, "HH:mm")
                    }

                    binding.scanSuccessResult.ivImage.setImageURI(image)
                    binding.scanSuccessResult.tvSugar.text = "$sugarContent gr"
                    binding.scanSuccessResult.tvTime.text = "$scanDate WIB"
                    val spoonContent = if (!sugarContent.isNullOrEmpty()) {
                        val sugarDouble = sugarContent.toDoubleOrNull() ?: 0.0
                        (sugarDouble / 12).toString()
                    } else {
                        "0"
                    }
                    val formatted = DecimalFormat("#.##")
                    val spoonContentFormatted = formatted.format(spoonContent.toDouble())
                    binding.scanSuccessResult.tvSpoon.text = "$spoonContentFormatted Spoon"


                    binding.progressBar.visibility = View.GONE
                    binding.containerWaiting.visibility = View.GONE
                    binding.containerScanSuccess.visibility = View.VISIBLE
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
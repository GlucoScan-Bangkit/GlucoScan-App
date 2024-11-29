package com.dicoding.glucoscan.ui.screen.scan

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.databinding.ActivityScanBinding
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendar = Calendar.getInstance()

        val currentDate = calendar.time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate)
        binding.inputDate.setText(formattedDate)


        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)

            val selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
            binding.inputDate.setText(selectedDate)
        }

        binding.inputDate.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.inputDateLayout.setEndIconOnClickListener {
            binding.inputDate.performClick()
        }

        val imageUri = intent.getStringExtra(EXTRA_CAMERAX_IMAGE)?.toUri()
        binding.ivScan.setImageURI(imageUri)
    }

    companion object{
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
    }
}
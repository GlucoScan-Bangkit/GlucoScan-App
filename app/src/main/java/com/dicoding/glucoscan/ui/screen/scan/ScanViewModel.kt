package com.dicoding.glucoscan.ui.screen.scan

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.data.repository.ScanRepository
import com.dicoding.glucoscan.data.response.ScanResponse
import com.dicoding.glucoscan.helper.reduceFileImage
import com.dicoding.glucoscan.helper.uriToFile
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class ScanViewModel(private val mApplication: Application, private val scanRepository: ScanRepository) : ViewModel() {
    private var _scanResponse = MutableLiveData<Result<ScanResponse>>()
    val scanResponse: LiveData<Result<ScanResponse>> = _scanResponse

    fun scanImage(uri: Uri) {
        _scanResponse.value = Result.Loading
        val file = uriToFile(uri, mApplication.baseContext).reduceFileImage()
        Log.d("Image File", "showImage: ${file.path}")

        val requestBody = file.asRequestBody("image/jpeg".toMediaType())
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestBody)

        viewModelScope.launch {
            _scanResponse.value = scanRepository.scanImage(filePart)
        }
    }
}
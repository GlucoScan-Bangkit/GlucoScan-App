package com.dicoding.glucoscan.ui.screen.scan

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.glucoscan.data.response.ScanResponse
import com.dicoding.glucoscan.data.retrofit.ApiConfig
import com.dicoding.glucoscan.helper.reduceFileImage
import com.dicoding.glucoscan.helper.uriToFile
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File

class ScanViewModel(private val mApplication: Application) : ViewModel() {
    private var _scanResponse = MutableLiveData<String>()
    val scanResponse: LiveData<String> = _scanResponse

    fun scanImage(uri: Uri) {
        val file = uriToFile(uri, mApplication.baseContext).reduceFileImage()
        Log.d("Image File", "showImage: ${file.path}")

        val requestBody = file.asRequestBody("image/jpeg".toMediaType())
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestBody)

        viewModelScope.launch {
            Log.d("ScanViewModel", "Requesting API")
            try {
                val apiService = ApiConfig.getApiService()
                val response = apiService.scanImage(filePart)
                if (response.error == false){
                    _scanResponse.value = response.sugarContent?.first()
                } else {
                    _scanResponse.value = "Error"
                }
                Log.d("ScanViewModel", "Response: $response")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("ScanViewModel", "Error response: $errorBody")
            }
        }
    }
}
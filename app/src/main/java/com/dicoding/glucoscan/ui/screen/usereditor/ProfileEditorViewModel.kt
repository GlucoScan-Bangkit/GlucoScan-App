package com.dicoding.glucoscan.ui.screen.usereditor

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.glucoscan.data.EncryptedSharedPreference.getToken
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.data.repository.UserRepository
import com.dicoding.glucoscan.data.response.ChangeDataResponse
import com.dicoding.glucoscan.helper.reduceFileImage
import com.dicoding.glucoscan.helper.uriToFile
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class ProfileEditorViewModel(private val mApplication: Application, private val userRepository: UserRepository): ViewModel() {
    private var _result = MutableLiveData<Result<ChangeDataResponse>>()
    val result: LiveData<Result<ChangeDataResponse>> = _result

    fun changeData(name: String, email: String, no_phone: String, age: Int?, gender: Boolean?, uri: Uri?) {
        var filePart: MultipartBody.Part? = null
        if (uri != null) {
            val file = uriToFile(uri, mApplication.baseContext).reduceFileImage()
            Log.d("Image File", "showImage: ${file.path}")
            val requestBody = file.asRequestBody("image/jpeg".toMediaType())

            filePart = MultipartBody.Part.createFormData("file", file.name, requestBody)
        }
        viewModelScope.launch {
            _result.value = userRepository.changeData(getToken(mApplication.baseContext)!!, name, email, no_phone, age, gender, filePart)
        }
    }
}
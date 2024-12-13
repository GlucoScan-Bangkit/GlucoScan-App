package com.dicoding.glucoscan.ui.screen.usereditor

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.glucoscan.data.EncryptedSharedPreference.getToken
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.data.repository.UserRepository
import com.dicoding.glucoscan.data.response.ChangeDataResponse
import kotlinx.coroutines.launch

class ProfileEditorViewModel(private val mApplication: Application, private val userRepository: UserRepository): ViewModel() {
    private var _result = MutableLiveData<Result<ChangeDataResponse>>()
    val result: LiveData<Result<ChangeDataResponse>> = _result

    fun changeData(name: String, email: String, no_phone: String, age: Int?, gender: Boolean?) {
        viewModelScope.launch {
            _result.value = userRepository.changeData(getToken(mApplication.baseContext)!!, name, email, no_phone, age, gender)
        }
    }
}
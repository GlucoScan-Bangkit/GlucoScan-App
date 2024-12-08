package com.dicoding.glucoscan.ui.screen.usereditor

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.glucoscan.data.EncryptedSharedPreference.getToken
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.data.repository.UserRepository
import com.dicoding.glucoscan.data.response.ChangePasswordResponse
import kotlinx.coroutines.launch

class PasswordEditorViewModel(
    private val mApplication: Application,
    private val userRepository: UserRepository
) : ViewModel() {
    private var _result = MutableLiveData<Result<ChangePasswordResponse>>()
    val result: LiveData<Result<ChangePasswordResponse>> = _result

    fun updatePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            _result.value = userRepository.updatePassword(getToken(mApplication)!!, oldPassword, newPassword)
        }
    }
}
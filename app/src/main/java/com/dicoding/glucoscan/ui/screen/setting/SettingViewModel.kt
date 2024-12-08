package com.dicoding.glucoscan.ui.screen.setting

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.glucoscan.data.repository.LoginRepository
import kotlinx.coroutines.launch

class SettingViewModel(mApplication: Application, private val loginRepository: LoginRepository) : ViewModel() {
    fun logout(token: String) {
        viewModelScope.launch {
            loginRepository.logout(token)
        }
    }
}
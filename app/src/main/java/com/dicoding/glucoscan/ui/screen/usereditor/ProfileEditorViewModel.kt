package com.dicoding.glucoscan.ui.screen.usereditor

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.glucoscan.data.EncryptedSharedPreference.getToken
import com.dicoding.glucoscan.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileEditorViewModel(private val mApplication: Application, private val userRepository: UserRepository): ViewModel() {
    fun changeData(name: String, email: String) {
        viewModelScope.launch {
            userRepository.changeData(getToken(mApplication.baseContext)!!, name, email)
        }
    }
}
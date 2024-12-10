package com.dicoding.glucoscan.ui.screen

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.glucoscan.data.EncryptedSharedPreference.getToken
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.data.repository.UserRepository
import com.dicoding.glucoscan.data.response.DashboardResponse
import kotlinx.coroutines.launch

class MainViewModel(private val mApplication: Application, private val repository: UserRepository): ViewModel() {
    private var _user = MutableLiveData<Result<DashboardResponse>>()
    val user: LiveData<Result<DashboardResponse>> = _user

    fun getDashboard() {
        viewModelScope.launch {
            _user.value = repository.getDashboard(getToken(mApplication.baseContext)!!)
        }
    }
}
package com.dicoding.glucoscan.ui.screen.home

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

class HomeViewModel(private val mApplication: Application, private val repository: UserRepository) : ViewModel() {
    private var _user = MutableLiveData<Result<DashboardResponse>>()
    val user: LiveData<Result<DashboardResponse>> = _user

    private val _daily = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val daily: LiveData<String> = _daily

    fun getDashboard(token: String) {
        viewModelScope.launch {
            _user.value = repository.getDashboard(token)
        }
    }

    fun getDailyRoundedData() : List<String> {
        return listOf("0 gr", "100 gr", "200 gr", "300 gr" , "100 gr", "200 gr", "300 gr", "100 gr", "200 gr", "300 gr")
    }
}
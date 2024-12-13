package com.dicoding.glucoscan.ui.screen.home

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.glucoscan.data.EncryptedSharedPreference.getToken
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.data.repository.UserRepository
import com.dicoding.glucoscan.data.response.DashboardResponse
import com.dicoding.glucoscan.helper.get7DateBehind
import kotlinx.coroutines.launch

class HomeViewModel(private val mApplication: Application, private val repository: UserRepository) : ViewModel() {
    private var _user = MutableLiveData<Result<DashboardResponse>>()
    val user: LiveData<Result<DashboardResponse>> = _user

    private val _daily = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val daily: LiveData<String> = _daily

    fun getDashboard() {
        viewModelScope.launch {
            _user.value = repository.getDashboard(getToken(mApplication.baseContext)!!)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDailyRoundedData() : List<List<String>> {
        val list = get7DateBehind()
        val data = listOf("0", "10", "20", "30" , "100", "200", "300", "10", "20", "30")
        var fullList = mutableListOf<List<String>>()

        for (i in list.indices){
            val dataStartIndex = i * 2
            if (dataStartIndex < data.size) {
                val dailyData = listOf(list[i]) + data.subList(dataStartIndex, minOf(dataStartIndex + 2, data.size))
                fullList.add(dailyData)
            }
        }
        return fullList
    }
}
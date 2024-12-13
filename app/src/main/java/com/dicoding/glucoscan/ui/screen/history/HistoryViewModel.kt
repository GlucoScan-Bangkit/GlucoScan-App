package com.dicoding.glucoscan.ui.screen.history

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.glucoscan.data.EncryptedSharedPreference.getToken
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.data.repository.ScanRepository
import com.dicoding.glucoscan.data.response.GetScanResponse
import com.dicoding.glucoscan.helper.createTimestamp
import com.dicoding.glucoscan.helper.get7DateBehind
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class HistoryViewModel(private val mApplication: Application, private val repository: ScanRepository) : ViewModel() {

    private var _history = MutableLiveData<Result<GetScanResponse>>()
    val history: LiveData<Result<GetScanResponse>> = _history

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDate(): List<String> {
        return get7DateBehind()
    }

    fun getHistory(date: String){
        viewModelScope.launch {
            _history.value = repository.getHistory(
                getToken(mApplication)!!,
                date
            )
        }
    }
}
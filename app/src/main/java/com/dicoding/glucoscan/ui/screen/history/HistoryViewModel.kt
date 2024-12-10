package com.dicoding.glucoscan.ui.screen.history

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.glucoscan.data.EncryptedSharedPreference.getToken
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.data.repository.ScanRepository
import com.dicoding.glucoscan.data.response.GetScanResponse
import com.dicoding.glucoscan.helper.createTimestamp
import kotlinx.coroutines.launch

class HistoryViewModel(private val mApplication: Application, private val repository: ScanRepository) : ViewModel() {

    private var _history = MutableLiveData<Result<GetScanResponse>>()
    val history: LiveData<Result<GetScanResponse>> = _history

    fun getDate(): List<String> {
        val data = createTimestamp("date").toInt()
        val dates = mutableListOf<String>()
        for (i in 0..6) {
            dates.add((data - i).toString())
        }
        return dates
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
package com.dicoding.glucoscan.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _daily = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val daily: LiveData<String> = _daily

    fun getDailyRoundedData() : List<String> {
        return listOf("0 gr", "100 gr", "200 gr", "300 gr" , "100 gr", "200 gr", "300 gr", "100 gr", "200 gr", "300 gr")
    }
}
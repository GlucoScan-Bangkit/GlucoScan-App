package com.dicoding.glucoscan.ui.screen.history

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.glucoscan.helper.createTimestamp

class HistoryViewModel(private val mApplication: Application) : ViewModel() {
    fun getHistoryData(): List<String> {
        return listOf("22", "23", "24", "25", "26", "27", "28", "29", "30", "31")
    }

    fun getDate(): List<String> {
        val data = createTimestamp("date").toInt()
        val dates = mutableListOf<String>()
        for (i in 0..6) {
            dates.add((data - i).toString())
        }
        return dates
    }

    fun getHistory(date: String): List<String> {
        val datas = mutableListOf<String>()
        for (i in 0..6){
            datas.add((0..100).random().toString())
        }
        return datas
    }
}
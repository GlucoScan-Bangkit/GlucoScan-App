package com.dicoding.glucoscan.ui.screen.history

import androidx.lifecycle.ViewModel
import com.dicoding.glucoscan.helper.timeStamp

class HistoryViewModel : ViewModel() {
    fun getHistoryData(): List<String> {
        return listOf("22", "23", "24", "25", "26", "27", "28", "29", "30", "31")
    }

    fun getDate(): List<String> {
        val data = timeStamp("date").toInt()
        val dates = mutableListOf<String>()
        for (i in 0..6) {
            dates.add((data - i).toString())
        }
        return dates
    }
}
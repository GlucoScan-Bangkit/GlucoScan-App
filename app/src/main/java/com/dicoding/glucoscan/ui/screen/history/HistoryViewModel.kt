package com.dicoding.glucoscan.ui.screen.history

import androidx.lifecycle.ViewModel

class HistoryViewModel : ViewModel() {
    fun getHistoryData(): List<String> {
        return listOf("22", "23", "24", "25", "26", "27", "28", "29", "30", "31")
    }
}
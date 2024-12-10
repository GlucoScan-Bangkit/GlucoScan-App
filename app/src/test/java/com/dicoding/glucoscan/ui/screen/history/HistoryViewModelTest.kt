package com.dicoding.glucoscan.ui.screen.history

import com.dicoding.glucoscan.helper.createTimestamp

import org.junit.Test

class HistoryViewModelTest {

    @Test
    fun getDate() {
        val data = createTimestamp("date").toInt()
        val dates = mutableListOf<String>()
        for (i in 0..6) {
            dates.add((data - i).toString())
            println(dates[i])
        }
    }
}
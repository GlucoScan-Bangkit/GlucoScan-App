package com.dicoding.glucoscan.helper

import org.junit.Assert.*

import org.junit.Test

class UtilsKtTest {

    @Test
    fun changeFormatTimestamp() {
        val data = "2024-12-10 03:35:07"
        val newData = com.dicoding.glucoscan.helper.changeFormatTimestamp(data, "HH:mm")
        println(newData)
    }
}
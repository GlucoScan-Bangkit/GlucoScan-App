package com.dicoding.glucoscan.helper

import org.junit.Assert.*

import org.junit.Test

class HistoryAdapterTest {

    @Test
    fun onCreateViewHolder() {
    }

    @Test
    fun onBindViewHolder() {
    }

    @Test
    fun getItemCount() {
        val adapter = HistoryAdapter(emptyList())
        assertEquals(0, adapter.itemCount)
    }
}
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
        val list = listOf("item1", "item2", "item3")
        val adapter = HistoryAdapter(list)
        assertEquals(3, adapter.itemCount)
    }
}
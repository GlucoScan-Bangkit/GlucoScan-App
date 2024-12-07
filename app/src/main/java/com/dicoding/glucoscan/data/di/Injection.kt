package com.dicoding.glucoscan.data.di

import android.content.Context
import com.dicoding.glucoscan.data.repository.ScanRepository
import com.dicoding.glucoscan.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): ScanRepository {
        val apiService = ApiConfig.getApiService()
        return ScanRepository.getInstance(apiService)
    }
}
package com.dicoding.glucoscan.data.di

import android.content.Context
import com.dicoding.glucoscan.data.repository.LoginRepository
import com.dicoding.glucoscan.data.repository.Repository
import com.dicoding.glucoscan.data.repository.ScanRepository
import com.dicoding.glucoscan.data.retrofit.ApiConfig

object Injection {
    fun <T> provideRepository(context: Context, type: String): T {
        val apiService = ApiConfig.getApiService()
        return when(type){
            "scan" -> ScanRepository.getInstance(apiService) as T
            "register" -> LoginRepository.getInstance(apiService) as T
            "login" -> LoginRepository.getInstance(apiService) as T
            else -> throw IllegalArgumentException("Unknown type: $type")
        }
    }
}
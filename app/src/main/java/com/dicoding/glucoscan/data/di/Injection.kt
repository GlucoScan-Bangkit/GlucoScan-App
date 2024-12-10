package com.dicoding.glucoscan.data.di

import android.content.Context
import com.dicoding.glucoscan.data.repository.LoginRepository
import com.dicoding.glucoscan.data.repository.Repository
import com.dicoding.glucoscan.data.repository.ScanRepository
import com.dicoding.glucoscan.data.repository.UserRepository
import com.dicoding.glucoscan.data.retrofit.ApiConfig

object Injection {
    @Suppress("UNCHECKED_CAST")
    fun <T> provideRepository(context: Context, type: String): T {
        val apiService = ApiConfig.getApiService()
        return when(type){
            "home" -> UserRepository.getInstance(apiService) as T
            "scan" -> ScanRepository.getInstance(ApiConfig.getApiService2()) as T
            "register" -> LoginRepository.getInstance(apiService) as T
            "login" -> LoginRepository.getInstance(apiService) as T
            "logout" -> LoginRepository.getInstance(apiService) as T
            "changePassword" -> UserRepository.getInstance(apiService) as T
            "changeData" -> UserRepository.getInstance(apiService) as T
            else -> throw IllegalArgumentException("Unknown type: $type")
        }
    }
}
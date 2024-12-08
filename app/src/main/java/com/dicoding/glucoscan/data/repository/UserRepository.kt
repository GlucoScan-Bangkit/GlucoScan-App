package com.dicoding.glucoscan.data.repository

import com.dicoding.glucoscan.data.EncryptedSharedPreference.getToken
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.data.response.ChangePasswordResponse
import com.dicoding.glucoscan.data.response.DashboardResponse
import com.dicoding.glucoscan.data.retrofit.ApiService

class UserRepository(
    private val apiService: ApiService
): Repository() {
    suspend fun getDashboard(token: String) : Result<DashboardResponse> {
        return try {
            val response = apiService.getDashboard("Bearer $token")
            Result.Success(response)
        } catch (e: Exception){
            Result.Error(e.message.toString())
        }
    }

    suspend fun updatePassword(token: String, oldPassword: String, newPassword: String) : Result<ChangePasswordResponse> {
        return try {
            val response = apiService.changePassword("Bearer $token", oldPassword, newPassword)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }
    }

    companion object{
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService
        ) = instance ?: synchronized(this){
            instance ?: UserRepository(apiService)
        }.also { instance = it }
    }
}
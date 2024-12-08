package com.dicoding.glucoscan.data.repository

import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.data.repository.ScanRepository.Companion
import com.dicoding.glucoscan.data.response.LoginRequest
import com.dicoding.glucoscan.data.response.LoginResponse
import com.dicoding.glucoscan.data.response.RegisterRequest
import com.dicoding.glucoscan.data.response.RegisterResponse
import com.dicoding.glucoscan.data.retrofit.ApiService

class LoginRepository private constructor(
    private val apiService: ApiService
): Repository(){
    suspend fun register(name: String, email: String, password: String): Result<RegisterResponse> {
        return try {
            val response = apiService.postRegister(RegisterRequest(name, email, password))
            if (response.message == "Registrasi berhasil"){
                Result.Success(response)
            } else {
                Result.Error(response.message ?: "Unknown error")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.postLogin(LoginRequest(email, password))
            if (response.message == "Login berhasil"){
                Result.Success(response)
            } else {
                Result.Error(response.message ?: "Unknown error")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }

    companion object{
        @Volatile
        private var instance: LoginRepository? = null
        fun getInstance(apiService: ApiService
        ): LoginRepository =
            instance ?: synchronized(this){
                instance ?: LoginRepository(apiService)
            }.also { instance = it }
    }
}
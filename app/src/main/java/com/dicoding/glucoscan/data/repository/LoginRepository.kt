package com.dicoding.glucoscan.data.repository

import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.data.repository.ScanRepository.Companion
import com.dicoding.glucoscan.data.response.LoginRequest
import com.dicoding.glucoscan.data.response.LoginResponse
import com.dicoding.glucoscan.data.response.LogoutResponse
import com.dicoding.glucoscan.data.response.RegisterRequest
import com.dicoding.glucoscan.data.response.RegisterResponse
import com.dicoding.glucoscan.data.retrofit.ApiService

class LoginRepository private constructor(
    private val apiService: ApiService
): Repository(){
    suspend fun register(name: String, email: String, password: String): Result<RegisterResponse> {
        return try {
            val response = apiService.postRegister(RegisterRequest(name, email, password))
            if (response.isSuccessful){
                val responseBody = response.body()
                if (responseBody?.message == "Registrasi berhasil"){
                    Result.Success(responseBody)
                } else {
                    Result.Error(responseBody?.message ?: "Unknown error")
                }
            } else {
                val errorBody = parseError(response.errorBody()?.string())
                Result.Error(errorBody)
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.postLogin(LoginRequest(email, password))
            if (response.isSuccessful){
                val responseBody = response.body()
                if (responseBody?.message == "Login berhasil"){
                    Result.Success(responseBody)
                } else {
                    Result.Error(responseBody?.message ?: "Unknown error")
                }
            } else {
                val errorBody = parseError(response.errorBody()?.string())
                Result.Error(errorBody)
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun logout(token: String) : Result<LogoutResponse> {
        return try {
            val response = apiService.postLogout("Bearer $token")
            if (response.isSuccessful){
                val responseBody = response.body()
                if (responseBody?.message == "Logout berhasil"){
                    Result.Success(responseBody)
                } else {
                    Result.Error(responseBody?.message ?: "Unknown error")
                }
            } else {
                val errorBody = parseError(response.errorBody()?.string())
                Result.Error(errorBody)
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
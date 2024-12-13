package com.dicoding.glucoscan.data.repository

import android.util.Log
import com.dicoding.glucoscan.data.EncryptedSharedPreference.getToken
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.data.response.ChangeData
import com.dicoding.glucoscan.data.response.ChangeDataResponse
import com.dicoding.glucoscan.data.response.ChangePasswordRequest
import com.dicoding.glucoscan.data.response.ChangePasswordResponse
import com.dicoding.glucoscan.data.response.DashboardResponse
import com.dicoding.glucoscan.data.retrofit.ApiService
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import java.io.File

class UserRepository(
    private val apiService: ApiService
): Repository() {
    suspend fun getDashboard(token: String) : Result<DashboardResponse> {
        return try {
            val response = apiService.getDashboard("Bearer $token")
            if (response.isSuccessful){
                val responseBody = response.body()
                if (responseBody?.message == "Berhasil mengambil data dashboard"){
                    Result.Success(responseBody)
                } else {
                    Result.Error(responseBody?.message ?: "Unknown error")
                }
            } else {
                val errorBody = parseError(response.errorBody()?.string())
                Result.Error(errorBody)
            }
        } catch (e: Exception){
            Result.Error(e.message.toString())
        }
    }

    suspend fun updatePassword(token: String, oldPassword: String, newPassword: String) : Result<ChangePasswordResponse> {
        return try {
            val response = apiService.changePassword("Bearer $token", ChangePasswordRequest(oldPassword, newPassword))
            if (response.isSuccessful){
                val responseBody = response.body()
                if (responseBody?.message == "Password berhasil diperbarui"){
                    Result.Success(responseBody)
                } else {
                    Result.Error(responseBody?.message ?: "Unknown error")
                }
            } else {
                val errorBody = parseError(response.errorBody()?.string())
                Result.Error(errorBody)
            }
        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }
    }

    suspend fun changeData(token: String, name: String, email: String, no_phone: String, age: Int?, gender: Boolean?, photo: MultipartBody.Part?) : Result<ChangeDataResponse> {
        return try {
            val response = apiService.changeData("Bearer $token", ChangeData(name, email, no_phone, age, gender), photo)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody?.message == "Data berhasil diperbarui") {
                    Result.Success(responseBody)
                } else {
                    Result.Error(responseBody?.message ?: "Unknown error")
                }
            } else {
                val errorBody = parseError(response.errorBody()?.string())
                Result.Error(errorBody)
            }
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
package com.dicoding.glucoscan.data.repository

import android.util.Log
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.data.response.GetScanResponse
import com.dicoding.glucoscan.data.response.ScanResponse
import com.dicoding.glucoscan.data.retrofit.ApiConfig
import com.dicoding.glucoscan.data.retrofit.ApiService
import okhttp3.MultipartBody
import retrofit2.HttpException

class ScanRepository private constructor(
    private val apiService: ApiService
): Repository(){
    suspend fun scanImage(token: String, filePart: MultipartBody.Part): Result<ScanResponse> {
        return try {
            val response = apiService.scanImage("Bearer $token", filePart)
            if (response.isSuccessful){
                val responseBody = response.body()
                if (responseBody?.error == false){
                    Result.Success(responseBody)
                } else {
                    Result.Error(responseBody?.message ?: "Unknown error")
                }
            } else {
                val errorBody = parseError(response.message())
                Result.Error(errorBody)
            }
        } catch (e: HttpException) {
            Result.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun getHistory(token: String, date: String): Result<GetScanResponse> {
        return try {
            val response = apiService.getHistory("Bearer $token", date)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody?.error == false) {
                    Result.Success(responseBody)
                } else {
                    Result.Error(responseBody?.message ?: "Unknown error")
                }
            } else {
                val errorBody = parseError(response.message())
                Result.Error(errorBody)
            }
        } catch (e: HttpException) {
            Result.Error(e.message ?: "Unknown error")
        }
    }

    companion object{
        @Volatile
        private var instance: ScanRepository? = null
        fun getInstance(
            apiService: ApiService
        ): ScanRepository =
            instance ?: synchronized(this){
                instance ?: ScanRepository(apiService)
            }.also { instance = it }
    }
}
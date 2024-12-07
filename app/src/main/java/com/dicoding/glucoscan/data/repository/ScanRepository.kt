package com.dicoding.glucoscan.data.repository

import android.util.Log
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.data.response.ScanResponse
import com.dicoding.glucoscan.data.retrofit.ApiConfig
import com.dicoding.glucoscan.data.retrofit.ApiService
import okhttp3.MultipartBody
import retrofit2.HttpException

class ScanRepository private constructor(
    private val apiService: ApiService
): Repository(){
    suspend fun scanImage(filePart: MultipartBody.Part): Result<ScanResponse> {
        return try {
            val response = apiService.scanImage(filePart)
            if (response.error == false){
                Result.Success(response)
            } else {
                Result.Error((response.error ?: "Unknown error").toString())
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
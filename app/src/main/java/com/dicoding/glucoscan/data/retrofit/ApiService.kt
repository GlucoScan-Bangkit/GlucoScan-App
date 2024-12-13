package com.dicoding.glucoscan.data.retrofit

import com.dicoding.glucoscan.data.response.ChangeData
import com.dicoding.glucoscan.data.response.ChangeDataResponse
import com.dicoding.glucoscan.data.response.ChangePasswordRequest
import com.dicoding.glucoscan.data.response.ChangePasswordResponse
import com.dicoding.glucoscan.data.response.DashboardResponse
import com.dicoding.glucoscan.data.response.GetScanResponse
import com.dicoding.glucoscan.data.response.LoginRequest
import com.dicoding.glucoscan.data.response.LoginResponse
import com.dicoding.glucoscan.data.response.LogoutResponse
import com.dicoding.glucoscan.data.response.RegisterRequest
import com.dicoding.glucoscan.data.response.RegisterResponse
import com.dicoding.glucoscan.data.response.ScanResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.io.File

interface ApiService {
    @POST("login")
    suspend fun postLogin(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("register")
    suspend fun postRegister(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("logout")
    suspend fun postLogout(
        @Header("Authorization") token: String
    ): Response<LogoutResponse>

    @Multipart
    @POST("predict")
    suspend fun scanImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
    ) : Response<ScanResponse>

    @PATCH("dashboard/ChangePassword")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body request: ChangePasswordRequest
    ) : Response<ChangePasswordResponse>

    @Multipart
    @PATCH("dashboard/gantiData")
    suspend fun changeData(
        @Header("Authorization") token: String,
        @Body request: ChangeData,
        @Part pictureProfile: MultipartBody.Part? = null
    ) : Response<ChangeDataResponse>

    @GET("dashboard")
    suspend fun getDashboard(
        @Header("Authorization") token: String
    ) : Response<DashboardResponse>

    @GET("get_history")
    suspend fun getHistory(
        @Header("Authorization") token: String,
        @Query("date") date: String
    ) : Response<GetScanResponse>
}
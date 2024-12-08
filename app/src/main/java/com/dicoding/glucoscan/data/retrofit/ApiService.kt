package com.dicoding.glucoscan.data.retrofit

import com.dicoding.glucoscan.data.response.ChangePasswordResponse
import com.dicoding.glucoscan.data.response.DashboardResponse
import com.dicoding.glucoscan.data.response.LoginRequest
import com.dicoding.glucoscan.data.response.LoginResponse
import com.dicoding.glucoscan.data.response.LogoutResponse
import com.dicoding.glucoscan.data.response.RegisterRequest
import com.dicoding.glucoscan.data.response.RegisterResponse
import com.dicoding.glucoscan.data.response.ScanResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

interface ApiService {
    @POST("login")
    suspend fun postLogin(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("register")
    suspend fun postRegister(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("logout")
    suspend fun postLogout(
        @Header("Authorization") token: String
    ): LogoutResponse

    @Multipart
    @POST("predict")
    suspend fun scanImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
    ) : ScanResponse

    @PATCH
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Field("passwordLama") oldPassword: String,
        @Field("passwordBaru") newPassword: String
    ) : ChangePasswordResponse

    @GET("dashboard")
    suspend fun getDashboard(
        @Header("Authorization") token: String
    ) : DashboardResponse
}
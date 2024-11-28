package com.dicoding.glucoscan.data.retrofit

import com.dicoding.glucoscan.data.response.LoginRequest
import com.dicoding.glucoscan.data.response.LoginResponse
import com.dicoding.glucoscan.data.response.RegisterRequest
import com.dicoding.glucoscan.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun postLogin(@Body request: LoginRequest): Call<LoginResponse>

    @POST("register")
    fun postRegister(@Body request: RegisterRequest): Call<RegisterResponse>
}
package com.dicoding.glucoscan.data.retrofit

import android.util.Log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        private val cookieJar = object : CookieJar{
            private val cookieStore = HashMap<HttpUrl, List<Cookie>>()

            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                Log.d("ApiConfig", "loadForRequest: ${cookieStore[url]}")
                return cookieStore[url] ?: emptyList()
            }

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                cookieStore[url] = cookies
                Log.d("ApiConfig", "saveFromResponse: $cookies")
            }
        }

        fun getApiService(): ApiService{
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.7:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
        fun getApiService2(): ApiService{
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.7:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
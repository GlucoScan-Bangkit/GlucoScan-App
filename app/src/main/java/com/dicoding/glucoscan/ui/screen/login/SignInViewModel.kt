package com.dicoding.glucoscan.ui.screen.login

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.dicoding.glucoscan.data.response.LoginRequest
import com.dicoding.glucoscan.data.response.LoginResponse
import com.dicoding.glucoscan.data.retrofit.ApiConfig
import com.dicoding.glucoscan.helper.FirebaseHelper
import com.dicoding.glucoscan.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel(private val mApplication: Application) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private var email = ""
    private var password = ""

    fun setEmailPassword(email: String, password: String) {
        this.email = email
        this.password = password
    }

    fun signIn(){
        // Implementasi signIn di sini
        FirebaseHelper(
            context = mApplication.baseContext,
            auth = auth
        ).signIn(email, password)
    }

    fun signIn2(){
        val client = ApiConfig.getApiService().postLogin(LoginRequest(
            email = email,
            password = password
        ))
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(p0: Call<LoginResponse>, p1: Response<LoginResponse>) {
                if (p1.isSuccessful) {
                    val responseBody = p1.body()
                    if (responseBody != null) {
                        // Login berhasil
                        Log.d("Sign in", "Success")
                        val intent = Intent(mApplication.baseContext, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        mApplication.baseContext.startActivity(intent)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${p1.message()}")
                }
            }

            override fun onFailure(p0: Call<LoginResponse>, p1: Throwable) {
                Log.e(TAG, "onFailure: ${p1.message}")

            }

        })
    }

    companion object{
        val TAG = SignInViewModel::class.java.simpleName
    }
}
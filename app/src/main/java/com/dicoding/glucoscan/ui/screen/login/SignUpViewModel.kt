package com.dicoding.glucoscan.ui.screen.login

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.glucoscan.data.EncryptedSharedPreference.saveUID
import com.dicoding.glucoscan.data.response.RegisterRequest
import com.dicoding.glucoscan.data.response.RegisterResponse
import com.dicoding.glucoscan.data.retrofit.ApiConfig
import com.dicoding.glucoscan.helper.FirebaseHelper
import com.dicoding.glucoscan.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel(private val mApplication: Application) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    fun setDatatoSignUp(email: String, password: String){
        _email.value = email
        _password.value = password
    }

    fun signUp() {
        FirebaseHelper(
            context = mApplication.baseContext,
            auth = auth
        ).signUp(email.value.toString(), password.value.toString())
    }

    fun signUp2(){
        val client = ApiConfig.getApiService().postRegister(
            RegisterRequest(
                name = "name",
                email = email.value.toString(),
                password = password.value.toString()
        ))
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(p0: Call<RegisterResponse>, p1: Response<RegisterResponse>) {
                if (p1.isSuccessful) {
                    val responseBody = p1.body()
                    if (responseBody != null) {
                        // Signup berhasil
                        Log.d("Sign up", "Success")
                        saveUID(
                            uid = p1.body()?.data?.idToken.toString(),
                            context = mApplication.baseContext
                        )
                        val intent = Intent(mApplication.baseContext, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        mApplication.baseContext.startActivity(intent)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${p1.message()}")
                }
            }

            override fun onFailure(p0: Call<RegisterResponse>, p1: Throwable) {
                Log.e(TAG, "onFailure: ${p1.message}")
            }

        })
    }

    companion object{
        val TAG = SignUpViewModel::class.java.simpleName
    }
}
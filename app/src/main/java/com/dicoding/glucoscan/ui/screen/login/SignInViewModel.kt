package com.dicoding.glucoscan.ui.screen.login

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.data.repository.LoginRepository
import com.dicoding.glucoscan.data.response.LoginRequest
import com.dicoding.glucoscan.data.response.LoginResponse
import com.dicoding.glucoscan.data.retrofit.ApiConfig
import com.dicoding.glucoscan.helper.FirebaseHelper
import com.dicoding.glucoscan.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel(private val mApplication: Application, private val loginRepository: LoginRepository) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private var email = ""
    private var password = ""

    private var _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

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
        viewModelScope.launch {
            _loginResult.value = Result.Loading
            val result = loginRepository.login(email, password)
            _loginResult.value = result
        }
    }

    companion object{
        val TAG: String = SignInViewModel::class.java.simpleName
    }
}
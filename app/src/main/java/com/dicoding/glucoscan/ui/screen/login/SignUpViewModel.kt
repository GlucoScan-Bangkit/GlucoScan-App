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
import com.dicoding.glucoscan.data.response.RegisterRequest
import com.dicoding.glucoscan.data.response.RegisterResponse
import com.dicoding.glucoscan.data.retrofit.ApiConfig
import com.dicoding.glucoscan.helper.FirebaseHelper
import com.dicoding.glucoscan.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel(private val mApplication: Application, private val registerRepository: LoginRepository) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _registerResult = MutableLiveData<Result<RegisterResponse>>()
    val registerResult: LiveData<Result<RegisterResponse>> = _registerResult

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    fun setDatatoSignUp(email: String, password: String){
        _username.value = email.split("@")[0]
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
        viewModelScope.launch {
            _registerResult.value = Result.Loading
            val result = registerRepository.register(username.value.toString(), email.value.toString(), password.value.toString())
            _registerResult.value = result
        }
    }

    companion object{
        val TAG = SignUpViewModel::class.java.simpleName
    }
}
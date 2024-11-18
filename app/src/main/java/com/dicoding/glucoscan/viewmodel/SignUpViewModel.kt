package com.dicoding.glucoscan.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.glucoscan.helper.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth

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
}
package com.dicoding.glucoscan.ui.screen.login

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.glucoscan.helper.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth

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
}
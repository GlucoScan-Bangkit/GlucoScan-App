package com.dicoding.glucoscan.data.response

// Data class untuk body request
data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val name: String, val email: String, val password: String)

// Data class untuk response
data class LoginResponse(
    val message: String,
    val data: UserData
)

data class UserData(
    val email: String,
    val idToken: String
)

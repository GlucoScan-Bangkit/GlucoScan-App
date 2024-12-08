package com.dicoding.glucoscan.data.response

// Data class untuk body request
data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val name: String, val email: String, val password: String)
data class ChangePasswordRequest(val oldPassword: String, val newPassword: String)
data class ChangeData(val name: String, val email: String)

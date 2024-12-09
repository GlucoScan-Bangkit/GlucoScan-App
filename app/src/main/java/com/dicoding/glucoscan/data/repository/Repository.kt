package com.dicoding.glucoscan.data.repository

import com.google.gson.Gson

open class Repository {
    data class ApiError(val status: String, val message: String)

    fun parseError(errorBody: String?): String {
        return try {
            val error = Gson().fromJson(errorBody, ApiError::class.java)
            error.message
        } catch (e: Exception) {
            "Unknown error"
        }
    }
}
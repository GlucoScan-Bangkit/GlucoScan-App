package com.dicoding.glucoscan.data.response

import com.google.gson.annotations.SerializedName

data class DashboardResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: UserData? = null
)

data class UserData(

	@field:SerializedName("profilePicture")
	val profilePicture: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

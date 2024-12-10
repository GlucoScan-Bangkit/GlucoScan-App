package com.dicoding.glucoscan.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DashboardResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: UserData? = null
)

@Parcelize
data class UserData(

	@field:SerializedName("profilePicture")
	val profilePicture: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable

data class ChangePasswordResponse(
	@field:SerializedName("message")
	val message: String? = null
)

data class ChangeDataResponse(

	@field:SerializedName("updatedData")
	val updatedData: UpdatedData? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class UpdatedData(

	@field:SerializedName("no_hp")
	val noHp: String? = null,

	@field:SerializedName("gender")
	val gender: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("age")
	val age: Int? = null
)

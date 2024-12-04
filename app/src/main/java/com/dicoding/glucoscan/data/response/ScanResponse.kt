package com.dicoding.glucoscan.data.response

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@field:SerializedName("sugar_content")
	val sugarContent: List<String?>? = null,

	@field:SerializedName("data_status")
	val dataStatus: String? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)

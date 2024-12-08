package com.dicoding.glucoscan.data.response

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("sugar_content")
	val sugarContent: String? = null,

	@field:SerializedName("scan_date")
	val scanDate: String? = null
)

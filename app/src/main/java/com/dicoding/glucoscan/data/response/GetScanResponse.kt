package com.dicoding.glucoscan.data.response

import com.google.gson.annotations.SerializedName

data class GetScanResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("kandungan_gula")
	val kandunganGula: List<Double?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)

package com.example.nowlocationn.model

import com.google.gson.annotations.SerializedName

data class NominatimDto(
    @SerializedName("lat") val lat: String,
    @SerializedName("lon")    val lon:String,
    @SerializedName("display_name") val display_name:String
)
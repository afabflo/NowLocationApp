package com.example.nowlocationn.model

import com.google.gson.annotations.SerializedName

data class MunicipioDto(
    @SerializedName("parent_code") val parentCode: String,
    @SerializedName("code") val code: String,
    @SerializedName("label") val label:String?
)
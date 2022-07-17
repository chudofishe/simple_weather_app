package com.chudofishe.weatherapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ConditionDto(
    val code: Int,
    val icon: String,
    val text: String
)
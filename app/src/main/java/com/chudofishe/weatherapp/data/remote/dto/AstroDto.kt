package com.chudofishe.weatherapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class AstroDto(
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)
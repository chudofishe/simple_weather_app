package com.chudofishe.weatherapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ForecastDto(
    val forecastday: List<ForecastdayDto>
)
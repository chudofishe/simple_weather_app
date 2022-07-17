package com.chudofishe.weatherapp.domain.model

import com.chudofishe.weatherapp.data.remote.dto.ConditionDto
import com.google.gson.annotations.SerializedName

data class CurrentWeather (
    val cloud: Int,
    val conditionIcon: String,
    val conditionText: String,
    val feelsLike: Double,
    val humidity: Int,
    val temp: Int,
    val uv: Double,
    val windKph: Double
    )
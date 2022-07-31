package com.chudofishe.weatherapp.domain.model

data class CurrentWeather (
    val city: String,
    val country: String,
    val cloud: Int,
    val conditionIcon: String,
    val conditionText: String,
    val feelsLike: Double,
    val humidity: Int,
    val temp: Int,
    val uv: Double,
    val windKph: Double
    )
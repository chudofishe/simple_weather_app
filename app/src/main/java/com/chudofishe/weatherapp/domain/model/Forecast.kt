package com.chudofishe.weatherapp.domain.model

data class Forecast (
    val dateEpoch: Int,
    val avgHumidity: Double,
    val avgTemp: Double,
    val icon: String,
    val text: String,
    val details: ForecastDetails
)
package com.chudofishe.weatherapp.domain.model

import java.time.DayOfWeek
import java.time.LocalDate

data class Forecast (
    val date: LocalDate,
    val avgHumidity: Double,
    val avgTemp: Double,
    val icon: String,
    val text: String,
    val details: ForecastDetails
)

package com.chudofishe.weatherapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate


@Parcelize
data class ForecastDetails (
    val sunrise: String,
    val sunset: String,
    val date: LocalDate,
    val avgHumidity: Double,
    val avgTemp: Double,
    val icon: String,
    val text: String,
    val dailyChanceOfRain: Int,
    val maxTemp: Double,
    val maxWind: Double,
    val minTemp: Double,
    val uv: Double,
    val hourForecastList: List<HourForecast>
    ) : Parcelable
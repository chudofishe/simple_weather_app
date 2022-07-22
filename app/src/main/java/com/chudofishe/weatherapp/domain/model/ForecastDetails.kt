package com.chudofishe.weatherapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ForecastDetails (
    val sunrise: String,
    val sunset: String,
    val dateEpoch: Int,
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
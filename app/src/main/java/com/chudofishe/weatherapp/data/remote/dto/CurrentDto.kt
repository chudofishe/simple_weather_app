package com.chudofishe.weatherapp.data.remote.dto


import com.chudofishe.weatherapp.domain.model.CurrentWeather
import com.google.gson.annotations.SerializedName

data class CurrentDto(
    val cloud: Int,
    val condition: ConditionDto,
    @SerializedName("feelslike_c")
    val feelslikeC: Double,
    @SerializedName("feelslike_f")
    val feelslikeF: Double,
    val humidity: Int,
    @SerializedName("is_day")
    val isDay: Int,
    @SerializedName("temp_c")
    val tempC: Double,
    val uv: Double,
    @SerializedName("wind_degree")
    val windDegree: Int,
    @SerializedName("wind_kph")
    val windKph: Double
)

fun CurrentDto.toCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        cloud = cloud,
        conditionIcon = "https://" + condition.icon.removePrefix("//"),
        conditionText = condition.text,
        feelsLike = feelslikeC,
        humidity = humidity,
        temp = tempC.toInt(),
        uv = uv,
        windKph = windKph
    )
}
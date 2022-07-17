package com.chudofishe.weatherapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class HourDto(
    @SerializedName("chance_of_rain")
    val chanceOfRain: Int,
    @SerializedName("chance_of_snow")
    val chanceOfSnow: Int,
    val cloud: Int,
    val condition: ConditionDtoX,
    @SerializedName("feelslike_c")
    val feelslikeC: Double,
    @SerializedName("heatindex_c")
    val heatindexC: Double,
    val humidity: Int,
    @SerializedName("is_day")
    val isDay: Int,
    @SerializedName("temp_c")
    val tempC: Double,
    val time: String,
    @SerializedName("time_epoch")
    val timeEpoch: Int,
    val uv: Double,
    @SerializedName("wind_degree")
    val windDegree: Int,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("windchill_c")
    val windchillC: Double
)
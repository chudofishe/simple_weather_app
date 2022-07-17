package com.chudofishe.weatherapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class DayDto(
    val avghumidity: Double,
    @SerializedName("avgtemp_c")
    val avgtempC: Double,
    val condition: ConditionDtoX,
    @SerializedName("daily_chance_of_rain")
    val dailyChanceOfRain: Int,
    @SerializedName("daily_chance_of_snow")
    val dailyChanceOfSnow: Int,
    @SerializedName("maxtemp_c")
    val maxtempC: Double,
    @SerializedName("maxwind_kph")
    val maxwindKph: Double,
    @SerializedName("mintemp_c")
    val mintempC: Double,
    val uv: Double
)
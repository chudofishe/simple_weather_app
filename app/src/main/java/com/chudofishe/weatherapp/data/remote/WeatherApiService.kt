package com.chudofishe.weatherapp.data.remote

import com.chudofishe.weatherapp.data.remote.dto.WeatherObjectDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast.json")
    suspend fun getCurrentWeatherByLocation(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int = 7
    ): WeatherObjectDto
}
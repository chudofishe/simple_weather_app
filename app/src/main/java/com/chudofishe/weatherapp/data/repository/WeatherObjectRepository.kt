package com.chudofishe.weatherapp.data.repository

import com.chudofishe.weatherapp.BuildConfig
import com.chudofishe.weatherapp.data.remote.WeatherApiService
import com.chudofishe.weatherapp.data.remote.dto.WeatherObjectDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherObjectRepository @Inject constructor(
    private val weatherApiService: WeatherApiService
) {
    suspend fun getCurrentWeather(location: String): WeatherObjectDto {
        return withContext(Dispatchers.IO) {
            return@withContext  weatherApiService.getCurrentWeatherByLocation(BuildConfig.WEATHER_API_KEY, location)
        }
    }
}
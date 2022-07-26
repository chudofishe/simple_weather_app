package com.chudofishe.weatherapp.data.repository

import com.chudofishe.weatherapp.BuildConfig
import com.chudofishe.weatherapp.data.remote.WeatherApiService
import com.chudofishe.weatherapp.data.remote.dto.CurrentDto
import com.chudofishe.weatherapp.data.remote.dto.ForecastdayDto
import com.chudofishe.weatherapp.data.remote.dto.WeatherObjectDto
import com.chudofishe.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherApiService: WeatherApiService) : WeatherRepository {

    override suspend fun getCurrentWeather(location: String): CurrentDto {
        return withContext(Dispatchers.IO) {
            return@withContext weatherApiService.getCurrentWeatherByLocation(BuildConfig.WEATHER_API_KEY, location).current
        }
    }

    override suspend fun getForecastList(location: String): List<ForecastdayDto> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherApiService.getCurrentWeatherByLocation(BuildConfig.WEATHER_API_KEY, location).forecast.forecastday
        }
    }
}
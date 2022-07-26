package com.chudofishe.weatherapp.domain.repository

import com.chudofishe.weatherapp.data.remote.dto.CurrentDto
import com.chudofishe.weatherapp.data.remote.dto.ForecastdayDto

interface WeatherRepository {

    suspend fun getCurrentWeather(location: String): CurrentDto

    suspend fun getForecastList(location: String): List<ForecastdayDto>
}
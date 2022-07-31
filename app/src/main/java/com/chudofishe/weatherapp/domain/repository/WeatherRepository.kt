package com.chudofishe.weatherapp.domain.repository

import com.chudofishe.weatherapp.data.remote.dto.CurrentDto
import com.chudofishe.weatherapp.data.remote.dto.ForecastdayDto
import com.chudofishe.weatherapp.data.remote.dto.LocationXCurrentDto

interface WeatherRepository {

    suspend fun getCurrentWeather(location: String): LocationXCurrentDto

    suspend fun getForecastList(location: String): List<ForecastdayDto>
}
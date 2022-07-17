package com.chudofishe.weatherapp.data.remote.dto

data class WeatherObjectDto(
    val current: CurrentDto,
    val forecast: ForecastDto,
    val location: LocationDto
)
package com.chudofishe.weatherapp.data.remote.dto

import com.chudofishe.weatherapp.common.Util
import com.chudofishe.weatherapp.domain.model.CurrentWeather

data class LocationXCurrentDto(
    val location: LocationDto,
    val current: CurrentDto
)


fun LocationXCurrentDto.toCurrentWeather(): CurrentWeather {
    val iconUrl = Util.getIconUrl(current.condition.icon)
    return CurrentWeather(
        cloud = current.cloud,
        conditionIcon = iconUrl,
        conditionText = current.condition.text,
        feelsLike = current.feelslikeC,
        humidity = current.humidity,
        temp = current.tempC.toInt(),
        uv = current.uv,
        windKph = current.windKph,
        city = location.name,
        country = location.country
    )
}

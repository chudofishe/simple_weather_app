package com.chudofishe.weatherapp.data.remote.dto


import com.chudofishe.weatherapp.domain.model.Forecast
import com.chudofishe.weatherapp.domain.model.ForecastDetails
import com.chudofishe.weatherapp.domain.model.HourForecast
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.LocalTime

data class ForecastdayDto(
    val astro: AstroDto,
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Int,
    val day: DayDto,
    val hour: List<HourDto>
)

fun ForecastdayDto.toForecast(): Forecast {
    return Forecast(
        dateEpoch = dateEpoch,
        avgHumidity = day.avghumidity,
        avgTemp = day.avgtempC,
        icon = "https://" + day.condition.icon.removePrefix("//"),
        text = day.condition.text,
        details = ForecastDetails(
            sunrise = astro.sunrise,
            sunset = astro.sunset,
            dateEpoch = dateEpoch,
            avgHumidity = day.avghumidity,
            avgTemp = day.avgtempC,
            icon = day.condition.icon,
            text = day.condition.text,
            dailyChanceOfRain = day.dailyChanceOfRain,
            maxTemp = day.maxtempC,
            maxWind = day.maxwindKph,
            minTemp = day.mintempC,
            uv = day.uv,
            hourForecastList = hour.map { dto -> HourForecast(
                time = dto.time.takeLast(4),
                temp = dto.tempC.toInt(),
                icon = "https://" + dto.condition.icon.removePrefix("//")
            )}
        )
    )
}
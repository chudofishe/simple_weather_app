package com.chudofishe.weatherapp.data.remote.dto


import com.chudofishe.weatherapp.common.Util
import com.chudofishe.weatherapp.domain.model.Forecast
import com.chudofishe.weatherapp.domain.model.ForecastDetails
import com.chudofishe.weatherapp.domain.model.HourForecast
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class ForecastdayDto(
    val astro: AstroDto,
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Int,
    val day: DayDto,
    val hour: List<HourDto>
)

fun ForecastdayDto.toForecast(): Forecast {
    val iconUrl = Util.getIconUrl(day.condition.icon)
    val date = LocalDate.parse(this.date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    return Forecast(
        date = date,
        avgHumidity = day.avghumidity,
        avgTemp = day.avgtempC,
        icon = iconUrl,
        text = day.condition.text,
        details = ForecastDetails(
            sunrise = astro.sunrise,
            sunset = astro.sunset,
            date = date,
            avgHumidity = day.avghumidity,
            avgTemp = day.avgtempC,
            icon = iconUrl,
            text = day.condition.text,
            dailyChanceOfRain = day.dailyChanceOfRain,
            maxTemp = day.maxtempC,
            maxWind = day.maxwindKph,
            minTemp = day.mintempC,
            uv = day.uv,
            hourForecastList = hour.map { dto -> HourForecast(
                time = dto.time.takeLastWhile { it != ' ' },
                temp = dto.tempC.toInt(),
                icon = Util.getIconUrl(dto.condition.icon)
            )}
        )
    )
}
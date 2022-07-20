package com.chudofishe.weatherapp.data.remote.dto


import com.chudofishe.weatherapp.domain.model.Forecast
import com.chudofishe.weatherapp.domain.model.ForecastDetails
import com.google.gson.annotations.SerializedName

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
            uv = day.uv
        )
    )
}
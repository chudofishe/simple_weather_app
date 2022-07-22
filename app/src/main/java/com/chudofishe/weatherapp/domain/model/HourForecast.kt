package com.chudofishe.weatherapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
data class HourForecast(
    val time: String,
    val temp: Int,
    val icon: String
) : Parcelable

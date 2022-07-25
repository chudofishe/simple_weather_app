package com.chudofishe.weatherapp.common

object Util {

    fun getIconUrl(path: String): String = "https://" + path.removePrefix("//")
}
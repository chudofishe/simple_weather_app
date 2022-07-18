package com.chudofishe.weatherapp.common

fun Double.toIntString() = this.toInt().toString()

fun Double.toPercent() = "${(this * 100).toIntString()}%"
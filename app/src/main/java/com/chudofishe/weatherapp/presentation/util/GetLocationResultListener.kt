package com.chudofishe.weatherapp.presentation.util

import android.location.Location

interface GetLocationResultListener {
    fun onGetLocationSucceed(location: Location)
    fun onGetLocationFailed()
}
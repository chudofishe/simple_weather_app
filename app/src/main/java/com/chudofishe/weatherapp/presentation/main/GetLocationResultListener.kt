package com.chudofishe.weatherapp.presentation.main

import android.location.Location

interface GetLocationResultListener {
    fun onGetLocationSucceed(location: Location)
    fun onGetLocationFailed()
}
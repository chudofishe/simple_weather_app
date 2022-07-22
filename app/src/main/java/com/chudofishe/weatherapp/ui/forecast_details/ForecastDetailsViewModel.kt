package com.chudofishe.weatherapp.ui.forecast_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.chudofishe.weatherapp.domain.model.ForecastDetails
import kotlinx.coroutines.flow.MutableStateFlow
import com.chudofishe.weatherapp.common.Result
import kotlinx.coroutines.flow.StateFlow

class ForecastDetailsViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _forecastDetails = MutableStateFlow(getStateValue())
    val forecastDetails: StateFlow<Result<ForecastDetails>>
        get() = _forecastDetails

    private fun getStateValue(): Result<ForecastDetails> {
        val details: ForecastDetails? = savedStateHandle[KEY_DETAILS]
        return if (details == null) Result.Error("Couldn't get forecast details") else Result.Success(details)
    }

    companion object {
        const val KEY_DETAILS = "details"
    }
}
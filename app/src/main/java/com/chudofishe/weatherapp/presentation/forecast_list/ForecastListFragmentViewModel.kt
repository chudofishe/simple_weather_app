package com.chudofishe.weatherapp.presentation.forecast_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chudofishe.weatherapp.common.Result
import com.chudofishe.weatherapp.domain.model.CurrentWeather
import com.chudofishe.weatherapp.domain.model.Forecast
import com.chudofishe.weatherapp.domain.use_case.GetForecastListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForecastListFragmentViewModel
@Inject constructor(private val getForecastListUseCase: GetForecastListUseCase) : ViewModel() {
    private var _forecastListState: MutableStateFlow<Result<List<Forecast>>> = MutableStateFlow(Result.Loading())
    val forecastListState: StateFlow<Result<List<Forecast>>>
        get() = _forecastListState

    fun getCurrentWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            getForecastListUseCase(latitude, longitude).collect {
                _forecastListState.value = it
            }
        }
    }

    fun getCurrentWeather(city: String) {
        viewModelScope.launch {
            getForecastListUseCase(city).collect {
                _forecastListState.value = it
            }
        }
    }
}
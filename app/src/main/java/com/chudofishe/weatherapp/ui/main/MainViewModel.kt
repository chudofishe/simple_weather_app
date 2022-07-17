package com.chudofishe.weatherapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chudofishe.weatherapp.common.Result
import com.chudofishe.weatherapp.data.remote.dto.toCurrentWeather
import com.chudofishe.weatherapp.data.remote.dto.toForecast
import com.chudofishe.weatherapp.data.repository.WeatherObjectRepository
import com.chudofishe.weatherapp.domain.model.CurrentWeather
import com.chudofishe.weatherapp.domain.model.Forecast
import com.chudofishe.weatherapp.domain.use_case.GetWeatherObjectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val weatherObjectRepository: WeatherObjectRepository) : ViewModel() {

    private val _currentWeatherState: MutableStateFlow<Result<CurrentWeather>> = MutableStateFlow(Result.Loading())
    val currentWeatherState: StateFlow<Result<CurrentWeather>>
        get() = _currentWeatherState

    private val _forecastListState: MutableStateFlow<Result<List<Forecast>>> = MutableStateFlow(Result.Loading())
    val forecastState: StateFlow<Result<List<Forecast>>>
        get() = _forecastListState

    init {
        viewModelScope.launch {
            GetWeatherObjectUseCase(weatherObjectRepository).invoke().collect { response ->
                when (response) {
                    is Result.Loading -> {
                        _currentWeatherState.value = Result.Loading()
                        _forecastListState.value = Result.Loading()
                    }
                    is Result.Success -> {
                        _currentWeatherState.value = if (response.data == null) {
                            Result.Error(response.message ?: "An unxpected error occurred")
                        } else {
                            Result.Success(response.data.current.toCurrentWeather())
                        }
                        _forecastListState.value = if (response.data == null) {
                            Result.Error(response.message ?: "An unxpected error occurred")
                        } else {
                            Result.Success(response.data.forecast.forecastday.map { it.toForecast() })
                        }
                    }
                    else -> {
                        _currentWeatherState.value = Result.Error(response.message ?: "An unxpected error occurred")
                        _forecastListState.value = Result.Error(response.message ?: "An unxpected error occurred")
                    }
                }
            }
        }
    }



}
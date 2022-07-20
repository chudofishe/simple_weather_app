package com.chudofishe.weatherapp.ui.current_weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chudofishe.weatherapp.common.Result
import com.chudofishe.weatherapp.data.remote.dto.toCurrentWeather
import com.chudofishe.weatherapp.data.remote.dto.toForecast
import com.chudofishe.weatherapp.data.repository.WeatherObjectRepository
import com.chudofishe.weatherapp.domain.model.CurrentWeather
import com.chudofishe.weatherapp.domain.use_case.GetWeatherObjectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(private val weatherObjectRepository: WeatherObjectRepository) : ViewModel() {

    private val _currentWeatherState: MutableStateFlow<Result<CurrentWeather>> = MutableStateFlow(Result.Loading())
    val currentWeatherState: StateFlow<Result<CurrentWeather>>
        get() = _currentWeatherState

    init {
        viewModelScope.launch {
            GetWeatherObjectUseCase(weatherObjectRepository).invoke().collect { response ->
                when (response) {
                    is Result.Loading -> {
                        _currentWeatherState.value = Result.Loading()
                    }
                    is Result.Success -> {
                        _currentWeatherState.value = if (response.data == null) {
                            Result.Error(response.message ?: "An unxpected error occurred")
                        } else {
                            Result.Success(response.data.current.toCurrentWeather())
                        }
                    }
                    else -> {
                        _currentWeatherState.value = Result.Error(response.message ?: "An unxpected error occurred")
                    }
                }
            }
        }
    }
}
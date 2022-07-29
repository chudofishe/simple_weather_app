package com.chudofishe.weatherapp.presentation.current_weather

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chudofishe.weatherapp.common.Constants
import com.chudofishe.weatherapp.common.Result
import com.chudofishe.weatherapp.domain.model.CurrentWeather
import com.chudofishe.weatherapp.domain.use_case.GetCurrentWeatherUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel
@Inject constructor(private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase) : ViewModel() {

    private var _currentWeatherState: MutableStateFlow<Result<CurrentWeather>> = MutableStateFlow(Result.Loading())
    val currentWeatherState: StateFlow<Result<CurrentWeather>>
        get() = _currentWeatherState

    fun getCurrentWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            getCurrentWeatherUseCase(latitude, longitude).collect {
                _currentWeatherState.value = it
            }
        }
    }

    fun getCurrentWeather(city: String) {
        viewModelScope.launch {
            getCurrentWeatherUseCase(city).collect {
                _currentWeatherState.value = it
            }
        }
    }

}
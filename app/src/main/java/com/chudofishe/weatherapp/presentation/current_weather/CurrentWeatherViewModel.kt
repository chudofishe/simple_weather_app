package com.chudofishe.weatherapp.presentation.current_weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chudofishe.weatherapp.common.Result
import com.chudofishe.weatherapp.domain.model.CurrentWeather
import com.chudofishe.weatherapp.domain.use_case.GetCurrentWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(getCurrentWeatherUseCase: GetCurrentWeatherUseCase) : ViewModel() {

    val currentWeatherState: StateFlow<Result<CurrentWeather>> = getCurrentWeatherUseCase().stateIn(
        viewModelScope, SharingStarted.Eagerly, Result.Loading())
}
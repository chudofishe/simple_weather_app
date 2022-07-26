package com.chudofishe.weatherapp.ui.forecast_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chudofishe.weatherapp.common.Result
import com.chudofishe.weatherapp.domain.model.Forecast
import com.chudofishe.weatherapp.domain.use_case.GetForecastListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ForecastListFragmentViewModel @Inject constructor(getForecastListUseCase: GetForecastListUseCase) : ViewModel() {
    val forecastListState: StateFlow<Result<List<Forecast>>> = getForecastListUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, Result.Loading())
}
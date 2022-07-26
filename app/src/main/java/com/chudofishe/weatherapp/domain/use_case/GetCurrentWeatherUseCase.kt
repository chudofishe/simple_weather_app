package com.chudofishe.weatherapp.domain.use_case

import com.chudofishe.weatherapp.domain.model.CurrentWeather
import com.chudofishe.weatherapp.common.Result
import com.chudofishe.weatherapp.data.remote.dto.toCurrentWeather
import com.chudofishe.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    operator fun invoke(location: String = "Podgorica"): Flow<Result<CurrentWeather>> = flow {
        try {
            emit(Result.Loading())
            val currentWeather = weatherRepository.getCurrentWeather(location).toCurrentWeather()
            emit(Result.Success(currentWeather))
        } catch(e: HttpException) {
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Result.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
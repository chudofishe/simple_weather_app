package com.chudofishe.weatherapp.domain.use_case

import com.chudofishe.weatherapp.common.Result
import com.chudofishe.weatherapp.data.remote.dto.toForecast
import com.chudofishe.weatherapp.domain.model.Forecast
import com.chudofishe.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetForecastListUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(city: String = "Podgorica"): Flow<Result<List<Forecast>>> {
        return makeRequest(city)
    }

    operator fun invoke(latitude: Double, longitude: Double): Flow<Result<List<Forecast>>>  {
        return makeRequest("${latitude.toFloat()},${longitude.toFloat()}")
    }

    private fun makeRequest(location: String): Flow<Result<List<Forecast>>> = flow {
        try {
            emit(Result.Loading())
            val forecastList = weatherRepository.getForecastList(location).map { it.toForecast() }
            emit(Result.Success(forecastList))
        } catch(e: HttpException) {
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Result.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
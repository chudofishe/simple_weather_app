package com.chudofishe.weatherapp.domain.use_case

import com.chudofishe.weatherapp.common.Result
import com.chudofishe.weatherapp.data.remote.dto.WeatherObjectDto
import com.chudofishe.weatherapp.data.repository.WeatherObjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetWeatherObjectUseCase @Inject constructor(
    private val weatherObjectRepository: WeatherObjectRepository
) {
    operator fun invoke(location: String = "Podgorica"): Flow<Result<WeatherObjectDto>> = flow {
        try {
            emit(Result.Loading())
            val weatherObject = weatherObjectRepository.getCurrentWeather(location)
            emit(Result.Success(weatherObject))
        } catch(e: HttpException) {
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Result.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
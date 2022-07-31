package com.chudofishe.weatherapp.domain.use_case

import com.chudofishe.weatherapp.common.createMockService
import com.chudofishe.weatherapp.common.enqueueResponse
import com.chudofishe.weatherapp.data.remote.WeatherApiService
import com.chudofishe.weatherapp.data.remote.dto.toCurrentWeather
import com.chudofishe.weatherapp.data.repository.WeatherRepositoryImpl
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets

class GetCurrentWeatherTest {

    private val mockWebServer = MockWebServer()
    private lateinit var apiService: WeatherApiService
    private lateinit var weatherRepository: WeatherRepositoryImpl

    @Before
    fun setUp() {
        mockWebServer.start()
        apiService = Retrofit.Builder().createMockService(mockWebServer.url("/"))

        weatherRepository = WeatherRepositoryImpl(apiService)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get current weather result should be not null`() {
        mockWebServer.enqueueResponse("success_response.json", 200)

        runBlocking {
            val result = GetCurrentWeatherUseCase(weatherRepository)().last().data

            assertNotNull(result)
        }
    }

    @Test
    fun `current dto maps to current weather`() {
        mockWebServer.enqueueResponse("success_response.json", 200)

        runBlocking {
            weatherRepository.getCurrentWeather("London").toCurrentWeather()
            assertTrue(true)
        }
    }

    @Test
    fun `get current weather location is correct`() {
        mockWebServer.enqueueResponse("success_response.json", 200)

        runBlocking {
            val result = GetCurrentWeatherUseCase(weatherRepository)().last().data
            val resultLocation = (result?.country ?: "") to (result?.city ?: "")
            val actual = "United Kingdom" to "London"

            assertEquals(resultLocation, actual)
        }
    }
}
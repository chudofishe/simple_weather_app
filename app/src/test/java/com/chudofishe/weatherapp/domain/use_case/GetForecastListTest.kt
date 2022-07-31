package com.chudofishe.weatherapp.domain.use_case

import com.chudofishe.weatherapp.common.createMockService
import com.chudofishe.weatherapp.common.enqueueResponse
import com.chudofishe.weatherapp.data.remote.WeatherApiService
import com.chudofishe.weatherapp.data.repository.WeatherRepositoryImpl
import com.chudofishe.weatherapp.domain.model.Forecast
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class GetForecastListTest {

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
    fun `get forecast list not empty`() {
        mockWebServer.enqueueResponse("success_response.json", 200)

        runBlocking {
            val result = GetForecastListUseCase(weatherRepository)().last().data

            assertNotNull(result)
            assertTrue(result?.isNotEmpty() ?: false)
        }
    }

    @Test
    fun `get forecast list type is correct`() {
        mockWebServer.enqueueResponse("success_response.json", 200)

        runBlocking {
            val result = GetForecastListUseCase(weatherRepository)().last().data

            assertTrue(result is List<Forecast>)
        }
    }

}
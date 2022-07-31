package com.chudofishe.weatherapp.data.remote

import com.chudofishe.weatherapp.BuildConfig
import com.chudofishe.weatherapp.common.createMockService
import com.chudofishe.weatherapp.common.enqueueResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit

class WeatherApiResponseTest {

    private val mockWebServer = MockWebServer()
    private lateinit var apiService: WeatherApiService

    @Before
    fun setUp() {
        mockWebServer.start()
        apiService = Retrofit.Builder().createMockService(mockWebServer.url("/"))
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch weather object not null given 200 response`() {
        mockWebServer.enqueueResponse("success_response.json", 200)

        runBlocking {
            val result = apiService.getCurrentWeatherByLocation(BuildConfig.WEATHER_API_KEY, "London")
            assertNotNull(result)
        }
    }

    @Test
    fun `should fail given not 200 response`() {
        mockWebServer.enqueueResponse("success_response.json", 403)

        runBlocking {
            var failed = false
            try {
                apiService.getCurrentWeatherByLocation(BuildConfig.WEATHER_API_KEY, "London")
            } catch (ex: HttpException) {
                failed = true
            }
            assertTrue(failed)
        }
    }
}
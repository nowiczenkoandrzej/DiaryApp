package com.an.diaryapp.feature_weather_api.data

import com.an.diaryapp.feature_weather_api.WeatherResponse
import com.an.diaryapp.feature_weather_api.domain.WeatherService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class WeatherServiceImpl @Inject constructor(
    private val client: HttpClient
): WeatherService {

    private companion object {
        val BASE_URL = "https://api.open-meteo.com/"
    }
    override suspend fun getWeather(latitude: Double, longitude: Double): WeatherResponse? {
        return client.get("v1/forecast?current=temperature_2m,weather_code&latitude=${latitude}&longitude=${longitude}").body()
    }
}
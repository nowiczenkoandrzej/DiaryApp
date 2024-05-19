package com.an.diaryapp.feature_weather_api.domain

import com.an.diaryapp.feature_weather_api.WeatherResponse

interface WeatherService {

    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
    ): WeatherResponse?

}
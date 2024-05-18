package com.an.diaryapp.feature_weather_api

interface WeatherService {

    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
    ): KtorResponse?

}
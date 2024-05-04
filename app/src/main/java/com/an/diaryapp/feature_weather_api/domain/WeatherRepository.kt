package com.an.diaryapp.feature_weather_api.domain

import com.an.diaryapp.core.domain.model.Resource
import com.an.diaryapp.core.domain.model.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherInfo(
        lat: Double,
        long: Double
    ): Resource<WeatherInfo>
}
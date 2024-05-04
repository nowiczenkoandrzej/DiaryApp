package com.an.diaryapp.feature_weather_api.domain

import com.squareup.moshi.Json

data class CurrentWeather(
    @field:Json(name = "temperature_2m")
    val temperature: Double,
    @field:Json(name = "weather_code")
    val weatherCode: Int
)

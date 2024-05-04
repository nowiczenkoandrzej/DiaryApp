package com.an.diaryapp.feature_weather_api.domain

import com.squareup.moshi.Json

data class WeatherResponse(
    @field:Json(name = "current")
    val currentWeather: CurrentWeather,
)

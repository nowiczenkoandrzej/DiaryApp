package com.an.diaryapp.feature_add_note.data.remote

import com.squareup.moshi.Json

data class CurrentWeather(
    @field:Json(name = "temperature_2m")
    val temperature: Double,
    @field:Json(name = "weather_code")
    val weatherCode: Int
)

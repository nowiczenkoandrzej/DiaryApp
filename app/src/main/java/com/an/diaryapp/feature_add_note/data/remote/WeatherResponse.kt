package com.an.diaryapp.feature_add_note.data.remote

import com.squareup.moshi.Json

data class WeatherResponse(
    @field:Json(name = "current")
    val currentWeather: CurrentWeather,
)

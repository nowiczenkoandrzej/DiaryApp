package com.an.diaryapp.feature_weather_api

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val current: Current
) {
    @Serializable
    data class Current(
        val temperature_2m: Double,
        val weather_code: Long
    )
}

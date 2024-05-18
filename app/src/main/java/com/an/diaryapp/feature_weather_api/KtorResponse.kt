package com.an.diaryapp.feature_weather_api

import com.an.diaryapp.feature_weather_api.data.WeatherSerializer
import kotlinx.serialization.Serializable

@Serializable
data class KtorResponse(
    val current: Current
) {
    @Serializable
    data class Current(
        val temperature_2m: Double,
        val weather_code: Long
    )
}

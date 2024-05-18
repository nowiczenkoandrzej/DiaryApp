package com.an.diaryapp.feature_weather_api

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class WeatherDataResponse(
    @SerializedName("current")
    val currentWeather: CurrentWeatherResponse
)

data class CurrentWeatherResponse(
    @SerializedName("temperature_2m")
    val temperature: Double,
    @SerializedName("weather_code")
    val weatherCode: Int
)

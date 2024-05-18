package com.an.diaryapp.feature_weather_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("current")
    val currentWeather: CurrentWeather,
)

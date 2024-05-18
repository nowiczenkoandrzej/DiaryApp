package com.an.diaryapp.feature_weather_api

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("current.temperature_2m")
    val temperature: Float,
    @SerializedName("current.weather_code")
    val weatherCode: Int,

)
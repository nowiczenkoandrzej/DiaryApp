package com.an.diaryapp.feature_weather_api.data

import com.an.diaryapp.feature_weather_api.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast?current=temperature_2m,weather_code")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): WeatherResponse

}
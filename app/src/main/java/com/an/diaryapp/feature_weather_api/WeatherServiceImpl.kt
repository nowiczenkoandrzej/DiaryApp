package com.an.diaryapp.feature_weather_api

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

class WeatherServiceImpl(
    private val client: HttpClient
): WeatherService {

    private companion object {
        val BASE_URL = "https://api.open-meteo.com/"
    }
    override suspend fun getWeather(latitude: Double, longitude: Double): KtorResponse? {
        return client.get("v1/forecast?current=temperature_2m,weather_code&latitude=${latitude}&longitude=${longitude}").body()


        /*try {

            client.get {
                url("${BASE_URL}v1/forecast?current=temperature_2m,weather_code&latitude=${latitude}&longitude=${longitude}")
                Log.d("TAG", "getWeather: inside call")
            }

        } catch (e: Exception) {
            Log.d("TAG", "getWeather: exception")
            e.printStackTrace()
            null
        }*/
    }
}
package com.an.diaryapp.feature_weather_api.data

import android.util.Log
import com.an.diaryapp.core.data.toWeatherInfo
import com.an.diaryapp.core.domain.model.Resource
import com.an.diaryapp.core.domain.model.WeatherInfo
import com.an.diaryapp.feature_weather_api.domain.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
): WeatherRepository {
    override suspend fun getWeatherInfo(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {

            Log.d("TAG", "getWeatherInfo: przed zapytaniem")
            val weather = weatherApi.getWeatherData(lat, long)
            Log.d("TAG", "getWeatherInfo: po zapytaniu -> $weather")

            Resource.Success(
                data = weather.toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()

            Resource.Error(e.message.toString())
        }
    }
}
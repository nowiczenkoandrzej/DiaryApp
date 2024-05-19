package com.an.diaryapp.feature_weather_api.data

import com.an.diaryapp.core.domain.model.Resource
import com.an.diaryapp.core.domain.model.WeatherInfo
import com.an.diaryapp.feature_weather_api.domain.WeatherService
import com.an.diaryapp.feature_weather_api.domain.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val service: WeatherService
): WeatherRepository {


    override suspend fun getWeatherInfo(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {

            val result = service.getWeather(lat, long)

            Resource.Success(
                data = WeatherInfo(
                    temperature = result!!.current.temperature_2m,
                    weatherType = result.current.weather_code
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()

            Resource.Error(e.message.toString())
        }
    }
}
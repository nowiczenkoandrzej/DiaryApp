package com.an.diaryapp.feature_weather_api.data

import android.util.Log
import com.an.diaryapp.core.domain.model.Resource
import com.an.diaryapp.core.domain.model.WeatherInfo
import com.an.diaryapp.feature_weather_api.WeatherServiceImpl
import com.an.diaryapp.feature_weather_api.domain.WeatherRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
): WeatherRepository {


    /*val service1 = WeatherServiceImpl(
        client = HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    )*/

    val service = WeatherServiceImpl(
        client = HttpClient(Android) {
            defaultRequest {
                url("https://api.open-meteo.com/")
            }

            install(ContentNegotiation) {
                json(Json{
                    ignoreUnknownKeys = true
                })
            }
        }
    )

    override suspend fun getWeatherInfo(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {

            /*Log.d("TAG", "getWeatherInfo: przed zapytaniem")
            val weather = weatherApi.getWeatherData(lat, long)
            Log.d("TAG", "getWeatherInfo: po zapytaniu -> $weather")*/

            val result = service.getWeather(lat, long)

            /*val result = WeatherInfo(
               // temperature = weather.temperature.toDouble(),
               // weatherType = weather.weatherCode.toLong()
            )
*/



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
package com.an.diaryapp.di

import com.an.diaryapp.feature_weather_api.data.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Provides
    @Singleton
    fun provideWeatherApi(
        converterFactory: MoshiConverterFactory
    ): WeatherApi = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(converterFactory)
            .build()
            .create()


}
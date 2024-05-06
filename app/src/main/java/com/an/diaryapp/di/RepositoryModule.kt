package com.an.diaryapp.di

import com.an.diaryapp.core.data.NotesRepositoryImpl
import com.an.diaryapp.feature_notification.data.NotificationPreferencesRepositoryImpl

import com.an.diaryapp.core.domain.repository.NotesRepository
import com.an.diaryapp.feature_location.data.LocationPreferencesRepositoryImpl
import com.an.diaryapp.feature_notification.domain.NotificationPreferencesRepository
import com.an.diaryapp.feature_location.data.LocationRepositoryImpl
import com.an.diaryapp.feature_location.domain.LocationPreferencesRepository
import com.an.diaryapp.feature_location.domain.LocationRepository
import com.an.diaryapp.feature_weather_api.data.WeatherRepositoryImpl
import com.an.diaryapp.feature_weather_api.domain.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindNoteRepository(
        repository: NotesRepositoryImpl
    ): NotesRepository

    @Binds
    @Singleton
    abstract fun bindNotificationPreferencesRepository(
        repository: NotificationPreferencesRepositoryImpl
    ): NotificationPreferencesRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        repository: LocationRepositoryImpl
    ): LocationRepository

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        repository: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindLocationPreferencesRepository(
        repository: LocationPreferencesRepositoryImpl
    ): LocationPreferencesRepository

}
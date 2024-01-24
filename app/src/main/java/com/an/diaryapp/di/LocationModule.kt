package com.an.diaryapp.di

import android.app.Application
import android.location.Geocoder
import com.an.diaryapp.feature_add_note.data.location.DefaultLocationTracker
import com.an.diaryapp.feature_add_note.domain.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        app: Application
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(app)

    @Singleton
    @Provides
    fun provideGeocoder(
        app: Application
    ): Geocoder = Geocoder(app)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationTrackerModule {


    @Binds
    @Singleton
    abstract fun bindLocationTracker(
        defaultLocationTracker: DefaultLocationTracker
    ): LocationTracker

}
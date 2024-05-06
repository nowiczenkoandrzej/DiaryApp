package com.an.diaryapp.feature_location.domain

import android.location.Location

interface LocationPreferencesRepository {

    suspend fun getDefaultLocation(): Location
    suspend fun saveDefaultLocation(location: Location)

    suspend fun getDefaultLocationName(): String
    suspend fun setDefaultLocationName(name: String)

    suspend fun getIsDefaultLocationPicked(): Boolean
    suspend fun setIsDefaultLocationPicked(isPicked: Boolean)

}
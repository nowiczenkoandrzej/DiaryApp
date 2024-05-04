package com.an.diaryapp.feature_location.domain

import android.location.Location

interface LocationRepository {

    suspend fun getLocation(): Location?

    suspend fun getCityNameFromLocation(
        location: Location
    ): String?

}
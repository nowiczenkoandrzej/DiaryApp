package com.an.diaryapp.feature_location.domain

import android.location.Location

interface LocationTracker {

    suspend fun getCurrentLocation(): Location?

}
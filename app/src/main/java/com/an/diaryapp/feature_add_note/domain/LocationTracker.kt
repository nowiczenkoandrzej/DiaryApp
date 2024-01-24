package com.an.diaryapp.feature_add_note.domain

import android.location.Location

interface LocationTracker {

    suspend fun getCurrentLocation(): Location?

}
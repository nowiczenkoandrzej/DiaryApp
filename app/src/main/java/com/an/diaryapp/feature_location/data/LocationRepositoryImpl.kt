package com.an.diaryapp.feature_location.data

import android.location.Geocoder
import android.location.Location
import com.an.diaryapp.feature_location.domain.LocationRepository
import com.an.diaryapp.feature_location.domain.LocationTracker
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationTracker: LocationTracker,
    private val geocoder: Geocoder
): LocationRepository {
    override suspend fun getLocation(): Location? {
        return locationTracker.getCurrentLocation()
    }

    override suspend fun getCityNameFromLocation(location: Location): String? {
        val address = geocoder.getFromLocation(
            location.latitude,
            location.longitude,
            1,
        )

        val locationCity = address?.let { adresses ->
            address[0].locality
        }

        return locationCity
    }
}
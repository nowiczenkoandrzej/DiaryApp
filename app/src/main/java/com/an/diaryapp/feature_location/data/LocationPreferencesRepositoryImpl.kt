package com.an.diaryapp.feature_location.data

import android.location.Location
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.an.diaryapp.feature_location.domain.LocationPreferencesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LocationPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): LocationPreferencesRepository {

    companion object {
        private val isDefaultLocationPickedKey = booleanPreferencesKey("is_default_location_picked_key")
        private val defaultLocationLatitudeKey = doublePreferencesKey("default_location_latitude_key")
        private val defaultLocationLongitudeKey = doublePreferencesKey("default_location_longitude_key")
        private val defaultLocationNameKey = stringPreferencesKey("default_location_name_key")
    }


    override suspend fun getDefaultLocation(): Location {
        val preferences = dataStore.data.first()

        return try {
            val defaultLocation = Location("google_maps")
            defaultLocation.latitude = preferences[defaultLocationLatitudeKey] as Double
            defaultLocation.longitude = preferences[defaultLocationLongitudeKey] as Double

            defaultLocation
        } catch (e: Exception) {
            val defaultLocation = Location("google_maps")
            defaultLocation.latitude = 54.10
            defaultLocation.longitude = 22.90

            defaultLocation
        }
    }

    override suspend fun saveDefaultLocation(location: Location) {
        dataStore.edit { settings ->
            settings[defaultLocationLatitudeKey] = location.latitude
            settings[defaultLocationLongitudeKey] = location.longitude
        }
    }

    override suspend fun getDefaultLocationName(): String {
        val preferences = dataStore.data.first()

        return try {
            preferences[defaultLocationNameKey] as String
        } catch (e: Exception) {
            ""
        }
    }

    override suspend fun setDefaultLocationName(name: String) {
        dataStore.edit { settings ->
            settings[defaultLocationNameKey] = name
        }
    }

    override suspend fun getIsDefaultLocationPicked(): Boolean {
        val preferences = dataStore.data.first()

        return try {
            preferences[isDefaultLocationPickedKey] as Boolean
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun setIsDefaultLocationPicked(isPicked: Boolean) {
        dataStore.edit { settings ->
            settings[isDefaultLocationPickedKey] = isPicked
        }
    }
}
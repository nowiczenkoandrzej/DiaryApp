package com.an.diaryapp.core.data.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.an.diaryapp.core.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): UserPreferencesRepository{

    private val hasNoteBeenAddedKey = booleanPreferencesKey("has_note_been_added")
    override suspend fun saveIsNoteAdded(isAdded: Boolean) {
        dataStore.edit { settings ->
            settings[hasNoteBeenAddedKey] = isAdded
        }
    }

    override suspend fun getIsNoteAdded(): Boolean? {

        val preferences = dataStore.data.first()
        return try {
            preferences[hasNoteBeenAddedKey] as Boolean
        } catch (e: NullPointerException) {
            false
        }



    }
}
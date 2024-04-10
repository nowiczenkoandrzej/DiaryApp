package com.an.diaryapp.core.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

interface UserPreferencesRepository {

    suspend fun saveIsNoteAdded(isAdded: Boolean)
    suspend fun getIsNoteAdded(): Boolean?
}
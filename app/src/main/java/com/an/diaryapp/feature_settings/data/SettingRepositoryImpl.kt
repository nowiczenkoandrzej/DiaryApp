package com.an.diaryapp.feature_settings.data

import android.content.Context
import androidx.datastore.core.DataStore
import com.an.diaryapp.feature_settings.domain.SettingsRepository
import com.an.diaryapp.feature_settings.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<AppSettings>
): SettingsRepository {


    override suspend fun putAppSettings(settings: AppSettings) {
        dataStore.updateData { settings }
    }

    override fun getAppSettings(): Flow<AppSettings> {

        return dataStore.data
    }


}
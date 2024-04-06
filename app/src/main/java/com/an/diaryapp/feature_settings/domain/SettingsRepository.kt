package com.an.diaryapp.feature_settings.domain

import com.an.diaryapp.feature_settings.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun putAppSettings(settings: AppSettings)

    fun getAppSettings(): Flow<AppSettings>

}
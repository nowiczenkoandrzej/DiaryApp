package com.an.diaryapp.feature_notification.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.an.diaryapp.feature_notification.domain.NotificationPreferencesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class NotificationPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): NotificationPreferencesRepository {

    companion object {
        private val hasNoteBeenAddedKey = booleanPreferencesKey("has_note_been_added")
        private val alarmKey = intPreferencesKey("alarm_key")
        private val isNotificationScheduledKey = booleanPreferencesKey("is_notificationScheduled")
        private val scheduledTimeKey = stringPreferencesKey("scheduled_time")
    }
    override suspend fun setIsNoteAdded(isAdded: Boolean) {
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

    override suspend fun setAlarmId(id: Int) {
        dataStore.edit { settings ->
            settings[alarmKey] = id
        }
    }

    override suspend fun getAlarmId(): Int {
        val preferences = dataStore.data.first()
        return preferences[alarmKey] as Int
    }

    override suspend fun getNotificationTime(): String {
        val preferences = dataStore.data.first()

        return try {
            preferences[scheduledTimeKey]!!
        } catch (e: Exception) {
            ""
        }

    }

    override suspend fun setNotificationTime(time: String) {
        dataStore.edit { settings ->
            settings[scheduledTimeKey] = time
        }
    }

    override suspend fun getIsNotificationScheduled(): Boolean {
        val preferences = dataStore.data.first()

        return try {
            preferences[isNotificationScheduledKey]!!
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun setIsNotificationScheduled(isScheduled: Boolean) {
        dataStore.edit { settings ->
            settings[isNotificationScheduledKey] = isScheduled
        }
    }


}
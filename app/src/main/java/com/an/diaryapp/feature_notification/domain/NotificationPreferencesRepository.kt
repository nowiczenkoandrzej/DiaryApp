package com.an.diaryapp.feature_notification.domain

interface NotificationPreferencesRepository {

    suspend fun setIsNoteAdded(isAdded: Boolean)
    suspend fun getIsNoteAdded(): Boolean?

    suspend fun setAlarmId(id: Int)

    suspend fun getAlarmId(): Int

    suspend fun getNotificationTime(): String

    suspend fun setNotificationTime(time: String)

    suspend fun getIsNotificationScheduled(): Boolean
    suspend fun setIsNotificationScheduled(isScheduled: Boolean)
}
package com.an.diaryapp.core.domain.repository

interface UserPreferencesRepository {

    suspend fun setIsNoteAdded(isAdded: Boolean)
    suspend fun getIsNoteAdded(): Boolean?

    suspend fun setAlarmId(id: Int)

    suspend fun getAlarmId(): Int
}
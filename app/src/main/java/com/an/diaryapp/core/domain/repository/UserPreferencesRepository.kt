package com.an.diaryapp.core.domain.repository

interface UserPreferencesRepository {

    suspend fun setIsNoteAdded(isAdded: Boolean)
    suspend fun getIsNoteAdded(): Boolean?

    suspend fun setHashCode(code: Int)

    suspend fun getHashCode(): Int
}
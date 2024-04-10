package com.an.diaryapp.di

import com.an.diaryapp.core.data.NotesRepositoryImpl
import com.an.diaryapp.core.data.data_store.UserPreferencesRepositoryImpl

import com.an.diaryapp.core.domain.repository.NotesRepository
import com.an.diaryapp.core.domain.repository.UserPreferencesRepository
import com.an.diaryapp.feature_settings.data.SettingRepositoryImpl
import com.an.diaryapp.feature_settings.domain.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindNoteRepository(
        repository: NotesRepositoryImpl
    ): NotesRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        repository: SettingRepositoryImpl
    ): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(
        repository: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository

}
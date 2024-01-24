package com.an.diaryapp.di

import com.an.diaryapp.core.data.NotesRepositoryImpl

import com.an.diaryapp.core.domain.NotesRepository
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
    abstract fun bindLocationTracker(
        repository: NotesRepositoryImpl
    ): NotesRepository

}
package com.an.diaryapp.di

import android.app.Application
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.an.diaryapp.core.domain.NoteDataSource
import com.an.diaryapp.core.data.sql_delight.NoteDataSourceImpl
import com.an.diaryapp.NotesDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {


    @Provides
    @Singleton
    fun provideDbDriver(
        app: Application
    ): AndroidSqliteDriver = AndroidSqliteDriver(
        schema = NotesDatabase.Schema,
        context = app,
        name = "note.db"
    )

    @Provides
    @Singleton
    fun provideDatabase(
        driver: AndroidSqliteDriver
    ): NotesDatabase = NotesDatabase(driver = driver)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class DatasourceModule {

    @Binds
    @Singleton
    abstract fun bindNoteDataSource(
        dataSource: NoteDataSourceImpl
    ): NoteDataSource

}

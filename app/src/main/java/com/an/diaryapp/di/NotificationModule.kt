package com.an.diaryapp.di

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import com.an.diaryapp.feature_notification.domain.NotificationPreferencesRepository
import com.an.diaryapp.feature_notification.data.AlarmSchedulerImpl
import com.an.diaryapp.feature_notification.domain.AlarmScheduler
import com.an.diaryapp.feature_notification.NotificationService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NotificationModule {

    @Provides
    @Singleton
    fun provideAlarmManager(
        app: Application
    ): AlarmManager = app.getSystemService(AlarmManager::class.java)

    @Provides
    @Singleton
    fun provideNotificationManager(
        app: Application
    ): NotificationManager = app.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @Provides
    @Singleton
    fun provideContext(
        @ApplicationContext app: Context
    ): Context = app

    @Singleton
    @Provides
    fun provideNotificationService(
        @ApplicationContext app: Context,
        notificationManager: NotificationManager,
        notificationPreferencesRepository: NotificationPreferencesRepository
    ): NotificationService = NotificationService(
        app,
        notificationManager,
        notificationPreferencesRepository
    )
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AlarmSchedulerModule {

    @Binds
    @Singleton
    abstract fun bindAlarmScheduler (
        alarmScheduler: AlarmSchedulerImpl
    ): AlarmScheduler

}
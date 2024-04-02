package com.an.diaryapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.an.diaryapp.feature_settings.data.NotificationService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DiaryApp: Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NotificationService.NOTIFICATION_CHANNEL_ID,
            "Note Reminder",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Reminds you to add new note every day."

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}
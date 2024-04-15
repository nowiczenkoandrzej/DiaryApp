package com.an.diaryapp.feature_notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.an.diaryapp.core.domain.repository.UserPreferencesRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver(
    /*private val notificationManager: NotificationManager,
    private val userPreferencesRepository: UserPreferencesRepository,*/
    //private val notificationService: NotificationService
): BroadcastReceiver() {

    @Inject lateinit var notificationService: NotificationService
    override fun onReceive(context: Context?, intent: Intent?) {

        if(context == null) return

        notificationService.showNotification()

        /*val service = NotificationService(
            context,
            notificationManager,
            userPreferencesRepository
        )
        service.showNotification()*/


    }


}
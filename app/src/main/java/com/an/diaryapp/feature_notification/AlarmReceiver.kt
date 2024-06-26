package com.an.diaryapp.feature_notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {

    @Inject lateinit var notificationService: NotificationService
    override fun onReceive(context: Context?, intent: Intent?) {

        if(context == null) return

        notificationService.showNotification()

    }


}
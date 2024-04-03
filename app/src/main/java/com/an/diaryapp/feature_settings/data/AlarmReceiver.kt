package com.an.diaryapp.feature_settings.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver(): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("MESSAGE") ?: return


        if(context == null) return

        val service = NotificationService(context)
        service.showNotification()





    }
}
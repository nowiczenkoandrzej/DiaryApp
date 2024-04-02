package com.an.diaryapp.feature_settings.data

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.an.diaryapp.MainActivity
import com.an.diaryapp.R

class NotificationService(
    private val context: Context
) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    fun showNotification() {

        val activityIntent = Intent(context, MainActivity::class.java)

        val intent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_menu_book_24)
            .setContentTitle("Create New Book")
            .setContentText("")
            .setContentIntent(intent)
            .build()

        notificationManager.notify(
            1,
            notification
        )
    }


    companion object {
        const val NOTIFICATION_CHANNEL_ID = "channel_id"
    }

}
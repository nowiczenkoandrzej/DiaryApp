package com.an.diaryapp.feature_notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.an.diaryapp.MainActivity
import com.an.diaryapp.R
import com.an.diaryapp.feature_notification.domain.NotificationPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationService @Inject constructor(
    private val context: Context,
    private val notificationManager: NotificationManager,
    private val notificationPreferencesRepository: NotificationPreferencesRepository
) {

    fun showNotification() {
        CoroutineScope(Dispatchers.IO).launch {
            val hasNoteBeenAddedToday = notificationPreferencesRepository.getIsNoteAdded() ?: false

            Log.d("TAG", "showNotification: alarm received, has note been added: $hasNoteBeenAddedToday")

            if(!hasNoteBeenAddedToday) {
                val activityIntent = Intent(context, MainActivity::class.java)

                val intent = PendingIntent.getActivity(
                    context,
                    1,
                    activityIntent,
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
                )

                val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.drawable.baseline_menu_book_24)
                    .setContentTitle("Create New Note")
                    .setContentText("")
                    .setContentIntent(intent)
                    .setAutoCancel(true)
                    .build()

                notificationManager.notify(
                    1,
                    notification
                )

                Log.d("TAG", "showNotification: notification shown")
            } else {
                notificationPreferencesRepository.setIsNoteAdded(false)
                Log.d("TAG", "showNotification: notification didnt show up")
            }
        }
    }


    companion object {
        const val NOTIFICATION_CHANNEL_ID = "channel_id"
    }

}
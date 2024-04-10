package com.an.diaryapp.feature_notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.an.diaryapp.MainActivity
import com.an.diaryapp.R
import com.an.diaryapp.core.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationService @Inject constructor(
    private val context: Context,
    private val notificationManager: NotificationManager,
    private val userPreferencesRepository: UserPreferencesRepository
) {

    //private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    fun showNotification() {
        CoroutineScope(Dispatchers.IO).launch {
            val hasNoteBeenAddedToday = userPreferencesRepository.getIsNoteAdded() ?: false

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
            }
        }
    }


    companion object {
        const val NOTIFICATION_CHANNEL_ID = "channel_id"
    }

}
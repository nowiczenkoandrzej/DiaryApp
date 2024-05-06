package com.an.diaryapp.feature_notification.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.an.diaryapp.feature_notification.AlarmReceiver
import com.an.diaryapp.feature_notification.domain.NotificationPreferencesRepository
import com.an.diaryapp.feature_notification.domain.AlarmItem
import com.an.diaryapp.feature_notification.domain.AlarmScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.ZoneId
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager,
    private val notificationPreferencesRepository: NotificationPreferencesRepository
): AlarmScheduler {

    override fun schedule(item: AlarmItem) {

        val intent = Intent(context, AlarmReceiver::class.java)

        val hashCode = item.hashCode()


        CoroutineScope(Dispatchers.IO).launch {

            cancel()

            notificationPreferencesRepository.setIsNoteAdded(false)

            notificationPreferencesRepository.setAlarmId(hashCode)

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
                AlarmManager.INTERVAL_DAY,
                PendingIntent.getBroadcast(
                    context,
                    hashCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
        }

    }

    override fun cancel() {

        CoroutineScope(Dispatchers.IO).launch {

            val hashCode = notificationPreferencesRepository.getAlarmId()

            alarmManager.cancel(
                PendingIntent.getBroadcast(
                    context,
                    hashCode,
                    Intent(context, AlarmReceiver::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )

        }
    }
}
package com.an.diaryapp.feature_notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.an.diaryapp.core.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.ZoneId
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager,
    private val userPreferencesRepository: UserPreferencesRepository
): AlarmScheduler {

    //private val alarmManager = context.getSystemService(AlarmManager::class.java)



    override fun schedule(item: AlarmItem) {

        val intent = Intent(context, AlarmReceiver::class.java)

        val hashCode = item.hashCode()


        CoroutineScope(Dispatchers.IO).launch {

            cancel()

            userPreferencesRepository.setIsNoteAdded(false)

            userPreferencesRepository.setAlarmId(hashCode)

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
                60000L, //AlarmManager.INTERVAL_DAY,
                PendingIntent.getBroadcast(
                    context,
                    hashCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )

        }



    }

    //@RequiresApi(34)
    override fun cancel() {

        CoroutineScope(Dispatchers.IO).launch {

            val hashCode = userPreferencesRepository.getAlarmId()

            alarmManager.cancel(
                PendingIntent.getBroadcast(
                    context,
                    hashCode,
                    Intent(context, AlarmReceiver::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )






        }

        //alarmManager.cancelAll()
    }
}
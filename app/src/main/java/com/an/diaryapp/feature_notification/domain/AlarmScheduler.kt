package com.an.diaryapp.feature_notification.domain

import com.an.diaryapp.feature_notification.domain.AlarmItem

interface AlarmScheduler {

    fun schedule(item: AlarmItem)
    fun cancel()

}
package com.an.diaryapp.feature_settings.domain

import com.an.diaryapp.feature_settings.domain.model.AlarmItem

interface AlarmScheduler {

    fun schedule(item: AlarmItem)
    fun cancel(item: AlarmItem)

}
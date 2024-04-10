package com.an.diaryapp.feature_notification

import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime,
    val message: String
)
package com.an.diaryapp.feature_settings.domain

import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime,
    val message: String
)
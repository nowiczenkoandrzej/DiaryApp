package com.an.diaryapp.feature_settings.domain.model



data class AppSettings(
    val isNotificationScheduled: Boolean = false,
    val notificationTime: String? = null,
    val isTimePickerDialogVisible: Boolean = false,
    val isCheckBoxChecked: Boolean = false,
    val isDefaultLocationPicked: Boolean = false,
    val defaultLocationLat: Double = 0.0,
    val defaultLocationLong: Double = 0.0,
)




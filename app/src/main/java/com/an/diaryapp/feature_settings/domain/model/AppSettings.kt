package com.an.diaryapp.feature_settings.domain.model



data class AppSettings(
    val isNotificationScheduled: Boolean = false,
    val notificationTime: String? = null,
    val isTimePickerDialogVisible: Boolean = false,
    val isCheckBoxChecked: Boolean = false
)




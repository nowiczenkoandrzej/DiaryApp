package com.an.diaryapp.feature_settings.domain.model



data class SettingsNotificationState(
    val isNotificationScheduled: Boolean = false,
    val notificationTime: String? = null,

    val isTimePickerDialogVisible: Boolean = false,
    val isNotificationSwitchChecked: Boolean = false,


)




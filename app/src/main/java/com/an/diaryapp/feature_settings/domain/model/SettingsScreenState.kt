package com.an.diaryapp.feature_settings.domain.model



data class SettingsScreenState(
    val isNotificationScheduled: Boolean = false,
    val notificationTime: String? = null,

    val isTimePickerDialogVisible: Boolean = false,
    val isNotificationSwitchChecked: Boolean = false,

    val isDefaultLocationPicked: Boolean = false,
    val defaultLocationLat: Double = 54.10,
    val defaultLocationLong: Double = 22.90,
    val defaultLocation: String = ""
)




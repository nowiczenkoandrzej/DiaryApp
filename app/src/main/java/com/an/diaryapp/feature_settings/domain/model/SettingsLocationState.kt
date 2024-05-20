package com.an.diaryapp.feature_settings.domain.model

data class SettingsLocationState(

    val isDefaultLocationPicked: Boolean = false,
    val isSwitchChecked: Boolean = false,
    val defaultLocationLat: Double = 54.10,
    val defaultLocationLong: Double = 22.90,
    val defaultLocation: String = ""

)

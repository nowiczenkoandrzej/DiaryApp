package com.an.diaryapp.feature_settings.domain.model

import android.location.Location
import java.time.LocalTime

sealed class SettingsScreenEvent {

    data class Schedule(val time: LocalTime): SettingsScreenEvent()
    object Cancel: SettingsScreenEvent()
    object ShowTimePickerDialog: SettingsScreenEvent()
    object TimePickerDialogShown: SettingsScreenEvent()
    object CheckBoxChecked: SettingsScreenEvent()
    data class SelectLocation(val location: Location): SettingsScreenEvent()

}

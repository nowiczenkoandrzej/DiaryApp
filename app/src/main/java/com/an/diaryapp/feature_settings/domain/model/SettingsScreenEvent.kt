package com.an.diaryapp.feature_settings.domain.model

import android.location.Location
import java.time.LocalTime

sealed class SettingsScreenEvent {

    data class Schedule(val time: LocalTime): SettingsScreenEvent()
    object CancelNotification: SettingsScreenEvent()
    object ShowTimePickerDialog: SettingsScreenEvent()
    object TimePickerDialogShown: SettingsScreenEvent()
    object CheckNotificationSwitch: SettingsScreenEvent()
    data class SelectLocation(val location: Location): SettingsScreenEvent()


    data class SetDefaultLocation(val location: Location): SettingsScreenEvent()
    object CheckLocationSwitch: SettingsScreenEvent()
    object ShowLocationDialog: SettingsScreenEvent()
    object GetLocation: SettingsScreenEvent()


}

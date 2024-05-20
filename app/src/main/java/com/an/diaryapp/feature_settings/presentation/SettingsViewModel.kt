package com.an.diaryapp.feature_settings.presentation

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.an.diaryapp.feature_location.domain.LocationPreferencesRepository
import com.an.diaryapp.feature_notification.domain.NotificationPreferencesRepository
import com.an.diaryapp.feature_location.domain.LocationRepository
import com.an.diaryapp.feature_notification.domain.AlarmItem
import com.an.diaryapp.feature_notification.domain.AlarmScheduler
import com.an.diaryapp.feature_settings.domain.model.SettingsLocationState
import com.an.diaryapp.feature_settings.domain.model.SettingsNotificationState
import com.an.diaryapp.feature_settings.domain.model.SettingsScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject




@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val notificationPreferencesRepository: NotificationPreferencesRepository,
    private val locationPreferencesRepository: LocationPreferencesRepository,
    private val locationRepository: LocationRepository,
    private val alarmScheduler: AlarmScheduler
): ViewModel() {

    private val _notificationState = MutableStateFlow(SettingsNotificationState())
    val notificationState = _notificationState.asStateFlow()

    private val _locationState = MutableStateFlow(SettingsLocationState())
    val locationState = _locationState.asStateFlow()

    init {
        viewModelScope.launch {
            _notificationState.value = SettingsNotificationState(
                isNotificationScheduled = notificationPreferencesRepository.getIsNotificationScheduled(),
                notificationTime = notificationPreferencesRepository.getNotificationTime(),
                isTimePickerDialogVisible = false,
                isNotificationSwitchChecked = notificationPreferencesRepository.getIsNotificationScheduled()
            )

            if(locationPreferencesRepository.getIsDefaultLocationPicked()) {
                _locationState.value = SettingsLocationState(
                    isDefaultLocationPicked = true,
                    isSwitchChecked = true,
                    defaultLocation = locationPreferencesRepository.getDefaultLocationName(),
                    defaultLocationLat = locationPreferencesRepository.getDefaultLocation().latitude,
                    defaultLocationLong = locationPreferencesRepository.getDefaultLocation().longitude,
                )
            }
        }
    }


    fun onEvent(event: SettingsScreenEvent)  {
        when (event) {

            SettingsScreenEvent.CancelNotification -> cancel()

            is SettingsScreenEvent.Schedule -> schedule(event.time)

            SettingsScreenEvent.ShowTimePickerDialog -> {
                _notificationState.value = notificationState.value.copy(
                    isTimePickerDialogVisible = true
                )
            }
            SettingsScreenEvent.TimePickerDialogShown ->  {
                _notificationState.value = notificationState.value.copy(
                    isTimePickerDialogVisible = false
                )
            }

            SettingsScreenEvent.CheckNotificationSwitch -> {
                _notificationState.value = notificationState.value.copy(
                    isNotificationSwitchChecked = !notificationState.value.isNotificationSwitchChecked
                )
            }

            is SettingsScreenEvent.SelectLocation ->  {

                viewModelScope.launch {
                    val locationName = locationRepository.getCityNameFromLocation(event.location)

                    _locationState.value = locationState.value.copy(
                        defaultLocationLat = event.location.latitude,
                        defaultLocationLong = event.location.longitude,
                        defaultLocation = locationName ?: ""
                    )
                }

            }

            SettingsScreenEvent.CheckLocationSwitch ->  {
                _locationState.value = locationState.value.copy(
                    isSwitchChecked = !locationState.value.isSwitchChecked
                )
            }
            is SettingsScreenEvent.SetDefaultLocation -> {
                saveDefaultLocation()
            }
            SettingsScreenEvent.ShowLocationDialog -> {}
            SettingsScreenEvent.GetLocation -> {

                viewModelScope.launch {
                    val currentLocation = locationRepository.getLocation()

                    if(currentLocation != null) {

                        val currentLocationName = locationRepository.getCityNameFromLocation(currentLocation)

                        _locationState.value = locationState.value.copy(
                            defaultLocationLat = currentLocation.latitude,
                            defaultLocationLong = currentLocation.longitude,
                            defaultLocation = currentLocationName ?: ""
                        )

                        saveDefaultLocation()

                    }
                }

            }
        }
    }

    private fun schedule(localTime: LocalTime) {
        val localDate = LocalDate.now()

        val scheduledDate = LocalDateTime.of(localDate, localTime)

        if(LocalDateTime.now().isAfter(scheduledDate))
            scheduledDate.plusDays(1)

        val alarmItem = AlarmItem(
            time = scheduledDate,
            message = "Notification Time"
        )
        alarmScheduler.schedule(alarmItem)

        viewModelScope.launch {
            notificationPreferencesRepository.setIsNotificationScheduled(true)
            notificationPreferencesRepository.setNotificationTime(localTime.toString())

            _notificationState.value = notificationState.value.copy(
                notificationTime = localTime.toString(),
                isNotificationScheduled = true
            )
        }
    }

    private fun cancel() {
        alarmScheduler.cancel()
        viewModelScope.launch {
            notificationPreferencesRepository.setIsNotificationScheduled(false)
            notificationPreferencesRepository.setNotificationTime("")

            _notificationState.value = notificationState.value.copy(
                isNotificationScheduled = false,
                notificationTime = ""
            )
        }

    }

    private fun saveDefaultLocation() {
        viewModelScope.launch {

            val defaultLocation = Location("default_location")
            defaultLocation.latitude = locationState.value.defaultLocationLat
            defaultLocation.longitude = locationState.value.defaultLocationLong

            locationPreferencesRepository.setIsDefaultLocationPicked(true)
            locationPreferencesRepository.saveDefaultLocation(defaultLocation)
            locationPreferencesRepository.setDefaultLocationName(locationState.value.defaultLocation)

            _locationState.value = locationState.value.copy(
                isDefaultLocationPicked = true,
                isSwitchChecked = true,
            )

        }
    }




}
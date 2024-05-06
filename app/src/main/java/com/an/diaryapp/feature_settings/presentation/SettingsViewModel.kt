package com.an.diaryapp.feature_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.an.diaryapp.feature_location.domain.LocationPreferencesRepository
import com.an.diaryapp.feature_notification.domain.NotificationPreferencesRepository
import com.an.diaryapp.feature_location.domain.LocationRepository
import com.an.diaryapp.feature_notification.domain.AlarmItem
import com.an.diaryapp.feature_notification.domain.AlarmScheduler
import com.an.diaryapp.feature_settings.domain.model.SettingsScreenState
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

    private val _screenState = MutableStateFlow(SettingsScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            _screenState.value = SettingsScreenState(
                isNotificationScheduled = notificationPreferencesRepository.getIsNotificationScheduled(),
                notificationTime = notificationPreferencesRepository.getNotificationTime(),
                isTimePickerDialogVisible = false,
                isNotificationSwitchChecked = notificationPreferencesRepository.getIsNotificationScheduled()
            )
        }
    }


    fun onEvent(event: SettingsScreenEvent)  {
        when (event) {

            SettingsScreenEvent.Cancel -> cancel()

            is SettingsScreenEvent.Schedule -> schedule(event.time)

            SettingsScreenEvent.ShowTimePickerDialog -> {
                _screenState.value = screenState.value.copy(
                    isTimePickerDialogVisible = true
                )
            }
            SettingsScreenEvent.TimePickerDialogShown ->  {
                _screenState.value = screenState.value.copy(
                    isTimePickerDialogVisible = false
                )
            }

            SettingsScreenEvent.CheckBoxChecked -> {
                _screenState.value = screenState.value.copy(
                    isNotificationSwitchChecked = !screenState.value.isNotificationSwitchChecked
                )
            }

            is SettingsScreenEvent.SelectLocation ->  {

                viewModelScope.launch {
                    val locationName = locationRepository.getCityNameFromLocation(event.location)

                    _screenState.value = screenState.value.copy(
                        defaultLocationLat = event.location.latitude,
                        defaultLocationLong = event.location.longitude,
                        defaultLocation = locationName ?: ""
                    )
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

            _screenState.value = screenState.value.copy(
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

            _screenState.value = screenState.value.copy(
                isNotificationScheduled = false,
                notificationTime = ""
            )
        }

    }



}
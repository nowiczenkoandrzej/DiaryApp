package com.an.diaryapp.feature_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.an.diaryapp.core.domain.repository.UserPreferencesRepository
import com.an.diaryapp.feature_notification.AlarmItem
import com.an.diaryapp.feature_notification.AlarmScheduler
import com.an.diaryapp.feature_settings.domain.model.AppSettings
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
    private val repository: UserPreferencesRepository,
    private val alarmScheduler: AlarmScheduler
): ViewModel() {

    private val _screenState = MutableStateFlow(AppSettings())
    val screenState = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            _screenState.value = AppSettings(
                isNotificationScheduled = repository.getIsNotificationScheduled(),
                notificationTime = repository.getNotificationTime(),
                isTimePickerDialogVisible = false,
                isCheckBoxChecked = repository.getIsNotificationScheduled()
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
                    isCheckBoxChecked = !screenState.value.isCheckBoxChecked
                )
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
            repository.setIsNotificationScheduled(true)
            repository.setNotificationTime(localTime.toString())

            _screenState.value = screenState.value.copy(
                notificationTime = localTime.toString(),
                isNotificationScheduled = true
            )
        }
    }

    private fun cancel() {
        alarmScheduler.cancel()
        viewModelScope.launch {
            repository.setIsNotificationScheduled(false)
            repository.setNotificationTime("")

            _screenState.value = screenState.value.copy(
                isNotificationScheduled = false,
                notificationTime = ""
            )
        }

    }



}
package com.an.diaryapp.feature_settings.presentation

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.an.diaryapp.di.DATA_STORE_FILE_NAME
import com.an.diaryapp.feature_settings.data.AppSettingsSerializer
import com.an.diaryapp.feature_settings.domain.SettingsRepository
import com.an.diaryapp.feature_settings.domain.model.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject




@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
): ViewModel() {


    init {
        viewModelScope.launch {
            repository.getAppSettings().collect{ appSettings ->
                _savedDate.value = appSettings.notificationHour
            }
        }
    }

    /*val data = dataStore.data.collectLatest {
        _savedDate.value = it.notificationHour
    }*/

    private val _date = MutableStateFlow(LocalTime.now())
    val date = _date.asStateFlow()

    private var _savedDate = MutableStateFlow(LocalTime.now())
    val savedDate = _savedDate.asStateFlow()

    fun setTime(time: LocalTime) {
        _date.value = time
    }

    fun saveTime() {
        viewModelScope.launch {
            repository.putAppSettings(
                settings = AppSettings(
                    notificationHour = date.value
                )
            )
        }
    }



}
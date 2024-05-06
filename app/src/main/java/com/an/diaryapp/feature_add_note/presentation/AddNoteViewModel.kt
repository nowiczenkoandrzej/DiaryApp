package com.an.diaryapp.feature_add_note.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.an.diaryapp.core.domain.repository.NotesRepository
import com.an.diaryapp.feature_add_note.domain.AddNoteScreenState
import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.core.domain.model.NoteItem
import com.an.diaryapp.core.domain.model.Resource
import com.an.diaryapp.core.domain.model.WeatherInfo
import com.an.diaryapp.feature_notification.domain.NotificationPreferencesRepository
import com.an.diaryapp.feature_location.domain.LocationRepository
import com.an.diaryapp.feature_weather_api.domain.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    private val notificationPreferencesRepository: NotificationPreferencesRepository,
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
): ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _screenState = MutableStateFlow(AddNoteScreenState())
    val screenState = _screenState.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        viewModelScope.launch {

            when(val result = notesRepository.getAllCategories()) {
                is Resource.Error -> {
                    _error.value = result.error
                    _categories.value = emptyList()
                }
                is Resource.Success -> {
                    _error.value = null
                    _categories.value = result.data ?: emptyList()
                }
            }
        }
    }

    fun selectCategory(category: Category) {

        val newList = ArrayList(screenState.value.selectedCategory)
        newList.add(category)
        _screenState.value = screenState.value.copy(
            selectedCategory = newList
        )
    }

    fun unselectCategory(category: Category) {

        val newList = ArrayList(screenState.value.selectedCategory)
        newList.remove(category)
        _screenState.value = screenState.value.copy(
            selectedCategory = newList
        )
    }

    fun setDescription(text: String) {
        _screenState.value = screenState.value.copy(
            description = text
        )
    }

    fun addNote() {
        viewModelScope.launch {

            val state = screenState.value

            notesRepository.addNote(
                NoteItem(
                    description = state.description,
                    timestamp = state.timestamp,
                    categories = state.selectedCategory,
                    weatherInfo = state.weatherInfo,
                    location = state.location
                )
            )

            notificationPreferencesRepository.setIsNoteAdded(true)
        }
    }

    fun getWeatherInfo() {
        viewModelScope.launch {

            locationRepository.getLocation()?.let { location ->

                locationRepository.getCityNameFromLocation(location)?.let {
                    _screenState.value = screenState.value.copy(
                        location = it
                    )
                }

                val weatherInfo = weatherRepository.getWeatherInfo(
                    lat = location.latitude,
                    long = location.longitude
                )

                when(weatherInfo) {
                    is Resource.Success -> {
                        _screenState.value = screenState.value.copy(
                            weatherInfo = weatherInfo.data
                        )
                    }
                    is Resource.Error -> {
                        Log.d("TAG", "getWeatherInfo: ${weatherInfo.error}")
                    }
                }
            }

        }
    }

    fun setTimestamp(date: LocalDate) {
        _screenState.value = screenState.value.copy(
            timestamp = date
        )
    }

    fun showOrHideDatePickerDialog() {
        _screenState.value = screenState.value.copy(
            isDatePickerVisible = !screenState.value.isDatePickerVisible
        )
    }

    fun addTestNote() {

        viewModelScope.launch {

            val state = screenState.value

            notesRepository.addNote(
                NoteItem(
                    description = state.description,
                    timestamp = state.timestamp,
                    categories = state.selectedCategory,
                    weatherInfo = WeatherInfo(
                        id = null,
                        weatherType = 81,
                        temperature = 25.0
                    ),
                    location = state.location
                )
            )

        }

    }

}
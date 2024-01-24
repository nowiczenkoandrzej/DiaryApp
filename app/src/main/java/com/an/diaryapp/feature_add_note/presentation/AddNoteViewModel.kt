package com.an.diaryapp.feature_add_note.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.an.diaryapp.feature_add_note.domain.LocationTracker
import com.an.diaryapp.core.domain.NotesRepository
import com.an.diaryapp.feature_add_note.domain.model.AddNoteScreenState
import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.core.domain.model.NoteItem
import com.an.diaryapp.core.domain.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val repository: NotesRepository,
    private val locationTracker: LocationTracker
): ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _screenState = MutableStateFlow(AddNoteScreenState())
    val screenState = _screenState.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()



    init {
        viewModelScope.launch {

            when(val result = repository.getAllCategories()) {
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

            repository.addNote(
                NoteItem(
                description = state.description,
                timestamp = LocalDate.now(),
                categories = state.selectedCategory,
                weatherInfo = state.weatherInfo,
                location = state.location
            )
            )
        }
    }

    fun getWeatherInfo() {
        viewModelScope.launch {
            Log.d("dupa", "getWeatherInfo: przed pobraniem lokalizacji")
            locationTracker.getCurrentLocation()?.let { location ->
                Log.d("dupa", "getWeatherInfo: ${location.latitude}, ${location.longitude}")
                val result = repository.getWeatherInfo(
                    lat = location.latitude,
                    long = location.longitude
                )



                when(result) {
                    is Resource.Success -> {
                        _screenState.value = screenState.value.copy(
                            weatherInfo = result.data
                        )
                        Log.d("dupa", "getWeatherInfo: success : ${result.data}")

                    }
                    is Resource.Error -> {
                        Log.d("dupa", "getWeatherInfo: error: ${result.error}")
                    }
                }
            }
        }
    }
}
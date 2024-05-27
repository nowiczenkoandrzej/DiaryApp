package com.an.diaryapp.feature_add_note.domain

import android.location.Location
import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.core.domain.model.WeatherInfo
import java.time.LocalDate

data class AddNoteScreenState(
    val description: String = "",
    val selectedCategory: List<Category> = emptyList(),
    val weatherInfo: WeatherInfo? = null,
    val locationName: String? = null,
    val timestamp: LocalDate = LocalDate.now(),
    val defaultLocation: Location? = null,
    val isWeatherInfoLoading: Boolean = false

)

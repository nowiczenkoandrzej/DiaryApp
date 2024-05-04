package com.an.diaryapp.feature_add_note.domain

import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.core.domain.model.WeatherInfo
import java.time.LocalDate

data class AddNoteScreenState(
    val description: String = "",
    val selectedCategory: List<Category> = emptyList(),
    val weatherInfo: WeatherInfo? = null,
    val location: String? = null,
    val timestamp: LocalDate = LocalDate.now(),
    val isDatePickerVisible: Boolean = false
)

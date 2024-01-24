package com.an.diaryapp.feature_add_note.domain.model

import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.core.domain.model.WeatherInfo

data class AddNoteScreenState(
    val description: String = "",
    val selectedCategory: List<Category> = emptyList(),
    val weatherInfo: WeatherInfo? = null,
    val location: String? = null
)

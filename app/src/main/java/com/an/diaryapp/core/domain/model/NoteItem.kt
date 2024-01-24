package com.an.diaryapp.core.domain.model

import java.time.LocalDate

data class NoteItem(
    val id: Long? = null,
    val description: String,
    val timestamp: LocalDate,
    val categories: List<Category> = emptyList(),
    val weatherInfo: WeatherInfo? = null,
    val location: String? = null
)
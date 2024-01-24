package com.an.diaryapp.core.data

import com.an.diaryapp.feature_add_note.data.remote.WeatherResponse
import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.core.domain.model.WeatherInfo
import diaryapp.db.CategoryEntity

/*fun NoteEntity.toNoteItem(): NoteItem {
    return NoteItem(
        id = this.id,
        description = this.description,
        timestamp = toLocalDate(this.timestamp),
    )
}

fun toLocalDate(date: String): LocalDate {
    val numbers = date.split("-")
    return LocalDate.of(
        numbers[0].toInt(),
        numbers[1].toInt(),
        numbers[2].toInt()
    )
}*/

fun CategoryEntity.toCategory() = Category(
    id = this.id,
    name = this.name,
    backgroundColor = this.background_color
)

fun List<CategoryEntity>.toCategoryList() = this.map { it.toCategory() }


fun WeatherResponse.toWeatherInfo(): WeatherInfo = WeatherInfo(
    temperature = this.currentWeather.temperature,
    weatherType = this.currentWeather.weatherCode.toLong()
)

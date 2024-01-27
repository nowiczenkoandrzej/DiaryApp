package com.an.diaryapp.core.data

import androidx.annotation.DrawableRes
import com.an.diaryapp.R
import com.an.diaryapp.feature_add_note.data.remote.WeatherResponse
import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.core.domain.model.WeatherInfo
import diaryapp.db.CategoryEntity

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

fun Long.toWeatherIcon(): Int {
    return when(this.toInt()) {
        0 -> R.drawable.ic_sunny
        1 -> R.drawable.ic_cloudy
        2 -> R.drawable.ic_cloudy
        3 -> R.drawable.ic_cloudy
        45 -> R.drawable.ic_very_cloudy
        48 -> R.drawable.ic_very_cloudy
        51-> R.drawable.ic_rainshower
        53 -> R.drawable.ic_rainshower
        55 -> R.drawable.ic_rainshower
        56 -> R.drawable.ic_snowyrainy
        57 -> R.drawable.ic_snowyrainy
        61 -> R.drawable.ic_rainy
        63 -> R.drawable.ic_rainy
        65 -> R.drawable.ic_rainy
        66 -> R.drawable.ic_snowyrainy
        67 -> R.drawable.ic_snowyrainy
        71 -> R.drawable.ic_snowy
        73 -> R.drawable.ic_heavysnow
        75 -> R.drawable.ic_heavysnow
        77 -> R.drawable.ic_heavysnow
        80 -> R.drawable.ic_rainshower
        81 -> R.drawable.ic_rainshower
        82 -> R.drawable.ic_rainshower
        85 -> R.drawable.ic_snowy
        86 -> R.drawable.ic_snowy
        95 -> R.drawable.ic_thunder
        96 -> R.drawable.ic_rainythunder
        99 -> R.drawable.ic_rainythunder
        else -> R.drawable.ic_sunny
    }
}

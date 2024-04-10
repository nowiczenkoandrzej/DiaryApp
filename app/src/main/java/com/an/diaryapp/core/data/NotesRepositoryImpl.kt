package com.an.diaryapp.core.data

import com.an.diaryapp.feature_add_note.data.remote.WeatherApi
import com.an.diaryapp.core.domain.NoteDataSource
import com.an.diaryapp.core.domain.repository.NotesRepository
import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.core.domain.model.NoteItem
import com.an.diaryapp.core.domain.model.Resource
import com.an.diaryapp.core.domain.model.WeatherInfo
import java.time.LocalDate
import javax.inject.Inject
class NotesRepositoryImpl @Inject constructor(
    private val db: NoteDataSource,
    private val weatherApi: WeatherApi
): NotesRepository {

    override suspend fun getAllCategories(): Resource<List<Category>> {

        return try {
            val categories = db.getAllCategories().toCategoryList()

            Resource.Success(
                data = categories
            )
        } catch (e: Exception) {
            Resource.Error(
                message = e.message.toString()
            )
        }
    }

    override suspend fun getNotes(
        content: String
    ): Resource<List<NoteItem>> {
        return try {
            val notes = db.getNotesByContent(content)
                .groupBy { it.note_id }
                .map { (_, group) ->

                    val  firstRow = group.first()
                    val isWeatherInfoAttached = firstRow.weather_id != null
                    val isCategoryListEmpty = firstRow.category_id == null

                    NoteItem(
                        id = firstRow.note_id,
                        description = firstRow.description,
                        location = firstRow.location,
                        timestamp = LocalDate.parse(firstRow.timestamp),
                        categories = if(isCategoryListEmpty) {
                            emptyList()
                        } else {
                            group.map {
                                Category(
                                    id = it.category_id,
                                    backgroundColor = it.background_color.toString(),
                                    name = it.name.toString()
                                )
                            }
                        },
                        weatherInfo = if(isWeatherInfoAttached) {
                            WeatherInfo(
                                id = firstRow.weather_id,
                                temperature = firstRow.temperature!!,
                                weatherType = firstRow.weather_type!!
                            )
                        } else {
                            null
                        }
                    )
                }
            Resource.Success(
                data = notes
            )

        } catch (e: Exception) {
            Resource.Error(
                message = e.message.toString()
            )
        }
    }

    override suspend fun getNoteById(id: Long): Resource<NoteItem> {
        return try {
            val note = db.getNoteById(id)!!
                .groupBy { it.note_id }
                .map { (_, group) ->


                    val firstRow = group.first()
                    val isWeatherInfoAttached = firstRow.weather_id != null
                    val isCategoryListEmpty = firstRow.category_id == null

                    NoteItem(
                        id = firstRow.note_id,
                        description = firstRow.description,
                        location = firstRow.location,
                        timestamp = LocalDate.parse(firstRow.timestamp),
                        categories = if(isCategoryListEmpty) {
                            emptyList()
                        } else {
                            group.map {
                                Category(
                                    id = it.category_id,
                                    backgroundColor = it.background_color.toString(),
                                    name = it.name.toString()
                                )
                            }
                        },
                        weatherInfo = if(isWeatherInfoAttached) {
                            WeatherInfo(
                                id = firstRow.weather_id,
                                temperature = firstRow.temperature!!,
                                weatherType = firstRow.weather_type!!
                            )
                        } else {
                            null
                        }
                    )
                }.first()

            Resource.Success(
                data = note
            )
        } catch (e: Exception) {
            Resource.Error(
                message = e.message.toString()
            )
        }

    }

    override suspend fun addNote(note: NoteItem) {
        db.addNote(note)
    }

    override suspend fun deleteNote(id: Long) {
        db.deleteNote(id)
    }

    override suspend fun getWeatherInfo(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            val weather = weatherApi.getWeatherData(lat, long)

            Resource.Success(
                data = weather.toWeatherInfo()
            )
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun getNoteFromDates(startDate: LocalDate, endDate: LocalDate): Resource<List<NoteItem>> {
        return try {
            val notes = db.getNoteFromDates(
                startDate = startDate,
                endDate = endDate
            ).groupBy { it.note_id }
                .map { (_, group) ->


                    val firstRow = group.first()
                    val isWeatherInfoAttached = firstRow.weather_id != null
                    val isCategoryListEmpty = firstRow.category_id == null

                    NoteItem(
                        id = firstRow.note_id,
                        description = firstRow.description,
                        location = firstRow.location,
                        timestamp = LocalDate.parse(firstRow.timestamp),
                        categories = if(isCategoryListEmpty) {
                            emptyList()
                        } else {
                            group.map {
                                Category(
                                    id = it.category_id,
                                    backgroundColor = it.background_color.toString(),
                                    name = it.name.toString()
                                )
                            }
                        },
                        weatherInfo = if(isWeatherInfoAttached) {
                            WeatherInfo(
                                id = firstRow.weather_id,
                                temperature = firstRow.temperature!!,
                                weatherType = firstRow.weather_type!!
                            )
                        } else {
                            null
                        }
                    )
                }
            Resource.Success(
                data = notes
            )

        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }


}


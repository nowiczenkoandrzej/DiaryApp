package com.an.diaryapp.core.data

import android.util.Log
import com.an.diaryapp.core.domain.NoteDataSource
import com.an.diaryapp.core.domain.repository.NotesRepository
import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.core.domain.model.NoteItem
import com.an.diaryapp.core.domain.model.Resource
import com.an.diaryapp.core.domain.model.WeatherInfo
import com.an.diaryapp.feature_note_list.domain.model.FilterType
import diaryapp.db.Note_view
import java.time.LocalDate
import javax.inject.Inject
class NotesRepositoryImpl @Inject constructor(
    private val db: NoteDataSource
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
            val dbResponse = db.getNotesByContent(content)

            val result = mapEntityToNotes(dbResponse)

            Resource.Success(
                data = result
            )

        } catch (e: Exception) {

            Resource.Error(
                message = e.message.toString()
            )
        }
    }

    override suspend fun getNoteById(id: Long): Resource<NoteItem> {
        return try {
            val dbResponse = db.getNoteById(id)

            val result = mapEntityToNotes(dbResponse!!).first()

            Resource.Success(
                data = result
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



    override suspend fun getFilteredNotes(filterType: FilterType): Resource<List<NoteItem>> {

        val onlyCategoryFilter = filterType.category != null && (filterType.startDate == null || filterType.endDate == null)
        val onlyDatesFilter = filterType.category == null && filterType.startDate != null && filterType.endDate != null
        val allFilters = filterType.category != null && filterType.startDate != null && filterType.endDate != null

        if(onlyCategoryFilter) {
            return try {
                val dbResponse = db.getNotesByCategory(
                    category = filterType.category!!
                )
                val result = mapEntityToNotes(dbResponse)

                Resource.Success(
                    data = result
                )
            } catch (e: Exception) {
                Resource.Error(
                    message = e.message.toString()
                )
            }
        } else if(onlyDatesFilter) {
            return try {
                val dbResponse = db.getNotesByDate(
                    startDate = filterType.startDate!!,
                    endDate = filterType.endDate!!
                )
                val result = mapEntityToNotes(dbResponse)

                Resource.Success(
                    data = result
                )
            } catch (e: Exception) {
                Resource.Error(
                    message = e.message.toString()
                )
            }
        } else if (allFilters) {
            return try {
                val dbResponse = db.getNotesByDateAndCategory(
                    filterType.startDate!!,
                    filterType.endDate!!,
                    filterType.category!!
                )
                val result = mapEntityToNotes(dbResponse)

                Resource.Success(
                    data = result
                )
            } catch (e: Exception) {
                Resource.Error(
                    message = e.message.toString()
                )
            }
        } else {
            return Resource.Error(message = "Something went wrong...")
        }




    }

    private fun mapEntityToNotes(
        list: List<Note_view>
    ): List<NoteItem> {
        return list.groupBy { it.note_id }
            .map {(_, group) ->
                val firstRow = group.first()
                val isWeatherInfoAttached = firstRow.weather_id != null
                val isCategoryListEmpty = firstRow.category_id == null

                NoteItem(
                    id = firstRow.note_id,
                    description = firstRow.note_content,
                    location = firstRow.location,
                    timestamp = LocalDate.parse(firstRow.timestamp),
                    categories = if(isCategoryListEmpty) {
                        emptyList()
                    } else {
                        group.map {
                            Category(
                                id = it.category_id,
                                backgroundColor = it.bg_color.toString(),
                                name = it.category_name.toString()
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
    }
}



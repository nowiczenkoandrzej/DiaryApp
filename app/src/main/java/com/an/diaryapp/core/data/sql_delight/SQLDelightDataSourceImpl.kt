package com.an.diaryapp.core.data.sql_delight

import android.util.Log
import com.an.diaryapp.NotesDatabase
import com.an.diaryapp.core.domain.NoteDataSource
import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.core.domain.model.NoteItem
import diaryapp.db.CategoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import diaryapp.db.Note_view
import java.time.LocalDate


class SQLDelightDataSourceImpl @Inject constructor(
    db: NotesDatabase
): NoteDataSource {

    private val queries = db.noteEntityQueries


    override suspend fun getNoteById(
        id: Long
    ): List<Note_view> {
        return queries
            .getNoteById(id)
            .executeAsList()
    }

    override suspend fun getNotesByContent(
        content: String
    ): List<Note_view> {
        return queries
            .getNotesByContent(content)
            .executeAsList()
    }

    override suspend fun addNote(noteItem: NoteItem) {
        queries.transaction {
            val isWeatherInfoAttached = noteItem.weatherInfo != null
            var weatherIndex = 0L
            if (isWeatherInfoAttached) {
                queries.insertWeatherInfo(
                    id = noteItem.weatherInfo?.id,
                    temperature = noteItem.weatherInfo!!.temperature,
                    weather_type = noteItem.weatherInfo.weatherType
                )
                weatherIndex = queries.getLastWeatherInfoIndex().executeAsOne()
            }
            queries.insertNote(
                id = noteItem.id,
                description = noteItem.description,
                timestamp = noteItem.timestamp.toString(),
                weather_id = if(isWeatherInfoAttached) {
                    weatherIndex
                } else {
                    null
                },
                location = noteItem.location
            )
            val noteIndex = queries.getLastNoteIndex().executeAsOne()
            noteItem.categories.forEach { category ->
                queries.insertNotesCategories(
                    note_id = noteIndex,
                    category_id = category.id
                )
            }
        }

    }
    override suspend fun getAllCategories(): List<CategoryEntity> {
         return withContext(Dispatchers.IO) {
            queries
                .getAllCategories()
                .executeAsList()
        }
    }

    override suspend fun addNewCategory(
        id: Long?,
        backgroundColor: String,
        name: String
    ) {
        withContext(Dispatchers.IO) {
            queries.addNewCategory(
                id = id,
                background_color = backgroundColor,
                name = name
            )
        }
    }

    override suspend fun deleteNote(id: Long) {
        queries.deleteNote(id)
    }

    override suspend fun getNotesByDateAndCategory(
        startDate: LocalDate,
        endDate: LocalDate,
        category: Category
    ): List<Note_view> {
        return withContext(Dispatchers.IO) {

            val result = queries
                .getNotesByDateAndCategory(
                    startDate = startDate.toString(),
                    endDate = endDate.toString(),
                    category = category.name
                )
                .executeAsList()

            Log.d("TAG", "getNotesByDateAndCategory onEvent: $result")
            result
        }
    }

    override suspend fun getNotesByDate(
        startDate: LocalDate,
        endDate: LocalDate
    ): List<Note_view> {
        return withContext(Dispatchers.IO) {
            val result = queries
                .getNotesByDates(
                    from = startDate.toString(),
                    to = endDate.toString()
                ).executeAsList()
            result
        }
    }

    override suspend fun getNotesByCategory(
        category: Category
    ): List<Note_view> {
        return withContext(Dispatchers.IO) {
            val result = queries
                .getNotesByCategory(
                    category = category.name
                ).executeAsList()
            result
        }
    }

}



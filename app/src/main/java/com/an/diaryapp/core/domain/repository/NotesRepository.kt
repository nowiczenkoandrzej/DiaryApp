package com.an.diaryapp.core.domain.repository

import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.core.domain.model.NoteItem
import com.an.diaryapp.core.domain.model.Resource
import com.an.diaryapp.core.domain.model.WeatherInfo
import java.time.LocalDate

interface NotesRepository {

    suspend fun getAllCategories(): Resource<List<Category>>

    suspend fun getNotes(content: String = ""): Resource<List<NoteItem>>

    suspend fun getNoteById(id: Long): Resource<NoteItem>

    suspend fun addNote(note: NoteItem)

    suspend fun deleteNote(id: Long)

    suspend fun getNoteFromDates(
        startDate: LocalDate,
        endDate: LocalDate
    ): Resource<List<NoteItem>>

}
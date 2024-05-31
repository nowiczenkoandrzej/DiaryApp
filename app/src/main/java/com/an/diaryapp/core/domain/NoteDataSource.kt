package com.an.diaryapp.core.domain

import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.core.domain.model.NoteItem
import com.an.diaryapp.core.domain.model.Resource
import com.an.diaryapp.feature_note_list.domain.model.FilterType
import diaryapp.db.CategoryEntity
import diaryapp.db.GetNotesByDateAndCategory
import diaryapp.db.GetNoteById
import diaryapp.db.GetNotesByCategory
import diaryapp.db.GetNotesByContent
import diaryapp.db.GetNotesByDates
import java.time.LocalDate


interface NoteDataSource {

    suspend fun getNoteById(id: Long): List<GetNoteById>?

    suspend fun getNotesByContent(content: String): List<GetNotesByContent>

    suspend fun addNote(noteItem: NoteItem)

    suspend fun getAllCategories(): List<CategoryEntity>

    suspend fun addNewCategory(
        id: Long? = null,
        backgroundColor: String = "0xFF6650a4",
        name: String
    )

    suspend fun deleteNote(id: Long)

    suspend fun getNotesByDateAndCategory(
        startDate: LocalDate,
        endDate: LocalDate,
        category: Category
    ): List<GetNotesByDateAndCategory>

    suspend fun getNotesByDate(
        startDate: LocalDate,
        endDate: LocalDate
    ) : List<GetNotesByDates>

    suspend fun getNotesByCategory(
        category: Category
    ): List<GetNotesByCategory>

}
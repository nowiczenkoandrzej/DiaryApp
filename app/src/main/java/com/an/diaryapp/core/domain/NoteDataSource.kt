package com.an.diaryapp.core.domain

import com.an.diaryapp.core.domain.model.NoteItem
import com.an.diaryapp.core.domain.model.Resource
import diaryapp.db.CategoryEntity
import diaryapp.db.GetNoteById
import diaryapp.db.GetNotesByContent
import diaryapp.db.GetNotesByDates
import java.time.LocalDate


interface NoteDataSource {

    suspend fun getNoteById(id: Long): List<GetNoteById>?

    suspend fun getNotesByContent(content: String): List<GetNotesByContent>

   // fun getNotesByCategory(id: Long): Flow<List<NoteEntity>>

    suspend fun addNote(noteItem: NoteItem)

    suspend fun getAllCategories(): List<CategoryEntity>

    suspend fun addNewCategory(
        id: Long? = null,
        backgroundColor: String = "0xFF6650a4",
        name: String
    )

    suspend fun deleteNote(id: Long)

    suspend fun getNoteFromDates(
        startDate: LocalDate,
        endDate: LocalDate
    ): List<GetNotesByDates>

}
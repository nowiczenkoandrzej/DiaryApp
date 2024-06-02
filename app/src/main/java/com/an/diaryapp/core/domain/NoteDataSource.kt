package com.an.diaryapp.core.domain

import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.core.domain.model.NoteItem
import diaryapp.db.CategoryEntity
import diaryapp.db.Note_view
import java.time.LocalDate


interface NoteDataSource {

    suspend fun getNoteById(id: Long): List<Note_view>?

    suspend fun getNotesByContent(content: String): List<Note_view>

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
    ): List<Note_view>

    suspend fun getNotesByDate(
        startDate: LocalDate,
        endDate: LocalDate
    ) : List<Note_view>

    suspend fun getNotesByCategory(
        category: Category
    ): List<Note_view>

}
package com.an.diaryapp.feature_note_list.domain.use_cases

import com.an.diaryapp.core.domain.NotesRepository
import com.an.diaryapp.core.domain.model.NoteItem
import com.an.diaryapp.core.domain.model.Resource

class GetNotes(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(
        content: String = ""
    ): List<NoteItem> {
        return when(val result = repository.getNotes(content)) {
            is Resource.Error -> emptyList()
            is Resource.Success -> result.data ?: emptyList()
        }
    }
}
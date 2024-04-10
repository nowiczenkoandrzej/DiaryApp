package com.an.diaryapp.feature_note_list.domain.use_cases

import com.an.diaryapp.core.domain.repository.NotesRepository

class RemoveNote(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(
        id: Long
    ) {
        repository.deleteNote(id)
    }

}
package com.an.diaryapp.feature_note_list.domain.model

import com.an.diaryapp.feature_note_list.domain.use_cases.GetNotes
import com.an.diaryapp.feature_note_list.domain.use_cases.RemoveNote

data class NoteListUseCases(
    val getNotes: GetNotes,
    val removeNote: RemoveNote
)
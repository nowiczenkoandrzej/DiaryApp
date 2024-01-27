package com.an.diaryapp.feature_note_list.domain.model

import com.an.diaryapp.core.domain.model.NoteItem

data class NoteListScreenState(
    val notes: List<NoteItem> = emptyList(),
    val searchBarText: String = ""
)

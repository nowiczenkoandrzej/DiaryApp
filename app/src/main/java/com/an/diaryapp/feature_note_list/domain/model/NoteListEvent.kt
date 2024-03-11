package com.an.diaryapp.feature_note_list.domain.model

import java.time.LocalDate

sealed class NoteListEvent{
    object GetNotes: NoteListEvent()
    data class RemoveNote(val noteId: Long): NoteListEvent()
    data class TypeSearchBar(val text: String): NoteListEvent()
    data class SelectFilterFromDate(val date: LocalDate): NoteListEvent()
    data class SelectFilterToDate(val date: LocalDate): NoteListEvent()
    object ActiveSearchBar: NoteListEvent()
}

package com.an.diaryapp.feature_note_list.domain.model

sealed class NoteListEvent{
    data class GetNotes(val content: String = ""): NoteListEvent()
    data class RemoveNote(val noteId: Long): NoteListEvent()
}

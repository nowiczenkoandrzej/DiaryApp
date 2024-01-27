package com.an.diaryapp.feature_add_note.domain.model

import com.an.diaryapp.core.domain.model.Category
import java.time.LocalDate

sealed class AddNoteEvent{
    object GetWeatherInfo: AddNoteEvent()
    object AddNote: AddNoteEvent()
    data class SelectCategory(val category: Category): AddNoteEvent()
    data class UnselectCategory(val category: Category): AddNoteEvent()
    data class SetDescription(val text: String): AddNoteEvent()
    data class SetTimeStamp(val date: LocalDate): AddNoteEvent()

}

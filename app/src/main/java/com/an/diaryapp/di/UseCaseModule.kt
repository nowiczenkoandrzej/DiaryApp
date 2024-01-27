package com.an.diaryapp.di

import com.an.diaryapp.core.domain.NotesRepository
import com.an.diaryapp.feature_note_list.domain.model.NoteListUseCases
import com.an.diaryapp.feature_note_list.domain.use_cases.GetNotes
import com.an.diaryapp.feature_note_list.domain.use_cases.RemoveNote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideNoteListUseCases(
        repository: NotesRepository
    ): NoteListUseCases = NoteListUseCases(
        getNotes = GetNotes(repository),
        removeNote = RemoveNote(repository)
    )

}
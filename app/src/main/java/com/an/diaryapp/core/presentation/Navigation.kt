package com.an.diaryapp.core.presentation

import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.an.diaryapp.core.domain.model.NOTE_ID
import com.an.diaryapp.core.domain.model.Screen
import com.an.diaryapp.feature_add_note.presentation.AddNoteScreen
import com.an.diaryapp.feature_add_note.presentation.AddNoteViewModel
import com.an.diaryapp.feature_note_details.presentation.DetailsScreen
import com.an.diaryapp.feature_note_details.presentation.DetailsViewModel
import com.an.diaryapp.feature_note_list.presentation.NotesListScreen
import com.an.diaryapp.feature_note_list.presentation.NotesListViewModel

@RequiresApi(34)
@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination =  Screen.DiaryNotesList.route
    ) {
        composable(route = Screen.DiaryNotesList.route) {

            val viewModel = hiltViewModel<NotesListViewModel>()

            NotesListScreen(
                viewModel = viewModel,
                navController = navController
            )

        }

        composable(route = Screen.AddNote.route) {

            val viewModel = hiltViewModel<AddNoteViewModel>()

            AddNoteScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen(navController = navController)

        }

        composable(
            route = Screen.NoteDetails.route,
            arguments = listOf(navArgument(NOTE_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong(NOTE_ID)
            val viewModel = hiltViewModel<DetailsViewModel>()

            viewModel.getNoteById(id!!)

            DetailsScreen(viewModel = viewModel)

        }
    }
}
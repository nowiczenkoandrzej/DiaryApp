package com.an.diaryapp.core.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.an.diaryapp.core.domain.model.Screen

data class BottomNavigationItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val route: String
) {
    companion object {
        val items = listOf(
            BottomNavigationItem(
                title = "Notes",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                route = Screen.DiaryNotesList.route
            ),
            BottomNavigationItem(
                title = "",
                selectedIcon = Icons.Filled.AddCircle,
                unselectedIcon = Icons.Outlined.Add,
                route = Screen.AddNote.route
            ),
            BottomNavigationItem(
                title = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings,
                route = Screen.Settings.route
            ),
        )
    }
}


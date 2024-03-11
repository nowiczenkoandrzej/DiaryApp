package com.an.diaryapp.core.presentation.components

import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.an.diaryapp.core.domain.BottomNavigationItem
import com.an.diaryapp.core.domain.model.Screen


@Composable
fun BottomNavBar(
    navController: NavController
) {

    val items = BottomNavigationItem.items

    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEachIndexed { index, item ->

            val isSelected = currentDestination?.route.equals(item.route)

            NavigationBarItem(
                selected = isSelected,
                label = {
                    if(item.title.isNotEmpty())
                        Text(text = item.title)
                },
                icon = {
                    Icon(
                        imageVector = if(isSelected) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = null
                    )
                },
                onClick = {
                    if(currentDestination?.route.equals(Screen.NoteDetails.route)
                        && item.route.equals(Screen.DiaryNotesList.route)
                    ) {
                        navController.popBackStack()
                    } else {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
            )
        }
    }

}

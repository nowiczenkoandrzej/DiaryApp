package com.an.diaryapp.feature_note_list.presentation

import android.util.Log
import android.util.Range
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.an.diaryapp.R
import com.an.diaryapp.core.domain.model.Screen
import com.an.diaryapp.feature_note_list.domain.model.NoteListEvent
import com.an.diaryapp.feature_note_list.presentation.components.AppBarTextField
import com.an.diaryapp.feature_note_list.presentation.components.ListCategory
import com.an.diaryapp.feature_note_list.presentation.components.DeleteNoteDialog
import com.an.diaryapp.feature_note_list.presentation.components.NoteListItem
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarView
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NotesListScreen(
    viewModel: NotesListViewModel,
    navController: NavController
) {

    val state = viewModel
        .screenState
        .collectAsState()
        .value

    var showDeleteNoteDialog by remember {
        mutableStateOf(false)
    }

    var deletedNoteId by remember {
        mutableLongStateOf(0)
    }

    val listState = rememberLazyListState()

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }


    val focusManager = LocalFocusManager.current

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val mainCalendarState = rememberSheetState()

    val selectedDateRange = remember {
        val value = Range(LocalDate.now().minusDays(7), LocalDate.now())
        mutableStateOf(value)
    }

    val lifecycleState by LocalLifecycleOwner
        .current
        .lifecycle
        .currentStateFlow
        .collectAsState()

    val topAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        scrolledContainerColor = MaterialTheme.colorScheme.primary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary
    )


    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()




    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {
                viewModel.onEvent(NoteListEvent.GetNotes)
            }

            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                viewModel.onEvent(NoteListEvent.GetNotes)
            }
        }
    }
    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) {

            focusManager.clearFocus(true)
        }
    }

    if (showDeleteNoteDialog) {
        DeleteNoteDialog(
            onDismissRequest = {
                showDeleteNoteDialog = false
                deletedNoteId = 0
            },
            onConfirmation = {
                viewModel.onEvent(NoteListEvent.RemoveNote(deletedNoteId))

                showDeleteNoteDialog = false
                deletedNoteId = 0
            },
            dialogTitle = "Do you want to delete that note?",
            icon = Icons.Default.Delete
        )
    }


    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.primary,
                        thickness = 1.dp,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "Date range",
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                    )

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.primary,
                        thickness = 1.dp,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))


                CalendarView(
                    sheetState = mainCalendarState,
                    config = CalendarConfig(
                        style = CalendarStyle.MONTH
                    ),
                    selection = CalendarSelection.Period(
                        selectedRange = selectedDateRange.value
                    ) { startDate, endDate ->
                        selectedDateRange.value = Range(startDate, endDate)
                    }
                )

                CalendarView(
                    sheetState = mainCalendarState,
                    config = CalendarConfig(
                        style = CalendarStyle.MONTH
                    ),
                    selection = CalendarSelection.Period(
                        selectedRange = selectedDateRange.value
                    ) { startDate, endDate ->
                        selectedDateRange.value = Range(startDate, endDate)
                    }
                )


                Spacer(modifier = Modifier.height(40.dp))

            }
        },
        sheetPeekHeight = 0.dp

    ) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = topAppBarColors,
                title = {
                    AppBarTextField(
                        value = state.searchBarText,
                        onValueChange = { newText ->
                            viewModel.onEvent(NoteListEvent.TypeSearchBar(newText))
                        },
                        hint = "Search...",
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                actions = {
                    IconButton(onClick = {
                        //isSheetOpen = true
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_filter_alt_24),
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            val groupedByYear = state.notes.groupBy { it.timestamp.year }


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                state = listState
            ) {

                groupedByYear.forEach { year, notes ->
                    val groupByMonth = notes.groupBy { it.timestamp.month }

                    groupByMonth.forEach { month, notes ->
                        stickyHeader {
                            ListCategory(
                                title = "${month.toString()} ${notes.first().timestamp.year}",
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.primaryContainer
                                    )
                                    .fillMaxWidth()
                                    .height(48.dp),
                            )
                        }
                        items(notes) { note ->
                            NoteListItem(
                                noteItem = note,
                                onClick = {
                                    navController.navigate(
                                        Screen.NoteDetails.passId(note.id!!)
                                    )
                                },
                                onLongClick = {
                                    showDeleteNoteDialog = true
                                    deletedNoteId = note.id!!
                                }
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }
            }


        }
        /*if(isSheetOpen) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { isSheetOpen = false },
                dragHandle = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Filters",
                            fontSize = 24.sp
                        )
                    }
                }
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.primary,
                            thickness = 1.dp,
                            modifier = Modifier.weight(1f)
                        )

                        Text(
                            text = "Date range",
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp)
                        )

                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.primary,
                            thickness = 1.dp,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    CalendarView(
                        sheetState = mainCalendarState,
                        config = CalendarConfig(
                            style = CalendarStyle.MONTH
                        ),
                        selection = CalendarSelection.Period(
                            selectedRange = selectedDateRange.value
                        ) { startDate, endDate ->
                            selectedDateRange.value = Range(startDate, endDate)
                        }
                    )

                    CalendarView(
                        sheetState = mainCalendarState,
                        config = CalendarConfig(
                            style = CalendarStyle.MONTH
                        ),
                        selection = CalendarSelection.Period(
                            selectedRange = selectedDateRange.value
                        ) { startDate, endDate ->
                            selectedDateRange.value = Range(startDate, endDate)
                        }
                    )


                    Spacer(modifier = Modifier.height(40.dp))

                }
            }
        }*/
    }
}

}

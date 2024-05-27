package com.an.diaryapp.feature_settings.presentation.components

import android.location.Location
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.an.diaryapp.R
import com.an.diaryapp.feature_settings.domain.model.SettingsLocationState
import com.an.diaryapp.feature_settings.domain.model.SettingsNotificationState
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPicker(
    modifier: Modifier,
    state: SettingsLocationState,
    onMapClick: (location: Location) -> Unit,
    onSaveLocationButtonClick: () -> Unit,
    onSwitchChecked: (isChecked: Boolean) -> Unit,
    onGetLocation: () -> Unit,


) {




    val location = LatLng(
        state.defaultLocationLat,
        state.defaultLocationLong
    )

    Log.d("TAG", "LocationPicker: $location")


    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 10f)
    }




    val markerState = MarkerState(position = location)

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val sheetScope = rememberCoroutineScope()

    var showBottomSheet by remember { mutableStateOf(false) }



    LaunchedEffect(
        state.defaultLocationLat,
        state.defaultLocationLong,
        showBottomSheet
        ) {
        if(showBottomSheet) {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newCameraPosition(
                    CameraPosition(location, 12f, 0f, 0f)
                ),
                durationMs = 500
            )
        }

    }

    if(showBottomSheet) {
        sheetScope.launch {
            sheetState.expand()
        }
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    cameraPositionState = cameraPositionState,
                    onMapClick = {
                        markerState.position = it

                        val newLocation = Location("google_map")
                        newLocation.latitude = it.latitude
                        newLocation.longitude = it.longitude

                        onMapClick(newLocation)

                    }
                ) {
                    Marker(
                        state = markerState,
                        title = "Default Location"
                    )

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Default Location: ${state.defaultLocation}")
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = {
                        onSaveLocationButtonClick()
                        sheetScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if(!sheetState.isVisible)
                                showBottomSheet = false
                            onSaveLocationButtonClick()
                        }
                    }) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            Switch(
                checked = state.isSwitchChecked,
                onCheckedChange =  { isChecked ->
                    onSwitchChecked(isChecked)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Default Weather Location")
        }
        AnimatedVisibility(visible = state.isSwitchChecked) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val defaultLocationName = if(state.isDefaultLocationPicked) {
                    state.defaultLocation
                } else {
                    "..."
                }

                Text(text = "Default Location: $defaultLocationName")
                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = {
                    showBottomSheet = true
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_map_24),
                        contentDescription = null
                    )
                }
                IconButton(onClick = {
                    onGetLocation()
                }) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null
                    )
                }
            }
        }


    }





}
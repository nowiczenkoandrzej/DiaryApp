package com.an.diaryapp.feature_settings.presentation.components

import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.an.diaryapp.feature_settings.domain.model.SettingsScreenState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun LocationPicker(
    modifier: Modifier,
    state: SettingsScreenState,
    onMapClick: (location: Location) -> Unit,
    onSaveButtonClick: () -> Unit
) {

    val location = LatLng(
        state.defaultLocationLat,
        state.defaultLocationLong
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 10f)
    }

    val markerState = MarkerState(position = location)

    Column(
        modifier = modifier
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
                onSaveButtonClick()
            }) {
                Text(text = "Save")
            }
        }
    }


}
package com.an.diaryapp.feature_settings.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.an.diaryapp.feature_settings.domain.model.AppSettings
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun LocationPicker(
    modifier: Modifier,
    state: AppSettings
) {

    val location = LatLng(
        state.defaultLocationLat,
        state.defaultLocationLong
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 10f)
    }

    Column(
        modifier = modifier
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = location),
                title = "Default Location"
            )

        }
    }


}
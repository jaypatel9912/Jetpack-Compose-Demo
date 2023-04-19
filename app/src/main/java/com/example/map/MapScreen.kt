package com.example.map

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.ThemeCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.map.ui.theme.MapTheme
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
//import com.google.android.libraries.maps.CameraUpdateFactory
//import com.google.android.libraries.maps.MapView
//import com.google.android.libraries.maps.OnMapReadyCallback
//import com.google.android.libraries.maps.model.LatLng
//import com.google.android.libraries.maps.model.MarkerOptions
//import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Map() {

    val context = LocalContext.current
    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    var properties by remember { mutableStateOf(MapProperties(mapType = MapType.SATELLITE)) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(1000.dp)
    ) {
        val singapore = LatLng(1.35, 103.87)
        val switzerland = LatLng(46.8182, 8.2275)
        val sentosa = LatLng(1.2494, 103.8303)
        val sunglade = LatLng(1.3490, 103.8712)

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(singapore, 17f)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = singapore),
                title = "Singapore",
                snippet = "Marker in Singapore",
                icon = bitmapDescriptor(context, R.drawable.car3)
            )
            Marker(
                state = MarkerState(position = sentosa),
                title = "Sentosa",
                snippet = "Marker in Sentosa"
            )
            Polyline(
                points = listOf(
                    LatLng(1.35, 103.87),
                    LatLng(1.3490, 103.8712)
                ), color = Color.Blue
            )
        }
        Switch(
            checked = uiSettings.zoomControlsEnabled,
            onCheckedChange = { uiSettings = uiSettings.copy(zoomControlsEnabled = it) })
    }
}

fun bitmapDescriptor(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {

    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

@Preview(showBackground = true)
@Composable
fun MapPreview() {
    //mapUi()
}
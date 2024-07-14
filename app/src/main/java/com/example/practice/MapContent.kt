package com.example.practice

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.util.FusedLocationSource

@SuppressLint("RememberReturnType")
@Composable
fun NaverMapComposable() {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    DisposableEffect(Unit) {
        mapView.onCreate(null)
        mapView.getMapAsync { naverMap ->
            setupNaverMap(naverMap)
        }
        onDispose {
            mapView.onDestroy()
        }
    }

    AndroidView(
        factory = { mapView },
        update = { view ->
            mapView.onResume()
        },
        modifier = Modifier.fillMaxSize()
    )
}

private fun setupNaverMap(naverMap: NaverMap) {
    naverMap.uiSettings.isLocationButtonEnabled = true
}

@Composable
fun MapContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("지도 내용")
        NaverMapComposable()
    }
}
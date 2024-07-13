package com.example.practice

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practice.ui.theme.PracticeTheme
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.ui.viewinterop.AndroidView
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.util.FusedLocationSource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

data class PopupStoreInfo(
    val title: String,
    val date: String,
    val location: String,
    val details: String
)

data class BottomNavigationItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean = false,
    val badgeCount: Int? = null
)

class MainActivity : ComponentActivity() {
    private lateinit var locationSource: FusedLocationSource
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            PracticeTheme {
                PopupStoreScreen()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                Log.e("MainActivity", "위치 권한이 거부되었습니다.")
            }
            return
        }
    }
}

@Composable
fun PopupStoreScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("상세정보", "지도", "후기")
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    BottomNavigationItem(
                        selectedIcon = Icons.Filled.Search,
                        unselectedIcon = Icons.Outlined.Search
                    ),
                    BottomNavigationItem(
                        selectedIcon = Icons.Filled.Place,
                        unselectedIcon = Icons.Outlined.Place
                    ),
                    BottomNavigationItem(
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home
                    ),
                    BottomNavigationItem(
                        selectedIcon = Icons.Filled.FavoriteBorder,
                        unselectedIcon = Icons.Outlined.FavoriteBorder
                    ),
                    BottomNavigationItem(
                        selectedIcon = Icons.Filled.AccountCircle,
                        unselectedIcon = Icons.Outlined.AccountCircle
                    )
                )
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = { selectedItemIndex = index },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (item.badgeCount != null) {
                                        Badge { Text(text = item.badgeCount.toString()) }
                                    } else if (item.hasNews) {
                                        Badge()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (selectedItemIndex == index) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // 상단 이미지와 로고
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "팝업스토어 로고")
            }

            // 탭
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            // 선택된 탭에 따른 내용 표시
            when (selectedTab) {
                0 -> DetailContent()
                1 -> MapContent()
                2 -> ReviewContent()
            }
        }
    }
}

fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}



private const val LOCATION_PERMISSION_REQUEST_CODE = 1000


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
fun DetailContent() {

    val context = LocalContext.current

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_instagram),
            contentDescription = "Instagram",
            modifier = Modifier
                .size(40.dp)
                .clickable { openUrl(context, "https://www.instagram.com") }
        )
        Image(
            painter = painterResource(id = R.drawable.ic_naver),
            contentDescription = "Naver",
            modifier = Modifier
                .size(40.dp)
                .clickable { openUrl(context, "https://www.naver.com") }
        )
    }

    val info = PopupStoreInfo(
        title = "짱구는 못말려 짱구는 여행중!",
        date = "24.06.05 ~ 24.06.16",
        location = "서울 롯데월드몰",
        details = """
            짱구는 여행중!
            24년 짱구는 못말려 첫 팝업스토어!
            In 잠실 롯데월드몰! 운영중지 🌞
            운영 방식: 오전 9시부터 1층 12번 게이트, 유니클로 옆(외부)
            10시 30분부터 1층 짱구 팝업스토어 입구에서 등록 가능
        """.trimIndent()
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text(info.title, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("기간: ${info.date}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Text("장소: ${info.location}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(info.details, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun MapContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("지도 내용")
        NaverMapComposable()
    }
}

@Composable
fun ReviewContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("후기 내용")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPopupStoreScreen() {
    PracticeTheme {
        PopupStoreScreen()
    }
}

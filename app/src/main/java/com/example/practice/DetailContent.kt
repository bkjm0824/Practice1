package com.example.practice

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

@Composable
fun DetailContent() {
    val context = LocalContext.current

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
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            ㅐ
            
        """.trimIndent()
    )

    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
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

        // 고정된 내용
        Column(modifier = Modifier.padding(16.dp)) {
            Text(info.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text("기간: ${info.date}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text("장소: ${info.location}", style = MaterialTheme.typography.bodyLarge)
        }

        // 스크롤 가능한 내용
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(info.details, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailContent() {
    DetailContent()
}
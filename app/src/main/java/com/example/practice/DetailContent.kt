package com.example.practice

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

@Composable
fun DetailContent() {
    val context = LocalContext.current
    var likeCount by remember { mutableStateOf(0) }

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
        """.trimIndent()
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorite",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { likeCount++ }
                        )
                        Text(text = likeCount.toString())
                    }
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { shareUrl(context, "https://www.naver.com") }
                    )
                }
            }

            // 고정된 내용
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(info.title, style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "캐릭터",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
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
}

fun shareUrl(context: Context, url: String) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(shareIntent, null))
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailContent() {
    DetailContent()
}

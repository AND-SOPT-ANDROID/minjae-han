package org.sopt.and

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.and.ui.theme.ANDANDROIDTheme

class MyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ANDANDROIDTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyPageScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MyPageScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.DarkGray)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.Cyan, shape = CircleShape)
                ) {}
                Spacer(modifier=Modifier.width(10.dp))
                Text(
                    "프로필 1님",
                    fontSize = 15.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { },
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "알림",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { },
                    imageVector = Icons.Default.Settings,
                    contentDescription = "설정",
                    tint = Color.White
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(15.dp)
        ) {
            Text(
                text = "첫 결재 시 첫 달 100원!",
                fontSize = 18.sp,
                color = Color.LightGray)
            Text(
                modifier = Modifier.clickable { },
                text = "구매하기 >",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(1.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(15.dp)
        ) {
            Text(
                text = "현재 보유하신 이용권이 없습니다.",
                fontSize = 18.sp,
                color = Color.LightGray)
            Text(
                modifier = Modifier.clickable { },
                text = "구매하기 >",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Text(
            modifier = Modifier.padding(15.dp),
            text = "전체 시청내역",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Icon(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterHorizontally)
                .clickable { },
            imageVector = Icons.Outlined.Warning,
            contentDescription = "알림",
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "시청 내역이 없어요.",
            color = Color.Gray
        )

        Text(
            modifier = Modifier.padding(15.dp),
            text = "관심 프로그램",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Icon(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterHorizontally)
                .clickable { },
            imageVector = Icons.Outlined.Warning,
            contentDescription = "알림",
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "관심 프로그램이 없어요.",
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyPageScreenPreview() {
    MyPageScreen()
}
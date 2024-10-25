package org.sopt.and

import android.os.Bundle
import android.view.Display.Mode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.and.ui.theme.ANDANDROIDTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ANDANDROIDTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.home_top_bar), // 이미지 리소스를 사용
            contentDescription = "Top Bar Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // 탑바 추가
        HomeTopBar()

        // 나머지 홈 화면 요소
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // 최상단 배너뷰
            item {
                BannerView()
            }

            // 믿고 보는 웨이브 에디터 추천작
            item {
                SectionTitle("믿고 보는 웨이브 에디터 추천작")
                EditorPicksList()
            }

            // 오늘의 TOP 20
            item {
                SectionTitle("오늘의 TOP 20")
                Top20List()
            }
        }
    }
}

@Composable
fun BannerView() {
    // 가로 스크롤 배너
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp), // 좌우 패딩을 추가하여 배너가 살짝 보이도록 설정
        horizontalArrangement = Arrangement.spacedBy(8.dp) // 배너 간의 간격 설정
    ) {
        items(6) { index -> // 6개의 배너 이미지 생성
            Box(
                modifier = Modifier
                    .width(320.dp) // 배너의 가로 크기 설정 (화면보다 작게)
                    .height(400.dp) // 배너 높이 설정
            ) {
                Image(
                    painter = painterResource(id = R.drawable.banner_image), // 이미지 리소스 사용
                    contentDescription = "Banner $index",
                    contentScale = ContentScale.Crop, // 이미지 비율 유지하며 화면에 맞추기
                    modifier = Modifier
                        .fillMaxWidth() // 배너 전체를 채우기
                        .fillMaxHeight() // 세로로 배너 전체를 채우기
                )
            }
        }
    }
}



@Composable
fun EditorPicksList() {
    // 가로 스크롤 가능한 추천작 리스트
    LazyRow {
        items(5) { // 임시로 5개의 항목 생성
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(150.dp, 200.dp)
                    .background(Color.LightGray) // 이미지 대신 배경색 설정
            ) {
                Text(
                    text = "추천작 $it",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Black // 글자색을 검정색으로 설정
                )
            }
        }
    }
}

@Composable
fun Top20List() {
    // 가로 스크롤 가능한 TOP 20 리스트
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp,vertical = 16.dp), // 좌우 패딩 추가
        horizontalArrangement = Arrangement.spacedBy(8.dp) // 항목 사이 간격 설정
    ) {
        items(20) { index -> // 임시로 20개의 항목 생성
            Box(
                modifier = Modifier
                    .width(180.dp) // 가로 크기를 더 크게 설정
                    .height(250.dp) // 세로 크기를 더 크게 설정
                    .background(Color.DarkGray) // 이미지 대신 배경색 설정
            ) {
                Text(
                    text = "Top $index",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White // 글자색을 흰색으로 설정
                )
            }
        }
    }
}


@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier
            .padding(15.dp)
            .background(Color.Black), // 배경색 검정으로 설정
        color = Color.White // 글자색 흰색으로 설정
    )
}

// 프리뷰
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

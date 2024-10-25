package org.sopt.and

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.sopt.and.ui.theme.ANDANDROIDTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ANDANDROIDTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavBar(navController = navController)
                    },
                    content = { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = BottomNavItem.Home.route
                        ) {
                            composable(BottomNavItem.Home.route) {
                                HomeScreen(Modifier.padding(innerPadding))
                            }
                        }
                    }
                )
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
            painter = painterResource(id = R.drawable.home_top_bar),
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
        HomeTopBar()

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                BannerView()
            }

            item {
                SectionTitle("믿고 보는 웨이브 에디터 추천작")
                EditorPicksList()
            }

            item {
                SectionTitle("오늘의 TOP 20")
                Top20List()
            }
        }
    }
}

@Composable
fun BannerView() {
    val images = listOf(
        R.drawable.banner_image,
        R.drawable.banner_image,
        R.drawable.banner_image,
        R.drawable.banner_image
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        BannerViewPager(images = images)
    }
}

@Composable
fun BannerViewPager(
    modifier: Modifier = Modifier,
    images: List<Int>
) {
    val pagerState = rememberPagerState(pageCount = { images.size })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
    ) { page ->
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(450.dp)
        ) {
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            // 현재 페이지 위치 표시
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text(
                    text = "${page + 1}/${images.size}",
                    color = Color.White,
                    modifier = Modifier
                        .background(
                            color = Color.Black.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun EditorPicksList() {
    LazyRow {
        items(5) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(150.dp, 200.dp)
                    .background(Color.LightGray)
            ) {
                Text(
                    text = "추천작 $it",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun Top20List() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(20) { index ->
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(250.dp)
                    .background(Color.DarkGray)
            ) {
                Text(
                    text = "Top $index",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
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
            .background(Color.Black),
        color = Color.White
    )
}

@Composable
fun PreviewNavHost() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding) // innerPadding이 프리뷰에서도 동작하도록 설정
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewBottomNavWithNavHost() {
    PreviewNavHost() // 프리뷰에서 네비게이션과 함께 보도록 설정
}

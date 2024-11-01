package org.sopt.and.presentation.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.and.R

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
                .height(100.dp),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
fun BannerView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        BannerViewPager()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerViewPager(
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState { 4 }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(450.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.banner_image),
                contentDescription = "Banner Image $page",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text(
                    text = "${page + 1}/4",
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
        items(6) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(150.dp, 200.dp)
                    .background(Color.LightGray)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.editor_image),
                    contentDescription = "Editor Pick",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentScale = ContentScale.Crop
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
                Image(
                    painter = painterResource(id = R.drawable.top20),
                    contentDescription = "Top $index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentScale = ContentScale.Crop
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
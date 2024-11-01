package org.sopt.and.presentation.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
fun PageNumber(
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = "$currentPage / $totalPages",
        color = Color.White,
        fontSize = 16.sp,
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.5f))
            .padding(4.dp)
    )
}

@Composable
fun BannerView(bannerImages: List<Int>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        BannerViewPager(bannerImages)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerViewPager(
    bannerImages: List<Int>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState { bannerImages.size }

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
            ) {
                Image(
                    painter = painterResource(id = bannerImages[page]),
                    contentDescription = "Banner Image $page",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(10.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }

        PageNumber(
            currentPage = pagerState.currentPage + 1,
            totalPages = pagerState.pageCount,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }
}

@Composable
fun EditorPicksList(editorPicks: List<String>) {
    LazyRow {
        items(editorPicks.size) { index ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(150.dp, 200.dp)
                    .background(Color.LightGray)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.editor_image),
                    contentDescription = "Editor Pick ${editorPicks[index]}",
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
fun Top20List(top20Items: List<String>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(top20Items.size) { index ->
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(250.dp)
                    .background(Color.DarkGray)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.top20),
                    contentDescription = top20Items[index],
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
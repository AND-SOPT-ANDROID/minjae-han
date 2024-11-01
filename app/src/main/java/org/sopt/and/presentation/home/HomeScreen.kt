package org.sopt.and.presentation.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.and.presentation.home.component.BannerView
import org.sopt.and.presentation.home.component.EditorPicksList
import org.sopt.and.presentation.home.component.HomeTopBar
import org.sopt.and.presentation.home.component.SectionTitle
import org.sopt.and.presentation.home.component.Top20List

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by homeViewModel.uiState.collectAsState()


    BackHandler {
        (context as? Activity)?.finish()
    }

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
                BannerView(uiState.bannerImages)
            }

            item {
                SectionTitle("믿고 보는 웨이브 에디터 추천작")
                EditorPicksList(uiState.editorPicks)
            }

            item {
                SectionTitle("오늘의 TOP 20")
                Top20List(uiState.top20Items)
            }
        }
    }
}
@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()

}
package org.sopt.and.presentation.home

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.and.presentation.home.component.BannerView
import org.sopt.and.presentation.home.component.EditorPicksList
import org.sopt.and.presentation.home.component.HomeTopBar
import org.sopt.and.presentation.home.component.SectionTitle
import org.sopt.and.presentation.home.component.Top20List

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    // Effect handling
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                HomeEffect.NavigateToDetail -> {
                    // Handle navigation
                }
            }
        }
    }

    // Back handler
    BackHandler {
        (context as? Activity)?.finish()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        HomeTopBar()

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(),
                color = Color.White
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    BannerView(state.bannerImages)
                }

                item {
                    SectionTitle("믿고 보는 웨이브 에디터 추천작")
                    EditorPicksList(state.editorPicks)
                }

                item {
                    SectionTitle("오늘의 TOP 20")
                    Top20List(state.top20Items)
                }
            }
        }
    }
}

package org.sopt.and.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    Text(
        text = "검색 화면",
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen()
}
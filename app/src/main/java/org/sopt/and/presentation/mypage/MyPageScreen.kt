package org.sopt.and.presentation.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.and.data.local.AuthLocalDataSource
import org.sopt.and.presentation.mypage.component.EmptyInfoIcon
import org.sopt.and.presentation.mypage.component.MyMenuSection
import org.sopt.and.presentation.mypage.component.MyPageTopBar
import org.sopt.and.presentation.mypage.component.PurchaseText

@Composable
fun MyPageScreen(
    myPageViewModel: MyPageViewModel = viewModel(
        factory = MyPageViewModel.provideFactory(
            authLocalDataSource = AuthLocalDataSource.getInstance(LocalContext.current)
        )
    ),
    modifier: Modifier = Modifier
) {
    val uiState by myPageViewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackBarHostState.showSnackbar(message)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        MyPageTopBar(
            hobby = uiState.hobby,
            isLoading = uiState.isLoading
        )

        Spacer(modifier = Modifier.height(1.dp))

        PurchaseText(
            title = "첫 결재 시 첫 달 100원!",
            onClick = { }
        )

        Spacer(modifier = Modifier.height(1.dp))

        PurchaseText(
            title = "현재 보유하신 이용권이 없습니다.",
            onClick = { }
        )

        MyMenuSection(text = "전체 시청내역")

        Spacer(modifier = Modifier.height(30.dp))

        EmptyInfoIcon(message = "시청 내역이 없어요.")

        MyMenuSection(text = "관심 프로그램")

        Spacer(modifier = Modifier.height(30.dp))

        EmptyInfoIcon(message = "관심 프로그램이 없어요.")
    }

    SnackbarHost(
        hostState = snackBarHostState,
        modifier = Modifier.padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {
    MyPageScreen()
}
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.sopt.and.presentation.mypage.component.EmptyInfoIcon
import org.sopt.and.presentation.mypage.component.MyMenuSection
import org.sopt.and.presentation.mypage.component.MyPageTopBar
import org.sopt.and.presentation.mypage.component.PurchaseText

@Composable
fun MyPageScreen(
    viewModel: MyPageViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    // Effect handling
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MyPageEffect.ShowError -> {
                    snackBarHostState.showSnackbar(effect.message)
                }
                MyPageEffect.NavigateToPurchase -> {
                    // Handle navigation to purchase
                }
                MyPageEffect.NavigateToNotifications -> {
                    // Handle navigation to notifications
                }
                MyPageEffect.NavigateToSettings -> {
                    // Handle navigation to settings
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        MyPageTopBar(
            hobby = state.hobby,
            isLoading = state.isLoading,
        )

        Spacer(modifier = Modifier.height(1.dp))

        PurchaseText(
            title = "첫 결재 시 첫 달 100원!",
            onClick = { viewModel.processIntent(MyPageIntent.OnPurchaseClick) }
        )

        Spacer(modifier = Modifier.height(1.dp))

        PurchaseText(
            title = "현재 보유하신 이용권이 없습니다.",
            onClick = { viewModel.processIntent(MyPageIntent.OnPurchaseClick) }
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

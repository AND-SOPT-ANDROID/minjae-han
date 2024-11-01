package org.sopt.and.presentation.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.and.presentation.mypage.component.EmptyInfoIcon
import org.sopt.and.presentation.mypage.component.MyMenuSection
import org.sopt.and.presentation.mypage.component.MyPageTopBar
import org.sopt.and.presentation.mypage.component.PurchaseText

@Composable
fun MyPageScreen(
    myPageViewModel: MyPageViewModel = viewModel(),
    modifier: Modifier = Modifier,
    email: String
) {
    val uiState by myPageViewModel.uiState.collectAsState()

    myPageViewModel.updateEmail(email)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        MyPageTopBar(email = uiState.email)

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
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {
    MyPageScreen(email = "wavve@example.com")
}
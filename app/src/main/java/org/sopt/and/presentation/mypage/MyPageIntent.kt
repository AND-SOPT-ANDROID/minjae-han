package org.sopt.and.presentation.mypage

sealed interface MyPageIntent {
    data object LoadMyHobby : MyPageIntent
    data object RefreshData : MyPageIntent
    data object OnPurchaseClick : MyPageIntent
}

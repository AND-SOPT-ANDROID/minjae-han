package org.sopt.and.presentation.mypage

sealed interface MyPageEffect {
    data class ShowError(val message: String) : MyPageEffect
    data object NavigateToPurchase : MyPageEffect
    data object NavigateToNotifications : MyPageEffect
    data object NavigateToSettings : MyPageEffect
}

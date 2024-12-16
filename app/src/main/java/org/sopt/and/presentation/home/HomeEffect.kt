package org.sopt.and.presentation.home

sealed interface HomeEffect {
    data class ShowError(val message: String) : HomeEffect
    data object NavigateToDetail : HomeEffect
}

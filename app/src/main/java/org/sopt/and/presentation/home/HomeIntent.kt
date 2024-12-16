package org.sopt.and.presentation.home

sealed interface HomeIntent {
    data object LoadInitialData : HomeIntent
    data object RefreshContent : HomeIntent
}

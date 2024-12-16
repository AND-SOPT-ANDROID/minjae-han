package org.sopt.and.presentation.home

data class HomeState(
    val bannerImages: List<Int> = emptyList(),
    val editorPicks: List<String> = emptyList(),
    val top20Items: List<String> = emptyList(),
    val isLoading: Boolean = false
)

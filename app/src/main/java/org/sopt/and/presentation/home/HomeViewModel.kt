package org.sopt.and.presentation.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.sopt.and.R

data class HomeUiState(
    val bannerImages: List<Int> = listOf(),
    val editorPicks: List<String> = listOf(),
    val top20Items: List<String> = listOf()
)

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadBannerImages()
        loadEditorPicks()
        loadTop20Items()
    }

    private fun loadBannerImages() {
        val images = listOf(
            R.drawable.banner_image,
            R.drawable.banner_image,
            R.drawable.banner_image,
            R.drawable.banner_image
        )
        _uiState.value = _uiState.value.copy(bannerImages = images)
    }

    private fun loadEditorPicks() {
        val picks = List(5) { "추천작 $it" }
        _uiState.value = _uiState.value.copy(editorPicks = picks)
    }

    private fun loadTop20Items() {
        val items = List(20) { "Top $it" }
        _uiState.value = _uiState.value.copy(top20Items = items)
    }
}
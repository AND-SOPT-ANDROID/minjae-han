package org.sopt.and.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.and.R
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _effect = Channel<HomeEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        processIntent(HomeIntent.LoadInitialData)
    }

    fun processIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadInitialData -> loadInitialData()
            HomeIntent.RefreshContent -> refreshContent()
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val images = listOf(
                R.drawable.banner_image,
                R.drawable.banner_image,
                R.drawable.banner_image,
                R.drawable.banner_image
            )
            val picks = List(5) { "추천작 $it" }
            val items = List(20) { "Top $it" }

            _state.update { it.copy(
                bannerImages = images,
                editorPicks = picks,
                top20Items = items,
                isLoading = false
            ) }
        }
    }

    private fun refreshContent() {
        loadInitialData()
    }
}

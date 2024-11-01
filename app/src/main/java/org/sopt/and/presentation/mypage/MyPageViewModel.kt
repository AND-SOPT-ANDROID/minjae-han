package org.sopt.and.presentation.mypage

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MyPageUiState(
    val email: String = "",
    val hasTicket: Boolean = false,
    val hasWatchHistory: Boolean = false,
    val hasInterestProgram: Boolean = false
)

class MyPageViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }
}
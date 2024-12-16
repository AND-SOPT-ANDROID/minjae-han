package org.sopt.and.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.and.domain.usecase.auth.GetMyHobbyUseCase
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getMyHobbyUseCase: GetMyHobbyUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    data class MyPageUiState(
        val hobby: String = "",
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    init {
        fetchMyHobby()
    }

    private fun fetchMyHobby() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getMyHobbyUseCase()
                .onSuccess { hobby ->
                    _uiState.update { it.copy(hobby = hobby) }
                }
                .onFailure { exception ->
                    _uiState.update { it.copy(errorMessage = exception.message) }
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}

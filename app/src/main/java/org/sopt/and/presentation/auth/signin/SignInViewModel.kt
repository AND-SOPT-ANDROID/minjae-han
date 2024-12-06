package org.sopt.and.presentation.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.and.domain.usecase.auth.SignInUseCase
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    data class SignInUiState(
        val username: String = "",
        val password: String = "",
        val showPassword: Boolean = false,
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val isSuccess: Boolean = false
    )

    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(
            username = username,
            errorMessage = null
        ) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(
            password = password,
            errorMessage = null
        ) }
    }

    fun onPasswordVisibilityChange() {
        _uiState.update { it.copy(showPassword = !it.showPassword) }
    }

    fun signIn() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            signInUseCase(
                username = _uiState.value.username,
                password = _uiState.value.password
            ).onSuccess {
                _uiState.update { it.copy(isSuccess = true) }
            }.onFailure { exception ->
                _uiState.update { it.copy(errorMessage = exception.message) }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}

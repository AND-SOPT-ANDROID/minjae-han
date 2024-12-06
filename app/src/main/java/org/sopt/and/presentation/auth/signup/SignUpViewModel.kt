package org.sopt.and.presentation.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.and.domain.entity.User
import org.sopt.and.domain.usecase.auth.SignUpUseCase
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    data class SignUpUiState(
        val username: String = "",
        val password: String = "",
        val hobby: String = "",
        val showPassword: Boolean = false,
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val isSuccess: Boolean = false
    )

    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(username = username, errorMessage = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, errorMessage = null) }
    }

    fun onHobbyChange(hobby: String) {
        _uiState.update { it.copy(hobby = hobby, errorMessage = null) }
    }

    fun onPasswordVisibilityChange() {
        _uiState.update { it.copy(showPassword = !it.showPassword) }
    }

    fun signUp() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val user = User(
                username = _uiState.value.username,
                password = _uiState.value.password,
                hobby = _uiState.value.hobby
            )
            signUpUseCase(user)
                .onSuccess {
                    _uiState.update { it.copy(isSuccess = true) }
                }
                .onFailure { exception ->
                    _uiState.update { it.copy(errorMessage = exception.message) }
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}

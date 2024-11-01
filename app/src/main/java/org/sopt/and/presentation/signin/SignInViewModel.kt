package org.sopt.and.presentation.signin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.sopt.and.domain.User

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val registeredUser: User? = null,
    val errorMessage: String? = null
)

class SignInViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun updateRegisteredUser(email: String, password: String) {
        _uiState.update { currentState ->
            currentState.copy(
                registeredUser = User(email, password),
                email = email,
                password = password
            )
        }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(
            email = email,
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

    fun signIn(email: String, password: String): Boolean {
        val currentState = _uiState.value

        if (email.isBlank() || password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "이메일과 비밀번호를 입력해주세요.") }
            return false
        }

        return if (currentState.registeredUser != null) {
            if (email == currentState.registeredUser.email &&
                password == currentState.registeredUser.password) {
                true
            } else {
                _uiState.update { it.copy(errorMessage = "이메일 또는 비밀번호가 일치하지 않습니다.") }
                false
            }
        } else {
            _uiState.update { it.copy(errorMessage = "등록되지 않은 사용자입니다.") }
            false
        }
    }
}
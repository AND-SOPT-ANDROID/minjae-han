package org.sopt.and.presentation.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import android.util.Patterns
import java.util.regex.Pattern
import org.sopt.and.domain.User

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val isSignUpSuccess: Boolean = false,
    val errorMessage: String? = null
)

sealed class SignUpEvent {
    data object Success : SignUpEvent()
    data class Error(val message: String) : SignUpEvent()
}

class SignUpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

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

    fun onSignUpClick(): Boolean {
        val currentState = _uiState.value

        return when {
            !isEmailValid(currentState.email) -> {
                _uiState.update { it.copy(
                    errorMessage = "유효하지 않은 이메일 형식입니다."
                ) }
                false
            }
            !isPasswordValid(currentState.password) -> {
                _uiState.update { it.copy(
                    errorMessage = "비밀번호는 8-20자의 영문 대소문자, 숫자, 특수문자를 포함해야 합니다."
                ) }
                false
            }
            else -> {
                _uiState.update { it.copy(
                    isSignUpSuccess = true,
                    errorMessage = null
                ) }
                true
            }
        }
    }

    fun getUser(): User = User(_uiState.value.email, _uiState.value.password)

    private fun isEmailValid(email: String): Boolean =
        email.isNotBlank() && emailPattern.matcher(email).matches()

    private fun isPasswordValid(password: String): Boolean =
        password.isNotBlank() && passwordPattern.matcher(password).matches()

    companion object {
        private val emailPattern = Patterns.EMAIL_ADDRESS
        private val passwordPattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#\$%^&*]).{8,20}$")
    }
}
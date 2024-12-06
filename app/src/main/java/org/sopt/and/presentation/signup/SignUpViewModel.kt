// presentation/signup/SignUpViewModel.kt

package org.sopt.and.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.and.data.ServicePool
import org.sopt.and.data.model.request.SignUpRequest

class SignUpViewModel : ViewModel() {
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

    private fun validateInput(input: String, fieldName: String): String? {
        return when {
            input.isBlank() -> "${fieldName}을 입력해주세요"
            input.length > 8 -> "${fieldName}은 8자 이하여야 합니다"
            else -> null
        }
    }

    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(
            username = username,
            errorMessage = validateInput(username, "username")
        ) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(
            password = password,
            errorMessage = validateInput(password, "password")
        ) }
    }

    fun onHobbyChange(hobby: String) {
        _uiState.update { it.copy(
            hobby = hobby,
            errorMessage = validateInput(hobby, "hobby")
        ) }
    }

    fun onPasswordVisibilityChange() {
        _uiState.update { it.copy(showPassword = !it.showPassword) }
    }

    fun signUp() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val response = ServicePool.authService.signUp(
                    SignUpRequest(
                        username = _uiState.value.username,
                        password = _uiState.value.password,
                        hobby = _uiState.value.hobby
                    )
                )

                when {
                    response.isSuccessful && response.body()?.result != null -> {
                        _uiState.update { it.copy(isSuccess = true) }
                    }
                    response.code() == 400 -> {
                        val errorMessage = when(response.body()?.code) {
                            "00" -> "요청이 유효하지 않습니다"
                            "01" -> "입력값이 8자를 초과했습니다"
                            else -> "회원가입에 실패했습니다"
                        }
                        _uiState.update { it.copy(errorMessage = errorMessage) }
                    }
                    response.code() == 409 -> {
                        _uiState.update { it.copy(errorMessage = "이미 존재하는 username입니다") }
                    }
                    else -> {
                        _uiState.update { it.copy(errorMessage = "회원가입에 실패했습니다") }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "네트워크 오류가 발생했습니다") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
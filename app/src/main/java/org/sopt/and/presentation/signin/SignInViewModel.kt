package org.sopt.and.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.and.data.ServicePool
import org.sopt.and.data.local.AuthLocalDataSource
import org.sopt.and.data.model.request.SignInRequest

class SignInViewModel(
    private val authDataStore: AuthLocalDataSource
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    data class SignInUiState(
        val username: String = "",
        val password: String = "",
        val showPassword: Boolean = false,
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val token: String? = null
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

    fun onPasswordVisibilityChange() {
        _uiState.update { it.copy(showPassword = !it.showPassword) }
    }

    fun signIn() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val response = ServicePool.authService.signIn(
                    SignInRequest(
                        username = _uiState.value.username,
                        password = _uiState.value.password
                    )
                )

                when {
                    response.isSuccessful && response.body()?.result != null -> {
                        response.body()?.result?.token?.let { token ->
                            authDataStore.saveToken(token)
                            _uiState.update { it.copy(token = token) }
                        }
                    }
                    response.code() == 400 -> {
                        val errorMessage = when(response.body()?.code) {
                            "01" -> "요청이 유효하지 않습니다"
                            "02" -> "로그인 정보가 올바르지 않습니다"
                            else -> "로그인에 실패했습니다"
                        }
                        _uiState.update { it.copy(errorMessage = errorMessage) }
                    }
                    response.code() == 403 -> {
                        _uiState.update { it.copy(errorMessage = "비밀번호가 틀렸습니다") }
                    }
                    else -> {
                        _uiState.update { it.copy(errorMessage = "로그인에 실패했습니다") }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "네트워크 오류가 발생했습니다") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    companion object {
        fun provideFactory(
            authDataStore: AuthLocalDataSource
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SignInViewModel(authDataStore) as T
            }
        }
    }
}
package org.sopt.and.presentation.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.and.domain.error.AuthError
import org.sopt.and.domain.usecase.auth.SignInUseCase
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _effect = Channel<SignInEffect>()
    val effect = _effect.receiveAsFlow()

    fun processIntent(intent: SignInIntent) {
        when (intent) {
            is SignInIntent.UpdateUsername -> updateUsername(intent.username)
            is SignInIntent.UpdatePassword -> updatePassword(intent.password)
            is SignInIntent.TogglePasswordVisibility -> togglePasswordVisibility()
            is SignInIntent.SignIn -> signIn()
        }
    }

    private fun updateUsername(username: String) {
        _state.update { it.copy(username = username) }
    }

    private fun updatePassword(password: String) {
        _state.update { it.copy(password = password) }
    }

    private fun togglePasswordVisibility() {
        _state.update { it.copy(showPassword = !it.showPassword) }
    }

    private fun signIn() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            signInUseCase(
                username = state.value.username,
                password = state.value.password
            ).onSuccess {
                _effect.send(SignInEffect.NavigateToHome)
            }.onFailure { exception ->
                _effect.send(SignInEffect.ShowError((exception as? AuthError)?.message ?: "Unknown error"))
            }

            _state.update { it.copy(isLoading = false) }
        }
    }
}

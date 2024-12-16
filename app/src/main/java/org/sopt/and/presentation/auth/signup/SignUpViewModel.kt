package org.sopt.and.presentation.auth.signup

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
import org.sopt.and.domain.entity.User
import org.sopt.and.domain.usecase.auth.SignUpUseCase
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    private val _effect = Channel<SignUpEffect>()
    val effect = _effect.receiveAsFlow()

    fun processIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.UpdateUsername -> updateUsername(intent.username)
            is SignUpIntent.UpdatePassword -> updatePassword(intent.password)
            is SignUpIntent.UpdateHobby -> updateHobby(intent.hobby)
            is SignUpIntent.TogglePasswordVisibility -> togglePasswordVisibility()
            is SignUpIntent.SignUp -> signUp()
        }
    }

    private fun updateUsername(username: String) {
        _state.update { it.copy(username = username) }
    }

    private fun updatePassword(password: String) {
        _state.update { it.copy(password = password) }
    }

    private fun updateHobby(hobby: String) {
        _state.update { it.copy(hobby = hobby) }
    }

    private fun togglePasswordVisibility() {
        _state.update { it.copy(showPassword = !it.showPassword) }
    }

    private fun signUp() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val user = User(
                username = state.value.username,
                password = state.value.password,
                hobby = state.value.hobby
            )

            signUpUseCase(user)
                .onSuccess {
                    _effect.send(SignUpEffect.ShowSignUpSuccess)
                    _effect.send(SignUpEffect.NavigateToSignIn)
                }
                .onFailure { exception ->
                    _effect.send(SignUpEffect.ShowError(exception.message ?: "Unknown error"))
                }

            _state.update { it.copy(isLoading = false) }
        }
    }
}

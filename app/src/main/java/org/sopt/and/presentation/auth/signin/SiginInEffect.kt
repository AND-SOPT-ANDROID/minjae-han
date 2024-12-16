package org.sopt.and.presentation.auth.signin

sealed interface SignInEffect {
    data class ShowError(val message: String) : SignInEffect
    data object NavigateToHome : SignInEffect
}

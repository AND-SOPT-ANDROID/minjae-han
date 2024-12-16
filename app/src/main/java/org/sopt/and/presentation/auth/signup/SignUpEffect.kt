package org.sopt.and.presentation.auth.signup

sealed interface SignUpEffect {
    data class ShowError(val message: String) : SignUpEffect
    data object NavigateToSignIn : SignUpEffect
    data object ShowSignUpSuccess : SignUpEffect
}

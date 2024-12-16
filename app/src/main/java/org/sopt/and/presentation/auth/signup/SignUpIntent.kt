package org.sopt.and.presentation.auth.signup

sealed interface SignUpIntent {
    data class UpdateUsername(val username: String) : SignUpIntent
    data class UpdatePassword(val password: String) : SignUpIntent
    data class UpdateHobby(val hobby: String) : SignUpIntent
    data object TogglePasswordVisibility : SignUpIntent
    data object SignUp : SignUpIntent
}

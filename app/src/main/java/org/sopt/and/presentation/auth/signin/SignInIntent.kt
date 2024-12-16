package org.sopt.and.presentation.auth.signin

sealed interface SignInIntent {
    data class UpdateUsername(val username: String) : SignInIntent
    data class UpdatePassword(val password: String) : SignInIntent
    data object TogglePasswordVisibility : SignInIntent
    data object SignIn : SignInIntent
}

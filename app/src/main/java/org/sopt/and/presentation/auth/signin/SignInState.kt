package org.sopt.and.presentation.auth.signin

data class SignInState(
    val username: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val isLoading: Boolean = false
)

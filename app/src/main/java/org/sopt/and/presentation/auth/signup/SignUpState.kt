package org.sopt.and.presentation.auth.signup

data class SignUpState(
    val username: String = "",
    val password: String = "",
    val hobby: String = "",
    val showPassword: Boolean = false,
    val isLoading: Boolean = false
)

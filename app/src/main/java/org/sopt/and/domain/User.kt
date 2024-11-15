package org.sopt.and.domain

data class User(
    val username: String = "",
    val password: String = "",
    val hobby: String = ""     // hobby 필드 추가
)
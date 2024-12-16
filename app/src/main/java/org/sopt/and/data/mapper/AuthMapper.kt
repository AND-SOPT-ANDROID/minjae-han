package org.sopt.and.data.mapper

import org.sopt.and.data.remote.dto.request.SignUpRequest
import org.sopt.and.data.remote.dto.response.SignInResponse
import org.sopt.and.domain.entity.Auth
import org.sopt.and.domain.entity.User

fun User.toSignUpRequest() = SignUpRequest(
    username = username,
    password = password,
    hobby = hobby
)

fun SignUpRequest.toUser() = User(
    username = username,
    password = password,
    hobby = hobby
)

fun SignInResponse.toAuth() = Auth(
    token = token
)

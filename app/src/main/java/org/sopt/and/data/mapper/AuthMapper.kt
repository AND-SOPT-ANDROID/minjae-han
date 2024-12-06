package org.sopt.and.data.mapper

import org.sopt.and.data.remote.dto.request.SignUpRequest
import org.sopt.and.data.remote.dto.response.SignInResponse
import org.sopt.and.domain.entity.Auth
import org.sopt.and.domain.entity.User
import javax.inject.Inject

class AuthMapper @Inject constructor() {
    fun toSignUpRequest(user: User) = SignUpRequest(
        username = user.username,
        password = user.password,
        hobby = user.hobby
    )

    fun toUser(signUpRequest: SignUpRequest) = User(
        username = signUpRequest.username,
        password = signUpRequest.password,
        hobby = signUpRequest.hobby
    )

    fun toAuth(response: SignInResponse) = Auth(
        token = response.token
    )
}

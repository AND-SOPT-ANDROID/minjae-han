package org.sopt.and.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("result")
    val result: T? = null,
    @SerialName("code")
    val code: String? = null
)

@Serializable
data class SignUpResponse(
    @SerialName("no")
    val no: Int
)

@Serializable
data class SignInResponse(
    @SerialName("token")
    val token: String
)

@Serializable
data class HobbyResponse(
    @SerialName("hobby")
    val hobby: String
)

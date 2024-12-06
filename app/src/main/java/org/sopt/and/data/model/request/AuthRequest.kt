package org.sopt.and.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
    @SerialName("hobby")
    val hobby: String
)

@Serializable
data class SignInRequest(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String
)
package org.sopt.and.data.remote.api

import org.sopt.and.data.remote.dto.request.SignInRequest
import org.sopt.and.data.remote.dto.request.SignUpRequest
import org.sopt.and.data.remote.dto.response.BaseResponse
import org.sopt.and.data.remote.dto.response.HobbyResponse
import org.sopt.and.data.remote.dto.response.SignInResponse
import org.sopt.and.data.remote.dto.response.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("user")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): Response<BaseResponse<SignUpResponse>>

    @POST("login")
    suspend fun signIn(
        @Body request: SignInRequest
    ): Response<BaseResponse<SignInResponse>>

    @GET("user/my-hobby")
    suspend fun getMyHobby(
        @Header("token") token: String
    ): Response<BaseResponse<HobbyResponse>>
}

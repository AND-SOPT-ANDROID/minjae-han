package org.sopt.and.data.service

import org.sopt.and.data.model.request.SignInRequest
import org.sopt.and.data.model.request.SignUpRequest
import org.sopt.and.data.model.response.BaseResponse
import org.sopt.and.data.model.response.HobbyResponse
import org.sopt.and.data.model.response.SignInResponse
import org.sopt.and.data.model.response.SignUpResponse
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
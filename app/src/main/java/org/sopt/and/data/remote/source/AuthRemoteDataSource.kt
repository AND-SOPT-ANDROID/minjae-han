package org.sopt.and.data.remote.source

import org.sopt.and.data.remote.api.AuthService
import org.sopt.and.data.remote.dto.request.SignInRequest
import org.sopt.and.data.remote.dto.request.SignUpRequest
import org.sopt.and.data.remote.dto.response.BaseResponse
import org.sopt.and.data.remote.dto.response.HobbyResponse
import org.sopt.and.data.remote.dto.response.SignInResponse
import org.sopt.and.data.remote.dto.response.SignUpResponse
import retrofit2.Response  
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun signIn(request: SignInRequest): Response<BaseResponse<SignInResponse>> {
        return authService.signIn(request)
    }

    suspend fun signUp(request: SignUpRequest): Response<BaseResponse<SignUpResponse>> {
        return authService.signUp(request)
    }

    suspend fun getMyHobby(token: String): Response<BaseResponse<HobbyResponse>> {
        return authService.getMyHobby(token)
    }
}

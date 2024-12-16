package org.sopt.and.data.repository

import kotlinx.coroutines.flow.first
import org.sopt.and.data.local.source.AuthLocalDataSource
import org.sopt.and.data.mapper.toAuth
import org.sopt.and.data.mapper.toSignUpRequest
import org.sopt.and.data.remote.dto.request.SignInRequest
import org.sopt.and.data.remote.source.AuthRemoteDataSource
import org.sopt.and.domain.entity.Auth
import org.sopt.and.domain.entity.User
import org.sopt.and.domain.error.AuthError
import org.sopt.and.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource,
) : AuthRepository{

    override suspend fun signIn(username: String, password: String): Result<Auth> {
        return try {
            val response = authRemoteDataSource.signIn(SignInRequest(username, password))
            when {
                response.isSuccessful && response.body()?.result?.token != null -> {
                    val token = response.body()?.result?.token!!
                    authLocalDataSource.saveToken(token)
                    Result.success(response.body()?.result?.toAuth() ?: throw AuthError.Unknown)
                }
                response.code() == 400 -> Result.failure(AuthError.InvalidCredentials)
                response.code() == 403 -> Result.failure(AuthError.Forbidden)
                else -> Result.failure(AuthError.Unknown)
            }
        } catch (e: Exception) {
            Result.failure(AuthError.NetworkError)
        }
    }

    override suspend fun signUp(user: User): Result<Unit> {
        return try {
            val request = user.toSignUpRequest()
            val response = authRemoteDataSource.signUp(request)
            when {
                response.isSuccessful -> Result.success(Unit)
                response.code() == 409 -> Result.failure(AuthError.InvalidCredentials)
                else -> Result.failure(AuthError.Unknown)
            }
        } catch (e: Exception) {
            Result.failure(AuthError.NetworkError)
        }
    }

    override suspend fun getMyHobby(): Result<String> {
        return try {
            val token = getToken().getOrElse { error ->
                return Result.failure(error)
            }
            val response = authRemoteDataSource.getMyHobby(token)
            when {
                response.isSuccessful && response.body()?.result != null -> {
                    Result.success(response.body()?.result?.hobby ?: "")
                }
                response.code() == 401 -> Result.failure(AuthError.Unauthorized)
                response.code() == 403 -> Result.failure(AuthError.Forbidden)
                else -> Result.failure(AuthError.Unknown)
            }
        } catch (e: Exception) {
            Result.failure(AuthError.NetworkError)
        }
    }

    override suspend fun saveToken(token: String) {
        authLocalDataSource.saveToken(token)
    }

    override suspend fun getToken(): Result<String> {
        return try {
            val token = authLocalDataSource.getToken().first()
            if (token != null) {
                Result.success(token)
            } else {
                Result.failure(AuthError.Unauthorized)
            }
        } catch (e: Exception) {
            Result.failure(AuthError.Unknown)
        }
    }

    override suspend fun clearToken() {
        authLocalDataSource.clearToken()
    }
}

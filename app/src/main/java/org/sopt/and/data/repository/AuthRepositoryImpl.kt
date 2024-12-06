package org.sopt.and.data.repository

import kotlinx.coroutines.flow.first
import org.sopt.and.data.local.source.AuthLocalDataSource
import org.sopt.and.data.mapper.AuthMapper
import org.sopt.and.data.remote.dto.request.SignInRequest
import org.sopt.and.data.remote.source.AuthRemoteDataSource
import org.sopt.and.domain.entity.Auth
import org.sopt.and.domain.entity.User
import org.sopt.and.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource,
    private val mapper: AuthMapper
) : AuthRepository {

    override suspend fun signIn(username: String, password: String): Result<Auth> {
        return try {
            val response = authRemoteDataSource.signIn(SignInRequest(username, password))
            when {
                response.isSuccessful && response.body()?.result != null -> {
                    val token = response.body()?.result?.token ?: ""
                    authLocalDataSource.saveToken(token)
                    Result.success(Auth(token))
                }
                response.code() == 400 -> {
                    Result.failure(Exception("Invalid credentials"))
                }
                response.code() == 403 -> {
                    Result.failure(Exception("Wrong password"))
                }
                else -> {
                    Result.failure(Exception("Login failed"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(user: User): Result<Unit> {
        return try {
            val request = mapper.toSignUpRequest(user)
            val response = authRemoteDataSource.signUp(request)
            when {
                response.isSuccessful -> Result.success(Unit)
                response.code() == 409 -> Result.failure(Exception("Username already exists"))
                else -> Result.failure(Exception("Sign up failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMyHobby(): Result<String> {
        return try {
            val token = authLocalDataSource.getToken().first() ?: throw Exception("Token not found")
            val response = authRemoteDataSource.getMyHobby(token)
            when {
                response.isSuccessful && response.body()?.result != null -> {
                    Result.success(response.body()?.result?.hobby ?: "")
                }
                response.code() == 401 -> Result.failure(Exception("Token not found"))
                response.code() == 403 -> Result.failure(Exception("Invalid token"))
                else -> Result.failure(Exception("Failed to get hobby"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveToken(token: String) {
        authLocalDataSource.saveToken(token)
    }

    override suspend fun getToken(): String? {
        return authLocalDataSource.getToken().first()
    }

    override suspend fun clearToken() {
        authLocalDataSource.clearToken()
    }
}

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
                response.isSuccessful && response.body()?.result?.token != null -> {
                    val token = response.body()?.result?.token!!
                    authLocalDataSource.saveToken(token)
                    Result.success(Auth(token))
                }
                response.code() == 400 -> Result.failure(Exception("아이디 또는 비밀번호를 확인해주세요"))
                response.code() == 403 -> Result.failure(Exception("비밀번호가 일치하지 않습니다"))
                else -> Result.failure(Exception("로그인에 실패했습니다"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("네트워크 오류가 발생했습니다"))
        }
    }

    override suspend fun signUp(user: User): Result<Unit> {
        return try {
            val request = mapper.toSignUpRequest(user)
            val response = authRemoteDataSource.signUp(request)
            when {
                response.isSuccessful -> Result.success(Unit)
                response.code() == 409 -> Result.failure(Exception("이미 존재하는 아이디입니다"))
                else -> Result.failure(Exception("회원가입에 실패했습니다"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("네트워크 오류가 발생했습니다"))
        }
    }

    override suspend fun getMyHobby(): Result<String> {
        return try {
            val token = authLocalDataSource.getToken().first() ?: throw Exception("로그인이 필요합니다")
            val response = authRemoteDataSource.getMyHobby(token)
            when {
                response.isSuccessful && response.body()?.result != null -> {
                    Result.success(response.body()?.result?.hobby ?: "")
                }
                response.code() == 401 -> Result.failure(Exception("로그인이 필요합니다"))
                response.code() == 403 -> Result.failure(Exception("인증이 만료되었습니다"))
                else -> Result.failure(Exception("취미 정보 조회에 실패했습니다"))
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

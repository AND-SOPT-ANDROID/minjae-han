package org.sopt.and.domain.repository

import org.sopt.and.domain.entity.Auth
import org.sopt.and.domain.entity.User

interface AuthRepository {
    suspend fun signIn(username: String, password: String): Result<Auth>
    suspend fun signUp(user: User): Result<Unit>
    suspend fun getMyHobby(): Result<String>
    suspend fun saveToken(token: String)
    suspend fun getToken(): Result<String>
    suspend fun clearToken()
}

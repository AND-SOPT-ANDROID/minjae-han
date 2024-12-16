package org.sopt.and.domain.usecase.auth

import org.sopt.and.domain.entity.Auth
import org.sopt.and.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<Auth> {
        if (username.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Username and password cannot be blank"))
        }
        if (username.length > 8 || password.length > 8) {
            return Result.failure(IllegalArgumentException("Username and password must be 8 characters or less"))
        }
        return authRepository.signIn(username, password)
    }
}

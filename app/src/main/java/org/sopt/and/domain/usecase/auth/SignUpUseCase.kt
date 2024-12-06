package org.sopt.and.domain.usecase.auth

import org.sopt.and.domain.entity.User
import org.sopt.and.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(user: User): Result<Unit> {
        with(user) {
            if (username.isBlank() || password.isBlank() || hobby.isBlank()) {
                return Result.failure(IllegalArgumentException("All fields must be filled"))
            }
            if (username.length > 8 || password.length > 8 || hobby.length > 8) {
                return Result.failure(IllegalArgumentException("All fields must be 8 characters or less"))
            }
        }
        return authRepository.signUp(user)
    }
}

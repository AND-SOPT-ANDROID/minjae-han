package org.sopt.and.domain.usecase.auth

import org.sopt.and.domain.repository.AuthRepository
import javax.inject.Inject

class GetMyHobbyUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<String> {
        return authRepository.getMyHobby()
    }
}

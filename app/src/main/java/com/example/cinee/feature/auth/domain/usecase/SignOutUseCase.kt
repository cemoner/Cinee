package com.example.cinee.feature.auth.domain.usecase

import com.example.cinee.feature.auth.domain.repository.AuthenticationRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
private val authRepository: AuthenticationRepository) {

    suspend operator fun invoke(): Result<String> {
        return try {
            authRepository.signOut()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
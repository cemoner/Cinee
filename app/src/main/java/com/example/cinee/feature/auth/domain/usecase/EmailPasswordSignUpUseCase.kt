package com.example.cinee.feature.auth.domain.usecase

import com.example.cinee.feature.auth.domain.repository.AuthenticationRepository
import javax.inject.Inject

class EmailPasswordSignUpUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String
    ): Result<String> {
        return try {
            authenticationRepository.signUpWithEmailAndPassword(email, password,name)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
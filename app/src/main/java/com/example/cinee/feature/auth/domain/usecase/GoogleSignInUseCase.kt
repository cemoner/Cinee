package com.example.cinee.feature.auth.domain.usecase

import android.content.Context
import com.example.cinee.feature.auth.domain.repository.AuthenticationRepository
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend operator fun invoke(activityContext: Context):Result<String> {
        return try {
            authenticationRepository.signInWithGoogle(activityContext)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
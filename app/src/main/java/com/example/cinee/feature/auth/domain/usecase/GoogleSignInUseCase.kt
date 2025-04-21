package com.example.cinee.feature.auth.domain.usecase

import com.example.cinee.feature.auth.domain.repository.AuthenticationRepository
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend fun invoke(){

    }
}
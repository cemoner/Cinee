package com.example.cinee.feature.auth.data.repository

import com.example.cinee.feature.auth.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
):AuthenticationRepository {

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithPhoneNumber(phoneNumber: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithGoogle(idToken: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithFacebook(accessToken: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(email: String): Result<String> {
        TODO("Not yet implemented")
    }
}
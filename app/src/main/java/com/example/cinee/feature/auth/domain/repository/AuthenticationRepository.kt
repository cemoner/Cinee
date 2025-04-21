package com.example.cinee.feature.auth.domain.repository

interface AuthenticationRepository {

    // Email and password sign in and sign up
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<String>
    suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<String>

    // Phone number sign in
    suspend fun signInWithPhoneNumber(phoneNumber: String): Result<String>

    // Platform sign in
    suspend fun signInWithGoogle(idToken: String): Result<String>
    suspend fun signInWithFacebook(accessToken: String): Result<String>

    suspend fun resetPassword(email: String): Result<String>
}
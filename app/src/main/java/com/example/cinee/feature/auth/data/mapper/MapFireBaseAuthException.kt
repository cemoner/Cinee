package com.example.cinee.feature.auth.data.mapper

import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

fun mapFirebaseAuthException(e: Exception): String {
    return when (e) {
        is FirebaseAuthInvalidCredentialsException -> "Invalid credentials. Please check your email and password."
        is FirebaseAuthInvalidUserException -> "No account found with this email."
        is FirebaseAuthUserCollisionException -> "An account already exists with this email."
        is GoogleIdTokenParsingException -> "Failed to parse Google credentials. Please try again."
        is NullPointerException -> when (e.message) {
            "Firebase user is null" -> "Failed to get user details after authentication."
            else -> "Required data was not available."
        }
        is IllegalArgumentException -> when (e.message) {
            "Invalid credential type received" -> "Unsupported credential type. Please try a different sign-in method."
            else -> "Invalid input provided."
        }
        else -> when (e.message) {
            "NETWORK_ERROR" -> "Network error. Please check your connection and try again."
            "CANCELED" -> "Sign-in was canceled."
            "INVALID_ACCOUNT" -> "Google account is invalid or not supported."
            "DEVELOPER_ERROR" -> "App configuration error. Please contact support."
            "NO_CREDENTIALS_AVAILABLE" -> "No Google credentials available. Please try another sign-in method."
            else -> "An unexpected error occurred. Please try again."
        }
    }
}
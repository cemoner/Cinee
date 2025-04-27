package com.example.cinee.feature.auth.data.mapper

import com.example.cinee.feature.auth.domain.AuthCancelledException
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
        is AuthCancelledException -> e.message ?: "Authentication was cancelled."
        is NullPointerException -> when (e.message) {
            "EMPTY_EMAIL" -> "Email cannot be empty. Please enter a valid email."
            "EMPTY_PASSWORD" -> "Password cannot be empty. Please enter a valid password."
            "ID_NOT_FOUND" -> "ID not found. Please check your credentials."
            "NULL_USER" -> "User not found. Please check your credentials."
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
            "NON_EXISTENT_ACCOUNT" -> "Account does not exist."
            "DEVELOPER_ERROR" -> "App configuration error. Please contact support."
            "NO_CREDENTIALS_AVAILABLE" -> "No Google credentials available. Please try another sign-in method."
            "INVALID_EMAIL_FORMAT" -> "Invalid email format. Please check and try again."
            "INVALID_PASSWORD" -> "Invalid password. Please check and try again."
            "INVALID_CREDENTIALS" -> "Invalid credentials. Please check your email and password."
            "FIREBASE_ERROR" -> "Firebase error occurred. Please try again."
            "EMAIL_ALREADY_IN_USE" -> "Email already in use. Please use a different email."
            "GOOGLE_ID_TOKEN_PARSING_ERROR" -> "Failed to parse Google credentials. Please try again."
            else -> "An unexpected error occurred. Please try again."
        }
    }
}
package com.example.cinee.feature.auth.data.mapper


import com.example.cinee.feature.auth.domain.AuthCancelledException
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.IllegalArgumentException
import java.lang.NullPointerException



class MapFireBaseAuthExceptionKtTest {

    @Test
    fun authCancelledExceptionHandling() {
        // Use the actual exception type if available and not final, otherwise mock or use a generic Exception
        val exception = AuthCancelledException() // Assuming AuthCancelledException requires a non-null message
        val expectedMessage = "Authentication was cancelled" // Match the mapper's output
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun authCancelledExceptionCustomMessageHandling() {
        val customMessage = "User cancelled the flow."
        val exception = AuthCancelledException(customMessage) // Assuming AuthCancelledException is accessible
        assertEquals(customMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun firebaseAuthInvalidCredentialsExceptionHandling() {
        // Use a generic Exception with a specific message string
        // Ensure mapFirebaseAuthException handles "INVALID_CREDENTIALS" or similar
        val exception = Exception("INVALID_CREDENTIALS") // Or match the specific code Firebase might use
        val expectedMessage = "Invalid credentials. Please check your email and password."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun firebaseAuthInvalidUserExceptionHandling() {
        // Use a generic Exception with a specific message string
        // Ensure mapFirebaseAuthException handles "USER_NOT_FOUND" or similar
        val exception = Exception("NON_EXISTENT_ACCOUNT") // Or match the specific code Firebase might use
        val expectedMessage = "Account does not exist."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun firebaseAuthUserCollisionExceptionHandling() {
        // Use a generic Exception with a specific message string
        // Ensure mapFirebaseAuthException handles "EMAIL_ALREADY_IN_USE" or similar
        val exception = Exception("EMAIL_ALREADY_IN_USE") // Or match the specific code Firebase might use
        val expectedMessage = "Email already in use. Please use a different email."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun googleIdTokenParsingExceptionHandling() {
        val exception = Exception("GOOGLE_ID_TOKEN_PARSING_ERROR") // Use a specific message
        val expectedMessage = "Failed to parse Google credentials. Please try again."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun nullPointerExceptionEmptyEmailHandling() {
        val exception = NullPointerException("EMPTY_EMAIL")
        val expectedMessage = "Email cannot be empty. Please enter a valid email."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun nullPointerExceptionEmptyPasswordHandling() {
        val exception = NullPointerException("EMPTY_PASSWORD")
        val expectedMessage = "Password cannot be empty. Please enter a valid password."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun nullPointerExceptionIdNotFoundHandling() {
        val exception = NullPointerException("ID_NOT_FOUND")
        val expectedMessage = "ID not found. Please check your credentials."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun nullPointerExceptionNullUserHandling() {
        val exception = NullPointerException("NULL_USER")
        val expectedMessage = "User not found. Please check your credentials."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun nullPointerExceptionDefaultHandling() {
        val exception = NullPointerException("Some other null pointer reason")
        val expectedMessage = "Required data was not available." // Default message from mapper
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun illegalArgumentExceptionInvalidCredentialTypeHandling() {
        val exception = IllegalArgumentException("Invalid credential type received")
        val expectedMessage = "Unsupported credential type. Please try a different sign-in method."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun illegalArgumentExceptionDefaultHandling() {
        val exception = IllegalArgumentException("Some other illegal argument")
        val expectedMessage = "Invalid input provided." // Default message from mapper
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun genericExceptionNetworkErrorHandling() {
        val exception = Exception("NETWORK_ERROR")
        val expectedMessage = "Network error. Please check your connection and try again."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun genericExceptionCanceledHandling() {
        val exception = Exception("CANCELED")
        val expectedMessage = "Sign-in was canceled."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun genericExceptionInvalidAccountHandling() {
        val exception = Exception("INVALID_ACCOUNT")
        val expectedMessage = "Google account is invalid or not supported."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun genericExceptionNonExistentAccountHandling() {
        val exception = Exception("NON_EXISTENT_ACCOUNT")
        val expectedMessage = "Account does not exist."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun genericExceptionDeveloperErrorHandling() {
        val exception = Exception("DEVELOPER_ERROR")
        val expectedMessage = "App configuration error. Please contact support."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun genericExceptionNoCredentialsAvailableHandling() {
        val exception = Exception("NO_CREDENTIALS_AVAILABLE")
        val expectedMessage = "No Google credentials available. Please try another sign-in method."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun genericExceptionInvalidEmailFormatHandling() {
        val exception = Exception("INVALID_EMAIL_FORMAT")
        val expectedMessage = "Invalid email format. Please check and try again."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun genericExceptionInvalidPasswordHandling() {
        val exception = Exception("INVALID_PASSWORD")
        val expectedMessage = "Invalid password. Please check and try again."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun genericExceptionFirebaseErrorHandling() {
        val exception = Exception("FIREBASE_ERROR")
        val expectedMessage = "Firebase error occurred. Please try again."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun genericExceptionEmailAlreadyInUseHandling() {
        val exception = Exception("EMAIL_ALREADY_IN_USE")
        val expectedMessage = "Email already in use. Please use a different email."
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun genericExceptionDefaultHandling() {
        val exception = Exception("Some unknown generic error")
        val expectedMessage = "An unexpected error occurred. Please try again." // Default generic message
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }

    @Test
    fun unknownExceptionHandling() {
        // Use a custom exception type not explicitly handled
        class UnknownCustomException(message: String) : Exception(message)
        val exception = UnknownCustomException("This is totally unexpected")
        val expectedMessage = "An unexpected error occurred. Please try again." // Default generic message
        assertEquals(expectedMessage, mapFirebaseAuthException(exception))
    }
}
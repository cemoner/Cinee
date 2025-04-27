package com.example.cinee.feature.auth.data.repository

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.datastore.core.DataStore
import com.example.cinee.datastore.model.UserAccount
import com.example.cinee.datastore.model.UserPreferences
import com.example.cinee.datastore.model.UserProfile
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class AuthenticationRepositoryImplTest {

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    @Mock
    private lateinit var dataStore: DataStore<UserAccount>

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var mockAuthTask: Task<AuthResult>

    @Mock
    private lateinit var mockVoidTask: Task<Void> // For reset password

    @Mock
    private lateinit var mockAuthResult: AuthResult

    @Mock
    private lateinit var mockUser: FirebaseUser

    @Mock
    private lateinit var credentialManager: CredentialManager // Keep if used elsewhere, otherwise remove

    private lateinit var repository: AuthenticationRepositoryImpl

    // Mock UserAccount for reuse
    private val mockUserProfile = UserProfile(userId = "test-uid", name = "Test User", imageUrl = "")
    private val mockUserPreferences = UserPreferences()
    private val mockUserAccount = UserAccount(
        userProfile = mockUserProfile,
        userPreferences = mockUserPreferences,
        isLoggedIn = true
    )
    private val mockLoggedOutUserAccount = mockUserAccount.copy(isLoggedIn = false)


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = AuthenticationRepositoryImpl(firebaseAuth, dataStore, context)

        whenever(mockUser.uid).thenReturn("test-uid")
        whenever(mockAuthResult.user).thenReturn(mockUser)

        // Mock static FacebookAuthProvider.getCredential if needed for Facebook tests
        // mockStatic(FacebookAuthProvider::class.java).use { mockedStatic ->
        //     val mockCredential = mock(AuthCredential::class.java)
        //     mockedStatic.`when`<AuthCredential> { FacebookAuthProvider.getCredential(any()) }.thenReturn(mockCredential)
        //     // ... rest of setup if needed
        // }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun sign_In_With_Email_And_Password_valid_credentials() = runTest {
        // Setup
        val email = "test@example.com"
        val password = "password123"

        whenever(mockAuthTask.isComplete).thenReturn(true)
        whenever(mockAuthTask.result).thenReturn(mockAuthResult)
        whenever(mockAuthTask.exception).thenReturn(null)

        whenever(firebaseAuth.signInWithEmailAndPassword(email, password)).thenReturn(mockAuthTask)

        whenever(dataStore.updateData(any())).thenReturn(mockUserAccount) // Return logged-in state

        // Execute
        val result = repository.signInWithEmailAndPassword(email, password)

        // Verify
        assertTrue(result.isSuccess)
        assertEquals("test-uid", result.getOrNull())
        verify(dataStore).updateData(any()) // Check interaction
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun sign_In_With_Email_And_Password_Network_Error() = runTest(UnconfinedTestDispatcher()) {
        // Setup - Representative Failure Case
        val email = "test@example.com"
        val password = "password123"

        whenever(mockAuthTask.isComplete).thenReturn(true)
        whenever(mockAuthTask.exception).thenReturn(Exception("NETWORK_ERROR")) // Simulate failure
        whenever(firebaseAuth.signInWithEmailAndPassword(email, password)).thenReturn(mockAuthTask)

        // Execute
        val result = repository.signInWithEmailAndPassword(email, password)

        // Verify
        assertTrue(result.isFailure)
        // We don't assert the exact message here, as that's the mapper's job.
        // Just verify the failure state and lack of side effects.
        verify(dataStore, never()).updateData(any()) // Ensure no datastore update on failure
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun sign_Up_With_Email_And_Password_Existing_User() = runTest(UnconfinedTestDispatcher()) {
        // Setup - Representative Failure Case
        val email = "existing@example.com"
        val password = "password123"
        val name = "Existing User"

        // Simulate user collision exception
        whenever(mockAuthTask.isComplete).thenReturn(true)
        whenever(mockAuthTask.exception).thenReturn(Exception("USER_EXISTS"))
        whenever(firebaseAuth.createUserWithEmailAndPassword(email, password)).thenReturn(mockAuthTask)

        // Execute
        val result = repository.signUpWithEmailAndPassword(email, password, name)

        // Verify
        assertTrue(result.isFailure)
        verify(dataStore, never()).updateData(any()) // Ensure no datastore update on failure
    }

    // --- Sign Out Tests ---

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun sign_Out_Successful() = runTest {
        // Setup
        // No specific setup needed for firebaseAuth.signOut() itself, just mock the datastore interaction
        whenever(dataStore.updateData(any())).thenReturn(mockLoggedOutUserAccount) // Return logged-out state

        // Execute
        val result = repository.signOut()

        // Verify
        assertTrue(result.isSuccess)
        verify(firebaseAuth).signOut() // Verify signOut was called
        verify(dataStore).updateData(any()) // Verify datastore was updated
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun sign_Out_Failure() = runTest(UnconfinedTestDispatcher()) {
        // Setup - Representative Failure Case
        // Simulate an error during sign out (less common, but possible)
        val signOutException = RuntimeException("Sign out failed unexpectedly")
        whenever(firebaseAuth.signOut()).thenThrow(signOutException)

        // Execute
        val result = repository.signOut()

        // Verify
        assertTrue(result.isFailure)
        // Check the specific exception if mapFirebaseAuthException handles RuntimeException
        // assertEquals("An unexpected error occurred. Please try again.", result.exceptionOrNull()?.message)
        verify(dataStore, never()).updateData(any()) // Ensure datastore is not updated on failure
    }


    // --- Reset Password Tests ---

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun reset_Password_Valid_Email() = runTest {
        // Setup
        val email = "test@example.com"

        whenever(mockVoidTask.isComplete).thenReturn(true)
        whenever(mockVoidTask.exception).thenReturn(null) // Indicate success
        whenever(firebaseAuth.sendPasswordResetEmail(email)).thenReturn(mockVoidTask)

        // Execute
        val result = repository.resetPassword(email)

        // Verify
        assertTrue(result.isSuccess)
        verify(firebaseAuth).sendPasswordResetEmail(email) // Verify the call
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun reset_Password_Invalid_Email() = runTest(UnconfinedTestDispatcher()) {
        // Setup - Representative Failure Case
        val email = "nonexistent@example.com"

        // Simulate user not found error
        whenever(mockVoidTask.isComplete).thenReturn(true)
        whenever(mockVoidTask.exception).thenReturn(Exception("USER_NOT_FOUND")) // Or FirebaseAuthInvalidUserException
        whenever(firebaseAuth.sendPasswordResetEmail(email)).thenReturn(mockVoidTask)

        // Execute
        val result = repository.resetPassword(email)

        // Verify
        assertTrue(result.isFailure)
        verify(firebaseAuth).sendPasswordResetEmail(email) // Verify the call was still made
    }

}
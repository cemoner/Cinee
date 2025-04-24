package com.example.cinee.feature.auth.data.repository

import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.datastore.core.DataStore
import com.example.cinee.R
import com.example.cinee.datastore.model.UserAccount
import com.example.cinee.datastore.model.UserProfile
import com.example.cinee.feature.auth.data.mapper.mapFirebaseAuthException
import com.example.cinee.feature.auth.domain.AuthCancelledException
import com.example.cinee.feature.auth.domain.repository.AuthenticationRepository
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val dataStore: DataStore<UserAccount>,
    @ApplicationContext private val context: Context,
):AuthenticationRepository {


    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Result<String> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: return Result.failure(Exception(mapFirebaseAuthException(NullPointerException("User is null"))))
            dataStore.updateData { currentAccount ->
                currentAccount.copy(
                    userProfile = UserProfile(
                        userId = user.uid,
                        name = user.displayName ?: "John Smith",
                        imageUrl = user.photoUrl.toString(),
                    ),
                    isLoggedIn = true,
                )
            }
            Result.success(authResult.user?.uid.orEmpty() )
        } catch (e: Exception) {
            Result.failure(Exception(mapFirebaseAuthException(e)))
        }
    }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        name: String
    ): Result<String> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: return Result.failure(Exception(mapFirebaseAuthException(NullPointerException("User is null"))))
            val profileUpdates = com.google.firebase.auth.userProfileChangeRequest {
                displayName = name
            }

            user.updateProfile(profileUpdates).await()

            Result.success(authResult.user?.uid.orEmpty())
        } catch (e: Exception) {
            Result.failure(Exception(mapFirebaseAuthException(e)))
        }
    }

    override suspend fun signInWithPhoneNumber(phoneNumber: String): Result<String> {
        TODO("Not yet implemented")
    }

    // Google sign in
    private suspend fun isSignedIn(): Boolean {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            try {
                // Force a token refresh to verify the session is valid
                currentUser.getIdToken(true).await()
                return true
            } catch (e: Exception) {
                Log.w("Auth", "Session invalid, signing out", e)
                signOut()
                return false
            }
        }
        return false
    }

    override suspend fun signInWithGoogle(activityContext: Context): Result<String> {
        if (isSignedIn()) {
            val userId = firebaseAuth.currentUser?.uid ?: return Result.failure(Exception(mapFirebaseAuthException(NullPointerException("ID Not Found"))))
            return Result.success(userId)
        }

        return try {
            val result = buildCredentialRequest(activityContext)
            handleSignInWithGoogle(result)
        }
        catch (e: CancellationException) {
            Result.failure(AuthCancelledException("Sign-in was cancelled"))
        }

        catch (e: Exception) {
            Log.e("GoogleSignIn", "Error signing in with Google", e)
            Result.failure(Exception(mapFirebaseAuthException(e)))
        }
    }

    private suspend fun handleSignInWithGoogle(result: GetCredentialResponse): Result<String> {
        val credential = result.credential
        if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            try {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val authCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                val authResult = firebaseAuth.signInWithCredential(authCredential).await()
                val user = authResult.user ?: return Result.failure(Exception(mapFirebaseAuthException(NullPointerException("Firebase user is null"))))

                dataStore.updateData { currentAccount ->
                    currentAccount.copy(
                        userProfile = UserProfile(
                            userId = user.uid,
                            name = user.displayName ?: "John Smith",
                            imageUrl = user.photoUrl.toString(),
                        ),
                        isLoggedIn = true,
                    )
                }
                return Result.success(user.uid)
            } catch (e: GoogleIdTokenParsingException) {
                Log.e("GoogleSignIn", "Error parsing Google ID token", e)
                return Result.failure(Exception(mapFirebaseAuthException(e)))
            } catch (e: Exception) {
                Log.e("GoogleSignIn", "Error authenticating with Firebase", e)
                return Result.failure(Exception(mapFirebaseAuthException(e)))
            }
        } else {
            return Result.failure(Exception(mapFirebaseAuthException(IllegalArgumentException("Invalid credential type received"))))
        }
    }

    private suspend fun buildCredentialRequest(activityContext:Context):GetCredentialResponse{
        val signInCredentialManager = CredentialManager.create(context = activityContext)
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(
                        activityContext.getString(R.string.default_web_client_id)
                    )
                    .setAutoSelectEnabled(false)
                    .build()
            ).build()
        return signInCredentialManager.getCredential(
            activityContext,request
        )
    }


    // Facebook sign in
    override suspend fun signInWithFacebook(accessToken: String): Result<String> {
        return try {
            val credential = FacebookAuthProvider.getCredential(accessToken)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val user = authResult.user ?: return Result.failure(Exception(mapFirebaseAuthException(NullPointerException("Firebase user is null"))))

            dataStore.updateData { currentAccount ->
                currentAccount.copy(
                    userProfile = UserProfile(
                        userId = user.uid,
                        name = user.displayName ?: "John Smith",
                        imageUrl = user.photoUrl.toString(),
                    ),
                    isLoggedIn = true,
                )
            }

            Result.success(user.uid)
        } catch (e: Exception) {
            Log.e("FacebookLogin", "Repository failure", e)
            Result.failure(Exception(mapFirebaseAuthException(e)))
        }
    }

    // Sign out
    override suspend fun signOut(): Result<String> {
        return try {
            try {
                // Only clear credential state if we're actually signed in
                if (firebaseAuth.currentUser != null) {
                    CredentialManager.create(context).clearCredentialState(
                        ClearCredentialStateRequest()
                    )
                }
            } catch (e: Exception) {
                Log.w("Auth", "Failed to clear credential state", e)
                // Continue with sign out anyway
            }

            firebaseAuth.signOut()
            dataStore.updateData { currentAccount ->
                currentAccount.copy(
                    userProfile = UserProfile(
                        userId = "-1",
                        name = "John Smith",
                        imageUrl = "",
                    ),
                    isLoggedIn = false
                )
            }
            Result.success("Successfully signed out")
        } catch (e: Exception) {
            Result.failure(Exception(mapFirebaseAuthException(e)))
        }
    }

    // Reset password
    override suspend fun resetPassword(email: String): Result<String> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success("Password reset email sent to $email")
        } catch (e: Exception) {
            Result.failure(Exception(mapFirebaseAuthException(e)))
        }
    }
}
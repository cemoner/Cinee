package com.example.cinee.feature.auth.domain.bus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialSignInBus @Inject constructor() {
    private val _facebookSignInRequested = MutableSharedFlow<Unit>()
    val facebookSignInRequested = _facebookSignInRequested.asSharedFlow()

    suspend fun requestFacebookLogin() {
        _facebookSignInRequested.emit(Unit)
    }

    private val _googleSignInRequested = MutableSharedFlow<Unit>()
    val googleSignInRequested = _googleSignInRequested.asSharedFlow()

    suspend fun requestGoogleLogin() {
        _googleSignInRequested.emit(Unit)
    }

}
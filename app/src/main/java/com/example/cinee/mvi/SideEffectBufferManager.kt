package com.example.cinee.mvi

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Buffers critical side effects to ensure delivery across lifecycle changes.
 */
object SideEffectBuffer {
    // Using a replay-based shared flow to preserve side effects across lifecycle changes
    private val _buffer = MutableSharedFlow<Any>(
        extraBufferCapacity = 10,
        replay = 5
    )
    val buffer = _buffer.asSharedFlow()

    suspend fun emit(effect: Any) {
        _buffer.emit(effect)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun resetReplayCache() {
        _buffer.resetReplayCache()
    }
}
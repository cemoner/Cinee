package com.example.cinee.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

@Composable
fun <T> CollectSideEffect(
    sideEffect: Flow<T>,
    processBuffer: Boolean = true,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = Dispatchers.Main.immediate,
    onSideEffect: suspend CoroutineScope.(effect: T) -> Unit,
) {
    // Collect standard side effects
    LaunchedEffect(sideEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            withContext(context) {
                sideEffect.collect { effect ->
                    onSideEffect(effect)
                }
            }
        }
    }

    // Collect buffered side effects if enabled
    if (processBuffer) {
        LaunchedEffect(Unit) {
            SideEffectBuffer.buffer.collect { bufferedEffect ->
                try {
                    // Use unchecked cast with try-catch for safety
                    @Suppress("UNCHECKED_CAST")
                    val typedEffect = bufferedEffect as T
                    onSideEffect(typedEffect)
                    SideEffectBuffer.resetReplayCache()
                } catch (e: ClassCastException) {
                    // Not the right type for this collector, ignore
                }
            }
        }
    }
}
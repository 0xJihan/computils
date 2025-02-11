package com.jihan.composeutils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun LifecycleManager(
    onStart: () -> Unit = {},
    onResume: () -> Unit = {},
    onPause: () -> Unit = {},
    onStop: () -> Unit = {},
    onDestroy: () -> Unit = {},
    onRestart: () -> Unit = {},
    onDispose: () -> Unit = {},
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    var wasStopped by remember { mutableStateOf(false) }

    // Ensure latest lambda references
    val currentOnStart = rememberUpdatedState(onStart)
    val currentOnResume = rememberUpdatedState(onResume)
    val currentOnPause = rememberUpdatedState(onPause)
    val currentOnStop = rememberUpdatedState(onStop)
    val currentOnDestroy = rememberUpdatedState(onDestroy)
    val currentOnRestart = rememberUpdatedState(onRestart)
    val currentOnDispose = rememberUpdatedState(onDispose)

    // Handle lifecycle events
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    currentOnStart.value()
                    if (wasStopped) {
                        currentOnRestart.value()
                        wasStopped = false
                    }
                }

                Lifecycle.Event.ON_RESUME -> currentOnResume.value()
                Lifecycle.Event.ON_PAUSE -> currentOnPause.value()
                Lifecycle.Event.ON_STOP -> {
                    currentOnStop.value()
                    wasStopped = true
                }

                Lifecycle.Event.ON_DESTROY -> currentOnDestroy.value()
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            currentOnDispose.value()
        }
    }
}



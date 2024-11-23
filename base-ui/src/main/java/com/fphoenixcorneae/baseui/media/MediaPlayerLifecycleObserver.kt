package com.fphoenixcorneae.baseui.media

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun MediaPlayerLifecycleObserver(player: MediaPlayer) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    var previousPlaying by remember { mutableStateOf(player.isPlaying) }
    DisposableEffect(player) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    previousPlaying = player.isPlaying
                    if (player.isPlaying) {
                        player.pause()
                    }
                }

                Lifecycle.Event.ON_RESUME -> {
                    if (previousPlaying) {
                        player.start()
                    }
                }

                else -> {}
            }
        }
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
            player.release()
        }
    }
}

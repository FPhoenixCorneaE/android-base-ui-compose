package com.fphoenixcorneae.baseui.media.video

import android.os.Handler
import android.os.Looper
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.fphoenixcorneae.baseui.ext.enterPictureInPictureMode
import com.fphoenixcorneae.baseui.ext.findActivity
import com.fphoenixcorneae.baseui.ext.isInPictureInPictureMode

@OptIn(UnstableApi::class)
@Composable
fun ExoPlayerLifecycleObserver(
    player: ExoPlayer,
    defaultPlayerView: PlayerView,
    enabledEnterPictureInPictureMode: Boolean,
    usePlayerController: Boolean,
    autoDispose: Boolean,
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    var previousPlaying by remember { mutableStateOf(player.isPlaying) }
    DisposableEffect(defaultPlayerView) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    previousPlaying = player.isPlaying
                    if (!enabledEnterPictureInPictureMode && player.isPlaying) {
                        player.pause()
                    }
                    if (enabledEnterPictureInPictureMode && player.playWhenReady) {
                        context.findActivity()?.enterPictureInPictureMode(defaultPlayerView)
                    }
                }

                Lifecycle.Event.ON_RESUME -> {
                    if (previousPlaying) {
                        player.play()
                    }
                    if (enabledEnterPictureInPictureMode && player.playWhenReady) {
                        defaultPlayerView.useController = usePlayerController
                    }
                }

                Lifecycle.Event.ON_STOP -> {
                    if (!(enabledEnterPictureInPictureMode && context.isInPictureInPictureMode())) {
                        player.stop()
                    }
                }

                else -> {}
            }
        }
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
            if (autoDispose) {
                player.release()
            }
        }
    }
}

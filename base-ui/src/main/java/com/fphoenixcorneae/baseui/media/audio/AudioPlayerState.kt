@file:JvmName("IAudioPlayerKt")

package com.fphoenixcorneae.baseui.media.audio

import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.fphoenixcorneae.baseui.media.MediaPlayerLifecycleObserver

@Composable
fun rememberAudioPlayerState(source: Any): IAudioPlayer {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val player = remember { MediaPlayer() }
    val state = remember {
        AudioPlayerImpl(context = context, coroutineScope = coroutineScope, player = player)
    }
    LaunchedEffect(source) {
        when (source) {
            is String -> {
                state.setSource(source)
            }

            is Uri -> {
                state.setSource(source)
            }

            else -> {
                throw IllegalArgumentException("Audio player source must be String or Uri type!")
            }
        }
    }
    MediaPlayerLifecycleObserver(player)
    return state
}
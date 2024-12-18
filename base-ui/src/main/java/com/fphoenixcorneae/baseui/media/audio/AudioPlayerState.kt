@file:JvmName("IAudioPlayerKt")

package com.fphoenixcorneae.baseui.media.audio

import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext

@Stable
interface AudioPlayerState {
    /** 播放器实例 */
    val player: MediaPlayer

    /** 是否播放中 */
    val isPlaying: Boolean

    /** 总时长 */
    val totalDuration: Int

    /** 当前播放位置 */
    val currentPosition: Int

    /** 设置网络音源 */
    fun setSource(path: String)

    /** 设置本地音源 */
    fun setSource(uri: Uri)

    /** 开始/暂停播放 */
    fun playOrPause()

    /** 跳转到指定时长 */
    fun seekTo(milliseconds: Int)
}

@Composable
fun rememberAudioPlayerState(source: Any): AudioPlayerState {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val player = remember { MediaPlayer() }
    val state = remember {
        AudioPlayerStateImpl(context = context, coroutineScope = coroutineScope, player = player)
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
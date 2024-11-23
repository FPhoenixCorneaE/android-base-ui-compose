package com.fphoenixcorneae.baseui.media.audio

import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.runtime.Stable

@Stable
interface IAudioPlayer {
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
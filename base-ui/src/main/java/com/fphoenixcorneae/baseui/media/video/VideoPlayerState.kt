package com.fphoenixcorneae.baseui.media.video

import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.C.AUDIO_CONTENT_TYPE_MOVIE
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import com.fphoenixcorneae.baseui.ext.enterPictureInPictureMode
import com.fphoenixcorneae.baseui.ext.findActivity

@Stable
interface VideoPlayerState {
    /** 播放器实例 */
    val player: ExoPlayer

    val playerView: PlayerView

    /** 是否播放中 */
    val isPlaying: Boolean

    /** 总时长 */
    val totalDuration: Long

    /** 当前播放位置 */
    val currentPosition: Long

    /** 设置播放列表 */
    fun setMediaItems(items: List<VideoPlayerMediaItem>)

    /** 开始/暂停播放 */
    fun playOrPause()

    /** 跳转到指定时长 */
    fun seekTo(milliseconds: Long)
}

@OptIn(UnstableApi::class)
@Composable
fun rememberVideoPlayerState(
    mediaItems: List<VideoPlayerMediaItem>,
    seekBeforeMilliSeconds: Long = 10000L,
    seekAfterMilliSeconds: Long = 10000L,
    repeatMode: RepeatMode = RepeatMode.NONE,
    resizeMode: ResizeMode = ResizeMode.FIT,
    usePlayerController: Boolean = true,
    handleAudioFocus: Boolean = true,
    enabledEnterPictureInPictureMode: Boolean = false,
    autoPlay: Boolean = true,
    autoDispose: Boolean = true,
    playerBuilder: ExoPlayer.Builder.() -> Unit = { },
): VideoPlayerState {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val player = remember {
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()

        ExoPlayer.Builder(context)
            .setSeekBackIncrementMs(seekBeforeMilliSeconds)
            .setSeekForwardIncrementMs(seekAfterMilliSeconds)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AUDIO_CONTENT_TYPE_MOVIE)
                    .setUsage(C.USAGE_MEDIA)
                    .build(),
                handleAudioFocus,
            )
            .apply {
                val cache = VideoPlayerCacheManager.initialize(context).getCache()
                if (cache != null) {
                    val cacheDataSourceFactory = CacheDataSource.Factory()
                        .setCache(cache)
                        .setUpstreamDataSourceFactory(
                            DefaultDataSource.Factory(
                                context,
                                httpDataSourceFactory
                            )
                        )
                    setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory))
                }
            }
            .apply {
                playerBuilder()
            }
            .build()
    }
    val defaultPlayerView = remember { PlayerView(context) }
    val state = remember {
        VideoPlayerStateImpl(
            context = context,
            coroutineScope = coroutineScope,
            player = player,
            playerView = defaultPlayerView,
        )
    }
    BackHandler(enabledEnterPictureInPictureMode) {
        context.findActivity()?.enterPictureInPictureMode(defaultPlayerView)
        player.play()
    }
    LaunchedEffect(mediaItems) {
        state.setMediaItems(mediaItems)
        if (autoPlay) {
            player.play()
        }
    }
    LaunchedEffect(player) {
        defaultPlayerView.player = player
    }
    LaunchedEffect(usePlayerController) {
        defaultPlayerView.useController = usePlayerController
    }
    LaunchedEffect(resizeMode) {
        defaultPlayerView.resizeMode = resizeMode.toPlayerViewResizeMode()
    }
    LaunchedEffect(repeatMode) {
        player.repeatMode = repeatMode.toExoPlayerRepeatMode()
    }
    ExoPlayerLifecycleObserver(
        player = player,
        defaultPlayerView = defaultPlayerView,
        enabledEnterPictureInPictureMode = enabledEnterPictureInPictureMode,
        usePlayerController = usePlayerController,
        autoDispose = autoDispose
    )
    return state
}
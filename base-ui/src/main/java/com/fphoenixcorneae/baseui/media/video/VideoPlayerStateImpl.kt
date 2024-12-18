package com.fphoenixcorneae.baseui.media.video

import android.content.Context
import androidx.annotation.OptIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.media3.common.ForwardingPlayer
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.analytics.AnalyticsListener
import androidx.media3.session.MediaSession
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(UnstableApi::class)
internal class VideoPlayerStateImpl(
    private val context: Context,
    private val coroutineScope: CoroutineScope,
    override val player: ExoPlayer,
    override val playerView: PlayerView,
) : VideoPlayerState {
    override var isPlaying: Boolean by mutableStateOf(player.isPlaying)
        private set
    override var totalDuration by mutableLongStateOf(0)
        private set
    override var currentPosition by mutableLongStateOf(0)
        private set

    private var mediaSession: MediaSession? = null
    private var mProgressJob: Job? = null

    override fun setMediaItems(items: List<VideoPlayerMediaItem>) {
        reset()
        mediaSession?.release()
        val sessionId = UUID.randomUUID().toString().lowercase().split("-").first()
        mediaSession = MediaSession.Builder(context, ForwardingPlayer(player))
            .setId("VideoPlayerMediaSession_${sessionId}")
            .build()
        prepare(items)
    }

    override fun playOrPause() {
        if (isPlaying) {
            pause()
        } else {
            play()
        }
    }

    override fun seekTo(milliseconds: Long) {
        if (milliseconds <= totalDuration) {
            currentPosition = milliseconds
            player.seekTo(milliseconds)
            if (!isPlaying) {
                play()
            }
        }
    }

    private fun play() {
        player.play()
        startUpdatingProgress()
    }

    private fun pause() {
        player.pause()
        stopUpdatingProgress()
    }

    private fun startUpdatingProgress() {
        stopUpdatingProgress()
        mProgressJob = coroutineScope.launch {
            while (isActive) {
                currentPosition = player.currentPosition
                delay(500)
            }
        }
    }

    private fun stopUpdatingProgress() {
        mProgressJob?.cancel()
        mProgressJob = null
    }

    private fun reset() {
        isPlaying = false
        currentPosition = 0
        stopUpdatingProgress()
    }

    private fun prepare(items: List<VideoPlayerMediaItem>) {
        player.apply {
            val exoPlayerMediaItems = items.map {
                MediaItem.Builder().apply {
                    setUri(it.toUri())
                    setMediaMetadata(it.mediaMetadata)
                    setMimeType(it.mimeType)
                    if (it is VideoPlayerMediaItem.NetworkMediaItem) {
                        setDrmConfiguration(it.drmConfiguration)
                    }
                }.build()
            }
            setMediaItems(exoPlayerMediaItems)
            prepare()
            totalDuration = duration
            addAnalyticsListener(object : AnalyticsListener {
                override fun onIsLoadingChanged(
                    eventTime: AnalyticsListener.EventTime,
                    isLoading: Boolean
                ) {

                }

                override fun onIsPlayingChanged(
                    eventTime: AnalyticsListener.EventTime,
                    isPlaying: Boolean
                ) {
                    this@VideoPlayerStateImpl.isPlaying = isPlaying
                }

                override fun onPlayWhenReadyChanged(
                    eventTime: AnalyticsListener.EventTime,
                    playWhenReady: Boolean,
                    reason: Int
                ) {

                }
            })
        }
    }
}
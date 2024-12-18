package com.fphoenixcorneae.baseui.media.audio

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class AudioPlayerStateImpl(
    private val context: Context,
    private val coroutineScope: CoroutineScope,
    override val player: MediaPlayer
) : AudioPlayerState {
    override var isPlaying: Boolean by mutableStateOf(player.isPlaying)
        private set
    override var totalDuration by mutableIntStateOf(0)
        private set
    override var currentPosition by mutableIntStateOf(0)
        private set
    private var mProgressJob: Job? = null

    override fun setSource(path: String) {
        reset()
        player.setDataSource(path)
        prepare()
    }

    override fun setSource(uri: Uri) {
        reset()
        player.setDataSource(context, uri)
        prepare()
    }

    override fun playOrPause() {
        if (isPlaying) {
            pause()
        } else {
            play()
        }
    }

    override fun seekTo(milliseconds: Int) {
        if (milliseconds <= totalDuration) {
            currentPosition = milliseconds
            player.seekTo(milliseconds)
            if (!isPlaying) {
                play()
            }
        }
    }

    private fun play() {
        player.start()
        startUpdatingProgress()
        isPlaying = player.isPlaying
    }

    private fun pause() {
        player.pause()
        stopUpdatingProgress()
        isPlaying = player.isPlaying
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
        player.reset()
        isPlaying = false
        currentPosition = 0
        stopUpdatingProgress()
    }

    private fun prepare() {
        player.apply {
            prepareAsync()
            setOnPreparedListener {
                totalDuration = it.duration
            }
            setOnCompletionListener {
                this@AudioPlayerStateImpl.isPlaying = false
            }
        }
    }
}

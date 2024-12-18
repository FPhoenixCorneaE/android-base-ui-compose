package com.fphoenixcorneae.baseui.media.video

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView

@Preview
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    mediaItems: List<VideoPlayerMediaItem> = emptyList(),
    videoPlayerState: VideoPlayerState? = rememberVideoPlayerState(mediaItems),
) {
    AndroidView(
        factory = { videoPlayerState?.playerView ?: View(it) },
        modifier = modifier.background(Color.Black),
    )
}
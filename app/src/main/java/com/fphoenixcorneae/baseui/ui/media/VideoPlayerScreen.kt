package com.fphoenixcorneae.baseui.ui.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MimeTypes
import com.fphoenixcorneae.baseui.R
import com.fphoenixcorneae.baseui.media.video.VideoPlayer
import com.fphoenixcorneae.baseui.media.video.VideoPlayerMediaItem
import com.fphoenixcorneae.baseui.media.video.rememberVideoPlayerState

@Preview
@Composable
fun VideoPlayerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff5f5f5)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val context = LocalContext.current
        val mediaItems = listOf(
            VideoPlayerMediaItem.RawResourceMediaItem(
                rawResourceId = R.raw.video_hot_town,
                mediaMetadata = MediaMetadata.Builder().setTitle("Hot Town").build(),
                mimeType = MimeTypes.APPLICATION_MP4,
            )
        )
        val videoPlayerState = if (!LocalView.current.isInEditMode) {
            rememberVideoPlayerState(
                mediaItems = mediaItems,
                usePlayerController = true,
                autoPlay = true,
                autoDispose = true,
                enabledEnterPictureInPictureMode = false,
            )
        } else {
            null
        }
        VideoPlayer(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            videoPlayerState = videoPlayerState
        )
    }

    Column(modifier = Modifier.padding(25.dp)) {
        Text("VideoPlayer", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("视频播放器", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}
package com.fphoenixcorneae.baseui.ui.media

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PauseCircleOutline
import androidx.compose.material.icons.rounded.PlayCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.CustomSeekbar
import com.fphoenixcorneae.baseui.GradientButton
import com.fphoenixcorneae.baseui.R
import com.fphoenixcorneae.baseui.ext.clickableNoRipple
import com.fphoenixcorneae.baseui.ext.format
import com.fphoenixcorneae.baseui.media.audio.rememberAudioPlayerState
import kotlin.time.Duration.Companion.milliseconds

@Preview
@Composable
fun AudioPlayerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff5f5f5)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val context = LocalContext.current
        val uri = Uri.parse("android.resource://${context.packageName}/${R.raw.audio_luo_tangbohu}")
        val uri2 = Uri.parse("android.resource://${context.packageName}/${R.raw.audio_shipmenttovietnam_loveblowswiththewind}")
//        val path = "https://music.163.com/song/media/outer/url?id=2026224214"
        // 网易云音乐外链地址可使用上面的地址在浏览器中打开获取（因为一段时间后会失效），更换音乐只需更换id即可
//        val path =
//            "https://m701.music.126.net/20241123142322/6d93670e7eaf8ed2674ec3a539ab6948/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/25358070960/0c23/1e94/f335/e256582282cad4d51161905234e4dd08.mp3"
        var source by remember { mutableStateOf<Any>(uri) }
        val audioPlayerState = if (!LocalView.current.isInEditMode) {
            rememberAudioPlayerState(source)
        } else {
            null
        }
        Text(
            text = audioPlayerState?.currentPosition?.milliseconds.format(),
            style = TextStyle(
                color = Color.Black,
                fontSize = 32.sp,
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray.copy(0.8f))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = if (audioPlayerState?.isPlaying == true) {
                    Icons.Rounded.PauseCircleOutline
                } else {
                    Icons.Rounded.PlayCircleOutline
                },
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clickableNoRipple {
                        audioPlayerState?.playOrPause()
                    },
                tint = Color.White,
            )
            CustomSeekbar()
        }
        Spacer(modifier = Modifier.height(16.dp))
        GradientButton(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(40.dp),
            "切换音频"
        ) {
            source = if (source == uri2) {
                uri
            } else {
                uri2
            }
            Toast.makeText(context, "切换音频成功", Toast.LENGTH_SHORT).show()
        }
    }

    Column(modifier = Modifier.padding(25.dp)) {
        Text("AudioPlayer", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("音频播放器", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}
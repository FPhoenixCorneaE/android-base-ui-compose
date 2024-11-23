package com.fphoenixcorneae.baseui.ui.basic

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.R
import com.fphoenixcorneae.baseui.Seekbar

@Preview
@Composable
fun SeekbarScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff5f5f5))
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(Color.White, RoundedCornerShape(6.dp))
                .padding(horizontal = 20.dp)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_voice_silence),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.CenterStart)
            )
            Image(
                painter = painterResource(id = R.mipmap.ic_voice),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.CenterEnd)
            )
            Seekbar(
                modifier = Modifier
                    .padding(horizontal = 70.dp)
                    .align(Alignment.CenterStart)
            ) { progress: Float, fromUser: Boolean ->
                Log.d("Seekbar", "SeekbarScreen: onProgressChanged: $progress, $fromUser")
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        Seekbar(
            progress = 20f,
            progressRange = -100f..100f
        ) { progress: Float, fromUser: Boolean ->
            Log.d("Seekbar", "SeekbarScreen: onProgressChanged: $progress, $fromUser")
        }
        Spacer(modifier = Modifier.height(50.dp))
        Seekbar(
            progress = 20f,
            showProgressText = false,
            thumbSize = 20.dp
        ) { progress: Float, fromUser: Boolean ->
            Log.d("Seekbar", "SeekbarScreen: onProgressChanged: $progress, $fromUser")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text("不可操作", color = Color.Black, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(50.dp))
        Seekbar(
            progress = 20f,
            enabled = false
        ) { progress: Float, fromUser: Boolean ->
            Log.d("Seekbar", "SeekbarScreen: onProgressChanged: $progress, $fromUser")
        }
    }
    Column(modifier = Modifier.padding(25.dp)) {
        Text("CustomSeekbar", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("拖动条", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}
package com.fphoenixcorneae.baseui.ui.advanced

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.GlowCircle

@Preview
@Composable
fun GlowCircleScreen() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        GlowCircle(
            modifier = Modifier
                .size(60.dp)
                .background(Color.Black),
            blurStyle = BlurMaskFilter.Blur.NORMAL,
        )
        GlowCircle(
            modifier = Modifier
                .size(60.dp)
                .background(Color.Black),
            glowColor = Color.Magenta,
            blurStyle = BlurMaskFilter.Blur.SOLID,
        )
        GlowCircle(
            modifier = Modifier
                .size(60.dp)
                .background(Color.Black),
            blurStyle = BlurMaskFilter.Blur.INNER,
        )
        GlowCircle(
            modifier = Modifier
                .size(60.dp)
                .background(Color.Black),
            glowColor = Color.Magenta,
            blurStyle = BlurMaskFilter.Blur.OUTER,
        )
    }
    Column(modifier = Modifier.padding(25.dp)) {
        Text("GlowCircle", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("辉光呼吸圆圈", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}
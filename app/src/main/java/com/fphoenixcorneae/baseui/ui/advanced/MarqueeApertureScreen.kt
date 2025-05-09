package com.fphoenixcorneae.baseui.ui.advanced

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.MarqueeAperture

@Preview
@Composable
fun MarqueeApertureScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MarqueeAperture(modifier = Modifier.size(60.dp), radius = 8.dp)
        Spacer(modifier = Modifier.height(8.dp))
        MarqueeAperture(
            modifier = Modifier.size(60.dp),
            radius = 30.dp,
            color2Stops = null
        )
        Spacer(modifier = Modifier.height(8.dp))
        MarqueeAperture(
            modifier = Modifier
                .width(80.dp)
                .height(60.dp),
            radius = 0.dp,
            color1Stops = null
        )
    }
    Column(modifier = Modifier.padding(25.dp)) {
        Text("MarqueeAperture", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("跑马灯光圈", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}
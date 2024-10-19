package com.fphoenixcorneae.baseui.ui.advanced

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.ShadowLayout
import com.fphoenixcorneae.baseui.drawColoredShadow

@Preview
@Composable
fun ShadowScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff5f5f5))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            modifier = Modifier.drawColoredShadow(
                color = Color.Red,
                alpha = 0.5f,
                offsetX = 0.dp,
                offsetY = (-4).dp,
                shadowRadius = 8.dp,
            ),
            thickness = 2.dp,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(20.dp))
        ShadowLayout(
            modifier = Modifier.clip(RoundedCornerShape(8.dp)),
            shadowColor = Color.Yellow,
            offsetX = 8.dp,
            offsetY = 8.dp,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(color = Color.White)
            )
        }
    }
    Column(modifier = Modifier.padding(25.dp)) {
        Text("ShadowLayout", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("阴影布局", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}
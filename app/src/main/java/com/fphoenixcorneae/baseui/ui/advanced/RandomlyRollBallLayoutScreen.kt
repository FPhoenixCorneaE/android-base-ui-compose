package com.fphoenixcorneae.baseui.ui.advanced

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.RandomlyRollBallLayout

@Preview
@Composable
fun RandomlyRollBallLayoutScreen() {
    RandomlyRollBallLayout()
    Column(modifier = Modifier.padding(25.dp)) {
        Text("RandomlyRollBallLayout", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("随机滚动小球布局", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}
package com.fphoenixcorneae.baseui.ui.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.Badge

@Preview
@Composable
fun BadgeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff5f5f5)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // 不设置内容则显示一个小红点
        Badge()
        Spacer(modifier = Modifier.height(20.dp))
        Badge(number = 8)
        Spacer(modifier = Modifier.height(20.dp))
        Badge(number = 99)
        Spacer(modifier = Modifier.height(20.dp))
        Badge(number = 999, maxCharacterCount = 4)
        Spacer(modifier = Modifier.height(20.dp))
        Badge(number = 1001, maxCharacterCount = 4)
    }
    Column(modifier = Modifier.padding(25.dp)) {
        Text("Badge", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("红点提示", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}
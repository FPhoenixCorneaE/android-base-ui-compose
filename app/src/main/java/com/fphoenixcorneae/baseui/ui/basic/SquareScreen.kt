package com.fphoenixcorneae.baseui.ui.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.Square

@Preview
@Composable
fun SquareScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff5f5f5)),
        contentAlignment = Alignment.Center
    ) {
        Square(modifier = Modifier.width(60.dp)) {
            Box(modifier = Modifier.background(Color.Blue))
        }
    }
    Column(modifier = Modifier.padding(25.dp)) {
        Text("Square", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("正方形", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}
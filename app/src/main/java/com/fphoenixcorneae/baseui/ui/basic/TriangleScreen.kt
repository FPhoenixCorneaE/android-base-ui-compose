package com.fphoenixcorneae.baseui.ui.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.fphoenixcorneae.baseui.Triangle

@Preview
@Composable
fun TriangleScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff5f5f5)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Triangle(width = 60.dp, height = 40.dp, color = Color.Blue, cornerRadius = 20f)
        Spacer(modifier = Modifier.height(8.dp))
        Triangle(
            width = 60.dp,
            height = 40.dp,
            color = Color.Red,
            cornerRadius = 20f,
            rotateDegree = 180f
        )
    }
    Column(modifier = Modifier.padding(25.dp)) {
        Text("Triangle", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("三角形", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}
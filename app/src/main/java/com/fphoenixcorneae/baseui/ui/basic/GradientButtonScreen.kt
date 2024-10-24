package com.fphoenixcorneae.baseui.ui.basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.GradientButton

@Preview
@Composable
fun GradientButtonScreen() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        GradientButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            text = "Gradient Button",
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500
            ),
            gradientBrush = Brush.linearGradient(
                listOf(
                    Color(0xFF4185F2),
                    Color(0xFF3362F7)
                )
            ),
        )
        Spacer(Modifier.height(16.dp))
        GradientButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            text = "禁用状态",
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500
            ),
            gradientBrush = Brush.linearGradient(
                listOf(
                    Color(0xFF4185F2),
                    Color(0xFF3362F7)
                )
            ),
            shape = RoundedCornerShape(6.dp),
            enabled = false,
        )
    }
    Column(modifier = Modifier.padding(25.dp)) {
        Text("GradientButton", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("渐变背景按钮", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}
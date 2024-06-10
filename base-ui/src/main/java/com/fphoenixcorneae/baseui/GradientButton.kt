package com.fphoenixcorneae.baseui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 渐变背景按钮
 * @param gradientBrush 渐变画笔
 */
@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = TextStyle.Default,
    gradientBrush: Brush,
    shape: Shape = ButtonDefaults.shape,
    onClick: () -> Unit = { },
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradientBrush),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                style = textStyle,
            )
        }
    }
}

@Preview
@Composable
fun PreviewGradientButton() {
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
        shape = RoundedCornerShape(6.dp),
    )
}